package edu.brown.cs032.eheimark.catan.launch.screens.jcomponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

import edu.brown.cs032.eheimark.catan.gui.Constants;

public class ShadowLabel extends JLabel {

	private String text;

	private Font f;

	public ShadowLabel() {
		super();
	}

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

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2D = (Graphics2D) g;
		// antialiasing
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		/**
		 * draw text
		 */
		g2D.setFont(f);
		g2D.setColor(Constants.CATAN_BLUE);
		g2D.drawString(this.text, 11, 33);
		g2D.setColor(Constants.CATAN_WHITE);
		g2D.drawString(this.text, 10, 32);
		g2D.dispose();
	}

	public void setText(String text) {
		this.text = text;
		repaint();
	}

	/**
	 * Default UID
	 */
	private static final long serialVersionUID = 1L;

}