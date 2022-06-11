package net.rossonet.operator.model.cron.job;

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
import net.rossonet.operator.model.simple.job.KettleJob;

@ControllerConfiguration
public class CronKettleJobReconciler
		implements Reconciler<KettleJob>, ErrorStatusHandler<KettleJob>, Cleaner<KettleJob> {
	private static final Logger logger = Logger.getLogger(CronKettleJobReconciler.class.getName());
	private final KubernetesClient client;

	public CronKettleJobReconciler(final KubernetesClient client) {
		this.client = client;
	}

	@Override
	public DeleteControl cleanup(final KettleJob resource, final Context<KettleJob> context) {
		// TODO Auto-generated method stub
		logger.info("cleanup " + resource + " -> " + context);
		return DeleteControl.defaultDelete();
	}

	@Override
	public UpdateControl<KettleJob> reconcile(final KettleJob resource, final Context<KettleJob> context)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("reconcile " + resource + " -> " + context);
		return UpdateControl.noUpdate();
	}

	@Override
	public ErrorStatusUpdateControl<KettleJob> updateErrorStatus(final KettleJob resource,
			final Context<KettleJob> context, final Exception e) {
		// TODO Auto-generated method stub
		logger.info("updateErrorStatus " + resource + " -> " + context);
		return ErrorStatusUpdateControl.noStatusUpdate();
	}

}
