package edu.brown.cs032.eheimark.catan.gui.navigator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.brown.cs032.eheimark.catan.gui.Constants;
import javax.swing.SwingConstants;

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
		name.setHorizontalAlignment(SwingConstants.LEFT);
		name.setBounds(30, 2, 120, LABEL_HEIGHT);
		add(name);
		name.setFont(Constants.MY_FONT_SMALL);
		
		vps = new JLabel("Victory Points");
		vps.setHorizontalAlignment(SwingConstants.CENTER);
		vps.setBounds(166, 2, 109, LABEL_HEIGHT);
		add(vps);
		vps.setFont(Constants.MY_FONT_SMALL);
		
		devcards = new JLabel("Development Cards");
		devcards.setHorizontalAlignment(SwingConstants.CENTER);
		devcards.setBounds(459, 2, 139, LABEL_HEIGHT);
		add(devcards);
		devcards.setFont(Constants.MY_FONT_SMALL);
		
		roads = new JLabel("Roads");
		roads.setHorizontalAlignment(SwingConstants.CENTER);
		roads.setBounds(610, 2, 97, LABEL_HEIGHT);
		add(roads);
		roads.setFont(Constants.MY_FONT_SMALL);
		
		settlements = new JLabel("Settlements");
		settlements.setHorizontalAlignment(SwingConstants.CENTER);
		settlements.setBounds(721, 2, 114, LABEL_HEIGHT);
		add(settlements);
		settlements.setFont(Constants.MY_FONT_SMALL);
		
		resources = new JLabel("Resource Cards");
		resources.setHorizontalAlignment(SwingConstants.CENTER);
		resources.setBounds(313, 2, 114, LABEL_HEIGHT);
		resources.setFont(Constants.MY_FONT_SMALL);
		add(resources);
		
		cities = new JLabel("Cities");
		cities.setHorizontalAlignment(SwingConstants.CENTER);
		cities.setFont(Constants.MY_FONT_SMALL);
		cities.setBounds(847, 2, 97, LABEL_HEIGHT);
		add(cities);
		
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
