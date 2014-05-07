/*
 * Thomas Mercurio, tmercuri
 * CS032, Spring 2014
 */

package edu.brown.cs032.tmercuri.catan.logic;

import edu.brown.cs032.atreil.catan.networking.server.CatanServer;
import edu.brown.cs032.sbreslow.catan.gui.board.Board;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Edge.blue;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Edge.orange;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Edge.red;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Edge.white;
import edu.brown.cs032.sbreslow.catan.gui.board.Edge;
import edu.brown.cs032.sbreslow.catan.gui.board.Node;
import edu.brown.cs032.sbreslow.catan.gui.board.Tile;
import static edu.brown.cs032.tmercuri.catan.logic.BuildConstants.*;
import static edu.brown.cs032.tmercuri.catan.logic.MoveMessage.*;
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
	private Node _startupSettlement;
	private int _startUp;
    private boolean _pushPlayers, _pushBoard;
    private boolean _moveTheRobber;
    private int _needToDropResources;
    private boolean _rbtwo;

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
        _startUp = 0;
        _startupSettlement = null;
		_players = players;
		_board = new Board(true);
		_server = server;
        _pushPlayers = _pushBoard = false;
        _moveTheRobber = false;
        _needToDropResources = 0;
        _rbtwo = false;
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

		// roll the dice for each player and order the players based on the rolls
		rollForOrder();
		
		pushPlayers();
		pushBoard();

		_server.sendMessage(null, "Welcome to The Settlers of Catan!\n\nUse the window below to chat with other players.\n\nThe die have been rolled and the order determined.\n\nYou will now place your initial settlements.");
		
		_startUp = 1;
		firstMoves();
		_startUp = 0;
        _server.sendEndStart();

		for (int z=0; !_gameOver; z++) {
			int currentPlayer = z % _players.length;
			setActivePlayer(currentPlayer);
			while (!_turnOver) {
                _pushPlayers = _pushBoard = false;
				Move move = _server.readMove();
				MoveMessage whatHappened = MoveMessage.getMessage(makeMove(move));
				if (whatHappened.isError()) {
					_server.sendMessage(move.getPlayerName(), whatHappened.getDescription());
				} else if (whatHappened != MESSAGE_000) {
					if (move instanceof TradeMove && (whatHappened == MESSAGE_403 || whatHappened == MESSAGE_410)) {
						_server.sendMessage(null, String.format(whatHappened.getDescription(), ((TradeMove)move).getProposedTo(), _activePlayer.getName()));
					} else if (move instanceof TradeMove && whatHappened == MESSAGE_400) {
						_server.sendMessage(null, String.format(whatHappened.getDescription(), _activePlayer.getName(), ((TradeMove)move).getProposedTo()));
					} else if (move instanceof TradeMove && whatHappened == MESSAGE_510) {
						_server.sendMessage(null, String.format(whatHappened.getDescription(), ((TradeMove)move).getPlayerName()));
					} else if (move instanceof RobberMove && whatHappened == MESSAGE_500) {
						_server.sendMessage(null, String.format(whatHappened.getDescription(), _activePlayer.getName(), ((RobberMove)move).getToStealFrom()));
					} else {
						_server.sendMessage(null, String.format(whatHappened.getDescription(), _activePlayer.getName()));
					}
				}
                if (_pushPlayers)
                    pushPlayers();
                if (_pushBoard)
                    pushBoard();
                if (_rbtwo)
                    _server.sendSecondRB(_activePlayer.getName());
			}
            _activePlayer.mergeDevCards();
			findWinner();
		}
		_server.sendGameOver(_winner.getName()+" has won!\nPlease return to the Main Menu.");
	}
    
    /**
     * Gives the specified player 10 of each resource, cheater.
     * @param playerName the player to give to, cheater
     */
    public void foodler(String playerName) {
        Player player = null;
        for (Player p : _players) {
            if (p.getName().equals(playerName)) player = p;
        }
        if (player != null) player.addResources(new int[]{10,10,10,10,10});
        pushPlayers();
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
					_server.sendMessage(move.getPlayerName(), "You need to build a settlement.");
				} else {
					MoveMessage whatHappened = MoveMessage.getMessage(makeMove(move));
					if (whatHappened.isError()) {
						_server.sendMessage(move.getPlayerName(), whatHappened.getDescription());
					} else {
                        _server.sendStartSettle(_activePlayer.getName(), ((BuildMove)move).getBuildLocation());
						_server.sendMessage(null, String.format(whatHappened.getDescription(), _activePlayer.getName()));
						validSettlement = true;
					}
                    if (_pushPlayers)
                        pushPlayers();
                    if (_pushBoard)
                        pushBoard();
				}
			}
			boolean validRoad = false;
			while (!validRoad) {
				_server.startRoad(p.getName());
				_server.sendMessage(p.getName(), "Click where you would like to build your first road.");
				Move move = _server.readMove();
				if (!(move instanceof BuildMove)) {
					_server.sendMessage(move.getPlayerName(), "You need to build a road.");
				} else {
					MoveMessage whatHappened = MoveMessage.getMessage(makeMove(move));
					if (whatHappened.isError()) {
						_server.sendMessage(move.getPlayerName(), whatHappened.getDescription());
					} else {
						_server.sendMessage(null, String.format(whatHappened.getDescription(), _activePlayer.getName()));
						validRoad = true;
					}
                    if (_pushPlayers)
                        pushPlayers();
                    if (_pushBoard)
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
				if (!(move instanceof BuildMove)) {
					_server.sendMessage(move.getPlayerName(), "You need to build a settlement.");
				} else {
					MoveMessage whatHappened = MoveMessage.getMessage(makeMove(move));
					if (whatHappened.isError()) {
						_server.sendMessage(move.getPlayerName(), whatHappened.getDescription());
					} else {
                        _server.sendStartSettle(_activePlayer.getName(), ((BuildMove)move).getBuildLocation());
						_server.sendMessage(null, String.format(whatHappened.getDescription(), _activePlayer.getName()));
						validSettlement = true;
					}
                    if (_pushPlayers)
                        pushPlayers();
                    if (_pushBoard)
                        pushBoard();
				}
			}
			boolean validRoad = false;
			while (!validRoad) {
				_server.startRoad(p.getName());
				_server.sendMessage(p.getName(), "Click where you would like to build your second road.");
				Move move = _server.readMove();
				if (!(move instanceof BuildMove)) {
					_server.sendMessage(move.getPlayerName(), "You need to build a road.");
				} else {
					MoveMessage whatHappened = MoveMessage.getMessage(makeMove(move));
					if (whatHappened.isError()) {
						_server.sendMessage(move.getPlayerName(), whatHappened.getDescription());
					} else {
						_server.sendMessage(null, String.format(whatHappened.getDescription(), _activePlayer.getName()));
						validRoad = true;
					}
                    if (_pushPlayers)
                        pushPlayers();
                    if (_pushBoard)
                        pushBoard();
				}
			}
		}
	}

	private void setActivePlayer(int p) {
		for (Player pa : _players) {
			pa.setIsActive(false);
		}
		Player player = _players[p];
		player.setIsActive(true);
		_server.sendMessage(null, String.format("It is now %s's turn.", player.getName()));
		_turnOver = false;
		_activePlayer = player;
		pushPlayers();
	}

	private void setActivePlayer(Player player) {
		for (Player pa : _players) {
			pa.setIsActive(false);
		}
		player.setIsActive(true);
		_server.sendMessage(null, String.format("It is now %s's turn.", player.getName()));
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
		if (_startUp == 0 && !_activePlayer.hasRolled()) return 998;
        if (_needToDropResources != 0) return 996;
		switch (move.getBuildType()) {
            case ROAD:
                Edge e = _board.getEdges()[move.getBuildLocation()];
                int i = move.getBuildLocation();
                if (i <= 42 || i == 44 || i == 47 || i == 50 || i == 52 || i == 55 || i == 58 || i == 60 || i == 63 || i == 66 || i == 68 || i == 71 || i == 74 || i == 76 || i == 79 || i == 82 || i == 84 || i == 87) return 108;
                if (_startUp != 0) {
                    if (e.isRoad()) return 101;
                    if (!edgeIsNextToNode(e, _startupSettlement)) return 107;
                    _activePlayer.decRoadCount();
                    e.setOwner(_activePlayer);
                    e.grow();
                    _pushPlayers = _pushBoard = true;
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
                    _pushPlayers = _pushBoard = true;
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
                    _pushPlayers = _pushBoard = true;
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
                    _pushPlayers = _pushBoard = true;
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
                _pushPlayers = _pushBoard = true;
                return 300;
            case DEV_CARD:
                int card = _deck.getCard();
                if (card == -1) return 701;
                if (!_activePlayer.hasResources(BUILD_DEV_CARD)) return 702;
                _activePlayer.removeResources(BUILD_DEV_CARD);
                _activePlayer.addDevCard(card);
                _pushPlayers = true;
                return 700;
            case ROAD_BUILDER_1:
                Edge eRB1 = _board.getEdges()[move.getBuildLocation()];
                if (eRB1.isRoad() || _activePlayer.getRoadCount() == 0 || !ownedRoadAdjacent(eRB1)) {
                    try {
                        _server.sendRB(move.getPlayerName());
                    } catch (IllegalArgumentException | IOException ex) {
                        ex.printStackTrace();
                    }
                    return 601;
                }
                _activePlayer.decRoadCount();
                eRB1.setOwner(_activePlayer);
                eRB1.grow();
                _pushPlayers = _pushBoard = true;
                _rbtwo = true;
                return 610;
            case ROAD_BUILDER_2:
                Edge eRB2 = _board.getEdges()[move.getBuildLocation()];
                if (eRB2.isRoad() || _activePlayer.getRoadCount() == 0 || !ownedRoadAdjacent(eRB2)) {
                    try {
                        _server.sendRB(move.getPlayerName());
                    } catch (IllegalArgumentException | IOException ex) {
                        ex.printStackTrace();
                    }
                    return 601;
                }
                _activePlayer.decRoadCount();
                eRB2.setOwner(_activePlayer);
                eRB2.grow();
                _pushPlayers = _pushBoard = true;
                _rbtwo = false;
                return 610;
            default:
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
			if (e.isRoad() && e.getOwner().equals(_activePlayer)) {
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
		if (!_activePlayer.hasRolled()) return 998;
        if (!move.getPlayerName().equals(move.getProposedTo()) && _moveTheRobber) return 997;
        if (!move.getPlayerName().equals(move.getProposedTo()) && _needToDropResources != 0) return 996;

		Player robbed = null;
		for (Player p : _players) {
			if (p.getName().equals(move.getPlayerName())) robbed = p;
		}

		if (move.getPlayerName().equals(move.getProposedTo())) {
			int[] res = move.getResources();
			if (robbed != null) robbed.removeResources(res);
            _needToDropResources--;
            if (_needToDropResources == 0 && !_moveTheRobber) _server.sendEndSeven(_activePlayer.getName());
            _pushPlayers = true;
			return 510;
		}

		int[] giving = {0,0,0,0,0};
		int[] receiving = {0,0,0,0,0};
		for (int i=0; i<move.getResources().length;i++) {
			int res = move.getResources()[i];
			if (res < 0) {
				giving[i] = 0-res;
			} else if (res > 0) {
				receiving[i] = res;
			}
		}

		if (move.getProposedTo().equals("***MERCHANT***")) {
			int recNum = 0;
			for (int r : receiving) {
				recNum += r;
			}
			if (recNum != 1) return 404;

			int numGiveTypes = 0;
			int resIndex = -1;
			for (int i=0; i<giving.length;i++) {
				if (giving[i] > 0) {
					numGiveTypes++;
					resIndex = i;
				}
			}

			if (numGiveTypes < 1) return 407;
			if (numGiveTypes > 1 || resIndex == -1) return 405;

			switch (giving[resIndex]) {
			case 4:
				if (_activePlayer.hasResources(giving)) {
					_activePlayer.removeResources(giving);
					_activePlayer.addResources(receiving);
				}
                _pushPlayers = true;
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
                _pushPlayers = true;
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
                _pushPlayers = true;
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
			if (receiver != null && !receiver.hasResources(receiving)) return 403;
			_server.sendTrade(move.getProposedTo(), move);
            _pushPlayers = _pushBoard = false;
			return 400;
		case 0:
            _pushPlayers = _pushBoard = false;
			return 403;
		case 1:
			if (giver != null && receiver != null) {
				giver.removeResources(giving);
				giver.addResources(receiving);
				receiver.removeResources(receiving);
				receiver.addResources(giving);
			}
            _pushPlayers = true;
			return 410;
		default:
			return -1;
		}
	}

	private int robberMove(RobberMove move) {
		if (!move.getPlayerName().equals(_activePlayer.getName())) return 999;
        //if (_needToDropResources != 0) return 996;
        _moveTheRobber = false;
        if (_needToDropResources == 0 && !_moveTheRobber) _server.sendEndSeven(_activePlayer.getName());
		Tile newRobber = _board.getTiles()[move.getNewLocation()];
		Player victim = null;
		if (newRobber.hasRobber()) return 501;
		for (Tile t : _board.getTiles()) {
			if (t.hasRobber()) t.setRobber(false);
		}
		newRobber.setRobber(true);
		if (move.getToStealFrom() == null) {
            _pushBoard = true;
			return 503;
		} else {
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
            _pushPlayers = _pushBoard = true;
			return 500;
		}
	}

	private int yearOfPlentyMove(YearOfPlentyMove move) {
		if (!move.getPlayerName().equals(_activePlayer.getName())) return 999;
		if (!_activePlayer.hasRolled()) return 998;
        if (_moveTheRobber) return 997;
        if (_needToDropResources != 0) return 996;
		Player played = null;
		for (Player p : _players) {
			if (p.getName().equals(move.getPlayerName()))
				played = p;
		}
		int[] newRes = {0,0,0,0,0};
		newRes[move.getType1()] += 1;
		newRes[move.getType2()] += 1;
		if (played != null) played.addResources(newRes);
        _pushPlayers = true;
		return 620;
	}

	private int monopolyMove(MonopolyMove move) {
		if (!move.getPlayerName().equals(_activePlayer.getName())) return 999;
		if (!_activePlayer.hasRolled()) return 998;
        if (_moveTheRobber) return 997;
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
        _pushPlayers = true;
		return 630;
	}

	private int victoryPointMove(VictoryPointMove move) {
		if (!move.getPlayerName().equals(_activePlayer.getName())) return 999;
		if (!_activePlayer.hasRolled()) return 998;
        if (_moveTheRobber) return 997;
        if (_needToDropResources != 0) return 996;
		Player played = null;
		for (Player p : _players) {
			if (p.getName().equals(move.getPlayerName()))
				played = p;
		}
		if (played != null) played.incVictoryPoints();
        _pushPlayers = true;
		return 640;
	}

	private int devCardMove(DevCardMove move) {
		if (!move.getPlayerName().equals(_activePlayer.getName())) return 999;
		if (move.getIndex() != 0 && !_activePlayer.hasRolled()) return 998;
        if (_moveTheRobber) return 997;
        if (_needToDropResources != 0) return 996;
		Player played = null;
		for (Player p : _players) {
			if (p.getName().equals(move.getPlayerName()))
				played = p;
		}
		if (played != null) played.removeDevCard(move.getIndex());
		if (move.getIndex() == 0) {
			if (played != null) {
				played.incLargestArmy();
				if ((_army == null && played.getArmySize()>= 3)) {
					_server.sendMessage(null, String.format("%s has taken largest army.", played.getName()));
					played.incVictoryPoints();
					played.incVictoryPoints();
					_army = played;
				} else if (_army != null && played.getArmySize() > _army.getArmySize() && !_army.equals(played)) {
					_server.sendMessage(null, String.format("%s has taken largest army from %s.", played.getName(), _army.getName()));
					_army.decVictoryPoints();
					_army.decVictoryPoints();
					played.incVictoryPoints();
					played.incVictoryPoints();
					_army = played;
				}
			}
		}
        _pushPlayers = true;
		return 600;
	}

	private int startTurn(FirstMove move) {
		if (!move.getPlayerName().equals(_activePlayer.getName())) return 999;
        if (_needToDropResources != 0) return 996;
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
						}
					}
				}
			}
		} else {
			for (Player p: _players) {
				if (p.getResourceCount()>7) {
					try {
                        _needToDropResources++;
						_server.sendMessage(p.getName(), "The robber has attacked! Please drop half your resources.");
						_server.sendSeven(p.getName());
					} catch (IllegalArgumentException | IOException ex) {
						ex.printStackTrace();
					}
				}
			}
			_server.sendMessage(_activePlayer.getName(), "Please move the robber to a new terrain hex.");
            _moveTheRobber = true;
            _pushPlayers = true;
		}
		_activePlayer.setRolled(true);
        _pushPlayers = true;
		return 000;
	}

	private int endTurn(LastMove move) {
		if (!move.getPlayerName().equals(_activePlayer.getName())) return 999;
        if (_moveTheRobber) return 997;
        if (_needToDropResources != 0) return 996;
		_activePlayer.setIsActive(false);
		_server.sendLastMove();
		_turnOver = true;
        _pushPlayers = _pushBoard = false;
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
				if (!set.isEmpty()) edgeSets.add(set);
			}
			_board.clearEdges();
			int l = 0;
			for (List<Edge> set : edgeSets) {
				for (int i=0; i<set.size(); i++) {
					Edge e = set.get(i);
					if (isEndEdge(e, set) || i == set.size()-1) {
						l = Math.max(l, findLongestRoadInSet(e, set, getEndNode(e, set), 1));
						for (Edge ed : set) ed.setVisited(false);
					}
				}
			}
			p.setLongestRoad(l);
			_board.clearEdges();
			if ((_road == null && p.getLongestRoad() >= 5)) {
				_server.sendMessage(null, String.format("%s has taken longest road.", p.getName()));
				p.incVictoryPoints();
				p.incVictoryPoints();
				_road = p;
			} else if (_road != null && p.getLongestRoad() > _road.getLongestRoad() && !_road.equals(p)) {
				_server.sendMessage(null, String.format("%s has taken longest road from %s.", p.getName(), _road.getName()));
				_road.decVictoryPoints();
				_road.decVictoryPoints();
				p.incVictoryPoints();
				p.incVictoryPoints();
				_road = p;
			}
		}
		if (_road != null && _road.getLongestRoad() < 5) {
			_server.sendMessage(null, String.format("%s has lost longest road.", _road.getName()));
			_road.decVictoryPoints();
			_road.decVictoryPoints();
			_road = null;
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
		e.setVisited(true);
		for (Node n : e.getNodes()) {
			if (n.getIndex() != last.getIndex()) {
				int longest = 0;
				for (Edge ed : n.getEdges()) {
					if (!ed.wasVisited() && ed.getIndex() != e.getIndex() && set.contains(ed)) {
						int thisOne = findLongestRoadInSet(ed, set, n, length+1);
						if (thisOne > longest) longest = thisOne;
					}
				}
				return 1+longest;
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
