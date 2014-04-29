/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.tmercuri.catan.logic;

import edu.brown.cs032.atreil.catan.networking.server.CatanServer;
import edu.brown.cs032.sbreslow.catan.gui.board.Board;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Edge.blue;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Edge.orange;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Edge.red;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Edge.white;
import edu.brown.cs032.sbreslow.catan.gui.board.Edge;
import edu.brown.cs032.sbreslow.catan.gui.board.Node;
import edu.brown.cs032.sbreslow.catan.gui.board.Tile;
import static edu.brown.cs032.tmercuri.catan.logic.BuildConstants.*;
import edu.brown.cs032.tmercuri.catan.logic.move.BuildMove;
import edu.brown.cs032.tmercuri.catan.logic.move.DevCardMove;
import edu.brown.cs032.tmercuri.catan.logic.move.FirstMove;
import edu.brown.cs032.tmercuri.catan.logic.move.LastMove;
import edu.brown.cs032.tmercuri.catan.logic.move.MonopolyMove;
import edu.brown.cs032.tmercuri.catan.logic.move.Move;
import edu.brown.cs032.tmercuri.catan.logic.move.RobberMove;
import edu.brown.cs032.tmercuri.catan.logic.move.TradeMove;
import edu.brown.cs032.tmercuri.catan.logic.move.VictoryPointMove;
import edu.brown.cs032.tmercuri.catan.logic.move.YearOfPlentyMove;

import java.awt.Color;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * A Settlers of Catan referee.
 * @author Thomas Mercurio
 */
public class Referee {
    
    private final Player[] _players;
    private final Board _board;
    private final CatanServer _server;
    private Player _road, _army, _activePlayer, _winner;
    private boolean _gameOver, _turnOver;
    private final PairOfDice _dice;
    private final DevCardDeck _deck;
    private Node _startupSettlement = null;
    private int _startUp = 0;
    
    /**
     * Creates a new Referee, with clear fields.
     * @param players all the players in this game
     * @param server the server that will be used to communicate over the network
     */
    public Referee(Player[] players, CatanServer server) {
        _road = _army = null;
        _gameOver = _turnOver = false;
        _dice = new PairOfDice();
        _deck = new DevCardDeck();
        _players = players;
        _board = new Board(true);
        _server = server;
    }
    
    /**
     * Tells whether the game is over.
     * @return true if the game is over, false if not
     */
    public boolean isGameOver() {
        return _gameOver;
    }
    
    /**
     * Starts the game, and runs it until a player wins.
     * @throws SocketException if the server exits
     */
    public void runGame() throws SocketException {
        Color[] colors = new Color[] {red, blue, orange, white};
        int i = 0;
        for (Player p : _players) {
            p.addResources(new int[]{0,0,0,0,0});
            p.setColor(colors[i]);
            i++;
        }
        
        pushPlayers();
        pushBoard();
        
        // roll the dice for each player and order the players based on the rolls
        rollForOrder();
        
        _startUp = 1;
        firstMoves();
        _startUp = 0;
        
        for (int z=0; !_gameOver; z++) {
            System.out.println("Entering main game loop.");
            int currentPlayer = z % _players.length;
            setActivePlayer(currentPlayer);
            while (!_turnOver) {
                Move move = _server.readMove();
                MoveMessage whatHappened = MoveMessage.getMessage(makeMove(move));
                if (whatHappened.isError()) {
                    _server.sendMessage(move.getPlayerName(), "Not allowed - " + whatHappened.getDescription());
                    System.out.println("Not allowed - " + whatHappened.getDescription());
                } else {
                    _server.sendMessage(null, String.format(whatHappened.getDescription(), _activePlayer.getName()));
                    System.out.println(String.format(whatHappened.getDescription(), _activePlayer.getName()));
                }
                pushPlayers();
                pushBoard();
            }
            findWinner();
        }
        //_server.sendMessage(null, String.format("%s has won the game!", _winner));
        _server.sendGameOver("Player "+_winner.getName()+" has won!"
        		+ "  Please return to the Main Menu.");
    }
    
