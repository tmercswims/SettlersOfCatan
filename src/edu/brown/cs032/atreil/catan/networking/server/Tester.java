package edu.brown.cs032.atreil.catan.networking.server;

import java.io.IOException;


public class Tester {

	@SuppressWarnings("unused")
	private static String usage;

	/**
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		usage = "<port>";
		
		try {
			new CatanServer("localhost", Integer.parseInt(args[0]), 1).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
