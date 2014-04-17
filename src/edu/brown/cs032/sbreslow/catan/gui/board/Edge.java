package edu.brown.cs032.sbreslow.catan.gui.board;

import edu.brown.cs032.tmercuri.catan.logic.Player;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;

public class Edge extends BoardComponent{
    
    private static final long serialVersionUID = 2374415817409860796L;
	
	private Node[] _nodes = new Node[2];
	private boolean _road;
	private Player _owner;
	private Line2D _l;
	private Color _c;
	private BasicStroke _s;
	
	public Edge(Node[] nodes){
		_road = false;
		//_owner = null;
		_nodes = nodes;
		_l = new Line2D.Double(_nodes[0].getX()+Node._diam/2, _nodes[0].getY()+Node._diam/2, 
				_nodes[1].getX()+Node._diam/2, _nodes[1].getY()+Node._diam/2);
		_c = Color.white;
		_s = new BasicStroke(4);
		System.out.println("Before: "+_l.getBounds());
		if(_l.getBounds().height==0){
			_l.getBounds().setBounds(_l.getBounds().x,_l.getBounds().y,_l.getBounds().width,50);
			System.out.println("After: "+_l.getBounds().height);
		}
		this.setType(1);
	}
	
	public Node[] getNodes(){
		return _nodes;
	}
	
	public boolean isRoad(){
		return _road;
	}
	
	public void setOwner(Player p){
		_road = true;
		_owner = p;
	}
    
    public Player getOwner() {
        return _owner;
    }
	
	@Override
	public void paint(Graphics g){
		Graphics2D brush = (Graphics2D) g;
		brush.setColor(_c);
		brush.setStroke(_s);
		brush.draw(_l);
	}
	
	public Shape getShape(){
		if(_l.getBounds().height==0){
			return new Rectangle(_l.getBounds().x,_l.getBounds().y,_l.getBounds().width,50);
		}
		else{
			return _l.getBounds();
		}
	}

	@Override
	public void setColor(Color c) {
		_c = c;
	}

	@Override
	public void grow() {
		if(_s.getLineWidth()==4){
			_s = new BasicStroke(12);
		}
	}

}
