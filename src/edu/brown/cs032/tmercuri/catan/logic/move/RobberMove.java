/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.tmercuri.catan.logic.move;

import edu.brown.cs032.tmercuri.catan.logic.Player;

/**
 * A move for changing the position of the robber on the board.
 * @author Thomas Mercurio
 */
public class RobberMove extends Move {
    
    private static final long serialVersionUID = 2284263193601317847L;
    
    private final int _newLocation;
    private final Player _toStealFrom;
    
    /**
     * Makes a new RobberMove.
     * @param newLocation the new location for the robber; should be the index of the new tile that the robber needs to go on
     * @param toStealFrom the player on the new robber tile that should be stolen from
     */
    public RobberMove(int newLocation, Player toStealFrom) {
        this._newLocation = newLocation;
        this._toStealFrom = toStealFrom;
    }
}
