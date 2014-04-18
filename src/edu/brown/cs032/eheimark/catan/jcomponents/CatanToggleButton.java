package edu.brown.cs032.eheimark.catan.jcomponents;

import javax.swing.JToggleButton;
import edu.brown.cs032.eheimark.catan.gui.Constants;

// Generic toggle buttons for Catan Launch Menu's
public class CatanToggleButton extends JToggleButton {
	private static final long serialVersionUID = 1L;

	public CatanToggleButton(String name) {
		super(name);
		
		setPreferredSize(Constants.DEFAULT_BUTTON_SIZE);
		setMinimumSize(Constants.DEFAULT_BUTTON_SIZE);
		setMaximumSize(Constants.DEFAULT_BUTTON_SIZE);
		setFont(Constants.DEFAULT_BUTTON_FONT);
		
		setForeground(Constants.CATAN_BLUE);
	}
}
