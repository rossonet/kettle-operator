package net.rossonet.operator.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.google.common.io.Files;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.KubernetesResourceList;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.batch.v1.CronJob;
import io.fabric8.kubernetes.api.model.batch.v1.Job;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.ExecListener;
import io.fabric8.kubernetes.client.dsl.ExecWatch;
import io.fabric8.kubernetes.client.dsl.MixedOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import net.rossonet.operator.model.cron.job.CronKettleJob;
import net.rossonet.operator.model.cron.job.CronKettleJobSpec;
import net.rossonet.operator.model.cron.job.CronKettleJobStatus;
import net.rossonet.operator.model.cron.transformation.CronKettleTransformation;
import net.rossonet.operator.model.cron.transformation.CronKettleTransformationSpec;
import net.rossonet.operator.model.cron.transformation.CronKettleTransformationStatus;
import net.rossonet.operator.model.ide.KettleIdeStatus;
import net.rossonet.operator.model.repository.KettleRepository;
import net.rossonet.operator.model.repository.KettleRepositoryStatus;
import net.rossonet.operator.model.simple.job.KettleJob;
import net.rossonet.operator.model.simple.job.KettleJobSpec;
import net.rossonet.operator.model.simple.job.KettleJobStatus;
import net.rossonet.operator.model.simple.transformation.KettleTransformation;
import net.rossonet.operator.model.simple.transformation.KettleTransformationSpec;
import net.rossonet.operator.model.simple.transformation.KettleTransformationStatus;

public class StaticUtils {

	private static class ExecPodListener implements ExecListener {
		private final CountDownLatch execLatch;

		public ExecPodListener(final CountDownLatch execLatch) {
			this.execLatch = execLatch;
		}

		public CountDownLatch getExecLatch() {
			return execLatch;
		}

		@Override
		public void onClose(final int i, final String s) {
			logger.info("Shell Closing with return code " + i);
			logger.info(s);
			execLatch.countDown();
		}

		@Override
		public void onFailure(final Throwable t, final Response failureResponse) {
			logger.warning("Some error encountered");
			logger.warning(LogUtils.stackTraceToString(t));
			try {
				logger.warning(failureResponse.body());
			} catch (final IOException e) {
				logger.warning(LogUtils.stackTraceToString(e));
			}
			execLatch.countDown();
		}

		@Override
		public void onOpen() {
			logger.info("Shell was opened");
		}
	}

	public static class ExecResult {

		private String[] command;
		private String standardError;
		private String standardOutput;

		public ExecResult(final String[] command, final String standardOutput, final String standardError) {
			this.command = command;
			this.standardOutput = standardOutput;
			this.standardError = standardError;
		}

		public String[] getCommand() {
			return command;
		}

		public String getStandardError() {
			return standardError;
		}

		public String getStandardOutput() {
			return standardOutput;
		}

		public void setCommand(final String[] command) {
			this.command = command;
		}

		public void setStandardError(final String standardError) {
			this.standardError = standardError;
		}

