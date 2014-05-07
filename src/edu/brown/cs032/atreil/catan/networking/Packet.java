package edu.brown.cs032.atreil.catan.networking;

import java.io.Serializable;

import edu.brown.cs032.sbreslow.catan.gui.board.Board;
import edu.brown.cs032.tmercuri.catan.logic.Player;
import edu.brown.cs032.tmercuri.catan.logic.move.Move;
import edu.brown.cs032.tmercuri.catan.logic.move.TradeMove;


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
	 * The object is a {@link Player}
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
	 * The object is a {@link Handshake} object. Signals a successful connection
	 */
	public static int HANDSHAKE = 3;
	
	/**
	 * The object is a string identifying the issue
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
	
	
	/**
	 * Indicates that the active player must place their initial settlement
	 */
	public static int SETTLEMENT = 10;
	
	
	/**
	 * Indicates that the active player must place their initial road
	 */
	public static int ROAD = 11;
	
	/**
	 * Indicates a trade move with the player
	 */
	public static int TRADE = 12;
	
	/**
	 * Player is ending their move; null
	 */
	public static int LASTMOVE = 13;
	
	/**
	 * A seven is rolled so the player should drop their resources; null
	 */
	public static int SEVEN = 14;
    
	/**
	 * Bad roll builder play; null
	 */
    public static int BAD_RB = 15;
    
    /**
     * Game is over; message to follow
     */
    public static int GAME_OVER = 16;
    
    /**
     * Int of the index of where the settlement in the pre-game phase was built
     */
    public static int START_SETTLE = 17;
    
    /**
     * The pre-game is over; boolean
     */
    public static int END_START = 18;
    
    /**
     * All players have ended their turn and the player may 
     * end their turn; null
     */
    public static int END_SEVEN = 19;
    
    /**
     * The second road of a road builder needs to be highlighted; null
     */
    public static int SECOND_RB = 20;
	
	/*********************************************/
	private final int _type; //the type of the object
	private final Object _o; //the object
	
	/**
	 * Constructs a new packet that will store an object of the specified type
	 * @param type The type of the object being sent. Use the static integers defined in the
	 * Packet class.
	 * @param type The type of the object.
	 * @param o The object itself. If the Object and the type do not match, then an IllegalArgumentException is thrown
	 * @throws IllegalArgumentException If the Object and integer type do not match or are invalid.
	 */
	public Packet(int type, Object o){
		
		//check to see type and object match
		validate(type, o);
		
		_o = o;
		_type = type;
	}
	
	/**
	 * @deprecated discontinued the use of uids. Use the two argument constructor
	 * that ignores the uid<p>
	 * Constructs a new packet that will store an object of the specified type
	 * @param type The type of the object being sent. Use the static integers defined in the
	 * Packet class.
	 * @param type The type of the object.
	 * @param o The object itself. If the Object and the type do not match, then an IllegalArgumentException is thrown
	 * @param uid unique identifier
	 * @throws IllegalArgumentException If the Object and integer type do not match or are invalid.
	 */
	@Deprecated
	public Packet(int type, Object o, int uid) throws IllegalArgumentException{
		
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
			if(!(o instanceof Board))
				throw new IllegalArgumentException("Given object is not of type Board");
		} else if(type == Packet.ROLL){
			if(!(o instanceof Integer))
				throw new IllegalArgumentException("Given object is not of type Integer");
		} else if(type == Packet.ERROR){
			if(!(o instanceof String))
				throw new IllegalArgumentException("Given object is not of type String");
		} else if(type == Packet.HANDSHAKE){
			if(!(o instanceof Handshake))
				throw new IllegalArgumentException("Given object is not of type Handshake");
		} else if(type == Packet.STARTGAME){
			if(o != null)
				throw new IllegalArgumentException("Given object is not null");
		} else if(type == Packet.SETTLEMENT){
			if(o != null)
				throw new IllegalArgumentException("Given object is not null");
		} else if(type == Packet.ROAD){
			if(o != null)
				throw new IllegalArgumentException("Given object is not null");
		} else if(type == Packet.TRADE){
			if(!(o instanceof TradeMove))
				throw new IllegalArgumentException("Given object is not of type TradeMove");
		} else if(type == Packet.LASTMOVE){
			if(o != null)
				throw new IllegalArgumentException("Given object is not null");
		} else if(type == Packet.SEVEN){
			if(o != null)
				throw new IllegalArgumentException("Given object is not null");
		} else if(type == Packet.BAD_RB){
			if(o != null)
				throw new IllegalArgumentException("Given object is not null");
		} else if(type == Packet.GAME_OVER){
			if(!(o instanceof String)){
				throw new IllegalArgumentException("Given object is not a String");
			}
		} else if (type == Packet.START_SETTLE) {
            if(!(o instanceof Integer)){
				throw new IllegalArgumentException("Given object is not a int");
			}
        } else if (type == Packet.END_START) {
            if(!(o instanceof Boolean)){
				throw new IllegalArgumentException("Given object is not a boolean");
			}
        } else if(type == Packet.END_SEVEN){
        	if(o != null)
        		throw new IllegalArgumentException("Given object is not null");
        } else if (type == Packet.SECOND_RB) {
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
	 * @deprecated the unique id was used for early debugging but isn't needed anymore. Will
	 * always return -1<p>
	 * 
	 * Gets the unique id of the packet
	 * @return unique if of the packet.
	 */
	@Deprecated
	public int getUID(){
		return -1;
	}
	
	/**
	 * Returns the object in this method.
	 * @return The object contained in the packet
	 */
	public Object getObject(){
		return _o;
	}
}
