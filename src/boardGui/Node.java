package boardGui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;

public class Node extends BoardComp{
	
	private Tile[] _tiles = new Tile[3];
	private Edge[] _edges = new Edge[3];
	private int _vp;
	private boolean _owned;
	private Ellipse2D _o;
	private Color _c;
	//private Player _owner;
	//Port _port;
	
	public Node(int x, int y){
		super();
		this.setLocation(new Point(x,y));
		_owned = false;
		//_owner = null;
		//_port = null;
		_o = new Ellipse2D.Double(this.getX(), this.getY(), 6, 6);
		_c = Color.BLACK;
	}
	
	public boolean isOwned(){
		return _owned;
	}
	
	/*public void setOwner(Player p){
		_owned = true;
		_owner = p;
	}
	
	public Player getOwner(){
		if(_owned){
			return _owner;
		}
		else{
			throw new RuntimeException("NO OWNER");
		}
	}*/
	
	public Tile[] getTiles(){
		return _tiles;
	}
	
	public Tile getTile(int index) throws ArrayIndexOutOfBoundsException{
		return _tiles[index];
	}
	
	public void setTiles(Tile[] tiles){
		_tiles = tiles;
	}
	
	public Edge[] getEdges(){
		return _edges;
	}
	
	public Edge getEdge(int index) throws ArrayIndexOutOfBoundsException{
		return _edges[index];
	}
	
	public void setEdges(Edge[] edges){
		_edges = edges;
	}
	
	public void setVP(int vp){
		_vp = vp;
	}
	
	public int getVP(){
		return _vp;
	}
	
	/*public Port getPort(){
		return _port;
	}*/
	
	@Override
	public void paint(Graphics g){
		Graphics2D brush = (Graphics2D) g;
		brush.setColor(_c);
		brush.setStroke(new BasicStroke());
		brush.fill(_o);
		//brush.fillOval(this.getX(), this.getY(), 4, 4);
	}
	
	public Shape getShape(){
		return _o;
	}

	@Override
	public void setColor(Color c) {
		_c = c;
	}

}
