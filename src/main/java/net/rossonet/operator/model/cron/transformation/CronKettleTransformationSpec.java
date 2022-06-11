package net.rossonet.operator.model.cron.transformation;

import java.util.Arrays;

import net.rossonet.operator.model.simple.transformation.KettleTransformationSpec;

public class CronKettleTransformationSpec extends KettleTransformationSpec {

	private String schedule = null;

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(final String schedule) {
		this.schedule = schedule;
	}

	@Override
	public String toString() {
		return "CronKettleTransformationSpec [schedule=" + schedule + ", getImage()=" + getImage() + ", getParam()="
				+ Arrays.toString(getParam()) + ", getDir()=" + getDir() + ", getExprep()=" + getExprep()
				+ ", getFile()=" + getFile() + ", getLevel()=" + getLevel() + ", getListdir()=" + getListdir()
				+ ", getListrep()=" + getListrep() + ", getListtrans()=" + getListtrans() + ", getLogfile()="
				+ getLogfile() + ", getNorep()=" + getNorep() + ", getPass()=" + getPass() + ", getRep()=" + getRep()
				+ ", getSafemode()=" + getSafemode() + ", getTrans()=" + getTrans() + ", getUser()=" + getUser()
				+ ", getVersion()=" + getVersion() + "]";
	}

}
