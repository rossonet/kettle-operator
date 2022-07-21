package net.rossonet.operator;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.ConfigurationException;

import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.http.Exit;
import org.takes.http.FtBasic;

import io.fabric8.kubernetes.client.APIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.AdmissionRegistrationAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.ApiextensionsAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.AppsAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.AuthorizationAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.AutoscalingAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.BatchAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.CertificatesAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.DiscoveryAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.EventingAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.ExtensionsAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.FlowControlAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.MetricAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.NetworkAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.PolicyAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.RbacAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.SchedulingAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.StorageAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V1APIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V1AdmissionRegistrationAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V1ApiextensionsAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V1AuthorizationAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V1AutoscalingAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V1BatchAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V1CertificatesAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V1DiscoveryAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V1EventingAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V1NetworkAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V1PolicyAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V1SchedulingAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V1beta1AdmissionRegistrationAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V1beta1ApiextensionsAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V1beta1AuthorizationAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V1beta1BatchAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V1beta1CertificatesAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V1beta1DiscoveryAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V1beta1EventingAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V1beta1FlowControlAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V1beta1NetworkAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V1beta1PolicyAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V1beta1SchedulingAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V2beta1AutoscalingAPIGroupExtensionAdapter;
import io.fabric8.kubernetes.client.V2beta2AutoscalingAPIGroupExtensionAdapter;
import io.fabric8.openshift.client.NamespacedOpenShiftExtensionAdapter;
import io.fabric8.openshift.client.OpenShiftClusterAutoscalingAPIGroupExtensionAdapter;
import io.fabric8.openshift.client.OpenShiftConfigAPIGroupExtensionAdapter;
import io.fabric8.openshift.client.OpenShiftConsoleAPIGroupExtensionAdapter;
import io.fabric8.openshift.client.OpenShiftExtensionAdapter;
import io.fabric8.openshift.client.OpenShiftHiveAPIGroupExtensionAdapter;
import io.fabric8.openshift.client.OpenShiftMachineAPIGroupExtensionAdapter;
import io.fabric8.openshift.client.OpenShiftMachineConfigurationAPIGroupExtensionAdapter;
import io.fabric8.openshift.client.OpenShiftMonitoringAPIGroupExtensionAdapter;
import io.fabric8.openshift.client.OpenShiftOperatorAPIGroupExtensionAdapter;
import io.fabric8.openshift.client.OpenShiftOperatorHubAPIGroupExtensionAdapter;
import io.fabric8.openshift.client.OpenShiftQuotaAPIGroupExtensionAdapter;
import io.fabric8.openshift.client.OpenShiftStorageVersionMigratorApiGroupExtensionAdapter;
import io.fabric8.openshift.client.OpenShiftTunedAPIGroupExtensionAdapter;
import io.fabric8.openshift.client.OpenShiftWhereaboutsAPIGroupExtensionAdapter;
import io.fabric8.openshift.client.V1OpenShiftClusterAutoscalingAPIGroupExtensionAdapter;
import io.fabric8.openshift.client.V1beta1OpenShiftClusterAutoscalingAPIGroupExtensionAdapter;
import io.javaoperatorsdk.operator.Operator;
import net.rossonet.operator.model.cron.job.CronKettleJobReconciler;
import net.rossonet.operator.model.cron.transformation.CronKettleTransformationReconciler;
import net.rossonet.operator.model.ide.KettleIdeReconciler;
import net.rossonet.operator.model.repository.KettleRepositoryReconciler;
import net.rossonet.operator.model.simple.job.KettleJobReconciler;
import net.rossonet.operator.model.simple.transformation.KettleTransformationReconciler;

public class KettleOperator {

	private static final Logger logger = Logger.getLogger(KettleOperator.class.getName());

	public static void changeLogLevel(final String logLevel) throws ConfigurationException {
		final Logger rootLogger = Logger.getLogger("");
		Level targetLevel = Level.INFO;
		switch (logLevel) {
		case "all":
			targetLevel = Level.ALL;
			break;
		case "config":
			targetLevel = Level.CONFIG;
			break;
		case "fine":
			targetLevel = Level.FINE;
			break;
		case "finer":
			targetLevel = Level.FINER;
			break;
		case "finest":
			targetLevel = Level.FINEST;
			break;
		case "info":
			targetLevel = Level.INFO;
			break;
		case "off":
			targetLevel = Level.OFF;
			break;
		case "severe":
			targetLevel = Level.SEVERE;
			break;
		case "warning":
			targetLevel = Level.WARNING;
			break;
		default:
			throw new ConfigurationException("log level " + logLevel
					+ " not exists. You can use: all, config, fine, finer, finest, info, off, severe or warning");
		}
		rootLogger.setLevel(targetLevel);
		for (final Handler handler : rootLogger.getHandlers()) {
			handler.setLevel(targetLevel);
		}
	}

	public static void main(final String[] args) throws IOException {
		logger.info("operator init");
		try {
			changeLogLevel("fine");
		} catch (final ConfigurationException e) {
			logger.warning("Exception: " + e.getMessage());
		}
		final Config config = new ConfigBuilder().withNamespace(null).build();
		final KubernetesClient client = new DefaultKubernetesClient(config);
		final Operator operator = new Operator(client);
		operator.register(new KettleJobReconciler(client));
		operator.register(new KettleTransformationReconciler(client));
		operator.register(new CronKettleJobReconciler(client));
		operator.register(new CronKettleTransformationReconciler(client));
		operator.register(new KettleIdeReconciler(client));
		operator.register(new KettleRepositoryReconciler(client));
		operator.installShutdownHook();
		operator.start();
		logger.info("operator started");
		new FtBasic(new TkFork(new FkRegex("/health", "ALL GOOD.")), 8080).start(Exit.NEVER);
		client.close();
	}

