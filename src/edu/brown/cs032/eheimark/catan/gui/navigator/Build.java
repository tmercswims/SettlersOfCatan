package edu.brown.cs032.eheimark.catan.gui.navigator;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Constants;
import edu.brown.cs032.eheimark.catan.gui.Update;
import edu.brown.cs032.sbreslow.catan.gui.board.DrawingPanel;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Background.felt;
import static edu.brown.cs032.tmercuri.catan.logic.BuildConstants.DEV_CARD;
import edu.brown.cs032.tmercuri.catan.logic.move.BuildMove;

import java.io.IOException;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;


/**
 * The Class Build is a tabbed panel that helps users to manage building
 * a road, settlement, city, etc.
 */
public class Build extends JPanel implements Update {
	private static final long serialVersionUID = 1L;
	private final Image img; // background image
	private static final Font MY_FONT = new Font("Georgia", Font.BOLD, 18);
	private static final Font MY_FONT2 = new Font("Times", Font.ITALIC, 12);
	private static final Color MY_BACKGROUND = Constants.CATAN_RED;
	private final CatanClient _client;
	private final DrawingPanel _dp;
	private final JButton buildSettlementButton, buildDevCardButton, buildRoadButton, buildCityButton;
	
	/**
	 * Instantiates a new Build panel.
	 *
	 * @param c the client
	 * @param dp the drawing panel that contains board
	 */
	public Build(CatanClient c, DrawingPanel dp) {
		super();
		_client  = c;
		_dp = dp;
		this.img = Constants.BUILD_IMAGE;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 238, 238, 238, 238, 0};
		gridBagLayout.rowHeights = new int[]{11, 43, 13, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		//		add(buildSettlementButton);
				
				buildDevCardButton = new JButton("Build Dev. Card");
				buildDevCardButton.addActionListener(new BuildDevCardActionListener());
				buildDevCardButton.setForeground(Constants.CATAN_RED);
				buildDevCardButton.setFont(MY_FONT);
				//buildDevCardButton.setBounds(763, 26, 170, 29);
				buildDevCardButton.setEnabled(false);
				GridBagConstraints gbc_buildDevCardButton = new GridBagConstraints();
				gbc_buildDevCardButton.fill = GridBagConstraints.BOTH;
				gbc_buildDevCardButton.insets = new Insets(0, 0, 5, 5);
				gbc_buildDevCardButton.gridx = 1;
				gbc_buildDevCardButton.gridy = 1;
				add(buildDevCardButton, gbc_buildDevCardButton);
		
		buildCityButton = new JButton("Build City");
		buildCityButton.addActionListener(new BuildCityActionListener());
		
		
//		JPanel bp = new JPanel();
//		setOpaque(false);
//		setLayout(new GridLayout(1,4));
		buildSettlementButton = new JButton("Build Settlement");
		buildSettlementButton.addActionListener(new BuildSettlementActionListener());
		//		add(buildDevCardButton);
				
				buildRoadButton = new JButton("Build Road");
				buildRoadButton.addActionListener(new BuildRoadActionListener());
				buildRoadButton.setForeground(Constants.CATAN_RED);
				buildRoadButton.setFont(MY_FONT);
				//buildRoadButton.setBounds(73, 26, 148, 29);
				buildRoadButton.setEnabled(false);
				GridBagConstraints gbc_buildRoadButton = new GridBagConstraints();
				gbc_buildRoadButton.fill = GridBagConstraints.BOTH;
				gbc_buildRoadButton.insets = new Insets(0, 0, 5, 5);
				gbc_buildRoadButton.gridx = 2;
				gbc_buildRoadButton.gridy = 1;
				add(buildRoadButton, gbc_buildRoadButton);
		buildSettlementButton.setForeground(Constants.CATAN_RED);
		buildSettlementButton.setFont(MY_FONT);
		//buildSettlementButton.setBounds(288, 26, 185, 29);
		buildSettlementButton.setEnabled(false);
		GridBagConstraints gbc_buildSettlementButton = new GridBagConstraints();
		gbc_buildSettlementButton.fill = GridBagConstraints.BOTH;
		gbc_buildSettlementButton.insets = new Insets(0, 0, 5, 5);
		gbc_buildSettlementButton.gridx = 3;
		gbc_buildSettlementButton.gridy = 1;
		add(buildSettlementButton, gbc_buildSettlementButton);
		buildCityButton.setForeground(Constants.CATAN_RED);
		buildCityButton.setFont(MY_FONT);
		//buildCityButton.setBounds(548, 26, 148, 29);
		buildCityButton.setEnabled(false);
		GridBagConstraints gbc_buildCityButton = new GridBagConstraints();
		gbc_buildCityButton.fill = GridBagConstraints.BOTH;
		gbc_buildCityButton.insets = new Insets(0, 0, 5, 0);
		gbc_buildCityButton.gridx = 4;
		gbc_buildCityButton.gridy = 1;
		add(buildCityButton, gbc_buildCityButton);
		
		JLabel lblCostOre = new JLabel("Cost: 1 Ore, 1 Wheat, 1 Wool");
		lblCostOre.setHorizontalAlignment(SwingConstants.CENTER);
		lblCostOre.setOpaque(false);
		lblCostOre.setForeground(Color.WHITE);
		lblCostOre.setFont(MY_FONT2);
		lblCostOre.setAlignmentX(Component.CENTER_ALIGNMENT);
		GridBagConstraints gbc_lblCostOre = new GridBagConstraints();
		gbc_lblCostOre.fill = GridBagConstraints.BOTH;
		gbc_lblCostOre.insets = new Insets(0, 0, 5, 5);
		gbc_lblCostOre.gridx = 1;
		gbc_lblCostOre.gridy = 2;
		add(lblCostOre, gbc_lblCostOre);
		
		JLabel roadCostLabel = new JLabel("Cost: 1 Brick, 1 Lumber");
		roadCostLabel.setHorizontalAlignment(SwingConstants.CENTER);
		roadCostLabel.setOpaque(false);
		roadCostLabel.setForeground(Color.WHITE);
		roadCostLabel.setFont(MY_FONT2);
		roadCostLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		GridBagConstraints gbc_roadCostLabel = new GridBagConstraints();
		gbc_roadCostLabel.fill = GridBagConstraints.BOTH;
		gbc_roadCostLabel.insets = new Insets(0, 0, 5, 5);
		gbc_roadCostLabel.gridx = 2;
		gbc_roadCostLabel.gridy = 2;
		add(roadCostLabel, gbc_roadCostLabel);
		
		JLabel settlementCostLabel = new JLabel("Cost: 1 Brick, 1 Lumber, 1 Wool, 1 Wheat");
		settlementCostLabel.setHorizontalAlignment(SwingConstants.CENTER);
		settlementCostLabel.setOpaque(false);
		settlementCostLabel.setForeground(Color.WHITE);
		settlementCostLabel.setFont(MY_FONT2);
		settlementCostLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		GridBagConstraints gbc_settlementCostLabel = new GridBagConstraints();
		gbc_settlementCostLabel.fill = GridBagConstraints.BOTH;
		gbc_settlementCostLabel.insets = new Insets(0, 0, 5, 5);
		gbc_settlementCostLabel.gridx = 3;
		gbc_settlementCostLabel.gridy = 2;
		add(settlementCostLabel, gbc_settlementCostLabel);
		
		JLabel lblCostOre_1 = new JLabel("Cost: 3 Ore, 2 Wheat");
		lblCostOre_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblCostOre_1.setOpaque(false);
		lblCostOre_1.setForeground(Color.WHITE);
		lblCostOre_1.setFont(MY_FONT2);
		lblCostOre_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		GridBagConstraints gbc_lblCostOre_1 = new GridBagConstraints();
		gbc_lblCostOre_1.fill = GridBagConstraints.BOTH;
		gbc_lblCostOre_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblCostOre_1.gridx = 4;
		gbc_lblCostOre_1.gridy = 2;
		add(lblCostOre_1, gbc_lblCostOre_1);
		
		JLabel label_2 = new JLabel("+2 VPs If Largest Army");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setOpaque(false);
		label_2.setForeground(Constants.CATAN_YELLOW);
		label_2.setFont(new Font("Times", Font.ITALIC, 12));
		label_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.fill = GridBagConstraints.BOTH;
		gbc_label_2.insets = new Insets(0, 0, 0, 5);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 3;
		add(label_2, gbc_label_2);
		
		JLabel rdVP = new JLabel("+2 VPs If Longest Road");
		rdVP.setHorizontalAlignment(SwingConstants.CENTER);
		rdVP.setOpaque(false);
		rdVP.setForeground(Constants.CATAN_YELLOW);
		rdVP.setFont(new Font("Times", Font.ITALIC, 12));
		rdVP.setAlignmentX(Component.CENTER_ALIGNMENT);
		GridBagConstraints gbc_rdVP = new GridBagConstraints();
		gbc_rdVP.fill = GridBagConstraints.BOTH;
		gbc_rdVP.insets = new Insets(0, 0, 0, 5);
		gbc_rdVP.gridx = 2;
		gbc_rdVP.gridy = 3;
		add(rdVP, gbc_rdVP);
		
		JLabel label = new JLabel("+1 VP Per Settlement");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setOpaque(false);
		label.setForeground(Constants.CATAN_YELLOW);
		label.setFont(new Font("Times", Font.ITALIC, 12));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.fill = GridBagConstraints.BOTH;
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 3;
		gbc_label.gridy = 3;
		add(label, gbc_label);
				
				JLabel label_1 = new JLabel("+2 VPs Per City");
				label_1.setHorizontalAlignment(SwingConstants.CENTER);
				label_1.setOpaque(false);
				label_1.setForeground(Constants.CATAN_YELLOW);
				label_1.setFont(new Font("Times", Font.ITALIC, 12));
				label_1.setAlignmentX(Component.CENTER_ALIGNMENT);
				GridBagConstraints gbc_label_1 = new GridBagConstraints();
				gbc_label_1.fill = GridBagConstraints.BOTH;
				gbc_label_1.gridx = 4;
				gbc_label_1.gridy = 3;
				add(label_1, gbc_label_1);
		GridBagConstraints gbc_lpt = new GridBagConstraints();
		gbc_lpt.insets = new Insets(0, 0, 5, 0);
		gbc_lpt.gridx = 1;
		gbc_lpt.gridy = 3;
		GridBagConstraints gbc_lpb = new GridBagConstraints();
		gbc_lpb.gridx = 1;
		gbc_lpb.gridy = 4;
		
		setPreferredSize(Constants.TAB_PANEL_MENU_SIZE);
		setMaximumSize(Constants.TAB_PANEL_MENU_SIZE);
		setMinimumSize(Constants.TAB_PANEL_MENU_SIZE);
	}
	
