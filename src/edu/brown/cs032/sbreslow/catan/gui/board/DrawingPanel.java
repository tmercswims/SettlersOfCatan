package edu.brown.cs032.sbreslow.catan.gui.board;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import edu.brown.cs032.tmercuri.catan.logic.move.*;

public class DrawingPanel extends JPanel{// implements MouseListener{
    
    private static final long serialVersionUID = 333238774355322463L;
	
	private final ArrayList<BoardComponent> _toDraw;
	private final CatanClient _client;
	private int  _selectable;
	private Board _board;
	
	public DrawingPanel(CatanClient client){
		super();
		_client = client;
		setBackground(new Color(41, 105, 168));
		setSize(750,770);
		setPreferredSize(getSize());
		setMaximumSize(getPreferredSize());
		setMinimumSize(getPreferredSize());
		_toDraw = new ArrayList<>();
		this.setVisible(true);
		this.addMouseListener(new ClickList(this));
		System.out.println("Trying to get board...");
		Board b = _client.getBoard();
		System.out.println("Got board!");
		_toDraw.addAll(b.getBoard());
		_selectable = 3;
	}
    
    @Deprecated
    public DrawingPanel(){
		super();
		setBackground(new Color(41, 105, 168));
		setSize(750,770);
		setPreferredSize(getSize());
		setMaximumSize(getPreferredSize());
		setMinimumSize(getPreferredSize());
		_toDraw = new ArrayList<>();
		setVisible(true);
		addMouseListener(new ClickList(this));
		Board b = new Board();
		_toDraw.addAll(b.getBoard());
        _client = null;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
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
				if(c.getShape().contains(e.getPoint()) && c.getType()==_selectable){
					/*System.out.println(c);
					Double red = Math.random()*255;
					Double grn = Math.random()*255;
					Double blu = Math.random()*255;
					Color color = new Color(red.intValue(),grn.intValue(),blu.intValue());*/
					/*Color color;
					switch(c.getType()){
					case 0:
						color = Color.MAGENTA;
						break;
					case 1:
						color = Color.CYAN;
						break;
					case 2:
						color = Color.YELLOW;
						break;
					default:
						color = Color.WHITE;
					}
					c.setColor(color);
					c.grow();*/
					c.grow();
					int buildtype = -1;
					switch(c.getType()){
					case 1:
						buildtype = 0;
						break;
					case 2:
						Node n = (Node) c;
						if(n.getVP()==1){
							buildtype = 2;
						}
						else if(n.getVP()==0){
							buildtype = 1;
						}
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
	 * Node = 2;
	 * Nothing = anything else;
	 * @param s
	 */
	public void setSelect(int s){
		_selectable = s;
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
