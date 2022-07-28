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

	@Override
	public String toString() {
		return "KettleIdeSpec [host=" + host + ", image=" + image + ", path=" + path + ", repositories="
				+ Arrays.toString(repositories) + "]";
	}

}
