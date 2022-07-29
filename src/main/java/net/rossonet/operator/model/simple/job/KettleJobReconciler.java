package net.rossonet.operator.model.simple.job;

import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.batch.v1.Job;
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
import net.rossonet.operator.model.simple.transformation.RepositoriesConfigMapResource;

@ControllerConfiguration(dependents = { @Dependent(type = SimpleJobResource.class),
		@Dependent(type = RepositoriesConfigMapResource.class) })
public class KettleJobReconciler implements Reconciler<KettleJob> {
	private static final Logger logger = Logger.getLogger(KettleJobReconciler.class.getName());

	@SuppressWarnings("unused")
	private final KubernetesClient kubernetesClient;

	public KettleJobReconciler() {
		this(new DefaultKubernetesClient(new ConfigBuilder().withNamespace(null).build()));
	}

	public KettleJobReconciler(final KubernetesClient kubernetesClient) {
		this.kubernetesClient = kubernetesClient;
	}

	@Override
	public UpdateControl<KettleJob> reconcile(final KettleJob resource, final Context<KettleJob> context) {
		try {
			logger.info("reconciler  " + resource + " -> " + context);
			final ConfigMap configMap = context.getSecondaryResource(ConfigMap.class).get();
			logger.fine("repository.xml in config map  " + configMap.getData().get(StaticUtils.REPOSITORIES));
			final Job job = context.getSecondaryResource(Job.class).get();
			resource.setStatus(StaticUtils.createKettleJobStatus(resource.getStatus(), job));
			return UpdateControl.patchStatus(resource);
		} catch (final Exception ee) {
			logger.severe(LogUtils.stackTraceToString(ee));
			throw ee;
		}
	}

}
