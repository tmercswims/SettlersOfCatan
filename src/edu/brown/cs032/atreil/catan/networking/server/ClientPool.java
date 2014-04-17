package edu.brown.cs032.atreil.catan.networking.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import edu.brown.cs032.atreil.catan.networking.Packet;
import edu.brown.cs032.tmercuri.catan.logic.move.Move;

/**
 * This class groups all of the clients together so that the server can easily communicate
 * with them.
 * @author atreil
 *
 */
public class ClientPool {

	private final HashMap<String, ClientManager> clients; //keeps track of clients
	private CatanServer _server; //use to pass moves
	
	/**
	 * Initializes a new ClientPool with a given CatanServer.
	 * @param server The server to give moves to
	 */
	public ClientPool(CatanServer server){
		clients = new HashMap<>();
		this._server = server;
	}
	
	/**
	 * Adds a new client to the pool.
	 * @param name Unique name of the client. If the name is already used, the old one is replaced and returned
	 * @param clientManager The client to add
	 * @return The old ClientManager that was associated with the name, or null if no ClientManager existed
	 */
	public synchronized ClientManager addClient(String name, ClientManager clientManager){
		synchronized(clients){
			 return clients.put(name, clientManager);
		}
	}
	
	/**
	 * Returns the number of clients connected
	 * @return The number of clients currently connected
	 */
	public int getNumConnected(){
		synchronized (clients) {
			return clients.size();
		}
	}
	
	/**
	 * Returns the name of all the connected players in no particular order
	 * @return A list of strings representing the connected players
	 */
	public List<String> getPlayerNames(){
		LinkedList<String> list = new LinkedList<>();
		
		for(ClientManager client : clients.values()){
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
		if(!clients.containsKey(name))
			throw new IllegalArgumentException(String.format("Key not found: %d", name));
		
		ClientManager mngr = clients.get(name);
		
		mngr.send(packet);
	}
	
	/**
	 * Broadcasts a packets to all of the clients
	 * @param packet The packet to send to the clients
	 * @throws IOException If anything goes wrong with the socket connection
	 */
	public void broadcast(Packet packet) throws IOException{
		for(ClientManager mngr : clients.values()){
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
}
