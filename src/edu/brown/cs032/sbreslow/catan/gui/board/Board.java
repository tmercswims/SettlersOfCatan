package edu.brown.cs032.sbreslow.catan.gui.board;

import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board implements Serializable {
    
    private static final long serialVersionUID = 6129113010292654449L;
	
	private Tile[] _tiles = new Tile[37];
	private Node[] _nodes = new Node[96];
	private Edge[] _edges = new Edge[132];
	private final int _x = 34;//25;//50
	private final int _y = 55;//38;//75
	private List<Integer> _resources = new ArrayList<Integer>(19);
	private List<Integer> _nums = new ArrayList<Integer>(18);
	
	public Board(){
		for(int i = 0; i < 19; i++){
			if(i<1){
				_resources.add(5);//desert
			}
			else if(i<4){
				_resources.add(3);//ore
			}
			else if(i<7){
				_resources.add(2);//brick
			}
			else if(i<11){
				_resources.add(0);//wheat
			}
			else if(i<14){
				_resources.add(1);//sheep
			}
			else{
				_resources.add(4);//wood
			}
		}
		for(int i = 0; i < 18; i++){
			switch(i){
			case 0:
				_nums.add(2);
				break;
			case 1:
				_nums.add(3);
				break;
			case 2:
				_nums.add(3);
				break;
			case 3:
				_nums.add(4);
				break;
			case 4:
				_nums.add(4);
				break;
			case 5:
				_nums.add(5);
				break;
			case 6:
				_nums.add(5);
				break;
			case 7:
				_nums.add(6);
				break;
			case 8:
				_nums.add(6);
				break;
			case 9:
				_nums.add(8);
				break;
			case 10:
				_nums.add(8);
				break;
			case 11:
				_nums.add(9);
				break;
			case 12:
				_nums.add(9);
				break;
			case 13:
				_nums.add(10);
				break;
			case 14:
				_nums.add(10);
				break;
			case 15:
				_nums.add(11);
				break;
			case 16:
				_nums.add(11);
				break;
			case 17:
				_nums.add(12);
				break;
			}
		}
		
		int x = 0;
		int y = 2*_y;
		for(int i = 0; i <= 95; i++){
			if((i<=8)||(i>41 && i<=48)||(i>71 && i<=76)||(i>88 && i<=92)){
				if(i%2==0){
					x += _x;
				}
				else{
					x -= _x;
				}
				y += _y;
			}
			else if((i>8 && i<=15)||(i>48 && i<=53)||(i>76 && i<=79)){
				if(i%2!=0){
					x +=2*_x;
				}//over
				else{
					x += _x;
					y += _y;
				}//down and over
			}
			else if((i>15 && i<=22)||(i>53 && i<=58)||(i>79 && i<=82)){
				if(i%2==0){
					y -= _y;
					x += _x;
				}//up and over
				else{
					x += 2*_x;
				}//over
			}
			else if((i>22 && i <=29)||(i>58 && i<=63)||(i>82 && i<=85)){
				if(i%2!=0){
					x -= _x;
				}//in and up
				else{
					x += _x;
				}//out and up
				y -= _y;
			}
			else if((i>29 && i<=36)||(i>63 && i<=68)||(i>85 && i<=88)){
				if(i%2==0){
					x -= 2*_x;
				}
				else{
					x -= _x;
					y -= _y;
				}
			}
			else if((i>36 && i<=41)||(i>68 && i<=71)){
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
			_nodes[i] = new Node(x,y);
		}
		int i = 0;
		try(RandomAccessFile raf = new RandomAccessFile("tiletonode.tsv","r")) {
			raf.readLine();
			for(i = 0; i <= 36; i++){
				String[] line = raf.readLine().split("\t");
				String[] nodes = line[1].split(",");
				int[] ndices = new int[nodes.length];
				for(int j = 0; j< nodes.length; j++){
					ndices[j] = Integer.parseInt(nodes[j]);
				}
				List<Node> list = new ArrayList<Node>(nodes.length);
				for(int j: ndices){
					list.add(_nodes[j]);
				}
				if(i<=17){
					_tiles[i] = new Tile(6,0,null);
				}
				else{
					int rsrc = getResource();
					if(rsrc==5){
						_tiles[i] = new Tile(rsrc,0,null);
					}
					else{
						_tiles[i] = new Tile(rsrc,getNum(),null);
					}
				}
				_tiles[i].setNodes(list);
			}
		} catch (Exception e) {
			System.err.println("ERROR: "+e.getMessage());
			System.err.println(i);
			e.printStackTrace();
		}
		try(RandomAccessFile raf = new RandomAccessFile("edgetonode.tsv","r")) {
			raf.readLine();
			for(i = 0; i <= 131; i++){
				String[] line = raf.readLine().split("\t");
				String[] nodes = line[1].split(",");
				int[] ndices = new int[nodes.length];
				for(int j = 0; j< nodes.length; j++){
					ndices[j] = Integer.parseInt(nodes[j]);
				}
				Node[] tmp = {_nodes[ndices[0]],_nodes[ndices[1]]};
				_edges[i] = new Edge(tmp);
			}
		} catch (Exception e) {
			System.err.println("ERROR: "+e.getMessage());
			System.err.println(i);
			e.printStackTrace();
		}
		try(RandomAccessFile raf = new RandomAccessFile("nodetoall.tsv","r")){
			raf.readLine();
			for(i = 0; i <= 95; i++){
				String[] line = raf.readLine().split("\t");
				String[] tiles = line[1].split(",");
				String[] edges = line[2].split(",");
				int[] tiledices = new int[tiles.length];
				int[] edgedices = new int[edges.length];
				for(int j = 0; j < tiledices.length; j++){
					tiledices[j] = Integer.parseInt(tiles[j]);
				}
				for(int j = 0; j < edgedices.length; j++){
					edgedices[j] = Integer.parseInt(edges[j]);
				}
				List<Tile> tlist = new ArrayList<Tile>();
				for(int index: tiledices){
					tlist.add(_tiles[index]);
				}
				List<Edge> elist = new ArrayList<Edge>();
				for(int index: edgedices){
					elist.add(_edges[index]);
				}
				_nodes[i].setTiles(tlist);
				_nodes[i].setEdges(elist);
			}
		} catch (Exception e) {
			System.err.println("ERROR: "+e.getMessage());
			System.err.println(i);
			e.printStackTrace();
		}
	}
	
	private int getNum() {
		return _nums.remove((int)(Math.random()*_nums.size()));
	}

	private int getResource() {
		return _resources.remove((int)(Math.random()*_resources.size()));
	}

	/*public List<Tile> getTiles(){
		return (ArrayList<Tile>)Arrays.asList(_tiles);
	}
	
	public List<Node> getNodes(){
		return (ArrayList<Node>)Arrays.asList(_nodes);
	}
	
	public List<Edge> getEdges(){
		return (ArrayList<Edge>)Arrays.asList(_edges);
	}*/
	
	public Tile[] getTiles(){
		return _tiles;
	}
	
	public Edge[] getEdges(){
		return _edges;
	}
	
	public Node[] getNodes(){
		return _nodes;
	}
	
	public List<BoardComponent> getBoard(){
		ArrayList<BoardComponent> list = new ArrayList<BoardComponent>();
		list.addAll(Arrays.asList(_nodes));
		list.addAll(Arrays.asList(_tiles));
		list.addAll(Arrays.asList(_edges));
		return list;
	}
	
}
