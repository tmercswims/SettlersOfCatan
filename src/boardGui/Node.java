package boardGui;

import java.awt.Point;

public class Node extends Point {
	
	private Tile[] _tiles = new Tile[3];
	private Edge[] _edges = new Edge[3];
	private int _vp;
	private boolean _owned;
	//private Player _owner;
	//Port _port;
	
	public Node(){
		super();
		_owned = false;
		//_owner = null;
		//_port = null;
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
	

}
