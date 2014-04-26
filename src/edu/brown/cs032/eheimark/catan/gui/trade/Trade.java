package edu.brown.cs032.eheimark.catan.gui.trade;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Constants;
import edu.brown.cs032.eheimark.catan.gui.Update;
import edu.brown.cs032.tmercuri.catan.logic.Player;
import edu.brown.cs032.tmercuri.catan.logic.ResourceConstants;
import edu.brown.cs032.tmercuri.catan.logic.move.TradeMove;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * The Class Trade is the tabbed pane window that allows users to propose a trade to another
 * player at the bottom of the main GUI interface.
 */
public class Trade extends JPanel implements Update {
	private static final long serialVersionUID = 1L;
	private final Image img; // background image
	private static final Color MY_BACKGROUND = Constants.CATAN_RED;
	private final JComboBox<Integer> oreCB, wheatCB, woolCB, lumberCB, brickCB;
	private JComboBox<String> toPlayerCB;
	private final CatanClient client;

	/**
	 * Instantiates a new trade.
	 *
	 * @param cc the cc
	 */
	public Trade(CatanClient cc) {
		super();
		final Integer[] tradeValues = new Integer[] {-5, -4, -3, -2, -1, +0, 1, 2, 3, 4, 5};
		this.client = cc;

		this.img = Constants.TRADE_TAB_IMAGE;
		setLayout(null); // absolute layout

		toPlayerCB = new JComboBox<String>();
		toPlayerCB.setBounds(53, 15, 140, 16);
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
		brickLabel.setFont( Constants.DEFAULT_LABEL_FONT);
		brickLabel.setOpaque(true);
		brickLabel.setBackground(Color.WHITE);
		brickLabel.setBounds(801, 59, 44, 16);
		brickLabel.setForeground(Color.RED);
		add(brickLabel);

		JLabel lumberLabel = new JLabel("Lumber:");
		lumberLabel.setFont( Constants.DEFAULT_LABEL_FONT);
		lumberLabel.setOpaque(true);
		lumberLabel.setBackground(Color.WHITE);
		lumberLabel.setBounds(610, 59, 63, 16);
		lumberLabel.setForeground(Color.GREEN);
		add(lumberLabel);

		JLabel woolLabel = new JLabel("Wool:");
		woolLabel.setFont( Constants.DEFAULT_LABEL_FONT);
		woolLabel.setOpaque(true);
		woolLabel.setBackground(Color.WHITE);
		woolLabel.setBounds(456, 59, 45, 16);
		woolLabel.setForeground(Color.LIGHT_GRAY);
		add(woolLabel);

		JLabel grainLabel = new JLabel("Wheat:");
		grainLabel.setFont( Constants.DEFAULT_LABEL_FONT);
		grainLabel.setOpaque(true);
		grainLabel.setBackground(Color.WHITE);
		grainLabel.setBounds(270, 59, 55, 16);
		grainLabel.setForeground(Color.ORANGE);
		add(grainLabel);

		JLabel oreLabel = new JLabel("Ore:");
		oreLabel.setFont( Constants.DEFAULT_LABEL_FONT);
		oreLabel.setOpaque(true);
		oreLabel.setBackground(Color.WHITE);
		oreLabel.setBounds(123, 59, 31, 16);
		oreLabel.setForeground(Color.DARK_GRAY);
		add(oreLabel);

		JButton proposeButton = new JButton("Propose");
		proposeButton.setFont( Constants.DEFAULT_LABEL_FONT);
		proposeButton.setBounds(374, 99, 125, 29);
		proposeButton.addActionListener(new ProposeTradeActionListener());
		add(proposeButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont( Constants.DEFAULT_LABEL_FONT);
		cancelButton.addActionListener(new CancelTradesActionListener());
		cancelButton.setBounds(499, 99, 122, 29);
		add(cancelButton);

		JLabel toLabel = new JLabel("To:");
		toLabel.setFont( Constants.DEFAULT_LABEL_FONT);
		toLabel.setOpaque(true);
		toLabel.setForeground(Color.BLACK);
		toLabel.setBackground(Color.WHITE);
		toLabel.setBounds(28, 15, 138, 16);
		add(toLabel);

		JLabel clarificationLabel = new JLabel("+ = RECEIVE, - = SEND");
		clarificationLabel.setOpaque(true);
		clarificationLabel.setForeground(Color.BLACK);
		clarificationLabel.setFont(new Font("Times", Font.ITALIC, 12));
		clarificationLabel.setBackground(Color.WHITE);
		clarificationLabel.setBounds(28, 105, 136, 16);
		add(clarificationLabel);

		setPreferredSize(Constants.TAB_PANEL_MENU_SIZE);
		setMaximumSize(Constants.TAB_PANEL_MENU_SIZE);
		setMinimumSize(Constants.TAB_PANEL_MENU_SIZE);
	}

	/**
	 * ActionListener to propose a trade to another player.
	 */
	class ProposeTradeActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				int[] trade = new int[5]; // There are five different basic resources
				trade[ResourceConstants.ORE] = (Integer) oreCB.getSelectedItem();
				trade[ResourceConstants.WHEAT] = (Integer) wheatCB.getSelectedItem();
				trade[ResourceConstants.SHEEP] = (Integer) woolCB.getSelectedItem();
				trade[ResourceConstants.WOOD] = (Integer) lumberCB.getSelectedItem();
				trade[ResourceConstants.BRICK] = (Integer) brickCB.getSelectedItem();
				String toPlayer = (String) toPlayerCB.getSelectedItem();
				client.sendMove(new TradeMove(client.getPlayerName(), toPlayer, trade, -1));
			} catch (IllegalArgumentException | IOException e1) {
				e1.printStackTrace();
			}
		}
	};

	/**
	 * ActionListener to cancel any outstanding trades.
	 */
	class CancelTradesActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) { // TODO Add This
			System.out.println("Cancel all outstanding trades");
		}
	};

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(MY_BACKGROUND);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(img, 0, 0, null);
	}

	private boolean updated = false;
	@Override
	public void update() {
		if(!updated) {
			System.out.println("IN TRADE MENU UPDATE");
			Player[] players = client.getPlayers();

			int i = 0;
			toPlayerCB.insertItemAt("***MERCHANT***", i);
			for(Player p : players) { // Adds all other players except current player to trade with
				if(!client.getPlayerName().equals(p.getName())) {
					toPlayerCB.insertItemAt(p.getName(), i++);
				}
			}		

			toPlayerCB.repaint();
			repaint();
			System.out.println("DONE WITH TRADE MENU UPDATE");
		}
		updated = true;
	}
}
