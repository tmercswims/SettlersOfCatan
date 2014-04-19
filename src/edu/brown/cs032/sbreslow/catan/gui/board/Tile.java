package edu.brown.cs032.sbreslow.catan.gui.board;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComponent;

public class Tile extends BoardComponent{
    
    private static final long serialVersionUID = 331277313248085333L;

	public final int _resource;
	public int _num;

	private List<Node> _nodes = new ArrayList<Node>(6);
	boolean _robber;
	Polygon _p;
	private Color _c;
	private Point _index;
	private List<Tile> _bors = new ArrayList<Tile>();

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
	
	public void setNum(int num){
		_num = num;
	}
	
	public List<Tile> getBors(){
		return _bors;
	}
	
	public void setBors(List<Tile> bors){
		_bors = bors;
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
		switch(_resource){
		case 0://wheat
			brush.setColor(new Color(255,205,0));
			break;
		case 1://brick
			brush.setColor(new Color(119,255,0));
			break;
		case 2://sheep
			brush.setColor(new Color(255,102,0));
			break;
		case 3://ore
			brush.setColor(new Color(85,85,85));
			break;
		case 4://wood
			brush.setColor(new Color(38,73,29));
			break;
		case 5://desert
			brush.setColor(new Color(247,239,164));
			break;
		case 6://water
			brush.setColor(Color.blue);
		}
		//brush.setColor(_c);
		brush.setStroke(new BasicStroke());
		brush.fillPolygon(_p);
		char[] toprint = Integer.toString(_num).toCharArray();
		// = {num.charAt(0)};
		//System.out.println(Integer.toString(_num));
		brush.setColor(Color.white);
		Rectangle r = _p.getBounds();
		if(_resource!=6){
			brush.fillOval((int)r.getCenterX()-15, (int)r.getCenterY()-15, 30, 30);
			if(_num==6 || _num==8){
				brush.setColor(Color.red);
			}
			else{
				brush.setColor(Color.black);
			}
			brush.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
			brush.setStroke(new BasicStroke(5));
			if(toprint.length>1){
				brush.drawChars(toprint, 0, toprint.length, (int)r.getCenterX()-8, (int)r.getCenterY()+5);		
			}
			else{
				brush.drawChars(toprint, 0, toprint.length, (int)r.getCenterX()-4, (int)r.getCenterY()+5);
			}
		}
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