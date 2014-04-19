package edu.brown.cs032.atreil.catan.networking.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import edu.brown.cs032.atreil.catan.networking.Packet;
import edu.brown.cs032.tmercuri.catan.logic.Player;
import edu.brown.cs032.tmercuri.catan.logic.move.Move;

/**
 * This class handles interactions with a client. The server will use this to 
 * communicate with the client.
 * @author atreil
 *
 */
public class ClientManager extends Thread {

	private Player _p; //the player class associated with the client
	private Socket _client; //the socket to listen and communicate with
	private ObjectInputStream _in; //read messages from client
	private ObjectOutputStream _out; //sends data to client
	private ClientPool _pool; //contains the other clients
	private boolean _running; 
	
	/**
	 * This constructor initializes a new client from which the server can listen to
	 * and communicate with. This won't connect to the client until start() is called.
	 * @param client The socket to send and receive messages from
	 * @param p The player to associate this client with
	 * @throws IOException If anything goes wrong with the connection with the client
	 */
	public ClientManager(ClientPool pool, Socket client) throws IOException{
		this._client = client;
		this._pool = pool;
		
		//setting up readers and writers
		this._in = new ObjectInputStream(_client.getInputStream());
		this._out = new ObjectOutputStream(_client.getOutputStream());
	}
	

	/**
	 * Communicates with a newly connected client in order to get player information
	 * @return A Player class associated with the client
	 * @throws IOException If anything goes wrong with the IO
	 * @throws IllegalArgumentException If the username is already taken
	 */
	private void welcome() throws IOException, IllegalArgumentException{
		
		/*
		 * When a client connects to the server, the server should send a WELCOME string, 
		 * acknowledging the connection. The client then should send their name/player class
		 */
		
		//someone is trying to connect
		_pool.addUpdate("Client is trying to connect...");
		
		//send welcome message
		_out.writeObject(new Packet(Packet.HANDSHAKE, null));
		_out.flush();
		
		try {
			// expect packet with player class
			Packet packet = (Packet) _in.readObject();
			
			if(packet.getType() == Packet.PLAYER){
				//we can assume that the packet contains a player object
				this._p = (Player) packet.getObject();
				
				//check to make sure that the name is not already taken
				if(_pool.containsKey(_p.getName()))
					throw new IllegalArgumentException("User name already taken! Dropping client...");
			} else
				throw new ClassNotFoundException();
			
			//client successfully connected!
			_pool.addUpdate(String.format("Client %s successfully connected!", _p.getName()));
			
		} catch (ClassNotFoundException e) {
			//invalid protocol; probably an internal error
			String msg = "Server failed. Dropping client...";
			throw new IllegalArgumentException(msg);
		} catch (ClassCastException e){
			//the user probably sent something other than a Player object
			String msg = "Invalid protocol: Expected a Player object in the packet. Dropping client...";
			throw new IllegalArgumentException(msg);
		}
	}
	
	/**
	 * Starts the ClientManager by listening for inputs
	 */
	public void run(){
		try {
			welcome();
			
			//add to the pool
			_pool.addClient(_p.getName(), this);
			
			//start to listen for commands from the player
			_running = true;
			
			while(_running){
				try{
					Packet packet = (Packet) _in.readObject();
					parsePacket(packet);
				}  catch (ClassNotFoundException e) {
					//invalid protocol; probably an internal error
					String msg = "Server failed. Disconnecting...";
					sendError(msg);
					kill();
				} catch (ClassCastException e){
					//the user probably sent something other than a Player object
					String msg = "Invalid protocol: Expected a Player object in the packet";
					sendError(msg);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			_pool.addUpdate(e.getMessage());
		} catch(IllegalArgumentException e){
			try {
				//invalid protocol; stop
				sendError(e.getMessage());
				_pool.addUpdate(String.format("Error: %s", e.getMessage()));
				kill();
			} catch (IOException e1) {
				_pool.addUpdate(String.format("Error: %s", e.getMessage()));
			}
		}
	}
	
	/**
	 * Sends a simple text message to the client. This is a low level method and should not
	 * be used
	 * @param packet The string to send to the client
	 * @throws IOException If anything goes wrong with sending the message
	 */
	public void send(Packet packet) throws IOException{
		_out.writeObject(packet);
		_out.flush();
	}

	/**
	 * Returns the player name associated with the client
	 * @return The name of the player
	 */
	public String getPlayerName(){
		return _p.getName();
	}
	
	/**
	 * Opens a packet, determines what type it is, and performs the appropriate action
	 * @param packet The packet to open
	 * @throws IOException If something goes wrong with the io
	 */
	private void parsePacket(Packet packet) throws IOException{
		int type = packet.getType();
		
		if(type == Packet.MOVE){
			System.out.println("Got move");
			_pool.addMove((Move) packet.getObject());
		} else{
			//only moves can be sent; send an error
			String msg = "Invalid protocol. Expected a move object";
			sendError(msg);
			_pool.addUpdate(msg);
		}
	}
	
	/**
	 * Sends an error to the client in the form of a message explaining the error
	 * @param msg A message explaining the error
	 * @throws IOException If anything goes wrong with sending the message
	 */
	private void sendError(String msg) throws IOException{
		_out.writeObject(new Packet(Packet.ERROR, msg));
		_out.flush();
	}
	
	/**
	 * Kills this client by removing it from the pool and freeing up its resources
	 * @throws IOException If anything goes wrong with the IO
	 */
	public void kill() throws IOException{
		_pool.remove(this);
		_in.close();
		_out.close();
		_client.close();
		_pool.addUpdate(String.format("Player %s disconnected", _p.getName()));
	}
	
	/**
	 * Returns the player class
	 * @return Player class
	 */
	public Player getPlayer(){
		return _p;
	}
}