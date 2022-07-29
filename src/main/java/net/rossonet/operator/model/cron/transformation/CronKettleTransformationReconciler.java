package net.rossonet.operator.model.cron.transformation;

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
import net.rossonet.operator.model.simple.transformation.RepositoriesConfigMapResource;

@ControllerConfiguration(dependents = { @Dependent(type = SimpleCronTransformationResource.class),
		@Dependent(type = RepositoriesConfigMapResource.class) })
public class CronKettleTransformationReconciler implements Reconciler<CronKettleTransformation> {
	private static final Logger logger = Logger.getLogger(CronKettleTransformationReconciler.class.getName());

	@SuppressWarnings("unused")
	private final KubernetesClient kubernetesClient;

	public CronKettleTransformationReconciler() {
		this(new DefaultKubernetesClient(new ConfigBuilder().withNamespace(null).build()));
	}

	public CronKettleTransformationReconciler(final KubernetesClient kubernetesClient) {
		this.kubernetesClient = kubernetesClient;
	}

	@Override
	public UpdateControl<CronKettleTransformation> reconcile(final CronKettleTransformation resource,
			final Context<CronKettleTransformation> context) {
		try {
			logger.info("reconciler  " + resource + " -> " + context);
			final CronJob job = context.getSecondaryResource(CronJob.class).get();
			final ConfigMap configMap = context.getSecondaryResource(ConfigMap.class).get();
			logger.fine("repository.xml in config map  " + configMap.getData().get(StaticUtils.REPOSITORIES));
			resource.setStatus(StaticUtils.createCronKettleTransformationStatus(resource.getStatus(), job));
			return UpdateControl.patchStatus(resource);
		} catch (final Exception ee) {
			logger.severe(LogUtils.stackTraceToString(ee));
			throw ee;
		}
	}

}
