package gui.board;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

public class Node extends BoardComponent{
	
	private List<Tile> _tiles = new ArrayList<Tile>();
	private List<Edge> _edges = new ArrayList<Edge>();
	private int _vp;
	private boolean _owned;
	private Ellipse2D _o;
	private Color _c;
	public final static int _diam = 20;
	private int _scale = 1;
	//private Player _owner;
	//Port _port;
	
	public Node(int x, int y){
		super();
		this.setLocation(new Point(x,y));
		_owned = false;
		//_owner = null;
		//_port = null;
		_o = new Ellipse2D.Double(this.getX(), this.getY(), _diam, _diam);
		_c = Color.BLACK;
		_vp = 0;
		this.setType(2);
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
	
	public List<Tile> getTiles(){
		return _tiles;
	}
	
	public Tile getTile(int index) throws ArrayIndexOutOfBoundsException{
		return _tiles.get(index);
	}
	
	public void setTiles(List<Tile> list){
		_tiles = list;
	}
	
	public List<Edge> getEdges(){
		return _edges;
	}
	
	public Edge getEdge(int index) throws ArrayIndexOutOfBoundsException{
		return _edges.get(index);
	}
	
	public void setEdges(List<Edge> list){
		_edges = list;
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
		brush.setColor(Color.WHITE);
		char[] toprint = Integer.toString(_vp).toCharArray();
		Rectangle r = _o.getBounds();
		brush.drawChars(toprint, 0, 1, (int)r.getCenterX()-Node._diam/4, (int)r.getCenterY()+Node._diam/4);
		//brush.fillOval(this.getX(), this.getY(), 4, 4);
	}
	
	public Shape getShape(){
		return _o;
	}

	@Override
	public void setColor(Color c) {
		_c = c;
	}

	@Override
	public void grow() {
		// TODO Auto-generated method stub
		if(_scale==1){
			/*Rectangle r = this.getBounds();
			Double x = r.getCenterX();
			Double y = r.getCenterY();
			System.out.println(_diam*Math.pow(2,_scale-1));*/
			System.out.println("X: "+this.getX()+", Y: "+this.getY());
			_o = new Ellipse2D.Double(this.getX()-_diam/2,this.getY()-_diam/2, _diam*Math.pow(2,_scale), _diam*Math.pow(2,_scale));
			_scale++;
			_vp++;
		}
		else if(_scale==2){
			_o = new Ellipse2D.Double(this.getX()-3*(_diam/2),this.getY()-3*(_diam/2), _diam*Math.pow(2,_scale), _diam*Math.pow(2,_scale));
			_scale++;
			_vp++;
		}
	}

}
