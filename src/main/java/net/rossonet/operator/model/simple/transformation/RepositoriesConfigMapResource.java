package net.rossonet.operator.model.simple.transformation;

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
public class RepositoriesConfigMapResource extends CRUKubernetesDependentResource<ConfigMap, KettleTransformation> {

	private static final Logger logger = Logger.getLogger(RepositoriesConfigMapResource.class.getName());

	public RepositoriesConfigMapResource() {
		super(ConfigMap.class);
		logger.info("ConfigMap class created");
	}

	@Override
	protected ConfigMap desired(final KettleTransformation kettleTransformation,
			final Context<KettleTransformation> context) {
		final ConfigMap configMap = new ConfigMap();
		try {
			logger.fine("kettle transformation " + kettleTransformation);
			configMap.setMetadata(new ObjectMeta());
			configMap.getMetadata().setName(kettleTransformation.getMetadata().getName());
			configMap.getMetadata().setNamespace(kettleTransformation.getMetadata().getNamespace());
			final Map<String, String> labels = new HashMap<>();
			labels.put(StaticUtils.LABEL_MANAGED_BY, StaticUtils.DATA_MANAGED_BY);
			labels.put(StaticUtils.LABEL_APP, kettleTransformation.getMetadata().getName());
			labels.put(StaticUtils.LABEL_PART_OF, kettleTransformation.getMetadata().getName());
			configMap.getMetadata().setLabels(labels);
			String xmlRepositories = "";
			if (kettleTransformation.getSpec().getXmlRepository() != null
					&& !kettleTransformation.getSpec().getXmlRepository().isEmpty()) {
				xmlRepositories = kettleTransformation.getSpec().getXmlRepository();
			} else {
				xmlRepositories = StaticUtils.repositoriesManagement(KettleOperator.getUsedClient(),
						kettleTransformation);
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
