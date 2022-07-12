package net.rossonet.operator.model.simple.job;

import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.batch.v1.Job;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;
import net.rossonet.operator.LogUtils;
import net.rossonet.operator.model.StaticUtils;

@ControllerConfiguration(dependents = { @Dependent(type = SimpleJobResource.class) })
public class KettleJobReconciler implements Reconciler<KettleJob> {
	private static final Logger logger = Logger.getLogger(KettleJobReconciler.class.getName());

	@SuppressWarnings("unused")
	private final KubernetesClient kubernetesClient;

	public KettleJobReconciler() {
		this(new DefaultKubernetesClient());
	}

	public KettleJobReconciler(final KubernetesClient kubernetesClient) {
		this.kubernetesClient = kubernetesClient;
	}

	@Override
	public UpdateControl<KettleJob> reconcile(final KettleJob resource, final Context<KettleJob> context) {
		try {
			logger.info("reconciler  " + resource + " -> " + context);
			final String name = context.getSecondaryResource(Job.class).get().getMetadata().getName();
			resource.setStatus(StaticUtils.createKettleJobStatus(name));
			return UpdateControl.patchStatus(resource);
		} catch (final Exception ee) {
			logger.severe(LogUtils.stackTraceToString(ee));
			throw ee;
		}
	}

}
