package boardGui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
	
	private Tile[][] _tiles;
	private Edge[][] _edges;
	private Node[][] _nodes;
	private final int _scale = 100;
	
	public Board() {
		
		_tiles = new Tile[10][10];
		_edges = new Edge[22][22];
		_nodes = new Node[22][22];
		
		
		//init nodes
		for(int i = 0; i < 22; i++){
			for(int j = 0; j < 22; j++){
				_nodes[i][j] = new Node(i*_scale,j*_scale);
			}
		}
		//init tiles (w/nodes)
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				_tiles[i][j] = new Tile(0,0,null);
				_tiles[i][j].setIndex(i, j);
				_tiles[i][j].setNodes(getVerts(_tiles[i][j]));
			}
		}
		//init edges (w/nodes)
		for(int i = 0; i < 22; i++){
			for(int j = 0; j < 22; j++){
				Node[] ends = getEnds(i,j);
				if(ends!=null){
					_edges[i][j] = new Edge(getEnds(i,j));
				}
			}
		}
		//set tiles and edges for nodes
		for(int i = 0; i < 22; i++){
			for(int j = 0; j < 22; j++){
				_nodes[i][j].setEdges(getEdges(_nodes[i][j]));
				_nodes[i][j].setTiles(getTiles(_nodes[i][j]));
			}
		}
		
	}
	
	public List<Node> getVerts(Tile t){
		Point p = t.getIndex();
		int offset = p.x % 2;
		ArrayList<Node> verts = new ArrayList<Node>(6);
		verts.add(_nodes[p.x][2*p.y+offset]);
		verts.add(_nodes[p.x][2*p.y+1+offset]);
		verts.add(_nodes[p.x][2*p.y+2+offset]);
		verts.add(_nodes[p.x+1][2*p.y+offset]);
		verts.add(_nodes[p.x+1][2*p.y+1+offset]);
		verts.add(_nodes[p.x+1][2*p.y+2+offset]);
		return verts;
	}
	
	public Node[] getEnds(int x, int y){
		Node[] nodes = new Node[2];
		try{
			if(x%2 == 0){
				nodes[0] = _nodes[x/2][y];
				nodes[1] = _nodes[x/2][y+1];
			}
			else{
				nodes[0] = _nodes[(x-1)/2][y];
				nodes[1] = _nodes[(x+1)/2][y];
			}
			return nodes;
		}
		catch(ArrayIndexOutOfBoundsException e){
			return null;
		}
	}
	
	public List<Tile> getTiles(Node n){
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		int x = n.getX()/_scale;
		int y = n.getY()/_scale;
		int xoff = x % 2;
		int yoff = y % 2;
		try{
			tiles.add(_tiles[x-1][(y+yoff)/2-1]);
		}
		catch(ArrayIndexOutOfBoundsException e){
			
		}
		try{
			tiles.add(_tiles[x-(1-yoff)*xoff][(y-1)/2]);
		}
		catch(ArrayIndexOutOfBoundsException e){
			
		}
		try{
			tiles.add(_tiles[x][(y-xoff)/2]);
		}
		catch(ArrayIndexOutOfBoundsException e){
			
		}
		return tiles;
	}
	
	public List<Edge> getEdges(Node n){
		ArrayList<Edge> edges = new ArrayList<Edge>();
		int x = n.getX()/_scale;
		int y = n.getY()/_scale;
		try{
			edges.add(_edges[x*2][y]);
		}
		catch(ArrayIndexOutOfBoundsException e){
			
		}
		try{
			edges.add(_edges[x*2+1][y]);
		}
		catch(ArrayIndexOutOfBoundsException e){
			
		}
		try{
			edges.add(_edges[x*2-1][y]);
		}
		catch(ArrayIndexOutOfBoundsException e){
			
		}
		return edges;
	}
	
	public List<BoardComponent> getBoard(){
		List<BoardComponent> board = new ArrayList<BoardComponent>();
		for(int i = 0; i < 22; i++){
			for(int j = 0; j < 22; j++){
				if(i<10 && j<10){
					if(_tiles[i][j]!=null)
						board.add(_tiles[i][j]);
				}
				if(_nodes[i][j]!=null)
					board.add(_nodes[i][j]);
				if(_edges[i][j]!=null)
					board.add(_edges[i][j]);
			}
		}
		
		return board;
		
	}

}
