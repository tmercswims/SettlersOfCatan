package edu.brown.cs032.atreil.catan.networking.client;

import java.io.IOException;
import java.net.UnknownHostException;

import edu.brown.cs032.atreil.catan.networking.Packet;
import edu.brown.cs032.tmercuri.catan.logic.Player;

/**
 * This class runs a client. This is purely used for proof of concept.
 * @author Alex Treil
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/**
		 * Usage: <player name> <hostname> <port>
		 */
		
		if(args.length != 3)
			System.err.println("Invalid number of arguments");
		else{
			try{
				String playername = args[0];
				String hostname = args[1];
				int port = Integer.parseInt(args[2]);
				
				CatanClient catanClient = new CatanClient(new Player(playername), hostname, port);
				//catanClient.connect();

				while(true){
					Packet packet = catanClient.readPacket();
					
					//retrieve type of message
					int type = packet.getType();
					
					//check
					if(type == Packet.MESSAGE){
						System.out.println((String)packet.getObject());
					}
				}
			} catch(NumberFormatException e){
				System.err.println("Received a non-integer for the port number");
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
