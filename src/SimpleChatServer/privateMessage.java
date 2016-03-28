package SimpleChatServer;

import client.ChatClient1;
import client.ClientCommand;
import ocsf.server.ConnectionToClient;

//import java.util.ArrayList; 

public class privateMessage extends ServerCommand {

	public privateMessage(String str, EchoServer1 server)
	  {
	    super(str, server);    
	  }

	public void doCommand(){	 
		final String str = getStr(); 
	    int indexBlank = str.indexOf(' ');
	    final String receiver = str.substring(0, indexBlank);
	    final String message = str.substring(indexBlank+1);
		
		Thread[] clientThreadList = getServer().getClientConnections();
		for (int i=0; i<clientThreadList.length; i++)
	    {
	    	ConnectionToClient connection = (ConnectionToClient)clientThreadList[i];
	    	if (((String)connection.getInfo("id")).equals(receiver)){
	            try{
	                connection.sendToClient((String)connection.getInfo("id") + ">" + message);
	            }
	            
	            catch (Exception ex) {}
	        }
	    }
	}
	
=======
import ocsf.server.ConnectionToClient;

public class privateMessage extends ServerCommand {

    public privateMessage(String str, EchoServer1 server) {
        super(str, server);
    }

    public void doCommand() {
        final String receiver = getStr().split(" ")[0];
        final String message = getStr().split(" ")[1];

        Thread[] clientThreadList = getServer().getClientConnections();
        for (Thread thr : clientThreadList){ // i = 0; i < clientThreadList.length; i++) {
            ConnectionToClient connection = (ConnectionToClient) thr;
            try {
                if (connection.getInetAddress().getHostName().equals(receiver)) {
                    connection.sendToClient(message);
                }
            } catch (Exception e) {
            } 
        }
    }
}
