package net.rossonet.operator.model.simple.job;

import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;
import net.rossonet.operator.model.StaticUtils;

@ControllerConfiguration(dependents = { @Dependent(type = SimpleJobResource.class) })
public class KettleJobReconciler implements Reconciler<KettleJob> {
	private static final Logger logger = Logger.getLogger(KettleJobReconciler.class.getName());
	public static final String SELECTOR = "managed";

	@Override
	public UpdateControl<KettleJob> reconcile(final KettleJob resource, final Context<KettleJob> context)
			throws Exception {
		logger.info("reconcile  " + resource + " -> " + context);
		final String name = context.getSecondaryResource(ConfigMap.class).get().getMetadata().getName();
		resource.setStatus((KettleJobStatus) StaticUtils.createStatus(name));
		return UpdateControl.patchStatus(resource);
	}

}
