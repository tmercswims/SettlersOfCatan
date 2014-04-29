package edu.brown.cs032.eheimark.catan.gui.trade;

import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Background.felt;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Constants;
import edu.brown.cs032.eheimark.catan.gui.Update;
import edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Misc;
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
	private final int resourceHeight;
	private final int sendHeight;
	private JButton proposeButton;

	/**
	 * Instantiates a new trade.
	 *
	 * @param cc the cc
	 */
	public Trade(CatanClient cc) {
		super();
		
		this.sendHeight = 15;
		this.resourceHeight = 50; 
		
		final Integer[] tradeValues = new Integer[] {-5, -4, -3, -2, -1, +0, 1, 2, 3, 4, 5};
		this.client = cc;

		this.img = Constants.TRADE_TAB_IMAGE;
		//setLayout(null); // absolute layout
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));//GridLayout(3,1));

		toPlayerCB = new JComboBox<String>();
		toPlayerCB.setOpaque(false);
		//toPlayerCB.setBackground(Color.white);
		//toPlayerCB.setBounds(53, sendHeight, 140, 16);
		//toPlayerCB.setBorder(BorderFactory.createLineBorder(Color.black));
		//add(toPlayerCB);

		/*oreCB = new JComboBox<Integer>(tradeValues);
		oreCB.setSelectedIndex(5);
		oreCB.setOpaque(true);
		oreCB.setBackground(Color.white);
		oreCB.setBounds(155, resourceHeight, 67, 16);
		oreCB.setBorder(BorderFactory.createLineBorder(Color.black));
		add(oreCB);

		wheatCB = new JComboBox<Integer>(tradeValues);
		wheatCB.setSelectedIndex(5);
		wheatCB.setOpaque(true);
		wheatCB.setBackground(Color.white);
		wheatCB.setBounds(328, resourceHeight, 67, 16);
		wheatCB.setBorder(BorderFactory.createLineBorder(Color.black));
		add(wheatCB);

		woolCB = new JComboBox<Integer>(tradeValues);
		woolCB.setSelectedIndex(5);
		woolCB.setOpaque(true);
		woolCB.setBackground(Color.white);
		woolCB.setBounds(501, resourceHeight, 67, 16);
		woolCB.setBorder(BorderFactory.createLineBorder(Color.black));
		add(woolCB);

		lumberCB = new JComboBox<Integer>(tradeValues);
		lumberCB.setSelectedIndex(5);
		lumberCB.setOpaque(true);
		lumberCB.setBackground(Color.white);
		lumberCB.setBounds(674, resourceHeight, 67, 16);
		lumberCB.setBorder(BorderFactory.createLineBorder(Color.black));
		add(lumberCB);

		brickCB = new JComboBox<Integer>(tradeValues);
		brickCB.setSelectedIndex(5);
		brickCB.setOpaque(true);
		brickCB.setBackground(Color.white);
		brickCB.setBounds(847, resourceHeight, 67, 16);
		brickCB.setBorder(BorderFactory.createLineBorder(Color.black));
		add(brickCB);*/
		
		setPreferredSize(Constants.TAB_PANEL_MENU_SIZE);
		setMaximumSize(Constants.TAB_PANEL_MENU_SIZE);
		setMinimumSize(Constants.TAB_PANEL_MENU_SIZE);
		
		JPanel comboPanel = new JPanel();
		comboPanel.setLayout(new GridLayout(2,5));
		comboPanel.add(new JLabel(new ImageIcon(Misc.oreToken.getImage().getScaledInstance(
				(int)(Misc.oreToken.getIconWidth()*Constants.TAB_PANEL_MENU_SIZE.getHeight()/5/Misc.oreToken.getIconHeight()),
				(int)(Constants.TAB_PANEL_MENU_SIZE.getHeight()/5),Image.SCALE_SMOOTH))));
		comboPanel.add(new JLabel(new ImageIcon(Misc.wheatToken.getImage().getScaledInstance(
				(int)(Misc.wheatToken.getIconWidth()*Constants.TAB_PANEL_MENU_SIZE.getHeight()/5/Misc.wheatToken.getIconHeight()),
				(int)(Constants.TAB_PANEL_MENU_SIZE.getHeight()/5),Image.SCALE_SMOOTH))));
		comboPanel.add(new JLabel(new ImageIcon(Misc.woolToken.getImage().getScaledInstance(
				(int)(Misc.woolToken.getIconWidth()*Constants.TAB_PANEL_MENU_SIZE.getHeight()/5/Misc.woolToken.getIconHeight()),
				(int)(Constants.TAB_PANEL_MENU_SIZE.getHeight()/5),Image.SCALE_SMOOTH))));
		comboPanel.add(new JLabel(new ImageIcon(Misc.woodToken.getImage().getScaledInstance(
				(int)(Misc.woodToken.getIconWidth()*Constants.TAB_PANEL_MENU_SIZE.getHeight()/5/Misc.woodToken.getIconHeight()),
				(int)(Constants.TAB_PANEL_MENU_SIZE.getHeight()/5),Image.SCALE_SMOOTH))));
		comboPanel.add(new JLabel(new ImageIcon(Misc.brickToken.getImage().getScaledInstance(
				(int)(Misc.brickToken.getIconWidth()*Constants.TAB_PANEL_MENU_SIZE.getHeight()/5/Misc.brickToken.getIconHeight()),
				(int)(Constants.TAB_PANEL_MENU_SIZE.getHeight()/5),Image.SCALE_SMOOTH))));
		
		oreCB = new JComboBox<Integer>(tradeValues);
		oreCB.setSelectedIndex(5);
		oreCB.setToolTipText("Ore");
		//oreCB.setBounds(155, 59, 67, 16);
		comboPanel.add(oreCB);

		wheatCB = new JComboBox<Integer>(tradeValues);
		wheatCB.setSelectedIndex(5);
		wheatCB.setToolTipText("Wheat");
		//wheatCB.setBounds(328, 59, 67, 16);
		comboPanel.add(wheatCB);

		woolCB = new JComboBox<Integer>(tradeValues);
		woolCB.setSelectedIndex(5);
		//woolCB.setBounds(501, 59, 67, 16);
		woolCB.setToolTipText("Wool");
		comboPanel.add(woolCB);

		lumberCB = new JComboBox<Integer>(tradeValues);
		lumberCB.setSelectedIndex(5);
		//lumberCB.setBounds(674, 59, 67, 16);
		lumberCB.setToolTipText("Lumber");
		comboPanel.add(lumberCB);

		brickCB = new JComboBox<Integer>(tradeValues);
		brickCB.setSelectedIndex(5);
		brickCB.setToolTipText("Brick");
		//brickCB.setBounds(847, 59, 67, 16);
		comboPanel.add(brickCB);

		/*JLabel brickLabel = new JLabel("Brick:");
		brickLabel.setFont( Constants.DEFAULT_LABEL_FONT);
		brickLabel.setOpaque(true);
		brickLabel.setBackground(Color.WHITE);
		brickLabel.setBounds(801, resourceHeight, 44, 16);
		brickLabel.setForeground(Color.RED);
		brickLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		add(brickLabel);

		JLabel lumberLabel = new JLabel("Lumber:");
		lumberLabel.setFont( Constants.DEFAULT_LABEL_FONT);
		lumberLabel.setOpaque(true);
		lumberLabel.setBackground(Color.WHITE);
		lumberLabel.setBounds(610, resourceHeight, 63, 16);
		lumberLabel.setForeground(Color.GREEN);
		lumberLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		add(lumberLabel);

		JLabel woolLabel = new JLabel("Wool:");
		woolLabel.setFont( Constants.DEFAULT_LABEL_FONT);
		woolLabel.setOpaque(true);
		woolLabel.setBackground(Color.WHITE);
		woolLabel.setBounds(456, resourceHeight, 45, 16);
		woolLabel.setForeground(Color.LIGHT_GRAY);
		woolLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		add(woolLabel);

		JLabel grainLabel = new JLabel("Wheat:");
		grainLabel.setFont( Constants.DEFAULT_LABEL_FONT);
		grainLabel.setOpaque(true);
		grainLabel.setBackground(Color.WHITE);
		grainLabel.setBounds(270, resourceHeight, 55, 16);
		grainLabel.setForeground(Color.ORANGE);
		grainLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		add(grainLabel);

		JLabel oreLabel = new JLabel("Ore:");
		oreLabel.setFont( Constants.DEFAULT_LABEL_FONT);
		oreLabel.setOpaque(true);
		oreLabel.setBackground(Color.WHITE);
		oreLabel.setBounds(123, resourceHeight, 31, 16);
		oreLabel.setForeground(Color.DARK_GRAY);
		oreLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		add(oreLabel);*/

		proposeButton = new JButton("Send");
		proposeButton.setFont( Constants.DEFAULT_LABEL_FONT);
		//proposeButton.setBounds(195, sendHeight, 70, 16);
		proposeButton.addActionListener(new ProposeTradeActionListener());
		//add(proposeButton);

		JLabel toLabel = new JLabel("To:");
		toLabel.setFont( Constants.DEFAULT_LABEL_FONT);
		toLabel.setOpaque(false);
		toLabel.setForeground(Color.white);
		//toLabel.setBackground(Color.WHITE);
		//toLabel.setBounds(28, sendHeight, 138, 16);
		//toLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		//add(toLabel);
		
		JPanel toPanel = new JPanel();
		toPanel.setLayout(new BoxLayout(toPanel, BoxLayout.LINE_AXIS));
		toPanel.add(toLabel);
		toPanel.add(toPlayerCB);

		JLabel clarificationLabel = new JLabel("+ = RECEIVE, - = SEND");
		clarificationLabel.setOpaque(false);
		clarificationLabel.setForeground(Color.white);
		clarificationLabel.setFont(new Font("Times", Font.ITALIC, 12));
		//clarificationLabel.setBackground(Color.WHITE);
		//clarificationLabel.setBounds(28, 90, 136, 16);
		//clarificationLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		//add(clarificationLabel);
		
		JPanel bp = new JPanel();
		bp.setLayout(new BoxLayout(bp, BoxLayout.LINE_AXIS));
		bp.add(proposeButton);
		bp.add(clarificationLabel);
		toPanel.setOpaque(false);
		comboPanel.setOpaque(false);
		bp.setOpaque(false);
		
		this.add(toPanel);
		this.add(comboPanel);
		this.add(bp);
		//bp.setAlignmentX(Component.CENTER_ALIGNMENT);

		
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

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
        Image background = felt;
        int iw = background.getWidth(this);
        int ih = background.getHeight(this);
        if (iw > 0 && ih > 0) {
            for (int x = 0; x < getWidth(); x += iw) {
                for (int y = 0; y < getHeight(); y += ih) {
                    System.out.println("DREW A BG TILE");
                    g.drawImage(background, x, y, iw, ih, this);
                }
            }
        }
	}

	private boolean updated = false;
	@Override
	public void ericUpdate() {
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
		if((!client.getPlayer().isActive())||(!client.getPlayer().hasRolled())){
			proposeButton.setEnabled(false);
		}
		else{
			proposeButton.setEnabled(true);
		}
		updated = true;
	}
}
