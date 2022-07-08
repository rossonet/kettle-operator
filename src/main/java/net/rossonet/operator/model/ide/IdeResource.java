package net.rossonet.operator.model.ide;

import java.util.Arrays;
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
import net.rossonet.operator.LogUtils;

@KubernetesDependent(labelSelector = KettleIdeReconciler.SELECTOR)
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
			final PodSpec jobDetails = new PodSpec();
			final Container container = new Container();
			container.setImage(kettleRepository.getSpec().getImage());
			jobDetails.setContainers(Arrays.asList(new Container[] { container }));
			final DeploymentSpec spec = new DeploymentSpec();
			final PodTemplateSpec template = new PodTemplateSpec();
			spec.setTemplate(template);
			deployment.setSpec(spec);
			deployment.getSpec().getTemplate().setSpec(jobDetails);
			logger.info("actual deployment " + deployment);
		} catch (final Exception e) {
			logger.severe(LogUtils.stackTraceToString(e));
		}
		return deployment;
	}

}
