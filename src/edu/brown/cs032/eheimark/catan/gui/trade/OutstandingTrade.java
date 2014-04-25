package edu.brown.cs032.eheimark.catan.gui.trade;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Font;
import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Constants;
import edu.brown.cs032.tmercuri.catan.logic.ResourceConstants;
import edu.brown.cs032.tmercuri.catan.logic.move.TradeMove;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * The Class OutstandingTrade is a JPanel that contains information regarding a 
 * proposed trade from another player. The player can then accept or reject a trade.
 */
public class OutstandingTrade extends JPanel {
	private static final long serialVersionUID = 1L;
	private final TradeMove trade; // Reference to trade move underlying that launched the JPanel
	private final CatanClient client; // Reference to client
	private final TradeFrame frame; // Reference to pain

	/**
	 * Instantiates a new outstanding trade jpanel.
	 *
	 * @param clientIn the client in
	 * @param tradeIn the trade in
	 * @param frameIn the frame in
	 */
	public OutstandingTrade(CatanClient clientIn, TradeMove tradeIn, TradeFrame frameIn) {
		super();
		client = clientIn;
		frame = frameIn;
		trade = tradeIn;
		trade.getPlayerName();
		int[] resources = trade.getResources(); // Reference to resource exchange involved in the trade

		setBackground(Constants.CATAN_BLUE);
		setLayout(null);

		JLabel brickLabel = new JLabel("Brick:" + resources[ResourceConstants.BRICK]);
		brickLabel.setFont(Constants.DEFAULT_BUTTON_FONT);
		brickLabel.setOpaque(true);
		brickLabel.setBackground(Color.WHITE);
		brickLabel.setBounds(801, 59, 60, 16);
		brickLabel.setForeground(Color.RED);
		add(brickLabel);

		JLabel lumberLabel = new JLabel("Lumber:" + resources[ResourceConstants.WOOD]);
		lumberLabel.setFont(Constants.DEFAULT_LABEL_FONT);
		lumberLabel.setOpaque(true);
		lumberLabel.setBackground(Color.WHITE);
		lumberLabel.setBounds(610, 59, 77, 16);
		lumberLabel.setForeground(Color.GREEN);
		add(lumberLabel);

		JLabel woolLabel = new JLabel("Wool:" + + resources[ResourceConstants.SHEEP]);
		woolLabel.setFont(Constants.DEFAULT_LABEL_FONT);
		woolLabel.setOpaque(true);
		woolLabel.setBackground(Color.WHITE);
		woolLabel.setBounds(456, 59, 60, 16);
		woolLabel.setForeground(Color.LIGHT_GRAY);
		add(woolLabel);

		JLabel grainLabel = new JLabel("Wheat:" + resources[ResourceConstants.WHEAT]);
		grainLabel.setFont(Constants.DEFAULT_LABEL_FONT);
		grainLabel.setOpaque(true);
		grainLabel.setBackground(Color.WHITE);
		grainLabel.setBounds(270, 59, 65, 16);
		grainLabel.setForeground(Color.ORANGE);
		add(grainLabel);

		JLabel oreLabel = new JLabel("Ore:" + resources[ResourceConstants.ORE]);
		oreLabel.setFont(Constants.DEFAULT_LABEL_FONT);
		oreLabel.setOpaque(true);
		oreLabel.setBackground(Color.WHITE);
		oreLabel.setBounds(123, 59, 45, 16);
		oreLabel.setForeground(Color.DARK_GRAY);
		add(oreLabel);

		JButton acceptButton = new JButton("Accept Trade");
		acceptButton.setForeground(Constants.CATAN_RED);
		acceptButton.setFont(Constants.DEFAULT_LABEL_FONT);
		acceptButton.setBounds(374, 99, 125, 29);
		acceptButton.addActionListener(new AcceptTradeActionListener());
		add(acceptButton);

		JButton rejectTrade = new JButton("Reject Trade");
		rejectTrade.setForeground(Constants.CATAN_RED);
		rejectTrade.setFont(Constants.DEFAULT_LABEL_FONT);
		rejectTrade.addActionListener(new RejectTradeActionListener());
		rejectTrade.setBounds(499, 99, 122, 29);
		add(rejectTrade);

		JLabel fromLabel = new JLabel("From: " + trade.getPlayerName());
		fromLabel.setFont(Constants.DEFAULT_LABEL_FONT);
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

		setMaximumSize(Constants.TAB_PANEL_MENU_SIZE);
		setMinimumSize(Constants.TAB_PANEL_MENU_SIZE);
		setPreferredSize(Constants.TAB_PANEL_MENU_SIZE);
	}

	/**
	 * The ActionListener for accepting a trade.
	 */
	class AcceptTradeActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					System.out.println("Accept trade!");
					trade.setType(1);
					try {
						client.sendMove(trade);
						frame.close();
					} catch (IllegalArgumentException | IOException e1) {
						e1.printStackTrace();
					}				
				}
			});
		}
	};

	/**
	 * The ActionListener for rejecting a trade.
	 */
	class RejectTradeActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					System.out.println("Reject trade!");
					trade.setType(0);
					try {
						client.sendMove(trade);
						frame.close();
					} catch (IllegalArgumentException | IOException e1) {
						e1.printStackTrace();
					}				
				}
			});
		}
	};
}
