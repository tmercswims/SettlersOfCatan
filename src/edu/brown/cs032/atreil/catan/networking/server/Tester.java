package edu.brown.cs032.atreil.catan.networking.server;

import java.io.IOException;


public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String usage = "<port>";
		
		try {
			new CatanServer("localhost", Integer.parseInt(args[0]), 1).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
