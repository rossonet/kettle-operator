package net.rossonet.operator.model.cron.transformation;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.ShortNames;
import io.fabric8.kubernetes.model.annotation.Version;
import net.rossonet.operator.model.simple.transformation.KettleTransformationSpec;
import net.rossonet.operator.model.simple.transformation.KettleTransformationStatus;

@Group("kettle.rossonet.net")
@Version("v1")
@ShortNames("kettlecrontran")
public class CronKettleTransformation extends CustomResource<KettleTransformationSpec, KettleTransformationStatus>
		implements Namespaced {
	private static final long serialVersionUID = 4345050362805262846L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}
