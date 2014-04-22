package edu.brown.cs032.eheimark.catan.launch.screens;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import edu.brown.cs032.eheimark.catan.gui.Constants;

/**
 * The Class CatanMenu is used for generic background panels for the launch menu that can then
 * be customized with custom buttons and functionality on top of this generic panel.
 */
public class CatanMenu extends JPanel {
	private static final long serialVersionUID = 1L;
	private final Image img; // background image
	private final String IMG_FILE_LOC = "images/CatanScaled700x700.png";
	private GridBagConstraints gbc; // constraints
	private	final JPanel buttonsPanel = new JPanel(); // Panel that contains buttons you can add on each launch menu page
	/**
	 * Instantiates a new catan menu.
	 */
	public CatanMenu() {
		super();
		this.img = new ImageIcon(IMG_FILE_LOC).getImage();
		setPreferredSize(Constants.DEFAULT_MENU_SIZE);
		setMinimumSize(Constants.DEFAULT_MENU_SIZE);
		setMaximumSize(Constants.DEFAULT_MENU_SIZE);
		buttonsPanel.setOpaque(false);	
		buttonsPanel.setLayout(new GridBagLayout());
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(250, 0, 0, 0));
		add(buttonsPanel);
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

	/**
	 * Paints.
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}
}
