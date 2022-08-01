package net.rossonet.operator.model.ide;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.rossonet.operator.model.KettleWork;

public class KettleIdeSpec {

	@JsonPropertyDescription("Ingress hostname")
	private String host = null;

	@JsonPropertyDescription("Docker image")
	private String image = KettleWork.DEFAULT_IDE_IMAGE;

	@JsonPropertyDescription("Ingress path")
	private String path = "/";

	@JsonPropertyDescription("Ingress service port")
	private Integer servicePort = 80;

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

	public Integer getServicePort() {
		return servicePort;
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

	public void setServicePort(final Integer servicePort) {
		this.servicePort = servicePort;
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
		if (servicePort != null) {
			builder.append("servicePort=");
			builder.append(servicePort);
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
		}
		builder.append("]");
		return builder.toString();
	}

}
