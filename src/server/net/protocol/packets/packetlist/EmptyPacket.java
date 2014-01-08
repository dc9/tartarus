package server.net.protocol.packets.packetlist;

import server.net.ClientChannel;
import server.net.protocol.packets.Packet;
import server.net.protocol.packets.PacketHandler;
import server.util.Logger;

/**
 *
 * EmptyPacket.java
 * Created on: Feb 16, 2013, Last Modified on: Feb 16, 2013
 * @author deathschaos9
 *
 */
public class EmptyPacket implements PacketHandler {


	@Override
	public void handlePacket(ClientChannel channel, Packet p) {
		Logger.getLogger().println("Unhandled packet: " + p.opcode());
		
	}

}
