package edu.brown.cs032.atreil.catan.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This protected class handles the communication with the client
 * @author Alex Treil
 *
 */
class ChatClientManager extends Thread{

	private ClientPool _pool; //keeps track of the other clients
	private Socket _client; //communicates with the client
	private BufferedReader _in;
	private PrintWriter _out;
	private boolean _running;
	private String _name;
	
	/**
	 * Initializes a new client manager. This won't start communicating with
	 * the client until start() is called
	 * @param pool The pool to add the client manager to 
	 * @param client The actual client to connect to.
	 * @throws IOException If anything goes wrong with trying to connect to the client
	 */
	public ChatClientManager(ClientPool pool, Socket client) throws IOException{
		this._pool = pool;
		this._client = client;
		this._name = "";
		
		//set up streams
		_in = new BufferedReader(new InputStreamReader(_client.getInputStream()));
		_out = new PrintWriter(_client.getOutputStream(), true);
	}
	
	/**
	 * Receives messages from the client
	 */
	public void run(){
		try {
			//get name
			_name = _in.readLine();
			
			//start listening
			_running = true;
			_pool.addClient(_name, this);
			
			while(_running){
				try{
					String message = _in.readLine();
					if(message!=null)
						parseMessage(message);
				} catch(IOException e){
					// TODO: 
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Parses the message and sends it appropriately. If it is a private message, then 
	 * sends the message to that particular user
	 */
	private void parseMessage(String message){
		
		String[] messageArray = message.split(" ");
		if(messageArray.length >= 3){
			//could be private message
			if(messageArray[0].equals("/m") || messageArray[0].equals("/msg")){
				//private message
				String playername = messageArray[1];
				
				try{
					String toSend = extractMessage(messageArray);
					_pool.send(playername, toSend, this);
					return;
				} catch(IllegalArgumentException e){
					//TODO: throw back something to client
					synchronized(_out){
						_out.println(String.format("Server: No player exists with the name '%s'", playername));
					}
				}
			}
		}
		
		//otherwise, broadcast to all
		//System.out.println(message);
		_pool.sendAll(message, this);
	}
	
	/**
	 * Removes the commands from a private message and extracts the message
	 * @param message The array where the first two indices contain the private
	 * message keyword and the user name.
	 * @return The message to send to the user
	 */
	private String extractMessage(String[] message){
		String result = "";
		
		for(int i = 2; i < message.length; i++){
			result += message[i] + " ";
		}
		
		return ("*whisper* " + result);
	}
	
	/**
	 * Returns the player's name associated with the client
	 * @return Player name
	 */
	public synchronized String getPlayerName(){
		return _name;
	}
	
	/**
	 * Sends a message to the user
	 * @param message The message to send
	 */
	public synchronized void send(String message){
		synchronized(_out){
			_out.println(message);
		}
	}
}
