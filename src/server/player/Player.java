package server.player;

import java.nio.channels.SocketChannel;

import server.net.ClientChannel;
import server.net.protocol.packets.PacketBuilder;
import server.player.updating.Location;
import server.player.updating.PlayerUpdating;
import server.util.ISAACCipher;

/**
 * 
 * Player.java Created on: Feb 14, 2013, Last Modified on: Feb 14, 2013
 * 
 * @author deathschaos9
 * 
 */
public class Player {

	public String username;
	public String password;
	public ClientChannel channelContext;
	public int userId;

	/**
	 * @param username
	 * @param password
	 * @param channelContext
	 */
	public Player(String username, String password,
			ClientChannel channelContext, int playerIndex) {
		this.username = username;
		this.password = password;
		this.channelContext = channelContext;
		this.userId = playerIndex;
	}

	public PlayerUpdating playerUpdating;
	public Location location;

	/**
	 * Initialize the player
	 */
	public void initialize() {
		sendInitializationPacket();
		location = new Location(this);
		playerUpdating = new PlayerUpdating(this);
	}

	/**
	 * 
	 */
	private void sendInitializationPacket() {
		PacketBuilder out = PacketBuilder.allocate(4);
		out.createFrame(249, getEncryption());
		out.putByteA(1);
		out.putLEShortA(userId);
		out.sendTo(getChannel());
		PacketBuilder out1 = PacketBuilder.allocate(2);
		out1.createFrame(107, getEncryption());
		out1.sendTo(getChannel());
	}

	/**
	 * @return the players socket channel
	 */
	public SocketChannel getChannel() {
		return channelContext.clientSocket;
	}
	
	public ISAACCipher getEncryption() {
		return channelContext.encryption;
	}
	public ISAACCipher getDecryption() {
		return channelContext.decryption;
	}

	/**
	 * @return
	 */
	public Location getLocation() {
		return location;
	}

}
