package net.rossonet.operator.model.repository;

import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.networking.v1.Ingress;
import io.fabric8.kubernetes.api.model.networking.v1.IngressBackend;
import io.fabric8.kubernetes.api.model.networking.v1.IngressServiceBackend;
import io.fabric8.kubernetes.api.model.networking.v1.IngressSpec;
import io.fabric8.kubernetes.api.model.networking.v1.ServiceBackendPort;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import net.rossonet.operator.model.LogUtils;
import net.rossonet.operator.model.StaticUtils;

@KubernetesDependent(labelSelector = StaticUtils.SELECTOR)
public class IngressRepositoryResource extends CRUKubernetesDependentResource<Ingress, KettleRepository> {
	private static final Logger logger = Logger.getLogger(IngressRepositoryResource.class.getName());

	public IngressRepositoryResource() {
		super(Ingress.class);
		logger.info("Ingress class created");
	}

	@Override
	protected Ingress desired(final KettleRepository kettleRepository, final Context<KettleRepository> context) {
		final Ingress ingress = new Ingress();
		try {
			logger.info("kettle repository " + kettleRepository);
			ingress.setMetadata(new ObjectMeta());
			ingress.getMetadata().setName(kettleRepository.getMetadata().getName());
			ingress.getMetadata().setNamespace(kettleRepository.getMetadata().getNamespace());
			final IngressSpec spec = new IngressSpec();
			final IngressBackend backend = new IngressBackend();
			final IngressServiceBackend service = new IngressServiceBackend();
			service.setName("test");
			final ServiceBackendPort servicePort = new ServiceBackendPort();
			servicePort.setNumber(80);
			service.setPort(servicePort);
			backend.setService(service);
			spec.setDefaultBackend(backend);
			ingress.setSpec(spec);
			logger.info("actual ingress " + ingress);
		} catch (final Exception e) {
			logger.severe(LogUtils.stackTraceToString(e));
		}
		return ingress;
	}

}