	@SuppressWarnings("unused")
	private final AdmissionRegistrationAPIGroupExtensionAdapter AdmissionRegistrationAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final ApiextensionsAPIGroupExtensionAdapter ApiextensionsAPIGroupExtensionAdapter = null;
	@SuppressWarnings({ "unused", "rawtypes" })
	private final APIGroupExtensionAdapter APIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final AppsAPIGroupExtensionAdapter AppsAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final AuthorizationAPIGroupExtensionAdapter AuthorizationAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final AutoscalingAPIGroupExtensionAdapter AutoscalingAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final BatchAPIGroupExtensionAdapter BatchAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final CertificatesAPIGroupExtensionAdapter CertificatesAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final DiscoveryAPIGroupExtensionAdapter DiscoveryAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final EventingAPIGroupExtensionAdapter EventingAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final ExtensionsAPIGroupExtensionAdapter ExtensionsAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final FlowControlAPIGroupExtensionAdapter FlowControlAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final io.fabric8.kubernetes.internal.InternalResourceMappingProvider InternalResourceMappingProvider = null;
	@SuppressWarnings("unused")
	private final MetricAPIGroupExtensionAdapter MetricAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final NamespacedOpenShiftExtensionAdapter NamespacedOpenShiftExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final NetworkAPIGroupExtensionAdapter NetworkAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final OpenShiftClusterAutoscalingAPIGroupExtensionAdapter OpenShiftClusterAutoscalingAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final OpenShiftConfigAPIGroupExtensionAdapter OpenShiftConfigAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final OpenShiftConsoleAPIGroupExtensionAdapter OpenShiftConsoleAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final OpenShiftExtensionAdapter OpenShiftExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final OpenShiftHiveAPIGroupExtensionAdapter OpenShiftHiveAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final OpenShiftMachineAPIGroupExtensionAdapter OpenShiftMachineAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final OpenShiftMachineConfigurationAPIGroupExtensionAdapter OpenShiftMachineConfigurationAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final OpenShiftMonitoringAPIGroupExtensionAdapter OpenShiftMonitoringAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final OpenShiftOperatorAPIGroupExtensionAdapter OpenShiftOperatorAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final OpenShiftOperatorHubAPIGroupExtensionAdapter OpenShiftOperatorHubAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final OpenShiftQuotaAPIGroupExtensionAdapter OpenShiftQuotaAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final OpenShiftStorageVersionMigratorApiGroupExtensionAdapter OpenShiftStorageVersionMigratorApiGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final OpenShiftTunedAPIGroupExtensionAdapter OpenShiftTunedAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final OpenShiftWhereaboutsAPIGroupExtensionAdapter OpenShiftWhereaboutsAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final PolicyAPIGroupExtensionAdapter PolicyAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final RbacAPIGroupExtensionAdapter RbacAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final SchedulingAPIGroupExtensionAdapter SchedulingAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final StorageAPIGroupExtensionAdapter StorageAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1AdmissionRegistrationAPIGroupExtensionAdapter V1AdmissionRegistrationAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1ApiextensionsAPIGroupExtensionAdapter V1ApiextensionsAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1APIGroupExtensionAdapter V1APIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1AuthorizationAPIGroupExtensionAdapter V1AuthorizationAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1AutoscalingAPIGroupExtensionAdapter V1AutoscalingAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1BatchAPIGroupExtensionAdapter V1BatchAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1beta1AdmissionRegistrationAPIGroupExtensionAdapter V1beta1AdmissionRegistrationAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1beta1ApiextensionsAPIGroupExtensionAdapter V1beta1ApiextensionsAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1beta1AuthorizationAPIGroupExtensionAdapter V1beta1AuthorizationAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1beta1BatchAPIGroupExtensionAdapter V1beta1BatchAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1beta1CertificatesAPIGroupExtensionAdapter V1beta1CertificatesAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1beta1DiscoveryAPIGroupExtensionAdapter V1beta1DiscoveryAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1beta1EventingAPIGroupExtensionAdapter V1beta1EventingAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1beta1FlowControlAPIGroupExtensionAdapter V1beta1FlowControlAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1beta1NetworkAPIGroupExtensionAdapter V1beta1NetworkAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1beta1OpenShiftClusterAutoscalingAPIGroupExtensionAdapter V1beta1OpenShiftClusterAutoscalingAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1beta1PolicyAPIGroupExtensionAdapter V1beta1PolicyAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1beta1SchedulingAPIGroupExtensionAdapter V1beta1SchedulingAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1CertificatesAPIGroupExtensionAdapter V1CertificatesAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1DiscoveryAPIGroupExtensionAdapter V1DiscoveryAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1EventingAPIGroupExtensionAdapter V1EventingAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1NetworkAPIGroupExtensionAdapter V1NetworkAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1OpenShiftClusterAutoscalingAPIGroupExtensionAdapter V1OpenShiftClusterAutoscalingAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1PolicyAPIGroupExtensionAdapter V1PolicyAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V1SchedulingAPIGroupExtensionAdapter V1SchedulingAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V2beta1AutoscalingAPIGroupExtensionAdapter V2beta1AutoscalingAPIGroupExtensionAdapter = null;
	@SuppressWarnings("unused")
	private final V2beta2AutoscalingAPIGroupExtensionAdapter V2beta2AutoscalingAPIGroupExtensionAdapter = null;

}
