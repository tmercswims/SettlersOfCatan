/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.tmercuri.catan.logic;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Settlers of Catan Player.
 * @author Thomas Mercurio
 */
public class Player implements Serializable {
    
    private static final long serialVersionUID = 1571495798749582725L;
    
    private final int[] _resources;
    private int _roadsRemaining, _settlementsRemaining, _citiesRemaining, _victoryPoints, _armySize, _longestRoad;
    private final String _name;
    private boolean _isActive;
    private int _initRoll;
    
    /**
     * Makes a new player with the given _name.
     * @param name the _name of this player
     */
    public Player(String name) {
        this._resources = new int[]{0,0,0,0,0};
        this._roadsRemaining = 15;
        this._settlementsRemaining = 5;
        this._citiesRemaining = 4;
        this._victoryPoints = this._armySize = this._longestRoad = 0;
        this._name = name;
        this._isActive = false;
    }
    
    /**
     * Adds resources to this player.
     * @param newResources array of length 5, where each index is a certain resource and its value is the number of each resource to give to this player
     */
    public void addResources(int[] newResources) {
        for (int i=0; i<newResources.length; i++) {
            this._resources[i] += newResources[i];
        }
    }
    
    /**
     * Removes the number of each resource that is given from this player.
     * @param toRemove a resource array of how many of each resource to remove
     */
    public void removeResources(int[] toRemove) {
        for (int i=0; i<5; i++) {
            _resources[i] -= toRemove[i];
            if (_resources[i] < 0) {
                _resources[i] = 0;
            }
        }
    }
    
    /**
     * Tells whether the player has at least the resources given.
     * @param requiredResources a resource array that the player must at least meet, but may exceed
     * @return true if the player meets or exceeds requiredResources, false if not
     */
    public boolean hasResources(int[] requiredResources) {
        for (int i=0; i<5; i++) {
            if (_resources[i] < requiredResources[i]) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Sets whether this Player is the active one.
     * @param isActive is this player active?
     */
    public void setIsActive(boolean isActive) {
        this._isActive = isActive;
    }
    
    /**
     * Tells whether this player is active or not.
     * @return true if this player is active, false if not
     */
    public boolean isActive() {
        return this._isActive;
    }
    
    /**
     * Decreases this player's remaining road count by 1.
     */
    public void decRoadCount() {
        this._roadsRemaining--;
    }
    
    /**
     * Gets this player's road count.
     * @return remaining roads
     */
    public int getRoadCount() {
        return this._roadsRemaining;
    }
    
    /**
     * Decreases this player's remaining settlement count by 1.
     */
    public void decSettlementCount() {
        this._settlementsRemaining--;
    }
    
    /**
     * Gets this player's settlement count.
     * @return
     */
    public int getSettlementCount() {
        return this._settlementsRemaining;
    }
    
    /**
     * Decreases this player's remaining city count by 1.
     */
    public void decCityCount() {
        this._citiesRemaining--;
    }
    
    /**
     * Gets this player's city count.
     * @return
     */
    public int getCityCount() {
        return this._citiesRemaining;
    }
    
    /**
     * Increases this player's victory point count by 1.
     */
    public void incVictoryPoints() {
        this._victoryPoints++;
    }
    
    /**
     * Gets this player's victory point count.
     * @return
     */
    public int getVictoryPoints() {
        return this._victoryPoints;
    }
    
    /**
     * Increases this player's army size by 1.
     */
    public void incArmySize() {
        this._armySize++;
    }
    
    /**
     * Gets this player's army size.
     * @return
     */
    public int getArmySize() {
        return this._armySize;
    }
    
    /**
     * Gets this player's _name.
     * @return 
     */
    public String getName() {
        return this._name;
    }
    
    /**
     * Sets this player's longest road.
     * @param length the length of this player's longest road
     */
    public void setLongestRoad(int length) {
        this._longestRoad = length;
    }
    
    /**
     * Gets this player's longest road length.
     * @return
     */
    public int getLongestRoad() {
        return this._longestRoad;
    }
    
    /**
     * Sets this players initial roll for sorting.
     * @param roll 
     */
    public void setInitRoll(int roll) {
        this._initRoll = roll;
    }
    
    /**
     * Gets this players initial roll for sorting.
     */
    public int getInitRoll() {
        return this._initRoll;
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
        hash = 23 * hash + Objects.hashCode(this._name);
        return hash;
    }
}
