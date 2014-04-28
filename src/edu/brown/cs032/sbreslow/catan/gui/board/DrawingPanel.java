package edu.brown.cs032.sbreslow.catan.gui.board;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Update;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Background.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import edu.brown.cs032.sbreslow.catan.gui.devCards.RobberFrame;
import edu.brown.cs032.tmercuri.catan.logic.move.*;
import edu.brown.cs032.tmercuri.catan.logic.*;

public class DrawingPanel extends JPanel implements Update {// implements MouseListener{
    
    private static final long serialVersionUID = 333238774355322463L;
	
	private final ArrayList<BoardComponent> _toDraw;
	private final CatanClient _client;
	private int  _selectable;
	private boolean _road;
	private boolean _city;
	private boolean _settlement;
	private int _rbcount;
	
	public DrawingPanel(CatanClient client){
		super();
		_client = client;
		//setBackground(new Color(41, 105, 168));
        setOpaque(false);
		setSize(600,610);
		setPreferredSize(getSize());
		setMaximumSize(getPreferredSize());
		setMinimumSize(getPreferredSize());
		_toDraw = new ArrayList<>();
		this.setVisible(true);
		this.addMouseListener(new ClickList(this));
		_selectable = -1;
		_road = false;
		_city = false;
		_settlement = false;
		_rbcount = 0;
	}
    
    @Deprecated
    public DrawingPanel(){
		super();
		setBackground(new Color(41, 105, 168));
		setSize(600,610);
		setPreferredSize(getSize());
		setMaximumSize(getPreferredSize());
		setMinimumSize(getPreferredSize());
		_toDraw = new ArrayList<>();
		setVisible(true);
		addMouseListener(new ClickList(this));
		Board b = new Board(true);
		_toDraw.addAll(b.getBoard());
        _client = null;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
        Image background = wood;
        int iw = background.getWidth(this);
        int ih = background.getHeight(this);
        if (iw > 0 && ih > 0) {
            for (int x = 0; x < getWidth(); x += iw) {
                for (int y = 0; y < getHeight(); y += ih) {
                    System.out.println("DREW A BG TILE");
                    g.drawImage(background, x, y, iw, ih, this);
                }
            }
        }
		int i = 0;
		for(BoardComponent c : _toDraw){
			if(c!=null){
				if(c.getType()==0)
					c.paint(g);
			}
			else{
				System.out.println(i);
			}
			i++;
		}
		i = 0;
		for(BoardComponent c : _toDraw){
			if(c!=null){
				if(c.getType()==1)
					if(i>175 && i!=177 && i!=180 && i!= 183 && i!=185 && i!=188 && i!=191 && i!= 193
					&& i!=196 && i!=199 && i!=201 && i!=204 && i!=207 && i!=209 && i!=212 && i!=215
					&& i!=217 && i!=220)
						c.paint(g);
			}
			else{
				System.out.println(i);
			}
			i++;
		}
		i = 0;
		for(BoardComponent c : _toDraw){
			if(c!=null){
				if(c.getType()==2)
					if(i>41)
						c.paint(g);
			}
			else{
				System.out.println(i);
			}
			i++;
		}
		
		//_client.confirmPacket();
	}
	
	private class ClickList implements MouseListener{
		
		private final DrawingPanel _dp;
		
		private ClickList(DrawingPanel dp){
			_dp = dp;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("clicked: "+e.getX()+", "+e.getY());
			for(BoardComponent c: _toDraw){
				if(c.getShape().contains(e.getPoint()) && (c.getType()==_selectable || (c.getType()==2 && _selectable==3)
						|| (c.getType()==1 && _selectable==4))){
					//c.grow();
					int buildtype = -1;
					switch(_selectable){
					case 0:
						Tile t = (Tile) c;
						if(!t.hasRobber()){
							ArrayList<Player> plist = new ArrayList<Player>(0);
							for(Node n:t.getNodes()){
								System.out.println("Node "+n.getIndex()+", isOwned: "+n.isOwned());
								if(n.isOwned()){
									if(!plist.contains(n.getOwner()))
										plist.add(n.getOwner());
								}
							}
							if(plist.size()==0){
								RobberMove rm = new RobberMove(_client.getPlayer().getName(), t.getIndex(), null);
								try {
									_client.sendMove(rm);
								} catch (IllegalArgumentException | IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							else{
								new RobberFrame(plist, t.getIndex(), _client);
							}
							_selectable = Integer.MAX_VALUE;
						}
						_selectable = -1;
						break;
					case 1:
						buildtype = 0;
						_selectable = -1;
						break;
					case 2:
						Node n = (Node) c;
						if(n.getVP()==0){
							buildtype = 1;
						}
						_selectable = -1;
						break;
					case 3:
						n = (Node) c;
						if(n.getVP()==1){
							buildtype = 2;
						}
						_selectable = -1;
						break;
					case 4:
						if(_rbcount < 2){
							buildtype = 4;
							_rbcount++;
						}
						else{
							_rbcount = 0;
							_selectable = -1;
						}
						break;
					}
					if(buildtype!=-1){
						BuildMove bm = new BuildMove(_client.getPlayer().getName(), buildtype, c.getIndex());
						try {
							_client.sendMove(bm);
						} catch (IllegalArgumentException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else{
						//TODO: get build info to display in chat
					}
					_dp.repaint();
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
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
	}

	@Override
	public void ericUpdate() {
		_toDraw.clear();
		System.out.println("TRYING TO GET BOARD IN DRAWING PANEL!");
		_toDraw.addAll(_client.getBoard().getBoard()); //TODO Fix this
		System.out.println("GOT BOARD BACK FROM SERVER");
		repaint();
	}
	
	public void decRBCount(){
		_rbcount--;
	}

	/*@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("clicked: ");
		for(Component c: _todraw){
			if(c.contains(e.getPoint())){
				System.out.println(c);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}*/

}
