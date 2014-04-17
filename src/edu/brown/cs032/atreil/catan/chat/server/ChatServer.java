package edu.brown.cs032.atreil.catan.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import edu.brown.cs032.atreil.catan.networking.server.ClientManager;

/**
 * This class handles sending and receiving messages to display to the chat box.
 * @author Alex Treil
 *
 */
public class ChatServer extends Thread{

	private ServerSocket _server; //the socket that accepts new connections
	private ClientPool _clients; //keeps track of the other clients
	private boolean _running;
	private int _maxClients; //number of maximum players
	private static int TIMEOUT = 5000; //number of ms server should wait for connections before checking how many are connected
	
	/**
	 * Initializes a new CatanChatServer that will listen for connections and spawn
	 * them in a new thread. The port must be greater than 1024.
	 * @param port The port to listen to
	 * @throws IOException If something goes wrong with initializing the socket
	 */
	public ChatServer(int port, int maxClients) throws IOException{
		
		if (port <= 1024) {
			throw new IllegalArgumentException("Ports under 1024 are reserved!");
		}
		
		this._clients = new ClientPool();
		this._server = new ServerSocket(port);
		this._maxClients = maxClients;
		
		//setup properties
		this._server.setSoTimeout(TIMEOUT);
	}
	
	/**
	 * Starts the Server by accepting new connections and running them on their own threads
	 */
	public void run(){
		_running = true;
		
		//accept connections
		while(_clients.getNumClients() < _maxClients){
			try {
				Socket client = _server.accept();
				System.out.println("Connected to a client");
				
				//set up new client manager
				new ChatClientManager(_clients, client).start();
				
				//System.out.println(String.format("Number of connected clients: %s", _pool.getNumConnected()));
			} catch(SocketTimeoutException e){
				//simply checking how many connections there are
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
