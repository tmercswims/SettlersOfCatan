package edu.brown.cs032.eheimark.catan.launch.screens.jcomponents;

import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Colors.CATAN_BLUE;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Dimensions.DEFAULT_BUTTON_SIZE;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Fonts.DEFAULT_BUTTON_FONT;

import javax.swing.JToggleButton;

/**
 * The Class CatanToggleButton is used to maintain settings for JToggleButtons on the launch menu.
 */
public class CatanToggleButton extends JToggleButton {
	private static final long serialVersionUID = 7948783062925761229L;

	/**
	 * Instantiates a new catan toggle button.
	 *
	 * @param name the name
	 */
	public CatanToggleButton(String name) {
		super(name);
		setPreferredSize(DEFAULT_BUTTON_SIZE);
		setMinimumSize(DEFAULT_BUTTON_SIZE);
		setMaximumSize(DEFAULT_BUTTON_SIZE);
		setFont(DEFAULT_BUTTON_FONT);
		setForeground(CATAN_BLUE);
	}
}
