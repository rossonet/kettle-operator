package net.rossonet.operator.model.cron.job;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.ShortNames;
import io.fabric8.kubernetes.model.annotation.Version;
import net.rossonet.operator.model.simple.KettleWork;

@Group("kettle.rossonet.net")
@Version("v1")
@ShortNames("kcj")
public class CronKettleJob extends CustomResource<CronKettleJobSpec, CronKettleJobStatus>
		implements Namespaced, KettleWork {
	private static final long serialVersionUID = 8849061516845526594L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}
