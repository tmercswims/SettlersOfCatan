package edu.brown.cs032.atreil.catan.networking.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import edu.brown.cs032.tmercuri.catan.logic.Player;
import edu.brown.cs032.atreil.catan.networking.Packet;

/**
 * This class handles a client communicating with the server. The client will communicate with the server
 * through this class.
 * @author atreil
 *
 */
public class CatanClient {

	private Player _p; //the player class associated with this client
	private Socket _socket; //the socket to communicate with the server
	private ObjectInputStream _in; //the stream to read in from the server
	private ObjectOutputStream _out; //the stream to send messages to the server
	
	/**
	 * Protocols
	 */
	public static final int HANDSHAKE = 0;
	
	/**
	 * Constructs a new Client from an existing player class. After construction, the client will attempt to 
	 * connect to the server on the given port and hostname. If the connection fails, an exception is thrown
	 * @param p The player associated with the client
	 * @param hostname The name of the host server
	 * @param port The port of the host server
	 * @throws IOException If anything goes wrong with communicating with the server
	 * @throws UnknownHostException If the host does not exist
	 * @throws ClassNotFoundException 
	 */
	public CatanClient(Player p, String hostname, int port) throws UnknownHostException, IOException{
		this._p = p;
		this._socket = new Socket(hostname, port);
		
		//setting up readers	
		_out = new ObjectOutputStream(_socket.getOutputStream());
		_out.flush();
		_in = new ObjectInputStream(_socket.getInputStream());
	}
	
	/**
	 * Connects to the server
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void connect() throws IOException, ClassNotFoundException{
		//receive handshake
		Packet packet = (Packet) readPacket();
		
		int cmd = packet.getType();
		
		//check for errors
		if(cmd == Packet.ERROR){
			String error = (String) _in.readObject();
			throw new IOException(error);
		} else if(cmd != Packet.HANDSHAKE){
			//the server is bad so don't connect
			throw new IOException("Bad host");
		}
		
		//sending packet with the player class
		packet = new Packet(Packet.PLAYER, _p);
		_out.writeObject(packet);
		_out.flush();
	}
	
	/**
	 * Reads in a packet from the socket. This method will block until an
	 * object is read.
	 * @return The packet sent to the socket
	 * @throws ClassNotFoundException If the class of the object does not exist
	 * @throws IOException If something goes wrong with communicating with the socket
	 */
	public Packet readPacket() throws ClassNotFoundException, IOException{
		Object o = _in.readObject();
		
		if(o instanceof Packet)
			return (Packet) o;
		else
			throw new IOException("Invalid protocol: Received something other than a packet");
	}
	
	/**
	 * Returns the player's name
	 * @return Player's name
	 */
	public String getPlayerName(){
		return _p.getName();
	}
}
