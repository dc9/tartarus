package server.player.updating;

import server.player.Player;

/**
 *
 * PlayerUpdating.java
 * Created on: Feb 16, 2013, Last Modified on: Feb 16, 2013
 * @author deathschaos9
 *
 */
public class PlayerUpdating {
	
	Player p;
	
	public PlayerUpdating(Player p) {
		this.p = p;
	}
	
	public void updatePlayer() {
		if (p.getLocation().mapRegionChanged)
			p.getLocation().sendMapRegion();
		
		
	}

}
