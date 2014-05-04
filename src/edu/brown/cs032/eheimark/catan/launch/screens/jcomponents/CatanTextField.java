package edu.brown.cs032.eheimark.catan.launch.screens.jcomponents;

import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Colors.CATAN_BLUE;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Dimensions.DEFAULT_BUTTON_SIZE;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Fonts.DEFAULT_BUTTON_FONT;

import javax.swing.JTextField;

/**
 * The Class CatanTextField is used to maintain settings for JTextFields on the launch menu.
 */
public class CatanTextField extends JTextField {
	private static final long serialVersionUID = 516567057079417136L;

	/**
	 * Instantiates a new catan text field.
	 *
	 * @param name the name
	 */
	public CatanTextField(String name) {
		super(name);
		setPreferredSize(DEFAULT_BUTTON_SIZE);
		setMinimumSize(DEFAULT_BUTTON_SIZE);
		setMaximumSize(DEFAULT_BUTTON_SIZE);
		setHorizontalAlignment(JTextField.CENTER);
		setFont(DEFAULT_BUTTON_FONT);
		setForeground(CATAN_BLUE);
		setEditable(true);
	}
}
