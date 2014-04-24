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
    
    private final String _playerName, _proposingTo;
    
    /**
     * Makes a new TradeMove.
     * @param playerName the player who played this move
     * @param proposingTo the player that the trade is being proposed to
     * @param giving a resource array indicating what the proposing player is giving up
     * @param receiving a resource array indicating what the proposing player wants in return
     */
    public TradeMove(String playerName, String proposingTo, int[] giving, int[] receiving) {
        _giving = giving;
        _receiving = receiving;
        _playerName = playerName;
        _proposingTo = proposingTo;
    }
    
    /**
     * Gets the name of the player who played this move.
     * @return the name of the player who played this move
     */
    @Override
    public String getPlayerName() {
        return _playerName;
    }
    
    /**
     * Gets the name of the player that the trade is being proposed to.
     * @return the name of the receiving player of the trade
     */
    public String getProposedTo() {
        return _proposingTo;
    }
    
    /**
     * Gets the resource array that tells what the player is giving up.
     * @return the resources that the player is giving up
     */
    public int[] getGiving() {
        return _giving;
    }
    
    /**
     * Gets the resource array that tells what the player wants.
     * @return the resources that the player is receiving
     */
    public int[] getReceiving() {
        return _receiving;
    }
}
