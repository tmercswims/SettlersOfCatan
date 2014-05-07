package edu.brown.cs032.sbreslow.catan.gui.board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board implements Serializable {

	private static final long serialVersionUID = 6129113010292654449L;

	private final Tile[] _tiles = new Tile[37];
	private final Node[] _nodes = new Node[96];
	private final Edge[] _edges = new Edge[132];
	private int _x = 34;//25;//50
	private int _y = 55;//38;//75
	private List<Integer> _resources = new ArrayList<>(19);
	private List<Integer> _nums = new ArrayList<>(18);

	public Board(boolean small){
		if(small){
			_x = 26;
			_y = 42;
		}
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
			else if(i<15){
				_resources.add(1);//sheep
			}
			else{
				_resources.add(4);//wood
			}
		}
		setNum();

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
			_nodes[i] = new Node(x,y);
		}
		
		int i;
		try(BufferedReader raf = new BufferedReader(new InputStreamReader(Board.class.getResourceAsStream("/data/tiletonode.tsv")))) {
			raf.readLine();
			for(i = 0; i <= 36; i++){
				String[] line = raf.readLine().split("\t");
				String[] nodes = line[1].split(",");
				int[] ndices = new int[nodes.length];
				for(int j = 0; j< nodes.length; j++){
					ndices[j] = Integer.parseInt(nodes[j]);
				}
				List<Node> list = new ArrayList<>(nodes.length);
				for(int j: ndices){
					list.add(_nodes[j]);
				}
				if(i<=17){
					_tiles[i] = new Tile(6,0,null);
				}
				else{
					int rsrc = getResource();
					_tiles[i] = new Tile(rsrc,0,null);
				}
				_tiles[i].setNodes(list);
				_tiles[i].setIndex(i);
			}
		} catch (Exception ex) {
			System.err.println(String.format("ERROR: %s", ex.getMessage()));
		}
		
		try(BufferedReader raf = new BufferedReader(new InputStreamReader(Board.class.getResourceAsStream("/data/tiletonode.tsv")))) {
			raf.readLine();
			for(i = 0; i <= 36; i++){
				String[] line = raf.readLine().split("\t");
				String[] tiles = line[2].split(",");
				int[] tdices = new int[tiles.length];
				for(int j = 0; j< tiles.length; j++){
					tdices[j] = Integer.parseInt(tiles[j]);
				}
				List<Tile> tlist = new ArrayList<>(tiles.length);
				for(int j: tdices){
					tlist.add(_tiles[j]);
				}
				_tiles[i].setBors(tlist);
			}
		} catch (Exception ex) {
			System.err.println(String.format("ERROR: %s", ex.getMessage()));
		}
		layoutNums();
		try(BufferedReader raf = new BufferedReader(new InputStreamReader(Board.class.getResourceAsStream("/data/edgetonode.tsv")))) {
			raf.readLine();
			for(i = 0; i <= 131; i++){
				String[] line = raf.readLine().split("\t");
				String[] nodes = line[1].split(",");
				int[] ndices = new int[nodes.length];
				for(int j = 0; j< nodes.length; j++){
					ndices[j] = Integer.parseInt(nodes[j]);
				}
				Node[] tmp = {_nodes[ndices[0]],_nodes[ndices[1]]};
				_edges[i] = new Edge(tmp,i);
				_edges[i].setIndex(i);
				if(i==83 || i==67 || i==51 || i==88){
					_edges[i].setPort(5);
				}
				if(i==72){
					_edges[i].setPort(4);
				}
				if(i==78){
					_edges[i].setPort(2);
				}
				if(i==46){
					_edges[i].setPort(1);
				}
				if(i==56){
					_edges[i].setPort(3);
				}
				if(i==62){
					_edges[i].setPort(0);
				}
			}
		} catch (Exception ex) {
			System.err.println(String.format("ERROR: %s", ex.getMessage()));
		}
		try(BufferedReader raf = new BufferedReader(new InputStreamReader(Board.class.getResourceAsStream("/data/nodetoall.tsv")))){
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
				List<Tile> tlist = new ArrayList<>();
				for(int index: tiledices){
					tlist.add(_tiles[index]);
				}
				List<Edge> elist = new ArrayList<>();
				for(int index: edgedices){
					elist.add(_edges[index]);
				}
				_nodes[i].setTiles(tlist);
				_nodes[i].setEdges(elist);
				_nodes[i].setIndex(i);
			}
		} catch (Exception ex) {
			System.err.println(String.format("ERROR: %s", ex.getMessage()));
		}
	}
	
	public void resize(int x, int y){
		_x = x/23;
		_y = y/14;
		layoutChanges();
	}
    
    private void layoutChanges() {
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
			_nodes[i].setLoc(x,y);
		}
		try(BufferedReader raf = new BufferedReader(new InputStreamReader(Board.class.getResourceAsStream("/data/tiletonode.tsv")))) {
			raf.readLine();
			for(int i = 0; i <= 36; i++){
				String[] line = raf.readLine().split("\t");
				String[] nodes = line[1].split(",");
				int[] ndices = new int[nodes.length];
				for(int j = 0; j< nodes.length; j++){
					ndices[j] = Integer.parseInt(nodes[j]);
				}
				List<Node> list = new ArrayList<>(nodes.length);
				for(int j: ndices){
					list.add(_nodes[j]);
				}
				_tiles[i].setPoly(list);
			}
		} catch (Exception ex) {
			System.err.println(String.format("ERROR: %s", ex.getMessage()));
		}
		try(BufferedReader raf = new BufferedReader(new InputStreamReader(Board.class.getResourceAsStream("/data/edgetonode.tsv")))) {
			raf.readLine();
			for(int i = 0; i <= 131; i++){
				String[] line = raf.readLine().split("\t");
				String[] nodes = line[1].split(",");
				int[] ndices = new int[nodes.length];
				for(int j = 0; j< nodes.length; j++){
					ndices[j] = Integer.parseInt(nodes[j]);
				}
				Node[] tmp = {_nodes[ndices[0]],_nodes[ndices[1]]};
				_edges[i].setNodes(tmp);// = new Edge(tmp,i);
			}
		} catch (IOException ex) {
			System.err.println(String.format("ERROR: %s", ex.getMessage()));
		}
	}

	public void clearEdges() {
        for (Edge e : _edges) {
            e.setVisited(false);
        }
    }

	private void layoutNums() {
		for(int k = 18; k < _tiles.length; k++){
			if(_tiles[k]._resource==5){
				_tiles[k].setNum(0);
				_tiles[k].setRobber(true);
			}
			else{
				_tiles[k].setNum(getNum(_tiles[k]));
			}
		}
		for(Tile t: _tiles){
			/*if(t._num==6 || t._num==8){
				for(Tile tmp: t.getBors()){
					if(tmp._num==6 || tmp._num==8){
						setNum();
						layoutNums();
					}
				}
			}*/
			if(t._num!=0){
				for(Tile tmp: t.getBors()){
					if(((tmp._num==6 || tmp._num==8)&&(t._num==6 || t._num==8))||
							(t._num==tmp._num)){
						setNum();
						layoutNums();
					}
				}
			}
		}
	}

	private void setNum(){
		_nums.clear();
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
	}

	private int getNum(Tile tile) {
		int num = _nums.remove((int)(Math.random()*_nums.size()));
		return num;
	}

	private int getResource() {
		return _resources.remove((int)(Math.random()*_resources.size()));
	}

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
		ArrayList<BoardComponent> list = new ArrayList<>();
		list.addAll(Arrays.asList(_nodes));
		list.addAll(Arrays.asList(_tiles));
		list.addAll(Arrays.asList(_edges));
		return list;
	}

}
