package edu.brown.cs032.eheimark.catan.jcomponents;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import edu.brown.cs032.eheimark.catan.gui.Constants;


public class CatanScrollableTextArea extends JScrollPane {
	private static final long serialVersionUID = 1L;
	private JTextArea textarea;
	
	public CatanScrollableTextArea() {
		super();
		textarea = new JTextArea();
		textarea.setEditable(false);
		textarea.setLineWrap(true);
		setViewportView(textarea);

		setPreferredSize(Constants.TEXTAREA_SIZE);
		setMinimumSize(Constants.TEXTAREA_SIZE);
		setMaximumSize(Constants.TEXTAREA_SIZE);
		setFont(Constants.DEFAULT_BUTTON_FONT);
		setForeground(Constants.CATAN_RED);
	}
	
	public JTextArea getTextArea() {
		return textarea;
	}
}
