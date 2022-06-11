package net.rossonet.operator.model.simple.transformation;

public class KettleTransformationStatus {
	private Integer readyReplicas = 0;

	public Integer getReadyReplicas() {
		return readyReplicas;
	}

	public void setReadyReplicas(final Integer readyReplicas) {
		this.readyReplicas = readyReplicas;
	}

}
