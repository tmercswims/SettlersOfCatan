package edu.brown.cs032.sbreslow.catan.gui.board;

import java.awt.Color;
import java.awt.Shape;

import javax.swing.JComponent;

public abstract class BoardComponent extends JComponent {
    
    private static final long serialVersionUID = 902101441014129383L;

	private int _type;
	private int  _index;
    int _ghostLevel = 0;
    Color _lookerColor;
	
	public BoardComponent() {
		super();
		_type = 0;
		_index = -1;
	}
    
    public void setLookerColor(Color c) {
        _lookerColor = c;
    }
    
    public void setGhostLevel(int ghostLevel) {
        _ghostLevel = ghostLevel;
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