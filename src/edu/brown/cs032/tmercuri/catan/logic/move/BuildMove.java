/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.tmercuri.catan.logic.move;

/**
 * A move for building something. Tells the referee to check if the building and location are valid, and then to build it and send the new board to the players.
 * @author Thomas Mercurio
 */
public class BuildMove extends Move {
    
    private static final long serialVersionUID = 3300561535871370673L;
    
    private final int _buildType, _buildLocation;
    private final String _playerName;
    
    /**
     * Makes a new BuildMove.
     * @param playerName the player who played this move
     * @param buildType the type of this building, from BuildConstants
     * @param buildLocation the location to build this; should be the index of the edge/node in the board's representation
     */
    public BuildMove(String playerName, int buildType, int buildLocation) {
        _buildType = buildType;
        _buildLocation = buildLocation;
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
