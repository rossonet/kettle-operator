package net.rossonet.operator.model.simple.job;

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
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;
import net.rossonet.operator.model.StaticUtils;

@ControllerConfiguration(dependents = { @Dependent(type = SimpleJobResource.class) })
public class KettleJobReconciler implements Reconciler<KettleJob>, ErrorStatusHandler<KettleJob>, Cleaner<KettleJob> {
	public static final String SELECTOR = "managed";
	private static final Logger logger = Logger.getLogger(KettleJobReconciler.class.getName());
	@SuppressWarnings("unused")
	private final KubernetesClient client;

	public KettleJobReconciler(final KubernetesClient client) {
		this.client = client;
	}

	@Override
	public DeleteControl cleanup(final KettleJob resource, final Context<KettleJob> context) {
		logger.fine("cleanup  " + resource + " -> " + context);
		return DeleteControl.defaultDelete();
	}

	@Override
	public UpdateControl<KettleJob> reconcile(final KettleJob resource, final Context<KettleJob> context)
			throws Exception {
		logger.fine("reconcile  " + resource + " -> " + context);
		final String name = context.getSecondaryResource(ConfigMap.class).get().getMetadata().getName();
		resource.setStatus((KettleJobStatus) StaticUtils.createStatus(name));
		return UpdateControl.patchStatus(resource);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ErrorStatusUpdateControl<KettleJob> updateErrorStatus(final KettleJob resource,
			final Context<KettleJob> context, final Exception e) {
		logger.fine("updateErrorStatus  " + resource + " -> " + context);
		return (ErrorStatusUpdateControl<KettleJob>) StaticUtils.handleError(resource, e);
	}

}
