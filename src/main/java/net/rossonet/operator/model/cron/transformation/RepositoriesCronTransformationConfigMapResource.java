package net.rossonet.operator.model.cron.transformation;

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
public class RepositoriesCronTransformationConfigMapResource extends CRUKubernetesDependentResource<ConfigMap, CronKettleTransformation> {

	private static final Logger logger = Logger.getLogger(RepositoriesCronTransformationConfigMapResource.class.getName());

	public RepositoriesCronTransformationConfigMapResource() {
		super(ConfigMap.class);
		logger.info("ConfigMap class created");
	}

	@Override
	protected ConfigMap desired(final CronKettleTransformation kettleCronTransformation,
			final Context<CronKettleTransformation> context) {
		final ConfigMap configMap = new ConfigMap();
		try {
			logger.fine("kettle cron transformation " + kettleCronTransformation);
			configMap.setMetadata(new ObjectMeta());
			configMap.getMetadata().setName(kettleCronTransformation.getMetadata().getName());
			configMap.getMetadata().setNamespace(kettleCronTransformation.getMetadata().getNamespace());
			final Map<String, String> labels = new HashMap<>();
			labels.put(StaticUtils.LABEL_MANAGED_BY, StaticUtils.DATA_MANAGED_BY);
			labels.put(StaticUtils.LABEL_APP, kettleCronTransformation.getMetadata().getName());
			labels.put(StaticUtils.LABEL_PART_OF, kettleCronTransformation.getMetadata().getName());
			configMap.getMetadata().setLabels(labels);
			String xmlRepositories = "";
			if (kettleCronTransformation.getSpec().getXmlRepository() != null
					&& !kettleCronTransformation.getSpec().getXmlRepository().isEmpty()) {
				xmlRepositories = kettleCronTransformation.getSpec().getXmlRepository();
			} else {
				xmlRepositories = StaticUtils.repositoriesManagement(KettleOperator.getUsedClient(),
						kettleCronTransformation);
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
