package edu.brown.cs032.eheimark.catan.jcomponents;

import javax.swing.JTextField;
import edu.brown.cs032.eheimark.catan.gui.Constants;

public class CatanTextField extends JTextField {
	private static final long serialVersionUID = 1L;
	
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
