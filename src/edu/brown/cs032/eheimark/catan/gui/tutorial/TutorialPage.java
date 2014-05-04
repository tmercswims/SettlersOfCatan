package edu.brown.cs032.eheimark.catan.gui.tutorial;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import edu.brown.cs032.eheimark.catan.gui.Constants;

public class TutorialPage extends JPanel {
	private static final long serialVersionUID = 1L;

	public TutorialPage(String message, ImageIcon icon) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		if(icon != null) {
			JLabel iconLabel = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth() / icon.getIconHeight() * 50, 50, Image.SCALE_SMOOTH)));
			iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			add(iconLabel);
		}
		JTextPane messagePane = new JTextPane();
		SimpleAttributeSet _tutorial = new SimpleAttributeSet();
		StyleConstants.setFontFamily(_tutorial, "Georgia");
		StyleConstants.setFontSize(_tutorial, 14);
		StyleConstants.setForeground(_tutorial, Color.BLACK);
		StyleConstants.setAlignment(_tutorial, StyleConstants.ALIGN_CENTER);
		messagePane.getStyledDocument().setParagraphAttributes(0, messagePane.getStyledDocument().getLength(), _tutorial, false);
		try {
			messagePane.getDocument().insertString(0, message, _tutorial);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		messagePane.setAlignmentX(Component.CENTER_ALIGNMENT);
		messagePane.setOpaque(false);
		messagePane.setEditable(false);
		add(messagePane);
		setPreferredSize(Constants.TUTORIAL_SIZE);
	}
}
