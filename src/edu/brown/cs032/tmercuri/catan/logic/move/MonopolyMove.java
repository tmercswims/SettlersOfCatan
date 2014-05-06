/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.tmercuri.catan.logic.move;

/**
 * A move for Monopoly dev card.
 * @author Thomas Mercurio
 */
public class MonopolyMove extends Move {

	private static final long serialVersionUID = 5130676700128920752L;

	private final String _playerName;
	private final int _type;

	public MonopolyMove(String playerName, int type) {
		_playerName = playerName;
		_type = type;
	}

	/**
	 * Gets the type of resource that the player wants to take.
	 * @return 
	 */
	public int getType() {
		return _type;
	}

	/**
	 * Gets the name of the player who played this move.
	 * @return 
	 */
	@Override
	public String getPlayerName() {
		return _playerName;
	}

}
