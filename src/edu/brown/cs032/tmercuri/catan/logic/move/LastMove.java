/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.tmercuri.catan.logic.move;

/**
 * The last move of a turn. Tells the referee to end the current player's turn and signal the next player.
 * @author Thomas Mercurio
 */
public class LastMove extends Move {

	private static final long serialVersionUID = 1193677566233444614L;

	private final String _playerName;

	/**
	 * A new LastMove, with the player who played it.
	 * @param playerName the player who played this move
	 */
	public LastMove(String playerName) {
		_playerName = playerName;
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
