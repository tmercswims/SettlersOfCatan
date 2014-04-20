package edu.brown.cs032.atreil.catan.networking.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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

	private final HashMap<String, ClientManager> _clients; //keeps track of clients
	private CatanServer _server; //use to pass moves
	
	/**
	 * Initializes a new ClientPool with a given CatanServer.
	 * @param server The server to give moves to
	 */
	public ClientPool(CatanServer server){
		_clients = new HashMap<>();
		this._server = server;
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
		
		for(ClientManager client : _clients.values()){
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
	 * Broadcasts a packets to all of the clients
	 * @param packet The packet to send to the clients
	 * @throws IOException If anything goes wrong with the socket connection
	 */
	public void broadcast(Packet packet) throws IOException{
		for(ClientManager mngr : _clients.values()){
			
			if(packet.getType() == Packet.BOARD){
				Board board = (Board) packet.getObject();
				System.out.println("SENDING TO CLIENT " + mngr.getName() + " " + board.getNodes()[90].getVP() + " PACKET " + packet.getUID());
			}
			
			mngr.send(packet);
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
	 * @return true if the client was removed, false if they were not there.
	 */
	public synchronized ClientManager remove(ClientManager client) {
		synchronized(_clients){
			return _clients.remove(client);
		}
	}
	
	/**
	 * Kills all of the clients by freeing up their resources
	 */
	public void killAll(){
		for(ClientManager client : _clients.values()){
			try {
				client.kill();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//not much to do here
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Returns an array of players currently connected
	 * @return Array of players
	 */
	public Player[] getPlayers(){
		Player[] toReturn = new Player[_clients.size()];
		int i = 0;
		
		for(ClientManager client : _clients.values())
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
	public void addUpdate(String message){
		_server.addUpdate(message);
	}
}
