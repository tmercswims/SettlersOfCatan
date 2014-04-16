/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package logic;

import java.util.Random;

/**
 * A pair of dice that can be rolled over and over.
 * @author Thomas Mercurio
 */
public class PairOfDice {
    
    private final Random die1, die2;
    
    public PairOfDice() {
        this.die1 = new Random();
        this.die2 = new Random();
    }
    
    /**
     * Rolls the pair of dice, returning the sum of their rolls.
     * @return the sum of rolling two dice
     */
    public int roll() {
        int roll1 = die1.nextInt(6) + 1;
        int roll2 = die2.nextInt(6) + 1;
        return roll1 + roll2;
    }
}
