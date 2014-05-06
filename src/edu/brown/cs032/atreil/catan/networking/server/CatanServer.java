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
	//private ChatServer _chatServer; //the chat server
	//public final String _hostname; //the host of the computer that is hosting the game
	private final ServerSocket _server; //the object that handles that physical connections
	public final int _numClients; //specifies how many players must be connected in order for the game to start
	private final ClientPool _pool; //keeps track of all the clients
	//private final Executor _e; //manages threads to deal with new connections
	private final int TIMEOUT = 5000; //the time the server should wait while waiting for connectino before checking number of connections
	private final LinkedList<Move> _moveBuffer; //keeps track of any available moves from clients
	private Referee _ref;
	private StringBuilder _update; //keeps track of updates of the server 
	private boolean _isRunning; //keeps track if the server is running
	private final Integer _isRunningLock;
	private boolean _inLobby; //keeps track if we are in the lobby waiting for connections
	private final Integer _inLobbyLock;
	private boolean _inGame; //keeps track if we are currently playing a game
	private final Integer _inGameLock;
	
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
		_pool = new ClientPool(this, _numClients);
		//_e = Executors.newCachedThreadPool();
		_server = new ServerSocket(_port);
		_server.setSoTimeout(TIMEOUT); //the server will wait five seconds for connections, and then check how many connections there are
		_moveBuffer = new LinkedList<>();
		_chatPort = -1;
		
		//locks
		_isRunningLock = new Integer(-1);
		_inGameLock = new Integer(-1);
		_inLobbyLock = new Integer(-1);
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
		_pool = new ClientPool(this, _numClients);
		//_e = Executors.newCachedThreadPool();
		_server = new ServerSocket(_port);
		_server.setSoTimeout(TIMEOUT); //the server will wait five seconds for connections, and then check how many connections there are
		_moveBuffer = new LinkedList<>();
		_update = new StringBuilder("");
		_isRunning = false;
		_inLobby = false;
		_inGame = false;
		
		//locks
		_isRunningLock = new Integer(-1);
		_inGameLock = new Integer(-1);
		_inLobbyLock = new Integer(-1);
		
		_isRunning = true;
		_inLobby = true;
		_inGame = false;
	}
	
	/**
	 * Starts the server by accepting connections, and then actually launching the game
	 * once all players have connected
	 */
	public void run(){
		
		//accept connections
		accept();
		
		setInGame(true);
			//start the game
			try {
				if(getIsRunning()){
					_pool.broadcast(new Packet(Packet.STARTGAME, null));
					_pool.addUpdate("Starting the game\n");
	
					//no more clients may connect
					_server.close();
					
					_ref = new Referee(_pool.getPlayers(), this);
					_inGame = true;
					_ref.runGame();
				}
			} catch (IllegalArgumentException e) {
				addUpdate(e.getMessage());
			} catch (SocketException e){
				addUpdate(e.getMessage());
			} catch (IOException e) {
				addUpdate(e.getMessage());
			}
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
		
		while((_pool.getNumConnected() < _numClients) && getIsRunning()){
			try {
				
				if(_pool.getNumConnected() < _numClients){
					Socket client = _server.accept();
					
					//set up new client manager
					//_e.execute(new ClientRunnable(client, _pool, _chatPort, _numClients));
					new ClientManager(_pool, client, _chatPort, _numClients).start();
					
					sendConnected();
				}
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
			} catch(IllegalArgumentException e){
				addUpdate(String.format("Error: %s", e.getMessage()));
			}
		}
		
		setInLobby(false);
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
		_pool.broadcast(new Packet(Packet.MESSAGE, String.format("%s", waiting.toString())));
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
		
	public void startSettlement(String playerName) throws IllegalArgumentException{
		try {
			_pool.send(playerName, new Packet(Packet.SETTLEMENT, null));
            addUpdate("It is " + playerName + "'s turn to place a settlement.");
		} catch (IOException e) {
			addUpdate(e.getMessage());
		}
	}
	
	public void startRoad(String playerName) throws IllegalArgumentException{
		try {
			_pool.send(playerName, new Packet(Packet.ROAD, null));
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
			_pool.broadcast(new Packet(Packet.PLAYERARRAY, players));
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
			Packet p = new Packet(Packet.BOARD, board);
			
			_pool.broadcast(p);
		} catch (IOException e) {
			addUpdate(e.getMessage());
		}
	}
    
    public void sendStartSettle(String name, int index){
		try {
			Packet p = new Packet(Packet.START_SETTLE, index);
			
			_pool.send(name, p);
		} catch (IOException e) {
			addUpdate(e.getMessage());
		}
	}
    
    public void sendEndStart(){
		try {
			Packet p = new Packet(Packet.END_START, false);
			
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
			_pool.send(playerName, new Packet(Packet.ROLL, new Integer(roll)));
			_pool.sendAllChat(String.format("Server %s rolled a %s", playerName, roll), "Server");
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
				_pool.sendAllChat("server " + message, "Server");
			else
				_pool.sendChat(playerName, "server " + message, "Server");
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends a trade to the specified player
	 * @param playerName The user to send to
	 * @param trade The trade to offer
	 */
	public void sendTrade(String playerName, TradeMove trade){
		try{
			_pool.send(playerName, new Packet(Packet.TRADE, trade));
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
			_pool.broadcast(new Packet(Packet.LASTMOVE, null));
		} catch (IllegalArgumentException | IOException e) {
			addUpdate(e.getMessage());
		}
	}
	
	/**
	 * Sends a game over to the client and kills the server
	 * @param player
	 */
	public void sendGameOver(String player){
		if(getIsRunning()){
			try{
				_pool.broadcast(new Packet(Packet.GAME_OVER, player));
			} catch (IllegalArgumentException | IOException e) {
				addUpdate(e.getMessage());
			}
			kill();
		}
	}
	
	/**
	 * Tells the client who rolled the seven that everyone has dropped
	 * their resources and that they can end their turn
     * @param playerName name to send it to
	 */
	public void sendEndSeven(String playerName){
		try{
			_pool.send(playerName, new Packet(Packet.END_SEVEN, null));
		} catch(IllegalArgumentException | IOException e){
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
			while(_moveBuffer.isEmpty() && getIsRunning()){
				try {
					_moveBuffer.wait();
				} catch (InterruptedException e) {}
			}
						
			if(getIsRunning())
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
		String update = "Error: Nothing to read...";
		
		synchronized(_update){
			while(_update.length() == 0 && getIsRunning()){
				try {
					_update.wait();
				} catch (InterruptedException e) {
					//can't do anything at this point
					addUpdate(String.format("Error: %s", e.getMessage()));
				}
			}
			
			update = _update.toString();
			_update.setLength(0);
			
			return update;
		}
	}
	
	/**
	 * Adds an update and notifies any methods waiting on the update
	 * @param message The update
	 */
	public void addUpdate(String message){
		boolean running = getIsRunning();
		
		if(running){
			synchronized(_update){
				_update.append(String.format("%s\n", message));
				_update.notifyAll();
			}
		}
	}
	
	/**@deprecated use {@link getIsRunning}
	 * Returns whether or not the game is still going on
	 * @return True, if the game is still going on and false otherwise
	 */
	@Deprecated
	public boolean isRunning(){
		return !_ref.isGameOver();
	}
	
	/**
	 * @deprecated use {@link getIsRunning}
	 * Returns the state of the server.
	 * @return true, if it is running, and false otherwise
	 */
	@Deprecated
	public boolean isServerRunning(){
		return getIsRunning();
	}
	
	/**
	 * Closes down the server and its associated resources
	 */
	public void kill(){
		
		boolean running = getIsRunning();
		
		if(running){
			setIsRunning(false);
			setInLobby(false);
			setInGame(false);
			_pool.killAll();
			
			try{
				_server.close();
			} catch(IOException e){
				//already closed
			}
			
			//_chatServer.kill();
			
			//notify anybody reading updates
			synchronized (_update) {
				_update.notifyAll();
			}
			
			//notify anybody reading moves
			synchronized(_moveBuffer){
				_moveBuffer.notifyAll();
			}
		}
	}

	public void sendSeven(String name) throws IllegalArgumentException, IOException {
		_pool.send(name, new Packet(Packet.SEVEN, null));
	}
	

	public void sendRB(String name) throws IllegalArgumentException, IOException{
		_pool.send(name, new Packet(Packet.BAD_RB, null));
	}
	
	/**
	 * Sets if the server is running. This method
	 * is thread safe
	 * @param running true, if the server is running and false otherwise
	 */
	private void setIsRunning(boolean running){
		synchronized(_isRunningLock){
			_isRunning = running;
		}
	}
	
	/**
	 * Sets if the server is in the lobby. This method
	 * is thread safe
	 * @param inLobby true, if the server is in the lobby and false otherwise
	 */
	private void setInLobby(boolean inLobby){
		synchronized(_inLobbyLock){
			_inLobby = inLobby;
		}
	}
	
	/**
	 * Sets if the server is in the game. This method
	 * is thread safe
	 * @param inGame true, if the server is in the game and false otherwise
	 */
	private void setInGame(boolean inGame){
		synchronized(_inGameLock){
			_inGame = inGame;
		}
	}
	
	/**
	 * Returns if the server is running. This method
	 * is thread safe
	 * @return true, if the server is running and false otherwise
	 */
	public boolean getIsRunning(){
		synchronized(_isRunningLock){
			return _isRunning;
		}
	}
	
	/**
	 * Returns if the server is in the lobby. This method
	 * is thread safe
	 * @return true, if the server is in the lobby and false otherwise
	 */
	public boolean getInLobby(){
		synchronized(_inLobbyLock){
			return _inLobby;
		}
	}
	
	/**
	 * Returns if the server is in the game. This method
	 * is thread safe
	 * @return true, if the server is in the game and false otherwise
	 */
	public boolean getInGame(){
		synchronized(_inGameLock){
			return _inGame;
		}
	}
	
	/**
	 * Gives 10 of each resources to the given player.
	 * @param playerName The player to give 10 of each resource.
	 */
	public void foodler(String playerName){
		_ref.foodler(playerName);
	}
}
