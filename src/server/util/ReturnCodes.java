package server.util;

/**
 *
 * ReturnCodes.java
 * Created on: Feb 16, 2013, Last Modified on: Feb 16, 2013
 * @author deathschaos9
 *
 */
public class ReturnCodes {

	/**
	 * Session is valid, continue with the login process
	 */
	public static final int CONTINUE_LOGIN = 0;
	
	/**
	 * Login is valid, username and password are correct
	 */
	public static final int VALID_LOGIN = 2;
	
	/**
	 * Invalid username or password
	 */
	public static final int INVALID_LOGIN = 3;
	
	/**
	 * Client is banned
	 */
	public static final int ACCOUNT_DISABLED = 4;
	
	/**
	 * Duplicate player
	 */
	public static final int ALREADY_ONLINE = 5;
	
	/**
	 * Client is out of date, update required
	 */
	public static final int SERVER_UPDATED = 6;
	
	/**
	 * World is full
	 */
	public static final int WORLD_FULL = 7;
	
	/**
	 * Login server is nulled/overflowed/full
	 */
	public static final int LOGIN_SERVER_OFFLINE = 8;
	
	/**
	 * Client has exceeded number of logins
	 */
	public static final int LOGIN_LIMIT = 9;
	
	/**
	 * Non-member connecting to a members world
	 */
	public static final int MEMBERS_WORLD = 12;
	
	/**
	 * Something went wrong, dunno what
	 */
	public static final int UNKNOWN_ERROR = 13;
	
	/**
	 * Server is updating, reject client
	 */
	public static final int UPDATE_IN_PROGRESS = 14;
	
	/**
	 * Too many failed logins
	 */
	public static final int ATTEMPTS_EXCEEDED = 16;
	
	/**
	 * Client is in a members only area
	 */
	public static final int MEMBERS_ONLY_AREA = 17;
	
	/**
	 * Transferring from one world to another
	 */
	public static final int WORLD_TRANSFER = 21;

	
}
