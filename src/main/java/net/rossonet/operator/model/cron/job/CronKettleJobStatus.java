package net.rossonet.operator.model.cron.job;

public class CronKettleJobStatus {

	private String errorMessage = null;
	private long executionTimeMs = 0;
	private String returnCode = "INIT";

	public String getErrorMessage() {
		return errorMessage;
	}

	public long getExecutionTimeMs() {
		return executionTimeMs;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setErrorMessage(final String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setExecutionTimeMs(final long executionTimeMs) {
		this.executionTimeMs = executionTimeMs;
	}

	public void setReturnCode(final String returnCode) {
		this.returnCode = returnCode;
	}

	@Override
	public String toString() {
		return "CronKettleJobStatus [errorMessage=" + errorMessage + ", executionTimeMs=" + executionTimeMs
				+ ", returnCode=" + returnCode + "]";
	}

}
