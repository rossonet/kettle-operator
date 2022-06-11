package net.rossonet.operator.model.simple.transformation;

import java.util.Arrays;

import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.PodSpec;
import io.fabric8.kubernetes.api.model.batch.v1.Job;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import net.rossonet.operator.model.StaticUtils;

@KubernetesDependent(labelSelector = KettleTransformationReconciler.SELECTOR)
public class SimpleTransformationResource extends CRUKubernetesDependentResource<Job, KettleTransformation> {

	public SimpleTransformationResource(final Class<Job> resource) {
		super(resource);
	}

	@Override
	protected Job desired(final KettleTransformation kettleTransformation,
			final Context<KettleTransformation> context) {
		final Job job = new Job();
		job.getMetadata().setName(kettleTransformation.getMetadata().getName());
		job.getMetadata().setNamespace(kettleTransformation.getMetadata().getNamespace());
		final PodSpec jobDetails = new PodSpec();
		final Container container = new Container();
		container.setImage(kettleTransformation.getSpec().getImage());
		container.setCommand(StaticUtils.createTransformationCommand(kettleTransformation));
		jobDetails.setContainers(Arrays.asList(new Container[] { container }));
		job.getSpec().getTemplate().setSpec(jobDetails);
		return job;
	}

}
