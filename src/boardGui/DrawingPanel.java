package boardGui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class DrawingPanel extends JPanel{// implements MouseListener{
	
	private ArrayList<BoardComp> _todraw;
	
	public DrawingPanel(){
		super();
		this.setBackground(Color.WHITE);
		this.setSize(500,500);
		this.setPreferredSize(getSize());
		this.setMaximumSize(getPreferredSize());
		this.setMinimumSize(getPreferredSize());
		_todraw = new ArrayList<BoardComp>();
		this.setVisible(true);
		Node n1 = new Node(100,100);
		Node n2 = new Node(200,50);
		Node n3 = new Node(300,100);
		Node n4 = new Node(300,200);
		Node n5 = new Node(200,250);
		Node n6 = new Node(100,200);
		Node[] enodes = {n1, n2};
		Edge e = new Edge(enodes);
		_todraw.add(e);
		_todraw.add(n1);
		_todraw.add(n2);
		_todraw.add(n3);
		_todraw.add(n4);
		_todraw.add(n5);
		_todraw.add(n6);
		Node[] tnodes = {n1,n2,n3,n4,n5,n6};
		Tile t = new Tile(0,0,tnodes);
		_todraw.add(t);
		this.addMouseListener(new ClickList(this));
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(Component c : _todraw){
			c.paint(g);
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
			for(BoardComp c: _todraw){
				if(c.getShape().contains(e.getPoint())){
					System.out.println(c);
					/*Color color = new Color((int)Math.random()*255,(int)Math.random()*255,(int)Math.random()*255);
					c.setColor(color);
					_dp.getGraphics().setColor(color);
					_dp.repaint();*/
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
