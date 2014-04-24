package edu.brown.cs032.sbreslow.catan.gui.board;

import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Edge.*;
import edu.brown.cs032.tmercuri.catan.logic.Player;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;

public class Edge extends BoardComponent{
    
    private static final long serialVersionUID = 2374415817409860796L;
    
	private Node[] _nodes = new Node[2];
	private boolean _road;
	private Player _owner;
	private final Line2D _l;
	private Color _c;
	private int _port;
	private final int _index;
	
	public Edge(Node[] nodes, int index){
		_road = false;
		_owner = null;
		_nodes = nodes;
		_l = new Line2D.Double(_nodes[0].getX()+Node._diam/2, _nodes[0].getY()+Node._diam/2, 
				_nodes[1].getX()+Node._diam/2, _nodes[1].getY()+Node._diam/2);
		_c = Color.gray;
		
		this.setType(1);
		_port = 6;
		_index = index;
	}
	
	public int getPort(){
		return _port;
	}
	
	public void setPort(int port){
		_port = port;
		_nodes[0].setPort(port);
		_nodes[1].setPort(port);
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
		switch(_port){
		case 0:
			char[] todraw = "Wheat Port".toCharArray();
            Image portImage = wheatPort;
			//brush.drawChars(todraw, 0, todraw.length,(int)_l.getX1()+2,(int) _l.getY1()+30);
            brush.drawImage(portImage, (int)_l.getX1()+14, (int) _l.getY1()+20,
            		/*(int)_l.getBounds().getWidth(), (int)_l.getBounds().getHeight(),*/ null);
			int[] wheatx = {(int) _l.getX1(), (int) _l.getX2(), (int) (_l.getX1()+Math.abs(_l.getX2()-_l.getX1())/2)};
			int[] wheaty = {(int) _l.getY1()+2, (int) _l.getY2()+2, (int)_l.getY1()+20};
			brush.setColor(new Color(255,205,0));
			//brush.fill(new Polygon(wheatx,wheaty,3));
            brush.drawImage(southPort, (int)_l.getX1(), (int)_l.getY1(), 
            		(int)_l.getBounds().getWidth(), (int)_l.getBounds().getHeight(), null);
			break;
		case 1:
			todraw = "Sheep Port".toCharArray();
            portImage = sheepPort;
			//brush.drawChars(todraw, 0, todraw.length,(int)_l.getX1()-100,(int) _l.getY1());
            brush.drawImage(portImage, (int)_l.getX1()-78, (int) _l.getY1()-15,
            		/*(int)_l.getBounds().getWidth(), (int)_l.getBounds().getHeight(),*/ null);
			int[] sheepx = {(int) _l.getX1(), (int) _l.getX2(), (int) _l.getX2()};
			int[] sheepy = {(int) _l.getY1(), (int) _l.getY2(), (int) _l.getY2()-40};
			brush.setColor(new Color(119,255,0));
			//brush.fill(new Polygon(sheepx,sheepy,3));
            brush.drawImage(northwestPort, (int)_l.getX1()-49, (int)_l.getY1()-3, 
            		/*(int)_l.getBounds().getWidth(), (int)_l.getBounds().getHeight(),*/ null);
			break;
		case 2:
			todraw = "Brick Port".toCharArray();
            portImage = brickPort;
			//brush.drawChars(todraw, 0, todraw.length,(int)_l.getX2()+50,(int) _l.getY2()+20);
            brush.drawImage(portImage, (int)_l.getX2()+39, (int) _l.getY2()-14,
            		/*(int)_l.getBounds().getWidth(), (int)_l.getBounds().getHeight(),*/ null);
			int[] brickx = {(int) _l.getX1(), (int) _l.getX2(), (int) _l.getX1()};
			int[] bricky = {(int) _l.getY1(), (int) _l.getY2(), (int) _l.getY1()-40};
			brush.setColor(new Color(255,102,0));
			//brush.fill(new Polygon(brickx,bricky,3));
            brush.drawImage(northeastPort, (int)_l.getX2()+5, (int)_l.getY2()-5,
            		/*(int)_l.getBounds().getWidth(), (int)_l.getBounds().getHeight(),*/ null);
			break;
		case 3:
			todraw = "Ore Port".toCharArray();
            portImage = orePort;
			//brush.drawChars(todraw, 0, todraw.length,(int)_l.getX1()+7,(int) _l.getY1()+30);
            brush.drawImage(portImage, (int)_l.getX1()+14, (int) _l.getY1()+20,
            		/*(int)_l.getBounds().getWidth(), (int)_l.getBounds().getHeight(),*/ null);
			int[] orex = {(int) _l.getX1(), (int) _l.getX2(), (int) (_l.getX1()+Math.abs(_l.getX2()-_l.getX1())/2)};
			int[] orey = {(int) _l.getY1()+2, (int) _l.getY2()+2, (int)_l.getY1()+20};
			brush.setColor(new Color(85,85,85));
			//brush.fill(new Polygon(orex,orey,3));
            brush.drawImage(southPort, (int)_l.getX1(), (int)_l.getY1(),
            		/*(int)_l.getBounds().getWidth(), (int)_l.getBounds().getHeight(),*/ null);
			break;
		case 4:
			todraw = "Wood Port".toCharArray();
            portImage = woodPort;
			//brush.drawChars(todraw, 0, todraw.length,(int)_l.getX2()+50,(int) _l.getY2()+20);
            brush.drawImage(portImage, (int)_l.getX2()+39, (int) _l.getY2()-14,
            		/*(int)_l.getBounds().getWidth(), (int)_l.getBounds().getHeight(),*/ null);
			int[] woodx = {(int) _l.getX1(), (int) _l.getX2(), (int) _l.getX1()};
			int[] woody = {(int) _l.getY1(), (int) _l.getY2(), (int) _l.getY1()-40};
			brush.setColor(new Color(38,73,29));
			//brush.fill(new Polygon(woodx,woody,3));
            brush.drawImage(northeastPort, (int)_l.getX2()+5, (int)_l.getY2()-5,
            		/*(int)_l.getBounds().getWidth(), (int)_l.getBounds().getHeight(),*/ null);
			break;
		case 5:
			todraw = "3-1 Port".toCharArray();
            portImage = genericPort;
			brush.setColor(Color.white);
			if(_index==83){
				//brush.drawChars(todraw, 0, todraw.length,(int)_l.getX2()+7,(int) _l.getY1()-30);
                brush.drawImage(portImage, (int)_l.getX2()+13, (int) _l.getY2()-63,
                		/*(int)_l.getBounds().getWidth(), (int)_l.getBounds().getHeight(),*/ null);
				int[] x = {(int) _l.getX1(), (int) _l.getX2(), (int) (_l.getX2()+Math.abs(_l.getX1()-_l.getX2())/2)};
				int[] y = {(int) _l.getY1(), (int) _l.getY2(), (int) _l.getY1()-20};
				brush.setColor(Color.black);
				//brush.fill(new Polygon(x,y,3));
                brush.drawImage(northPort, (int)_l.getX2(), (int)_l.getY2()-23,
                		/*(int)_l.getBounds().getWidth(), (int)_l.getBounds().getHeight(),*/ null);
			}
			if(_index==88){
				//brush.drawChars(todraw, 0, todraw.length,(int)_l.getX2()-50,(int) _l.getY1());
                brush.drawImage(portImage, (int)_l.getX1()-78, (int) _l.getY1()-15,
                		/*(int)_l.getBounds().getWidth(), (int)_l.getBounds().getHeight(),*/ null);
				int[] x = {(int) _l.getX1(), (int) _l.getX2(), (int) _l.getX2()};
				int[] y = {(int) _l.getY1(), (int) _l.getY2(), (int) _l.getY2()-40};
				brush.setColor(Color.black);
				//brush.fill(new Polygon(x,y,3));
                brush.drawImage(northwestPort, (int)_l.getX1()-49, (int)_l.getY1()-3,
                		/*(int)_l.getBounds().getWidth(), (int)_l.getBounds().getHeight(),*/ null);
			}
			if(_index==51){
				//brush.drawChars(todraw, 0, todraw.length,(int)_l.getX2()-100,(int) _l.getY1()+50);
                brush.drawImage(portImage, (int)_l.getX1()-42, (int) _l.getY1()+27,
                		/*(int)_l.getBounds().getWidth(), (int)_l.getBounds().getHeight(),*/ null);
				int[] x = {(int) _l.getX1(), (int) _l.getX2(), (int) _l.getX1()};
				int[] y = {(int) _l.getY1(), (int) _l.getY2(), (int) _l.getY1()+40};
				brush.setColor(Color.black);
				//brush.fill(new Polygon(x,y,3));
                brush.drawImage(southwestPort, (int)_l.getX1()-13, (int)_l.getY1()+7,
                		/*(int)_l.getBounds().getWidth(), (int)_l.getBounds().getHeight(),*/ null);
			}
			if(_index==67){
				//brush.drawChars(todraw, 0, todraw.length,(int)_l.getX2()+10,(int) _l.getY1()-10);
                brush.drawImage(portImage, (int)_l.getX2()+6, (int) _l.getY2()+24,
                		/*(int)_l.getBounds().getWidth(), (int)_l.getBounds().getHeight(),*/ null);
				int[] x = {(int) _l.getX1(), (int) _l.getX2(), (int) _l.getX2()};
				int[] y = {(int) _l.getY1(), (int) _l.getY2(), (int) _l.getY2()+40};
				brush.setColor(Color.black);
				//brush.fill(new Polygon(x,y,3));
                brush.drawImage(southeastPort, (int)_l.getX2()-28, (int)_l.getY2()+7,
                		/*(int)_l.getBounds().getWidth(), (int)_l.getBounds().getHeight(),*/ null);
			}
			break;
		}
		brush.setColor(_c);
        if (!_c.equals(Color.lightGray)) {
            brush.setStroke(new BasicStroke(5));
        } else {
            brush.setStroke(new BasicStroke(3));
        }
        brush.draw(_l);
	}
	
    @Override
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
		_c = _owner.getColor();
	}

}
