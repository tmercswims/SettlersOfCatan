package edu.brown.cs032.eheimark.catan.gui.navigator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.brown.cs032.eheimark.catan.gui.Constants;

/**
 * The Class PlayerStats contains the stats for one player placed onto a JPanel. Stats
 * include items like name, victory points, number of dev cards, etc.
 */
public class PlayerStats extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final int LABEL_WIDTH = 80;
	private static final int LABEL_HEIGHT = 15;
	public static final Dimension MY_SIZE = new Dimension(850, 20);
	private static final int HEIGHT = 4;
	private final JLabel name, vps, devcards, roads, settlements, resources, cities;
	private final Image img;
	private boolean isActivePlayer;
	
	/**
	 * Instantiates a new player stats.
	 */
	public PlayerStats() {
		super();
		setLayout(null);
		setOpaque(false);
		
		this.isActivePlayer = false;
		this.img = edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Misc.arrow;

		name = new JLabel("Name");
		name.setBounds(36, HEIGHT, LABEL_WIDTH, LABEL_HEIGHT);
		add(name);
		name.setFont(Constants.MY_FONT_SMALL);
		
		vps = new JLabel("VPs");
		vps.setBounds(152, HEIGHT, LABEL_WIDTH, LABEL_HEIGHT);
		add(vps);
		vps.setFont(Constants.MY_FONT_SMALL);
		
		devcards = new JLabel("devCards");
		devcards.setBounds(384, HEIGHT, LABEL_WIDTH, LABEL_HEIGHT);
		add(devcards);
		devcards.setFont(Constants.MY_FONT_SMALL);
		
		roads = new JLabel("roads");
		roads.setBounds(500, HEIGHT, LABEL_WIDTH, LABEL_HEIGHT);
		add(roads);
		roads.setFont(Constants.MY_FONT_SMALL);
		
		settlements = new JLabel("settlements");
		settlements.setBounds(616, HEIGHT, LABEL_WIDTH, LABEL_HEIGHT);
		add(settlements);
		settlements.setFont(Constants.MY_FONT_SMALL);
		
		resources = new JLabel("resourceCards");
		resources.setBounds(268, HEIGHT, LABEL_WIDTH, LABEL_HEIGHT);
		resources.setFont(Constants.MY_FONT_SMALL);
		add(resources);
		
		cities = new JLabel("cities");
		cities.setFont(Constants.MY_FONT_SMALL);
		cities.setBounds(732, HEIGHT, LABEL_WIDTH, LABEL_HEIGHT);
		add(cities);
		

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
