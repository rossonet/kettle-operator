package net.rossonet.operator.model.cron.job;

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
public class SimpleCronJobResource extends CRUKubernetesDependentResource<CronJob, CronKettleJob> {
	private static final Logger logger = Logger.getLogger(SimpleCronJobResource.class.getName());

	public SimpleCronJobResource() {
		super(CronJob.class);
		logger.info("CronJob class created");
	}

	@Override
	protected CronJob desired(final CronKettleJob kettleJob, final Context<CronKettleJob> context) {
		final CronJob job = new CronJob();
		try {
			logger.info("kettle job " + kettleJob);
			job.setMetadata(new ObjectMeta());
			job.getMetadata().setName(kettleJob.getMetadata().getName());
			job.getMetadata().setNamespace(kettleJob.getMetadata().getNamespace());
			final Map<String, String> labels = new HashMap<>();
			labels.put(StaticUtils.LABEL, StaticUtils.LABEL_DATA);
			job.getMetadata().setLabels(labels);
			final PodSpec jobDetails = new PodSpec();
			final Container container = new Container();
			container.setName(kettleJob.getMetadata().getName());
			container.setImage(kettleJob.getSpec().getImage());
			container.setCommand(StaticUtils.createCronJobCommand(kettleJob));
			jobDetails.setContainers(Arrays.asList(new Container[] { container }));
			jobDetails.setRestartPolicy("Never");
			final CronJobSpec spec = new CronJobSpec();
			final JobTemplateSpec template = new JobTemplateSpec();
			spec.setJobTemplate(template);
			job.setSpec(spec);
			job.getSpec().getJobTemplate().getSpec().getTemplate().setSpec(jobDetails);
			job.getSpec().setSchedule(kettleJob.getSpec().getSchedule());
			logger.info("actual cronjob " + job);
		} catch (final Exception e) {
			logger.severe(LogUtils.stackTraceToString(e));
		}
		return job;
	}

}
