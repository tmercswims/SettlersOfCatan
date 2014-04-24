package edu.brown.cs032.eheimark.catan.gui.trade;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Constants;
import edu.brown.cs032.tmercuri.catan.logic.Player;
import edu.brown.cs032.tmercuri.catan.logic.ResourceConstants;
import edu.brown.cs032.tmercuri.catan.logic.move.TradeMove;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class Trade extends JPanel {
	private static final long serialVersionUID = 1L;
	private final Image img; // background image
	private final String IMG_FILE_LOC = "images/Cards1000x140.png";
	private static final Font MY_FONT = new Font("Georgia", Font.BOLD, 14);
	private static final Color MY_BACKGROUND = Constants.CATAN_RED;
	private static final Color MY_FOREGROUND = Constants.CATAN_YELLOW;
	private final Integer[] tradeValues;
	private final JComboBox<Integer> oreCB, wheatCB, woolCB, lumberCB, brickCB;
	private final JComboBox<String> toPlayerCB;
	private final CatanClient client;

	public Trade(CatanClient cc) {
		super();
		//TODO: Change below
		tradeValues = new Integer[] {-5, -4, -3, -2, -1, +0, 1, 2, 3, 4, 5};
		
		this.client = cc;

		Player[] players = client.getPlayers();
		String[] playerStringArray= new String[players.length-1];
		
		int i = 0;
		for(Player p : players) {
			if(!client.getPlayerName().equals(p.getName())) {
				playerStringArray[i] = p.getName();
				i++;
			}
		}

		setForeground(MY_FOREGROUND);
		this.img = new ImageIcon(IMG_FILE_LOC).getImage();

		setPreferredSize(Constants.TAB_PANEL_MENU_SIZE);
		setMaximumSize(Constants.TAB_PANEL_MENU_SIZE);
		setMinimumSize(Constants.TAB_PANEL_MENU_SIZE);
		setLayout(null);
		
		toPlayerCB = new JComboBox<String>(playerStringArray);
		toPlayerCB.setBounds(53, 15, 117, 16);
		add(toPlayerCB);
		
		oreCB = new JComboBox<Integer>(tradeValues);
		oreCB.setSelectedIndex(5);
		oreCB.setBounds(155, 59, 67, 16);
		add(oreCB);
		
		wheatCB = new JComboBox<Integer>(tradeValues);
		wheatCB.setSelectedIndex(5);
		wheatCB.setBounds(328, 59, 67, 16);
		add(wheatCB);
		
		woolCB = new JComboBox<Integer>(tradeValues);
		woolCB.setSelectedIndex(5);
		woolCB.setBounds(501, 59, 67, 16);
		add(woolCB);
		
		lumberCB = new JComboBox<Integer>(tradeValues);
		lumberCB.setSelectedIndex(5);
		lumberCB.setBounds(674, 59, 67, 16);
		add(lumberCB);
		
		brickCB = new JComboBox<Integer>(tradeValues);
		brickCB.setSelectedIndex(5);
		brickCB.setBounds(847, 59, 67, 16);
		add(brickCB);
		
		JLabel brickLabel = new JLabel("Brick:");
		brickLabel.setFont(MY_FONT);
		brickLabel.setOpaque(true);
		brickLabel.setBackground(Color.WHITE);
		brickLabel.setBounds(801, 59, 44, 16);
		brickLabel.setForeground(Color.RED);
		add(brickLabel);
		
		JLabel lumberLabel = new JLabel("Lumber:");
		lumberLabel.setFont(MY_FONT);
		lumberLabel.setOpaque(true);
		lumberLabel.setBackground(Color.WHITE);
		lumberLabel.setBounds(610, 59, 63, 16);
		lumberLabel.setForeground(Color.GREEN);
		add(lumberLabel);
		
		JLabel woolLabel = new JLabel("Wool:");
		woolLabel.setFont(MY_FONT);
		woolLabel.setOpaque(true);
		woolLabel.setBackground(Color.WHITE);
		woolLabel.setBounds(456, 59, 45, 16);
		woolLabel.setForeground(Color.LIGHT_GRAY);
		add(woolLabel);
		
		JLabel grainLabel = new JLabel("Wheat:");
		grainLabel.setFont(MY_FONT);
		grainLabel.setOpaque(true);
		grainLabel.setBackground(Color.WHITE);
		grainLabel.setBounds(270, 59, 55, 16);
		grainLabel.setForeground(Color.ORANGE);
		add(grainLabel);
		
		JLabel oreLabel = new JLabel("Ore:");
		oreLabel.setFont(MY_FONT);
		oreLabel.setOpaque(true);
		oreLabel.setBackground(Color.WHITE);
		oreLabel.setBounds(123, 59, 31, 16);
		oreLabel.setForeground(Color.DARK_GRAY);
		add(oreLabel);
		
		JButton proposeButton = new JButton("Propose");
		proposeButton.setFont(MY_FONT);
		proposeButton.setBounds(374, 99, 125, 29);
		proposeButton.addActionListener(new ProposeTradeActionListener());
		add(proposeButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont(MY_FONT);
		cancelButton.addActionListener(new CancelTradesActionListener());
		cancelButton.setBounds(499, 99, 122, 29);
		add(cancelButton);
		
		JLabel toLabel = new JLabel("To:");
		toLabel.setFont(MY_FONT);
		toLabel.setOpaque(true);
		toLabel.setForeground(Color.BLACK);
		toLabel.setBackground(Color.WHITE);
		toLabel.setBounds(28, 15, 138, 16);
		add(toLabel);
		
		JLabel clarificationLabel = new JLabel("+ = Incoming, - = Outgoing");
		clarificationLabel.setOpaque(true);
		clarificationLabel.setForeground(Color.BLACK);
		clarificationLabel.setFont(new Font("Times", Font.ITALIC, 12));
		clarificationLabel.setBackground(Color.WHITE);
		clarificationLabel.setBounds(28, 105, 136, 16);
		add(clarificationLabel);
	}
	
	class ProposeTradeActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Integer ore = (Integer) oreCB.getSelectedItem();
			Integer wheat = (Integer) wheatCB.getSelectedItem();
			Integer wool = (Integer) woolCB.getSelectedItem();
			Integer lumber = (Integer) lumberCB.getSelectedItem();
			Integer brick = (Integer) brickCB.getSelectedItem();
			String player = (String) toPlayerCB.getSelectedItem();
			System.out.println(String.format("ProposeTrade to %s: Ore %s Wheat %s Wool %s Lumber %s Brick %s", player, ore, wheat, wool, lumber, brick));
			
//		    public TradeMove(String playerName, String proposingTo, int[] giving, int[] receiving, int type) {

			String toPlayer = (String) toPlayerCB.getSelectedItem();
					
			try { //TODO Clean up this
				int[] trade = new int[5];
				trade[ResourceConstants.ORE] = ore;
				trade[ResourceConstants.WHEAT] = wheat;
				trade[ResourceConstants.SHEEP] = wool;
				trade[ResourceConstants.WOOD] = lumber;
				trade[ResourceConstants.BRICK] = brick;
				client.sendMove(new TradeMove(client.getPlayerName(), toPlayer, trade, -1));
			} catch (IllegalArgumentException | IOException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	class CancelTradesActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Cancel all outstanding trades");
		}
	};
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(MY_BACKGROUND);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(img, 0, 0, null);
	}
}
