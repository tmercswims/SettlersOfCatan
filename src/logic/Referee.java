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
    
    private final Player[] _players;
    private Player _road, _army, _activePlayer;
    private boolean _gameOver;
    private final PairOfDice _dice;
    
    /**
     * Creates a new Referee, with clear fields.
     * @param players all the players in this game
     */
    public Referee(Player[] players) {
        this._road = this._army = null;
        this._gameOver = false;
        this._dice = new PairOfDice();
        this._players = players;
    }
    
    public void runGame() {
        chooseOrder();
        
        // get each player to choose 2 places to build a settlement and road
        
        for (int z=0; !_gameOver; z++) {
            
        }
    }
    
    private void chooseOrder() {
        for (Player p : _players) {
            p.setInitRoll(_dice.roll());
        }
        
        Arrays.sort(_players, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return Integer.compare(o1.getInitRoll(), o2.getInitRoll());
            }
        });
    }
    
    private void setActivePlayer(int p) {
        Player player = _players[p];
        
    }
    
    private void startTurn() {
        
    }

    private void makeMove(Move move) {
        
    }

    private void endTurn() {
        
    }

    private void calculateVP(Player player) {
        
    }

    private void findLongestRoad(Player player) {
        
    }
}
