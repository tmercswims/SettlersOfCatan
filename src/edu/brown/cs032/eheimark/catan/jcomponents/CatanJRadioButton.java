package edu.brown.cs032.eheimark.catan.jcomponents;

import javax.swing.JRadioButton;

import edu.brown.cs032.eheimark.catan.menu.Constants;

public class CatanJRadioButton extends JRadioButton {
	private static final long serialVersionUID = 1L;

	public CatanJRadioButton(String name) {
		super(name);
		
		setPreferredSize(Constants.DEFAULT_BUTTON_SIZE);
		setMinimumSize(Constants.DEFAULT_BUTTON_SIZE);
		setMaximumSize(Constants.DEFAULT_BUTTON_SIZE);
		
		setFont(Constants.DEFAULT_BUTTON_FONT);

		setForeground(Constants.CATAN_BLUE);

	}

}
