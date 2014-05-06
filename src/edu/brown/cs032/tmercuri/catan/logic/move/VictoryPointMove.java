/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.tmercuri.catan.logic.move;

/**
 * A move for playing a victory point card.
 * @author Thomas Mercurio
 */
public class VictoryPointMove extends Move {

	private static final long serialVersionUID = 7245826125048625202L;

	private final String _playerName;

	public VictoryPointMove(String playerName) {
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
