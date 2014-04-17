package edu.brown.cs032.atreil.catan.chat.server;

import java.io.IOException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String usage = "<port> <max clients>";
		
		if(args.length != 2){
			System.err.println("Not enough arguments. " + usage);
			return;
		}
		
		try{
			ChatServer server = new ChatServer(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			server.start();
			System.out.println("Started server");
		} catch(NumberFormatException e){
			System.err.println("Not a number");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
