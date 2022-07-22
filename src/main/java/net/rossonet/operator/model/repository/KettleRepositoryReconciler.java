package net.rossonet.operator.model.repository;

import java.io.IOException;
import java.util.Date;
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
import net.rossonet.operator.model.StaticUtils.ExecResult;

@ControllerConfiguration(dependents = { @Dependent(type = RepositoryResource.class),
		@Dependent(type = ServiceRepositoryResource.class) })
public class KettleRepositoryReconciler implements Reconciler<KettleRepository> {

	public enum RepositoryStatus {
		FAULT, INIT, SYNCHRONIZED
	}

	private static final Logger logger = Logger.getLogger(KettleRepositoryReconciler.class.getName());
	private static final String SYNCHRONIZED_FILE_MARK = "SYNCHRONIZED FILE FOUND";
	private static final String SYNCHRONIZED_PATH = "/SYNCHRONIZED";
	private static final long TIMEOUT_RESTORE_DB_SECONDS = 5 * 60;
	private static final String TMP_CHECK_SCRIPT_SH = "/tmp/check_script.sh";
	private static final String TMP_LOAD_DB_SCRIPT_PATH = "/tmp/load_db.sh";
	private final KubernetesClient kubernetesClient;

	public KettleRepositoryReconciler() {
		this(new DefaultKubernetesClient(new ConfigBuilder().withNamespace(null).build()));
	}

	public KettleRepositoryReconciler(final KubernetesClient kubernetesClient) {
		this.kubernetesClient = kubernetesClient;
	}

