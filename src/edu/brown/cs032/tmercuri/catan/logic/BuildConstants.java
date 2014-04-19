/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.tmercuri.catan.logic;

/**
 *
 * @author Thomas Mercurio
 */
public class BuildConstants {
    
    public static final int
    
        /**
         * road
         */
        ROAD = 0,

        /**
         * settlement
         */
        SETTLEMENT = 1,

        /**
         * city
         */
        CITY = 2,

        /**
         * development card
         */
        DEV_CARD = 3;
    
    public static final int[]
            
            /**
             * build road
             */
            BUILD_ROAD = new int[]{0,0,1,0,1},
            
            /**
             * build settlement
             */
            BUILD_SETTLEMENT = new int[]{1,1,1,0,1},
            
            /**
             * build city
             */
            BUILD_CITY = new int[]{2,0,0,3,0},
            
            /**
             * build development card
             */
            BUILD_DEV_CARD = new int[]{1,1,0,1,0};
}
