package net.rossonet.operator.model;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogUtils {

	public static String stackTraceToString(final Throwable throwable) {
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		String response = null;
		if (throwable.getCause() != null && throwable.getCause().getMessage() != null) {
			response = " [M] " + throwable.getCause().getMessage() + " -> " + sw.toString();
		} else {
			response = " [M] " + sw.toString();
		}
		return response;
	}

	public static String stackTraceToString(final Throwable throwable, final int numLines) {
		try {
			final List<String> lines = Arrays.asList(stackTraceToString(throwable).split("\n"));
			final ArrayList<String> al = new ArrayList<>(lines.subList(0, Math.min(lines.size(), numLines)));
			final StringBuilder returnString = new StringBuilder();
			for (final String line : al) {
				returnString.append(line + "\n");
			}
			return returnString.toString();
		} catch (final Exception n) {
			return stackTraceToString(throwable);
		}
	}

	public static String threadStackTrace() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Printing runtime stack trace:\n");
		final StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		for (int i = 1; i < elements.length; i++) {
			final StackTraceElement s = elements[i];
			sb.append("\tat " + s.getClassName() + "." + s.getMethodName() + "(" + s.getFileName() + ":"
					+ s.getLineNumber() + ")\n");
		}
		return sb.toString();
	}

	private LogUtils() {
		throw new UnsupportedOperationException("Just for static usage");
	}

}
