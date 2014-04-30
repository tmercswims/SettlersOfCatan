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
import java.awt.FlowLayout;
import javax.swing.border.EmptyBorder;


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
		setLayout(new GridLayout(0, 4, 0, 0));

		JPanel devCardPanel = new JPanel();
		devCardPanel.setBorder(new EmptyBorder(25, 0, 25, 0));
		devCardPanel.setOpaque(false);
		add(devCardPanel);
		devCardPanel.setLayout(new BorderLayout(0, 0));

		buildDevCardButton = new JButton("Build Dev. Card");
		devCardPanel.add(buildDevCardButton, BorderLayout.NORTH);
		buildDevCardButton.addActionListener(new BuildDevCardActionListener());
		buildDevCardButton.setForeground(Constants.CATAN_RED);
		buildDevCardButton.setFont(MY_FONT);
		buildDevCardButton.setEnabled(false);

		JLabel oreCostLabel = new JLabel("Cost: 1 Ore, 1 Wheat, 1 Wool");
		devCardPanel.add(oreCostLabel, BorderLayout.CENTER);
		oreCostLabel.setHorizontalAlignment(SwingConstants.CENTER);
		oreCostLabel.setOpaque(false);
		oreCostLabel.setForeground(Color.WHITE);
		oreCostLabel.setFont(MY_FONT2);
		oreCostLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel devCardCostLabel = new JLabel("+2 VPs If Largest Army");
		devCardPanel.add(devCardCostLabel, BorderLayout.SOUTH);
		devCardCostLabel.setHorizontalAlignment(SwingConstants.CENTER);
		devCardCostLabel.setOpaque(false);
		devCardCostLabel.setForeground(Constants.CATAN_YELLOW);
		devCardCostLabel.setFont(new Font("Times", Font.ITALIC, 12));
		devCardCostLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel roadPanel = new JPanel();
		roadPanel.setBorder(new EmptyBorder(25, 0, 25, 0));
		roadPanel.setOpaque(false);
		add(roadPanel);
		roadPanel.setLayout(new BorderLayout(0, 0));

		buildRoadButton = new JButton("Build Road");
		roadPanel.add(buildRoadButton, BorderLayout.NORTH);
		buildRoadButton.addActionListener(new BuildRoadActionListener());
		buildRoadButton.setForeground(Constants.CATAN_RED);
		buildRoadButton.setFont(MY_FONT);
		buildRoadButton.setEnabled(false);

		JLabel roadCostLabel = new JLabel("Cost: 1 Brick, 1 Lumber");
		roadPanel.add(roadCostLabel, BorderLayout.CENTER);
		roadCostLabel.setHorizontalAlignment(SwingConstants.CENTER);
		roadCostLabel.setOpaque(false);
		roadCostLabel.setForeground(Color.WHITE);
		roadCostLabel.setFont(MY_FONT2);
		roadCostLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel rdVP = new JLabel("+2 VPs If Longest Road");
		roadPanel.add(rdVP, BorderLayout.SOUTH);
		rdVP.setHorizontalAlignment(SwingConstants.CENTER);
		rdVP.setOpaque(false);
		rdVP.setForeground(Constants.CATAN_YELLOW);
		rdVP.setFont(new Font("Times", Font.ITALIC, 12));
		rdVP.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel settlementPanel = new JPanel();
		settlementPanel.setBorder(new EmptyBorder(25, 0, 25, 0));
		settlementPanel.setOpaque(false);
		add(settlementPanel);
		settlementPanel.setLayout(new BorderLayout(0, 0));

		buildSettlementButton = new JButton("Build Settlement");
		settlementPanel.add(buildSettlementButton, BorderLayout.NORTH);
		buildSettlementButton.addActionListener(new BuildSettlementActionListener());
		buildSettlementButton.setForeground(Constants.CATAN_RED);
		buildSettlementButton.setFont(MY_FONT);
		buildSettlementButton.setEnabled(false);

		JLabel settlementCostLabel = new JLabel("Cost: 1 Brick, 1 Lumber, 1 Wool, 1 Wheat");
		settlementPanel.add(settlementCostLabel, BorderLayout.CENTER);
		settlementCostLabel.setHorizontalAlignment(SwingConstants.CENTER);
		settlementCostLabel.setOpaque(false);
		settlementCostLabel.setForeground(Color.WHITE);
		settlementCostLabel.setFont(MY_FONT2);
		settlementCostLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel label = new JLabel("+1 VP Per Settlement");
		settlementPanel.add(label, BorderLayout.SOUTH);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setOpaque(false);
		label.setForeground(Constants.CATAN_YELLOW);
		label.setFont(new Font("Times", Font.ITALIC, 12));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel cityPanel = new JPanel();
		cityPanel.setBorder(new EmptyBorder(25, 0, 25, 0));
		cityPanel.setOpaque(false);
		add(cityPanel);
		cityPanel.setLayout(new BorderLayout(0, 0));

		buildCityButton = new JButton("Build City");
		cityPanel.add(buildCityButton, BorderLayout.NORTH);
		buildCityButton.addActionListener(new BuildCityActionListener());
		buildCityButton.setForeground(Constants.CATAN_RED);
		buildCityButton.setFont(MY_FONT);
		buildCityButton.setEnabled(false);

		JLabel lblCostOre_1 = new JLabel("Cost: 3 Ore, 2 Wheat");
		cityPanel.add(lblCostOre_1, BorderLayout.CENTER);
		lblCostOre_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblCostOre_1.setOpaque(false);
		lblCostOre_1.setForeground(Color.WHITE);
		lblCostOre_1.setFont(MY_FONT2);
		lblCostOre_1.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel label_1 = new JLabel("+2 VPs Per City");
		cityPanel.add(label_1, BorderLayout.SOUTH);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setOpaque(false);
		label_1.setForeground(Constants.CATAN_YELLOW);
		label_1.setFont(new Font("Times", Font.ITALIC, 12));
		label_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		setPreferredSize(Constants.TAB_PANEL_MENU_SIZE);
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
