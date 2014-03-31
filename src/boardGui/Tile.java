package boardGui;

public class Tile {
	
	public final int _resource;
	public final int _num;
	private Node[] _nodes;
	boolean _robber;
	
	public Tile(int resource, int num, Node[] nodes){
		_resource = resource;
		_num = num;
		_nodes = nodes;
		if(num==0 && resource==5){
			_robber = true;
		}
		else{
			_robber = false;
		}
	}
	
	public boolean hasRobber(){
		return _robber;
	}
	
	public Node[] getNodes(){
		return _nodes;
	}
	
	public Node getNode(int index) throws ArrayIndexOutOfBoundsException{
		return _nodes[index];
	}
	
	public void setRobber(boolean robber){
		_robber = robber;
	}

}
