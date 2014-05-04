package edu.brown.cs032.eheimark.catan.launch.screens;

import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Dimensions.DEFAULT_MENU_SIZE;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants;

/**
 * The Class CatanMenu is used for generic background panels for the launch menu that can then
 * be customized with custom buttons and functionality on top of this generic panel.
 */
public class CatanMenu extends JPanel {
	private static final long serialVersionUID = 1L;
	private GridBagConstraints gbc; // constraints
	private	final JPanel buttonsPanel = new JPanel(); // Panel that contains buttons you can add on each launch menu page

	/**
	 * Instantiates a new catan menu.
	 */
	public CatanMenu() {
		super();
		buttonsPanel.setOpaque(false);	
		buttonsPanel.setLayout(new GridBagLayout());
		setButtonsPanelBorder(BorderFactory.createEmptyBorder(200, 0, 0, 0)); // Positions buttons midway down screen
		add(buttonsPanel);
		setPreferredSize(DEFAULT_MENU_SIZE);
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
	 * Set top border for the buttonsPanel. Used when there are so many
	 * buttons that you cannot use the default CatanMenu border of 200.
	 * 
	 * @param Border
	 */
	public void setButtonsPanelBorder(Border b) {
		buttonsPanel.setBorder(b);
	}

	/**
	 * Paints background image.
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(GUIConstants.Background.CATAN_LAUNCH_MENU_BACKGROUND, 0, 0, null);
	}
}
