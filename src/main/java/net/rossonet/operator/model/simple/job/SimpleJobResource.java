package net.rossonet.operator.model.simple.job;

import java.util.Arrays;

import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.PodSpec;
import io.fabric8.kubernetes.api.model.batch.v1.Job;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import net.rossonet.operator.model.StaticUtils;

@KubernetesDependent(labelSelector = KettleJobReconciler.SELECTOR)
public class SimpleJobResource extends CRUKubernetesDependentResource<Job, KettleJob> {

	public SimpleJobResource() {
		super(Job.class);
	}

	@Override
	protected Job desired(final KettleJob kettleJob, final Context<KettleJob> context) {
		final Job job = new Job();
		job.getMetadata().setName(kettleJob.getMetadata().getName());
		job.getMetadata().setNamespace(kettleJob.getMetadata().getNamespace());
		final PodSpec jobDetails = new PodSpec();
		final Container container = new Container();
		container.setImage(kettleJob.getSpec().getImage());
		container.setCommand(StaticUtils.createJobCommand(kettleJob));
		jobDetails.setContainers(Arrays.asList(new Container[] { container }));
		job.getSpec().getTemplate().setSpec(jobDetails);
		return job;
	}

}
