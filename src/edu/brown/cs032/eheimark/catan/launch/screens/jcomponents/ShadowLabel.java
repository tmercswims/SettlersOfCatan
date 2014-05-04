package edu.brown.cs032.eheimark.catan.launch.screens.jcomponents;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

import edu.brown.cs032.eheimark.catan.gui.Constants;

/**
 * The Class ShadowLabel is a JLabel with a blue shadow behind the text
 */
public class ShadowLabel extends JLabel {
	private static final long serialVersionUID = 5320121446782324729L;
	private String text; // Text to display in label
	private Font f; // Label font

	/**
	 * Instantiates a new shadow label.
	 */
	public ShadowLabel() {
		super();
	}

	/**
	 * Instantiates a new shadow label.
	 *
	 * @param text the text
	 */
	public ShadowLabel(String text) {
		super();
		setText(text);
		setPreferredSize(Constants.DEFAULT_BUTTON_SIZE);
		setMinimumSize(Constants.DEFAULT_BUTTON_SIZE);
		setMaximumSize(Constants.DEFAULT_BUTTON_SIZE);
		f = Constants.DEFAULT_BUTTON_FONT;
		setHorizontalAlignment(JLabel.CENTER);
		setForeground(Constants.CATAN_BLUE);
	}

	/**
	 * Paints a shadow label
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setFont(f);
		g2D.setColor(Constants.CATAN_BLUE);
		g2D.drawString(this.text, 11, 33);
		g2D.setColor(Constants.CATAN_WHITE);
		g2D.drawString(this.text, 10, 32);
		g2D.dispose();
	}

	/**
	 * Sets the ShadowLabel text
	 * @param text for the ShadowLabel
	 */
	@Override
	public void setText(String text) {
		this.text = text;
		repaint();
	}
}