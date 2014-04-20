/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.tmercuri.catan.logic;

import edu.brown.cs032.atreil.catan.networking.server.CatanServer;
import edu.brown.cs032.sbreslow.catan.gui.board.Board;
import edu.brown.cs032.sbreslow.catan.gui.board.Edge;
import edu.brown.cs032.sbreslow.catan.gui.board.Node;
import edu.brown.cs032.sbreslow.catan.gui.board.Tile;
import static edu.brown.cs032.tmercuri.catan.logic.BuildConstants.*;
import edu.brown.cs032.tmercuri.catan.logic.move.BuildMove;
import edu.brown.cs032.tmercuri.catan.logic.move.FirstMove;
import edu.brown.cs032.tmercuri.catan.logic.move.LastMove;
import edu.brown.cs032.tmercuri.catan.logic.move.Move;
import edu.brown.cs032.tmercuri.catan.logic.move.RobberMove;
import edu.brown.cs032.tmercuri.catan.logic.move.TradeMove;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * A Settlers of Catan referee.
 * @author Thomas Mercurio
 */
public class Referee {
    
    private final Player[] _players;
    private final Board _board;
    private final CatanServer _server;
    private Player _road, _army, _activePlayer;
    private boolean _gameOver, _turnOver;
    private final PairOfDice _dice;
    
    /**
     * Creates a new Referee, with clear fields.
     * @param players all the players in this game
     * @param server the server that will be used to communicate over the network
     */
    public Referee(Player[] players, CatanServer server) {
        _road = _army = null;
        _gameOver = _turnOver = false;
        _dice = new PairOfDice();
        _players = players;
        _board = new Board();
        _server = server;
    }
    
    /**
     * Starts the game, and runs it until a player wins.
     */
    public void runGame() {
        for (Player p : _players) {
            p.addResources(new int[]{20,20,20,20,20});
        }
        pushPlayers();
        pushBoard();
        rollForOrder();
        
        for (int z=0; !_gameOver; z++) {
            int currentPlayer = z % _players.length;
            setActivePlayer(currentPlayer);
            while (!_turnOver) {
                Move move = _server.readMove();
                MoveMessage whatHappened = MoveMessage.getMessage(makeMove(move));
                if (whatHappened.isError()) {
                    _server.sendError(_activePlayer.getName(), whatHappened.getDescription());
                    System.out.println(whatHappened.getDescription());
                } else {
                    _server.sendError(null, whatHappened.getDescription());
                    System.out.println(whatHappened.getDescription());
                }
                pushPlayers();
                pushBoard();
            }
        }
    }
    
    /**
     * Tells whether the game is over.
     * @return true if the game is over, false if not
     */
    public boolean isGameOver() {
        return _gameOver;
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
    
    private void setActivePlayer(int p) {
        Player player = _players[p];
        _activePlayer = player;
        _server.startTurn(player.getName());
    }
    
    private void setActivePlayer(Player player) {
        _activePlayer = player;
        _server.startTurn(player.getName());
    }

    private int makeMove(Move move) {
        if (move instanceof FirstMove) {
            return startTurn();
        } else if (move instanceof BuildMove) {
            BuildMove bMove = (BuildMove) move;
            return buildMove(bMove);
        } else if (move instanceof TradeMove) {
            TradeMove tMove = (TradeMove) move;
            System.err.println("Can't trade yet.");
            return tradeMove(tMove);
        } else if (move instanceof RobberMove) {
            RobberMove rMove = (RobberMove) move;
            System.err.println("Can't rob yet.");
            return robberMove(rMove);
        } else if (move instanceof LastMove) {
            return endTurn();
        }
        return -1;
    }
    
    private int buildMove(BuildMove move) {
        System.out.println("Player '" + _activePlayer.getName() + "' played a building move.");
        switch (move.getBuildType()) {
            case ROAD:
                System.out.println("They want to build a road at " + move.getBuildLocation() + ".");
                Edge e = _board.getEdges()[move.getBuildLocation()];
                if (e.isRoad()) return 101;
                if (!_activePlayer.hasResources(BUILD_ROAD)) return 102;
                if (_activePlayer.getRoadCount() == 0) return 103;
                _activePlayer.removeResources(BUILD_ROAD);
                _activePlayer.decRoadCount();
                e.setOwner(_activePlayer);
                e.grow();
                return 100;
            case SETTLEMENT:
                System.out.println("They want to build a settlement at " + move.getBuildLocation() + ".");
                Node ns = _board.getNodes()[move.getBuildLocation()];
                if (ns.getVP() == 1 || ns.isOwned()) return 201;
                if (!_activePlayer.hasResources(BUILD_SETTLEMENT)) return 202;
                if (_activePlayer.getSettlementCount() == 0) return 203;
                if (structureAdjacent(ns)) return 204;
                _activePlayer.removeResources(BUILD_SETTLEMENT);
                _activePlayer.decSettlementCount();
                ns.setOwner(_activePlayer);
                ns.grow();
                return 200;
            case CITY:
                System.out.println("They want to build a city at " + move.getBuildLocation() + ".");
                Node nc = _board.getNodes()[move.getBuildLocation()];
                if (nc.getVP() == 2 || nc.isOwned()) return 301;
                if (!_activePlayer.hasResources(BUILD_CITY)) return 302;
                if (_activePlayer.getCityCount() == 0) return 303;
                if (nc.getVP() == 1 && !_activePlayer.equals(nc.getOwner())) return 305;
                _activePlayer.removeResources(BUILD_CITY);
                _activePlayer.decCityCount();
                nc.grow();
                return 300;
            case DEV_CARD:
                System.out.println("No dev cards yet :(");
                return -1;
            default:
                System.out.println("build move had bad build type");
                return -1;
        }
    }
    
    private boolean structureAdjacent(Node node) {
        List<Edge> edges = node.getEdges();
        for (Edge e : edges) {
            Node[] endpoints = e.getNodes();
            for (Node n : endpoints) {
                if (n != node && n.isOwned())
                    return true;
            }
        }
        return false;
    }
    
    private int tradeMove(TradeMove move) {
        
        return -1;
    }
    
    private int robberMove(RobberMove move) {
        
        return -1;
    }
    
    private int startTurn() {
        int roll = _dice.roll();
        _server.sendRoll(_activePlayer.getName(), roll);
        if (roll != 7) {
            for (Tile t : _board.getTiles()) {
                if (roll == t.getNum() && !t.hasRobber()) {
                    for (Node n : t.getNodes()) {
                        if (n.isOwned()) {
                            Player p = n.getOwner();
                            int[] newRes = new int[]{0,0,0,0,0};
                            newRes[t.getResource()] = n.getVP();
                            p.addResources(newRes);
                        }
                    }
                }
            }
        }
        return 000;
    }
    
    private void pushPlayers() {
        _server.sendPlayerArray(_players);
    }
    
    private void pushBoard() {
        _server.sendBoard(_board);
    }

    private int endTurn() {
        _turnOver = true;
        return 000;
    }

    private void calculateVP(Player player) {
        
    }

    private void findLongestRoad(Player player) {
        
    }
}
