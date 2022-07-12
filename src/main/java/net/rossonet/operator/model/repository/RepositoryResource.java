package net.rossonet.operator.model.repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.Container;
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
public class RepositoryResource extends CRUKubernetesDependentResource<Deployment, KettleRepository> {
	private static final Logger logger = Logger.getLogger(RepositoryResource.class.getName());

	public RepositoryResource() {
		super(Deployment.class);
		logger.info("Deployment class created");
	}

	@Override
	protected Deployment desired(final KettleRepository kettleRepository, final Context<KettleRepository> context) {
		final Deployment deployment = new Deployment();
		try {
			logger.info("kettle repository " + kettleRepository);
			deployment.setMetadata(new ObjectMeta());
			deployment.getMetadata().setName(kettleRepository.getMetadata().getName());
			deployment.getMetadata().setNamespace(kettleRepository.getMetadata().getNamespace());
			final Map<String, String> labels = new HashMap<>();
			labels.put(StaticUtils.LABEL, StaticUtils.LABEL_DATA);
			deployment.getMetadata().setLabels(labels);
			final PodSpec podSpec = new PodSpec();
			final Container container = new Container();
			container.setName(kettleRepository.getMetadata().getName());
			container.setImage(kettleRepository.getSpec().getImage());
			podSpec.setContainers(Arrays.asList(new Container[] { container }));
			podSpec.setRestartPolicy("Always");
			final DeploymentSpec spec = new DeploymentSpec();
			final PodTemplateSpec template = new PodTemplateSpec();
			spec.setTemplate(template);
			deployment.setSpec(spec);
			deployment.getSpec().getTemplate().setSpec(podSpec);
			logger.info("actual deployment " + deployment);
			// logger.info(LogUtils.threadStackTrace());
		} catch (final Exception e) {
			logger.severe(LogUtils.stackTraceToString(e));
		}
		return deployment;
	}

}
