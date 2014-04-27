/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.tmercuri.catan.logic.move;

/**
 *
 * @author Thomas Mercurio
 */
public class DevCardMove extends Move {
    
    private static final long serialVersionUID = 727514557442984259L;
    
    private final String _playerName;
    private final int _index;
    
    public DevCardMove(String playerName, int index) {
        _playerName = playerName;
        _index = index;
    }
    
    /**
     * Gets the index of the type of card.
     * @return 
     */
    public int getIndex() {
        return _index;
    }

    @Override
    public String getPlayerName() {
        return _playerName;
    }
    
}
