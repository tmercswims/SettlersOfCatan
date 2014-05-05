package edu.brown.cs032.sbreslow.catan.gui.devCards;

import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Fonts.DEFAULT_LABEL_FONT;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.GUIFrame;
import edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Misc;
import edu.brown.cs032.tmercuri.catan.logic.ResourceConstants;
import edu.brown.cs032.tmercuri.catan.logic.move.TradeMove;
import javax.swing.JDialog;

public class SevenFrame extends JFrame {
	private static final long serialVersionUID = 2775532151453365472L;
	private CatanClient _cc;
	private final JComboBox<Integer> oreCB, wheatCB, woolCB, lumberCB, brickCB;
	
	public SevenFrame(CatanClient cc, GUIFrame frame){
		//super(frame, "Please drop half of your resources...", true);
		super("Please drop half of your resources...");
		_cc = cc;
		//setLayout(null); // absolute layout
		final Integer[] tradeValues = new Integer[] {10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
		JPanel comboPanel = new JPanel();
		comboPanel.setLayout(new GridLayout(2,5));
		comboPanel.add(new JLabel(Misc.oreToken));
		comboPanel.add(new JLabel(Misc.wheatToken));
		comboPanel.add(new JLabel(Misc.woolToken));
		comboPanel.add(new JLabel(Misc.woodToken));
		comboPanel.add(new JLabel(Misc.brickToken));
		
		oreCB = new JComboBox<Integer>(tradeValues);
		oreCB.setSelectedIndex(10);
		oreCB.setToolTipText("Ore");
		//oreCB.setBounds(155, 59, 67, 16);
		comboPanel.add(oreCB);

		wheatCB = new JComboBox<Integer>(tradeValues);
		wheatCB.setSelectedIndex(10);
		wheatCB.setToolTipText("Wheat");
		//wheatCB.setBounds(328, 59, 67, 16);
		comboPanel.add(wheatCB);

		woolCB = new JComboBox<Integer>(tradeValues);
		woolCB.setSelectedIndex(10);
		//woolCB.setBounds(501, 59, 67, 16);
		woolCB.setToolTipText("Sheep");
		comboPanel.add(woolCB);

		lumberCB = new JComboBox<Integer>(tradeValues);
		lumberCB.setSelectedIndex(10);
		//lumberCB.setBounds(674, 59, 67, 16);
		lumberCB.setToolTipText("Wood");
		comboPanel.add(lumberCB);

		brickCB = new JComboBox<Integer>(tradeValues);
		brickCB.setSelectedIndex(10);
		brickCB.setToolTipText("Brick");
		//brickCB.setBounds(847, 59, 67, 16);
		comboPanel.add(brickCB);

		/*JLabel brickLabel = new JLabel("Brick:");
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
		add(oreLabel);*/

		JButton proposeButton = new JButton("Propose");
		proposeButton.setFont(DEFAULT_LABEL_FONT);
		//proposeButton.setBounds(374, 99, 125, 29);
		proposeButton.addActionListener(new ProposeTradeActionListener(_cc, this));
		proposeButton.setFocusable(false);
		JPanel bp = new JPanel();
		bp.add(proposeButton);
		
		//setPreferredSize(Constants.TAB_PANEL_MENU_SIZE);
		//setBackground(Color.red);
		//setMaximumSize(Constants.TAB_PANEL_MENU_SIZE);
		//setMinimumSize(new Dimension(1000,200));
		JPanel mp = new BackgroundPanel();
		mp.setLayout(new BoxLayout(mp, BoxLayout.PAGE_AXIS));
		comboPanel.setOpaque(false);
		mp.add(comboPanel);
		bp.setOpaque(false);
		mp.add(bp);
		this.add(mp);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		//this.setUndecorated(true);
		setLocationRelativeTo(_cc.getFrame());
		setVisible(true);
		pack();
		setMinimumSize(getSize());
		setMaximumSize(getSize());
	}
	
	private class ProposeTradeActionListener implements ActionListener {
		
		private JFrame _frame;
        private CatanClient _cc;
		
		private ProposeTradeActionListener(CatanClient cc, SevenFrame sevenFrame){
			_frame = sevenFrame;
            _cc = cc;
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
					} else {
                        _cc.getGUI().getChat().addMessage("Server (server): Invalid resources to drop.");
                    }
				}
			} catch (IllegalArgumentException | IOException e1) {
				e1.printStackTrace();
			}
		}
	};

}
