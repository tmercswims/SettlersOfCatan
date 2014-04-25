
//TODO: Need to convert IP to Hostname
package edu.brown.cs032.atreil.catan.networking.client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import edu.brown.cs032.atreil.catan.networking.Handshake;
import edu.brown.cs032.atreil.catan.networking.Packet;
import edu.brown.cs032.eheimark.catan.gui.GUI;
import edu.brown.cs032.eheimark.catan.gui.trade.TradeFrame;
import edu.brown.cs032.eheimark.catan.launch.LaunchConfiguration;
import edu.brown.cs032.sbreslow.catan.gui.board.Board;
import edu.brown.cs032.sbreslow.catan.gui.devCards.SevenFrame;
import edu.brown.cs032.tmercuri.catan.logic.Player;
import edu.brown.cs032.tmercuri.catan.logic.move.LastMove;
import edu.brown.cs032.tmercuri.catan.logic.move.Move;
import edu.brown.cs032.tmercuri.catan.logic.move.TradeMove;

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
	private /*volatile*/ boolean _isStarting; //the game is actually starting and the client is no longer waiting //TODO: Needs to be volatile?
	private GUI _gui; //the gui that displays the game
	private int _chatPort; //port of the chatServer
	private TradeFrame tradeframe;
	
	/*
	 * Locks
	 */
	private Boolean _startTurnLock; //lock on this to change _startTurn
	private boolean _startTurn; //indicates if it's the players turn
	private Integer _rollLock; //lock on this to set _roll
	private int _roll; //the roll of the player

	private String _ip;	//ip of the computer hosting the game
	
	private Integer _boardLock;
	private Board _board; //a cached version of the most recent version of the board
	private Integer _playersLock;
	private Player[] _players; //a cached version of the most recent version of players

	
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
	public CatanClient(LaunchConfiguration configs) throws IllegalArgumentException, UnknownHostException, IOException{
		
		//setting the fields
		this._p = new Player(configs.getName());
		_players = new Player[3]; //TODO: send number of players over network
		_board = new Board(true);
		_ip = configs.getHostName();
		_isStarting = false;
		
		//setting up socket
		this._socket = new Socket(InetAddress.getByName(_ip).getHostName(), Integer.parseInt(configs.getJoinPort()));

		//setting up readers
		_out = new ObjectOutputStream(_socket.getOutputStream());
		_out.flush();
		_in = new ObjectInputStream(_socket.getInputStream());
		
		//setting up locks
		_rollLock = new Integer(-1);
		_startTurnLock = new Boolean(false);
		_roll = -1;
		_startTurn = false;
		_boardLock = new Integer(-1);
		_playersLock = new Integer(-1);
		
		//connecting
		connect();
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
			} else{
				//got Handshake
				Handshake hs = (Handshake) packet.getObject();
				_chatPort = hs._chatPort;
				_players = new Player[hs._numPlayers];
			}
			
			//sending packet with the player class
			packet = new Packet(Packet.PLAYER, _p, 0);
			_out.writeObject(packet);
			_out.flush();
		} catch (ClassNotFoundException e) {
			throw new IOException(e.getMessage());
		}
	}
	
	/**
	 * Sets the gui
	 * @param gui The gui
	 */
	public void setGUI(GUI gui){
		_gui = gui;
	}
	
	/**
	 * Returns the ip of the server hosting the game
	 * @return ip of the server hosting the game
	 */
	public String getIP(){
		return _ip;
	}
	
	/**
	 * Returns the player associated with this client
	 * @return player associated with this client
	 */
	public Player getPlayer(){
		return _p;
	}
	
	/**
	 * Starts listening to the server once the game has started
	 */
	public void run(){
		try {
			while(_isStarting){
				Packet packet = (Packet) readPacket();
				
				parsePacket(packet);
			}
		} catch (SocketException e){
			//server disconnected
		} catch (IOException e) {
			//not much to do
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			kill();
		}
	}
	
	/**
	 * Parses a packet and performs an appropriate action
	 * @param packet The packet to parse
	 */
	private void parsePacket(Packet packet){
		int type = packet.getType();
		
		if(type == Packet.BOARD){

			synchronized(_boardLock){
				_board = (Board) packet.getObject();
			}
			
			_gui.repaint();
		} else if(type == Packet.PLAYERARRAY){
			
			synchronized(_playersLock){
				_players = (Player[]) packet.getObject();
				updateLocalPlayer();
			}
			
			_gui.repaint();
		} else if(type == Packet.START){
			//TODO: notify the start
			synchronized(_startTurnLock){
				_startTurn = true;
			}
		} else if(type == Packet.ROLL){
			//TODO: notify of roll
			synchronized(_rollLock){
				_roll = ((Integer) packet.getObject()).intValue();
				if(_roll==7 && _p.getResourceCount()>7){
					new SevenFrame(this);
				}
			}
		} else if(type == Packet.ERROR){
			//TODO: notify errors
			System.out.println(String.format("Error: %s", (String) packet.getObject()));
		} else if(type == Packet.SETTLEMENT){
			placeSettlement();
		} else if(type == Packet.ROAD){
			placeRoad();
		} else if(type == Packet.TRADE){
			//TODO: set up notifying trade
			TradeMove trade = (TradeMove) packet.getObject();
			if(tradeframe != null) {
				tradeframe.close();
			}
			tradeframe = new TradeFrame("Trade Received!", trade, this);
		} else if (type == Packet.LASTMOVE){
			if(tradeframe != null) {
				tradeframe.close();
			}
		} else{
			System.out.println(String.format("Unsupported. Got: %s", type));
		}
	}
	
	private void placeSettlement() {
		_gui.getDP().setSelect(2);
	}
	
	private void placeRoad() {
		_gui.getDP().setSelect(1);
	}

	/**
	 * Updates the clients copy of the player to the one in the player array
	 */
	private void updateLocalPlayer(){
		int i = 0;
		
		for(Player p : _players){
			if(p.getName().equals(_p.getName()))
				_p = _players[i];
			i++;
		}
	}
	
	/**
	 * Sends a move to the server for processing
	 * @param move The move to send to the server
	 * @throws IOException If anything goes wrong with the IO
	 * @throws IllegalArgumentException If the Client failed to properly package the object
	 */
	public void sendMove(Move move) throws IllegalArgumentException, IOException{
		if(move instanceof LastMove){
			synchronized(_startTurnLock){
				_startTurn = false;
			}
		}
		_out.writeObject(new Packet(Packet.MOVE, move, 0));
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
		} catch(EOFException e){
			kill();
			throw new SocketException("This shouldn't be printed...(1)");
		}
		catch(SocketException e){
			kill();
			throw new SocketException("This shouldn't be printed...(2)");
		} catch(IOException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
			kill();
			throw new IOException(e.getMessage());
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
	 * Returns the most recent version of players received by the client.
	 * @return A list of players
	 */
	public Player[] getPlayers(){
		
		synchronized(_players){
			return _players;
		}
	}
	
	/**
	 * Returns the most recent version of players received by the client.
	 * @return A Board
	 */
	public Board getBoard(){
		synchronized(_board){
			return _board;
		}
	}
	
	/**
	 * Returns the roll of the player
	 * @return Roll of the player
	 */
	public int getRoll(){
		synchronized(_rollLock){
			return _roll;
		}
	}
	
	/**
	 * Indicates if the client should start the turn
	 * @return True, if the client should start, and false otherwise
	 */
	public boolean getStartTurn(){
		synchronized (_startTurnLock) {
			return _startTurn;
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

	public int getChatPort() {
		//TODO Implement setting the chat port
		return _chatPort;
	}
}
