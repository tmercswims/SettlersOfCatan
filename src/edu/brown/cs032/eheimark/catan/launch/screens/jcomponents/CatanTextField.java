package edu.brown.cs032.eheimark.catan.launch.screens.jcomponents;

import javax.swing.JTextField;

import edu.brown.cs032.eheimark.catan.gui.Constants;

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
		setPreferredSize(Constants.DEFAULT_BUTTON_SIZE);
		setMinimumSize(Constants.DEFAULT_BUTTON_SIZE);
		setMaximumSize(Constants.DEFAULT_BUTTON_SIZE);
		setHorizontalAlignment(JTextField.CENTER);
		setFont(Constants.DEFAULT_BUTTON_FONT);
		setForeground(Constants.CATAN_BLUE);
		setEditable(true);
	}
}
