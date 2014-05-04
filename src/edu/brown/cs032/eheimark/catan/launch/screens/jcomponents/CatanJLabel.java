package edu.brown.cs032.eheimark.catan.launch.screens.jcomponents;

import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Colors.CATAN_BLUE;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Dimensions.DEFAULT_BUTTON_SIZE;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Fonts.DEFAULT_BUTTON_FONT;

import javax.swing.JLabel;

/**
 * The Class CatanJLabel is used to maintain settings for JLabels on the launch menu.
 */
public class CatanJLabel extends JLabel {
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new catan j label.
	 *
	 * @param name the name
	 */
	public CatanJLabel(String name) {
		super(name);
		setPreferredSize(DEFAULT_BUTTON_SIZE);
		setMinimumSize(DEFAULT_BUTTON_SIZE);
		setMaximumSize(DEFAULT_BUTTON_SIZE);
		setFont(DEFAULT_BUTTON_FONT);
		setHorizontalAlignment(JLabel.CENTER);
		setForeground(CATAN_BLUE);
	}

	/**
	 * Instantiates a new catan j label.
	 */
	public CatanJLabel() {
		setPreferredSize(DEFAULT_BUTTON_SIZE);
		setMinimumSize(DEFAULT_BUTTON_SIZE);
		setMaximumSize(DEFAULT_BUTTON_SIZE);
		setFont(DEFAULT_BUTTON_FONT);
		setHorizontalAlignment(JLabel.CENTER);
		setForeground(CATAN_BLUE);
	}
}
