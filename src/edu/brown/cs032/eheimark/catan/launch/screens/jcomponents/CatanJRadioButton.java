package edu.brown.cs032.eheimark.catan.launch.screens.jcomponents;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JRadioButton;

import edu.brown.cs032.eheimark.catan.gui.Constants;

/**
 * The Class CatanJRadioButton is used to maintain settings for JRadioButtons on the launch menu.
 */
public class CatanJRadioButton extends JRadioButton {
	private static final long serialVersionUID = -53379452102351836L;
	private Font f;
	private final String text;
	/**
	 * Instantiates a new catan j radio button.
	 *
	 * @param name the name
	 */
	public CatanJRadioButton(String name) {
		super();
		text = name;
		setPreferredSize(Constants.DEFAULT_BUTTON_SIZE);
		setMinimumSize(Constants.DEFAULT_BUTTON_SIZE);
		setMaximumSize(Constants.DEFAULT_BUTTON_SIZE);
		setFont(Constants.DEFAULT_BUTTON_FONT);
		setForeground(Constants.CATAN_BLUE);
		f = Constants.DEFAULT_BUTTON_FONT;
	}

	/**
	 * Paints radio button with custom shadow text
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setFont(f);
		g2D.setColor(Constants.CATAN_BLUE);
		g2D.drawString(text, 27, 29);
		g2D.setColor(Constants.CATAN_WHITE);
		g2D.drawString(text, 26, 28);
		g2D.dispose();
	}
}
