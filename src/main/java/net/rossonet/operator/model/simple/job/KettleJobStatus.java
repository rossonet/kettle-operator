package net.rossonet.operator.model.simple.job;

import java.util.ArrayList;
import java.util.List;

public class KettleJobStatus {

	private List<String> messages = new ArrayList<>();
	private String returnCode = "INIT";

	public List<String> getMessages() {
		return messages;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setMessages(final List<String> messages) {
		this.messages = messages;
	}

	public void setReturnCode(final String returnCode) {
		this.returnCode = returnCode;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("KettleJobStatus [");
		if (messages != null) {
			builder.append("messages=");
			builder.append(messages);
			builder.append(", ");
		}
		if (returnCode != null) {
			builder.append("returnCode=");
			builder.append(returnCode);
		}
		builder.append("]");
		return builder.toString();
	}

}
