package net.rossonet.operator.model.repository;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.rossonet.operator.model.StaticUtils;
import net.rossonet.operator.model.simple.KettleWork;

public class KettleRepositorySpec {

	@JsonPropertyDescription("Database name")
	private String databaseName = "kettle";

	@JsonPropertyDescription("the Docker image default is " + KettleWork.DEFAULT_REPOSITORY_IMAGE)
	private String image = KettleWork.DEFAULT_REPOSITORY_IMAGE;

	/** Database password */
	@JsonPropertyDescription("Database password")
	private String password = "password";

	/** Repository password */
	@JsonPropertyDescription("Repository password")
	private String repositoryPassword = null;

	/** Repository certificate */
	@JsonPropertyDescription("Repository ssh certificate")
	private String repositorySshCertificate = null;

	@JsonPropertyDescription("repository url to get the data. Should start with the protocol '" + StaticUtils.GIT_SSH
			+ "', '" + StaticUtils.GIT_HTTP + "', '" + StaticUtils.GIT_HTTPS + "', '" + StaticUtils.S3 + "', '"
			+ StaticUtils.HTTP + "', '" + StaticUtils.HTTPS + "' or '" + StaticUtils.FILE + "'")
	private String repositoryUrl = null;

	/** Repository username */
	@JsonPropertyDescription("Repository username")
	private String repositoryUsername = null;

	/**
	 * synchronize schedule notation
	 */
	@JsonPropertyDescription("synchronize local disk to remote repository delay in seconds")
	private Long scheduleDelaySeconds = null;

	/** Database username */
	@JsonPropertyDescription("Database username")
	private String username = "rossonet";

	public String getDatabaseName() {
		return databaseName;
	}

	public String getImage() {
		return image;
	}

	public String getPassword() {
		return password;
	}

	public String getRepositoryPassword() {
		return repositoryPassword;
	}

	public String getRepositorySshCertificate() {
		return repositorySshCertificate;
	}

	public String getRepositoryUrl() {
		return repositoryUrl;
	}

	public String getRepositoryUsername() {
		return repositoryUsername;
	}

	public Long getScheduleDelaySeconds() {
		return scheduleDelaySeconds;
	}

	public String getUsername() {
		return username;
	}

	public void setDatabaseName(final String databaseName) {
		this.databaseName = databaseName;
	}

	public void setImage(final String image) {
		this.image = image;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setRepositoryPassword(final String repositoryPassword) {
		this.repositoryPassword = repositoryPassword;
	}

	public void setRepositorySshCertificate(final String repositorySshCertificate) {
		this.repositorySshCertificate = repositorySshCertificate;
	}

	public void setRepositoryUrl(final String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}

	public void setRepositoryUsername(final String repositoryUsername) {
		this.repositoryUsername = repositoryUsername;
	}

	public void setScheduleDelaySeconds(final Long scheduleDelaySeconds) {
		this.scheduleDelaySeconds = scheduleDelaySeconds;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("KettleRepositorySpec [");
		if (databaseName != null) {
			builder.append("databaseName=");
			builder.append(databaseName);
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
			builder.append(", ");
		}
		if (repositoryUsername != null) {
			builder.append("repositoryUsername=");
			builder.append(repositoryUsername);
			builder.append(", ");
		}
		if (repositorySshCertificate != null) {
			builder.append("repositorySshCertificate=");
			builder.append(repositorySshCertificate);
			builder.append(", ");
		}
		if (scheduleDelaySeconds != null) {
			builder.append("scheduleDelaySeconds=");
			builder.append(scheduleDelaySeconds);
			builder.append(", ");
		}
		if (username != null) {
			builder.append("username=");
			builder.append(username);
		}
		builder.append("]");
		return builder.toString();
	}

}
