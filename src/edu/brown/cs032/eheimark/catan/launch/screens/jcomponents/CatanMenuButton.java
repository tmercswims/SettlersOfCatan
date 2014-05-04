package edu.brown.cs032.eheimark.catan.launch.screens.jcomponents;

import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Colors.CATAN_RED;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Dimensions.DEFAULT_BUTTON_SIZE;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Fonts.DEFAULT_BUTTON_FONT;

import javax.swing.Action;
import javax.swing.JButton;

/**
 * The Class CatanMenuButton is used to maintain settings for buttons on the launch menu.
 */
public class CatanMenuButton extends JButton {
	private static final long serialVersionUID = -2553782016402532610L;

	/**
	 * Instantiates a new catan menu button.
	 *
	 * @param name the name
	 */
	public CatanMenuButton(String name) {
		super(name);
		setPreferredSize(DEFAULT_BUTTON_SIZE);
		setMinimumSize(DEFAULT_BUTTON_SIZE);
		setMaximumSize(DEFAULT_BUTTON_SIZE);
		setFont(DEFAULT_BUTTON_FONT);
		setForeground(CATAN_RED);
	}
	
	public CatanMenuButton(Action e) {
		super(e);
		setPreferredSize(DEFAULT_BUTTON_SIZE);
		setMinimumSize(DEFAULT_BUTTON_SIZE);
		setMaximumSize(DEFAULT_BUTTON_SIZE);
		setFont(DEFAULT_BUTTON_FONT);
		setForeground(CATAN_RED);
	}
}
