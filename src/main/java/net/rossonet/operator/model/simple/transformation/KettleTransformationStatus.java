package net.rossonet.operator.model.simple.transformation;

import net.rossonet.operator.model.simple.KettleStatus;

public class KettleTransformationStatus implements KettleStatus {

	public enum ReturnCode {

		INIT(99, "The trasformation is not ran"), Ko1(1, "Errors occurred during processing"),
		Ko2(2, "An unexpected error occurred during loading / running of the transformation"),
		Ko3(3, "Unable to prepare and initialize this transformation"),
		Ko7(7, "The transformation couldn't be loaded from XML or the Repository"),
		Ko8(8, "Error loading steps or plugins (error in loading one of the plugins mostly)"),
		Ko9(9, "Command line usage printing"), Ok(0, "The transformation ran without a problem");

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
		return "KettleTransformationStatus [errorMessage=" + errorMessage + ", executionTimeMs=" + executionTimeMs
				+ ", returnCode=" + returnCode + "]";
	}

}
