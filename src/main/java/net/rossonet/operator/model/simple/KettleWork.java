package net.rossonet.operator.model.simple;

import io.fabric8.kubernetes.api.model.HasMetadata;

public interface KettleWork extends HasMetadata {

	/**
	 * kettle container image
	 */
	public final String DEFAULT_IMAGE = "rossonet/kettle-runner:latest";

	/**
	 * • Set to true to suppress warnings. • Leave this option empty to view
	 * warnings. Option to suppress warnings that the libwebkitgtk file is not
	 * installed when launching the PDI client. You can:
	 */
	public String FILTER_GTK_WARNINGS = "";
	/**
	 * The directory where the PDI client is installed. KETTLE_REPOSITORY The
	 * repository that Kettle connects to when it starts.
	 */
	public String KETTLE_DIR = "";
	/**
	 * Option identifying the user's home directory. The directory contains
	 * configuration files, which vary depending on the user who is logged on. You
	 * can use the variable to change the location of the files normally in the
	 * <user home>.kettle directory or to specify the home directory for all users
	 * on a machine.
	 */
	public String KETTLE_HOME = "";
	/**
	 * Option used to change the Simple JNDI path, which is the directory that
	 * contains the jdbc.properties file.
	 */
	public String KETTLE_JNDI_ROOT = "";
	/**
	 * Option to limit the log size of transformations and jobs that do not have the
	 * log size limit property.
	 */
	public String KETTLE_LOG_SIZE_LIMIT = "";
	/**
	 * Value that is passed as the -Djava.library.path Java parameter.
	 */
	public String LIBPATH = "";
	/**
	 * Option to pass additional Java arguments when running Kettle. For example,
	 * you can set an option to Increase the
	 */
	public String PENTAHO_DI_JAVA_OPTIONS = "";
	/**
	 * • Set to true to suppress warnings. • Leave this option empty to view
	 * warnings.
	 */
	public String SKIP_WEBKITGTK_CHECK = "";

}
