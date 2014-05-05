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
    MESSAGE_NEG1(-1, true, "Something happened."),
    
    /**
     * started turn
     */
    MESSAGE_000(000, false, "%s started their turn."),
    
    /**
     * ended turn
     */
    MESSAGE_001(001, false, "%s completed their turn."),
    
    /**
     * everybody needs to drop
     */
    MESSAGE_996(996, true, "Everybody has not dropped resources yet."),
    
    /**
     * you need to move the robber
     */
    MESSAGE_997(997, true, "You need to move the robber before you do anything else."),
    
    /**
     * you haven't rolled yet
     */
    MESSAGE_998(998, true, "You have not rolled the die yet."),
    
    /**
     * it's not your turn
     */
    MESSAGE_999(999, true, "It is not your turn."),
    
    /**
     * road
     */
    MESSAGE_100(100, false, "%s built a road."),
    MESSAGE_101(101, true, "There is already a road there."),
    MESSAGE_102(102, true, "You do not have enough resources for a road."),
    MESSAGE_103(103, true, "You do not have any roads left to build."),
    MESSAGE_106(106, true, "You do not have a road that reaches that edge."),
    MESSAGE_107(107, true, "You must build the road touching your previous settlement."),
    MESSAGE_108(108, true, "You cannot build a road in the ocean."),
    
    /**
     * settlement
     */
    MESSAGE_200(200, false, "%s built a settlement."),
    MESSAGE_201(201, true, "There is already a settlement there."),
    MESSAGE_202(202, true, "You do not have enough resources for a settlement."),
    MESSAGE_203(203, true, "You do not have any settlements left to build."),
    MESSAGE_204(204, true, "There is a structure next to that intersection."),
    MESSAGE_206(206, true, "You do not have a road that reaches that intersection."),
    
    /**
     * city
     */
    MESSAGE_300(300, false, "%s built a city."),
    MESSAGE_301(301, true, "There is already a city there."),
    MESSAGE_302(302, true, "You do not have enough resources for a city."),
    MESSAGE_303(303, true, "You do not have any cities left to build."),
    MESSAGE_305(305, true, "Yhe settlement at that intersection is not yours.\n"),
    
    /**
     * trading
     */
    MESSAGE_400(400, false, "%s proposed a trade to %s."),
    MESSAGE_401(401, true, "You do not have enough resources for that trade."),
    MESSAGE_402(402, true, "The other player does not have enough resources for that trade."),
    MESSAGE_403(403, false, "%s rejected %s's trade proposal."),
    MESSAGE_404(404, true, "You must only receive one resource in order to trade with the merchant."),
    MESSAGE_405(405, true, "You must only give up one type of resource in order to trade with the merchant."),
    MESSAGE_406(406, true, "You do not have access to that port."),
    MESSAGE_407(407, true, "You need to give up one type of resource in order to trade with the merchant."),
    MESSAGE_410(410, false, "%s accepted %s's trade proposal."),
    MESSAGE_411(411, false, "%s traded with the bank."),
    MESSAGE_412(412, false, "%s traded with a port."),
    
    /**
     * robber
     */
    MESSAGE_500(500, false, "%s moved the robber and stole from %s."),
    MESSAGE_501(501, true, "The robber is already on that hex."),
    MESSAGE_502(502, true, "That player does not have a building on that hex."),
    MESSAGE_503(503, true, "There are no players on that hex, so you could not steal."),
    MESSAGE_510(510, false, "%s had resources stolen by the robber."),
    
    /**
     * dev card
     */
    MESSAGE_600(600, false, "%s played a development card."),
    MESSAGE_601(601, true, "You cannot build a road there."),
    MESSAGE_610(610, false, "%s played a Road Builder."),
    MESSAGE_620(620, false, "%s played a Year of Plenty."),
    MESSAGE_630(630, false, "%s played a Monopoly."),
    MESSAGE_640(640, false, "%s played a Victory Point."),
    MESSAGE_700(700, false, "%s bought a development card."),
    MESSAGE_701(701, true, "There are no development cards left."),
    MESSAGE_702(702, true, "You do not have enough resources for a development card.");
    
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
