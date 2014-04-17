package edu.brown.cs032.atreil.catan.networking;

import java.io.Serializable;

import edu.brown.cs032.sbreslow.catan.gui.board.Board;
import edu.brown.cs032.tmercuri.catan.logic.Player;
import edu.brown.cs032.tmercuri.catan.logic.move.Move;


/**
 * This class acts as a message between a server and a client. It tells the client/server
 * what object is being sent.
 * 
 * @author Alex Treil
 *
 */
public class Packet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3486606709069969779L;

	/**
	 * The object is a {@link}Player
	 */
	public static int PLAYER = 0;
	
	/**
	 * The object is a move
	 */
	public static int MOVE = 1;
	
	/**
	 * The object is a string
	 */
	public static int MESSAGE = 2;
	
	/**
	 * The object is an empty object. Signals a successful connection
	 */
	public static int HANDSHAKE = 3;
	
	/**
	 * The object is an Integer specifying an error code //TODO: implement the error codes
	 */
	public static int ERROR = 4;
	
	/**
	 * The object is null. signifies that a player should start their turn
	 */
	public static int START = 5;
	
	/**
	 * The object will be an array
	 */
	public static int PLAYERARRAY = 6;
	
	/**
	 * The object will be a board
	 */
	public static int BOARD = 7;
	
	/**
	 * The object will be an Integer specifying the roll
	 */
	public static int ROLL = 8;
	
	/**
	 * Indicates that the game is about to start and that the clients should launch the board.
	 * Object should be null
	 */
	public static int STARTGAME = 9;
	
	/*********************************************/
	private final int _type; //the type of the object
	private final Object _o; //the object
	private boolean accessed = false; //determines if the object has been given out
	
	/**
	 * Constructs a new packet that will store an object of the specified type
	 * @param type The type of the object being sent. Use the static integers defined in the
	 * Packet class.
	 * @param type The type of the object.
	 * @param o The object itself. If the Object and the type do not match, then an IllegalArgumentException is thrown
	 * @throws IllegalArgumentException If the Object and integer type do not match or are invalid.
	 */
	public Packet(int type, Object o) throws IllegalArgumentException{
		
		//check to see the type and object match
		validate(type, o);
		
		this._o = o;
		this._type = type;
	}
	
	/**
	 * This inner method checks to make sure that the user entered a valid type and that the object matches that type
	 * @param type The type of the object as defined by the static fields in {@link}Packet
	 * @param o The object in the packet
	 * @throws IllegalArgumentException If the type is invalid or the object does not match the given type
	 */
	private void validate(int type, Object o) throws IllegalArgumentException{
		
		if(type == Packet.PLAYER){
			if(!(o instanceof Player))
				throw new IllegalArgumentException("Given object is not of type Player!");
		} else if(type == Packet.MESSAGE){
			if(!(o instanceof String))
				throw new IllegalArgumentException("Given object is not of type string");
		} else if(type == Packet.MOVE){
			if(!(o instanceof Move))
				throw new IllegalArgumentException("Given object is not of type move");
		} else if(type == Packet.START){
			if(o != null)
				throw new IllegalArgumentException("Given object is not null");
		} else if(type == Packet.PLAYERARRAY){
			if(!(o instanceof Player[]))
				throw new IllegalArgumentException("Given object is not of type Player[]");
		} else if(type == Packet.BOARD){
			//TODO: is board serializable? and all of its fields?
			if(!(o instanceof Board))
				throw new IllegalArgumentException("Given object is not of type Board");
		} else if(type == Packet.ROLL){
			if(!(o instanceof Integer))
				throw new IllegalArgumentException("Given object is not of type Integer");
		} else if(type == Packet.ERROR){
			if(!(o instanceof Integer))
				throw new IllegalArgumentException("Given object is not of type Integer");
		}
		else if(type == Packet.HANDSHAKE){
			if(o != null)
				throw new IllegalArgumentException("Given object is not null");
		} else if(type == Packet.STARTGAME){
			if(o != null)
				throw new IllegalArgumentException("Given object is not null");
		}
		else
			throw new IllegalArgumentException("Given invalid type!");
	}
	
	/**
	 * Returns the type of the object stored in the packet
	 * @return The type of the object as dictated by the Enums in the Packet class
	 */
	public int getType(){
		return _type;
	}
	
	/**
	 * Returns the object in this method. May only be called once. Any future attempts
	 * at accessing the object will throw an IllegalArgumentException
	 * @return The object contained in the packet
	 */
	public Object getObject(){
		
		if(!accessed){
			accessed = true;
			return _o;
		} else
			throw new IllegalArgumentException("Object has already been accessed.");
	}
}
