package edu.brown.cs032.atreil.catan.networking.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.brown.cs032.atreil.catan.chat.server.ChatServer;
import edu.brown.cs032.atreil.catan.networking.Packet;
import edu.brown.cs032.eheimark.catan.launch.LaunchConfiguration;
import edu.brown.cs032.sbreslow.catan.gui.board.Board;
import edu.brown.cs032.tmercuri.catan.logic.Player;
import edu.brown.cs032.tmercuri.catan.logic.Referee;
import edu.brown.cs032.tmercuri.catan.logic.move.Move;
import edu.brown.cs032.tmercuri.catan.logic.move.TradeMove;


/**
 * This class handles the hosting of a Catan game. It contains a reference to all the clients and the referee. A
 * convenient abstraction for this class is that of a messenger that receives a move from a player, sends it to
 * the referee to validate the move, and then sends the updated board to the other players.
 * @author atreil
 *
 */
public class CatanServer extends Thread{

	public final int _port; //the port that the clients will connect to
	private int _chatPort; //the port of the chat server
	private ChatServer _chatServer; //the chat server
	//public final String _hostname; //the host of the computer that is hosting the game
	private final ServerSocket _server; //the object that handles that physical connections
	public final int _numClients; //specifies how many players must be connected in order for the game to start
	private final ClientPool _pool; //keeps track of all the clients
	private final Executor _e; //manages threads to deal with new connections
	private final int TIMEOUT = 5000; //the time the server should wait while waiting for connectino before checking number of connections
	private int id = 0; //keeps track of the unique id for the client
	private final LinkedList<Move> _moveBuffer; //keeps track of any available moves from clients
	private Referee _ref;
	private StringBuilder _update; //keeps track of updates of the server 
	public boolean _isRunning; //keeps track if the server is running
	
	/**
	 * This constructor initializes a server from a port and hostname. The instantiated object will NOT listen
	 * to new connections until the start() command is executed.
	 * <p>
	 * @deprecated use the new constructor that takes in a {@link LaunchConfiguration} class instead.
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
			throw new IllegalArgumentException(String.format("Port must be b/w 1024 and 65535 inclusive; got %d", port));
		
		//setting up fields
		_port = port;
		_numClients = numClients;
		_pool = new ClientPool(this);
		_e = Executors.newCachedThreadPool();
		_server = new ServerSocket(_port);
		_server.setSoTimeout(TIMEOUT); //the server will wait five seconds for connections, and then check how many connections there are
		_moveBuffer = new LinkedList<>();
		_chatPort = -1;
	}
	
	/**
	 * This constructor initializes a server from a @{link LaunchConfiguration} class. The instantiated object will NOT listen
	 * to new connections until the start() command is executed.
	 * @param configs The class that represents the configurations to be used
	 * @throws IOException If anything goes wrong with setting up the server
	 * @throws IllegalArgumentException If port is invalid
	 */
	public CatanServer(LaunchConfiguration configs) throws IOException, IllegalArgumentException {
		int port = Integer.parseInt(configs.getHostPort());
		
		//check if port is valid
		if(port <= 1024 || port > 65535)
			throw new IllegalArgumentException(String.format("Port must be between 1024 exclusive and 65535 inclusive; got %d", port));
		
		//setting up fields
		_port = port;
		_numClients = (configs.isFourPlayerGame()) ? 4 : 3;
		_pool = new ClientPool(this);
		_e = Executors.newCachedThreadPool();
		_server = new ServerSocket(_port);
		_server.setSoTimeout(TIMEOUT); //the server will wait five seconds for connections, and then check how many connections there are
		_moveBuffer = new LinkedList<>();
		_update = new StringBuilder();
		_chatServer = startChatServer(_port);
		_chatServer.start();
	}
	
	/**
	 * Starts up a chat server. Begins looking at port+1
	 * @param port the port to start looking from.
	 * @return The ChatServer
	 * @throws IOException If a port could not be found for the chat server
	 */
	private ChatServer startChatServer(int port) throws IOException{
		boolean found = false;
		ChatServer chat = null;
		
		while(!found){
			try {
				chat = new ChatServer(++port, _numClients);
				
				//found empty port
				_chatPort = port;
				found = true;
			} catch (IOException e) {
				//port is already taken, try again
			}
		}
		
		if(chat == null)
			throw new IOException("Could not find port for chat server.");
		
		return chat;
	}
	
