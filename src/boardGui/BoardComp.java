package boardGui;

import java.awt.Color;
import java.awt.Shape;

import javax.swing.JComponent;

public abstract class BoardComp extends JComponent {

	public BoardComp() {
		super();
	}
	
	public abstract Shape getShape();
	
	public abstract void setColor(Color c);

}
