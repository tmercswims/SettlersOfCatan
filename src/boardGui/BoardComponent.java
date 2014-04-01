package boardGui;

import java.awt.Color;
import java.awt.Shape;

import javax.swing.JComponent;

public abstract class BoardComponent extends JComponent {

	private int _type;
	
	public BoardComponent() {
		super();
		_type = 0;
	}
	
	public abstract Shape getShape();
	
	public abstract void setColor(Color c);
	
	public abstract void grow();
	
	public void setType(int type){
		_type = type;
	}

	public int getType(){
		return _type;
	}

}