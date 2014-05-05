package edu.brown.cs032.eheimark.catan.gui.tutorial;

import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Dimensions.TUTORIAL_SIZE;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * The Class TutorialPage is a single page within the Tutorial. The Tutorial
 * consists of a collection of many pages which can be flipped through.
 */
public class TutorialPage extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new tutorial page.
	 *
	 * @param message the message to display on the page
	 * @param icon the icon to display on the page (or null if no icon)
	 */
	public TutorialPage(String message, ImageIcon icon) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel iconLabel = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth() / icon.getIconHeight() * 50, 50, Image.SCALE_SMOOTH)));
		iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(iconLabel);

		// Displays message. Uses text pane with style attributes for word wrapping, custom alignment, etc.
		JTextPane messagePane = new JTextPane();
		SimpleAttributeSet _tutorial = new SimpleAttributeSet();
		StyleConstants.setFontFamily(_tutorial, "Georgia");
		StyleConstants.setFontSize(_tutorial, 14);
		StyleConstants.setForeground(_tutorial, Color.BLACK);
		StyleConstants.setAlignment(_tutorial, StyleConstants.ALIGN_CENTER);
		messagePane.getStyledDocument().setParagraphAttributes(0, messagePane.getStyledDocument().getLength(), _tutorial, false);
		try {
			messagePane.getDocument().insertString(0, message, _tutorial);
		} catch (BadLocationException e) {} // Should not ever happen

		messagePane.setAlignmentX(Component.CENTER_ALIGNMENT);
		messagePane.setOpaque(false);
		messagePane.setEditable(false);
		add(messagePane);
		setPreferredSize(TUTORIAL_SIZE);
	}

	public TutorialPage(String message, Image img) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel iconLabel = new JLabel(new ImageIcon(img));
		iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(iconLabel);

		// Displays message. Uses text pane with style attributes for word wrapping, custom alignment, etc.
		JTextPane messagePane = new JTextPane();
		SimpleAttributeSet _tutorial = new SimpleAttributeSet();
		StyleConstants.setFontFamily(_tutorial, "Georgia");
		StyleConstants.setFontSize(_tutorial, 14);
		StyleConstants.setForeground(_tutorial, Color.BLACK);
		StyleConstants.setAlignment(_tutorial, StyleConstants.ALIGN_CENTER);
		messagePane.getStyledDocument().setParagraphAttributes(0, messagePane.getStyledDocument().getLength(), _tutorial, false);
		try {
			messagePane.getDocument().insertString(0, message, _tutorial);
		} catch (BadLocationException e) {} // Should not ever happen

		messagePane.setAlignmentX(Component.CENTER_ALIGNMENT);
		messagePane.setOpaque(false);
		messagePane.setEditable(false);
		add(messagePane);
	}

	public TutorialPage(String message) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Displays message. Uses text pane with style attributes for word wrapping, custom alignment, etc.
		JTextPane messagePane = new JTextPane();
		SimpleAttributeSet _tutorial = new SimpleAttributeSet();
		StyleConstants.setFontFamily(_tutorial, "Georgia");
		StyleConstants.setFontSize(_tutorial, 14);
		StyleConstants.setForeground(_tutorial, Color.BLACK);
		StyleConstants.setAlignment(_tutorial, StyleConstants.ALIGN_CENTER);
		messagePane.getStyledDocument().setParagraphAttributes(0, messagePane.getStyledDocument().getLength(), _tutorial, false);
		try {
			messagePane.getDocument().insertString(0, message, _tutorial);
		} catch (BadLocationException e) {} // Should not ever happen

		messagePane.setAlignmentX(Component.CENTER_ALIGNMENT);
		messagePane.setOpaque(false);
		messagePane.setEditable(false);
		add(messagePane);
		setPreferredSize(TUTORIAL_SIZE);
	}
}
