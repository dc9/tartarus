package server.util;

/**
 *
 * SimpleTimer.java
 * Created on: Feb 12, 2013, Last Modified on: Feb 12, 2013
 * @author deathschaos9
 *
 */
public class SimpleTimer {
	private long cachedTime;

	public SimpleTimer() {
		reset();
	}

	public void reset() {
		cachedTime = System.currentTimeMillis();
	}

	public long elapsed() {
		return System.currentTimeMillis() - cachedTime;
	}
}