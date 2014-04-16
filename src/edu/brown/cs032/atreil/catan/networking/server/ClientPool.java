package edu.brown.cs032.atreil.catan.networking.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import edu.brown.cs032.atreil.catan.networking.Packet;

/**
 * This class groups all of the clients together so that the server can easily communicate
 * with them.
 * @author atreil
 *
 */
public class ClientPool {

	private final HashMap<Integer, ClientManager> clients; //keeps track of clients
	
	/**
	 * Initializes a new ClientPool
	 */
	public ClientPool(){
		clients = new HashMap<>();
	}
	
	/**
	 * Adds a new client to the pool.
	 * @param id Unique id of the client. If the id is already used, the old one is replaced and returned
	 * @param clientManager The client to add
	 * @return The old ClientManager that was associated with the id, or null if no ClientManager existed
	 */
	public synchronized ClientManager addClient(int id, ClientManager clientManager){
		synchronized(clients){
			 return clients.put(id, clientManager);
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
	 * Sends a packet to the client with the given id. If the id does not exist, an exception is thrown.
	 * @param id The id of the client
	 * @param packet The packet
	 * @throws IOException If anything goes wrong with the IO
	 * @throws IllegalArgumentException If the key does not exist in the table
	 */
	public void send(int id, Packet packet) throws IOException, IllegalArgumentException{
		
		//check if the key is in the table
		if(!clients.containsKey(id))
			throw new IllegalArgumentException(String.format("Key not found: %d", id));
		
		ClientManager mngr = clients.get(id);
		
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
}
