package edu.brown.cs032.atreil.catan.networking.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.brown.cs032.atreil.catan.networking.Packet;
import edu.brown.cs032.sbreslow.catan.gui.board.Board;
import edu.brown.cs032.tmercuri.catan.logic.Player;
import edu.brown.cs032.tmercuri.catan.logic.move.Move;

/**
 * This class groups all of the clients together so that the server can easily communicate
 * with them.
 * @author atreil
 *
 */
public class ClientPool {

	private final Map<String, ClientManager> _clients; //keeps track of clients
	private CatanServer _server; //use to pass moves
	
	/**
	 * Initializes a new ClientPool with a given CatanServer.
	 * @param server The server to give moves to
	 */
	public ClientPool(CatanServer server){
		_clients = Collections.synchronizedMap(new HashMap<String, ClientManager>());
		this._server = server;
	}
	
	/**
	 * Returns a list of players
	 * @return list of players connected
	 */
	public List<Player> getPlayerList(){
		ArrayList<Player> players = new ArrayList<Player>();
		
		for(ClientManager client : Collections.synchronizedCollection(_clients.values())){
			players.add(client.getPlayer());
		}
		
		return players;
	}
	
	/**
	 * Adds a new client to the pool.
	 * @param name Unique name of the client. If the name is already used, the old one is replaced and returned
	 * @param clientManager The client to add
	 * @return The old ClientManager that was associated with the name, or null if no ClientManager existed
	 */
	public synchronized ClientManager addClient(String name, ClientManager clientManager){
		synchronized(_clients){
			 return _clients.put(name, clientManager);
		}
	}
	
	/**
	 * Returns the number of clients connected
	 * @return The number of clients currently connected
	 */
	public int getNumConnected(){
		synchronized (_clients) {
			return _clients.size();
		}
	}
	
	/**
	 * Returns the name of all the connected players in no particular order
	 * @return A list of strings representing the connected players
	 */
	public List<String> getPlayerNames(){
		LinkedList<String> list = new LinkedList<>();
		
		for(ClientManager client : Collections.synchronizedCollection(_clients.values())){
			list.add(client.getPlayerName());
		}
		
		return list;
	}
	
	/**
	 * Sends a packet to the client with the given name. If the name does not exist, an exception is thrown.
	 * @param name The name of the client
	 * @param packet The packet
	 * @throws IOException If anything goes wrong with the IO
	 * @throws IllegalArgumentException If the key does not exist in the table
	 */
	public void send(String name, Packet packet) throws IOException, IllegalArgumentException{
		
		//check if the key is in the table
		if(!_clients.containsKey(name))
			throw new IllegalArgumentException(String.format("Key not found: %d", name));
		
		ClientManager mngr = _clients.get(name);
		
		mngr.send(packet);
	}
	
	/**
	 * Sends a message to all of the clients from a given client manager
	 * @param message The message to send
	 * @param sender The client that sent the message
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	public synchronized void sendAllChat(String message, String sender) throws IllegalArgumentException, IOException{
		String color = message.split(" ")[0];
		String actualMessage = message.substring(message.indexOf(" "));
		String toSend = String.format("%s (%s): %s", sender, color, actualMessage);
		
		synchronized(_clients){
			for(ClientManager mngr : Collections.synchronizedCollection(_clients.values())){
				mngr.send(new Packet(Packet.MESSAGE, toSend, 0));
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
	 * @throws IOException 
	 */
	public synchronized void sendChat(String player, String message, String sender) throws IllegalArgumentException, IOException{
		
		//check player exists
		synchronized(_clients){
			if(!_clients.containsKey(player)){
				sendChat(sender, String.format("server No player exists with that name: %s\n", player), "Server");
				return;
			}
			
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
			_clients.get(player).send(new Packet(Packet.MESSAGE, String.format("%s (%s): %s", sender, color, message), 0));
			
			if(!sender.equalsIgnoreCase("Server"))
				_clients.get(sender).send(new Packet(Packet.MESSAGE, String.format("%s (%s) (%s): %s", sender, color, player, message), 0));
			
			System.out.println("Sending " + message);
		}
	}
	
