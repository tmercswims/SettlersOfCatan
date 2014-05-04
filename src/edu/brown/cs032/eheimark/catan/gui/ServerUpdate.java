package edu.brown.cs032.eheimark.catan.gui;

/**
 * The Interface ServerUpdate is used to handle repainting when the server sets new Board information
 * or Player informaton
 */
public interface ServerUpdate {
	/**
	 * Updates GUI with latest info from server.
	 */
	public void serverUpdate();
}
