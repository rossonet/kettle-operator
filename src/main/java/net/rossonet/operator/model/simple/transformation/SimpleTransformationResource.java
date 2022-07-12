package net.rossonet.operator.model.simple.transformation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.PodSpec;
import io.fabric8.kubernetes.api.model.PodTemplateSpec;
import io.fabric8.kubernetes.api.model.batch.v1.Job;
import io.fabric8.kubernetes.api.model.batch.v1.JobSpec;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import net.rossonet.operator.LogUtils;
import net.rossonet.operator.model.StaticUtils;

@KubernetesDependent(labelSelector = StaticUtils.SELECTOR)
public class SimpleTransformationResource extends CRUKubernetesDependentResource<Job, KettleTransformation> {
	private static final Logger logger = Logger.getLogger(SimpleTransformationResource.class.getName());

	public SimpleTransformationResource() {
		super(Job.class);
		logger.info("Job class created");
	}

	@Override
	protected Job desired(final KettleTransformation kettleTransformation,
			final Context<KettleTransformation> context) {
		final Job job = new Job();
		try {
			logger.info("kettle transformation " + kettleTransformation);
			job.setMetadata(new ObjectMeta());
			job.getMetadata().setName(kettleTransformation.getMetadata().getName());
			job.getMetadata().setNamespace(kettleTransformation.getMetadata().getNamespace());
			final Map<String, String> labels = new HashMap<>();
			labels.put(StaticUtils.LABEL, "true");
			job.getMetadata().setLabels(labels);
			final PodSpec jobDetails = new PodSpec();
			final Container container = new Container();
			container.setName(kettleTransformation.getMetadata().getName());
			container.setImage(kettleTransformation.getSpec().getImage());
			container.setCommand(StaticUtils.createTransformationCommand(kettleTransformation));
			jobDetails.setContainers(Arrays.asList(new Container[] { container }));
			final JobSpec spec = new JobSpec();
			final PodTemplateSpec template = new PodTemplateSpec();
			spec.setTemplate(template);
			job.setSpec(spec);
			job.getSpec().getTemplate().setSpec(jobDetails);
			logger.info("actual job " + job);
			logger.info(LogUtils.threadStackTrace());
		} catch (final Exception e) {
			logger.severe(LogUtils.stackTraceToString(e));
		}
		return job;
	}

}
