package net.rossonet.operator.model.cron.job;

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
public class RepositoriesConfigMapResource extends CRUKubernetesDependentResource<ConfigMap, CronKettleJob> {

	private static final Logger logger = Logger.getLogger(RepositoriesConfigMapResource.class.getName());

	public RepositoriesConfigMapResource() {
		super(ConfigMap.class);
		logger.info("ConfigMap class created");
	}

	@Override
	protected ConfigMap desired(final CronKettleJob kettleCronJob, final Context<CronKettleJob> context) {
		final ConfigMap configMap = new ConfigMap();
		try {
			logger.fine("kettle cron job " + kettleCronJob);
			configMap.setMetadata(new ObjectMeta());
			configMap.getMetadata().setName(kettleCronJob.getMetadata().getName());
			configMap.getMetadata().setNamespace(kettleCronJob.getMetadata().getNamespace());
			final Map<String, String> labels = new HashMap<>();
			labels.put(StaticUtils.LABEL_MANAGED_BY, StaticUtils.DATA_MANAGED_BY);
			labels.put(StaticUtils.LABEL_APP, kettleCronJob.getMetadata().getName());
			labels.put(StaticUtils.LABEL_PART_OF, kettleCronJob.getMetadata().getName());
			configMap.getMetadata().setLabels(labels);
			String xmlRepositories = "";
			if (kettleCronJob.getSpec().getXmlRepository() != null
					&& !kettleCronJob.getSpec().getXmlRepository().isEmpty()) {
				xmlRepositories = kettleCronJob.getSpec().getXmlRepository();
			} else {
				xmlRepositories = StaticUtils.repositoriesManagement(KettleOperator.getUsedClient(), kettleCronJob);
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
