package net.rossonet.operator.model.cron.transformation;

import java.util.logging.Logger;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.javaoperatorsdk.operator.api.reconciler.Cleaner;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.DeleteControl;
import io.javaoperatorsdk.operator.api.reconciler.ErrorStatusHandler;
import io.javaoperatorsdk.operator.api.reconciler.ErrorStatusUpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;

@ControllerConfiguration
public class CronKettleTransformationReconciler implements Reconciler<CronKettleTransformation>,
		ErrorStatusHandler<CronKettleTransformation>, Cleaner<CronKettleTransformation> {
	private static final Logger logger = Logger.getLogger(CronKettleTransformationReconciler.class.getName());
	private final KubernetesClient client;

	public CronKettleTransformationReconciler(final KubernetesClient client) {
		this.client = client;
	}

	@Override
	public DeleteControl cleanup(final CronKettleTransformation resource,
			final Context<CronKettleTransformation> context) {
		// TODO Auto-generated method stub
		logger.info("cleanup " + resource + " -> " + context);
		return DeleteControl.defaultDelete();
	}

	@Override
	public UpdateControl<CronKettleTransformation> reconcile(final CronKettleTransformation resource,
			final Context<CronKettleTransformation> context) throws Exception {
		// TODO Auto-generated method stub
		logger.info("reconcile " + resource + " -> " + context);
		return UpdateControl.noUpdate();
	}

	@Override
	public ErrorStatusUpdateControl<CronKettleTransformation> updateErrorStatus(final CronKettleTransformation resource,
			final Context<CronKettleTransformation> context, final Exception e) {
		// TODO Auto-generated method stub
		logger.info("updateErrorStatus " + resource + " -> " + context);
		return ErrorStatusUpdateControl.noStatusUpdate();
	}

}
