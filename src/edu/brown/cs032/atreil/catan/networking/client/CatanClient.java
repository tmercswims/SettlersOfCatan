
//TODO: Need to convert IP to Hostname
package edu.brown.cs032.atreil.catan.networking.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import edu.brown.cs032.atreil.catan.networking.Packet;
import edu.brown.cs032.eheimark.catan.menu.LaunchConfiguration;
import edu.brown.cs032.sbreslow.catan.gui.board.Board;
import edu.brown.cs032.tmercuri.catan.logic.Player;
import edu.brown.cs032.tmercuri.catan.logic.move.Move;

/**
 * This class handles a client communicating with the server. The client will communicate with the server
 * through this class.
 * @author atreil
 *
 */
public class CatanClient extends Thread{

	private Player _p; //the player class associated with this client
	private Socket _socket; //the socket to communicate with the server
	private ObjectInputStream _in; //the stream to read in from the server
	private ObjectOutputStream _out; //the stream to send messages to the server
	private volatile boolean _isStarting;
	private Player[] _updatedPlayers;
	private Boolean _hasUpdatedPlayers;//any new updates
	private Board _updatedBoard;
	private Boolean _hasUpdatedBoard; //any new  board updates
	
	/**
	 * Constructs a new Client from an existing player class. After construction, the client will attempt to 
	 * connect to the server on the given port and hostname. If the connection fails, an exception is thrown
	 * @param p The player associated with the client
	 * @param hostname The name of the host server
	 * @param port The port of the host server
	 * @throws IOException If anything goes wrong with communicating with the server
	 * @throws UnknownHostException If the host does not exist
	 */
	@Deprecated
	public CatanClient(Player p, String hostname, int port) throws UnknownHostException, IOException{
		this._p = p;
		this._socket = new Socket(hostname, port);
		
		//setting up readers	
		_out = new ObjectOutputStream(_socket.getOutputStream());
		_out.flush();
		_in = new ObjectInputStream(_socket.getInputStream());
	}
	
	/**
	 * Launches a new CatanClient with the specified LaunchConfiguration. This will begin to connected immediately.
	 * @param configs
	 * @throws IOException If anything goes wrong with the IO
	 * @throws UnknownHostException If the host does not exist
	 */
	public CatanClient(LaunchConfiguration configs) throws UnknownHostException, IOException{
		this._p = new Player(configs.getAvatarName());
		_updatedBoard = null;
		_updatedPlayers = null;
		_hasUpdatedBoard = false;
		_hasUpdatedPlayers = false;
		
		//TODO: DEBUGING MODE
		//_p.addResources(new int[]{10,10,10,10,10});
		//
		
		String ip = configs.getHostName();
		
		this._socket = new Socket(InetAddress.getByName(ip).getHostName(), configs.getJoinPort());
		_isStarting = false;
		
		//setting up readers
		_out = new ObjectOutputStream(_socket.getOutputStream());
		_out.flush();
		_in = new ObjectInputStream(_socket.getInputStream());
		
		//connecting
		connect();
	}
	
