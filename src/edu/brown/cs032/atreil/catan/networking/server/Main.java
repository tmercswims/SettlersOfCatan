package edu.brown.cs032.atreil.catan.networking.server;

import java.io.IOException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*
		 * Usage: <hostname> <port> <number of clients>
		 */
		
		if(args.length != 3)
			System.err.println("Invalid number of arguments");
		else{
			try{
				String hostname = args[0];
				int port = Integer.parseInt(args[1]);
				int numClients = Integer.parseInt(args[2]);
				
				CatanServer catanServer = new CatanServer(hostname, port, numClients);
				System.out.println("Running...");
				//catanServer.accept();
				catanServer.start();
				System.out.println("Finished!");
			} catch(NumberFormatException e){
				System.err.println("Invalid number");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
