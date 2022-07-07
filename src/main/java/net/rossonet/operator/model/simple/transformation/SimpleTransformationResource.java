package net.rossonet.operator.model.simple.transformation;

import java.util.Arrays;
import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.PodSpec;
import io.fabric8.kubernetes.api.model.batch.v1.Job;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUKubernetesDependentResource;
import net.rossonet.operator.model.StaticUtils;

public class SimpleTransformationResource extends CRUKubernetesDependentResource<Job, KettleTransformation> {
	private static final Logger logger = Logger.getLogger(SimpleTransformationResource.class.getName());

	public SimpleTransformationResource() {
		super(Job.class);
		logger.info("Job created");
	}

	@Override
	protected Job desired(final KettleTransformation kettleTransformation,
			final Context<KettleTransformation> context) {
		final Job job = new Job();
		logger.info("kettle transformation " + kettleTransformation);
		logger.info("actual job " + job);
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
