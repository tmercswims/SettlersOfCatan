package boardGui;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Board {
	
	private Tile[] _tiles = new Tile[37];
	private Node[] _nodes = new Node[96];
	private Edge[] _edges = new Edge[132];
	private final int _x = 25;
	private final int _y = 38;
	
	public Board(){
		int x = 0;
		int y = 2*_y;
		for(int i = 0; i <= 95; i++){
			if(i<=8){
				if(i%2==0){
					x += _x;
				}
				else{
					x -= _x;
				}
				y += _y;
			}
			else if(i>8 && i<=15){
				if(i%2!=0){
					x +=2*_x;
				}//over
				else{
					x += _x;
					y += _y;
				}//down and over
			}
			else if(i>15 && i<=22){
				if(i%2==0){
					y -= _y;
					x += _x;
				}//up and over
				else{
					x += 2*_x;
				}//over
			}
			else if(i>22 && i <=29){
				if(i%2!=0){
					x -= _x;
				}//in and up
				else{
					x += _x;
				}//out and up
				y -= _y;
			}
			else if(i>29 && i<=36){
				if(i%2==0){
					x -= 2*_x;
				}
				else{
					x -= _x;
					y -= _y;
				}
			}
			else if(i>36 && i<=41){
				if(i%2!=0){
					x -= _x;
					y += _y;
				}
				else{
					x -= 2*_x;
				}
			}
			else if(i>41 && i<=48){
				if(i%2==0){
					x += _x;
				}
				else{
					x -= _x;
				}
				y += _y;
			}
			else if(i>48 && i<=53){
				if(i%2!=0){
					x +=2*_x;
				}//over
				else{
					x += _x;
					y += _y;
				}//down and over
			}
			else if(i>53 && i<=58){
				if(i%2==0){
					y -= _y;
					x += _x;
				}//up and over
				else{
					x += 2*_x;
				}//over
			}
			else if(i>58 && i<=63){
				if(i%2!=0){
					x -= _x;
				}//in and up
				else{
					x += _x;
				}//out and up
				y -= _y;
			}
			else if(i>63 && i <=68){
				if(i%2==0){
					x -= 2*_x;
				}
				else{
					x -= _x;
					y -= _y;
				}
			}
			else if(i>68 && i <=71){
				if(i%2!=0){
					x -= _x;
					y += _y;
				}
				else{
					x -= 2*_x;
				}
			}
			else if(i>71 && i<=76){
				if(i%2==0){
					x += _x;
				}
				else{
					x -= _x;
				}
				y += _y;
			}
			else if(i>76 && i<=79){
				if(i%2!=0){
					x +=2*_x;
				}//over
				else{
					x += _x;
					y += _y;
				}//down and over
			}
			else if(i>79 && i<=82){
				if(i%2==0){
					y -= _y;
					x += _x;
				}//up and over
				else{
					x += 2*_x;
				}//over
			}
			else if(i>82 && i<=85){
				if(i%2!=0){
					x -= _x;
				}//in and up
				else{
					x += _x;
				}//out and up
				y -= _y;
			}
			else if(i>85 && i<=88){
				if(i%2==0){
					x -= 2*_x;
				}
				else{
					x -= _x;
					y -= _y;
				}
			}
			else if(i>88 && i<=92){
				if(i%2!=0){
					x -= _x;
					y += _y;
				}
				else{
					x -= 2*_x;
				}
			}
			else if(i==93){
				x += 2*_x;
			}
			else if(i==94){
				x += _x;
				y -= _y;
			}
			else if(i==95){
				x -= _x;
				y -= _y;
			}
			else{
				System.err.println("WTF");
			}
			System.out.println("Printing node at: "+x+", "+y);
			_nodes[i] = new Node(x,y);
		}
		int i = 0;
		try {
			RandomAccessFile raf = new RandomAccessFile("tiletonode.tsv","r");
			raf.readLine();
			for(i = 0; i <= 36; i++){
				String[] line = raf.readLine().split("\t");
				//System.out.println(Arrays.toString(line));
				String[] nodes = line[1].split(",");
				int[] ndices = new int[nodes.length];
				for(int j = 0; j< nodes.length; j++){
					ndices[j] = Integer.parseInt(nodes[j]);
				}
				List<Node> list = new ArrayList<Node>(nodes.length);
				for(int j: ndices){
					list.add(_nodes[j]);
				}
				_tiles[i] = new Tile(0,0,null);
				_tiles[i].setNodes(list);
			}
		} catch (Exception e) {
			System.err.println("ERROR: "+e.getMessage());
			System.err.println(i);
			e.printStackTrace();
		}
	}
	
	public List<Tile> getTiles(){
		return Arrays.asList(_tiles);
	}
	
	public List<Node> getNodes(){
		return Arrays.asList(_nodes);
	}
	
	public List<Edge> getEdges(){
		return Arrays.asList(_edges);
	}
	
	public List<BoardComponent> getBoard(){
		List<BoardComponent> list = new ArrayList<BoardComponent>();
		list.addAll(Arrays.asList(_nodes));
		list.addAll(Arrays.asList(_tiles)); //SOMETHING WRONG WITH TILE LAYOUT
		return list;
	}
	
}
	
	/*private Tile[][] _tiles;
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

}*/
