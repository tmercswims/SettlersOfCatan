/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.tmercuri.catan.logic.move;

/**
 *
 * @author Thomas Mercurio
 */
public class YearOfPlentyMove extends Move {
    
    private static final long serialVersionUID = 2660024999523979439L;

    private final int _type1, _type2;
    private final String _playerName;
    
    public YearOfPlentyMove(String playerName, int type1, int type2) {
        _type1 = type1;
        _type2 = type2;
        _playerName = playerName;
    }
    
    public int getType1() {
        return _type1;
    }
    
    public int getType2() {
        return _type2;
    }
    
    @Override
    public String getPlayerName() {
        return _playerName;
    }
    
}
