package edu.brown.cs032.sbreslow.catan.gui.board;

import java.awt.Color;
import java.awt.Shape;

import javax.swing.JComponent;

public abstract class BoardComponent extends JComponent {
    
    private static final long serialVersionUID = 902101441014129383L;

	private int _type;
	private int  _index;
	
	public BoardComponent() {
		super();
		_type = 0;
		_index = -1;
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

	public int getIndex() {
		return _index;
	}
	
	public void setIndex(int index){
		_index = index;
	}

}