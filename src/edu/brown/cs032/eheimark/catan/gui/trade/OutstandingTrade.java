package edu.brown.cs032.eheimark.catan.gui.trade;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Constants;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Background.wood;
import edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Misc;
import edu.brown.cs032.tmercuri.catan.logic.ResourceConstants;
import edu.brown.cs032.tmercuri.catan.logic.move.TradeMove;
import java.awt.Graphics;
import java.awt.Image;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;

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

		//setBackground(Constants.CATAN_BLUE);
		//setLayout(null);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JPanel comboPanel = new JPanel();
		comboPanel.setLayout(new GridLayout(1,5));
		comboPanel.add(new JLabel(Misc.brickToken));
		comboPanel.add(new JLabel(Misc.woodToken));
		comboPanel.add(new JLabel(Misc.woolToken));
		comboPanel.add(new JLabel(Misc.wheatToken));
		comboPanel.add(new JLabel(Misc.oreToken));
		
		JPanel labelPanel = new JPanel(new GridLayout(1,5));

		JLabel brickLabel = new JLabel("Brick:" + -resources[ResourceConstants.BRICK]);
		brickLabel.setHorizontalAlignment(SwingConstants.CENTER);
		brickLabel.setFont(Constants.DEFAULT_LABEL_FONT);
		brickLabel.setOpaque(true);
		//brickLabel.setBounds(801, 59, 60, 16);
		brickLabel.setForeground(Color.RED);
		labelPanel.add(brickLabel);

		JLabel lumberLabel = new JLabel("Wood:" + -resources[ResourceConstants.WOOD]);
		lumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lumberLabel.setFont(Constants.DEFAULT_LABEL_FONT);
		lumberLabel.setOpaque(true);
		//lumberLabel.setBounds(610, 59, 77, 16);
		lumberLabel.setForeground(Color.GREEN);
		labelPanel.add(lumberLabel);

		JLabel woolLabel = new JLabel("Sheep:" + -resources[ResourceConstants.SHEEP]);
		woolLabel.setHorizontalAlignment(SwingConstants.CENTER);
		woolLabel.setFont(Constants.DEFAULT_LABEL_FONT);
		woolLabel.setOpaque(true);
		//woolLabel.setBounds(456, 59, 60, 16);
		woolLabel.setForeground(Color.LIGHT_GRAY);
		labelPanel.add(woolLabel);

		JLabel grainLabel = new JLabel("Wheat:" + -resources[ResourceConstants.WHEAT]);
		grainLabel.setHorizontalAlignment(SwingConstants.CENTER);
		grainLabel.setFont(Constants.DEFAULT_LABEL_FONT);
		grainLabel.setOpaque(true);
		//grainLabel.setBounds(270, 59, 65, 16);
		grainLabel.setForeground(Color.ORANGE);
		labelPanel.add(grainLabel);

		JLabel oreLabel = new JLabel("Ore:" + -resources[ResourceConstants.ORE]);
		oreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		oreLabel.setFont(Constants.DEFAULT_LABEL_FONT);
		oreLabel.setOpaque(true);
		//oreLabel.setBounds(123, 59, 45, 16);
		oreLabel.setForeground(Color.DARK_GRAY);
		labelPanel.add(oreLabel);

		JPanel bp = new JPanel();
		bp.setLayout(new BoxLayout(bp, BoxLayout.LINE_AXIS));
		JButton acceptButton = new JButton("Accept Trade");
		acceptButton.setForeground(Constants.CATAN_RED);
		acceptButton.setFont(Constants.DEFAULT_LABEL_FONT);
		//acceptButton.setBounds(374, 99, 125, 29);
		acceptButton.addActionListener(new AcceptTradeActionListener());
		acceptButton.setFocusable(false);
		bp.add(acceptButton);

		JButton rejectTrade = new JButton("Reject Trade");
		rejectTrade.setForeground(Constants.CATAN_RED);
		rejectTrade.setFont(Constants.DEFAULT_LABEL_FONT);
		rejectTrade.addActionListener(new RejectTradeActionListener());
		rejectTrade.setFocusable(false);
		//rejectTrade.setBounds(499, 99, 122, 29);
		bp.add(rejectTrade);

		JPanel lp = new JPanel();
		lp.setLayout(new BoxLayout(lp, BoxLayout.LINE_AXIS));
		
		JLabel fromLabel = new JLabel("From: " + trade.getPlayerName());
		fromLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
		fromLabel.setFont(Constants.DEFAULT_LABEL_FONT);
		fromLabel.setOpaque(true);
		fromLabel.setForeground(Color.BLACK);
		//fromLabel.setBounds(28, 15, 150, 16);
		lp.add(fromLabel);

		JLabel clarificationLabel = new JLabel("+ = RECEIVE, - = SEND");
		clarificationLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
		clarificationLabel.setOpaque(true);
		clarificationLabel.setForeground(Color.BLACK);
		clarificationLabel.setFont(new Font("Times", Font.ITALIC, 12));
		//clarificationLabel.setBounds(28, 105, 170, 16);
//		lp.add(clarificationLabel);
		

		//setMaximumSize(Constants.TAB_PANEL_MENU_SIZE);
		//setMinimumSize(Constants.TAB_PANEL_MENU_SIZE);
		//setPreferredSize(Constants.TAB_PANEL_MENU_SIZE);
		this.add(lp);
		this.add(comboPanel);
		labelPanel.setSize(labelPanel.getWidth(), 4);
		this.add(labelPanel);
		clarificationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(clarificationLabel);
		this.add(bp);
	}
    
    @Override
    public void paintComponent(Graphics g) {
        Image background = wood;
        int iw = background.getWidth(this);
        int ih = background.getHeight(this);
        if (iw > 0 && ih > 0) {
            for (int x = 0; x < getWidth(); x += iw) {
                for (int y = 0; y < getHeight(); y += ih) {
                    g.drawImage(background, x, y, iw, ih, this);
                }
            }
        }
        super.paintComponent(g);
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