	/**
	 * Accepts connections and adds them to the client pool. Once the number of connections has reached
	 * numClients, the server will stop listening for new connections and return
	 * <p>
	 */
	private void accept(){
		//accept connections
		
		//display ip address
		addUpdate(String.format("This is your ip address: %s\nThis is your port: %s\nGive them to your friends" +
				" so that they can connect to you!", getLocalIP(), getLocalPort()));
		
		while((_pool.getNumConnected() < _numClients) && _isRunning){
			try {
				Socket client = _server.accept();
				
				//set up new client manager
				_e.execute(new ClientRunnable(client, _pool, _chatPort, _numClients));
				
				sendConnected();
			} catch(SocketTimeoutException e){
				//simply checking how many connections there are
				try {
					sendConnected();
				} catch (IllegalArgumentException e1) {
					addUpdate(String.format("Error: %s", e1.getMessage()));
				} catch (IOException e1) {
					addUpdate(String.format("Error: %s", e1.getMessage()));
				}
			} catch (IOException e) {
				addUpdate(String.format("Error: %s", e.getMessage()));
			}
		}
	}
	
	/**
	 * Starts the server by accepting connections, and then actually launching the game
	 * once all players have connected
	 */
	public void run(){
		_isRunning = true;
				
		//accept connections
		accept();
		
		if(_isRunning){
			//start the game
			try {
				_pool.broadcast(new Packet(Packet.STARTGAME, null, id++));
				_pool.addUpdate("Starting the game\n");
				_chatServer.setPlayers(_pool.getPlayerList());
				//client will no longer listen to clients so shutdown its server
				_server.close();
				
				_ref = new Referee(_pool.getPlayers(), this);
				_ref.runGame();
			} catch (IllegalArgumentException e) {
				addUpdate(e.getMessage());
			} catch (SocketException e){
				//game is over
			} catch (IOException e) {
				addUpdate(e.getMessage());
			}
		}
	}
	
	/**
	 * Sends the name of all of the connected players.
	 * @throws IOException If anything goes wrong with the IO
	 * @throws IllegalArgumentException If the packet was formated wrong
	 */
	private void sendConnected() throws IllegalArgumentException, IOException{
		StringBuilder waiting = new StringBuilder(""); //all of the players that are currently connected
		
		//get player names
		for(String name : _pool.getPlayerNames())
			waiting.append(String.format("Connected: %s\n", name));
		
		//sending update
		//addUpdate(String.format("Sending update: %s", waiting.toString()));
		
		_pool.broadcast(new Packet(Packet.MESSAGE, String.format("%s", waiting.toString()), id++));
	}
	
