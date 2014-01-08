package server;

import java.io.IOException;

import server.net.LoginServer;
import server.net.protocol.packets.PacketLoader;
import server.util.Logger;
import server.util.ServerTicker;

/**
 * 
 * Server.java Created on: Feb 12, 2013, Last Modified on: Feb 12, 2013
 * 
 * @author deathschaos9
 * 
 */
public class Server {

	/**
	 * The instance of the class for default output
	 */
	public static Logger outLogger = new Logger(System.out);

	/**
	 * The instance of the class for error output
	 */
	public static Logger errLogger = new Logger(System.err);

	/**
	 * 
	 */
	public static LoginServer loginServer;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Logger.getLogger().log(Server.class, "Tartarus is starting... Please wait.");
		initializeServer();
		initializeLoginServer();
		new Thread(new ServerTicker()).start();

	}

	/**
	 * Creates a new instance of LoginServer while shutting down any currently
	 * running instances
	 */
	public static void initializeLoginServer() {
		if (loginServer != null) {
			try {
				loginServer.channel.close();
				loginServer.selector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		loginServer = new LoginServer();
	}

	/**
	 * Kill existing instances of classes and reinitializing them
	 */
	public static void initializeServer() {
		System.setOut(outLogger);
		System.setErr(errLogger);
		PacketLoader.initializePackets();

	}

}
