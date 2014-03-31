package boardGui;

import java.awt.Component;

public class Edge extends Component{
	
	private Node[] _nodes = new Node[2];
	boolean _road;
	//private Player _owner;
	
	public Edge(){
		_road = false;
		//_owner = null;
	}
	
	public void setNodes(Node[] nodes){
		_nodes = nodes;
	}
	
	public Node[] getNodes(){
		return _nodes;
	}
	
	public boolean isRoad(){
		return _road;
	}
	
	/*public void setOwner(Player P){
		_road = true;
		_owner = p;
	}*/

}
