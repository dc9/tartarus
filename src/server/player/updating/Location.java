package server.player.updating;

import server.net.protocol.packets.PacketBuilder;
import server.player.Player;

/**
 * 
 * Movement.java Created on: Feb 16, 2013, Last Modified on: Feb 16, 2013
 * 
 * @author deathschaos9
 * 
 */
public class Location {
	
	public int destX, destY;
	public int posX, posY;
	public int mapRegionX, mapRegionY;
	public boolean isTeleporting;
	public boolean mapRegionChanged = true;
	
	Player p;

	public Location(Player p) {
		this.p = p;
		this.posX = 3200;
		this.posY = 3200;
		this.mapRegionX = (posX >> 3) - 6;
		this.mapRegionY = (posY >> 3) - 6;
	}

	/**
	 * Send the players current map region to the client
	 */
	public void sendMapRegion() {
		PacketBuilder out = PacketBuilder.allocate(5);
		out.createFrame(73, p.channelContext.encryption);
		out.putShortA(getMapRegionX());
		out.putShort(getMapRegionY());
		out.sendTo(p.getChannel());
		mapRegionChanged = false;
	}
	
	public int getMapRegionX() {
		return mapRegionX + 6;
	}
	public int getMapRegionY() {
		return mapRegionY + 6;
	}
}
