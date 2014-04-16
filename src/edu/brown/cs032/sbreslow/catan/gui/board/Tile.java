package edu.brown.cs032.sbreslow.catan.gui.board;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

public class Tile extends BoardComponent{

	public final int _resource;
	public final int _num;

	private List<Node> _nodes = new ArrayList<Node>(6);
	boolean _robber;
	Polygon _p;
	private Color _c;
	private Point _index;

	public Tile(int resource, int num, List<Node> nodes){
		_resource = resource;
		_num = num;
		_nodes = nodes;
		if(num==0 && resource==5){
			_robber = true;
		}
		else{
			_robber = false;
		}
		int[] xPoints = new int[6];
		int[] yPoints = new int[6];
		int i = 0;
		if(_nodes!=null){
			for(Node n: _nodes){
				xPoints[i] = n.getX()+Node._diam/2;
				yPoints[i] = n.getY()+Node._diam/2;
				i++;
			}
			_p = new Polygon(xPoints, yPoints, 6);
		}
		_c = Color.GREEN;
		this.setType(0);
		//Rectangle r = new Rectangle(nodes[0].getX()+2, nodes[0].getY()+2, 200, 100);
		//this.setLocation(nodes[0].getX()+2, nodes[0].getY()+2);
		//this.setBounds(r);
		_index = null;
	}
	
	public int getResource() {
		return _resource;
	}

	public int getNum() {
		return _num;
	}

	public void setNodes(List<Node> nodes){
		_nodes = nodes;
		int[] xPoints = new int[6];
		int[] yPoints = new int[6];
		int i = 0;
		if(_nodes!=null){
			for(Node n: _nodes){
				xPoints[i] = n.getX()+Node._diam/2;
				yPoints[i] = n.getY()+Node._diam/2;
				i++;
			}
			_p = new Polygon(xPoints, yPoints, 6);
		}
	}

	public void setIndex(int x, int y){
		_index = new Point(x,y);
	}

	public Point getIndex(){
		return _index;
	}

	public boolean hasRobber(){
		return _robber;
	}

	public List<Node> getNodes(){
		return _nodes;
	}

	public Node getNode(int index) throws ArrayIndexOutOfBoundsException{
		return _nodes.get(index);
	}

	public void setRobber(boolean robber){
		_robber = robber;
	}

	@Override
	public void paint(Graphics g){
		Graphics2D brush = (Graphics2D) g;
		brush.setColor(_c);
		brush.setStroke(new BasicStroke());
		brush.fillPolygon(_p);
		char[] toprint = Integer.toString(_num).toCharArray();
		brush.setColor(Color.BLACK);
		Rectangle r = _p.getBounds();
		brush.drawChars(toprint, 0, 1, (int)r.getCenterX(), (int)r.getCenterY());
	}

	public void setColor(Color c){
		_c = c;
	}

	public Shape getShape(){
		return _p;
	}

	@Override
	public void grow() {
		// SHOULD NEVER BE USED
	}

}
