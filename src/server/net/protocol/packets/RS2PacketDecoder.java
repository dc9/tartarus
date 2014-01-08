package server.net.protocol.packets;

import java.io.IOException;
import java.nio.ByteBuffer;

import server.Configuration;
import server.net.Decoder;
import server.net.ClientChannel;
import server.util.Logger;

/**
 * 
 * RS2PacketDecoder.java Created on: Feb 14, 2013, Last Modified on: Feb 14,
 * 2013
 * 
 * @author deathschaos9
 * 
 */
public class RS2PacketDecoder implements Decoder {

	public int packet = -1;
	public int packetLength = -1;

	public ByteBuffer buffer = ByteBuffer.allocateDirect(256);

	@Override
	public void decode(ClientChannel channel) throws IOException {
		channel.clientSocket.read(buffer);
		buffer.flip();
		if (!buffer.hasRemaining()) {
			buffer.clear();
			return;
		}
		do {
			if (packet == -1) {
				packet = buffer.get() & 0xFF;
				packet = packet - channel.decryption.getNextValue() & 0xff;
				packetLength = Configuration.PACKET_SIZES[packet];
			}
			if (variableSizedPacket(packet)) {
				if (!buffer.hasRemaining()) {
					buffer.clear();
					return;
				}
				packetLength = buffer.get() & 0xFF;
			}
			if (buffer.remaining() < packetLength) {
				buffer.compact();
				return;
			}
			Logger.getLogger().println("Packet: " + packet);
			Packet p = Packet.buildPacket(buffer, packet, packetLength);
			PacketHandler.packetList[packet].handlePacket(channel, p);
			packet = packetLength = -1;
			buffer.compact().flip();
		} while (buffer.hasRemaining());
		buffer.clear();
	}

	/**
	 * @param packet
	 * @return
	 */
	private boolean variableSizedPacket(int packet) {
		return Configuration.PACKET_SIZES[packet] == -1;
	}

}
