package edu.brown.cs032.atreil.catan.networking.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.brown.cs032.atreil.catan.networking.Packet;
import edu.brown.cs032.eheimark.catan.menu.LaunchConfiguration;
import edu.brown.cs032.sbreslow.catan.gui.board.Board;
import edu.brown.cs032.tmercuri.catan.logic.Player;
import edu.brown.cs032.tmercuri.catan.logic.move.Move;


/**
 * This class handles the hosting of a Catan game. It contains a reference to all the clients and the referee. A
 * convenient abstraction for this class is that of a messenger that receives a move from a player, sends it to
 * the referee to validate the move, and then sends the updated board to the other players.
 * @author atreil
 *
 */
public class CatanServer extends Thread{

	public final int _port; //the port that the clients will connect to
	//public final String _hostname; //the host of the computer that is hosting the game
	private final ServerSocket _server; //the object that handles that physical connections
	public final int _numClients; //specifies how many players must be connected in order for the game to start
	private final ClientPool _pool; //keeps track of all the clients
	private final Executor _e; //manages threads to deal with new connections
	private final int TIMEOUT = 5000; //the time the server should wait while waiting for connectino before checking number of connections
	private int id = 0; //keeps track of the unique id for the client
	private LinkedList<Move> moveBuffer; //keeps track of any available moves from clients
	
	/**
	 * This constructor initializes a server from a port and hostname. The instantiated object will NOT listen
	 * to new connections until the start() command is executed.
	 * <p>
	 * @deprecated use the new constructor that takes in a @link{LaunchConfiguration} class instead.
	 * 
	 * @param hostname The name of the computer hosting the game/server
	 * @param port The port on which the server is being hosted on. Must be in range (1024, 65535] (1024 is exclusive
	 * and 65535 is inclusive)
	 * @param numClients The number of clients that must be connected for the game to start
	 * @throws IOException If anything goes wrong with reading in connections
	 */
	@Deprecated
	public CatanServer(String hostname, int port, int numClients) throws IOException{
		
		//check if port is valid
		if(port <= 1024 || port > 65535)
			throw new IllegalArgumentException(String.format("Port must be between 1024 exclusive and 65535 inclusive; got %d", port));
		
		//setting up fields
		_port = port;
		//_hostname = hostname;
		_numClients = numClients;
		_pool = new ClientPool(this);
		//TODO: change number of threads
		_e = Executors.newCachedThreadPool();
		_server = new ServerSocket(_port);
		_server.setSoTimeout(TIMEOUT); //the server will wait five seconds for connections, and then check how many connections there are
	}
	
	/**
	 * This constructor initializes a server from a LaunchConfiguration class. The instantiated object will NOT listen
	 * to new connections until the start() command is executed.
	 * @param configs The class that represents the configurations to be used
	 * @throws IOException If anything goes wrong with setting up the server
	 */
	public CatanServer(LaunchConfiguration configs) throws IOException{
		int port = configs.getHostPort();
		
		//check if port is valid
		if(port <= 1024 || port > 65535)
			throw new IllegalArgumentException(String.format("Port must be between 1024 exclusive and 65535 inclusive; got %d", port));
		
		//setting up fields
		_port = port;
		//_hostname = ;
		_numClients = (configs.isFourPlayerGame()) ? 4 : 3;
		_pool = new ClientPool(this);
		_e = Executors.newCachedThreadPool();
		_server = new ServerSocket(_port);
		_server.setSoTimeout(TIMEOUT); //the server will wait five seconds for connections, and then check how many connections there are
	}
	
	/**
	 * Accepts connections and adds them to the client pool. Once the number of connections has reached
	 * numClients, the server will stop listening for new connections and return
	 * <p>
	 */
	private void accept(){
		//accept connections
		while(_pool.getNumConnected() < _numClients){
			try {
				Socket client = _server.accept();
				System.out.println("Connected to a client");
				
				//set up new client manager
				_e.execute(new ClientRunnable(client, _pool));
				
				//System.out.println(String.format("Number of connected clients: %s", _pool.getNumConnected()));
			} catch(SocketTimeoutException e){
				//simply checking how many connections there are
				try {
					StringBuilder waiting = new StringBuilder(""); //all of the players that are currently connected
					
					//get player names
					for(String name : _pool.getPlayerNames())
						waiting.append(String.format("Connected: %s\n", name));
					
					_pool.broadcast(new Packet(Packet.MESSAGE, String.format("Waiting...\n%s", waiting.toString())));
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Starts the server by accepting connections, and then actually launching the game
	 * once all players have connected
	 */
	public void run(){
		//accept connections
		accept();
		
		//start the game
		try {
			_pool.broadcast(new Packet(Packet.STARTGAME, null));
			//client will no longer listen to clients so shutdown its server
			_server.close();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				new ClientManager(_pool, _client).start();
			} catch (IOException e) {
				System.err.println("Error: " + e.getMessage());
				
				writeError(e);
			}
		}
		
	}
	
	/**
	 * Notifies the player to start their turn
	 * @param playerName The player to start the turn
	 * @throws IllegalArgumentException If no player exists with that name
	 */
	public void startTurn(String playerName) throws IllegalArgumentException{
		try {
			_pool.send(playerName, new Packet(Packet.START, null));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends an array of players to all the clients
	 * @param players The players to send
	 */
	public void sendPlayerArray(Player[] players){
		try {
			_pool.broadcast(new Packet(Packet.PLAYERARRAY, players));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends a board to all the clients
	 * @param board The board to send
	 */
	public void sendBoard(Board board){
		try {
			_pool.broadcast(new Packet(Packet.BOARD, board));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends a roll to the specified client
	 * @param playerName The player to send the roll to
	 * @param roll The roll to send
	 */
	public void sendRoll(String playerName, int roll){
		try {
			_pool.send(playerName, new Packet(Packet.ROLL, new Integer(roll)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends an error type to the specified client
	 * @param playerName The client to send the error to
	 * @param error The type of the error to send
	 */
	public void sendError(String playerName, int error){
		try {
			_pool.send(playerName, new Packet(Packet.ERROR, new Integer(error)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads a move from the server.
	 * @return The move
	 */
	public Move readMove(){
		synchronized(moveBuffer){
			while(moveBuffer.isEmpty()){
				try {
					moveBuffer.wait();
				} catch (InterruptedException e) {}
			}
			
			return moveBuffer.poll();
		}
	}
	
	/**
	 * Adds a Move to the internal buffer so that the Referee may process it
	 * @param move The move to add
	 */
	public void addMove(Move move){
		synchronized(moveBuffer){
			moveBuffer.add(move);
			moveBuffer.notifyAll();
		}
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
