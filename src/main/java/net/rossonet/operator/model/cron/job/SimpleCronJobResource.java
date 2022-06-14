package net.rossonet.operator.model.cron.job;

import java.util.Arrays;
import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.PodSpec;
import io.fabric8.kubernetes.api.model.batch.v1.CronJob;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import net.rossonet.operator.model.StaticUtils;

@KubernetesDependent(labelSelector = CronKettleJobReconciler.SELECTOR)
public class SimpleCronJobResource extends CRUKubernetesDependentResource<CronJob, CronKettleJob> {
	private static final Logger logger = Logger.getLogger(SimpleCronJobResource.class.getName());

	public SimpleCronJobResource() {
		super(CronJob.class);
		logger.info("Job created");
	}

	@Override
	protected CronJob desired(final CronKettleJob kettleJob, final Context<CronKettleJob> context) {
		final CronJob job = new CronJob();
		job.getMetadata().setName(kettleJob.getMetadata().getName());
		job.getMetadata().setNamespace(kettleJob.getMetadata().getNamespace());
		final PodSpec jobDetails = new PodSpec();
		final Container container = new Container();
		container.setImage(kettleJob.getSpec().getImage());
		container.setCommand(StaticUtils.createCronJobCommand(kettleJob));
		jobDetails.setContainers(Arrays.asList(new Container[] { container }));
		job.getSpec().getJobTemplate().getSpec().getTemplate().setSpec(jobDetails);
		job.getSpec().setSchedule(kettleJob.getSpec().getSchedule());
		logger.info("desired cronjob " + job);
		return job;
	}

}
