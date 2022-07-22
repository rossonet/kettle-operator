package net.rossonet.operator.model.ide;

import net.rossonet.operator.model.simple.KettleWork;

public class KettleIdeSpec {

	private String host = null;

	private String image = KettleWork.DEFAULT_IDE_IMAGE;

	private String path = "/";

	public String getHost() {
		return host;
	}

	public String getImage() {
		return image;
	}

	public String getPath() {
		return path;
	}

	public void setHost(final String host) {
		this.host = host;
	}

	public void setImage(final String image) {
		this.image = image;
	}

	public void setPath(final String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("KettleIdeSpec [");
		if (image != null) {
			builder.append("image=");
			builder.append(image);
			builder.append(", ");
		}
		if (path != null) {
			builder.append("path=");
			builder.append(path);
			builder.append(", ");
		}
		if (host != null) {
			builder.append("host=");
			builder.append(host);
		}
		builder.append("]");
		return builder.toString();
	}

}
