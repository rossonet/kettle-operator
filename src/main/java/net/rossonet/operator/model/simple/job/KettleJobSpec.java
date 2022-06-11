package net.rossonet.operator.model.simple.job;

import net.rossonet.operator.model.simple.KettleWork;

public class KettleJobSpec {

	/**
	 * The repository directory that contains the job, including the leading slash
	 */
	private String dir = null;

	/**
	 * Exports all linked resources of the specified job. The argument is the name
	 * of a ZIP file.
	 */
	private String export = null;
	/**
	 * If you are calling a local KJB file, this is the filename, including the path
	 * if it is not in the local directory
	 */
	private String file = null;
	private final String image = KettleWork.DEFAULT_IMAGE;
	/**
	 * The name of the job (as it appears in the repository) to launch
	 */
	private String job = null;
	/**
	 * The logging level (Basic, Detailed, Debug, Rowlevel, Error, Nothing)
	 */
	private String level = null;
	/**
	 * Lists the sub-directories within the specified repository directory
	 */
	private String listdir = null;
	/** Lists the jobs in the specified repository directory */
	private String listjob = null;
	/** Lists the available repositories */
	private String listrep = null;
	/** A local filename to write log output to */
	private String logfile = null;
	/**
	 * Prevents Kitchen from logging into a repository. If you have set the
	 * KETTLE_REPOSITORY, KETTLE_USER, and KETTLE_PASSWORD environment variables,
	 * then this option will enable you to prevent Kitchen from logging into the
	 * specified repository, assuming you would like to execute a local KTR file
	 * instead.
	 */
	private String norep = null;
	/** Set a named parameter in a name=value format. For */
	private String param = null;
	/** Repository password */
	private String pass = null;
	/**
	 * Enterprise or database repository name, if you are using one
	 */
	private String rep = null;
	/** Repository username */
	private String user = null;
	/** Shows the version, revision, and build date */
	private String version = null;

	public String getDir() {
		return dir;
	}

	public String getExport() {
		return export;
	}

	public String getFile() {
		return file;
	}

	public String getImage() {
		return image;
	}

	public String getJob() {
		return job;
	}

	public String getLevel() {
		return level;
	}

	public String getListdir() {
		return listdir;
	}

	public String getListjob() {
		return listjob;
	}

	public String getListrep() {
		return listrep;
	}

	public String getLogfile() {
		return logfile;
	}

	public String getNorep() {
		return norep;
	}

	public String getParam() {
		return param;
	}

	public String getPass() {
		return pass;
	}

	public String getRep() {
		return rep;
	}

	public String getUser() {
		return user;
	}

	public String getVersion() {
		return version;
	}

	public void setDir(final String dir) {
		this.dir = dir;
	}

	public void setExport(final String export) {
		this.export = export;
	}

	public void setFile(final String file) {
		this.file = file;
	}

	public void setJob(final String job) {
		this.job = job;
	}

	public void setLevel(final String level) {
		this.level = level;
	}

	public void setListdir(final String listdir) {
		this.listdir = listdir;
	}

	public void setListjob(final String listjob) {
		this.listjob = listjob;
	}

	public void setListrep(final String listrep) {
		this.listrep = listrep;
	}

	public void setLogfile(final String logfile) {
		this.logfile = logfile;
	}

	public void setNorep(final String norep) {
		this.norep = norep;
	}

	public void setParam(final String param) {
		this.param = param;
	}

	public void setPass(final String pass) {
		this.pass = pass;
	}

	public void setRep(final String rep) {
		this.rep = rep;
	}

	public void setUser(final String user) {
		this.user = user;
	}

	public void setVersion(final String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "KettleJobSpec [image=" + image + ", rep=" + rep + ", user=" + user + ", job=" + job + ", dir=" + dir
				+ ", file=" + file + ", level=" + level + ", logfile=" + logfile + ", listdir=" + listdir + ", listjob="
				+ listjob + ", listrep=" + listrep + ", export=" + export + ", norep=" + norep + ", version=" + version
				+ ", param=" + param + "]";
	}

}
