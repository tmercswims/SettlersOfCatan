package edu.brown.cs032.eheimark.catan.gui.trade;

import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Background.felt;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.border.BevelBorder;

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
import java.util.HashMap;

/**
 * The Class Trade is the tabbed pane window that allows users to propose a trade to another
 * player at the bottom of the main GUI interface.
 */
public class Trade extends JPanel implements Update {
	private static final long serialVersionUID = 1L;
	private final JComboBox<Integer> oreCB, wheatCB, woolCB, lumberCB, brickCB;
	private JComboBox<String> toPlayerCB;
	private final CatanClient client;
	private JButton proposeButton;

	/**
	 * Instantiates a new trade.
	 *
	 * @param cc the cc
	 */
	public Trade(CatanClient cc) {
		super();

		final Integer[] tradeValues = new Integer[] {-5, -4, -3, -2, -1, +0, 1, 2, 3, 4, 5};
		this.client = cc;

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));//GridLayout(3,1));

		toPlayerCB = new JComboBox<String>();
		
		setPreferredSize(Constants.TAB_PANEL_MENU_SIZE);

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
		comboPanel.add(oreCB);

		wheatCB = new JComboBox<Integer>(tradeValues);
		wheatCB.setSelectedIndex(5);
		wheatCB.setToolTipText("Wheat");
		comboPanel.add(wheatCB);

		woolCB = new JComboBox<Integer>(tradeValues);
		woolCB.setSelectedIndex(5);
		woolCB.setToolTipText("Wool");
		comboPanel.add(woolCB);

		lumberCB = new JComboBox<Integer>(tradeValues);
		lumberCB.setSelectedIndex(5);
		lumberCB.setToolTipText("Lumber");
		comboPanel.add(lumberCB);

		brickCB = new JComboBox<Integer>(tradeValues);
		brickCB.setSelectedIndex(5);
		brickCB.setToolTipText("Brick");
		comboPanel.add(brickCB);

		proposeButton = new JButton("Send");
		proposeButton.setFont( Constants.DEFAULT_LABEL_FONT);
		proposeButton.addActionListener(new ProposeTradeActionListener());

		JLabel toLabel = new JLabel("To:");
		toLabel.setFont( Constants.DEFAULT_LABEL_FONT);
		toLabel.setOpaque(false);
		toLabel.setForeground(Color.white);

		JPanel toPanel = new JPanel();
		toPanel.setLayout(new BoxLayout(toPanel, BoxLayout.LINE_AXIS));
		toPanel.add(toLabel);
		toPanel.add(toPlayerCB);

		JLabel clarificationLabel = new JLabel("+ = RECEIVE, - = SEND");
		clarificationLabel.setOpaque(false);
		clarificationLabel.setForeground(Color.white);
		clarificationLabel.setFont(new Font("Times", Font.ITALIC, 12));

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
				int count = 0;
				for(int i: trade){
					if(i==0){
						count++;
					}
				}
				if(count!=5){
					client.sendMove(new TradeMove(client.getPlayerName(), toPlayer, trade, -1));
					oreCB.setSelectedItem(0);
					lumberCB.setSelectedItem(0);
					wheatCB.setSelectedItem(0);
					brickCB.setSelectedItem(0);
					woolCB.setSelectedItem(0);
				}
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

			HashMap<Integer, Color> colors = new HashMap<Integer, Color>();
			int idx = 0;

			toPlayerCB.addItem("***MERCHANT***");
			colors.put(idx++, Color.WHITE);
			
			for(Player p : players) { // Adds all other players except current player to trade with
				if(!client.getPlayerName().equals(p.getName())) {
					toPlayerCB.addItem(p.getName());
					colors.put(idx++, p.getColor().brighter());
				}
			}
			toPlayerCB.setRenderer(new MyComboBoxRenderer(colors));
		}
		toPlayerCB.setSelectedItem("***MERCHANT***");
		toPlayerCB.setBackground(Color.white);

		if((!client.getPlayer().isActive())||(!client.getPlayer().hasRolled())){
			proposeButton.setEnabled(false);
		}
		else{
			proposeButton.setEnabled(true);
		}
		updated = true;
	}

	class MyComboBoxRenderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = 1L;
		HashMap<Integer,Color> table;

		public MyComboBoxRenderer(HashMap<Integer,Color> table) {
			this.table = table;
			setOpaque(true);
		}

		@SuppressWarnings("rawtypes")
		public Component getListCellRendererComponent(JList jc,Object val,int idx,boolean isSelected,boolean cellHasFocus) {
			setText(val.toString());

			if(isSelected) {
				setBackground(Color.LIGHT_GRAY);
				toPlayerCB.setBackground(table.get(idx));
			}
			else {
				setBackground(table.get(idx));
			}
			setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			return this;
		}
	}
}
