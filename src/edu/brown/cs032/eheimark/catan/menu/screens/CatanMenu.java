package edu.brown.cs032.eheimark.catan.menu.screens;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import edu.brown.cs032.eheimark.catan.menu.Constants;

// Generic background panel for Catan Menu
public class CatanMenu extends JPanel {
	private static final long serialVersionUID = 1L;
	private final Image img; // background image
	private final String IMG_FILE_LOC = "images/CatanScaled700x700.png";
	private GridBagConstraints gbc;
	private	final JPanel buttonsPanel = new JPanel(); // Panel for buttons on a page

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

	public void addButton(Component comp) {
		gbc.gridy++;
		buttonsPanel.add(comp, gbc);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}
}
