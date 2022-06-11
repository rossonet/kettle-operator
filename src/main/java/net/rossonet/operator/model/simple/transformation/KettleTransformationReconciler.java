package net.rossonet.operator.model.simple.transformation;

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

@ControllerConfiguration(dependents = { @Dependent(type = SimpleTransformationResource.class) })
public class KettleTransformationReconciler implements Reconciler<KettleTransformation>,
		ErrorStatusHandler<KettleTransformation>, Cleaner<KettleTransformation> {
	public static final String SELECTOR = "managed";

	private static final Logger logger = Logger.getLogger(KettleTransformationReconciler.class.getName());

	@SuppressWarnings("unused")
	private final KubernetesClient client;

	public KettleTransformationReconciler(final KubernetesClient client) {
		this.client = client;
	}

	@Override
	public DeleteControl cleanup(final KettleTransformation resource, final Context<KettleTransformation> context) {
		logger.fine("cleanup  " + resource + " -> " + context);
		return DeleteControl.defaultDelete();
	}

	@Override
	public UpdateControl<KettleTransformation> reconcile(final KettleTransformation resource,
			final Context<KettleTransformation> context) throws Exception {
		logger.fine("reconcile  " + resource + " -> " + context);
		final String name = context.getSecondaryResource(ConfigMap.class).get().getMetadata().getName();
		resource.setStatus((KettleTransformationStatus) StaticUtils.createStatus(name));
		return UpdateControl.patchStatus(resource);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ErrorStatusUpdateControl<KettleTransformation> updateErrorStatus(final KettleTransformation resource,
			final Context<KettleTransformation> context, final Exception e) {
		logger.fine("updateErrorStatus  " + resource + " -> " + context);
		return (ErrorStatusUpdateControl<KettleTransformation>) StaticUtils.handleError(resource, e);
	}
}
