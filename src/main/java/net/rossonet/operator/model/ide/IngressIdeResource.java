package net.rossonet.operator.model.ide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.networking.v1.HTTPIngressPath;
import io.fabric8.kubernetes.api.model.networking.v1.HTTPIngressRuleValue;
import io.fabric8.kubernetes.api.model.networking.v1.Ingress;
import io.fabric8.kubernetes.api.model.networking.v1.IngressBackend;
import io.fabric8.kubernetes.api.model.networking.v1.IngressRule;
import io.fabric8.kubernetes.api.model.networking.v1.IngressServiceBackend;
import io.fabric8.kubernetes.api.model.networking.v1.IngressSpec;
import io.fabric8.kubernetes.api.model.networking.v1.ServiceBackendPort;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import net.rossonet.operator.model.LogUtils;
import net.rossonet.operator.model.StaticUtils;

@KubernetesDependent(labelSelector = StaticUtils.SELECTOR)
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
			final Map<String, String> labels = new HashMap<>();
			labels.put(StaticUtils.LABEL_MANAGED_BY, StaticUtils.DATA_MANAGED_BY);
			labels.put(StaticUtils.LABEL_APP, kettleIde.getMetadata().getName());
			labels.put(StaticUtils.LABEL_PART_OF, kettleIde.getMetadata().getName());
			ingress.getMetadata().setLabels(labels);
			final IngressSpec spec = new IngressSpec();
			final IngressBackend backend = new IngressBackend();
			final IngressServiceBackend service = new IngressServiceBackend();
			service.setName(kettleIde.getMetadata().getName());
			final ServiceBackendPort servicePort = new ServiceBackendPort();
			servicePort.setNumber(kettleIde.getSpec().getServicePort());
			service.setPort(servicePort);
			backend.setService(service);
			spec.setDefaultBackend(backend);
			ingress.setSpec(spec);
			final List<IngressRule> rules = new ArrayList<>();
			final IngressRule ruleIde = new IngressRule();
			if (kettleIde.getSpec().getHost() != null) {
				ruleIde.setHost(kettleIde.getSpec().getHost());
			}
			final HTTPIngressRuleValue httpRule = new HTTPIngressRuleValue();
			final List<HTTPIngressPath> paths = new ArrayList<>();
			final HTTPIngressPath path = new HTTPIngressPath();
			path.setPath(kettleIde.getSpec().getPath());
			path.setPathType("Prefix");
			path.setBackend(backend);
			paths.add(path);
			httpRule.setPaths(paths);
			ruleIde.setHttp(httpRule);
			rules.add(ruleIde);
			spec.setRules(rules);
			logger.info("actual ingress " + ingress);
		} catch (final Exception e) {
			logger.severe(LogUtils.stackTraceToString(e));
		}
		return ingress;
	}

}
