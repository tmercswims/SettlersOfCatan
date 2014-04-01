package boardGui;

import java.awt.Color;
import java.awt.Shape;

import javax.swing.JComponent;

public abstract class BoardComponent extends JComponent {

	public BoardComponent() {
		super();
	}
	
	public abstract Shape getShape();
	
	public abstract void setColor(Color c);
	
	public abstract void grow();

}