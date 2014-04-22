package edu.brown.cs032.eheimark.catan.launch.screens.jcomponents;

import javax.swing.JToggleButton;
import edu.brown.cs032.eheimark.catan.gui.Constants;

/**
 * The Class CatanToggleButton is used to maintain settings for JToggleButtons on the launch menu.
 */
public class CatanToggleButton extends JToggleButton {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new catan toggle button.
	 *
	 * @param name the name
	 */
	public CatanToggleButton(String name) {
		super(name);
		setPreferredSize(Constants.DEFAULT_BUTTON_SIZE);
		setMinimumSize(Constants.DEFAULT_BUTTON_SIZE);
		setMaximumSize(Constants.DEFAULT_BUTTON_SIZE);
		setFont(Constants.DEFAULT_BUTTON_FONT);
		setForeground(Constants.CATAN_BLUE);
	}
}
