package net.rossonet.operator.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.google.common.io.Files;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.ExecListener;
import io.fabric8.kubernetes.client.dsl.ExecWatch;
import net.rossonet.operator.model.cron.job.CronKettleJob;
import net.rossonet.operator.model.cron.job.CronKettleJobStatus;
import net.rossonet.operator.model.cron.transformation.CronKettleTransformation;
import net.rossonet.operator.model.cron.transformation.CronKettleTransformationStatus;
import net.rossonet.operator.model.ide.KettleIdeStatus;
import net.rossonet.operator.model.repository.KettleRepositoryStatus;
import net.rossonet.operator.model.simple.job.KettleJob;
import net.rossonet.operator.model.simple.job.KettleJobStatus;
import net.rossonet.operator.model.simple.transformation.KettleTransformation;
import net.rossonet.operator.model.simple.transformation.KettleTransformationStatus;

public class StaticUtils {

	private static class ExecPodListener implements ExecListener {
		@Override
		public void onClose(final int i, final String s) {
			logger.info("Shell Closing");
			execLatch.countDown();
		}

		@Override
		public void onFailure(final Throwable t, final Response failureResponse) {
			logger.info("Some error encountered");
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

	public static final String DATA_MANAGED_BY = "kettle-operator";
	private static final CountDownLatch execLatch = new CountDownLatch(1);
	public static final String FILE = "file://";

	public static final String FTP = "ftp://";
	public static final String GIT_HTTP = "git-http://";
	public static final String GIT_HTTPS = "git-https://";

	public static final String GIT_SSH = "git-ssh://";

	public static final String HTTP = "http://";

	public static final String HTTPS = "https://";

	public static final String LABEL_APP = "app";

	public static final String LABEL_MANAGED_BY = "app.kubernetes.io/managed-by";

	public static final String LABEL_PART_OF = "app.kubernetes.io/part-of";

	private static final Logger logger = Logger.getLogger(StaticUtils.class.getName());

	public static final String S3 = "s3://";

	public static final String SELECTOR = LABEL_MANAGED_BY + "=" + DATA_MANAGED_BY;

	public static List<String> createCronJobCommand(final CronKettleJob kettleJob) {
		// TODO implementare logica
		return Arrays.asList(new String[] { "uname -a" });
	}

	public static CronKettleJobStatus createCronKettleJobStatus(final String name) {
		// TODO implementare logica
		return new CronKettleJobStatus();
	}

	public static CronKettleTransformationStatus createCronKettleTransformationStatus(final String name) {
		// TODO implementare logica
		return new CronKettleTransformationStatus();
	}

	public static List<String> createCronTransformationCommand(final CronKettleTransformation kettleTransformation) {
		// TODO implementare logica
		return Arrays.asList(new String[] { "uname -a" });
	}

	public static List<String> createJobCommand(final KettleJob kettleJob) {
		// TODO implementare logica
		return Arrays.asList(new String[] { "uname -a" });
	}

	public static KettleIdeStatus createKettleIdeStatus(final String name) {
		// TODO implementare logica
		return new KettleIdeStatus();
	}

	public static KettleJobStatus createKettleJobStatus(final String name) {
		// TODO implementare logica
		return new KettleJobStatus();
	}

	public static KettleRepositoryStatus createKettleRepositoryStatus(final String name) {
		// TODO implementare logica
		return new KettleRepositoryStatus();
	}

	public static KettleTransformationStatus createKettleTransformationStatus(final String name) {
		// TODO implementare logica
		return new KettleTransformationStatus();
	}

	public static List<String> createTransformationCommand(final KettleTransformation kettleTransformation) {
		// TODO implementare logica
		return Arrays.asList(new String[] { "uname -a" });
	}

	public static ExecResult execCommandOnPod(final KubernetesClient kubernetesClient,
			final Deployment deploymentDatabase, final String[] command, final long timeoutCommandSeconds)
			throws InterruptedException {
		final ByteArrayOutputStream standardOutput = new ByteArrayOutputStream();
		final ByteArrayOutputStream standardError = new ByteArrayOutputStream();
		final String namespace = deploymentDatabase.getMetadata().getNamespace();
		final String podName = deploymentDatabase.getMetadata().getName();
		String podNameSelected = "NaN";
		for (final Pod pod : kubernetesClient.pods().inNamespace(namespace).withLabel(StaticUtils.LABEL_APP, podName)
				.list().getItems()) {
			logger.fine("pod " + pod.getMetadata().getName() + " in namespace " + pod.getMetadata().getNamespace());
			podNameSelected = pod.getMetadata().getName();
			logger.finer(pod.toString() + "\n");
		}
		logger.info("**** try '" + Arrays.toString(command) + "' to " + podName + " in namespace " + namespace);
		final ExecWatch execWatch = kubernetesClient.pods().inNamespace(namespace).withName(podNameSelected)
				.writingOutput(standardOutput).writingError(standardError).usingListener(new ExecPodListener())
				.exec(command);
		final boolean latchTerminationStatus = execLatch.await(timeoutCommandSeconds, TimeUnit.SECONDS);
		if (!latchTerminationStatus) {
			logger.warning("Latch could not terminate within specified time");
		}
		logger.fine("Exec Output: {} " + standardOutput.toString());
		execWatch.close();
		return new ExecResult(command, standardOutput.toString(), standardError.toString());
	}

	public static void saveStringToFileOnPod(final KubernetesClient kubernetesClient,
			final Deployment deploymentDatabase, final String payload, final String destinationFile)
			throws IOException {
		final String namespace = deploymentDatabase.getMetadata().getNamespace();
		final String podName = deploymentDatabase.getMetadata().getName();
		String podNameSelected = "NaN";
		for (final Pod pod : kubernetesClient.pods().inNamespace(namespace).withLabel(StaticUtils.LABEL_APP, podName)
				.list().getItems()) {
			logger.fine("pod " + pod.getMetadata().getName() + " in namespace " + pod.getMetadata().getNamespace());
			podNameSelected = pod.getMetadata().getName();
			logger.finer(pod.toString() + "\n");
		}
		final File tempFile = new File("/tmp/" + UUID.randomUUID().toString());
		tempFile.deleteOnExit();
		Files.write(payload.getBytes(), tempFile);
		kubernetesClient.pods().inNamespace(namespace).withName(podNameSelected).file(destinationFile)
				.upload(tempFile.toPath());
		tempFile.delete();
	}

	private StaticUtils() {
		// solo metodi statici
	}

}
