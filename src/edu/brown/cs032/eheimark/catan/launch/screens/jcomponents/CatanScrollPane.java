package edu.brown.cs032.eheimark.catan.launch.screens.jcomponents;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import edu.brown.cs032.eheimark.catan.gui.Constants;

/**
 * The Class CatanScrollPane is used to main scrollable text areas on the launch menu
 */
public class CatanScrollPane extends JScrollPane {
	private static final long serialVersionUID = -2301306725421906640L;
	private JTextArea textArea;

	/**
	 * Instantiates a new catan scrollable text area.
	 */
	public CatanScrollPane() {
		super();
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setForeground(Constants.CATAN_BLUE);
		setViewportView(textArea);
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
		return textArea;
	}
}
