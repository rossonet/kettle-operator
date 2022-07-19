package net.rossonet.operator.model.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.EnvVar;
import io.fabric8.kubernetes.api.model.LabelSelector;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.PodSpec;
import io.fabric8.kubernetes.api.model.PodTemplateSpec;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentSpec;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import net.rossonet.operator.model.LogUtils;
import net.rossonet.operator.model.StaticUtils;

@KubernetesDependent(labelSelector = StaticUtils.SELECTOR)
public class RepositoryResource extends CRUKubernetesDependentResource<Deployment, KettleRepository> {

	private static final Logger logger = Logger.getLogger(RepositoryResource.class.getName());

	public RepositoryResource() {
		super(Deployment.class);
		logger.info("Deployment class created");
	}

	@Override
	protected Deployment desired(final KettleRepository kettleRepository, final Context<KettleRepository> context) {
		final Deployment deployment = new Deployment();
		try {
			logger.info("kettle repository " + kettleRepository);
			deployment.setMetadata(new ObjectMeta());
			deployment.getMetadata().setName(kettleRepository.getMetadata().getName());
			deployment.getMetadata().setNamespace(kettleRepository.getMetadata().getNamespace());
			final Map<String, String> labels = new HashMap<>();
			labels.put(StaticUtils.LABEL_MANAGED_BY, StaticUtils.DATA_MANAGED_BY);
			labels.put(StaticUtils.LABEL_APP, kettleRepository.getMetadata().getName());
			labels.put(StaticUtils.LABEL_PART_OF, kettleRepository.getMetadata().getName());
			deployment.getMetadata().setLabels(labels);
			final PodSpec podSpec = new PodSpec();
			final Container containerPostgresql = new Container();
			containerPostgresql.setName(kettleRepository.getMetadata().getName());
			containerPostgresql.setImage(kettleRepository.getSpec().getImage());
			final List<EnvVar> enviroments = new ArrayList<>();
			enviroments.add(new EnvVar("POSTGRES_PASSWORD", kettleRepository.getSpec().getPassword(), null));
			enviroments.add(new EnvVar("POSTGRES_USER", kettleRepository.getSpec().getUsername(), null));
			enviroments.add(new EnvVar("POSTGRES_DB", kettleRepository.getSpec().getDatabaseName(), null));
			containerPostgresql.setEnv(enviroments);
			podSpec.setContainers(Arrays.asList(new Container[] { containerPostgresql }));
			podSpec.setRestartPolicy("Always");
			final DeploymentSpec spec = new DeploymentSpec();
			final LabelSelector selector = new LabelSelector();
			selector.setMatchLabels(labels);
			spec.setSelector(selector);
			final PodTemplateSpec template = new PodTemplateSpec();
			template.setMetadata(new ObjectMeta());
			template.getMetadata().setLabels(labels);
			spec.setTemplate(template);
			deployment.setSpec(spec);
			deployment.getSpec().getTemplate().setSpec(podSpec);
			logger.info("actual deployment " + deployment);
		} catch (final Exception e) {
			logger.severe(LogUtils.stackTraceToString(e));
		}
		return deployment;
	}

}
