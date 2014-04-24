package edu.brown.cs032.atreil.catan.chat.server;

import java.util.HashMap;

/**
 * This protected class contains references to the other clients.
 * @author Alex Treil
 *
 */
class ClientPool {

	private HashMap<String, ChatClientManager> _clients; //maps a players name to the clientmanager
	
	/**
	 * Initializes a new ClientPool
	 */
	public ClientPool(){
		this._clients = new HashMap<>();
	}
	
	/**
	 * Adds a client to the pool with a given player name. If the name already exists,
	 * then the old one is replaced
	 * @param name The player name attached to the ClientManager
	 * @param mngr The ClientManager
	 * @return The old ClientManager that the name replaces, or null if the name did not previously
	 * exist
	 */
	public synchronized ChatClientManager addClient(String name, ChatClientManager mngr){
		synchronized(_clients){
			return _clients.put(name, mngr);
		}
	}
	
	/**
	 * Sends a message to all of the clients from a given client manager
	 * @param message The message to send
	 * @param sender The client that sent the message
	 */
	public synchronized void sendAll(String message, String sender){
		String color = message.split(" ")[0];
		String[] split = message.split(" ");
		String actualMessage = message.substring(message.indexOf(" "));
		String toSend = String.format("%s (%s): %s", sender, color, actualMessage);
		
		synchronized(_clients){
			for(ChatClientManager mngr : _clients.values()){
				mngr.send(toSend);
			}
		}
	}
	
	/**
	 * Sends a private message to a specific player. If the player does not exist, an
	 * exception is thrown.
	 * @param player The name of the player to send the message to
	 * @param message The message to send
	 * @param sender The sender
	 * @throws IllegalArgumentException If no player exists with the given name
	 */
	public synchronized void send(String player, String message, String sender) throws IllegalArgumentException{
		
		//check player exists
		synchronized(_clients){
			if(!_clients.containsKey(player))
				throw new IllegalArgumentException("No player exists with that name");
			
			String color = message.split(" ")[0];
			StringBuilder sb = new StringBuilder();
			boolean first = true;
			for(String s: message.split(" ")){
				if(!first){
					sb.append(s+" ");
				}
				first = false;
			}
			message  = sb.toString().trim();
			_clients.get(player).send(String.format("%s (%s): %s", sender, color, message));
			if(!sender.equalsIgnoreCase("Server"))
				_clients.get(sender).send(String.format("%s (%s): %s", sender, color, message));
			System.out.println("Sending " + message);
		}
	}
	
	/**
	 * Returns the number of clients currently connected
	 * @return The number of clients currently connected
	 */
	public int getNumClients(){
		synchronized(_clients){
			return _clients.size();
		}
	}
	
	/**
	 * Removes the client from the pool
	 * @param mngr The client to remove
	 * @return The clientmanager, if it existed, and null if it was already removed
	 */
	public ChatClientManager remove(ChatClientManager mngr){
		synchronized(_clients){
			return _clients.remove(mngr.getPlayerName());
		}
	}
	
	/**
	 * Kills all of the clients
	 */
	public void kill(){
		synchronized(_clients){
			for(ChatClientManager mngr : _clients.values())
				mngr.kill();
		}
	}
}
