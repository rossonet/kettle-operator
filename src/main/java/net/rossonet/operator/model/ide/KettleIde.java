package net.rossonet.operator.model.ide;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.ShortNames;
import io.fabric8.kubernetes.model.annotation.Version;

@Group("kettle.rossonet.net")
@Version("v1")
@ShortNames("ki")
public class KettleIde extends CustomResource<KettleIdeSpec, KettleIdeStatus> implements Namespaced {

	private static final long serialVersionUID = -7921397456982072570L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}
