package net.rossonet.operator.model.repository;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.ExecListener;
import io.fabric8.kubernetes.client.dsl.ExecWatch;
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
	private static class ExecPodExecListener implements ExecListener {
		@Override
		public void onClose(final int i, final String s) {
			logger.info("Shell Closing");
			execLatch.countDown();
		}

		@Override
		public void onFailure(final Throwable t, final Response failureResponse) {
			logger.info("Some error encountered");
			execLatch.countDown();
		}

		@Override
		public void onOpen() {
			logger.info("Shell was opened");
		}
	}

	public enum RepositoryStatus {
		FAULT, INIT, SYNCHRONIZED
	}

	private static final CountDownLatch execLatch = new CountDownLatch(1);

	private static final Logger logger = Logger.getLogger(KettleRepositoryReconciler.class.getName());
	private final KubernetesClient kubernetesClient;

	public KettleRepositoryReconciler() {
		this(new DefaultKubernetesClient());
	}

	public KettleRepositoryReconciler(final KubernetesClient kubernetesClient) {
		this.kubernetesClient = kubernetesClient;
	}

	private void databaseManagement(final KettleRepository kettleRepository, final Deployment deploymentDatabase,
			final Service serviceDatabase) {
		try {
			if (kettleRepository.getStatus().getReturnCode()
					.equals(KettleRepositoryReconciler.RepositoryStatus.INIT.toString())) {
				final ByteArrayOutputStream out = new ByteArrayOutputStream();
				final ByteArrayOutputStream error = new ByteArrayOutputStream();
				kubernetesClient.getConfiguration().setWebsocketTimeout(60000L);
				final ExecWatch execWatch = kubernetesClient.pods()
						.inNamespace(deploymentDatabase.getMetadata().getNamespace())
						.withName(deploymentDatabase.getMetadata().getName()).writingOutput(out).writingError(error)
						.usingListener(new ExecPodExecListener()).exec("ls", "/");

				final boolean latchTerminationStatus = execLatch.await(15, TimeUnit.SECONDS);
				if (!latchTerminationStatus) {
					logger.warning("Latch could not terminate within specified time");
				}
				logger.info("Exec Output: {} " + out.toString());
				execWatch.close();
			} else {
				logger.info("database already loaded, kettleRepository.getStatus().getReturnCode()="
						+ kettleRepository.getStatus().getReturnCode());
			}
		} catch (final Exception e) {
			logger.severe("Exception in database management " + LogUtils.stackTraceToString(e));
		}
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
