package net.rossonet.operator.model.ide;

import java.io.IOException;
import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.KubernetesResourceList;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.MixedOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;
import net.rossonet.operator.model.LogUtils;
import net.rossonet.operator.model.StaticUtils;
import net.rossonet.operator.model.repository.KettleRepository;

@ControllerConfiguration(dependents = { @Dependent(type = IdeResource.class),
		@Dependent(type = ServiceIdeResource.class), @Dependent(type = IngressIdeResource.class) })
public class KettleIdeReconciler implements Reconciler<KettleIde> {
	private static String footerRepositories = "</repositories>\n";

	private static String headerRepositories = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<repositories>\n";
	private static final Logger logger = Logger.getLogger(KettleIdeReconciler.class.getName());

	private final KubernetesClient kubernetesClient;

	public KettleIdeReconciler() {
		this(new DefaultKubernetesClient(new ConfigBuilder().withNamespace(null).build()));
	}

	public KettleIdeReconciler(final KubernetesClient kubernetesClient) {
		this.kubernetesClient = kubernetesClient;
	}

	private void addConnection(final StringBuilder sb, final KettleRepository kettleRepository) {
		sb.append("  <connection>\n");
		sb.append("    <name>" + kettleRepository.getMetadata().getName() + "</name>\n");
		sb.append("    <server>" + kettleRepository.getMetadata().getName() + "</server>\n");
		sb.append("    <type>POSTGRESQL</type>\n");
		sb.append("    <access>Native</access>\n");
		sb.append("    <database>" + kettleRepository.getSpec().getDatabaseName() + "</database>\n");
		sb.append("    <port>5432</port>\n");
		sb.append("    <username>" + kettleRepository.getSpec().getUsername() + "</username>\n");
		sb.append("    <password>" + kettleRepository.getSpec().getPassword() + "</password>\n");
		sb.append("    <servername/>\n");
		sb.append("    <data_tablespace/>\n");
		sb.append("    <index_tablespace/>\n");
		sb.append("    <attributes>\n");
		sb.append("      <attribute><code>FORCE_IDENTIFIERS_TO_LOWERCASE</code><attribute>N</attribute></attribute>\n");
		sb.append("      <attribute><code>FORCE_IDENTIFIERS_TO_UPPERCASE</code><attribute>N</attribute></attribute>\n");
		sb.append("      <attribute><code>IS_CLUSTERED</code><attribute>N</attribute></attribute>\n");
		sb.append("      <attribute><code>PORT_NUMBER</code><attribute>5432</attribute></attribute>\n");
		sb.append("      <attribute><code>PRESERVE_RESERVED_WORD_CASE</code><attribute>Y</attribute></attribute>\n");
		sb.append("      <attribute><code>QUOTE_ALL_FIELDS</code><attribute>N</attribute></attribute>\n");
		sb.append("      <attribute><code>SUPPORTS_BOOLEAN_DATA_TYPE</code><attribute>Y</attribute></attribute>\n");
		sb.append("      <attribute><code>SUPPORTS_TIMESTAMP_DATA_TYPE</code><attribute>Y</attribute></attribute>\n");
		sb.append("      <attribute><code>USE_POOLING</code><attribute>N</attribute></attribute>\n");
		sb.append("    </attributes>\n");
		sb.append("  </connection>");

	}

	private void addRepository(final StringBuilder sb, final KettleRepository kettleRepository) {
		sb.append(" <repository>\n");
		sb.append("    <id>KettleDatabaseRepository</id>\n");
		sb.append("    <name>Repository " + kettleRepository.getMetadata().getName() + "</name>\n");
		sb.append("    <description>" + "KubernetesRepository configuration:\n" + kettleRepository.getSpec().toString()
				+ "</description>\n");
		sb.append("    <is_default>false</is_default>\n");
		sb.append("    <connection>" + kettleRepository.getMetadata().getName() + "</connection>\n");
		sb.append("  </repository>");

	}

	private void createKettleConfigurationDirectory(final Deployment deployment)
			throws InterruptedException, IOException {
		final String target = "/root/.kettle";
		final String[] command = new String[] { "mkdir", "-p", target };
		StaticUtils.execCommandOnDeployment(kubernetesClient, deployment, command, 15, null);
	}

	@Override
	public UpdateControl<KettleIde> reconcile(final KettleIde resource, final Context<KettleIde> context)
			throws Exception {
		try {
			logger.fine("reconciler  " + resource + " -> " + context);
			final Deployment deploymentIde = context.getSecondaryResource(Deployment.class).get();
			final Service serviceIde = context.getSecondaryResource(Service.class).get();
			resource.setStatus(StaticUtils.createKettleIdeStatus(deploymentIde.getMetadata().getName()));
			if (deploymentIde != null && deploymentIde.getStatus() != null
					&& deploymentIde.getStatus().getReadyReplicas() != null
					&& deploymentIde.getStatus().getReadyReplicas() > 0 && serviceIde != null) {
				final String xmlRepositories = repositoriesManagement(resource, deploymentIde, serviceIde);
				StaticUtils.saveStringToFileOnDeployment(kubernetesClient, deploymentIde, xmlRepositories,
						"/root/.kettle/repositories.xml");
			} else {
				logger.info("reconciler  waiting all kubernetes resources");
			}
			return UpdateControl.patchStatus(resource);
		} catch (final Exception ee) {
			logger.severe(LogUtils.stackTraceToString(ee));
			throw ee;
		}
	}

	private String repositoriesManagement(final KettleIde kettleIde, final Deployment deploymentIde,
			final Service serviceIde) throws InterruptedException, IOException {
		createKettleConfigurationDirectory(deploymentIde);
		final MixedOperation<KettleRepository, KubernetesResourceList<KettleRepository>, Resource<KettleRepository>> repositoryClient = kubernetesClient
				.resources(KettleRepository.class);
		final StringBuilder sb = new StringBuilder();
		sb.append(headerRepositories);
		for (final KettleRepository kettleRepository : repositoryClient
				.inNamespace(kettleIde.getMetadata().getNamespace()).list().getItems()) {
			addConnection(sb, kettleRepository);
			addRepository(sb, kettleRepository);
		}
		sb.append(footerRepositories);
		return sb.toString();
	}

}
