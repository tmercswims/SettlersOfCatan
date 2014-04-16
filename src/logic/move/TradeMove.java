/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package logic.move;

/**
 * A move for proposing a trade with the other players.
 * @author Thomas Mercurio
 */
public class TradeMove extends Move {
    
    private static final long serialVersionUID = 7764431091933735922L;
    
    private final int[] giving, receiving;
    
    /**
     * Makes a new TradeMove.
     * @param giving a resource array indicating what the proposing player is giving up
     * @param receiving a resource array indicating what the proposing player wants in return
     */
    public TradeMove(int[] giving, int[] receiving) {
        this.giving = giving;
        this.receiving = receiving;
    }
}
