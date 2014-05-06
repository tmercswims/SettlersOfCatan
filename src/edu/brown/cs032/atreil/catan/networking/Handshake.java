package edu.brown.cs032.atreil.catan.networking;

import java.io.Serializable;

/**
 * This class contains information about the server
 * that is sent to the client upon connection
 * @author Alex Treil
 *
 */
public class Handshake implements Serializable {
	private static final long serialVersionUID = 2111822348641601002L;
	
	/**
	 * Number of players in the game
	 */
	public final int _numPlayers;
	
	/**
	 * The port of the chat server
	 */
	public final int _chatPort;
	
	/**
	 * Initializes a Handshake with information about the server
	 * @param numPlayers Number of players that will play the game
	 * @param chatPort the port of the chat server. The chat server
	 * has the same hostname as CatanClient
	 */
	public Handshake(int numPlayers, int chatPort){
		_numPlayers = numPlayers;
		_chatPort = chatPort;
	}
}
