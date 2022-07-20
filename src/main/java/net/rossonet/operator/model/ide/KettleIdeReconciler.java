package net.rossonet.operator.model.ide;

import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;
import net.rossonet.operator.model.LogUtils;
import net.rossonet.operator.model.StaticUtils;

@ControllerConfiguration(dependents = { @Dependent(type = IdeResource.class),
		@Dependent(type = ServiceIdeResource.class), @Dependent(type = IngressIdeResource.class) })
public class KettleIdeReconciler implements Reconciler<KettleIde> {
	private static final Logger logger = Logger.getLogger(KettleIdeReconciler.class.getName());

	@SuppressWarnings("unused")
	private final KubernetesClient kubernetesClient;

	public KettleIdeReconciler() {
		this(new DefaultKubernetesClient(new ConfigBuilder().withNamespace(null).build()));
	}

	public KettleIdeReconciler(final KubernetesClient kubernetesClient) {
		this.kubernetesClient = kubernetesClient;
	}

	@Override
	public UpdateControl<KettleIde> reconcile(final KettleIde resource, final Context<KettleIde> context) {
		try {
			logger.info("reconciler  " + resource + " -> " + context);
			final String name = context.getSecondaryResource(Deployment.class).get().getMetadata().getName();
			resource.setStatus(StaticUtils.createKettleIdeStatus(name));
			return UpdateControl.patchStatus(resource);
		} catch (final Exception ee) {
			logger.severe(LogUtils.stackTraceToString(ee));
			throw ee;
		}
	}

}
