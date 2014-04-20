package edu.brown.cs032.eheimark.catan.gui.navigator;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.JTextArea;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Constants;
import edu.brown.cs032.sbreslow.catan.gui.board.Board;
import edu.brown.cs032.tmercuri.catan.logic.Player;

import java.awt.Font;

public class Overview extends JPanel {
	private static final long serialVersionUID = 1L;
	private final Image img; // background image
	private final String IMG_FILE_LOC = "images/overview.png";
	private static final Font MY_FONT = new Font("Georgia", Font.BOLD, 14);
	private static final Color MY_BACKGROUND = Constants.CATAN_RED;
	private static final Color MY_FOREGROUND = Constants.CATAN_YELLOW;
	private final CatanClient client;
	private final JTextArea nameLabel, devCardsTA, resourcesTA, VPsTA, roadsTA, settlementsTA, citiesTA;
	
	public Overview(CatanClient cc) {
		super();
		setForeground(MY_FOREGROUND);
		setBackground(MY_BACKGROUND);
		this.client = cc;
		this.img = new ImageIcon(IMG_FILE_LOC).getImage();

		setPreferredSize(Constants.TAB_PANEL_MENU_SIZE);
		setMaximumSize(Constants.TAB_PANEL_MENU_SIZE);
		setMinimumSize(Constants.TAB_PANEL_MENU_SIZE);
		setLayout(null);
		
		nameLabel = new JTextArea();
		nameLabel.setBounds(6, 60, 165, 70);
		nameLabel.setEditable(false);
		nameLabel.setForeground(MY_FOREGROUND);
		nameLabel.setBackground(MY_BACKGROUND);
		nameLabel.setFont(MY_FONT);
		add(nameLabel);
		
		VPsTA = new JTextArea();
		VPsTA.setBounds(205, 60, 9, 70);
		VPsTA.setEditable(false);
		VPsTA.setBackground(MY_BACKGROUND);
		VPsTA.setForeground(MY_FOREGROUND);
		VPsTA.setFont(MY_FONT);
		add(VPsTA);
		
		devCardsTA = new JTextArea();
		devCardsTA.setBounds(355, 60, 9, 70);
		devCardsTA.setEditable(false);
		devCardsTA.setForeground(MY_FOREGROUND);
		devCardsTA.setFont(MY_FONT);
		devCardsTA.setBackground(MY_BACKGROUND);
		add(devCardsTA);
		
		resourcesTA = new JTextArea();
		resourcesTA.setBounds(512, 60, 16, 70);
		resourcesTA.setEditable(false);
		resourcesTA.setForeground(MY_FOREGROUND);
		resourcesTA.setFont(MY_FONT);
		resourcesTA.setBackground(MY_BACKGROUND);
		add(resourcesTA);
		
		roadsTA = new JTextArea();
		roadsTA.setBounds(662, 60, 18, 70);
		roadsTA.setEditable(false);
		roadsTA.setForeground(MY_FOREGROUND);
		roadsTA.setFont(MY_FONT);
		roadsTA.setBackground(MY_BACKGROUND);
		add(roadsTA);
		
		citiesTA = new JTextArea();
		citiesTA.setBounds(945, 60, 9, 70);
		citiesTA.setEditable(false);
		citiesTA.setForeground(MY_FOREGROUND);
		citiesTA.setFont(MY_FONT);
		citiesTA.setBackground(MY_BACKGROUND);
		add(citiesTA);

		settlementsTA = new JTextArea(); //SETTLEMENTS = 5, cities =4 
		settlementsTA.setBounds(816, 60, 9, 70);
		settlementsTA.setEditable(false);
		settlementsTA.setForeground(MY_FOREGROUND);
		settlementsTA.setFont(MY_FONT);
		settlementsTA.setBackground(MY_BACKGROUND);
		add(settlementsTA);
		
//		refreshText();
	}
	
	public void refreshText() {
		System.out.println("Trying to get players...");
		Player[] players = this.client.getPlayers();
		System.out.println("Got players!");
		StringBuilder names = new StringBuilder(), devCards = new StringBuilder(), roads = new StringBuilder(), settlements = new StringBuilder(), 
					cities = new StringBuilder(), vps = new StringBuilder(), resources = new StringBuilder();
		for(Player p : players) {
			names.append(p.getName() + "\n");
			vps.append(p.getVictoryPoints() + "\n");
			devCards.append(p.getDevCards() + "\n");
			int[] allResources = p.getResources();
			int resourceSum = 0;
			for(int i = 0; i < allResources.length; i ++) {
				resourceSum += allResources[i];
			}
			resources.append(resourceSum + "\n");
			roads.append(p.getRoadCount() + "\n");
			settlements.append(p.getSettlementCount() + "\n");
			cities.append(p.getCityCount() + "\n");
		}
		VPsTA.setText(vps.toString());
		devCardsTA.setText(devCards.toString());
		roadsTA.setText(roads.toString());
		nameLabel.setText(names.toString());
		settlementsTA.setText(settlements.toString());
		citiesTA.setText(cities.toString());
		resourcesTA.setText(resources.toString());
	}
	
	@Override
	public void paintComponent(Graphics g) {
		refreshText();
		g.setColor(MY_BACKGROUND);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(img, 0, 0, null);
	}
}
