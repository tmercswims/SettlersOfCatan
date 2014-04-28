package edu.brown.cs032.sbreslow.catan.gui.devCards;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Constants;
import edu.brown.cs032.tmercuri.catan.logic.Player;
import edu.brown.cs032.tmercuri.catan.logic.ResourceConstants;
import edu.brown.cs032.tmercuri.catan.logic.move.TradeMove;

public class SevenFrame extends JFrame {
	
	private CatanClient _cc;
	private final Image img; // background image
	private static final Color MY_BACKGROUND = Constants.CATAN_RED;
	private final JComboBox<Integer> oreCB, wheatCB, woolCB, lumberCB, brickCB;
	
	public SevenFrame(CatanClient cc){
		super("Please drop half of your resources...");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_cc = cc;
		this.img = Constants.TRADE_TAB_IMAGE;
		setLayout(null); // absolute layout
		final Integer[] tradeValues = new Integer[] {5, 4, 3, 2, 1, 0};
		
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
		proposeButton.addActionListener(new ProposeTradeActionListener(this));
		add(proposeButton);
		
		//setPreferredSize(Constants.TAB_PANEL_MENU_SIZE);
		setBackground(Color.red);
		//setMaximumSize(Constants.TAB_PANEL_MENU_SIZE);
		setMinimumSize(new Dimension(1000,200));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(_cc.getGUI());
		setVisible(true);
		pack();
	}
	
	private class ProposeTradeActionListener implements ActionListener {
		
		private JFrame _frame;
		
		private ProposeTradeActionListener(JFrame frame){
			_frame = frame;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				int[] trade = new int[5]; // There are five different basic resources
				trade[ResourceConstants.ORE] = (Integer) oreCB.getSelectedItem();
				trade[ResourceConstants.WHEAT] = (Integer) wheatCB.getSelectedItem();
				trade[ResourceConstants.SHEEP] = (Integer) woolCB.getSelectedItem();
				trade[ResourceConstants.WOOD] = (Integer) lumberCB.getSelectedItem();
				trade[ResourceConstants.BRICK] = (Integer) brickCB.getSelectedItem();
				int sum = 0;
				for(int i = 0; i < trade.length; i++){
					sum += trade[i];
				}
				if(!(sum < _cc.getPlayer().getResourceCount()/2)){
					boolean good = true;
					int[] resources = _cc.getPlayer().getResources();
					for(int i = 0; i < trade.length; i++){
						if(trade[i]>resources[i]){
							good = false;
							break;
						}
					}
					if(good){
						String toPlayer = (String) _cc.getPlayerName();
						_cc.sendMove(new TradeMove(_cc.getPlayerName(), toPlayer, trade, -1));
						_frame.setVisible(false);
						_frame.dispose();
						_cc.confirmPacket(); //done processing this move
					}
				}
			} catch (IllegalArgumentException | IOException e1) {
				e1.printStackTrace();
			}
		}
	};

}
