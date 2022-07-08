package net.rossonet.operator.model.cron.transformation;

import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.batch.v1.CronJob;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;
import net.rossonet.operator.LogUtils;
import net.rossonet.operator.model.StaticUtils;

@ControllerConfiguration(dependents = { @Dependent(type = SimpleCronTransformationResource.class) })
public class CronKettleTransformationReconciler implements Reconciler<CronKettleTransformation> {
	private static final Logger logger = Logger.getLogger(CronKettleTransformationReconciler.class.getName());
	public static final String SELECTOR = "app.kubernetes.io/managed-by=kettle-operator";

	@SuppressWarnings("unused")
	private final KubernetesClient kubernetesClient;

	public CronKettleTransformationReconciler() {
		this(new DefaultKubernetesClient());
	}

	public CronKettleTransformationReconciler(final KubernetesClient kubernetesClient) {
		this.kubernetesClient = kubernetesClient;
	}

	@Override
	public UpdateControl<CronKettleTransformation> reconcile(final CronKettleTransformation resource,
			final Context<CronKettleTransformation> context) {
		try {
			logger.info("reconciler  " + resource + " -> " + context);
			final String name = context.getSecondaryResource(CronJob.class).get().getMetadata().getName();
			resource.setStatus(StaticUtils.createCronKettleTransformationStatus(name));
			return UpdateControl.patchStatus(resource);
		} catch (final Exception ee) {
			logger.severe(LogUtils.stackTraceToString(ee));
			throw ee;
		}
	}

}
