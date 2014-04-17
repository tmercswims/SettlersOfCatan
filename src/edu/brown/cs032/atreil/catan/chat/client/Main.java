package edu.brown.cs032.atreil.catan.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import edu.brown.cs032.tmercuri.catan.logic.Player;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String usage = "<port>";
		
		if(args.length != 1){
			System.err.println("Not enough argumnets. Usage " + usage);
			return;
		}
		
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Name?: ");
			String name = in.readLine();
			
			ChatClient client = new ChatClient("localhost", Integer.parseInt(args[0]), new Player(name));
			new ReceiveThread(client).start();
			
			while(true){
				String send = in.readLine();
				client.println(send);
			}
		} catch(NumberFormatException e){
			System.err.println("Invalid number");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Prints out what is sent from Server
	 * @author Alex Treil
	 *
	 */
	private static class ReceiveThread extends Thread{
		
		private ChatClient _client;
		private boolean _running;
		
		public ReceiveThread(ChatClient client){
			_client = client;
		}
		
		public void run(){
			_running = true;
			
			while(_running){
				try {
					System.out.println(_client.readLine());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
