package net.rossonet.operator.model.simple.transformation;

import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;
import net.rossonet.operator.model.StaticUtils;

@ControllerConfiguration(dependents = { @Dependent(type = SimpleTransformationResource.class) })
public class KettleTransformationReconciler implements Reconciler<KettleTransformation> {
	public static final String SELECTOR = "managed";

	private static final Logger logger = Logger.getLogger(KettleTransformationReconciler.class.getName());

	@Override
	public UpdateControl<KettleTransformation> reconcile(final KettleTransformation resource,
			final Context<KettleTransformation> context) throws Exception {
		logger.info("reconcile  " + resource + " -> " + context);
		final String name = context.getSecondaryResource(ConfigMap.class).get().getMetadata().getName();
		resource.setStatus(StaticUtils.createKettleTransformationStatus(name));
		return UpdateControl.patchStatus(resource);
	}

}
