package net.rossonet.operator.model.repository;

public class KettleRepositoryStatus {

	private String errorMessage = null;

	private String lastBackupTime = null;
	private String returnCode = KettleRepositoryReconciler.RepositoryStatus.INIT.toString();

	private int totalJobs = 0;

	private int totalTransformations = 0;

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getLastBackupTime() {
		return lastBackupTime;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public int getTotalJobs() {
		return totalJobs;
	}

	public int getTotalTransformations() {
		return totalTransformations;
	}

	public void setErrorMessage(final String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setLastBackupTime(final String lastBackupTime) {
		this.lastBackupTime = lastBackupTime;
	}

	public void setReturnCode(final String returnCode) {
		this.returnCode = returnCode;
	}

	public void setTotalJobs(final int totalJobs) {
		this.totalJobs = totalJobs;
	}

	public void setTotalTransformations(final int totalTransformations) {
		this.totalTransformations = totalTransformations;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("KettleRepositoryStatus [");
		if (errorMessage != null) {
			builder.append("errorMessage=");
			builder.append(errorMessage);
			builder.append(", ");
		}
		if (lastBackupTime != null) {
			builder.append("lastBackupTime=");
			builder.append(lastBackupTime);
			builder.append(", ");
		}
		if (returnCode != null) {
			builder.append("returnCode=");
			builder.append(returnCode);
			builder.append(", ");
		}
		builder.append("totalJobs=");
		builder.append(totalJobs);
		builder.append(", totalTransformations=");
		builder.append(totalTransformations);
		builder.append("]");
		return builder.toString();
	}

}
