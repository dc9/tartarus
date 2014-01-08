package server.net.protocol.packets;

import server.net.ClientChannel;

/**
 *
 * PacketHandler.java
 * Created on: Feb 16, 2013, Last Modified on: Feb 16, 2013
 * @author deathschaos9
 *
 */
public interface PacketHandler {
	
	static PacketHandler[] packetList = new PacketHandler[256];

	/**
	 * @param channel
	 * @param p
	 */
	public void handlePacket(ClientChannel channel, Packet p);
		

}
