package net.rossonet.operator.model.repository;

import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;
import net.rossonet.operator.LogUtils;
import net.rossonet.operator.model.StaticUtils;

@ControllerConfiguration(dependents = { @Dependent(type = RepositoryResource.class),
		@Dependent(type = ServiceRepositoryResource.class), @Dependent(type = IngressRepositoryResource.class) })
public class KettleRepositoryReconciler implements Reconciler<KettleRepository> {
	private static final Logger logger = Logger.getLogger(KettleRepositoryReconciler.class.getName());

	public static final String SELECTOR = "app.kubernetes.io/managed-by=kettle-operator";

	@SuppressWarnings("unused")
	private final KubernetesClient kubernetesClient;

	public KettleRepositoryReconciler() {
		this(new DefaultKubernetesClient());
	}

	public KettleRepositoryReconciler(final KubernetesClient kubernetesClient) {
		this.kubernetesClient = kubernetesClient;
	}

	@Override
	public UpdateControl<KettleRepository> reconcile(final KettleRepository resource,
			final Context<KettleRepository> context) {
		try {
			logger.info("reconciler  " + resource + " -> " + context);
			final String name = context.getSecondaryResource(Deployment.class).get().getMetadata().getName();
			resource.setStatus(StaticUtils.createKettleRepositoryStatus(name));
			return UpdateControl.patchStatus(resource);
		} catch (final Exception ee) {
			logger.severe(LogUtils.stackTraceToString(ee));
			throw ee;
		}
	}

}
