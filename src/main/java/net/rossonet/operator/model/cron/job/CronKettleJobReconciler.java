package net.rossonet.operator.model.cron.job;

import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.batch.v1.CronJob;
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

@ControllerConfiguration(dependents = { @Dependent(type = SimpleCronJobResource.class),
		@Dependent(type = RepositoriesCronJobConfigMapResource.class) })
public class CronKettleJobReconciler implements Reconciler<CronKettleJob> {
	private static final Logger logger = Logger.getLogger(CronKettleJobReconciler.class.getName());

	@SuppressWarnings("unused")
	private final KubernetesClient kubernetesClient;

	public CronKettleJobReconciler() {
		this(new DefaultKubernetesClient(new ConfigBuilder().withNamespace(null).build()));
	}

	public CronKettleJobReconciler(final KubernetesClient kubernetesClient) {
		this.kubernetesClient = kubernetesClient;
	}

	@Override
	public UpdateControl<CronKettleJob> reconcile(final CronKettleJob resource, final Context<CronKettleJob> context) {
		try {
			logger.info("reconciler  " + resource + " -> " + context);
			final CronJob job = context.getSecondaryResource(CronJob.class).get();
			final ConfigMap configMap = context.getSecondaryResource(ConfigMap.class).get();
			logger.fine("repository.xml in config map  " + configMap.getData().get(StaticUtils.REPOSITORIES));
			resource.setStatus(StaticUtils.createCronKettleJobStatus(resource.getStatus(), job));
			return UpdateControl.patchStatus(resource);
		} catch (final Exception ee) {
			logger.severe(LogUtils.stackTraceToString(ee));
			throw ee;
		}
	}

}
