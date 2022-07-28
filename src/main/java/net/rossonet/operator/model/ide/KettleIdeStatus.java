package net.rossonet.operator.model.ide;

import java.util.ArrayList;
import java.util.List;

public class KettleIdeStatus {

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
		builder.append("KettleIdeStatus [");
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
