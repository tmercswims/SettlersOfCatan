/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package logic;

import java.util.Arrays;
import java.util.Comparator;
import logic.move.Move;

/**
 * A Settlers of Catan referee.
 * @author Thomas Mercurio
 */
public class Referee {
    
    private final Player[] players;
    private Player road, army, activePlayer;
    private boolean gameOver;
    private final PairOfDice dice;
    
    /**
     * Creates a new Referee, with clear fields.
     * @param players all the players in this game
     */
    public Referee(Player[] players) {
        this.road = this.army = null;
        this.gameOver = false;
        this.dice = new PairOfDice();
        this.players = players;
    }
    
    public void runGame() {
        chooseOrder();
        
        // get each player to choose 2 places to build a settlement and road
        
        for (int z=0; !gameOver; z++) {
            
        }
    }
    
    private void chooseOrder() {
        for (Player p : players) {
            p.setInitRoll(dice.roll());
        }
        
        Arrays.sort(players, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return Integer.compare(o1.getInitRoll(), o2.getInitRoll());
            }
        });
    }
    
    private void setActivePlayer(int p) {
        Player player = players[p];
        
    }
    
    private void startTurn() {
        
    }

    private void makeMove(Move move) {
        
    }

    private void endTurn() {
        
    }

    private void calculateVP(Player player) {
        
    }

    private void findLongestRoad() {
        
    }
}
