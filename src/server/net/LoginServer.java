package server.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import server.Configuration;
import server.net.protocol.RS2LoginProtocolDecoder;
import server.util.Logger;

/**
 *
 * LoginServer.java
 * Created on: Feb 14, 2013, Last Modified on: Feb 14, 2013
 * @author deathschaos9
 *
 */
public class LoginServer implements Runnable {
	
	public Selector selector;
	
	public ServerSocketChannel channel;
	
	public LoginServer() {
		try {
			setSelector(Selector.open());
			setChannel(ServerSocketChannel.open());
			getChannel().configureBlocking(true);
			getChannel().socket().bind(new InetSocketAddress(Configuration.SERVER_PORT));
			Logger.getLogger().log(LoginServer.class, "Server bound to port " + Configuration.SERVER_PORT + ".");
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		while (getChannel().isOpen()) {
			try {
				SocketChannel clientSocket = getChannel().accept();
				Logger.getLogger().log(LoginServer.class, "Connection accepted from " + clientSocket.socket().getInetAddress().getHostName());
				clientSocket.configureBlocking(false);
				ClientChannel channelContext = new ClientChannel(clientSocket, new RS2LoginProtocolDecoder());
				for (int i = 0; i < 3; i++) { // Loop through all three different login stages
					try {
						channelContext.decoder().decode(channelContext);
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
						channel.close();
					}
				}
				if (clientSocket.isOpen())
					clientSocket.register(getSelector(), SelectionKey.OP_READ, channelContext);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sets the selector to <i>s</s>
	 * @param s - the selector
	 */
	public void setSelector(Selector s) {
		selector = s;
	}
	
	/**
	 * Gets the selector
	 * @return - the current selector
	 */
	public Selector getSelector() {
		return selector;
	}
	
	/**
	 * Sets the channel to <i>ch</i>
	 * @param ch - the channel
	 */
	public void setChannel(ServerSocketChannel ch) {
		channel = ch;
	}
	
	/**
	 * Gets the server socket channel
	 * @return - the server socket channel
	 */
	public ServerSocketChannel getChannel() {
		return channel;
	}

}
