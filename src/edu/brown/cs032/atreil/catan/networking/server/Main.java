package edu.brown.cs032.atreil.catan.networking.server;

import java.io.IOException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * Usage: <hostname> <port> <number of clients>
		 */
		try{
			String hostname = args[0];
			int port = Integer.parseInt(args[1]);
			int numClients = Integer.parseInt(args[2]);

			@SuppressWarnings("deprecation")
			CatanServer catanServer = new CatanServer(hostname, port, numClients);
			catanServer.start();
		} catch(NumberFormatException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
