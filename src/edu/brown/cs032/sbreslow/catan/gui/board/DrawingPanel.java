package edu.brown.cs032.sbreslow.catan.gui.board;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;

public class DrawingPanel extends JPanel{// implements MouseListener{
    
    private static final long serialVersionUID = 333238774355322463L;
	
	private final ArrayList<BoardComponent> _toDraw;
	private final CatanClient _client;
	
	public DrawingPanel(CatanClient client){
		super();
		this._client = client;
		this.setBackground(new Color(41, 105, 168));
		this.setSize(750,770);
		this.setPreferredSize(getSize());
		this.setMaximumSize(getPreferredSize());
		this.setMinimumSize(getPreferredSize());
		_toDraw = new ArrayList<>();
		this.setVisible(true);
		this.addMouseListener(new ClickList(this));
		System.out.println("Trying to get board...");
		Board b = this._client.getBoard();
		System.out.println("Got board!");
		_toDraw.addAll(b.getBoard());
	}
    
    @Deprecated
    public DrawingPanel(){
		super();
		this.setBackground(new Color(41, 105, 168));
		this.setSize(750,770);
		this.setPreferredSize(getSize());
		this.setMaximumSize(getPreferredSize());
		this.setMinimumSize(getPreferredSize());
		_toDraw = new ArrayList<>();
		this.setVisible(true);
		this.addMouseListener(new ClickList(this));
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
				if(c.getShape().contains(e.getPoint())){
					/*System.out.println(c);
					Double red = Math.random()*255;
					Double grn = Math.random()*255;
					Double blu = Math.random()*255;
					Color color = new Color(red.intValue(),grn.intValue(),blu.intValue());*/
					Color color;
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
					c.grow();
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
