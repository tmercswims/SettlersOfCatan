package edu.brown.cs032.sbreslow.catan.gui.board;

import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Misc.musicOff;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Misc.musicOn;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Misc.ports;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Misc.question;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.ServerUpdate;
import edu.brown.cs032.eheimark.catan.gui.tutorial.Tutorial;
import edu.brown.cs032.sbreslow.catan.gui.devCards.RobberFrame;
import edu.brown.cs032.tmercuri.catan.logic.Player;
import edu.brown.cs032.tmercuri.catan.logic.move.BuildMove;
import edu.brown.cs032.tmercuri.catan.logic.move.RobberMove;



public class DrawingPanel extends JPanel implements ServerUpdate {// implements MouseListener{

	private static final long serialVersionUID = 333238774355322463L;

	private final ArrayList<BoardComponent> _toDraw;
	private final CatanClient _client;
	private int  _selectable;
	private boolean _startUp;
	private int _firstSettlement;
	private int _rbcount;

	private int _x, _y;
	private JButton _musicButton, _questionButton;
	private BoardComponent _lastHovered;
	private int _lastHoveredPreviousGhostLevel;
	private Color _lastHoveredPreviousLookerColor;

	public DrawingPanel(CatanClient client){
		super();
		_client = client;
		setSize(600,600);
		setPreferredSize(getSize());
		//setMaximumSize(getPreferredSize());
		//setMinimumSize(getSize());
		_toDraw = new ArrayList<>();
		this.setOpaque(false); // set background to transparent b/c drawing done in GUI class for background
		this.setVisible(true);
		this.addMouseListener(new ClickList(this));
		this.addMouseMotionListener(new MoveList(this));

		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.LINE_AXIS));
		_questionButton = new JButton() {
			private static final long serialVersionUID = 229623158965666152L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				setIcon(question);
			}
		};
		_questionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						_client.addFrame(new Tutorial());
					}
				});
			}
		});
		_musicButton = new JButton() {
			private static final long serialVersionUID = 8345488729823071304L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (_client.getFrame().isMusicPlaying()) {
					setIcon(musicOn);
				} else {
					setIcon(musicOff);
				}
			}
		};
		_musicButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						_client.getFrame().toggleMusic();
					}
				});
			}
		});

		_musicButton.setOpaque(false);
		_musicButton.setContentAreaFilled(false);
		_musicButton.setBorderPainted(false);
		_questionButton.setOpaque(false);
		_questionButton.setContentAreaFilled(false);
		_questionButton.setBorderPainted(false);

		_musicButton.setMinimumSize(new Dimension(50,50));
		_musicButton.setMaximumSize(new Dimension(50,50));
		_questionButton.setMinimumSize(new Dimension(50,50));
		_questionButton.setMaximumSize(new Dimension(50,50));

		innerPanel.add(_musicButton);
		innerPanel.add(_questionButton);
		innerPanel.add(Box.createHorizontalGlue());
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(innerPanel);
		this.add(Box.createVerticalGlue());
		innerPanel.setVisible(true);
		innerPanel.setOpaque(false);
		_rbcount = 0;
		_startUp = true;
		_x = 600;
		_y = 600;
	}

	@Deprecated
	public DrawingPanel(){
		super();
		setBackground(Color.WHITE);
		setSize(600,600);
		setPreferredSize(getSize());
		setMaximumSize(getPreferredSize());
		setMinimumSize(getPreferredSize());
		_toDraw = new ArrayList<>();
		setVisible(true);
		addMouseListener(new ClickList(this));
		Board b = new Board(true);
		_toDraw.addAll(b.getBoard());
		_client = null;
		_musicButton = new JButton();
		setLayout(null);
		_musicButton.setBounds(0,0,25,25);
		add(_musicButton);
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		int i = 0;
		// tiles
		for(BoardComponent c : _toDraw){
			if(c!=null){
				if(c.getType()==0)
					c.paint(g);
			}
			i++;
		}
		i = 0;
		// nodes
		for(BoardComponent c : _toDraw){
			if(c!=null){
				if(c.getType()==1)
					if(i>175 && i!=177 && i!=180 && i!= 183 && i!=185 && i!=188 && i!=191 && i!= 193
					&& i!=196 && i!=199 && i!=201 && i!=204 && i!=207 && i!=209 && i!=212 && i!=215
					&& i!=217 && i!=220)
						c.paint(g);
			}
			i++;
		}
		i = 0;
		// edges
		for(BoardComponent c : _toDraw){
			if(c!=null){
				if(c.getType()==2)
					if(i>41)
						c.paint(g);
			}
			i++;
		}

		g.drawImage(ports, getX(), getY(), _x, _y+5, this);
	}

	public void setStartUp(boolean b) {
		_startUp = b;
	}

	public void setFirstSettlement(int s) {
		_firstSettlement = s;
	}

	private class ClickList implements MouseListener {

		private final DrawingPanel _dp;

		private ClickList(DrawingPanel dp){
			_dp = dp;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			for(BoardComponent c: _toDraw){
				if(c.getShape().contains(e.getPoint()) && (c.getType()==_selectable || (c.getType()==2 && _selectable==3)
						|| (c.getType()==1 && _selectable==4))){
					int buildtype = -1;
					switch(_selectable){
					case 0:
						Tile t = (Tile) c;
						if (t.getIndex() <= 17) {
							_client.getGUI().getChat().addMessage("Server (server): You cannot move the robber into the ocean.");
						} else if(!t.hasRobber()){
							ArrayList<Player> plist = new ArrayList<>(0);
							for(Node n:t.getNodes()){
								if(n.isOwned()){
									if(!plist.contains(n.getOwner())&&(!n.getOwner().equals(_client.getPlayer())))
										plist.add(n.getOwner());
								}
							}
							if(plist.isEmpty()){
								RobberMove rm = new RobberMove(_client.getPlayer().getName(), t.getIndex(), null);
								try {
									_client.sendMove(rm);
								} catch (IllegalArgumentException | IOException e1) {
									e1.printStackTrace();
								}
							}
							else{
								_client.addFrame(new RobberFrame(plist, t.getIndex(), _client, _client.getFrame()));
							}
							_dp.setSelect(-1);
						}
						break;
					case 1:
						buildtype = 0;
						_dp.setSelect(-1);
						break;
					case 2:
						Node n = (Node) c;
						if(n.getVP()==0){
							buildtype = 1;
						}
						_dp.setSelect(-1);
						break;
					case 3:
						n = (Node) c;
						if(n.getVP()==1){
							buildtype = 2;
						}
						_dp.setSelect(-1);
						break;
					case 4:
						if(_rbcount == 0){
							buildtype = 4;
							_rbcount++;
                            _dp.setSelect(-1);
						} else if (_rbcount == 1){
                            buildtype = 5;
                            _rbcount++;
                            _dp.setSelect(-1);
                        }
						else{
							_rbcount = 0;
							_dp.setSelect(-1);
						}
						break;
					}
					if(buildtype!=-1){
						BuildMove bm = new BuildMove(_client.getPlayer().getName(), buildtype, c.getIndex());
						try {
							_client.sendMove(bm);
						} catch (IllegalArgumentException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					_dp.repaint();
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	private class MoveList implements MouseMotionListener {

		private final DrawingPanel _dp;

		private MoveList(DrawingPanel dp) {
			_dp = dp;
		}

		@Override
		public void mouseDragged(MouseEvent e) {}

		@Override
		public void mouseMoved(MouseEvent e) {
			boolean foundSomething = false;
			for (BoardComponent c: _toDraw) {
				if (c.getShape().contains(e.getPoint()) && (c.getType()==_selectable || (c.getType()==2 && _selectable==3) || (c.getType()==1 && _selectable==4))) {
					foundSomething = true;
					if (_lastHovered == null) {
						_lastHoveredPreviousGhostLevel = c.getGhostLevel();
						_lastHoveredPreviousLookerColor = c.getLookerColor();
						_lastHovered = c;
					} else if (c.getIndex() != _lastHovered.getIndex()) {
						_lastHovered.setGhostLevel(_lastHoveredPreviousGhostLevel);
						_lastHovered.setLookerColor(_lastHoveredPreviousLookerColor);
						_lastHoveredPreviousGhostLevel = c.getGhostLevel();
						_lastHoveredPreviousLookerColor = c.getLookerColor();
						_lastHovered = c;
					}
					if (c.getGhostLevel() == 1) {
						c.setGhostLevel(2);
						c.setLookerColor(_client.getPlayer().getColor());
					}
				} else {
					if (!foundSomething && _lastHovered != null) {
						_lastHovered.setGhostLevel(_lastHoveredPreviousGhostLevel);
						_lastHovered.setLookerColor(_lastHoveredPreviousLookerColor);
					}
				}
			}
			_dp.repaint();
		}
	}

	public void setResize(int x, int y){
		int tx = x/23;
		int ty = y/14;
		_x = tx*23;
		_y = ty*14;
	}


	/***
	 * Tile = 0;
	 * Edge = 1;
	 * Settlement = 2;
	 * City = 3;
	 * RoadBuilder = 4;
	 * Nothing = anything else;
	 * @param s
	 */
	public void setSelect(int s){
		_selectable = s;
		switch (_selectable) {
		case -1:
			for (BoardComponent c : _toDraw) {
				c.setGhostLevel(0);
				c.setLookerColor(null);
			}
			break;
		case 0: //robber
		for (BoardComponent c : _toDraw) {
			if (c.getType() == 0) {
				c.setGhostLevel(1);
			}
		}
		break;
		case 1: //road
		case 4: //road builder
			for (BoardComponent c : _toDraw) {
				if (c.getType() == 1) {
					Edge e = (Edge) c;
					if (_startUp) {
						if (edgeIsNextToNode(e, _firstSettlement)) {
							c.setGhostLevel(1);
							c.setLookerColor(_client.getPlayer().getColor());
						}
					} else {
						if (!e.isRoad() && ownedRoadAdjacent(_client.getPlayer(), e)) {
							c.setGhostLevel(1);
							c.setLookerColor(_client.getPlayer().getColor());
						}
					}
				}
			}
			break;
		case 2: //settlement
			for (BoardComponent c : _toDraw) {
				if (c.getType() == 2) {
					Node n = (Node) c;
					if (_startUp) {
						if (!n.isOwned() && n.getVP() == 0 && !structureAdjacent(n)) {
							c.setGhostLevel(1);
							c.setLookerColor(_client.getPlayer().getColor());
						}
					} else {
						if (!n.isOwned() && n.getVP() == 0 && !structureAdjacent(n) && ownedRoadAdjacent(_client.getPlayer(), n)) {
							c.setGhostLevel(1);
							c.setLookerColor(_client.getPlayer().getColor());
						}
					}
				}
			}
			break;
		case 3: //city
			for (BoardComponent c : _toDraw) {
				if (c.getType() == 2) {
					Node n = (Node) c;
					if (n.isOwned() && n.getVP() == 1 && n.getOwner().equals(_client.getPlayer())) {
						c.setGhostLevel(1);
						c.setLookerColor(_client.getPlayer().getColor());
					}
				}
			}
			break;
		}
		repaint();
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

	private boolean ownedRoadAdjacent(Player player, Node node) {
		for (Edge e : node.getEdges()) {
			if (e.isRoad() && e.getOwner().equals(player)) {
				return true;
			}
		}
		return false;
	}

	private boolean ownedRoadAdjacent(Player player, Edge edge) {
		for (Node n : edge.getNodes()) {
			for (Edge e : n.getEdges()) {
				if (e.getIndex() != edge.getIndex() && e.isRoad() && e.getOwner().equals(player)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean edgeIsNextToNode(Edge edge, int node) {
		for (Node n : edge.getNodes()) {
			if (n.getIndex() == node) 
				return true;
		}
		return false;
	}

	@Override
	public void serverUpdate() {
		_toDraw.clear();
		_toDraw.addAll(_client.getBoard().getBoard());
		repaint();
	}

	public void decRBCount(){
		_rbcount--;
	}
}
