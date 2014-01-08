package server.net.protocol.packets;

import server.net.protocol.packets.packetlist.EmptyPacket;

/**
 *
 * PacketLoader.java
 * Created on: Feb 16, 2013, Last Modified on: Feb 16, 2013
 * @author deathschaos9
 *
 */
public class PacketLoader {

	/**
	 * 
	 */
	public static void initializePackets() {
		PacketHandler emptyPacket = new EmptyPacket();
		for (int i = 0; i < PacketHandler.packetList.length; i++)
			PacketHandler.packetList[i] = emptyPacket; // No packets handled yet
		
		
	}

}
