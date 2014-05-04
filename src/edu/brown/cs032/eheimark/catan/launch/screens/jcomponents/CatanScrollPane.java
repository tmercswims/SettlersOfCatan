package edu.brown.cs032.eheimark.catan.launch.screens.jcomponents;

import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Colors.CATAN_BLUE;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Colors.CATAN_RED;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Dimensions.TEXTAREA_SIZE;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Fonts.DEFAULT_BUTTON_FONT;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
		textArea.setForeground(CATAN_BLUE);
		setViewportView(textArea);
		setPreferredSize(TEXTAREA_SIZE);
		setMinimumSize(TEXTAREA_SIZE);
		setMaximumSize(TEXTAREA_SIZE);
		setFont(DEFAULT_BUTTON_FONT);
		setForeground(CATAN_RED);
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
