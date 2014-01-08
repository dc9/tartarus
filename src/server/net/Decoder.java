package server.net;

import java.io.IOException;

/**
 *
 * Decoder.java
 * Created on: Feb 14, 2013, Last Modified on: Feb 14, 2013
 * @author deathschaos9
 *
 */
public interface Decoder {

	public void decode(ClientChannel channelContext) throws IOException;
}
