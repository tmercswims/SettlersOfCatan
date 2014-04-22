/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.tmercuri.catan.logic.move;

/**
 * A move for changing the position of the robber on the board.
 * @author Thomas Mercurio
 */
public class RobberMove extends Move {
    
    private static final long serialVersionUID = 2284263193601317847L;
    
    private final int _newLocation;
    private final String _playerName, _toStealFrom;
    
    /**
     * Makes a new RobberMove.
     * @param playerName the player who played this move
     * @param newLocation the new location for the robber; should be the index of the new tile that the robber needs to go on
     * @param toStealFrom the player on the new robber tile that should be stolen from
     */
    public RobberMove(String playerName, int newLocation, String toStealFrom) {
        _newLocation = newLocation;
        _toStealFrom = toStealFrom;
        _playerName = playerName;
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
     * Gets the tile index where the robber should now go.
     * @return the index of the tile that the robber is going to
     */
    public int getNewLocation() {
        return _newLocation;
    }
    
    /**
     * Gets the name of the player that should be stolen from.
     * @return the name of the player that is being stolen from
     */
    public String getToStealFrom() {
        return _toStealFrom;
    }
}
