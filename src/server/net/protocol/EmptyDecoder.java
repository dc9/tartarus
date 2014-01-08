package server.net.protocol;

import java.io.IOException;

import server.net.ClientChannel;
import server.net.Decoder;

/**
 *
 * EmptyDecoder.java
 * Created on: Feb 16, 2013, Last Modified on: Feb 16, 2013
 * @author deathschaos9
 *
 */
public class EmptyDecoder implements Decoder {

	@Override
	public void decode(ClientChannel channelContext) throws IOException {
		// Empty decoder to prevent closed channel exeptions
	}

}
