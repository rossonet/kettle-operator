package net.rossonet.operator.model.cron.job;

import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.batch.v1.CronJob;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;
import net.rossonet.operator.model.StaticUtils;

@ControllerConfiguration(dependents = { @Dependent(type = SimpleCronJobResource.class) })
public class CronKettleJobReconciler implements Reconciler<CronKettleJob> {
	public static final String SELECTOR = "app.kubernetes.io/managed-by=kettle-operator";
	private static final Logger logger = Logger.getLogger(CronKettleJobReconciler.class.getName());

	@Override
	public UpdateControl<CronKettleJob> reconcile(final CronKettleJob resource, final Context<CronKettleJob> context)
			throws Exception {
		logger.info("reconciler  " + resource + " -> " + context);
		final String name = context.getSecondaryResource(CronJob.class).get().getMetadata().getName();
		resource.setStatus(StaticUtils.createCronKettleJobStatus(name));
		return UpdateControl.patchStatus(resource);
	}

}
