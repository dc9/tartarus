package server.player;

import server.Configuration;
import server.net.ClientChannel;
import server.net.protocol.EmptyDecoder;
import server.net.protocol.packets.PacketBuilder;
import server.util.ReturnCodes;

/**
 *
 * PlayerManager.java
 * Created on: Feb 16, 2013, Last Modified on: Feb 16, 2013
 * @author deathschaos9
 *
 */
public class PlayerManager {
	
	private static Player[] players = new Player[Configuration.MAX_PLAYERS];

	/**
	 * @param username
	 * @param password
	 * @param channelContext
	 * @return
	 */
	public static Player registerPlayer(String username, String password,
			ClientChannel channelContext) {
		Player p = new Player(username, password, channelContext, getUnusedIndex());
		if (p.userId == -1) {
			revokeLogin(p, ReturnCodes.WORLD_FULL);
			return null;
		}
		players[p.userId] = p;
		return p;
	}

	/**
	 * @param username
	 * @param worldFull
	 */
	private static void revokeLogin(Player p, int response) {
		try {
			PacketBuilder output = PacketBuilder.allocate(3);
			output.putByte(response);
			output.putByte(0);
			output.putByte(0);
			output.sendTo(p.channelContext.clientSocket);
			p.channelContext.setDecoder(new EmptyDecoder());
			p = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @return the first unused player index or -1 if none are available
	 */
	private static int getUnusedIndex() {
		int index = -1;
		for (int i = 1; i < Configuration.MAX_PLAYERS; i++) {
			if (players[i] == null) {
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * This method is called every 600ms
	 */
	public static void tick() {
		for (Player p : players) {
			if (p == null)
				continue;
			p.playerUpdating.updatePlayer();
		}
	}

}
