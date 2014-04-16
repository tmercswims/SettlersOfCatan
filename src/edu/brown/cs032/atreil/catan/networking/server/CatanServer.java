package edu.brown.cs032.atreil.catan.networking.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.brown.cs032.atreil.catan.networking.Packet;


/**
 * This class handles the hosting of a Catan game. It contains a reference to all the clients and the referee. A
 * convenient abstraction for this class is that of a messenger that receives a move from a player, sends it to
 * the referee to validate the move, and then sends the updated board to the other players.
 * @author atreil
 *
 */
public class CatanServer{

	public final int _port; //the port that the clients will connect to
	public final String _hostname; //the host of the computer that is hosting the game
	private final ServerSocket _server; //the object that handles that physical connections
	public final int _numClients; //specifies how many players must be connected in order for the game to start
	private final ClientPool _pool; //keeps track of all the clients
	private final Executor _e; //manages threads to deal with new connections
	private final int TIMEOUT = 5000; //the time the server should wait while waiting for connectino before checking number of connections
	private int id = 0; //keeps track of the unique id for the client
	
	/**
	 * This constructor initializes a server from a port and hostname. The instantiated object will NOT listen
	 * to new connections until the start() command is executed. Calling kill() will safely close any clients and
	 * the server.
	 * 
	 * @param hostname The name of the computer hosting the game/server
	 * @param port The port on which the server is being hosted on. Must be in range (1024, 65535] (1024 is exclusive
	 * and 65535 is inclusive)
	 * @param numClients The number of clients that must be connected for the game to start
	 * @throws IOException If anything goes wrong with reading in connections
	 */
	public CatanServer(String hostname, int port, int numClients) throws IOException{
		
		//check if port is valid
		if(port <= 1024 || port > 65535)
			throw new IllegalArgumentException(String.format("Port must be between 1024 exclusive and 65535 inclusive; got %d", port));
		
		//setting up fields
		_port = port;
		_hostname = hostname;
		_numClients = numClients;
		_pool = new ClientPool();
		_e = Executors.newFixedThreadPool(4);
		_server = new ServerSocket(_port);
		_server.setSoTimeout(TIMEOUT); //the server will wait five seconds for connections, and then check how many connections there are
	}
	
	/**
	 * Accepts connections and adds them to the client pool. Once the number of connections has reached
	 * numClients, the server will stop listening for new connections and return
	 * @throws IOException 
	 */
	public void accept() throws IOException{
		//accept connections
		while(_pool.getNumConnected() < _numClients){
			try {
				Socket client = _server.accept();
				System.out.println("Connected to a client");
				
				//set up new client manager
				_e.execute(new ClientRunnable(client, _pool));
				
				System.out.println(String.format("Number of connected clients: %s", _pool.getNumConnected()));
			} catch(SocketTimeoutException e){
				//simply checking how many connections there are
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Starts a new game 
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	public void start() throws IllegalArgumentException, IOException{
		//starting game
		for(String name : _pool.getPlayerNames())
			System.out.println(String.format("Connected: %s", name));
		
		_pool.broadcast(new Packet(Packet.MESSAGE, "Starting game"));
	}
	
	/**
	 * Returns all of the player names connected to the server
	 * @return List of all the player names connected to the server
	 */
	public List<String> getPlayerNames(){
		return _pool.getPlayerNames();
	}
	
	/**
	 * This inner class handles new connections by extracting the player name and creating a new ClientHandler class
	 * @author atreil
	 *
	 */
	private class ClientRunnable implements Runnable{

		private final Socket _client; //the socket used to listen to the client
		private final ClientPool _pool; //contains all of the clients connected to the server
		
		public ClientRunnable(Socket client, ClientPool pool){
			this._client = client;
			this._pool = pool;
		}
		
		@Override
		public void run() {
			try {
				//set up new manager
				ClientManager mgr = new ClientManager(_pool, _client);
				
				//add to the pool
				_pool.addClient(id++, mgr);
			} catch (IOException e) {
				System.err.println("Error: " + e.getMessage());
				
				writeError(e);
			}
		}
		
	}
	
	/**
	 * Sends a packet to the client with the given id
	 * @param id The id of the client to send the packet to
	 * @param packet The packet to send the client to
	 */
	public void sendPacket(int id, Packet packet){
		
	}
	
	/**
	 * Writes an exception's stack trace to a log file.
	 * @param e The exception to write to file
	 */
	private void writeError(Exception e){
		try {
			e.printStackTrace(new PrintStream(new FileOutputStream(new File("data/server.log"))));
		} catch (FileNotFoundException e1) {
			//This really should not happen...
			System.out.println("Could not find log file...");
		}
	}
}
