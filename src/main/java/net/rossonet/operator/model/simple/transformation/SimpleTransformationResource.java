package net.rossonet.operator.model.simple.transformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.ConfigMapVolumeSource;
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.KeyToPath;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.PodSpec;
import io.fabric8.kubernetes.api.model.PodTemplateSpec;
import io.fabric8.kubernetes.api.model.Volume;
import io.fabric8.kubernetes.api.model.VolumeMount;
import io.fabric8.kubernetes.api.model.batch.v1.Job;
import io.fabric8.kubernetes.api.model.batch.v1.JobSpec;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import net.rossonet.operator.model.LogUtils;
import net.rossonet.operator.model.StaticUtils;

@KubernetesDependent(labelSelector = StaticUtils.SELECTOR)
public class SimpleTransformationResource extends CRUKubernetesDependentResource<Job, KettleTransformation> {
	private static final Logger logger = Logger.getLogger(SimpleTransformationResource.class.getName());
	private static final String REPOSITORIES_VOLUME_NAME = "repositories-config";

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
			final String transformationName = kettleTransformation.getMetadata().getName();
			job.getMetadata().setName(transformationName);
			job.getMetadata().setNamespace(kettleTransformation.getMetadata().getNamespace());
			final Map<String, String> labels = new HashMap<>();
			labels.put(StaticUtils.LABEL_MANAGED_BY, StaticUtils.DATA_MANAGED_BY);
			labels.put(StaticUtils.LABEL_APP, transformationName);
			labels.put(StaticUtils.LABEL_PART_OF, transformationName);
			job.getMetadata().setLabels(labels);
			final PodSpec podSpec = new PodSpec();
			final Container container = new Container();
			container.setName(transformationName);
			container.setImage(kettleTransformation.getSpec().getImage());
			container.setCommand(StaticUtils.createTransformationCommand(kettleTransformation));
			final List<VolumeMount> volumesList = new ArrayList<>();
			final List<Volume> volumes = new ArrayList<>();
			final Volume volume = new Volume();
			volume.setName(REPOSITORIES_VOLUME_NAME);
			final ConfigMapVolumeSource configMapSource = new ConfigMapVolumeSource();
			final List<KeyToPath> items = new ArrayList<>();
			final KeyToPath item = new KeyToPath();
			item.setKey(StaticUtils.REPOSITORIES);
			item.setPath("repositories.xml");
			items.add(item);
			configMapSource.setItems(items);
			configMapSource.setName(transformationName);
			volume.setConfigMap(configMapSource);
			volumes.add(volume);
			podSpec.setVolumes(volumes);
			final VolumeMount volumeRepositories = new VolumeMount();
			volumeRepositories.setMountPath("/root/.kettle");
			volumeRepositories.setReadOnly(true);
			volumeRepositories.setName(REPOSITORIES_VOLUME_NAME);
			volumesList.add(volumeRepositories);
			container.setVolumeMounts(volumesList);
			podSpec.setContainers(Arrays.asList(new Container[] { container }));
			podSpec.setRestartPolicy("Never");
			final JobSpec spec = new JobSpec();
			final PodTemplateSpec template = new PodTemplateSpec();
			template.setMetadata(new ObjectMeta());
			template.getMetadata().setLabels(labels);
			spec.setTemplate(template);
			job.setSpec(spec);
			job.getSpec().getTemplate().setSpec(podSpec);
			logger.info("actual job " + job);
		} catch (final Exception e) {
			logger.severe(LogUtils.stackTraceToString(e));
		}
		return job;
	}

}
