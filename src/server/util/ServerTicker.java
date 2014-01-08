package server.util;

import server.Configuration;
import server.player.PlayerManager;

/**
 *
 * ServerTicker.java
 * Created on: Feb 16, 2013, Last Modified on: Feb 16, 2013
 * @author deathschaos9
 *
 */
public class ServerTicker implements Runnable {

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (true) {
			try {
				PlayerManager.tick();
				Thread.sleep(Configuration.SLEEP_TIME);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
