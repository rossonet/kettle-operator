package net.rossonet.operator.model.simple.job;

import java.util.Arrays;
import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.PodSpec;
import io.fabric8.kubernetes.api.model.batch.v1.Job;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUKubernetesDependentResource;
import net.rossonet.operator.LogUtils;
import net.rossonet.operator.model.StaticUtils;

public class SimpleJobResource extends CRUKubernetesDependentResource<Job, KettleJob> {
	private static final Logger logger = Logger.getLogger(SimpleJobResource.class.getName());

	public SimpleJobResource() {
		super(Job.class);
		logger.info("Job class created");
	}

	@Override
	protected Job desired(final KettleJob kettleJob, final Context<KettleJob> context) {
		final Job job = new Job();
		try {
			logger.info("kettle job " + kettleJob);
			job.getMetadata().setName(kettleJob.getMetadata().getName());
			job.getMetadata().setNamespace(kettleJob.getMetadata().getNamespace());
			final PodSpec jobDetails = new PodSpec();
			final Container container = new Container();
			container.setImage(kettleJob.getSpec().getImage());
			container.setCommand(StaticUtils.createJobCommand(kettleJob));
			jobDetails.setContainers(Arrays.asList(new Container[] { container }));
			job.getSpec().getTemplate().setSpec(jobDetails);
			logger.info("actual job " + job);
		} catch (final Exception e) {
			logger.severe(LogUtils.stackTraceToString(e));
		}
		return job;
	}

}
