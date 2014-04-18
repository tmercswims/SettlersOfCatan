package edu.brown.cs032.eheimark.catan.gui.trade;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import edu.brown.cs032.eheimark.catan.gui.Constants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TradePopup extends JPanel {
	private static final long serialVersionUID = 1L;
	private final Image img; // background image
	private final String IMG_FILE_LOC = "images/Cards1000x140.png";
	private static final Font MY_FONT = new Font("Georgia", Font.BOLD, 14);
	private static final Color MY_BACKGROUND = Constants.CATAN_RED;
	private static final Color MY_FOREGROUND = Constants.CATAN_YELLOW;
	
	public TradePopup(String playerName, Integer ore, Integer wheat, Integer wool, Integer lumber, Integer brick) {
		super();

		setForeground(MY_FOREGROUND);
		this.img = new ImageIcon(IMG_FILE_LOC).getImage();

		setPreferredSize(Constants.TAB_MENU_SIZE);
		setMaximumSize(Constants.TAB_MENU_SIZE);
		setMinimumSize(Constants.TAB_MENU_SIZE);
		setLayout(null);
		
		JLabel brickLabel = new JLabel("Brick:" + brick);
		brickLabel.setFont(MY_FONT);
		brickLabel.setOpaque(true);
		brickLabel.setBackground(Color.WHITE);
		brickLabel.setBounds(801, 59, 60, 16);
		brickLabel.setForeground(Color.RED);
		add(brickLabel);
		
		JLabel lumberLabel = new JLabel("Lumber:" + lumber);
		lumberLabel.setFont(MY_FONT);
		lumberLabel.setOpaque(true);
		lumberLabel.setBackground(Color.WHITE);
		lumberLabel.setBounds(610, 59, 77, 16);
		lumberLabel.setForeground(Color.GREEN);
		add(lumberLabel);
		
		JLabel woolLabel = new JLabel("Wool:" + wool);
		woolLabel.setFont(MY_FONT);
		woolLabel.setOpaque(true);
		woolLabel.setBackground(Color.WHITE);
		woolLabel.setBounds(456, 59, 60, 16);
		woolLabel.setForeground(Color.LIGHT_GRAY);
		add(woolLabel);
		
		JLabel grainLabel = new JLabel("Wheat:" + wheat);
		grainLabel.setFont(MY_FONT);
		grainLabel.setOpaque(true);
		grainLabel.setBackground(Color.WHITE);
		grainLabel.setBounds(270, 59, 65, 16);
		grainLabel.setForeground(Color.ORANGE);
		add(grainLabel);
		
		JLabel oreLabel = new JLabel("Ore:" + ore);
		oreLabel.setFont(MY_FONT);
		oreLabel.setOpaque(true);
		oreLabel.setBackground(Color.WHITE);
		oreLabel.setBounds(123, 59, 45, 16);
		oreLabel.setForeground(Color.DARK_GRAY);
		add(oreLabel);
		
		JButton acceptButton = new JButton("Accept Trade");
		acceptButton.setFont(MY_FONT);
		acceptButton.setBounds(374, 99, 125, 29);
		acceptButton.addActionListener(new AcceptTradeActionListener());
		add(acceptButton);
		
		JButton rejectTrade = new JButton("Reject Trade");
		rejectTrade.setFont(MY_FONT);
		rejectTrade.addActionListener(new RejectTradeActionListener());
		rejectTrade.setBounds(499, 99, 122, 29);
		add(rejectTrade);
		
		JLabel fromLabel = new JLabel("From: " + playerName);
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
		}
	};
	
	
	class RejectTradeActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Reject trade!");
		}
	};
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(MY_BACKGROUND);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(img, 0, 0, null);
	}
	
}
