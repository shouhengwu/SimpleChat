package SimpleChatServer;

import java.util.ArrayList;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;


public class Channel {
	
	//instance variables
	 /**
	   * The thread group associated with client threads. Each member of the
	   * thread group is a <code> ConnectionToClient </code>.
	   */
	private ArrayList<ConnectionToClient> clients;
	
	/**
	 * Name of the channel given by integer.
	 */
	private String channelName;
	
	/**
	 * Server to which the channel belongs
	 */
	private AbstractServer server;

	/**
	 * Blocks/exclusions in where to send messages 
     */
	
	public Channel(String channelName, AbstractServer thisServer, ArrayList<ConnectionToClient> myClients) {
		server = thisServer;
		this.channelName = channelName; 
		clients = myClients;
	}
	
	public Channel(String stringFromUser, AbstractServer thisServer) {
		channelName = setupChannelName(stringFromUser);
		String[] users = parseChannelUsers(stringFromUser);
		Thread[] allClients = thisServer.getClientConnections();
		clients = new ArrayList<ConnectionToClient>(users.length);
		setupChannelUsers(users, allClients);
	}
	
	/**
	 * @param users Array of type String of usernames 
	 * @param allClients Array of all ConnectionToClient clients
	 */
	private void setupChannelUsers(String[] users, Thread[] allClients) {
		for (String user1 : users) {
			System.out.println(user1);
		}
		here: for (int i = 0; i<users.length; i++) {
			for (int k =0; i < allClients.length; k++) {
				ConnectionToClient client = (ConnectionToClient) allClients[k];
				String username = (String) client.getInfo("id");
				String user = users[i];
				if (user == null) 
					break here; 
				else {
					if (user.equals(username)) {
					clients.add (client);
					break here;
					}
				}
			}	
		}
		for (ConnectionToClient client : clients) {
			System.out.println(client);
		}
	}
	
	/**
	 * Parses the string from the user for the name of the channel and returns it.
	 * @param str	from user
	 * @return names of the channel
	 */
	private String setupChannelName(String str) {
		return str.split(",")[0];
	}
	
	/**
	 * Takes the string from a user and parses it for the username of each user 
	 * within the string.
	 * @param stringFromUser comma-separated list of usernames
	 * @return	array of strings with usernames
	 */
	private String[] parseChannelUsers(String stringFromUser) { //(stringFromUser: channelName, user1, user2...
		int index = stringFromUser.indexOf(","); //start after first comma, string before first comma should be channel name
		String[] users = new String[15];
		int count = 0;
		while (index<stringFromUser.lastIndexOf(",") ) {
			int end = stringFromUser.indexOf(",", index+1);
			String user = stringFromUser.substring(index+1, end);
			users[count] = user;
			count++;
			index = end;
		}
		if (index != stringFromUser.length()) //must be one more user eg. user1, user2, user 3 ->user 3 left out of while loop
			users[count] = stringFromUser.substring(index+1, stringFromUser.length());
		return users;
	}
	
	public String getChannelName() {
		return channelName;
	}
	
	public int numClients(){
		return clients.size();
	}
	
	public AbstractServer getServer() {
		return server;
	}
	
	public Object[] enumerateClients() {
		return clients.toArray();
	}
}