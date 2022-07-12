package net.rossonet.operator.model;

import java.util.Arrays;
import java.util.List;

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

	public static final String LABEL = "app.kubernetes.io/managed-by";
	public static final String LABEL_DATA = "kettle-operator";

	public static final String SELECTOR = LABEL + "=" + LABEL_DATA;

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

	private StaticUtils() {
		// solo metodi statici
	}

}
