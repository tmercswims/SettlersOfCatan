package edu.brown.cs032.eheimark.catan.launch.screens.jcomponents;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import edu.brown.cs032.eheimark.catan.gui.Constants;

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
		setPreferredSize(Constants.DEFAULT_BUTTON_SIZE);
		setMinimumSize(Constants.DEFAULT_BUTTON_SIZE);
		setMaximumSize(Constants.DEFAULT_BUTTON_SIZE);
		setFont(Constants.DEFAULT_BUTTON_FONT);
		setHorizontalAlignment(JLabel.CENTER);
		setForeground(Constants.CATAN_BLUE);
	}

	/**
	 * Instantiates a new catan j label.
	 */
	public CatanJLabel() {
		setPreferredSize(Constants.DEFAULT_BUTTON_SIZE);
		setMinimumSize(Constants.DEFAULT_BUTTON_SIZE);
		setMaximumSize(Constants.DEFAULT_BUTTON_SIZE);
		setFont(Constants.DEFAULT_BUTTON_FONT);
		setHorizontalAlignment(JLabel.CENTER);
		setForeground(Constants.CATAN_BLUE);
	}
}
