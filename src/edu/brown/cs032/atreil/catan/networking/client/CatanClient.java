
package edu.brown.cs032.atreil.catan.networking.client;

import java.awt.Window;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import edu.brown.cs032.atreil.catan.gui.trade.TradeFrame;
import edu.brown.cs032.atreil.catan.networking.Handshake;
import edu.brown.cs032.atreil.catan.networking.Packet;
import edu.brown.cs032.eheimark.catan.gui.GUI;
import edu.brown.cs032.eheimark.catan.gui.GUIFrame;
import edu.brown.cs032.eheimark.catan.gui.misc.AlertFrame;
import edu.brown.cs032.eheimark.catan.gui.tutorial.Tutorial;
import edu.brown.cs032.eheimark.catan.launch.LaunchConfiguration;
import edu.brown.cs032.eheimark.catan.launch.screens.JoinLoadingMenu;
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
	private boolean _isStarting; //the game is actually starting and the client is no longer waiting
	private GUI _gui; //the gui that displays the game
	private TradeFrame tradeframe;

	/*
	 * Locks
	 */
	private Boolean _startTurnLock; //lock on this to change _startTurn
	private Integer _rollLock; //lock on this to set _roll
	private int _roll; //the roll of the player
	private boolean _servicing; //whether or not the client is done with the given move
	private Integer _servicingLock; //lock for _servicing
	private JoinLoadingMenu _joinLoadingMenu; //the joinLoadingMenu to give updates to
	private Integer _guiLock;


	private String _ip;	//ip of the computer hosting the game

	private Integer _boardLock;
	private Board _board; //a cached version of the most recent version of the board
	private Integer _playersLock;
	private Player[] _players; //a cached version of the most recent version of players
	private GUIFrame _frame;
	
	private boolean _isRunning; //keeps track if the server is running
	private final Integer _isRunningLock;
	private boolean _inLobby; //keeps track if we are in the lobby waiting for connections
	private final Integer _inLobbyLock;
	private boolean _inGame; //keeps track if we are currently playing a game
	private final Integer _inGameLock;
	
	private int _x;
	private int _y;
	
	private ArrayList<Window> _frameList = new ArrayList<Window>();


	

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
		
		//locks
		_isRunningLock = new Integer(-1);
		_inGameLock = new Integer(-1);
		_inLobbyLock = new Integer(-1);
		
		_isRunning = true;
		_inLobby = true;
		_inGame = false;
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
		_players = new Player[3];
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
		_boardLock = new Integer(-1);
		_playersLock = new Integer(-1);
		_servicingLock = new Integer(-1);
		_guiLock = new Integer(-1);

		//connecting
		//locks
		_isRunningLock = new Integer(-1);
		_inGameLock = new Integer(-1);
		_inLobbyLock = new Integer(-1);
		
		_isRunning = true;
		_inLobby = true;
		_inGame = false;
		
		_x = 600/23;
		_y = 600/14;
		
		//connect();
	}

	/**
	 * Sets the joinLoadingMenu
	 * @param joinLoadingMenu
	 */
	public void setLoadingMenu(JoinLoadingMenu joinLoadingMenu){
		_joinLoadingMenu = joinLoadingMenu;
	}

	public void setFrame(GUIFrame frame){
		_frame = frame;
	}

	/**
	 * Starts listening to the server once the game has started
	 */
	public void run(){
		try {

			connect();
			
			//preloading stage
			readServerMessagePrivate();

			//starting game
			while(getInGame()){
				Packet packet = (Packet) readPacket();

				//check to make sure that client is not servicing
				synchronized(_servicingLock){
					while(_servicing){
						try {
							_servicingLock.wait();
						} catch (InterruptedException e) {
							System.out.println("Interrupted in Client");
							_servicing = false;
						}
					}
					_servicing = true;
				}

				if(getInGame()){
					parsePacket(packet);
				}
			}
		} catch (SocketException|EOFException e){
			//server disconnected
			new AlertFrame("Server disconnected!", _frame);
		} catch (IOException e) {
			new AlertFrame(String.format("Error: %s", e.getMessage()), _frame);
			//not much to do
		} catch (ClassNotFoundException e) {
			new AlertFrame(String.format("Error: %s", e.getMessage()), _frame);
		} finally{
			kill();
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
			} else{
				//got Handshake
				Handshake hs = (Handshake) packet.getObject();
				_players = new Player[hs._numPlayers];
			}

			//sending packet with the player class
			packet = new Packet(Packet.PLAYER, _p);
			_out.reset();
			_out.writeObject(packet);
			_out.flush();
		} catch (ClassNotFoundException e) {
			throw new IOException(e.getMessage());
		} catch(SocketException e){
			throw new IOException("Failed to connect to server");
		}
	}

	/**
	 * Sets the gui
	 * @param gui The gui
	 */
	public void setGUI(GUI gui){
		_gui = gui;
		
		synchronized(_guiLock){
			_guiLock.notifyAll();
		}
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
	
	private boolean checkDelta(int i, int j) {
		if((_x!=i/23) || (_y!=j/14)){
			_x = i/23;
			_y = j/14;
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Parses a packet and performs an appropriate action.
	 * <p> If you want to call this, you need to be <i>absolutely sure<i> that
	 * the client is done with the previous instruction. Use {@link confirmPacket} once
	 * you are done servicing a request (the actual gui shoudl call it)
	 * <p>
	 * @param packet The packet to parse
	 */
	private void parsePacket(Packet packet){

		//we are servicing an instruction
		synchronized(_servicingLock){
			_servicing = true;
		}

		int type = packet.getType();

		
		//determining what we got in the packet
		
		if(type == Packet.BOARD){

			synchronized(_boardLock){
				_board = (Board) packet.getObject();
			}

			//if gui is initialized, we are safe to call it
			if(_gui != null) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						_gui.getDP().setSize(_frame.getContentPane().getWidth()*600/1000, 
								_frame.getContentPane().getHeight()*600/825);
						if(checkDelta(_frame.getContentPane().getWidth()*600/1000, 
								_frame.getContentPane().getHeight()*600/825)){
							System.err.println("HERE");
							_gui.getDP().setResize(_frame.getContentPane().getWidth()*600/1000, 
									_frame.getContentPane().getHeight()*600/825);
						}
						_board.resize(_gui.getDP().getWidth(), _gui.getDP().getHeight());
					}
					
				});
				
				_gui.updateBoard();
			} else {
				//otherwise, wait
				while(_gui == null){
					synchronized(_guiLock){
						try {
							_guiLock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						_gui.getDP().setSize(_frame.getContentPane().getWidth()*600/1000, 
								_frame.getContentPane().getHeight()*600/825);
						if(checkDelta(_frame.getContentPane().getWidth()*600/1000, 
								_frame.getContentPane().getHeight()*600/825)){
							System.err.println("HERE");
							_gui.getDP().setResize(_frame.getContentPane().getWidth()*600/1000, 
									_frame.getContentPane().getHeight()*600/825);
						}
						_board.resize(_gui.getDP().getWidth(), _gui.getDP().getHeight());
					}
					
				});
				
				_gui.updateBoard();
			}
			//TODO: make sure _gui finishes
		} else if(type == Packet.PLAYERARRAY){

			synchronized(_playersLock){
				_players = (Player[]) packet.getObject();
				updateLocalPlayer();
			}

			if(_gui != null) {
				_gui.updatePlayers();
			}
			else {
				while(_gui == null){
					synchronized(_guiLock){
						try {
							_guiLock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				_gui.updatePlayers();
			}
			
			
			//TODO: make sure _gui finishes
		} else if(type == Packet.ROLL){
			//TODO: notify of roll
			synchronized(_rollLock){
				_roll = ((Integer) packet.getObject()).intValue();
                if (_roll == 7) {
                    System.out.println("Thinks roll is 7.");
                    System.out.println("Roll is " + _roll);
                    System.out.println("client setSelect(0)");
                    _gui.getDP().setSelect(0);
                    _gui.getActivePlayer().disableRoll();
                }
				/*if(_roll==7 && _p.getResourceCount()>7){
					new SevenFrame(this);
				}*/
				confirmPacket();
			}
		} else if (type == Packet.SEVEN){
			_frameList.add(new SevenFrame(this, getFrame()));
			confirmPacket();
		} else if(type == Packet.ERROR){
			//TODO: notify errors
			System.out.println(String.format("Error: %s", (String) packet.getObject()));
			confirmPacket();
		} else if(type == Packet.SETTLEMENT){
			placeSettlement();
			confirmPacket();
		} else if(type == Packet.ROAD){
			placeRoad();
			confirmPacket();
		} else if(type == Packet.TRADE){
			//TODO: set up notifying trade
			TradeMove trade = (TradeMove) packet.getObject();
			if(tradeframe != null) {
				tradeframe.close();
			}
			tradeframe = new TradeFrame("Trade Received!", trade, this, getFrame());
			_frameList.add(tradeframe);
			confirmPacket();
		} else if (type == Packet.LASTMOVE){
			if(tradeframe != null) {
				_frameList.remove(tradeframe);
				tradeframe.close();
			}
			confirmPacket();
		} else if (type == Packet.BAD_RB) {
			_gui.getDP().decRBCount();
			confirmPacket();
		} else if (type == Packet.MESSAGE) {
			_gui.getChat().addMessage((String)packet.getObject());
			confirmPacket();
		} else if (type == Packet.GAME_OVER) {
			new AlertFrame((String)packet.getObject(),_frame);
			_isStarting = false;
			kill();
			confirmPacket();
		} else if (type == Packet.START_SETTLE) {
            int index = (Integer) packet.getObject();
            _gui.getDP().setFirstSettlement(index);
			confirmPacket();
        } else if (type == Packet.END_START) {
            boolean startUp = (Boolean) packet.getObject();
            _gui.getDP().setStartUp(startUp);
			confirmPacket();
        } else if (type == Packet.END_SEVEN){
        	_gui.getActivePlayer().enableRoll();
        }
		else{
			System.out.println(String.format("Unsupported. Got: %s", type));
			confirmPacket();
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

		synchronized(_players){
			for(Player p : _players){
				if(p.getName().equals(_p.getName()))
					_p = _players[i];
				i++;
			}
		}
	}

	public void sendMessage(String message) throws IOException{
		synchronized(_out){
			_out.reset();
			_out.writeObject(new Packet(Packet.MESSAGE, message));
			_out.flush();
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
//				_startTurn = false;
			}
		}

		synchronized(_out){
			_out.reset();
			_out.writeObject(new Packet(Packet.MOVE, move));
			_out.flush();
		}
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
			Object o;

			synchronized(_in){
				o = _in.readObject();
			}

			if(o instanceof Packet)
				return (Packet) o;
			else
				throw new IOException("Invalid protocol: Received something other than a packet");
		} catch(EOFException e){
			throw new SocketException(String.format("Reached EOF: %s", e.getMessage()));
		} catch(SocketException e){
			throw new SocketException(e.getMessage());
		} catch(IOException e){
			throw new IOException(e.getMessage());
		}
	}

	/**
	 * Reads an update from the Server while waiting for the game to launch. This will also change
	 * the {@code _isStarting} field to true if the server is about to start the game.
	 * @return An update
	 * @throws IOException If anything goes wrong with IO
	 */
	private void readServerMessagePrivate() throws IOException{
		
		try {
			while(getInLobby()){
				Packet p = (Packet) readPacket();
				int type = p.getType();

				if(type == Packet.MESSAGE) {
					String message = (String) p.getObject();
					System.out.println(String.format("readServerMessage: %s", message));
					//TODO: print message in JTextArea
					_joinLoadingMenu.updateJTextArea(message);
				}
				else if(type == Packet.STARTGAME){
					setInGame(true);
					setInLobby(false);
					System.out.println("STARTTINGGGGG GAME");
					//TODO: launch game panel
					//return "Starting the game\n";
					_joinLoadingMenu.launchGame();
				} else if(type == Packet.ERROR){
					//TODO: print error in JTextArea
					String error = (String) p.getObject();
					System.out.println(String.format("readServerMessageError: %s", error));
					_joinLoadingMenu.updateJTextArea(error);
					//return (String) p.getObject();
				} else if(type == Packet.START){
					//TODO: set active player
					System.out.println("YOU ARE ACTIVE PLAYER");
					_joinLoadingMenu.updateJTextArea("You are the first player");
					//return "It is your turn. Make a move";
				}
				else
					throw new IOException(String.format("Bad server protocol. Got code: %s", type));
			}
		} catch (ClassNotFoundException e) {
			throw new IOException(e.getMessage());
		} catch (SocketException e){
			//TODO:
			kill();
		}
	}

	/**
	 * @deprecated client automatically reads updates
	 * @return An update
	 * @throws IOException If anything goes wrong with IO
	 */	
	@Deprecated
	public String readServerMessage() throws IOException{

		try {
			Packet p = (Packet) readPacket();
			int type = p.getType();

			if(type == Packet.MESSAGE) {
				System.out.println("GOT A MESSAGE");
				return (String) p.getObject();
			}
			else if(type == Packet.STARTGAME){
				_isStarting = true;
				System.out.println("STARTTINGGGGG GAME");
				return "Starting the game\n";
			} else if(type == Packet.ERROR){
				System.out.println("ERROR PACKET RECEIVED!!");
				return (String) p.getObject();
			} else if(type == Packet.START){
				System.out.println("YOU ARE ACTIVE PLAYER");
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
	 * Returns the player's name
	 * @return Player's name
	 */
	public String getPlayerName(){
		synchronized(_playersLock){
			return _p.getName();
		}
	}

	/**@deprecated use one of {@link getInLobby}, {@link getInGame}, or {@link getIsRunning}<p>
	 * Indicates whether or not the game is about to start
	 * @return true, if the game has started, and false otherwise
	 */
	@Deprecated
	public boolean getIsStarting(){
		return _isStarting;
	}

//	public int getChatPort() {
//		return _chatPort;
//	}

	public GUI getGUI() {
		return _gui;
	}
	
	public GUIFrame getFrame() {
		return _frame;
	}

	/**
	 * Confirms that the client is finished with the last server instruction
	 * and is ready for the next instruction
	 */
	public void confirmPacket(){

		synchronized(_servicingLock){
			_servicing = false;
			_servicingLock.notifyAll();
		}
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
	 * Kills the client by shutting down the socket and associated
	 * streams
	 */
	public void kill(){
		
		if(getIsRunning()){
			try{
				
				for(Window w: _frameList){
					w.setVisible(false);
					w.dispose();
				}
				
				if(getInLobby()){
					_joinLoadingMenu.updateJTextArea("Server disconnected");
				}
				
				setIsRunning(false);
				setInGame(false);
				setInLobby(false);

				//synchronized(_in){
					_in.close();
				//}

				//synchronized(_out){
					_out.close();
				//}

				//synchronized(_socket){
					_socket.close();
				//}

				synchronized(_servicingLock){
					_servicing = false;
				}
			} catch(IOException e){
				//not much to do
				System.err.println(String.format("Fatal exception: %s", e.getMessage()));
			}
		}
	}

	public void setXY(int x, int y) {
		_x = x;
		_y = y;
	}

	public void addFrame(Window frame) {
		_frameList.add(frame);
	}
	
	public void rmFrame(Window frame) {
		_frameList.remove(frame);
	}
}
