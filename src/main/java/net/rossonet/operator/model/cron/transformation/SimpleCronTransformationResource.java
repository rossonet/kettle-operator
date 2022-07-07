package net.rossonet.operator.model.cron.transformation;

import java.util.Arrays;
import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.PodSpec;
import io.fabric8.kubernetes.api.model.batch.v1.CronJob;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUKubernetesDependentResource;
import net.rossonet.operator.model.StaticUtils;

public class SimpleCronTransformationResource
		extends CRUKubernetesDependentResource<CronJob, CronKettleTransformation> {
	private static final Logger logger = Logger.getLogger(SimpleCronTransformationResource.class.getName());

	public SimpleCronTransformationResource() {
		super(CronJob.class);
		logger.info("CronJob created");
	}

	@Override
	protected CronJob desired(final CronKettleTransformation kettleTransformation,
			final Context<CronKettleTransformation> context) {
		final CronJob job = new CronJob();
		logger.info("kettle transformation " + kettleTransformation);
		logger.info("actual cronjob " + job);
		job.getMetadata().setName(kettleTransformation.getMetadata().getName());
		job.getMetadata().setNamespace(kettleTransformation.getMetadata().getNamespace());
		final PodSpec jobDetails = new PodSpec();
		final Container container = new Container();
		container.setImage(kettleTransformation.getSpec().getImage());
		container.setCommand(StaticUtils.createCronTransformationCommand(kettleTransformation));
		jobDetails.setContainers(Arrays.asList(new Container[] { container }));
		job.getSpec().getJobTemplate().getSpec().getTemplate().setSpec(jobDetails);
		job.getSpec().setSchedule(kettleTransformation.getSpec().getSchedule());
		return job;
	}

}
