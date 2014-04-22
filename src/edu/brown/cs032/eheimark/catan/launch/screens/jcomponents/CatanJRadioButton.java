package edu.brown.cs032.eheimark.catan.launch.screens.jcomponents;

import javax.swing.JRadioButton;
import edu.brown.cs032.eheimark.catan.gui.Constants;

/**
 * The Class CatanJRadioButton is used to maintain settings for JRadioButtons on the launch menu.
 */
public class CatanJRadioButton extends JRadioButton {
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new catan j radio button.
	 *
	 * @param name the name
	 */
	public CatanJRadioButton(String name) {
		super(name);
		setPreferredSize(Constants.DEFAULT_BUTTON_SIZE);
		setMinimumSize(Constants.DEFAULT_BUTTON_SIZE);
		setMaximumSize(Constants.DEFAULT_BUTTON_SIZE);
		setFont(Constants.DEFAULT_BUTTON_FONT);
		setForeground(Constants.CATAN_BLUE);
	}
}
