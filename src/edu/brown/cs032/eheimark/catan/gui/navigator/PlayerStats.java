package edu.brown.cs032.eheimark.catan.gui.navigator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerStats extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final int LABEL_WIDTH = 80;
	private static final int LABEL_HEIGHT = 15;
	private static final Font MY_FONT = new Font("Georgia", Font.PLAIN, 13);
	private static final Font MY_FONT_BOLD = new Font("Georgia", Font.BOLD, 13);
	public static final Dimension MY_SIZE = new Dimension(850, 20);
	private static final int HEIGHT = 4;
	private final JLabel name, vps, devcards, roads, settlements, resources, cities;

	public PlayerStats() {
		super();
		setLayout(null);
		setOpaque(false);

		setPreferredSize(MY_SIZE);
		setMinimumSize(MY_SIZE);
		setMaximumSize(MY_SIZE);
		
		name = new JLabel("Name");
		name.setBounds(36, HEIGHT, LABEL_WIDTH, LABEL_HEIGHT);
		add(name);
		name.setFont(MY_FONT);
		
		vps = new JLabel("VPs");
		vps.setBounds(152, HEIGHT, LABEL_WIDTH, LABEL_HEIGHT);
		add(vps);
		vps.setFont(MY_FONT);
		
		devcards = new JLabel("devCards");
		devcards.setBounds(384, HEIGHT, LABEL_WIDTH, LABEL_HEIGHT);
		add(devcards);
		devcards.setFont(MY_FONT);
		
		roads = new JLabel("roads");
		roads.setBounds(500, HEIGHT, LABEL_WIDTH, LABEL_HEIGHT);
		add(roads);
		roads.setFont(MY_FONT);
		
		settlements = new JLabel("settlements");
		settlements.setBounds(616, HEIGHT, LABEL_WIDTH, LABEL_HEIGHT);
		add(settlements);
		settlements.setFont(MY_FONT);
		
		resources = new JLabel("resourceCards");
		resources.setBounds(268, HEIGHT, LABEL_WIDTH, LABEL_HEIGHT);
		resources.setFont(MY_FONT);
		add(resources);
		
		cities = new JLabel("cities");
		cities.setFont(MY_FONT);
		cities.setBounds(732, HEIGHT, LABEL_WIDTH, LABEL_HEIGHT);
		add(cities);
		
	}
	
	public void setColor(Color c) {
		name.setForeground(c);
		vps.setForeground(c);
		devcards.setForeground(c);
		roads.setForeground(c);
		resources.setForeground(c);
		settlements.setForeground(c);
		cities.setForeground(c);
	}
	
	public void setCities(String s) {
		cities.setText(s);
	}
	
	public void setSettlements(String s) {
		settlements.setText(s);
	}
	public void setResources(String s) {
		resources.setText(s);
	}
	
	public void setRoads(String s) {
		roads.setText(s);
	}
	
	public void setDevCards(String s) {
		devcards.setText(s);
	}
	
	public void setVPs(String s) {
		vps.setText(s);
	}
	
	public void setName(String s) {
		name.setText(s);
	}
	
	public void setBold() {
		name.setFont(MY_FONT_BOLD);
		vps.setFont(MY_FONT_BOLD);
		devcards.setFont(MY_FONT_BOLD);
		roads.setFont(MY_FONT_BOLD);
		resources.setFont(MY_FONT_BOLD);
		settlements.setFont(MY_FONT_BOLD);
		cities.setFont(MY_FONT_BOLD);
	}
}