	private boolean checkSynchronizedFile(final Deployment deploymentDatabase)
			throws IOException, InterruptedException {
		final String script = "if [ -f /SYNCHRONIZED ]\nthen\necho '" + SYNCHRONIZED_FILE_MARK
				+ "'\nelse\necho 'KO'\nfi\n";
		StaticUtils.saveStringToFileOnPod(kubernetesClient, deploymentDatabase, script, TMP_CHECK_SCRIPT_SH);
		final String[] command = new String[] { "bash", TMP_CHECK_SCRIPT_SH };
		final ExecResult checkResult = StaticUtils.execCommandOnPod(kubernetesClient, deploymentDatabase, command,
				TIMEOUT_RESTORE_DB_SECONDS);
		return checkResult.getStandardOutput().contains(SYNCHRONIZED_FILE_MARK);
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
					.equals(KettleRepositoryReconciler.RepositoryStatus.SYNCHRONIZED.toString())) {
				if (!checkSynchronizedFile(deploymentDatabase)) {
					kettleRepository.getStatus()
							.setReturnCode(KettleRepositoryReconciler.RepositoryStatus.INIT.toString());
				}
			}
			if (kettleRepository.getStatus().getReturnCode()
					.equals(KettleRepositoryReconciler.RepositoryStatus.INIT.toString())) {
				String dumpPath = null;
				final String repositoryUrl = kettleRepository.getSpec().getRepositoryUrl();
				if (repositoryUrl.startsWith(StaticUtils.HTTP) || repositoryUrl.startsWith(StaticUtils.HTTPS)
						|| repositoryUrl.startsWith(StaticUtils.FTP)) {
					logger.severe(repositoryUrl + " is managed by wget");
					dumpPath = downloadDumpDatabaseFromFtpHttpOrHttps(deploymentDatabase, kettleRepository.getSpec());
				} else if (repositoryUrl.startsWith(StaticUtils.GIT_HTTP)
						|| repositoryUrl.startsWith(StaticUtils.GIT_HTTPS)
						|| repositoryUrl.startsWith(StaticUtils.GIT_SSH)) {
					logger.severe(repositoryUrl + " is managed by git");
					dumpPath = downloadDumpDatabaseFromGit(deploymentDatabase, kettleRepository.getSpec());
				} else if (repositoryUrl.startsWith(StaticUtils.S3)) {
					logger.severe(repositoryUrl + " is managed by s3 client");
					dumpPath = downloadDumpDatabaseFromS3(deploymentDatabase, kettleRepository.getSpec());
				} else if (repositoryUrl.startsWith(StaticUtils.FILE)) {
					logger.severe(repositoryUrl + " could not to be downloaded");
					dumpPath = repositoryUrl.substring(StaticUtils.FILE.length());
				} else {
					dumpPath = null;
					logger.severe(repositoryUrl + " not recognized");
				}
				if (dumpPath != null) {
					restoreDatabase(kettleRepository, deploymentDatabase, dumpPath);
				}
				setStatusSynchronized(kettleRepository, deploymentDatabase);
			} else {
				logger.info("database already loaded, kettleRepository.getStatus().getReturnCode()="
						+ kettleRepository.getStatus().getReturnCode());
			}
		} catch (final Exception e) {
			logger.severe("Exception in database management " + LogUtils.stackTraceToString(e));
		}
	}

	private String downloadDumpDatabaseFromFtpHttpOrHttps(final Deployment deploymentDatabase,
			final KettleRepositorySpec kettleRepositorySpec) throws InterruptedException {
		final String targetDirectory = createTemporaryRepositoryDirectory(deploymentDatabase);
		final String targetFile = targetDirectory + "/repository.sql";
		if (kettleRepositorySpec.getRepositoryUsername() != null
				&& kettleRepositorySpec.getRepositoryPassword() != null) {
			if (kettleRepositorySpec.getRepositoryUrl().startsWith(StaticUtils.FTP)) {
				final String[] command = new String[] { "wget", "-O", targetFile,
						"--ftp-user=" + kettleRepositorySpec.getRepositoryUsername(),
						"--ftp-password=" + kettleRepositorySpec.getRepositoryPassword(),
						kettleRepositorySpec.getRepositoryUrl() };
				StaticUtils.execCommandOnPod(kubernetesClient, deploymentDatabase, command, TIMEOUT_RESTORE_DB_SECONDS);
			} else {
				final String[] command = new String[] { "wget", "-O", targetFile,
						"--http-user=" + kettleRepositorySpec.getRepositoryUsername(),
						"--http-password=" + kettleRepositorySpec.getRepositoryPassword(),
						kettleRepositorySpec.getRepositoryUrl() };
				StaticUtils.execCommandOnPod(kubernetesClient, deploymentDatabase, command, TIMEOUT_RESTORE_DB_SECONDS);
			}
		} else {
			final String[] command = new String[] { "wget", "-O", targetFile, kettleRepositorySpec.getRepositoryUrl() };
			StaticUtils.execCommandOnPod(kubernetesClient, deploymentDatabase, command, TIMEOUT_RESTORE_DB_SECONDS);
		}
		return targetFile;
	}

	private String downloadDumpDatabaseFromGit(final Deployment deploymentDatabase,
			final KettleRepositorySpec kettleRepositorySpec) throws InterruptedException {
		@SuppressWarnings("unused")
		final String targetDirectory = createTemporaryRepositoryDirectory(deploymentDatabase);
		// TODO Auto-generated method stub
		return null;
	}

	private String downloadDumpDatabaseFromS3(final Deployment deploymentDatabase,
			final KettleRepositorySpec kettleRepositorySpec) throws InterruptedException {
		@SuppressWarnings("unused")
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
			} else {
				logger.info("reconciler  waiting all kubernetes resources");
			}
			return UpdateControl.patchStatus(resource);
		} catch (final Exception ee) {
			logger.severe(LogUtils.stackTraceToString(ee));
			throw ee;
		}
	}

	private void restoreDatabase(final KettleRepository kettleRepository, final Deployment deploymentDatabase,
			final String dumpPath) throws InterruptedException, IOException {
		final String script = "#!/bin/bash\ncat " + dumpPath + " | PGPASSWORD="
				+ kettleRepository.getSpec().getPassword() + " psql -h localhost -U "
				+ kettleRepository.getSpec().getUsername() + " " + kettleRepository.getSpec().getDatabaseName() + "\n";
		StaticUtils.saveStringToFileOnPod(kubernetesClient, deploymentDatabase, script, TMP_LOAD_DB_SCRIPT_PATH);
		final String[] commandChmod = new String[] { "chmod", "+x", TMP_LOAD_DB_SCRIPT_PATH };
		StaticUtils.execCommandOnPod(kubernetesClient, deploymentDatabase, commandChmod, TIMEOUT_RESTORE_DB_SECONDS);
		final String[] command = new String[] { TMP_LOAD_DB_SCRIPT_PATH };
		StaticUtils.execCommandOnPod(kubernetesClient, deploymentDatabase, command, TIMEOUT_RESTORE_DB_SECONDS);
	}

	private void setStatusSynchronized(final KettleRepository kettleRepository, final Deployment deploymentDatabase)
			throws InterruptedException, IOException {
		final String payload = new Date().toString();
		StaticUtils.saveStringToFileOnPod(kubernetesClient, deploymentDatabase, payload, SYNCHRONIZED_PATH);
		kettleRepository.getStatus().setReturnCode(KettleRepositoryReconciler.RepositoryStatus.SYNCHRONIZED.toString());
	}

}
