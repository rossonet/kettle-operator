package net.rossonet.operator.model.cron.job;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.rossonet.operator.model.simple.KettleWork;

public class CronKettleJobSpec {

	/**
	 * The repository directory that contains the job, including the leading slash
	 */
	@JsonPropertyDescription("The repository directory that contains the job, including the leading slash")
	@NotNull
	private String dir = null;

	/**
	 * Exports all linked resources of the specified job. The argument is the name
	 * of a ZIP file.
	 */
	@JsonPropertyDescription("Exports all linked resources of the specified job. The argument is the name of a ZIP file.")
	private String export = null;
	/**
	 * If you are calling a local KJB file, this is the filename, including the path
	 * if it is not in the local directory
	 */
	@JsonPropertyDescription("If you are calling a local KJB file, this is the filename, including the path if it is not in the local directory")
	private String file = null;
	@JsonPropertyDescription("the Docker image default is " + KettleWork.DEFAULT_IMAGE)
	private final String image = KettleWork.DEFAULT_IMAGE;
	/**
	 * The name of the job (as it appears in the repository) to launch
	 */
	@JsonPropertyDescription("The name of the job (as it appears in the repository) to launch")
	private String job = null;
	/**
	 * The logging level (Basic, Detailed, Debug, Rowlevel, Error, Nothing)
	 */
	@JsonPropertyDescription("The logging level (Basic, Detailed, Debug, Rowlevel, Error, Nothing)")
	private String level = null;
	/**
	 * Lists the sub-directories within the specified repository directory
	 */
	@JsonPropertyDescription("Lists the sub-directories within the specified repository directory")
	private String listdir = null;
	/** Lists the jobs in the specified repository directory */
	@JsonPropertyDescription("Lists the jobs in the specified repository directory")
	private String listjob = null;
	/** Lists the available repositories */
	@JsonPropertyDescription("Lists the available repositories")
	private String listrep = null;
	/** A local filename to write log output to */
	@JsonPropertyDescription("A local filename to write log output to")
	private String logfile = null;
	/**
	 * Prevents Kitchen from logging into a repository. If you have set the
	 * KETTLE_REPOSITORY, KETTLE_USER, and KETTLE_PASSWORD environment variables,
	 * then this option will enable you to prevent Kitchen from logging into the
	 * specified repository, assuming you would like to execute a local KTR file
	 * instead.
	 */
	@JsonPropertyDescription("Prevents Kitchen from logging into a repository. If you have set the KETTLE_REPOSITORY, KETTLE_USER, and KETTLE_PASSWORD environment variables, then this option will enable you to prevent Kitchen from logging into the specified repository, assuming you would like to execute a local KTR file instead.")
	private String norep = null;
	/** Set a named parameter in a name=value format. For */
	@JsonPropertyDescription("Set a named parameter in a name=value format. For")
	private String param = null;
	/** Repository password */
	@JsonPropertyDescription("Repository password")
	private String pass = null;
	/**
	 * Enterprise or database repository name, if you are using one
	 */
	@JsonPropertyDescription("Enterprise or database repository name, if you are using one")
	private String rep = null;
	/**
	 * schedule notation like crontab
	 */
	@JsonPropertyDescription("schedule notation like crontab")
	@NotNull
	private String schedule = null;
	/** Repository username */
	@JsonPropertyDescription("Repository username")
	private String user = null;
	/** Shows the version, revision, and build date */
	@JsonPropertyDescription("Shows the version, revision, and build date")
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

	public String getSchedule() {
		return schedule;
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

	public void setSchedule(final String schedule) {
		this.schedule = schedule;
	}

	public void setUser(final String user) {
		this.user = user;
	}

	public void setVersion(final String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "CronKettleJobSpec [schedule=" + schedule + ", dir=" + dir + ", export=" + export + ", file=" + file
				+ ", image=" + image + ", job=" + job + ", level=" + level + ", listdir=" + listdir + ", listjob="
				+ listjob + ", listrep=" + listrep + ", logfile=" + logfile + ", norep=" + norep + ", param=" + param
				+ ", rep=" + rep + ", user=" + user + ", version=" + version + "]";
	}

}
