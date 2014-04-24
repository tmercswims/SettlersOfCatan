package edu.brown.cs032.eheimark.catan.gui.trade;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Constants;
import edu.brown.cs032.tmercuri.catan.logic.ResourceConstants;
import edu.brown.cs032.tmercuri.catan.logic.move.TradeMove;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class TradePopup extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final Font MY_FONT = new Font("Georgia", Font.BOLD, 14);
	private final TradeMove trade;
	private final CatanClient client;
	
	public TradePopup(CatanClient clientIn, TradeMove tradeIn) {
		super();
		client = clientIn;
		trade = tradeIn;
		trade.getPlayerName();
		int[] resources = trade.getResources();
		
		setBackground(Color.BLUE);
		
		setMaximumSize(Constants.TAB_PANEL_MENU_SIZE);
		setMinimumSize(Constants.TAB_PANEL_MENU_SIZE);
		setLayout(null);
		
		JLabel brickLabel = new JLabel("Brick:" + resources[ResourceConstants.BRICK]);
		brickLabel.setFont(MY_FONT);
		brickLabel.setOpaque(true);
		brickLabel.setBackground(Color.WHITE);
		brickLabel.setBounds(801, 59, 60, 16);
		brickLabel.setForeground(Color.RED);
		add(brickLabel);
		
		JLabel lumberLabel = new JLabel("Lumber:" + resources[ResourceConstants.WOOD]);
		lumberLabel.setFont(MY_FONT);
		lumberLabel.setOpaque(true);
		lumberLabel.setBackground(Color.WHITE);
		lumberLabel.setBounds(610, 59, 77, 16);
		lumberLabel.setForeground(Color.GREEN);
		add(lumberLabel);
		
		JLabel woolLabel = new JLabel("Wool:" + + resources[ResourceConstants.SHEEP]);
		woolLabel.setFont(MY_FONT);
		woolLabel.setOpaque(true);
		woolLabel.setBackground(Color.WHITE);
		woolLabel.setBounds(456, 59, 60, 16);
		woolLabel.setForeground(Color.LIGHT_GRAY);
		add(woolLabel);
		
		JLabel grainLabel = new JLabel("Wheat:" + resources[ResourceConstants.WHEAT]);
		grainLabel.setFont(MY_FONT);
		grainLabel.setOpaque(true);
		grainLabel.setBackground(Color.WHITE);
		grainLabel.setBounds(270, 59, 65, 16);
		grainLabel.setForeground(Color.ORANGE);
		add(grainLabel);
		
		JLabel oreLabel = new JLabel("Ore:" + resources[ResourceConstants.ORE]);
		oreLabel.setFont(MY_FONT);
		oreLabel.setOpaque(true);
		oreLabel.setBackground(Color.WHITE);
		oreLabel.setBounds(123, 59, 45, 16);
		oreLabel.setForeground(Color.DARK_GRAY);
		add(oreLabel);
		
		JButton acceptButton = new JButton("Accept Trade");
		acceptButton.setForeground(Constants.CATAN_RED);
		acceptButton.setFont(MY_FONT);
		acceptButton.setBounds(374, 99, 125, 29);
		acceptButton.addActionListener(new AcceptTradeActionListener());
		add(acceptButton);
		
		JButton rejectTrade = new JButton("Reject Trade");
		rejectTrade.setForeground(Constants.CATAN_RED);
		rejectTrade.setFont(MY_FONT);
		rejectTrade.addActionListener(new RejectTradeActionListener());
		rejectTrade.setBounds(499, 99, 122, 29);
		add(rejectTrade);
		
		JLabel fromLabel = new JLabel("From: " + trade.getPlayerName());
		fromLabel.setFont(MY_FONT);
		fromLabel.setOpaque(true);
		fromLabel.setForeground(Color.BLACK);
		fromLabel.setBackground(Color.WHITE);
		fromLabel.setBounds(28, 15, 150, 16);
		add(fromLabel);
		
		JLabel clarificationLabel = new JLabel("+ = Incoming, - = Outgoing");
		clarificationLabel.setOpaque(true);
		clarificationLabel.setForeground(Color.BLACK);
		clarificationLabel.setFont(new Font("Times", Font.ITALIC, 12));
		clarificationLabel.setBackground(Color.WHITE);
		clarificationLabel.setBounds(28, 105, 136, 16);
		add(clarificationLabel);
	}
	
	class AcceptTradeActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Accept trade!");
			trade.setType(1);
			try {
				client.sendMove(trade);
			} catch (IllegalArgumentException | IOException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	
	class RejectTradeActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Reject trade!");
			trade.setType(0);
			try {
				client.sendMove(trade);
			} catch (IllegalArgumentException | IOException e1) {
				e1.printStackTrace();
			}
		}
	};
}