	/**
	 * Broadcasts a packets to all of the clients
	 * @param packet The packet to send to the clients
	 * @throws IOException If anything goes wrong with the socket connection
	 */
	public void broadcast(Packet packet) throws IOException{
		synchronized(_clients){
			for(ClientManager mngr : Collections.synchronizedCollection(_clients.values())){
				
				if(packet.getType() == Packet.BOARD){
					Board board = (Board) packet.getObject();
					System.out.println("SENDING TO CLIENT " + mngr.getName() + " " + board.getNodes()[90].getVP() + " PACKET " + packet.getUID());
				}
				
				try{
					mngr.send(packet);
				} catch(IOException e){
					addUpdate(String.format("Client %s does not exist; they probably disconnected: %s", mngr.getPlayerName(), e.getMessage()));
				}
			}
		}
	}
	
	/**
	 * Gives a Move to the Server to be processed by the Referee
	 * @param move The Move to process
	 */
	public void addMove(Move move){
		_server.addMove(move);
	}
	
	/**
	 * Remove a client from the pool. Only do this if you intend to clean up
	 * that client later.
	 * 
	 * @param client to remove
	 * @return the clientmanager class, if the client was in the pool, and null if they were not there.
	 */
	public synchronized ClientManager remove(ClientManager client) {
		synchronized(_clients){
			ClientManager toRemove = _clients.get(client.getName());
			
			if(client == toRemove)
				return _clients.remove(client.getName());
			else
				return null;
		}
	}
	
	/**
	 * Kills all of the clients by freeing up their resources
	 */
	public void killAll(){
		
		for(ClientManager client : Collections.synchronizedCollection(_clients.values())){
			client.kill();
		}
		

		synchronized(_clients){
			_clients.clear();
		}
	}
	
	/**
	 * Sends a game over to all of the clients. Used when one of the
	 * clients disconnects
	 * @param message The message to display to the clients
	 */
	public void sendGameOver(String message){
		_server.sendGameOver(message);
	}
	/**
	 * Returns an array of players currently connected
	 * @return Array of players
	 */
	public Player[] getPlayers(){
		Player[] toReturn = new Player[_clients.size()];
		int i = 0;
		
		for(ClientManager client : Collections.synchronizedCollection(_clients.values()))
			toReturn[i++] = client.getPlayer(); 
		
		return toReturn;
	}
	
	/**
	 * Returns whether or not a client with the given key already exists.
	 * @param key The key to check
	 * @return True, if a client already exists with the key, and false otherwise
	 */
	public boolean containsKey(String key){
		return _clients.containsKey(key);
	}
	
	/**
	 * Adds an update message to the server
	 * @param message The update
	 */
	public synchronized void addUpdate(String message){
		synchronized(_server){
			_server.addUpdate(message);
		}
	}
	
	/**
	 * Returns if the server is running. This method
	 * is thread safe
	 * @return true, if the server is running and false otherwise
	 */
	public synchronized boolean getIsRunning(){
		return _server.getIsRunning();
	}
	
	/**
	 * Returns if the server is in the lobby. This method
	 * is thread safe
	 * @return true, if the server is in the lobby and false otherwise
	 */
	public synchronized boolean getInLobby(){
		return _server.getInLobby();
	}
	
	/**
	 * Returns if the server is in the game. This method
	 * is thread safe
	 * @return true, if the server is in the game and false otherwise
	 */
	public synchronized boolean getInGame(){
		return _server.getInGame();
	}
	
	/**
	 * Gives 10 of each resource to a given player
	 * @param playerName The player to give the resource to
	 */
	public void foodler(String playerName){
		_server.foodler(playerName);
	}
}
