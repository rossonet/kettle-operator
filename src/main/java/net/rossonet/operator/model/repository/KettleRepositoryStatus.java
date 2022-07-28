package net.rossonet.operator.model.repository;

import java.util.ArrayList;
import java.util.List;

public class KettleRepositoryStatus {

	private List<String> messages = new ArrayList<>();

	private String returnCode = KettleRepositoryReconciler.RepositoryStatus.INIT.toString();

	private int totalJobs = 0;

	private int totalTransformations = 0;

	public List<String> getMessages() {
		return messages;
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

	public void setMessages(final List<String> messages) {
		this.messages = messages;
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
		if (messages != null) {
			builder.append("messages=");
			builder.append(messages);
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
