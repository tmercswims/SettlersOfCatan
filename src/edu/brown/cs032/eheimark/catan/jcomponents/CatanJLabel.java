package edu.brown.cs032.eheimark.catan.jcomponents;
import javax.swing.JLabel;

import edu.brown.cs032.eheimark.catan.menu.Constants;

public class CatanJLabel extends JLabel {
	private static final long serialVersionUID = 1L;

	public CatanJLabel(String name) {
		super(name);
		setPreferredSize(Constants.DEFAULT_BUTTON_SIZE);
		setMinimumSize(Constants.DEFAULT_BUTTON_SIZE);
		setMaximumSize(Constants.DEFAULT_BUTTON_SIZE);
		setFont(Constants.DEFAULT_BUTTON_FONT);
		setHorizontalAlignment(JLabel.CENTER);
		setForeground(Constants.CATAN_BLUE);
	}
	
	public CatanJLabel() {
		setPreferredSize(Constants.DEFAULT_BUTTON_SIZE);
		setMinimumSize(Constants.DEFAULT_BUTTON_SIZE);
		setMaximumSize(Constants.DEFAULT_BUTTON_SIZE);
		setFont(Constants.DEFAULT_BUTTON_FONT);
		setHorizontalAlignment(JLabel.CENTER);
		setForeground(Constants.CATAN_BLUE);
	}
}
