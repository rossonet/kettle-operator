package net.rossonet.operator.model.repository;

import net.rossonet.operator.model.simple.KettleWork;

public class KettleRepositorySpec {

	private final String image = KettleWork.DEFAULT_IMAGE;

	private String repositoryData = null; // il contenuto XML

	private String repositoryType = "database"; // database o filesystem

	private String repositoryUrl = null; // di tipo git-ssh:// s3:// git-http:// file://

	public String getImage() {
		return image;
	}

	public String getRepositoryData() {
		return repositoryData;
	}

	public String getRepositoryType() {
		return repositoryType;
	}

	public String getRepositoryUrl() {
		return repositoryUrl;
	}

	public void setRepositoryData(final String repositoryData) {
		this.repositoryData = repositoryData;
	}

	public void setRepositoryType(final String repositoryType) {
		this.repositoryType = repositoryType;
	}

	public void setRepositoryUrl(final String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("KettleRepositorySpec [");
		if (repositoryData != null) {
			builder.append("repositoryData=");
			builder.append(repositoryData);
			builder.append(", ");
		}
		if (repositoryType != null) {
			builder.append("repositoryType=");
			builder.append(repositoryType);
			builder.append(", ");
		}
		if (image != null) {
			builder.append("image=");
			builder.append(image);
			builder.append(", ");
		}
		if (repositoryUrl != null) {
			builder.append("repositoryUrl=");
			builder.append(repositoryUrl);
		}
		builder.append("]");
		return builder.toString();
	}

}