    private void rollForOrder() {
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
    
    private void firstMoves() throws SocketException {
        for (Player p : _players) {
            setActivePlayer(p);
            boolean validSettlement = false;
            while (!validSettlement) {
                _server.startSettlement(p.getName());
                _server.sendMessage(p.getName(), "Click where you would like to build your first settlement.");
                Move move = _server.readMove();
                if (!(move instanceof BuildMove)) {
                    _server.sendMessage(move.getPlayerName(), "Not allowed - you need to build a settlement.");
                    System.out.println("Not allowed - you need to build a settlement.");
                } else {
                    MoveMessage whatHappened = MoveMessage.getMessage(makeMove(move));
                    if (whatHappened.isError()) {
                        _server.sendMessage(move.getPlayerName(), "Not allowed - " + whatHappened.getDescription());
                        System.out.println("Not allowed - " + whatHappened.getDescription());
                    } else {
                        _server.sendMessage(null, String.format(whatHappened.getDescription(), _activePlayer.getName()));
                        System.out.println(String.format(whatHappened.getDescription(), _activePlayer.getName()));
                        validSettlement = true;
                    }
                    pushPlayers();
                    pushBoard();
                }
            }
            boolean validRoad = false;
            while (!validRoad) {
                _server.startRoad(p.getName());
                _server.sendMessage(p.getName(), "Click where you would like to build your first road.");
                Move move = _server.readMove();
                if (!(move instanceof BuildMove)) {
                    _server.sendMessage(move.getPlayerName(), "Not allowed - you need to build a road.");
                    System.out.println("Not allowed - you need to build a road.");
                } else {
                    MoveMessage whatHappened = MoveMessage.getMessage(makeMove(move));
                    if (whatHappened.isError()) {
                        _server.sendMessage(move.getPlayerName(), "Not allowed - " + whatHappened.getDescription());
                        System.out.println("Not allowed - " + whatHappened.getDescription());
                    } else {
                        _server.sendMessage(null, String.format(whatHappened.getDescription(), _activePlayer.getName()));
                        System.out.println(String.format(whatHappened.getDescription(), _activePlayer.getName()));
                        validRoad = true;
                    }
                    pushPlayers();
                    pushBoard();
                }
            }
        }
        _startUp = 2;
        for (int i=_players.length-1;i>=0;i--) {
            Player p = _players[i];
            setActivePlayer(p);
            boolean validSettlement = false;
            while (!validSettlement) {
                _server.startSettlement(p.getName());
                _server.sendMessage(p.getName(), "Click where you would like to build your second settlement.");
                Move move = _server.readMove();
                MoveMessage whatHappened = MoveMessage.getMessage(makeMove(move));
                if (whatHappened.isError()) {
                    _server.sendMessage(move.getPlayerName(), "Not allowed - " + whatHappened.getDescription());
                    System.out.println("Not allowed - " + whatHappened.getDescription());
                } else {
                    _server.sendMessage(null, String.format(whatHappened.getDescription(), _activePlayer.getName()));
                    System.out.println(String.format(whatHappened.getDescription(), _activePlayer.getName()));
                    validSettlement = true;
                }
                pushPlayers();
                pushBoard();
            }
            boolean validRoad = false;
            while (!validRoad) {
                _server.startRoad(p.getName());
                _server.sendMessage(p.getName(), "Click where you would like to build your second road.");
                Move move = _server.readMove();
                MoveMessage whatHappened = MoveMessage.getMessage(makeMove(move));
                if (whatHappened.isError()) {
                    _server.sendMessage(move.getPlayerName(), "Not allowed - " + whatHappened.getDescription());
                    System.out.println("Not allowed - " + whatHappened.getDescription());
                } else {
                    _server.sendMessage(null, String.format(whatHappened.getDescription(), _activePlayer.getName()));
                    System.out.println(String.format(whatHappened.getDescription(), _activePlayer.getName()));
                    validRoad = true;
                }
                pushPlayers();
                pushBoard();
            }
        }
    }
    
    private void setActivePlayer(int p) {
        for (Player pa : _players) {
            pa.setIsActive(false);
        }
        Player player = _players[p];
        player.setIsActive(true);
        _turnOver = false;
        _activePlayer = player;
        pushPlayers();
    }
    
    private void setActivePlayer(Player player) {
        for (Player pa : _players) {
            pa.setIsActive(false);
        }
        player.setIsActive(true);
        _turnOver = false;
        _activePlayer = player;
        pushPlayers();
    }

    private int makeMove(Move move) {
        if (move instanceof FirstMove) {
            FirstMove fMove = (FirstMove) move;
            return startTurn(fMove);
        } else if (move instanceof BuildMove) {
            BuildMove bMove = (BuildMove) move;
            return buildMove(bMove);
        } else if (move instanceof TradeMove) {
            TradeMove tMove = (TradeMove) move;
            return tradeMove(tMove);
        } else if (move instanceof RobberMove) {
            RobberMove rMove = (RobberMove) move;
            return robberMove(rMove);
        } else if (move instanceof DevCardMove) {
            DevCardMove dcMove = (DevCardMove) move;
            return devCardMove(dcMove);
        } else if (move instanceof YearOfPlentyMove) {
            YearOfPlentyMove yopMove = (YearOfPlentyMove) move;
            return yearOfPlentyMove(yopMove);
        } else if (move instanceof MonopolyMove) {
            MonopolyMove mMove = (MonopolyMove) move;
            return monopolyMove(mMove);
        } else if (move instanceof VictoryPointMove) {
            VictoryPointMove vpMove = (VictoryPointMove) move;
            return victoryPointMove(vpMove);
        } else if (move instanceof LastMove) {
            LastMove lMove = (LastMove) move;
            return endTurn(lMove);
        }
        return -1;
    }
    
    private int buildMove(BuildMove move) {
        if (!move.getPlayerName().equals(_activePlayer.getName())) return 999;
        switch (move.getBuildType()) {
            case ROAD:
                Edge e = _board.getEdges()[move.getBuildLocation()];
                if (_startUp != 0) {
                    if (e.isRoad()) return 101;
                    if (!edgeIsNextToNode(e, _startupSettlement)) return 107;
                    _activePlayer.decRoadCount();
                    e.setOwner(_activePlayer);
                    e.grow();
                    return 100;
                } else {
                    if (e.isRoad()) return 101;
                    if (!_activePlayer.hasResources(BUILD_ROAD)) return 102;
                    if (_activePlayer.getRoadCount() == 0) return 103;
                    if (!ownedRoadAdjacent(e)) return 106;
                    _activePlayer.removeResources(BUILD_ROAD);
                    _activePlayer.decRoadCount();
                    e.setOwner(_activePlayer);
                    e.grow();
                    return 100;
                }
            case SETTLEMENT:
                Node ns = _board.getNodes()[move.getBuildLocation()];
                if (_startUp != 0) {
                    if (ns.getVP() == 1 || ns.isOwned()) return 201;
                    if (structureAdjacent(ns)) return 204;
                    _activePlayer.decSettlementCount();
                    _activePlayer.incVictoryPoints();
                    ns.setOwner(_activePlayer);
                    if (_startUp == 2) distributeFirstResources(ns);
                    _startupSettlement = ns;
                    ns.grow();
                    return 200;
                } else {
                    if (ns.getVP() == 1 || ns.isOwned()) return 201;
                    if (!_activePlayer.hasResources(BUILD_SETTLEMENT)) return 202;
                    if (_activePlayer.getSettlementCount() == 0) return 203;
                    if (structureAdjacent(ns)) return 204;
                    if (!ownedRoadAdjacent(ns)) return 206;
                    _activePlayer.removeResources(BUILD_SETTLEMENT);
                    _activePlayer.decSettlementCount();
                    _activePlayer.incVictoryPoints();
                    ns.setOwner(_activePlayer);
                    ns.grow();
                    return 200;
                }
            case CITY:
                Node nc = _board.getNodes()[move.getBuildLocation()];
                if (nc.getVP() == 2) return 301;
                if (!_activePlayer.hasResources(BUILD_CITY)) return 302;
                if (_activePlayer.getCityCount() == 0) return 303;
                if (nc.getVP() == 1 && !_activePlayer.equals(nc.getOwner())) return 305;
                _activePlayer.removeResources(BUILD_CITY);
                _activePlayer.decCityCount();
                _activePlayer.incVictoryPoints();
                nc.grow();
                return 300;
            case DEV_CARD:
                int card = _deck.getCard();
                if (card == -1) return 701;
                _activePlayer.addDevCard(card);
                return 700;
            case ROAD_BUILDER:
                Edge eRB = _board.getEdges()[move.getBuildLocation()];
                if (eRB.isRoad() || _activePlayer.getRoadCount() == 0 || !ownedRoadAdjacent(eRB)) {
                    try {
                        _server.sendRB(move.getPlayerName());
                    } catch (IllegalArgumentException | IOException ex) {
                        System.err.println("ERROR: " + ex.getMessage());
                    }
                    System.out.println(eRB.isRoad() + " " + _activePlayer.getRoadCount() + " " + !ownedRoadAdjacent(eRB));
                    return 601;
                }
                _activePlayer.decRoadCount();
                _activePlayer.incVictoryPoints();
                eRB.setOwner(_activePlayer);
                eRB.grow();
                return 610;
            default:
                System.out.println("build move had bad build type");
                return -1;
        }
    }
    
    private void distributeFirstResources(Node node) {
        int[] resources = new int[]{0,0,0,0,0};
        for (Tile t : node.getTiles()) {
            if (t.getResource() < 5) resources[t.getResource()] += 1;
        }
        _activePlayer.addResources(resources);
    }
    
    private boolean structureAdjacent(Node node) {
        for (Edge e : node.getEdges()) {
            for (Node n : e.getNodes()) {
                if (n.getIndex() != node.getIndex() && n.isOwned())
                    return true;
            }
        }
        return false;
    }
    
    private boolean ownedRoadAdjacent(Node node) {
        for (Edge e : node.getEdges()) {
            if (e.isRoad() && e.getOwner().getName().equals(_activePlayer.getName())) {
                return true;
            }
        }
        return false;
    }
    
    private boolean ownedRoadAdjacent(Edge edge) {
        for (Node n : edge.getNodes()) {
            for (Edge e : n.getEdges()) {
                if (e.getIndex() != edge.getIndex() && e.isRoad() && e.getOwner().equals(_activePlayer)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean edgeIsNextToNode(Edge edge, Node node) {
        for (Node n : edge.getNodes()) {
            if (n.getIndex() == node.getIndex()) 
                return true;
        }
        return false;
    }
    
    private int tradeMove(TradeMove move) {
        if (!move.getPlayerName().equals(_activePlayer.getName()) && !move.getPlayerName().equals(move.getProposedTo())) return 999;
        
        Player robbed = null;
        for (Player p : _players) {
            if (p.getName().equals(move.getPlayerName())) robbed = p;
        }
        
        if (move.getPlayerName().equals(move.getProposedTo())) {
            int[] res = move.getResources();
            if (robbed != null) robbed.removeResources(res);
            return 510;
        }
        
        int[] giving = new int[]{0,0,0,0,0};
        int[] receiving = new int[]{0,0,0,0,0};
        for (int i=0; i<move.getResources().length;i++) {
            int res = move.getResources()[i];
            if (res < 0) {
                giving[i] = 0-res;
            } else if (res > 0) {
                receiving[i] = res;
            }
        }
        
        if (move.getProposedTo().equals("***Merchant***")) {
            int recNum = 0;
            for (int r : receiving) {
                recNum += r;
            }
            if (recNum != 1) return 404;
            
            int giveSum = 0;
            int resIndex = -1;
            for (int i=0; i<giving.length;i++) {
                if (giving[i] > 0) giveSum++;
                resIndex = i;
            }
            if (giveSum != 1 || resIndex == -1) return 405;
            
            switch (giving[resIndex]) {
                case 4:
                    if (_activePlayer.hasResources(giving)) {
                        _activePlayer.removeResources(giving);
                        _activePlayer.addResources(receiving);
                    }
                    return 411;
                case 3:
                    for (Node n : _board.getNodes()) {
                        if (_activePlayer.equals(n.getOwner())) {
                            if (n.getPort() == 5) {
                                if (_activePlayer.hasResources(giving)) {
                                    _activePlayer.removeResources(giving);
                                    _activePlayer.addResources(receiving);
                                }
                            }
                        }
                    }
                    return 412;
                case 2:
                    for (Node n : _board.getNodes()) {
                        if (_activePlayer.equals(n.getOwner())) {
                            if (n.getPort() == resIndex) {
                                if (_activePlayer.hasResources(giving)) {
                                    _activePlayer.removeResources(giving);
                                    _activePlayer.addResources(receiving);
                                }
                            }
                        }
                    }
                    return 412;
                default:
                    return 406;
            }
        }

        Player giver = null;
        Player receiver = null;
        for (Player p : _players) {
            if (p.getName().equals(move.getPlayerName())) {
                giver = p;
            } else if (p.getName().equals(move.getProposedTo())) {
                receiver = p;
            }
        }
        
        switch (move.getType()) {
            case -1:
                if (giver != null && !giver.hasResources(giving)) return 401;
                if (receiver != null && !receiver.hasResources(receiving)) return 402;
                _server.sendTrade(move.getProposedTo(), move);
                return 400;
            case 0:
                return 403;
            case 1:
                if (giver != null && receiver != null) {
                    giver.removeResources(giving);
                    giver.addResources(receiving);
                    receiver.removeResources(receiving);
                    receiver.addResources(giving);
                }
                return 410;
            default:
                System.out.println("build move had bad build type");
                return -1;
        }
    }
    
    private int robberMove(RobberMove move) {
        if (!move.getPlayerName().equals(_activePlayer.getName())) return 999;
        Tile newRobber = _board.getTiles()[move.getNewLocation()];
        Player victim = null;
        if (newRobber.hasRobber()) return 501;
        for (Tile t : _board.getTiles()) {
            if (t.hasRobber()) t.setRobber(false);
        }
        newRobber.setRobber(true);
        if (move.getToStealFrom() != null) {
            for (Node n : newRobber.getNodes()) {
                if (n.getOwner() != null && move.getToStealFrom().equals(n.getOwner().getName())) {
                    victim = n.getOwner();
                }
            }
            if (victim == null) return 502;
        
            boolean foundRes = false;
            int stealType = -1;
            while (!foundRes) {
                stealType = new Random().nextInt(5);
                if (victim.getResources()[stealType] != 0) foundRes = true;
            }
            int[] resSwing = new int[]{0,0,0,0,0};
            resSwing[stealType] = 1;
            victim.removeResources(resSwing);
            _activePlayer.addResources(resSwing);
            return 500;
        }
        
        return -1;
    }
    
    private int yearOfPlentyMove(YearOfPlentyMove move) {
        if (!move.getPlayerName().equals(_activePlayer.getName())) return 999;
        Player played = null;
        for (Player p : _players) {
            if (p.getName().equals(move.getPlayerName()))
                played = p;
        }
        int[] newRes = {0,0,0,0,0};
        newRes[move.getType1()] += 1;
        newRes[move.getType2()] += 1;
        if (played != null) played.addResources(newRes);
        return 620;
    }
    
    private int monopolyMove(MonopolyMove move) {
        if (!move.getPlayerName().equals(_activePlayer.getName())) return 999;
        Player played = null;
        for (Player p : _players) {
            if (p.getName().equals(move.getPlayerName()))
                played = p;
        }
        int add = 0;
        for (Player p : _players) {
            if (!p.equals(played)) {
                add += p.getResources()[move.getType()];
                p.getResources()[move.getType()] = 0;
            }
        }
        int[] newRes = {0,0,0,0,0};
        newRes[move.getType()] = add;
        if (played != null) played.addResources(newRes);
        return 630;
    }
    
    private int victoryPointMove(VictoryPointMove move) {
        if (!move.getPlayerName().equals(_activePlayer.getName())) return 999;
        Player played = null;
        for (Player p : _players) {
            if (p.getName().equals(move.getPlayerName()))
                played = p;
        }
        if (played != null) played.incVictoryPoints();
        return 640;
    }
    
    private int devCardMove(DevCardMove move) {
        if (!move.getPlayerName().equals(_activePlayer.getName())) return 999;
        Player played = null;
        for (Player p : _players) {
            if (p.getName().equals(move.getPlayerName()))
                played = p;
        }
        if (played != null) played.removeDevCard(move.getIndex());
        if (move.getIndex() == 0) {
            if (played != null) played.incLargestArmy();
            if ((played != null) && ((_army == null && played.getArmySize() >= 3) || (played.getArmySize() > _army.getArmySize() && !_army.equals(played)))) _army = played;
        }
        return 600;
    }
    
    private int startTurn(FirstMove move) {
        if (!move.getPlayerName().equals(_activePlayer.getName())) return 999;
        int roll = _dice.roll();
        _server.sendRoll(_activePlayer.getName(), roll);
        if (roll != 7) {
            for (Tile t : _board.getTiles()) {
                if (roll == t.getNum() && !t.hasRobber()) {
                    for (Node n : t.getNodes()) {
                        if (n.isOwned()) {
                            Player p = n.getOwner();
                            int[] newRes = new int[]{0,0,0,0,0};
                            newRes[t.getResource()] += n.getVP();
                            p.addResources(newRes);
                            System.out.println(String.format("%s got %d wheat, %d sheep, %d brick, %d ore, %d wood", p.getName(), newRes[0], newRes[1], newRes[2], newRes[3], newRes[4]));
                        }
                    }
                }
            }
        }
        else{
        	for(Player p: _players){
        		if(p.getResourceCount()>7){
        			try {
						_server.sendSeven(p.getName());
					} catch (IllegalArgumentException | IOException ex) {
                        System.err.println("ERROR: " + ex.getMessage());
					}
        		}
        	}
        }
        return 000;
    }

    private int endTurn(LastMove move) {
        if (!move.getPlayerName().equals(_activePlayer.getName())) return 999;
        _activePlayer.setIsActive(false);
        _server.sendLastMove();
        _turnOver = true;
        return 001;
    }
    
    private void pushPlayers() {
        _server.sendPlayerArray(_players);
    }
    
    private void pushBoard() {
        _server.sendBoard(_board);
    }
    
    private void findWinner() {
        findLongestRoad();
        for (Player p : _players) {
            int score = p.getVictoryPoints();
            if (p.equals(_army)) score+=2;
            if (p.equals(_road)) score+=2;
            if (score >= 10) {
                _gameOver = true;
                _winner = p;
            }
        }
    }
    
    private void findLongestRoad() {
        for (Player p : _players) {
            List<List<Edge>> edgeSets = new ArrayList<>();
            for (Edge e : _board.getEdges()) {
                List<Edge> set = new ArrayList<>();
                findConnected(p, e, set);
                edgeSets.add(set);
            }
            System.out.println(String.format("NUMBER OF ROAD SETS: %d", edgeSets.size()));
            _board.clearEdges();
            for (List<Edge> set : edgeSets) {
                for (int i=0; i<set.size(); i++) {
                    Edge e = set.get(i);
                    if (isEndEdge(e, set) || i == set.size()) {
                        int l = findLongestRoadInSet(e, set, getEndNode(e, set), 0);
                        System.out.println(String.format("LONGEST ROAD FOR %s: %d", p.getName(), l));
                        p.setLongestRoad(l);
                    }
                }
            }
            _board.clearEdges();
            if ((_road == null && p.getLongestRoad() >= 5) || (_road != null && p.getLongestRoad() > _road.getLongestRoad() && !_road.equals(p))) _road = p;
        }
    }
    
    private void findConnected(Player p, Edge e, List<Edge> set) {
        if (!e.wasVisited() && e.isRoad() && e.getOwner().equals(p)) {
            e.setVisited(true);
            set.add(e);
            for (Node n : e.getNodes()) {
                if (n.getOwner() == null || n.getOwner().equals(p)) {
                    for (Edge ed : n.getEdges()) {
                        if (ed.getIndex() != e.getIndex()) {
                            findConnected(p, ed, set);
                        }
                    }
                }
            }
        }
    }
    
    private int findLongestRoadInSet(Edge e, List<Edge> set, Node last, int length) {
        for (Node n : e.getNodes()) {
            if (n.getIndex() != last.getIndex()) {
                for (Edge ed : n.getEdges()) {
                    if (ed.getIndex() != e.getIndex() && set.contains(ed)) {
                        return findLongestRoadInSet(ed, set, n, length+1);
                    }
                }
            }
        }
        return length;
    }
    
    private boolean isEndEdge(Edge e, List<Edge> set) {
        boolean ret = false;
        for (Node n : e.getNodes()) {
            boolean loc = true;
            for (Edge ed : n.getEdges()) {
                if (ed.getIndex() != e.getIndex() && set.contains(ed)) loc = false;
            }
            ret = ret||loc;
        }
        return ret;
    }
    
    private Node getEndNode(Edge e, List<Edge> set) {
        Node fallback = null;
        for (Node n : e.getNodes()) {
            boolean end = true;
            for (Edge ed : n.getEdges()) {
                if (ed.getIndex() != e.getIndex() && set.contains(ed)) end = false;
            }
            if (end) return n;
            fallback = n;
        }
        return fallback;
    }
}
