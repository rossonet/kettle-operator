package net.rossonet.operator.model.cron.transformation;

public class CronKettleTransformationStatus {
	private Integer readyReplicas = 0;

	public Integer getReadyReplicas() {
		return readyReplicas;
	}

	public void setReadyReplicas(final Integer readyReplicas) {
		this.readyReplicas = readyReplicas;
	}

}
