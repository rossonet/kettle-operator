package net.rossonet.operator.model.simple.job;

public class KettleJobStatus {

	public enum ReturnCode {

		INIT(99, "The job is not ran"), Ko1(1, "Errors occurred during processing"),
		Ko2(2, "An unexpected error occurred during loading or running of the job"),
		Ko7(7, "The job couldn't be loaded from XML or the Repository"),
		Ko8(8, "Error loading steps or plugins (error in loading one of the plugins mostly)"),
		Ko9(9, "Command line usage printing"), Ok(0, "The job ran without a problem");

		private String description;
		private int returnCode;

		ReturnCode(final int returnCode, final String description) {
			this.returnCode = returnCode;
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

		public int getReturnCode() {
			return returnCode;
		}

		public void setDescription(final String description) {
			this.description = description;
		}

		public void setReturnCode(final int returnCode) {
			this.returnCode = returnCode;
		}

	}

	private String errorMessage = null;
	private long executionTimeMs = 0;
	private ReturnCode returnCode = ReturnCode.INIT;

	public String getErrorMessage() {
		return errorMessage;
	}

	public long getExecutionTimeMs() {
		return executionTimeMs;
	}

	public ReturnCode getReturnCode() {
		return returnCode;
	}

	public void setErrorMessage(final String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setExecutionTimeMs(final long executionTimeMs) {
		this.executionTimeMs = executionTimeMs;
	}

	public void setReturnCode(final ReturnCode returnCode) {
		this.returnCode = returnCode;
	}

	@Override
	public String toString() {
		return "KettleJobStatus [errorMessage=" + errorMessage + ", executionTimeMs=" + executionTimeMs
				+ ", returnCode=" + returnCode + "]";
	}

}
