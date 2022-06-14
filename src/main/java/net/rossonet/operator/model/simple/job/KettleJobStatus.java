package net.rossonet.operator.model.simple.job;

public class KettleJobStatus {

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
		return "KettleJobStatus [errorMessage=" + errorMessage + ", executionTimeMs=" + executionTimeMs
				+ ", returnCode=" + returnCode + "]";
	}

}
