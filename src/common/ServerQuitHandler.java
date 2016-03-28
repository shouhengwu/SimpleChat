package common;

<<<<<<< HEAD
import ocsf.server.*;

import java.io.*;

=======
>>>>>>> 6454f56404492813a20b500c1b581e3090cfd76c
public class ServerQuitHandler extends ServerMessageHandler{
	private String myId;

	  public ServerQuitHandler(String str)
	  {
	    myId = str;
	  }

	  /**
	   * This method logs the client in by saving its id String and
	   * sends a message to all clients that the new client has logged in
	   * If already logged in (id String has been set) a message is sent to the
	   * client and no other action is taken.
	   */
	  public void handleMessage()
	  {
	    System.out.println(myId + " has quited, connection ends");
	    getClient().setInfo("id", myId);
	  }

}
