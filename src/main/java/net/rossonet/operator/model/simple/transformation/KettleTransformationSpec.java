package net.rossonet.operator.model.simple.transformation;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.rossonet.operator.model.KettleWork;

public class KettleTransformationSpec {

	/**
	 * The repository directory that contains the transformation, including the
	 * leading slash
	 */
	@JsonPropertyDescription("The repository directory that contains the transformation, including the leading slash")
	private String dir = null;

	/** Exports all repository objects to one XML file */
	@JsonPropertyDescription("Exports all repository objects to one XML file")
	private String exprep = null;

	/**
	 * If you are calling a local KTR file, this is the filename, including the path
	 * if it is not in the local directory
	 */
	@JsonPropertyDescription("If you are calling a local KTR file, this is the filename, including the path if it is not in the local directory")
	private String file = null;

	@JsonPropertyDescription("the Docker image default is " + KettleWork.DEFAULT_IMAGE)
	private String image = KettleWork.DEFAULT_IMAGE;

	/**
	 * The logging level (Basic, Detailed, Debug, Rowlevel, Error, Nothing)
	 */
	@JsonPropertyDescription("The logging level (Basic, Detailed, Debug, Rowlevel, Error, Nothing)")
	private String level = null;

	/** Lists the directories in the specified repository */
	@JsonPropertyDescription("Lists the directories in the specified repository")
	private String listdir = null;
	/** Lists the available repositories */
	@JsonPropertyDescription("Lists the available repositories")
	private String listrep = null;
	/**
	 * Lists the transformations in the specified repository directory
	 */
	@JsonPropertyDescription("Lists the transformations in the specified repository directory")
	private String listtrans = null;
	/** A local filename to write log output to */
	@JsonPropertyDescription("A local filename to write log output to")
	private String logfile = null;

	/**
	 * Prevents Pan from logging into a repository. If you have set the
	 * KETTLE_REPOSITORY, KETTLE_USER, and KETTLE_PASSWORD environment variables,
	 * then this option will enable you to prevent Pan from logging into the
	 * specified repository, assuming you would like to execute a local KTR file
	 * instead.
	 */
	@JsonPropertyDescription("Prevents Pan from logging into a repository. If you have set the KETTLE_REPOSITORY, KETTLE_USER, and KETTLE_PASSWORD environment variables, then this option will enable you to prevent Pan from logging into the specified repository, assuming you would like to execute a local KTR file instead.")
	private String norep = null;

	/**
	 * Set a named parameter in a name=value format. For example: -param:FOO=bar
	 */
	@JsonPropertyDescription("Set a named parameter in a name=value format. For example: -param:FOO=bar")
	private String[] param = null;
	/** Repository password */
	@JsonPropertyDescription("Repository password")
	private String pass = null;

	/**
	 * Enterprise or database repository name, if you are using one
	 */
	@JsonPropertyDescription("Enterprise or database repository name, if you are using one")
	private String rep = null;
	/** Runs in safe mode, which enables extra checking */
	@JsonPropertyDescription("Runs in safe mode, which enables extra checking")
	private String safemode = null;
	/**
	 * The name of the transformation (as it appears in the repository) to launch
	 */
	@JsonPropertyDescription("The name of the transformation (as it appears in the repository) to launch")
	private String trans = null;
	/** Repository username */
	@JsonPropertyDescription("Repository username")
	private String user = null;
	/** Shows the version, revision, and build date */
	@JsonPropertyDescription("Shows the version, revision, and build date")
	private String version = null;
	@JsonPropertyDescription("XML with repositories configuration")
	private String xmlRepository = null;

	public String getDir() {
		return dir;
	}

	public String getExprep() {
		return exprep;
	}

	public String getFile() {
		return file;
	}

	public String getImage() {
		return image;
	}

	public String getLevel() {
		return level;
	}

	public String getListdir() {
		return listdir;
	}

	public String getListrep() {
		return listrep;
	}

	public String getListtrans() {
		return listtrans;
	}

	public String getLogfile() {
		return logfile;
	}

	public String getNorep() {
		return norep;
	}

	public String[] getParam() {
		return param;
	}

	public String getPass() {
		return pass;
	}

	public String getRep() {
		return rep;
	}

	public String getSafemode() {
		return safemode;
	}

	public String getTrans() {
		return trans;
	}

	public String getUser() {
		return user;
	}

	public String getVersion() {
		return version;
	}

	public String getXmlRepository() {
		return xmlRepository;
	}

	public void setDir(final String dir) {
		this.dir = dir;
	}

	public void setExprep(final String exprep) {
		this.exprep = exprep;
	}

	public void setFile(final String file) {
		this.file = file;
	}

	public void setImage(final String image) {
		this.image = image;
	}

	public void setLevel(final String level) {
		this.level = level;
	}

	public void setListdir(final String listdir) {
		this.listdir = listdir;
	}

	public void setListrep(final String listrep) {
		this.listrep = listrep;
	}

	public void setListtrans(final String listtrans) {
		this.listtrans = listtrans;
	}

	public void setLogfile(final String logfile) {
		this.logfile = logfile;
	}

	public void setNorep(final String norep) {
		this.norep = norep;
	}

	public void setParam(final String[] param) {
		this.param = param;
	}

	public void setPass(final String pass) {
		this.pass = pass;
	}

	public void setRep(final String rep) {
		this.rep = rep;
	}

	public void setSafemode(final String safemode) {
		this.safemode = safemode;
	}

	public void setTrans(final String trans) {
		this.trans = trans;
	}

	public void setUser(final String user) {
		this.user = user;
	}

	public void setVersion(final String version) {
		this.version = version;
	}

	public void setXmlRepository(final String xmlRepository) {
		this.xmlRepository = xmlRepository;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("KettleTransformationSpec [");
		builder.append("dir=");
		builder.append(dir);
		builder.append(", exprep=");
		builder.append(exprep);
		builder.append(", file=");
		builder.append(file);
		builder.append(", image=");
		builder.append(image);
		builder.append(", level=");
		builder.append(level);
		builder.append(", listdir=");
		builder.append(listdir);
		builder.append(", listrep=");
		builder.append(listrep);
		builder.append(", listtrans=");
		builder.append(listtrans);
		builder.append(", logfile=");
		builder.append(logfile);
		builder.append(", norep=");
		builder.append(norep);
		builder.append(", param=");
		builder.append(Arrays.toString(param));
		builder.append(", rep=");
		builder.append(rep);
		builder.append(", safemode=");
		builder.append(safemode);
		builder.append(", trans=");
		builder.append(trans);
		builder.append(", user=");
		builder.append(user);
		builder.append(", version=");
		builder.append(version);
		builder.append(", xmlRepository=");
		builder.append(xmlRepository);
		builder.append("]");
		return builder.toString();
	}

}
