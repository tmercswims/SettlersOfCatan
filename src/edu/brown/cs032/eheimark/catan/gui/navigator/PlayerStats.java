package edu.brown.cs032.eheimark.catan.gui.navigator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.brown.cs032.eheimark.catan.gui.Constants;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.border.EmptyBorder;

/**
 * The Class PlayerStats contains the stats for one player placed onto a JPanel. Stats
 * include items like name, victory points, number of dev cards, etc.
 */
public class PlayerStats extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final int LABEL_HEIGHT = 18;
	public static final Dimension MY_SIZE = new Dimension(950, 20);
	private final JLabel name, vps, devcards, roads, settlements, resources, cities;
	private final Image img;
	private boolean isActivePlayer;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;

	/**
	 * Instantiates a new player stats.
	 */
	public PlayerStats() {
		super();
		setOpaque(false);

		this.isActivePlayer = false;
		this.img = edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Misc.arrow;
												GridBagLayout gridBagLayout = new GridBagLayout();
												gridBagLayout.columnWidths = new int[]{1, 150, 125, 145, 170, 82, 117, 78, 0};
												gridBagLayout.rowHeights = new int[]{16, 0};
												gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
												gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
												setLayout(gridBagLayout);
																		
																				label = new JLabel("");
																				GridBagConstraints gbc_label = new GridBagConstraints();
																				gbc_label.anchor = GridBagConstraints.WEST;
																				gbc_label.insets = new Insets(0, 0, 0, 5);
																				gbc_label.gridx = 0;
																				gbc_label.gridy = 0;
																				add(label, gbc_label);
																
																		label_1 = new JLabel("");
																		GridBagConstraints gbc_label_1 = new GridBagConstraints();
																		gbc_label_1.anchor = GridBagConstraints.WEST;
																		gbc_label_1.insets = new Insets(0, 0, 0, 5);
																		gbc_label_1.gridx = 0;
																		gbc_label_1.gridy = 0;
																		add(label_1, gbc_label_1);
														
																label_2 = new JLabel("");
																GridBagConstraints gbc_label_2 = new GridBagConstraints();
																gbc_label_2.anchor = GridBagConstraints.WEST;
																gbc_label_2.insets = new Insets(0, 0, 0, 5);
																gbc_label_2.gridx = 0;
																gbc_label_2.gridy = 0;
																add(label_2, gbc_label_2);
																		
																				name = new JLabel("Name");
																				name.setPreferredSize(new Dimension(150, 16));
																				name.setHorizontalAlignment(SwingConstants.LEFT);
																				GridBagConstraints gbc_name = new GridBagConstraints();
																				gbc_name.anchor = GridBagConstraints.NORTHWEST;
																				gbc_name.insets = new Insets(0, 0, 0, 5);
																				gbc_name.gridx = 1;
																				gbc_name.gridy = 0;
																				add(name, gbc_name);
																				name.setFont(Constants.MY_FONT_SMALL);
																
																		vps = new JLabel("Victory Points");
																		vps.setBorder(new EmptyBorder(0, 10, 0, 20));
																		vps.setHorizontalAlignment(SwingConstants.CENTER);
																		GridBagConstraints gbc_vps = new GridBagConstraints();
																		gbc_vps.anchor = GridBagConstraints.NORTHWEST;
																		gbc_vps.insets = new Insets(0, 0, 0, 5);
																		gbc_vps.gridx = 2;
																		gbc_vps.gridy = 0;
																		add(vps, gbc_vps);
																		vps.setFont(Constants.MY_FONT_SMALL);
														
																resources = new JLabel("Resource Cards");
																resources.setBorder(new EmptyBorder(0, 20, 0, 20));
																resources.setHorizontalAlignment(SwingConstants.CENTER);
																resources.setFont(Constants.MY_FONT_SMALL);
																GridBagConstraints gbc_resources = new GridBagConstraints();
																gbc_resources.anchor = GridBagConstraints.NORTHWEST;
																gbc_resources.insets = new Insets(0, 0, 0, 5);
																gbc_resources.gridx = 3;
																gbc_resources.gridy = 0;
																add(resources, gbc_resources);
														
																devcards = new JLabel("Development Cards");
																devcards.setBorder(new EmptyBorder(0, 20, 0, 20));
																devcards.setHorizontalAlignment(SwingConstants.CENTER);
																GridBagConstraints gbc_devcards = new GridBagConstraints();
																gbc_devcards.anchor = GridBagConstraints.NORTHWEST;
																gbc_devcards.insets = new Insets(0, 0, 0, 5);
																gbc_devcards.gridx = 4;
																gbc_devcards.gridy = 0;
																add(devcards, gbc_devcards);
																devcards.setFont(Constants.MY_FONT_SMALL);
												
														roads = new JLabel("Roads");
														roads.setBorder(new EmptyBorder(0, 20, 0, 20));
														roads.setHorizontalAlignment(SwingConstants.CENTER);
														GridBagConstraints gbc_roads = new GridBagConstraints();
														gbc_roads.anchor = GridBagConstraints.NORTHWEST;
														gbc_roads.insets = new Insets(0, 0, 0, 5);
														gbc_roads.gridx = 5;
														gbc_roads.gridy = 0;
														add(roads, gbc_roads);
														roads.setFont(Constants.MY_FONT_SMALL);
														
																settlements = new JLabel("Settlements");
																settlements.setBorder(new EmptyBorder(0, 20, 0, 20));
																settlements.setHorizontalAlignment(SwingConstants.CENTER);
																GridBagConstraints gbc_settlements = new GridBagConstraints();
																gbc_settlements.anchor = GridBagConstraints.NORTHWEST;
																gbc_settlements.insets = new Insets(0, 0, 0, 5);
																gbc_settlements.gridx = 6;
																gbc_settlements.gridy = 0;
																add(settlements, gbc_settlements);
																settlements.setFont(Constants.MY_FONT_SMALL);
												
														cities = new JLabel("Cities");
														cities.setBorder(new EmptyBorder(0, 20, 0, 20));
														cities.setHorizontalAlignment(SwingConstants.CENTER);
														cities.setFont(Constants.MY_FONT_SMALL);
														GridBagConstraints gbc_cities = new GridBagConstraints();
														gbc_cities.anchor = GridBagConstraints.NORTHWEST;
														gbc_cities.gridx = 7;
														gbc_cities.gridy = 0;
														add(cities, gbc_cities);

		setColor(Color.black); // default color

		setPreferredSize(MY_SIZE);
		setMinimumSize(MY_SIZE);
		setMaximumSize(MY_SIZE);
	}

	/**
	 * Sets the color.
	 *
	 * @param c the new color
	 */
	public void setColor(Color c) {
		name.setForeground(c);
		vps.setForeground(c);
		devcards.setForeground(c);
		roads.setForeground(c);
		resources.setForeground(c);
		settlements.setForeground(c);
		cities.setForeground(c);
	}

	/**
	 * Sets the cities.
	 *
	 * @param s the new cities
	 */
	public void setCities(String s) {
		cities.setText(s);
	}

	/**
	 * Sets the settlements.
	 *
	 * @param s the new settlements
	 */
	public void setSettlements(String s) {
		settlements.setText(s);
	}

	/**
	 * Sets the resources.
	 *
	 * @param s the new resources
	 */
	public void setResources(String s) {
		resources.setText(s);
	}

	/**
	 * Sets the roads.
	 *
	 * @param s the new roads
	 */
	public void setRoads(String s) {
		roads.setText(s);
	}

	/**
	 * Sets the dev cards.
	 *
	 * @param s the new dev cards
	 */
	public void setDevCards(String s) {
		devcards.setText(s);
	}

	/**
	 * Sets the vps.
	 *
	 * @param s the new vps
	 */
	public void setVPs(String s) {
		vps.setText(s);
	}

	/**
	 * Sets the name.
	 *
	 * @param  s the new name
	 */
	public void setName(String s) {
		name.setText(s);
	}

	@Override
	public void paintComponent(Graphics g) {
		if(isActivePlayer) { // draws button indicating whether active player
			g.drawImage(img, 0, 4, null);
		}
	}

	/**
	 * Sets whether active player.
	 *
	 * @param b boolean indicating whether active player
	 */
	public void setActivePlayer(boolean b) {
		this.isActivePlayer = b;
	}
}
