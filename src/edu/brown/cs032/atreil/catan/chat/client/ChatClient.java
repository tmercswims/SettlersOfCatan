package edu.brown.cs032.atreil.catan.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import edu.brown.cs032.tmercuri.catan.logic.Player;

/**
 * This class handles receiving messages from other players and sending messages
 * to other players
 * @author Alex Treil
 *
 */
public class ChatClient {

	private Socket _socket;
	private BufferedReader _in;
	private PrintWriter _out;
	
	/**
	 * Creates a new ChatClient that will connect to a ChatServer. Upon creation,
	 * the constructor will attempt to connect to the ChatServer.
	 * @param hostname The host of the ChatServer
	 * @param port The port of the ChatServer
	 * @param player The Player that is associated with this client.
	 * @throws UnknownHostException If the host does not exist
	 * @throws IOException If something goes wrong with the IO
	 */
	public ChatClient(String hostname, int port, Player player) throws UnknownHostException, IOException{
		_socket = new Socket(hostname, port);
		
		//set up streams
		_in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
		_out = new PrintWriter(_socket.getOutputStream(), true);
		
		_out.println(player.getName());
	}
	
	/**
	 * Reads a line from the chat server
	 * @return Line from the chat server
	 * @throws IOException If anything goes wrong with the IO
	 */
	public String readLine() throws IOException{
		return _in.readLine();
	}
	
	/**
	 * Sends a message to the chat
	 * @param message The message to send
	 */
	public void println(String message){
		_out.println(message);
	}
	
	/**
	 * Closes down associated streams and sockets
	 * @throws IOException If anything goes wrong with the IO
	 */
	public void kill() throws IOException{
		_in.close();
		_out.close();
		_socket.close();
	}
}
