package net.rossonet.operator.model;

import java.util.List;

import io.javaoperatorsdk.operator.api.reconciler.ErrorStatusUpdateControl;
import net.rossonet.operator.model.cron.job.CronKettleJob;
import net.rossonet.operator.model.cron.transformation.CronKettleTransformation;
import net.rossonet.operator.model.simple.KettleStatus;
import net.rossonet.operator.model.simple.KettleWork;
import net.rossonet.operator.model.simple.job.KettleJob;
import net.rossonet.operator.model.simple.transformation.KettleTransformation;

public class StaticUtils {

	public static List<String> createCronJobCommand(final CronKettleJob kettleJob) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<String> createCronTransformationCommand(final CronKettleTransformation kettleTransformation) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<String> createJobCommand(final KettleJob kettleJob) {
		// TODO Auto-generated method stub
		return null;
	}

	public static KettleStatus createStatus(final String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<String> createTransformationCommand(final KettleTransformation kettleTransformation) {
		// TODO Auto-generated method stub
		return null;
	}

	public static ErrorStatusUpdateControl<? extends KettleWork> handleError(final KettleWork resource,
			final Exception e) {
		// TODO Auto-generated method stub
		return null;
	}

	private StaticUtils() {
		// solo metodi statici
	}

}
