package net.rossonet.operator.model.simple.job;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import net.rossonet.operator.KettleOperator;
import net.rossonet.operator.model.LogUtils;
import net.rossonet.operator.model.StaticUtils;

@KubernetesDependent(labelSelector = StaticUtils.SELECTOR)
public class RepositoriesJobConfigMapResource extends CRUKubernetesDependentResource<ConfigMap, KettleJob> {

	private static final Logger logger = Logger.getLogger(RepositoriesJobConfigMapResource.class.getName());

	public RepositoriesJobConfigMapResource() {
		super(ConfigMap.class);
		logger.info("ConfigMap class created");
	}

	@Override
	protected ConfigMap desired(final KettleJob kettleJob, final Context<KettleJob> context) {
		final ConfigMap configMap = new ConfigMap();
		try {
			logger.fine("kettle job " + kettleJob);
			configMap.setMetadata(new ObjectMeta());
			configMap.getMetadata().setName(kettleJob.getMetadata().getName());
			configMap.getMetadata().setNamespace(kettleJob.getMetadata().getNamespace());
			final Map<String, String> labels = new HashMap<>();
			labels.put(StaticUtils.LABEL_MANAGED_BY, StaticUtils.DATA_MANAGED_BY);
			labels.put(StaticUtils.LABEL_APP, kettleJob.getMetadata().getName());
			labels.put(StaticUtils.LABEL_PART_OF, kettleJob.getMetadata().getName());
			configMap.getMetadata().setLabels(labels);
			String xmlRepositories = "";
			if (kettleJob.getSpec().getXmlRepository() != null && !kettleJob.getSpec().getXmlRepository().isEmpty()) {
				xmlRepositories = kettleJob.getSpec().getXmlRepository();
			} else {
				xmlRepositories = StaticUtils.repositoriesManagement(KettleOperator.getUsedClient(), kettleJob);
			}
			final Map<String, String> dataMap = new HashMap<>();
			dataMap.put(StaticUtils.REPOSITORIES, xmlRepositories);
			configMap.setData(dataMap);
			logger.fine("actual config map " + configMap);
		} catch (final Exception e) {
			logger.severe(LogUtils.stackTraceToString(e));
		}
		return configMap;
	}

}
