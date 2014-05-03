package edu.brown.cs032.eheimark.catan.launch.screens;

import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import edu.brown.cs032.eheimark.catan.gui.Constants;

/**
 * The Class CatanMenu is used for generic background panels for the launch menu that can then
 * be customized with custom buttons and functionality on top of this generic panel.
 */
public class CatanMenu extends JPanel {
	private static final long serialVersionUID = 1L;
	private final Image img; // background image
	private GridBagConstraints gbc; // constraints
	private	final JPanel buttonsPanel = new JPanel(); // Panel that contains buttons you can add on each launch menu page
	
	/**
	 * Instantiates a new catan menu.
	 */
	public CatanMenu() {
		super();
		this.img = Constants.CATAN_LAUNCH_MENU_BACKGROUND;
		buttonsPanel.setOpaque(false);	
		buttonsPanel.setLayout(new GridBagLayout());
		setButtonsBorder(BorderFactory.createEmptyBorder(200, 0, 0, 0));
		add(buttonsPanel);
		setPreferredSize(Constants.DEFAULT_MENU_SIZE);
		gbc = new GridBagConstraints();
	}

	/**
	 * Adds a component to the page
	 *
	 * @param comp the component to add
	 */
	public void addComponent(Component comp) {
		gbc.gridy++;
		buttonsPanel.add(comp, gbc);
		
		
	}
	
	public void setButtonsBorder(Border b) {
		buttonsPanel.setBorder(b);
	}

	/**
	 * Paints.
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}
}
