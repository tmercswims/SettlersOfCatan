/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.tmercuri.catan.logic.move;

/**
 * The first move of each turn. Tells the referee to roll the dice and distribute resources.
 * @author Thomas Mercurio
 */
public class FirstMove extends Move {
    
    private static final long serialVersionUID = 904345047938488589L;
    
    private final String _playerName;
    
    /**
     * A new LastMove, with the player who played it.
     * @param playerName the player who played this move
     */
    public FirstMove(String playerName) {
        _playerName = playerName;
    }
    
    /**
     * Gets the name of the player who played this move.
     * @return 
     */
    public String getPlayerName() {
        return _playerName;
    }
}
