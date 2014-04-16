/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package logic.move;

/**
 * A move for building something. Tells the referee to check if the building and location are valid, and then to build it and send the new board to the players.
 * @author Thomas Mercurio
 */
public class BuildMove extends Move {
    
    private static final long serialVersionUID = 3300561535871370673L;
    
    private final int buildType, buildLocation;
    
    /**
     * Makes a new BuildMove.
     * @param buildType the type of this building, from BuildConstants
     * @param buildLocation the location to build this; should be the index of the edge/node in the board's representation
     */
    public BuildMove(int buildType, int buildLocation) {
        this.buildType = buildType;
        this.buildLocation = buildLocation;
    }
}
