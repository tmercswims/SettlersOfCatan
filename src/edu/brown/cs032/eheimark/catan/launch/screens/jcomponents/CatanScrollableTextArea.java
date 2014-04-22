package edu.brown.cs032.eheimark.catan.launch.screens.jcomponents;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import edu.brown.cs032.eheimark.catan.gui.Constants;

/**
 * The Class CatanScrollableTextArea is used to main scrollable text areas on the launch menu
 */
public class CatanScrollableTextArea extends JScrollPane {
	private static final long serialVersionUID = 1L;
	private JTextArea textarea;

	/**
	 * Instantiates a new catan scrollable text area.
	 */
	public CatanScrollableTextArea() {
		super();
		textarea = new JTextArea();
		textarea.setEditable(false);
		textarea.setLineWrap(true);
		textarea.setWrapStyleWord(true);
		setViewportView(textarea);
		setPreferredSize(Constants.TEXTAREA_SIZE);
		setMinimumSize(Constants.TEXTAREA_SIZE);
		setMaximumSize(Constants.TEXTAREA_SIZE);
		setFont(Constants.DEFAULT_BUTTON_FONT);
		setForeground(Constants.CATAN_RED);
	}

	/**
	 * Gets the text area.
	 *
	 * @return the text area
	 */
	public JTextArea getTextArea() {
		return textarea;
	}
}
