package edu.brown.cs032.eheimark.catan.jcomponents;

import javax.swing.JButton;
import edu.brown.cs032.eheimark.catan.gui.Constants;

// Generic buttons for Catan Launch Menu's
public class CatanMenuButton extends JButton {
	private static final long serialVersionUID = 1L;
	
	public CatanMenuButton(String name) {
		super(name);
		
		setPreferredSize(Constants.DEFAULT_BUTTON_SIZE);
		setMinimumSize(Constants.DEFAULT_BUTTON_SIZE);
		setMaximumSize(Constants.DEFAULT_BUTTON_SIZE);
		
		setFont(Constants.DEFAULT_BUTTON_FONT);
		
		setForeground(Constants.CATAN_RED);
	}
}
