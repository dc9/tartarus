package server.util;

import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import server.Server;

/**
 *
 * Logger.java
 * Created on: Feb 12, 2013, Last Modified on: Feb 12, 2013
 * @author deathschaos9
 *
 */
public class Logger extends PrintStream {
	
	public static Logger logger;
	private DateFormat dateFormat = new SimpleDateFormat();
	private Date cachedDate = new Date();
	private SimpleTimer refreshTimer = new SimpleTimer();

	public static Logger getLogger() {
		if (Server.outLogger == null)
			Server.outLogger = new Logger(System.out);
		return Server.outLogger;
	}
	
	public static Logger getError() {
		if (Server.errLogger == null)
			Server.errLogger = new Logger(System.out);
		return Server.errLogger;
	}

	/**
	 * @param out
	 */
	public Logger(OutputStream out) {
		super(out);
		
	}
	
	@Override
	public void println(String s) {
		super.println("[" + getDate() + "]: " + s);
	}
	
	public void log(Class<?> fromClass, String s) {
		super.print("[" + getDate() + "--" + fromClass.getSimpleName() + "]: " + s + "\n");
	}

	/**
	 * @return
	 */
	private String getDate() {
		if (refreshTimer.elapsed() > 1000) {
			refreshTimer.reset();
			cachedDate = new Date();
		}
		return dateFormat.format(cachedDate);
	}

}
