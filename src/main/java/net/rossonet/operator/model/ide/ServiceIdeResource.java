package net.rossonet.operator.model.ide;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServicePort;
import io.fabric8.kubernetes.api.model.ServiceSpec;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import net.rossonet.operator.model.LogUtils;
import net.rossonet.operator.model.StaticUtils;

@KubernetesDependent(labelSelector = StaticUtils.SELECTOR)
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
			final Map<String, String> labels = new HashMap<>();
			labels.put(StaticUtils.LABEL, StaticUtils.LABEL_DATA);
			labels.put("app", kettleIde.getMetadata().getName());
			labels.put("app.kubernetes.io/part-of", kettleIde.getMetadata().getName());
			service.getMetadata().setLabels(labels);
			final ServiceSpec spec = new ServiceSpec();
			final ServicePort port = new ServicePort();
			port.setPort(80);
			spec.setPorts(Arrays.asList(new ServicePort[] { port }));
			service.setSpec(spec);
			logger.info("actual service " + service);
		} catch (final Exception e) {
			logger.severe(LogUtils.stackTraceToString(e));
		}
		return service;
	}

}
