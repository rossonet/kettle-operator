package net.rossonet.operator.model.ide;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.LabelSelector;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.PodSpec;
import io.fabric8.kubernetes.api.model.PodTemplateSpec;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentSpec;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import net.rossonet.operator.model.LogUtils;
import net.rossonet.operator.model.StaticUtils;

@KubernetesDependent(labelSelector = StaticUtils.SELECTOR)
public class IdeResource extends CRUKubernetesDependentResource<Deployment, KettleIde> {
	private static final Logger logger = Logger.getLogger(IdeResource.class.getName());

	public IdeResource() {
		super(Deployment.class);
		logger.info("Deployment class created");
	}

	@Override
	protected Deployment desired(final KettleIde kettleRepository, final Context<KettleIde> context) {
		final Deployment deployment = new Deployment();
		try {
			logger.info("kettle repository " + kettleRepository);
			deployment.setMetadata(new ObjectMeta());
			deployment.getMetadata().setName(kettleRepository.getMetadata().getName());
			deployment.getMetadata().setNamespace(kettleRepository.getMetadata().getNamespace());
			final Map<String, String> labels = new HashMap<>();
			labels.put(StaticUtils.LABEL_MANAGED_BY, StaticUtils.DATA_MANAGED_BY);
			labels.put(StaticUtils.LABEL_APP, kettleRepository.getMetadata().getName());
			labels.put(StaticUtils.LABEL_PART_OF, kettleRepository.getMetadata().getName());
			deployment.getMetadata().setLabels(labels);
			final PodSpec podSpec = new PodSpec();
			final Container container = new Container();
			container.setName(kettleRepository.getMetadata().getName());
			container.setImage(kettleRepository.getSpec().getImage());
			podSpec.setContainers(Arrays.asList(new Container[] { container }));
			podSpec.setRestartPolicy("Always");
			final DeploymentSpec spec = new DeploymentSpec();
			final LabelSelector selector = new LabelSelector();
			selector.setMatchLabels(labels);
			spec.setSelector(selector);
			final PodTemplateSpec template = new PodTemplateSpec();
			template.setMetadata(new ObjectMeta());
			template.getMetadata().setLabels(labels);
			spec.setTemplate(template);
			deployment.setSpec(spec);
			deployment.getSpec().getTemplate().setSpec(podSpec);
			logger.info("actual deployment " + deployment);
		} catch (final Exception e) {
			logger.severe(LogUtils.stackTraceToString(e));
		}
		return deployment;
	}

}
