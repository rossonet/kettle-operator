package net.rossonet.operator.model.cron.transformation;

import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.javaoperatorsdk.operator.api.reconciler.Cleaner;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.DeleteControl;
import io.javaoperatorsdk.operator.api.reconciler.ErrorStatusHandler;
import io.javaoperatorsdk.operator.api.reconciler.ErrorStatusUpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import net.rossonet.operator.model.StaticUtils;

@ControllerConfiguration
public class CronKettleTransformationReconciler implements Reconciler<CronKettleTransformation>,
		ErrorStatusHandler<CronKettleTransformation>, Cleaner<CronKettleTransformation> {
	public static final String SELECTOR = "managed";
	private static final Logger logger = Logger.getLogger(CronKettleTransformationReconciler.class.getName());
	@SuppressWarnings("unused")
	private final KubernetesClient client;

	public CronKettleTransformationReconciler(final KubernetesClient client) {
		this.client = client;
	}

	@Override
	public DeleteControl cleanup(final CronKettleTransformation resource,
			final Context<CronKettleTransformation> context) {
		logger.fine("cleanup  " + resource + " -> " + context);
		return DeleteControl.defaultDelete();
	}

	@Override
	public UpdateControl<CronKettleTransformation> reconcile(final CronKettleTransformation resource,
			final Context<CronKettleTransformation> context) throws Exception {
		logger.fine("reconcile  " + resource + " -> " + context);
		final String name = context.getSecondaryResource(ConfigMap.class).get().getMetadata().getName();
		resource.setStatus((CronKettleTransformationStatus) StaticUtils.createStatus(name));
		return UpdateControl.patchStatus(resource);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ErrorStatusUpdateControl<CronKettleTransformation> updateErrorStatus(final CronKettleTransformation resource,
			final Context<CronKettleTransformation> context, final Exception e) {
		logger.fine("updateErrorStatus  " + resource + " -> " + context);
		return (ErrorStatusUpdateControl<CronKettleTransformation>) StaticUtils.handleError(resource, e);
	}

}
