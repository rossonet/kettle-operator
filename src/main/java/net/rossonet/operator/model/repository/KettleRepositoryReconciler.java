package net.rossonet.operator.model.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;

import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;
import net.rossonet.operator.model.LogUtils;
import net.rossonet.operator.model.StaticUtils;

@ControllerConfiguration(dependents = { @Dependent(type = RepositoryResource.class),
		@Dependent(type = ServiceRepositoryResource.class) })
public class KettleRepositoryReconciler implements Reconciler<KettleRepository> {
	public enum RepositoryStatus {
		FAULT, INIT, SYNCHRONIZED
	}

	private static final Logger logger = Logger.getLogger(KettleRepositoryReconciler.class.getName());

	@SuppressWarnings("unused")
	private final KubernetesClient kubernetesClient;

	public KettleRepositoryReconciler() {
		this(new DefaultKubernetesClient());
	}

	public KettleRepositoryReconciler(final KubernetesClient kubernetesClient) {
		this.kubernetesClient = kubernetesClient;
	}

	private void databaseManagement(final KettleRepository kettleRepository, final Deployment deploymentDatabase,
			final Service serviceDatabase) {
		try {
			if (kettleRepository.getStatus().getReturnCode()
					.equals(KettleRepositoryReconciler.RepositoryStatus.INIT.toString())) {
				final String dbURL = "jdbc:postgresql://" + serviceDatabase.getMetadata().getName() + ":5432/"
						+ kettleRepository.getSpec().getDatabaseName();
				final Properties parameters = new Properties();
				parameters.put("user", kettleRepository.getSpec().getUsername());
				parameters.put("password", kettleRepository.getSpec().getPassword());
				final Connection connection = DriverManager.getConnection(dbURL, parameters);
				final Statement statementCreate = connection.createStatement();
				statementCreate.execute("CREATE TABLE test_table (ID INT PRIMARY KEY , NAME TEXT)");
				final Statement statementInsert = connection.createStatement();
				statementInsert.execute("INSERT INTO test_table (3 , 'prova')");
				final Statement statementSelect = connection.createStatement();
				final ResultSet resultData = statementSelect.executeQuery("SELECT * from test_table");
				final ResultSetMetaData rsmd = resultData.getMetaData();
				final int columnCount = rsmd.getColumnCount();
				while (resultData.next()) {
					final StringBuilder tableData = new StringBuilder();
					for (int colIdx = 1; colIdx <= columnCount; colIdx++) {
						tableData.append(resultData.getObject(colIdx));
						if (colIdx != columnCount) {
							tableData.append(':');
						}
					}
					logger.info("DATA: " + tableData.toString());
				}
			} else {
				logger.info("database already loaded, kettleRepository.getStatus().getReturnCode()="
						+ kettleRepository.getStatus().getReturnCode());
			}
		} catch (final Exception e) {
			logger.severe("Exception in database management " + LogUtils.stackTraceToString(e));
		}
	}

	@Override
	public UpdateControl<KettleRepository> reconcile(final KettleRepository resource,
			final Context<KettleRepository> context) {
		try {
			logger.info("reconciler  " + resource + " -> " + context);
			final Deployment deploymentDatabase = context.getSecondaryResource(Deployment.class).get();
			final Service serviceDatabase = context.getSecondaryResource(Service.class).get();
			resource.setStatus(StaticUtils.createKettleRepositoryStatus(deploymentDatabase.getMetadata().getName()));
			if (deploymentDatabase != null && deploymentDatabase.getStatus() != null
					&& deploymentDatabase.getStatus().getReadyReplicas() > 0 && serviceDatabase != null) {
				databaseManagement(resource, deploymentDatabase, serviceDatabase);
			}
			return UpdateControl.patchStatus(resource);
		} catch (final Exception ee) {
			logger.severe(LogUtils.stackTraceToString(ee));
			throw ee;
		}
	}

}
