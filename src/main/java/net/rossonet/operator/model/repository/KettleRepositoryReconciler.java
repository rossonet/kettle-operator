package net.rossonet.operator.model.repository;

import java.util.UUID;
import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;
import net.rossonet.operator.model.LogUtils;
import net.rossonet.operator.model.StaticUtils;

@ControllerConfiguration(dependents = { @Dependent(type = RepositoryResource.class),
		@Dependent(type = ServiceRepositoryResource.class) })
public class KettleRepositoryReconciler implements Reconciler<KettleRepository> {

	public enum RepositoryStatus {
		FAULT, INIT, SYNCHRONIZED
	}

	private static final Logger logger = Logger.getLogger(KettleRepositoryReconciler.class.getName());
	private static final long TIMEOUT_RESTORE_DB_SECONDS = 5 * 60;
	private final KubernetesClient kubernetesClient;

	public KettleRepositoryReconciler() {
		this(new DefaultKubernetesClient(new ConfigBuilder().withNamespace(null).build()));
	}

	public KettleRepositoryReconciler(final KubernetesClient kubernetesClient) {
		this.kubernetesClient = kubernetesClient;
	}

	private String createTemporaryRepositoryDirectory(final Deployment deploymentDatabase) throws InterruptedException {
		final String target = "/tmp/" + UUID.randomUUID().toString();
		final String[] command = new String[] { "mkdir", "-p", target };
		StaticUtils.execCommandOnPod(kubernetesClient, deploymentDatabase, command, 15);
		return target;
	}

	private void databaseManagement(final KettleRepository kettleRepository, final Deployment deploymentDatabase,
			final Service serviceDatabase) {
		try {
			if (kettleRepository.getStatus().getReturnCode()
					.equals(KettleRepositoryReconciler.RepositoryStatus.INIT.toString())) {
				String dumpPath = null;
				if (kettleRepository.getSpec().getRepositoryUrl().startsWith(StaticUtils.HTTP)
						|| kettleRepository.getSpec().getRepositoryUrl().startsWith(StaticUtils.HTTP)) {
					dumpPath = downloadDumpDatabaseFromHttpOrHttps(deploymentDatabase, kettleRepository.getSpec());
				} else if (kettleRepository.getSpec().getRepositoryUrl().startsWith(StaticUtils.GIT_HTTP)
						|| kettleRepository.getSpec().getRepositoryUrl().startsWith(StaticUtils.GIT_HTTPS)
						|| kettleRepository.getSpec().getRepositoryUrl().startsWith(StaticUtils.GIT_SSH)) {
					dumpPath = downloadDumpDatabaseFromGit(deploymentDatabase, kettleRepository.getSpec());
				} else if (kettleRepository.getSpec().getRepositoryUrl().startsWith(StaticUtils.S3)) {
					dumpPath = downloadDumpDatabaseFromS3(deploymentDatabase, kettleRepository.getSpec());
				} else if (kettleRepository.getSpec().getRepositoryUrl().startsWith(StaticUtils.FILE)) {
					dumpPath = kettleRepository.getSpec().getRepositoryUrl().substring(StaticUtils.FILE.length());
				}
				if (dumpPath != null) {
					final String[] command = new String[] { "cat", dumpPath, "|",
							"PGPASSWORD=" + kettleRepository.getSpec().getPassword(), "psql", "-h", "localhost", "-U",
							kettleRepository.getSpec().getUsername(), kettleRepository.getSpec().getDatabaseName() };
					StaticUtils.execCommandOnPod(kubernetesClient, deploymentDatabase, command,
							TIMEOUT_RESTORE_DB_SECONDS);
				}
			} else {
				logger.info("database already loaded, kettleRepository.getStatus().getReturnCode()="
						+ kettleRepository.getStatus().getReturnCode());
			}
		} catch (final Exception e) {
			logger.severe("Exception in database management " + LogUtils.stackTraceToString(e));
		}
	}

	private String downloadDumpDatabaseFromGit(final Deployment deploymentDatabase, final KettleRepositorySpec spec)
			throws InterruptedException {
		final String targetDirectory = createTemporaryRepositoryDirectory(deploymentDatabase);
		// TODO Auto-generated method stub
		return null;
	}

	private String downloadDumpDatabaseFromHttpOrHttps(final Deployment deploymentDatabase,
			final KettleRepositorySpec spec) throws InterruptedException {
		final String targetDirectory = createTemporaryRepositoryDirectory(deploymentDatabase);
		final String targetFile = targetDirectory + "/repository.sql";
		final String[] command = new String[] { "wget", "-O", targetFile, spec.getRepositoryUrl() };
		StaticUtils.execCommandOnPod(kubernetesClient, deploymentDatabase, command, TIMEOUT_RESTORE_DB_SECONDS);
		return targetFile;
	}

	private String downloadDumpDatabaseFromS3(final Deployment deploymentDatabase, final KettleRepositorySpec spec)
			throws InterruptedException {
		final String targetDirectory = createTemporaryRepositoryDirectory(deploymentDatabase);
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UpdateControl<KettleRepository> reconcile(final KettleRepository resource,
			final Context<KettleRepository> context) {
		try {
			logger.info("reconciler  " + resource + " -> " + context);
			final Deployment deploymentDatabase = context.getSecondaryResource(Deployment.class).get();
			final Service serviceDatabase = context.getSecondaryResource(Service.class).get();
			resource.setStatus(StaticUtils.createKettleRepositoryStatus(deploymentDatabase.getMetadata().getName()));
			if (deploymentDatabase != null && deploymentDatabase.getStatus() != null
					&& deploymentDatabase.getStatus().getReadyReplicas() != null
					&& deploymentDatabase.getStatus().getReadyReplicas() > 0 && serviceDatabase != null) {
				databaseManagement(resource, deploymentDatabase, serviceDatabase);
			}
			return UpdateControl.patchStatus(resource);
		} catch (final Exception ee) {
			logger.severe(LogUtils.stackTraceToString(ee));
			throw ee;
		}
	}

}
