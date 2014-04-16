/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.tmercuri.catan.logic.move;

/**
 * A move for proposing a trade with the other players.
 * @author Thomas Mercurio
 */
public class TradeMove extends Move {
    
    private static final long serialVersionUID = 7764431091933735922L;
    
    private final int[] _giving, _receiving;
    
    private final String _playerName;
    
    /**
     * Makes a new TradeMove.
     * @param playerName the player who played this move
     * @param giving a resource array indicating what the proposing player is giving up
     * @param receiving a resource array indicating what the proposing player wants in return
     */
    public TradeMove(String playerName, int[] giving, int[] receiving) {
        _giving = giving;
        _receiving = receiving;
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
