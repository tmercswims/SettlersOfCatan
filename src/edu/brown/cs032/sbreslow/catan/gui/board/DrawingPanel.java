package edu.brown.cs032.sbreslow.catan.gui.board;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class DrawingPanel extends JPanel{// implements MouseListener{
	
	private ArrayList<BoardComponent> _todraw;
	
	public DrawingPanel(){
		super();
		this.setBackground(Color.WHITE);
		this.setSize(1100,1050);
		this.setPreferredSize(getSize());
		this.setMaximumSize(getPreferredSize());
		this.setMinimumSize(getPreferredSize());
		_todraw = new ArrayList<BoardComponent>();
		this.setVisible(true);
		/*Node n1 = new Node(0,300);
		Node n2 = new Node(50,225);
		Node n3 = new Node(150,225);
		Node n4 = new Node(200,300);
		Node n5 = new Node(150,375);
		Node n6 = new Node(50,375);
		Node[] enodes = {n1, n2};
		Edge e = new Edge(enodes);
		/*_todraw.add(e);
		_todraw.add(n1);
		_todraw.add(n2);
		_todraw.add(n3);
		_todraw.add(n4);
		_todraw.add(n5);
		_todraw.add(n6);*/
		/*ArrayList<Node> tnodes = new ArrayList<Node>(6);
		tnodes.add(n1);
		tnodes.add(n2);
		tnodes.add(n3);
		tnodes.add(n4);
		tnodes.add(n5);
		tnodes.add(n6);
		Tile t = new Tile(0,0,tnodes);
		//_todraw.add(t);*/
		this.addMouseListener(new ClickList(this));
		Board b = new Board();
		_todraw.addAll(b.getBoard());
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		int i = 0;
		for(BoardComponent c : _todraw){
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
		for(BoardComponent c : _todraw){
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
		for(BoardComponent c : _todraw){
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
		
		private DrawingPanel _dp;
		
		private ClickList(DrawingPanel dp){
			_dp = dp;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("clicked: "+e.getX()+", "+e.getY());
			for(BoardComponent c: _todraw){
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