	/**
	 * Update.
	 */
	@Override
	public void ericUpdate(){
		System.out.println(_client);
		System.out.println(_client.getPlayer());
		int[] resources = _client.getPlayer().getResources();
		if((resources[2]<1 || resources[4]<1)||
				(!_client.getPlayer().isActive())||
				(!_client.getPlayer().hasRolled())){
			buildRoadButton.setEnabled(false);
		}
		else{
			buildRoadButton.setEnabled(true);
		}
		if((resources[0]<1 || resources[1]<1 || resources[3]<1)||
				(!_client.getPlayer().isActive())||
				(!_client.getPlayer().hasRolled())){
			buildDevCardButton.setEnabled(false);
		}
		else{
			buildDevCardButton.setEnabled(true);
		}
		if((resources[3]<3 || resources[0]<2)||
				(!_client.getPlayer().isActive())||
				(!_client.getPlayer().hasRolled())){
			buildCityButton.setEnabled(false);
		}
		else{
			buildCityButton.setEnabled(true);
		}
		if((resources[0]<1 || resources[1]<1 || resources[2]<1 || resources[4]<1)||
				(!_client.getPlayer().isActive())||
				(!_client.getPlayer().hasRolled())){
			buildSettlementButton.setEnabled(false);
		}
		else{
			buildSettlementButton.setEnabled(true);
		}

		repaint();
	}
	
	/**
	 * The listener interface for receiving buildRoadAction events.
	 * The class that is interested in processing a buildRoadAction
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addBuildRoadActionListener<code> method. When
	 * the buildRoadAction event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see BuildRoadActionEvent
	 */
	class BuildRoadActionListener implements ActionListener {
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Trying to build road!");
			_dp.setSelect(1);
			//set drawing panel to be clickable
			//drawing panel sends move
		}
	};
	
	/**
	 * ActionListener that is used to help build settlements
	 */
	class BuildSettlementActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Trying to build settlement!");
			_dp.setSelect(2);
		}
	};
	
	/**
	 * ActionListener that is used to build cities.
	 */
	class BuildCityActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Trying to build city!");
			_dp.setSelect(3);
		}
	};
	
	/**
	 * ActionListener that is used to help build devcards
	 */
	class BuildDevCardActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Trying to build devcard!");
            try {
                _client.sendMove(new BuildMove(_client.getPlayerName(), DEV_CARD, 0));
            } catch (IllegalArgumentException | IOException ex) {
                System.err.println("ERROR: " + ex.getMessage());
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
}
