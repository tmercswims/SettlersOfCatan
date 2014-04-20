/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.tmercuri.catan.logic;

import java.util.HashMap;
import java.util.Map;

/**
 * An enum for conveying reasons that a move cannot be done.
 * @author Thomas Mercurio
 */
public enum MoveMessage {
    
    /**
     * generic error
     */
    MESSAGE_NEG1(-1, true, "something happened."),
    
    /**
     * started turn
     */
    MESSAGE_000(000, false, " has started their turn.\n"),
    
    /**
     * ended turn
     */
    MESSAGE_999(999, false, " has ended their turn.\n"),
    
    /**
     * road
     */
    MESSAGE_100(100, false, " built a road.\n"),
    MESSAGE_101(101, true, "there is already a road there.\n"),
    MESSAGE_102(102, true, "you do not have enough resources for a road.\n"),
    MESSAGE_103(103, true, "you do not have any roads left to build.\n"),
    MESSAGE_106(106, true, "you do not have a road that reaches that edge.\n"),
    
    /**
     * settlement
     */
    MESSAGE_200(200, false, " built a settlement.\n"),
    MESSAGE_201(201, true, "there is already a settlement there.\n"),
    MESSAGE_202(202, true, "you do not have enough resources for a settlement.\n"),
    MESSAGE_203(203, true, "you do not have any settlements left to build.\n"),
    MESSAGE_204(204, true, "there is a structure next to that intersection.\n"),
    MESSAGE_206(206, true, "you do not have a road that reaches that intersection.\n"),
    
    /**
     * city
     */
    MESSAGE_300(300, false, " built a city.\n"),
    MESSAGE_301(301, true, "there is already a city there.\n"),
    MESSAGE_302(302, true, "you do not have enough resources for a city.\n"),
    MESSAGE_303(303, true, "you do not have any cities left to build.\n"),
    MESSAGE_305(305, true, "the settlement at that intersection is not yours.\n");
    
    private final int _code;
    private final boolean _isError;
    private final String _description;
    private static final Map<Integer, MoveMessage> _messages = new HashMap<>();

    static {
        for (MoveMessage mess : MoveMessage.values()) {
            _messages.put(mess._code, mess);
        }
    }
    
    MoveMessage(final int code, final boolean isError, final String description) {
        _code = code;
        _isError = isError;
        _description = description;
    }
    
    /**
     * Gets the MoveMessage that is associated with the given code.
     * @param code an int that is the code you want to look up
     * @return the MoveMessage that code corresponds to
     */
    public static MoveMessage getMessage(int code) {
        return _messages.get(code);
    }
    
    /**
     * Gets the code of the MoveMessage.
     * @return the int that is the code
     */
    public int getCode() {
        return _code;
    }
    
    /**
     * Tells whether this MoveMessage indicates an error.
     * @return true if this MoveMessage is an error, false if not
     */
    public boolean isError() {
        return _isError;
    }
    
    /**
     * Gets the description of the MoveMessage.
     * @return a String that is the description of the message
     */
    public String getDescription() {
        return _description;
    }
}