	/**
	 * Starts listening to the server
	 */
	public void run(){
		try {
			while(_isStarting){
				Packet packet = (Packet) readPacket();
				
				parsePacket(packet);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Parses a packet and performs an appropriate action
	 * @param packet The packet to parse
	 */
	private void parsePacket(Packet packet){
		int type = packet.getType();
		
		if(type == Packet.BOARD){
			synchronized(_hasUpdatedBoard){
				_hasUpdatedBoard = true;
				_updatedBoard = (Board) packet.getObject();
				_updatedBoard.notifyAll();
			}
		} else if(type == Packet.PLAYERARRAY){
			synchronized(_hasUpdatedPlayers){
				_hasUpdatedPlayers = true;
				_updatedPlayers = (Player[]) packet.getObject();
				_updatedPlayers.notifyAll();
			}
		} else if(type == Packet.START){
			System.out.println("Start your turn");
		} else if(type == Packet.ROLL){
			System.out.println(String.format("Roll: %s", (Integer) packet.getObject()));
		}
		else{
			System.out.println(String.format("Unsupported. Got: %s", type));
		}
	}
	
	/**
	 * Connects to the server
	 * @throws IOException If anything goes wrong with the Server
	 */
	private void connect() throws IOException{
		try {
			//receive handshake
			Packet packet;
			packet = (Packet) readPacket();
			
			int cmd = packet.getType();
			
			//check for errors
			if(cmd == Packet.ERROR){
				String error = (String) packet.getObject();
				throw new IOException(error);
			} else if(cmd != Packet.HANDSHAKE){
				//the server is bad so don't connect
				throw new IOException("Bad server protocol");
			}
			
			//sending packet with the player class
			packet = new Packet(Packet.PLAYER, _p);
			_out.writeObject(packet);
			_out.flush();
		} catch (ClassNotFoundException e) {
			throw new IOException(e.getMessage());
		}
	}
	
	/**
	 * Sends a move to the server for processing
	 * @param move The move to send to the server
	 * @throws IOException If anything goes wrong with the IO
	 * @throws IllegalArgumentException If the Client failed to properly package the object
	 */
	public void sendMove(Move move) throws IllegalArgumentException, IOException{
		_out.writeObject(new Packet(Packet.MOVE, move));
	}
	
	/**
	 * Reads in a packet from the socket. This method will block until an
	 * object is read.
	 * @return The packet sent to the socket
	 * @throws ClassNotFoundException If the class of the object does not exist
	 * @throws IOException If something goes wrong with communicating with the socket
	 */
	private Packet readPacket() throws ClassNotFoundException, IOException{
		
		try{
			Object o = _in.readObject();
			
			if(o instanceof Packet)
				return (Packet) o;
			else
				throw new IOException("Invalid protocol: Received something other than a packet");
		} catch(IOException e){
			kill();
			throw new IOException("Server disconnected");
		}
	}
	
	/**
	 * Reads an update from the Server while waiting for the game to launch. This will also change
	 * the {@code _isStarting} field to true if the server is about to start the game.
	 * @return An update
	 * @throws IOException If anything goes wrong with IO
	 */
	public String readServerMessage() throws IOException{
		try {
			Packet p = (Packet) readPacket();
			int type = p.getType();
			
			if(type == Packet.MESSAGE)
				return (String) p.getObject();
			else if(type == Packet.STARTGAME){
				_isStarting = true;
				return "Starting the game\n";
			} else if(type == Packet.ERROR){
				return (String) p.getObject();
			} else if(type == Packet.START){
				return "It is your turn. Make a move";
			}
			else
				throw new IOException(String.format("Bad server protocol. Got code: %s", type));
		} catch (ClassNotFoundException e) {
			throw new IOException(e.getMessage());
		}
	}
	
	/**
	 * Returns an updated list of players. This will block until
	 * there is an updated list of players.
	 * @return A lift of updated players
	 */
	public Player[] getPlayers(){
		synchronized (_hasUpdatedPlayers) {
			while(_updatedPlayers.equals(false)){
				try {
					_updatedPlayers.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Player[] toReturn = _updatedPlayers;
			_updatedPlayers = null;
			_hasUpdatedPlayers = false;
			return toReturn;
		}
	}
	
	/**
	 * Returns an updated board. This will block until there
	 * is an updated board.
	 * @return An updated board
	 */
	public Board getBoard(){
		synchronized (_hasUpdatedBoard) {
			while(_hasUpdatedBoard.equals(false)){
				try {
					_updatedBoard.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Board toReturn = _updatedBoard;
			_updatedBoard = null;
			_hasUpdatedBoard = false;
			return toReturn;
		}
	}
	
	/**
	 * Returns the player's name
	 * @return Player's name
	 */
	public String getPlayerName(){
		return _p.getName();
	}
	
	/**
	 * Indicates whether or not the game is about to start
	 * @return true, if the game has started, and false otherwise
	 */
	public boolean getIsStarting(){
		return _isStarting;
	}
	
	/**
	 * Kills the client by shutting down the socket and associated
	 * streams
	 */
	public void kill(){
		try{
			_in.close();
			_out.close();
			_socket.close();
		} catch(IOException e){
			//not much to do
		}
	}
}
