package net.rossonet.operator.model.ide;

import net.rossonet.operator.model.simple.KettleWork;

public class KettleIdeSpec {

	private final String image = KettleWork.DEFAULT_IDE_IMAGE;

	public String getImage() {
		return image;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("KettleRepositorySpec [");

		if (image != null) {
			builder.append("image=");
			builder.append(image);
			builder.append(", ");
		}

		builder.append("]");
		return builder.toString();
	}

}
