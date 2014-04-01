package boardGui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;

import javax.swing.JComponent;

public class Edge extends BoardComponent{
	
	private Node[] _nodes = new Node[2];
	private boolean _road;
	//private Player _owner;
	private Line2D _l;
	private Color _c;
	private BasicStroke _s;
	
	public Edge(Node[] nodes){
		_road = false;
		//_owner = null;
		_nodes = nodes;
		_l = new Line2D.Double(_nodes[0].getX()+Node._diam/2, _nodes[0].getY()+Node._diam/2, 
				_nodes[1].getX()+Node._diam/2, _nodes[1].getY()+Node._diam/2);
		_c = Color.RED;
		_s = new BasicStroke(4);
	}
	
	public Node[] getNodes(){
		return _nodes;
	}
	
	public boolean isRoad(){
		return _road;
	}
	
	/*public void setOwner(Player P){
		_road = true;
		_owner = p;
	}*/
	
	@Override
	public void paint(Graphics g){
		Graphics2D brush = (Graphics2D) g;
		brush.setColor(_c);
		brush.setStroke(_s);
		brush.draw(_l);
	}
	
	public Shape getShape(){
		return _l.getBounds();
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
