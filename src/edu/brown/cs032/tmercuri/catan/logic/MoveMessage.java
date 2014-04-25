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
    MESSAGE_000(000, false, "%s started their turn."),
    
    /**
     * ended turn
     */
    MESSAGE_001(001, false, "%s completed their turn."),
    
    /**
     * it's not your turn
     */
    MESSAGE_999(999, true, "it is not your turn."),
    
    /**
     * road
     */
    MESSAGE_100(100, false, "%s built a road."),
    MESSAGE_101(101, true, "there is already a road there."),
    MESSAGE_102(102, true, "you do not have enough resources for a road."),
    MESSAGE_103(103, true, "you do not have any roads left to build."),
    MESSAGE_106(106, true, "you do not have a road that reaches that edge."),
    MESSAGE_107(107, true, "you must build the road touching your previous settlement."),
    
    /**
     * settlement
     */
    MESSAGE_200(200, false, "%s built a settlement."),
    MESSAGE_201(201, true, "there is already a settlement there."),
    MESSAGE_202(202, true, "you do not have enough resources for a settlement."),
    MESSAGE_203(203, true, "you do not have any settlements left to build."),
    MESSAGE_204(204, true, "there is a structure next to that intersection."),
    MESSAGE_206(206, true, "you do not have a road that reaches that intersection."),
    
    /**
     * city
     */
    MESSAGE_300(300, false, "%s built a city."),
    MESSAGE_301(301, true, "there is already a city there."),
    MESSAGE_302(302, true, "you do not have enough resources for a city."),
    MESSAGE_303(303, true, "you do not have any cities left to build."),
    MESSAGE_305(305, true, "the settlement at that intersection is not yours.\n"),
    
    /**
     * trading
     */
    MESSAGE_400(400, false, "%s proposed a trade."),
    MESSAGE_401(401, true, "you do not have enough resources for that trade."),
    MESSAGE_402(402, true, "the other player does not have enough resources for that trade."),
    MESSAGE_403(403, true, "the other player rejected the trade."),
    MESSAGE_404(404, true, "you must only receive one resource in order to trade with the merchant."),
    MESSAGE_405(405, true, "you must only give up one type of resource in order to trade with the merchant."),
    MESSAGE_406(406, true, "you do not have access to that port."),
    MESSAGE_410(410, false, "%s completed a trade."),
    MESSAGE_411(411, false, "%s traded with the bank."),
    MESSAGE_412(412, false, "%s traded with a port."),
    
    /**
     * robber
     */
    MESSAGE_500(500, false, "%s moved the robber."),
    MESSAGE_501(501, true, "the robber is already on that hex."),
    MESSAGE_502(502, true, "that player does not have a building on that hex.");
    
    private final int _code;
    private final boolean _isError;
    private final String _description;
    private static final Map<Integer, MoveMessage> _messages = new HashMap<>();

    static {
        for (MoveMessage message : MoveMessage.values()) {
            _messages.put(message._code, message);
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
