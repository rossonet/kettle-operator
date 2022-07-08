package net.rossonet.operator.model.ide;

import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.networking.v1.Ingress;
import io.fabric8.kubernetes.api.model.networking.v1.IngressSpec;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import net.rossonet.operator.LogUtils;

@KubernetesDependent(labelSelector = KettleIdeReconciler.SELECTOR)
public class IngressIdeResource extends CRUKubernetesDependentResource<Ingress, KettleIde> {
	private static final Logger logger = Logger.getLogger(IngressIdeResource.class.getName());

	public IngressIdeResource() {
		super(Ingress.class);
		logger.info("Ingress class created");
	}

	@Override
	protected Ingress desired(final KettleIde kettleIde, final Context<KettleIde> context) {
		final Ingress ingress = new Ingress();
		try {
			logger.info("kettle ide " + kettleIde);
			ingress.setMetadata(new ObjectMeta());
			ingress.getMetadata().setName(kettleIde.getMetadata().getName());
			ingress.getMetadata().setNamespace(kettleIde.getMetadata().getNamespace());
			final IngressSpec spec = new IngressSpec();
			ingress.setSpec(spec);
			logger.info("actual ingress " + ingress);
		} catch (final Exception e) {
			logger.severe(LogUtils.stackTraceToString(e));
		}
		return ingress;
	}

}
