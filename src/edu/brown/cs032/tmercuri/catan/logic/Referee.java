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
import static edu.brown.cs032.tmercuri.catan.logic.move.BuildConstants.BUILD_CITY;
import static edu.brown.cs032.tmercuri.catan.logic.move.BuildConstants.BUILD_ROAD;
import static edu.brown.cs032.tmercuri.catan.logic.move.BuildConstants.BUILD_SETTLEMENT;
import static edu.brown.cs032.tmercuri.catan.logic.move.BuildConstants.CITY;
import static edu.brown.cs032.tmercuri.catan.logic.move.BuildConstants.DEV_CARD;
import static edu.brown.cs032.tmercuri.catan.logic.move.BuildConstants.ROAD;
import static edu.brown.cs032.tmercuri.catan.logic.move.BuildConstants.SETTLEMENT;
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
        rollForOrder();
        
        for (int z=0; !_gameOver; z++) {
            int currentPlayer = z % _players.length;
            setActivePlayer(currentPlayer);
            while (!_turnOver) {
                Move move = _server.readMove();
                if (!makeMove(move)) {
                    sendError(1);
                }
                pushPlayers();
                pushBoard();
            }
        }
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

    private boolean makeMove(Move move) {
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
        return false;
    }
    
    private boolean buildMove(BuildMove move) {
        System.out.println("Player '" + _activePlayer.getName() + "' played a building move.");
        switch (move.getBuildType()) {
            case ROAD:
                System.out.println("They want to build a road at " + move.getBuildLocation());
                Edge e = _board.getEdges()[move.getBuildLocation()];
                if (e.isRoad() || !_activePlayer.hasResources(BUILD_ROAD)) return false;
                e.setOwner(_activePlayer);
                e.grow();
                return true;
            case SETTLEMENT:
                System.out.println("They want to build a settlement at " + move.getBuildLocation());
                Node ns = _board.getNodes()[move.getBuildLocation()];
                if (ns.getVP() == 1 || ns.isOwned() || structureAdjacent(ns) || !_activePlayer.hasResources(BUILD_SETTLEMENT)) return false;
                ns.setOwner(_activePlayer);
                ns.grow();
                return true;
            case CITY:
                System.out.println("They want to build a city at " + move.getBuildLocation());
                Node nc = _board.getNodes()[move.getBuildLocation()];
                if (nc.getVP() == 2 || !nc.getOwner().equals(_activePlayer) || !_activePlayer.hasResources(BUILD_CITY)) return false;
                nc.grow();
                return true;
            case DEV_CARD:
                System.out.println("No dev cards yet :(");
                return false;
            default:
                System.out.println("build move had bad build type");
                return false;
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
    
    private boolean tradeMove(TradeMove move) {
        
        return false;
    }
    
    private boolean robberMove(RobberMove move) {
        
        return false;
    }
    
    private boolean startTurn() {
        int roll = _dice.roll();
        _server.sendRoll(_activePlayer.getName(), roll);
        if (roll != 7) {
            for (Tile t : _board.getTiles()) {
                int resNum = t.getNum();
                int resType = t.getResource();
                if (roll == resNum) {
                    for (Node n : t.getNodes()) {
                        if (n.isOwned()) {
                            Player p = n.getOwner();
                            int[] newRes = new int[]{0,0,0,0,0};
                            newRes[resType] = n.getVP();
                            p.addResources(newRes);
                        }
                    }
                }
            }
        }
        return true;
    }
    
    private void pushPlayers() {
        _server.sendPlayerArray(_players);
    }
    
    private void pushBoard() {
        _server.sendBoard(_board);
    }
    
    private void sendError(int error) {
        _server.sendError(_activePlayer.getName(), error);
    }

    private boolean endTurn() {
        _turnOver = true;
        return true;
    }

    private void calculateVP(Player player) {
        
    }

    private void findLongestRoad(Player player) {
        
    }
}