		public void setStandardOutput(final String standardOutput) {
			this.standardOutput = standardOutput;
		}

		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder();
			builder.append("ExecResult [");
			if (command != null) {
				builder.append("command=");
				builder.append(Arrays.toString(command));
				builder.append(", ");
			}
			if (standardOutput != null) {
				builder.append("standardOutput=");
				builder.append(standardOutput);
				builder.append(", ");
			}
			if (standardError != null) {
				builder.append("standardError=");
				builder.append(standardError);
			}
			builder.append("]");
			return builder.toString();
		}

	}

	private static String BASE_COMMAND_DIRECTORY = "/data-integration";

	public static final String DATA_MANAGED_BY = "kettle-operator";

	public static final String FILE = "file://";
	private static String footerRepositories = "</repositories>\n";

	public static final String FTP = "ftp://";
	public static final String GIT_HTTP = "git-http://";
	public static final String GIT_HTTPS = "git-https://";

	public static final String GIT_SSH = "git-ssh://";

	private static String headerRepositories = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<repositories>\n";

	public static final String HTTP = "http://";

	public static final String HTTPS = "https://";

	public static final String LABEL_APP = "app";

	public static final String LABEL_MANAGED_BY = "app.kubernetes.io/managed-by";

	public static final String LABEL_PART_OF = "app.kubernetes.io/part-of";

	private static final Logger logger = Logger.getLogger(StaticUtils.class.getName());

	public static final String REPOSITORIES = "repositories";

	public static final String REPOSITORIES_VOLUME_NAME = "repositories-config";

	public static final String S3 = "s3://";

	public static final String SELECTOR = LABEL_MANAGED_BY + "=" + DATA_MANAGED_BY;

	private static final String SYNCHRONIZED_FILE_MARK = "SYNCHRONIZED FILE FOUND";

	private static final long TIMEOUT_CHECK_SECONDS = 60;

	private static void addConnection(final StringBuilder sb, final KettleRepository kettleRepository) {
		sb.append("  <connection>\n");
		sb.append("    <name>" + kettleRepository.getMetadata().getName() + "</name>\n");
		sb.append("    <server>" + kettleRepository.getMetadata().getName() + "</server>\n");
		sb.append("    <type>POSTGRESQL</type>\n");
		sb.append("    <access>Native</access>\n");
		sb.append("    <database>" + kettleRepository.getSpec().getDatabaseName() + "</database>\n");
		sb.append("    <port>5432</port>\n");
		sb.append("    <username>" + kettleRepository.getSpec().getUsername() + "</username>\n");
		sb.append("    <password>" + kettleRepository.getSpec().getPassword() + "</password>\n");
		sb.append("    <servername/>\n");
		sb.append("    <data_tablespace/>\n");
		sb.append("    <index_tablespace/>\n");
		sb.append("    <attributes>\n");
		sb.append("      <attribute><code>FORCE_IDENTIFIERS_TO_LOWERCASE</code><attribute>N</attribute></attribute>\n");
		sb.append("      <attribute><code>FORCE_IDENTIFIERS_TO_UPPERCASE</code><attribute>N</attribute></attribute>\n");
		sb.append("      <attribute><code>IS_CLUSTERED</code><attribute>N</attribute></attribute>\n");
		sb.append("      <attribute><code>PORT_NUMBER</code><attribute>5432</attribute></attribute>\n");
		sb.append("      <attribute><code>PRESERVE_RESERVED_WORD_CASE</code><attribute>Y</attribute></attribute>\n");
		sb.append("      <attribute><code>QUOTE_ALL_FIELDS</code><attribute>N</attribute></attribute>\n");
		sb.append("      <attribute><code>SUPPORTS_BOOLEAN_DATA_TYPE</code><attribute>Y</attribute></attribute>\n");
		sb.append("      <attribute><code>SUPPORTS_TIMESTAMP_DATA_TYPE</code><attribute>Y</attribute></attribute>\n");
		sb.append("      <attribute><code>USE_POOLING</code><attribute>N</attribute></attribute>\n");
		sb.append("    </attributes>\n");
		sb.append("  </connection>");
	}

	private static void addRepository(final StringBuilder sb, final KettleRepository kettleRepository) {
		sb.append(" <repository>\n");
		sb.append("    <id>KettleDatabaseRepository</id>\n");
		sb.append("    <name>Repository " + kettleRepository.getMetadata().getName() + "</name>\n");
		sb.append("    <description>" + "KubernetesRepository configuration:\n" + kettleRepository.getSpec().toString()
				+ "</description>\n");
		sb.append("    <is_default>false</is_default>\n");
		sb.append("    <connection>" + kettleRepository.getMetadata().getName() + "</connection>\n");
		sb.append("  </repository>");

	}

	private static void addRepositoryCopy(final List<String> command) {
		command.add("mkdir");
		command.add("-p");
		command.add("/root/.kettle");
		command.add(";");
		command.add("cp");
		command.add("/data/repositories.xml");
		command.add("/root/.kettle/repositories.xml");
		command.add(";");
	}

	private static boolean checkControlFile(final KubernetesClient kubernetesClient, final String namespace,
			final String podName, final String onlyOneTimePath) throws IOException, InterruptedException {
		final String destinationFile = onlyOneTimePath + "_script.sh";
		final String script = "if [ -f " + createCheckFile(onlyOneTimePath) + " ]\nthen\necho '"
				+ SYNCHRONIZED_FILE_MARK + "'\nelse\necho 'KO'\nfi\n";
		final File tempFile = createLocalTempFile(script);
		saveFileOnPod(kubernetesClient, destinationFile, namespace, tempFile, podName);
		final String[] command = new String[] { "bash", destinationFile };
		final ExecResult result = execOnPod(kubernetesClient, namespace, podName, command, TIMEOUT_CHECK_SECONDS);
		return result.getStandardOutput().contains(SYNCHRONIZED_FILE_MARK);
	}

	private static boolean checkValidStringParameter(final String parameter) {
		return parameter != null && !parameter.isEmpty();
	}

	private static String createCheckFile(final String onlyOneTimePath) {
		final String checkFile = onlyOneTimePath + "_check";
		return checkFile;
	}

	public static List<String> createCronJobCommandArguments(final CronKettleJob kettleJob) {
		final CronKettleJobSpec commandParameters = kettleJob.getSpec();
		final List<String> command = new ArrayList<>();
		addRepositoryCopy(command);
		command.add(BASE_COMMAND_DIRECTORY + "/kitchen.sh");
		if (checkValidStringParameter(commandParameters.getDir())) {
			command.add("-dir=" + commandParameters.getDir());
		}
		if (checkValidStringParameter(commandParameters.getExport())) {
			command.add("-export=" + commandParameters.getExport());
		}
		if (checkValidStringParameter(commandParameters.getFile())) {
			command.add("-file=" + commandParameters.getFile());
		}
		if (checkValidStringParameter(commandParameters.getJob())) {
			command.add("-job=" + commandParameters.getJob());
		}
		if (checkValidStringParameter(commandParameters.getLevel())) {
			command.add("-level=" + commandParameters.getLevel());
		}
		if (checkValidStringParameter(commandParameters.getListdir())) {
			command.add("-listdir=" + commandParameters.getListdir());
		}
		if (checkValidStringParameter(commandParameters.getListjob())) {
			command.add("-listjob=" + commandParameters.getListjob());
		}
		if (checkValidStringParameter(commandParameters.getListrep())) {
			command.add("-listrep=" + commandParameters.getListrep());
		}
		if (checkValidStringParameter(commandParameters.getLogfile())) {
			command.add("-logfile=" + commandParameters.getLogfile());
		}
		if (checkValidStringParameter(commandParameters.getNorep())) {
			command.add("-norep=" + commandParameters.getNorep());
		}
		if (checkValidStringParameter(commandParameters.getPass())) {
			command.add("-pass=" + commandParameters.getPass());
		}
		if (checkValidStringParameter(commandParameters.getRep())) {
			command.add("-rep=" + commandParameters.getRep());
		}
		if (checkValidStringParameter(commandParameters.getUser())) {
			command.add("-user=" + commandParameters.getUser());
		}
		if (checkValidStringParameter(commandParameters.getVersion())) {
			command.add("-version=" + commandParameters.getVersion());
		}
		if (commandParameters.getParam() != null && commandParameters.getParam().length > 0) {
			for (final String singleParam : commandParameters.getParam()) {
				command.add("-param:" + singleParam);
			}
		}
		return command;
	}

	public static CronKettleJobStatus createCronKettleJobStatus(final CronKettleJobStatus actualStatus,
			final CronJob job) {
		CronKettleJobStatus status = actualStatus;
		if (status == null) {
			status = new CronKettleJobStatus();
		}
		// TODO implementare logica
		return status;
	}

	public static CronKettleTransformationStatus createCronKettleTransformationStatus(
			final CronKettleTransformationStatus actualStatus, final CronJob job) {
		CronKettleTransformationStatus status = actualStatus;
		if (status == null) {
			status = new CronKettleTransformationStatus();
		}
		// TODO implementare logica
		return status;
	}

	public static List<String> createCronTransformationCommandArguments(final CronKettleTransformation kettleTransformation) {
		final CronKettleTransformationSpec commandParameters = kettleTransformation.getSpec();
		final List<String> command = new ArrayList<>();
		addRepositoryCopy(command);
		command.add(BASE_COMMAND_DIRECTORY + "/pan.sh");
		if (checkValidStringParameter(commandParameters.getDir())) {
			command.add("-dir=" + commandParameters.getDir());
		}
		if (checkValidStringParameter(commandParameters.getExprep())) {
			command.add("-exprep=" + commandParameters.getExprep());
		}
		if (checkValidStringParameter(commandParameters.getFile())) {
			command.add("-file=" + commandParameters.getFile());
		}
		if (checkValidStringParameter(commandParameters.getLevel())) {
			command.add("-level=" + commandParameters.getLevel());
		}
		if (checkValidStringParameter(commandParameters.getListdir())) {
			command.add("-listdir=" + commandParameters.getListdir());
		}
		if (checkValidStringParameter(commandParameters.getListrep())) {
			command.add("-listrep=" + commandParameters.getListrep());
		}
		if (checkValidStringParameter(commandParameters.getListtrans())) {
			command.add("-listtrans=" + commandParameters.getListtrans());
		}
		if (checkValidStringParameter(commandParameters.getLogfile())) {
			command.add("-logfile=" + commandParameters.getLogfile());
		}
		if (checkValidStringParameter(commandParameters.getNorep())) {
			command.add("-norep=" + commandParameters.getNorep());
		}
		if (checkValidStringParameter(commandParameters.getPass())) {
			command.add("-pass=" + commandParameters.getPass());
		}
		if (checkValidStringParameter(commandParameters.getRep())) {
			command.add("-rep=" + commandParameters.getRep());
		}
		if (checkValidStringParameter(commandParameters.getSafemode())) {
			command.add("-safemode=" + commandParameters.getSafemode());
		}
		if (checkValidStringParameter(commandParameters.getTrans())) {
			command.add("-trans=" + commandParameters.getTrans());
		}
		if (checkValidStringParameter(commandParameters.getUser())) {
			command.add("-user=" + commandParameters.getUser());
		}
		if (checkValidStringParameter(commandParameters.getVersion())) {
			command.add("-version=" + commandParameters.getVersion());
		}
		if (commandParameters.getParam() != null && commandParameters.getParam().length > 0) {
			for (final String singleParam : commandParameters.getParam()) {
				command.add("-param:" + singleParam);
			}
		}
		return command;
	}

	public static List<String> createJobCommandArguments(final KettleJob kettleJob) {
		final KettleJobSpec commandParameters = kettleJob.getSpec();
		final List<String> command = new ArrayList<>();
		addRepositoryCopy(command);
		command.add(BASE_COMMAND_DIRECTORY + "/kitchen.sh");
		if (checkValidStringParameter(commandParameters.getDir())) {
			command.add("-dir=" + commandParameters.getDir());
		}
		if (checkValidStringParameter(commandParameters.getExport())) {
			command.add("-export=" + commandParameters.getExport());
		}
		if (checkValidStringParameter(commandParameters.getFile())) {
			command.add("-file=" + commandParameters.getFile());
		}
		if (checkValidStringParameter(commandParameters.getJob())) {
			command.add("-job=" + commandParameters.getJob());
		}
		if (checkValidStringParameter(commandParameters.getLevel())) {
			command.add("-level=" + commandParameters.getLevel());
		}
		if (checkValidStringParameter(commandParameters.getListdir())) {
			command.add("-listdir=" + commandParameters.getListdir());
		}
		if (checkValidStringParameter(commandParameters.getListjob())) {
			command.add("-listjob=" + commandParameters.getListjob());
		}
		if (checkValidStringParameter(commandParameters.getListrep())) {
			command.add("-listrep=" + commandParameters.getListrep());
		}
		if (checkValidStringParameter(commandParameters.getLogfile())) {
			command.add("-logfile=" + commandParameters.getLogfile());
		}
		if (checkValidStringParameter(commandParameters.getNorep())) {
			command.add("-norep=" + commandParameters.getNorep());
		}
		if (checkValidStringParameter(commandParameters.getPass())) {
			command.add("-pass=" + commandParameters.getPass());
		}
		if (checkValidStringParameter(commandParameters.getRep())) {
			command.add("-rep=" + commandParameters.getRep());
		}
		if (checkValidStringParameter(commandParameters.getUser())) {
			command.add("-user=" + commandParameters.getUser());
		}
		if (checkValidStringParameter(commandParameters.getVersion())) {
			command.add("-version=" + commandParameters.getVersion());
		}
		if (commandParameters.getParam() != null && commandParameters.getParam().length > 0) {
			for (final String singleParam : commandParameters.getParam()) {
				command.add("-param:" + singleParam);
			}
		}
		return command;
	}

	public static void createKettleConfigurationDirectory(final KubernetesClient kubernetesClient,
			final Deployment deployment) throws InterruptedException, IOException {
		final String target = "/root/.kettle";
		final String[] command = new String[] { "mkdir", "-p", target };
		StaticUtils.execCommandOnDeployment(kubernetesClient, deployment, command, 15, null);
	}

	public static KettleIdeStatus createKettleIdeStatus(final KettleIdeStatus actualStatus,
			final Deployment deployment) {
		KettleIdeStatus status = actualStatus;
		if (status == null) {
			status = new KettleIdeStatus();
		}
		// TODO implementare logica
		return status;
	}

	public static KettleJobStatus createKettleJobStatus(final KettleJobStatus actualStatus, final Job job) {
		KettleJobStatus status = actualStatus;
		if (status == null) {
			status = new KettleJobStatus();
		}
		// TODO implementare logica
		return status;
	}

	public static KettleRepositoryStatus createKettleRepositoryStatus(final KettleRepositoryStatus actualStatus,
			final Deployment deployment) {
		KettleRepositoryStatus status = actualStatus;
		if (status == null) {
			status = new KettleRepositoryStatus();
		}
		// TODO implementare logica
		return status;
	}

	public static KettleTransformationStatus createKettleTransformationStatus(
			final KettleTransformationStatus actualStatus, final Job job) {
		KettleTransformationStatus status = actualStatus;
		if (status == null) {
			status = new KettleTransformationStatus();
		}
		// TODO implementare logica
		return status;
	}

	private static File createLocalTempFile(final String payload) throws IOException {
		final File tempFile = new File("/tmp/" + UUID.randomUUID().toString());
		Files.write(payload.getBytes(), tempFile);
		tempFile.deleteOnExit();
		return tempFile;
	}

	public static List<String> createTransformationCommandArguments(final KettleTransformation kettleTransformation) {
		final KettleTransformationSpec commandParameters = kettleTransformation.getSpec();
		final List<String> command = new ArrayList<>();
		addRepositoryCopy(command);
		command.add(BASE_COMMAND_DIRECTORY + "/pan.sh");
		if (checkValidStringParameter(commandParameters.getDir())) {
			command.add("-dir=" + commandParameters.getDir());
		}
		if (checkValidStringParameter(commandParameters.getExprep())) {
			command.add("-exprep=" + commandParameters.getExprep());
		}
		if (checkValidStringParameter(commandParameters.getFile())) {
			command.add("-file=" + commandParameters.getFile());
		}
		if (checkValidStringParameter(commandParameters.getLevel())) {
			command.add("-level=" + commandParameters.getLevel());
		}
		if (checkValidStringParameter(commandParameters.getListdir())) {
			command.add("-listdir=" + commandParameters.getListdir());
		}
		if (checkValidStringParameter(commandParameters.getListrep())) {
			command.add("-listrep=" + commandParameters.getListrep());
		}
		if (checkValidStringParameter(commandParameters.getListtrans())) {
			command.add("-listtrans=" + commandParameters.getListtrans());
		}
		if (checkValidStringParameter(commandParameters.getLogfile())) {
			command.add("-logfile=" + commandParameters.getLogfile());
		}
		if (checkValidStringParameter(commandParameters.getNorep())) {
			command.add("-norep=" + commandParameters.getNorep());
		}
		if (checkValidStringParameter(commandParameters.getPass())) {
			command.add("-pass=" + commandParameters.getPass());
		}
		if (checkValidStringParameter(commandParameters.getRep())) {
			command.add("-rep=" + commandParameters.getRep());
		}
		if (checkValidStringParameter(commandParameters.getSafemode())) {
			command.add("-safemode=" + commandParameters.getSafemode());
		}
		if (checkValidStringParameter(commandParameters.getTrans())) {
			command.add("-trans=" + commandParameters.getTrans());
		}
		if (checkValidStringParameter(commandParameters.getUser())) {
			command.add("-user=" + commandParameters.getUser());
		}
		if (checkValidStringParameter(commandParameters.getVersion())) {
			command.add("-version=" + commandParameters.getVersion());
		}
		if (commandParameters.getParam() != null && commandParameters.getParam().length > 0) {
			for (final String singleParam : commandParameters.getParam()) {
				command.add("-param:" + singleParam);
			}
		}
		return command;
	}

	public static List<ExecResult> execCommandOnDeployment(final KubernetesClient kubernetesClient,
			final Deployment deployment, final String[] command, final long timeoutCommandSeconds,
			final String onlyOneTimePath) throws InterruptedException, IOException {
		final List<ExecResult> result = new ArrayList<>();
		final String namespace = deployment.getMetadata().getNamespace();
		final String deploymentName = deployment.getMetadata().getName();
		for (final Pod pod : kubernetesClient.pods().inNamespace(namespace)
				.withLabel(StaticUtils.LABEL_APP, deploymentName).list().getItems()) {
			if (onlyOneTimePath == null
					|| (!checkControlFile(kubernetesClient, namespace, pod.getMetadata().getName(), onlyOneTimePath))) {
				result.add(execOnPod(kubernetesClient, namespace, pod.getMetadata().getName(), command,
						timeoutCommandSeconds));
				if (onlyOneTimePath != null) {
					saveFileOnPod(kubernetesClient, createCheckFile(onlyOneTimePath), namespace,
							createLocalTempFile(new Date().toString()), pod.getMetadata().getName());
				}
			}
		}
		return result;
	}

	private static ExecResult execOnPod(final KubernetesClient kubernetesClient, final String namespace,
			final String podName, final String[] command, final long timeoutCommandSeconds)
			throws InterruptedException, IOException {
		final ByteArrayOutputStream standardOutput = new ByteArrayOutputStream();
		final ByteArrayOutputStream standardError = new ByteArrayOutputStream();
		logger.fine("pod " + podName + " in namespace " + podName);
		logger.fine("try '" + Arrays.toString(command) + "' to " + podName + " in namespace " + namespace);
		final ExecPodListener listener = new ExecPodListener(new CountDownLatch(1));
		final ExecWatch execWatch = kubernetesClient.pods().inNamespace(namespace).withName(podName)
				.writingOutput(standardOutput).writingError(standardError)// .withTTY()
				.usingListener(listener).exec(command);
		final boolean latchTerminationStatus = listener.getExecLatch().await(timeoutCommandSeconds, TimeUnit.SECONDS);
		if (!latchTerminationStatus) {
			logger.warning("Latch could not terminate within specified time");
		}
		standardOutput.flush();
		standardError.flush();
		final String output = new String(standardOutput.toByteArray());
		final String error = new String(standardError.toByteArray());
		logger.fine("Exec Output: " + output);
		logger.fine("Exec Error: " + error);
		standardOutput.close();
		standardError.close();
		execWatch.close();
		return new ExecResult(command, output, error);
	}

	public static String repositoriesManagement(final KubernetesClient kubernetesClient, final HasMetadata resource)
			throws InterruptedException, IOException {
		final MixedOperation<KettleRepository, KubernetesResourceList<KettleRepository>, Resource<KettleRepository>> repositoryClient = kubernetesClient
				.resources(KettleRepository.class);
		final StringBuilder sb = new StringBuilder();
		sb.append(headerRepositories);
		for (final KettleRepository kettleRepository : repositoryClient
				.inNamespace(resource.getMetadata().getNamespace()).list().getItems()) {
			addConnection(sb, kettleRepository);
			addRepository(sb, kettleRepository);
		}
		sb.append(footerRepositories);
		return sb.toString();
	}

	private static void saveFileOnPod(final KubernetesClient kubernetesClient, final String destinationFile,
			final String namespace, final File tempFile, final String podNameSelected) {
		logger.fine("pod " + podNameSelected + " in namespace " + namespace);
		kubernetesClient.pods().inNamespace(namespace).withName(podNameSelected).file(destinationFile)
				.upload(tempFile.toPath());
	}

	public static void saveStringToFileOnDeployment(final KubernetesClient kubernetesClient,
			final Deployment deployment, final String payload, final String destinationFile) throws IOException {
		final String namespace = deployment.getMetadata().getNamespace();
		final String podName = deployment.getMetadata().getName();
		final File tempFile = createLocalTempFile(payload);
		for (final Pod pod : kubernetesClient.pods().inNamespace(namespace).withLabel(StaticUtils.LABEL_APP, podName)
				.list().getItems()) {
			saveFileOnPod(kubernetesClient, destinationFile, namespace, tempFile, pod.getMetadata().getName());
		}
		tempFile.delete();
	}

	private StaticUtils() {
		// only for static usage
	}

}
