package net.rossonet.operator.model.ide;

import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceSpec;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import net.rossonet.operator.LogUtils;

@KubernetesDependent(labelSelector = KettleIdeReconciler.SELECTOR)
public class ServiceIdeResource extends CRUKubernetesDependentResource<Service, KettleIde> {
	private static final Logger logger = Logger.getLogger(ServiceIdeResource.class.getName());

	public ServiceIdeResource() {
		super(Service.class);
		logger.info("Service class created");
	}

	@Override
	protected Service desired(final KettleIde kettleIde, final Context<KettleIde> context) {
		final Service service = new Service();
		try {
			logger.info("kettle ide " + kettleIde);
			service.setMetadata(new ObjectMeta());
			service.getMetadata().setName(kettleIde.getMetadata().getName());
			service.getMetadata().setNamespace(kettleIde.getMetadata().getNamespace());
			final ServiceSpec spec = new ServiceSpec();
			service.setSpec(spec);
			logger.info("actual service " + service);
		} catch (final Exception e) {
			logger.severe(LogUtils.stackTraceToString(e));
		}
		return service;
	}

}
