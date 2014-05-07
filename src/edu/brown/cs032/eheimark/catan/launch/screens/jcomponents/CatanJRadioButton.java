package edu.brown.cs032.eheimark.catan.launch.screens.jcomponents;

import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Colors.CATAN_BLUE;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Colors.CATAN_WHITE;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Dimensions.DEFAULT_BUTTON_SIZE;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Fonts.DEFAULT_BUTTON_FONT;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JRadioButton;

/**
 * The Class CatanJRadioButton is used to maintain settings for JRadioButtons on the launch menu.
 */
public class CatanJRadioButton extends JRadioButton {
	private static final long serialVersionUID = -53379452102351836L;
	private final Font f;
	private final String text;
	/**
	 * Instantiates a new catan j radio button.
	 *
	 * @param name the name
	 */
	public CatanJRadioButton(String name) {
		super();
		text = name;
		setPreferredSize(DEFAULT_BUTTON_SIZE);
		setMinimumSize(DEFAULT_BUTTON_SIZE);
		setMaximumSize(DEFAULT_BUTTON_SIZE);
		setFont(DEFAULT_BUTTON_FONT);
		setForeground(CATAN_BLUE);
		f = DEFAULT_BUTTON_FONT;
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
		g2D.setColor(CATAN_BLUE);
		g2D.drawString(text, 27, 29);
		g2D.setColor(CATAN_WHITE);
		g2D.drawString(text, 26, 28);
		g2D.dispose();
	}
}
