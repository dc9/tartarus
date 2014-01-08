package server.net.protocol;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import server.net.Decoder;
import server.net.ClientChannel;
import server.net.protocol.packets.PacketBuilder;
import server.net.protocol.packets.RS2PacketDecoder;
import server.player.Player;
import server.player.PlayerManager;
import server.util.ISAACCipher;
import server.util.Logger;

/**
 *
 * RS2LoginProtocolDecoder.java
 * Created on: Feb 14, 2013, Last Modified on: Feb 14, 2013
 * @author deathschaos9
 *
 */
public class RS2LoginProtocolDecoder implements Decoder {
	
	private ByteBuffer buffer = ByteBuffer.allocateDirect(256);
	
	private int loginStage = 1;

	private int rsaBlockSize;

	/* (non-Javadoc)
	 * @see server.net.Decoder#decode(server.net.ClientChannel)
	 */
	@Override
	public void decode(ClientChannel channelContext) throws IOException {
		SocketChannel client = channelContext.clientSocket;
		client.read(buffer);
		buffer.flip();
		switch (loginStage) {
		case 1:
		if (buffer.remaining() <= 1) {
			buffer.compact();
			break;
		}
		int opCode = buffer.get() & 0xFF;
		if (opCode != 14) {
			Logger.getError().log(RS2LoginProtocolDecoder.class, "Unexpected login opcode: " + opCode);
			client.close();
			break;
		}
		
		@SuppressWarnings("unused")
		int junk = buffer.get() & 0xFF;
		
		PacketBuilder output = PacketBuilder.allocate(17);
		output.putBytes(0, 17);
		output.sendTo(client);
		buffer.compact();
		loginStage = 2;
		break;
		
		case 2:
		if (buffer.remaining() <= 1) {
			buffer.compact();
			break;
		}
		int clientSendBack = buffer.get() & 0xFF;
		if (clientSendBack != 16 && clientSendBack != 18) {
			Logger.getError().log(RS2LoginProtocolDecoder.class, "Unexpected login request: " + clientSendBack);
			client.close();
			break;
		}
		rsaBlockSize = buffer.get() & 0xFF;
		loginStage = 3;
		case 3:
		if (buffer.remaining() < rsaBlockSize) {
			buffer.compact();
			break;
		}
		int magicNumber = buffer.get() & 0xFF;
		if (magicNumber != 255) {
			Logger.getError().log(RS2LoginProtocolDecoder.class, "Unexpected magic number: " + magicNumber);
			client.close();
			break;
		}
		int clientVersion = buffer.getShort();
		if (clientVersion != 317) {
			Logger.getError().log(RS2LoginProtocolDecoder.class, "Unexpected client version: " + clientVersion);
			client.close();
			break;
		}
		
		@SuppressWarnings("unused")
		int memoryVersion = buffer.get() & 0xFF;
		
		for (int i = 0; i <= 8; i++)
			buffer.getInt();
		
		int rsaEncodedBlock = buffer.get() & 0xFF;
		if (rsaEncodedBlock != rsaBlockSize - 41) {
			Logger.getError().log(RS2LoginProtocolDecoder.class, "RSA Mismatch..: " + rsaEncodedBlock + ", " + (rsaBlockSize - 42));
			client.close();
			break;
		}
		
		int rsaOpcode = buffer.get() & 0xFF;
		if (rsaOpcode != 10) {
			Logger.getError().log(RS2LoginProtocolDecoder.class, "RSA Opcode expected to be 10 but appears to be " + rsaOpcode);
			client.close();
			break;
		}
		
		long clientSeed = buffer.getLong();
		long serverSeed = buffer.getLong();
		
		int ISAACSeed[] = new int[4];
		ISAACSeed[0] = (int) (clientSeed >> 32);
		ISAACSeed[1] = (int) clientSeed;
		ISAACSeed[2] = (int) (serverSeed >> 32);
		ISAACSeed[3] = (int) serverSeed;
		
		channelContext.decryption = new ISAACCipher(ISAACSeed);
		for (int i = 0; i < 4; i++) {
			ISAACSeed[i] += 50;
		}
		channelContext.encryption = new ISAACCipher(ISAACSeed);
		
		@SuppressWarnings("unused")
		int UID = buffer.getInt();
		
		String username = readRS2String(buffer);
		String password = readRS2String(buffer);
		Player p = PlayerManager.registerPlayer(username, password, channelContext);
		if (p == null)
			return;
		//TODO: Register player via PlayerHandler or World or what have you
		//TODO: Load player profile, check password validity etc..
		int responseCode = 2; // Good login for now
		output = PacketBuilder.allocate(3);
		output.putByte(responseCode);
		output.putByte(2); // Player rights
		output.putByte(0); // Good login
		output.sendTo(client);
		channelContext.decoder = new RS2PacketDecoder();
		p.initialize();
		
		break;
		}       
	}
	public String readRS2String(ByteBuffer buffer) {
		byte data;
		StringBuilder builder = new StringBuilder();
		while ((data = buffer.get()) != 10) {
			builder.append((char) data);
		}
		return builder.toString();
	}

}
