/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package logic;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Settlers of Catan Player.
 * @author Thomas Mercurio
 */
public class Player implements Serializable {
    
    private static final long serialVersionUID = 1571495798749582725L;
    
    private final int[] resources;
    private int roadsRemaining, settlementsRemaining, citiesRemaining, victoryPoints, armySize, longestRoad;
    private final String name;
    private boolean isActive;
    
    /**
     * Makes a new player with the given name.
     * @param name the name of this player
     */
    public Player(String name) {
        this.resources = new int[]{0,0,0,0,0};
        this.roadsRemaining = 15;
        this.settlementsRemaining = 5;
        this.citiesRemaining = 4;
        this.victoryPoints = this.armySize = this.longestRoad = 0;
        this.name = name;
        this.isActive = false;
    }
    
    /**
     * Adds resources to this player.
     * @param newResources array of length 5, where each index is a certain resource and its value is the number of each resource to give to this player
     */
    public void addResources(int... newResources) {
        for (int i=0; i<newResources.length; i++) {
            this.resources[i] += newResources[i];
        }
    }
    
    /**
     * Sets whether this Player is the active one.
     * @param isActive is this player active?
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    /**
     * Tells whether this player is active or not.
     * @return true if this player is active, false if not
     */
    public boolean isActive() {
        return this.isActive;
    }
    
    /**
     * Decreases this player's remaining road count by 1.
     */
    public void decRoadCount() {
        this.roadsRemaining--;
    }
    
    /**
     * Gets this player's road count.
     * @return
     */
    public int getRoadCount() {
        return this.roadsRemaining;
    }
    
    /**
     * Decreases this player's remaining settlement count by 1.
     */
    public void decSettlementCount() {
        this.settlementsRemaining--;
    }
    
    /**
     * Gets this player's settlement count.
     * @return
     */
    public int getSettlementCount() {
        return this.settlementsRemaining;
    }
    
    /**
     * Decreases this player's remaining city count by 1.
     */
    public void decCityCount() {
        this.citiesRemaining--;
    }
    
    /**
     * Gets this player's city count.
     * @return
     */
    public int getCityCount() {
        return this.citiesRemaining;
    }
    
    /**
     * Increases this player's victory point count by 1.
     */
    public void incVictoryPoints() {
        this.victoryPoints++;
    }
    
    /**
     * Gets this player's victory point count.
     * @return
     */
    public int getVictoryPoints() {
        return this.victoryPoints;
    }
    
    /**
     * Increases this player's army size by 1.
     */
    public void incArmySize() {
        this.armySize++;
    }
    
    /**
     * Gets this player's army size.
     * @return
     */
    public int getArmySize() {
        return this.armySize;
    }
    
    /**
     * Sets this player's longest road.
     * @param length the length of this player's longest road
     */
    public void setLongestRoad(int length) {
        this.longestRoad = length;
    }
    
    /**
     * Gets this player's longest road length.
     * @return
     */
    public int getLongestRoad() {
        return this.longestRoad;
    }
    
    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof Player) {
            Player that = (Player) other;
            result = (this.getArmySize() == that.getArmySize() && this.getCityCount() == that.getCityCount() && this.getLongestRoad() == that.getLongestRoad() && this.getRoadCount() == that.getRoadCount() && this.getSettlementCount() == that.getSettlementCount() && this.getVictoryPoints() == that.getVictoryPoints());
        }
        return result;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.name);
        return hash;
    }
}
