package net.rossonet.operator.model.cron.transformation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.PodSpec;
import io.fabric8.kubernetes.api.model.batch.v1.CronJob;
import io.fabric8.kubernetes.api.model.batch.v1.CronJobSpec;
import io.fabric8.kubernetes.api.model.batch.v1.JobTemplateSpec;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import net.rossonet.operator.model.LogUtils;
import net.rossonet.operator.model.StaticUtils;

@KubernetesDependent(labelSelector = StaticUtils.SELECTOR)
public class SimpleCronTransformationResource
		extends CRUKubernetesDependentResource<CronJob, CronKettleTransformation> {
	private static final Logger logger = Logger.getLogger(SimpleCronTransformationResource.class.getName());

	public SimpleCronTransformationResource() {
		super(CronJob.class);
		logger.info("CronJob class created");
	}

	@Override
	protected CronJob desired(final CronKettleTransformation kettleTransformation,
			final Context<CronKettleTransformation> context) {
		final CronJob job = new CronJob();
		try {
			logger.info("kettle transformation " + kettleTransformation);
			job.setMetadata(new ObjectMeta());
			job.getMetadata().setName(kettleTransformation.getMetadata().getName());
			job.getMetadata().setNamespace(kettleTransformation.getMetadata().getNamespace());
			final Map<String, String> labels = new HashMap<>();
			labels.put(StaticUtils.LABEL_MANAGED_BY, StaticUtils.DATA_MANAGED_BY);
			job.getMetadata().setLabels(labels);
			final PodSpec jobDetails = new PodSpec();
			final Container container = new Container();
			container.setName(kettleTransformation.getMetadata().getName());
			container.setImage(kettleTransformation.getSpec().getImage());
			container.setCommand(StaticUtils.createCronTransformationCommand(kettleTransformation));
			jobDetails.setContainers(Arrays.asList(new Container[] { container }));
			jobDetails.setRestartPolicy("Never");
			final CronJobSpec spec = new CronJobSpec();
			final JobTemplateSpec template = new JobTemplateSpec();
			template.setMetadata(new ObjectMeta());
			template.getMetadata().setLabels(labels);
			spec.setJobTemplate(template);
			job.setSpec(spec);
			job.getSpec().getJobTemplate().getSpec().getTemplate().setSpec(jobDetails);
			job.getSpec().setSchedule(kettleTransformation.getSpec().getSchedule());
			logger.info("actual cronjob " + job);
		} catch (final Exception e) {
			logger.severe(LogUtils.stackTraceToString(e));
		}
		return job;
	}

}
