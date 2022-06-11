package net.rossonet.operator.model.cron.job;

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
public class CronKettleJobReconciler
		implements Reconciler<CronKettleJob>, ErrorStatusHandler<CronKettleJob>, Cleaner<CronKettleJob> {
	public static final String SELECTOR = "managed";
	private static final Logger logger = Logger.getLogger(CronKettleJobReconciler.class.getName());
	@SuppressWarnings("unused")
	private final KubernetesClient client;

	public CronKettleJobReconciler(final KubernetesClient client) {
		this.client = client;
	}

	@Override
	public DeleteControl cleanup(final CronKettleJob resource, final Context<CronKettleJob> context) {
		logger.fine("cleanup  " + resource + " -> " + context);
		return DeleteControl.defaultDelete();
	}

	@Override
	public UpdateControl<CronKettleJob> reconcile(final CronKettleJob resource, final Context<CronKettleJob> context)
			throws Exception {
		logger.fine("reconcile  " + resource + " -> " + context);
		final String name = context.getSecondaryResource(ConfigMap.class).get().getMetadata().getName();
		resource.setStatus((CronKettleJobStatus) StaticUtils.createStatus(name));
		return UpdateControl.patchStatus(resource);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ErrorStatusUpdateControl<CronKettleJob> updateErrorStatus(final CronKettleJob resource,
			final Context<CronKettleJob> context, final Exception e) {
		logger.fine("updateErrorStatus  " + resource + " -> " + context);
		return (ErrorStatusUpdateControl<CronKettleJob>) StaticUtils.handleError(resource, e);
	}

}