	/**
	 * Returns the local ip in a textual format. This is the ip that the
	 * host should give to other players if they want to connect on the 
	 * same network.
	 * @return The textual representation of the raw ip address
	 */
	public String getLocalIP(){
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "COULD NOT FIND ADDRESS";
		}
	}
	
	/**
	 * Returns the port that the server is running on
	 * @return Port of the server
	 */
	public int getLocalPort(){
		return _server.getLocalPort();
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
	 */
	private class ClientRunnable implements Runnable{

		private final Socket _client; //the socket used to listen to the client
		private final ClientPool _pool; //contains all of the clients connected to the server
		private final int _chatPort; //the port of the chat server
		private final int _numPlayers; //number of players
		
		public ClientRunnable(Socket client, ClientPool pool, int chatPort, int numPlayers){
			this._client = client;
			this._pool = pool;
			this._chatPort = chatPort;
			this._numPlayers = numPlayers;
		}
		
		@Override
		public void run() {
			try {
				//set up new manager
				new ClientManager(_pool, _client, _chatPort, _numPlayers).start();
			} catch (IOException e) {
				addUpdate(String.format("Error: %s", e.getMessage()));
				
			}
		}
		
	}
	
	
	public void startSettlement(String playerName) throws IllegalArgumentException{
		try {
			_pool.send(playerName, new Packet(Packet.SETTLEMENT, null, id++));
            addUpdate("It is " + playerName + "'s turn to place a settlement.");
		} catch (IOException e) {
			addUpdate(e.getMessage());
		}
	}
	
	public void startRoad(String playerName) throws IllegalArgumentException{
		try {
			_pool.send(playerName, new Packet(Packet.ROAD, null, id++));
            addUpdate("It is " + playerName + "'s turn to place a road.");
		} catch (IOException e) {
			addUpdate(e.getMessage());
		}
	}
	
	
	/**
	 * Sends an array of players to all the clients
	 * @param players The players to send
	 */
	public void sendPlayerArray(Player[] players){
		try {
			_pool.broadcast(new Packet(Packet.PLAYERARRAY, players, id++));
		} catch (IOException e) {
			addUpdate(e.getMessage());
		}
	}
	
	/**
	 * Sends a board to all the clients
	 * @param board The board to send
	 */
	public void sendBoard(Board board){
		try {
			Packet p = new Packet(Packet.BOARD, board, id++);
			
			_pool.broadcast(p);
		} catch (IOException e) {
			addUpdate(e.getMessage());
		}
	}
	
	/**
	 * Sends a roll to the specified client
	 * @param playerName The player to send the roll to
	 * @param roll The roll to send
	 */
	public void sendRoll(String playerName, int roll){
		try {
			_pool.send(playerName, new Packet(Packet.ROLL, new Integer(roll), id++));
			_chatServer.sendAll(String.format("%s rolled a %s", playerName, roll));
		} catch (IOException e) {
			addUpdate(e.getMessage());
		}
	}
	
	/**
	 * Sends a message to the specified client
	 * @param playerName The client to send the error to
	 * @param message The message to send
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	public void sendMessage(String playerName, String message){
		
		try{
			if(playerName == null)
				_pool.sendAllChat(message, "server");
			else
				_pool.sendChat(playerName, message, "server");
		} catch(IOException e){
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Sends a trade to the specified player
	 * @param playerName The user to send to
	 * @param trade The trade to offer
	 */
	public void sendTrade(String playerName, TradeMove trade){
		try{
			_pool.send(playerName, new Packet(Packet.TRADE, trade, 0));
		} catch(IOException e){
			addUpdate(e.getMessage());
		} catch(IllegalArgumentException e){
			addUpdate(e.getMessage());
		}
	}
	
	/**
	 * Sends a last move object to all the clients
	 */
	public void sendLastMove(){
		try {
			_pool.broadcast(new Packet(Packet.LASTMOVE, null, 0));
		} catch (IllegalArgumentException | IOException e) {
			addUpdate(e.getMessage());
		}
	}
	
	/**
	 * Reads a move from the server.
	 * @return The move
	 * @throws SocketException If the game is over
	 */
	public Move readMove() throws SocketException{
		synchronized(_moveBuffer){
			while(_moveBuffer.isEmpty() && _isRunning){
				try {
					_moveBuffer.wait();
				} catch (InterruptedException e) {}
			}
			
			if(_isRunning)
				return _moveBuffer.poll();
			else
				throw new SocketException("Game is over");
		}
	}
	
	/**
	 * Adds a Move to the internal buffer so that the Referee may process it
	 * @param move The move to add
	 */
	public void addMove(Move move){
		synchronized(_moveBuffer){
			_moveBuffer.add(move);
			_moveBuffer.notifyAll();
		}
	}
	
	/**
	 * Returns the status of the server. May include messages such as how many are connected, any
	 * errors, a new client just connected or a client disconnected. This will block similarly to how
	 * a read() on BufferedReader will block until there is input to read.
	 * @return The status of the update
	 */
	public String readStatus(){
		synchronized(_update){
			while(_update.length() == 0 && _isRunning){
				try {
					_update.wait();
				} catch (InterruptedException e) {
					//can't do anything at this point
					addUpdate(e.getMessage());
				}
			}
			
			String update = _update.toString();
			_update.setLength(0);
			return update;
		}
	}
	
	/**
	 * Adds an update and notifies any methods waiting on the update
	 * @param message The update
	 */
	public void addUpdate(String message){
		if(_isRunning){
			synchronized(_update){
				_update.append(String.format("%s\n", message));
				_update.notifyAll();
			}
		}
	}
	
	/**@deprecated use {@link isServerRunning}
	 * Returns whether or not the game is still going on
	 * @return True, if the game is still going on and false otherwise
	 */
	@Deprecated
	public boolean isRunning(){
		return !_ref.isGameOver();
	}
	
	/**
	 * Returns the state of the server.
	 * @return true, if it is running, and false otherwise
	 */
	public boolean isServerRunning(){
		return _isRunning;
	}
	
	/**
	 * Stops the game if any client/server disconnects or wins
	 * the game.
	 * @param message A message describing what happened
	 */
	public void stopGame(String message){
		//TODO: stop the game
	}
	
	/**
	 * Closes down the server and its associated resources
	 */
	public void kill(){
		try {
			_isRunning = false;
			_pool.killAll();
			_server.close();
			_chatServer.kill();
			
			//notify anybody reading updates
			synchronized (_update) {
				_update.notifyAll();
			}
			
			//notify anybody reading moves
			synchronized(_moveBuffer){
				_moveBuffer.notifyAll();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void sendSeven(String name) throws IllegalArgumentException, IOException {
		_pool.send(name, new Packet(Packet.SEVEN, null, 0));
	}
	

	public void sendRB(String name) throws IllegalArgumentException, IOException{
		_pool.send(name, new Packet(Packet.BAD_RB, null, 0));
	}
}
