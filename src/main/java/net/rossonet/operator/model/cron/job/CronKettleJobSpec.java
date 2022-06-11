package net.rossonet.operator.model.cron.job;

import net.rossonet.operator.model.simple.job.KettleJobSpec;

public class CronKettleJobSpec extends KettleJobSpec {

	private String schedule = null;

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(final String schedule) {
		this.schedule = schedule;
	}

	@Override
	public String toString() {
		return "CronKettleJobSpec [schedule=" + schedule + ", getDir()=" + getDir() + ", getExport()=" + getExport()
				+ ", getFile()=" + getFile() + ", getImage()=" + getImage() + ", getJob()=" + getJob() + ", getLevel()="
				+ getLevel() + ", getListdir()=" + getListdir() + ", getListjob()=" + getListjob() + ", getListrep()="
				+ getListrep() + ", getLogfile()=" + getLogfile() + ", getNorep()=" + getNorep() + ", getParam()="
				+ getParam() + ", getRep()=" + getRep() + ", getUser()=" + getUser() + ", getVersion()=" + getVersion()
				+ "]";
	}

}
