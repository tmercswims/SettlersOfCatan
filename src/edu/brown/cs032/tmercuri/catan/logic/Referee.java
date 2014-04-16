/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.tmercuri.catan.logic;

import edu.brown.cs032.atreil.catan.networking.server.CatanServer;
import edu.brown.cs032.sbreslow.catan.gui.board.Board;
import edu.brown.cs032.sbreslow.catan.gui.board.Node;
import edu.brown.cs032.sbreslow.catan.gui.board.Tile;
import edu.brown.cs032.tmercuri.catan.logic.move.BuildMove;
import edu.brown.cs032.tmercuri.catan.logic.move.FirstMove;
import edu.brown.cs032.tmercuri.catan.logic.move.LastMove;
import java.util.Arrays;
import java.util.Comparator;
import edu.brown.cs032.tmercuri.catan.logic.move.Move;
import edu.brown.cs032.tmercuri.catan.logic.move.RobberMove;
import edu.brown.cs032.tmercuri.catan.logic.move.TradeMove;

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
    
    public void runGame() {
        chooseOrder();
        
        for (int z=0; !_gameOver; z++) {
            int currentPlayer = z % _players.length;
            setActivePlayer(currentPlayer);
            while (!_turnOver) {
                // read a move from the server
                makeMove(null/*move*/);
                pushPlayers();
                pushBoard();
            }
        }
    }
    
    private void chooseOrder() {
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

    private void makeMove(Move move) {
        if (move instanceof FirstMove) {
            startTurn();
        } else if (move instanceof BuildMove) {
            BuildMove bMove = (BuildMove) move;
            
        } else if (move instanceof TradeMove) {
            TradeMove tMove = (TradeMove) move;
            
        } else if (move instanceof RobberMove) {
            RobberMove rMove = (RobberMove) move;
            
        } else if (move instanceof LastMove) {
            endTurn();
        }
    }
    
    private void startTurn() {
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
    }
    
    private void pushPlayers() {
        _server.sendPlayerArray(_players);
    }
    
    private void pushBoard() {
        _server.sendBoard(_board);
    }

    private void endTurn() {
        _turnOver = true;
    }

    private void calculateVP(Player player) {
        
    }

    private void findLongestRoad(Player player) {
        
    }
}
