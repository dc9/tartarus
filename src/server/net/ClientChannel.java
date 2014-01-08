package server.net;

import java.nio.channels.SocketChannel;

import server.player.Player;
import server.util.ISAACCipher;

/**
 *
 * ClientChannel.java
 * Created on: Feb 14, 2013, Last Modified on: Feb 14, 2013
 * @author deathschaos9
 *
 */
public class ClientChannel {

	public SocketChannel clientSocket;
	public ISAACCipher encryption;
	public ISAACCipher decryption;
	public Decoder decoder;
	public Player player;
	
	public ClientChannel(SocketChannel client, Decoder decoder) {
		this.clientSocket = client;
		this.decoder = decoder;
	}

	/**
	 * @return - the current decoder
	 */
	public Decoder decoder() {
		return decoder;
	}
	/**
	 * Sets the decoder to the supplied parameter
	 * @param decoder 
	 */
	public void setDecoder(Decoder decoder) {
		this.decoder = decoder;
	}
}
