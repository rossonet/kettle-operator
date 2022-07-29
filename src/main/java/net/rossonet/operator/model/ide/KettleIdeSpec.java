package net.rossonet.operator.model.ide;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.rossonet.operator.model.KettleWork;

public class KettleIdeSpec {

	@JsonPropertyDescription("Ingress hostname")
	private String host = null;

	@JsonPropertyDescription("Docker image")
	private String image = KettleWork.DEFAULT_IDE_IMAGE;

	@JsonPropertyDescription("Ingress path")
	private String path = "/";

	@JsonPropertyDescription("Repositories managed by this ide")
	private String[] repositories = null;

	@JsonPropertyDescription("XML with repositories configuration")
	private String xmlRepository = null;

	public String getHost() {
		return host;
	}

	public String getImage() {
		return image;
	}

	public String getPath() {
		return path;
	}

	public String[] getRepositories() {
		return repositories;
	}

	public String getXmlRepository() {
		return xmlRepository;
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

	public void setRepositories(final String[] repositories) {
		this.repositories = repositories;
	}

	public void setXmlRepository(final String xmlRepository) {
		this.xmlRepository = xmlRepository;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("KettleIdeSpec [");
		if (host != null) {
			builder.append("host=");
			builder.append(host);
			builder.append(", ");
		}
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
		if (xmlRepository != null) {
			builder.append("xmlRepository=");
			builder.append(xmlRepository);
			builder.append(", ");
		}
		if (repositories != null) {
			builder.append("repositories=");
			builder.append(Arrays.toString(repositories));
		}
		builder.append("]");
		return builder.toString();
	}

}
