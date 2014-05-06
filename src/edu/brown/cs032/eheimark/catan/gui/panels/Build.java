package edu.brown.cs032.eheimark.catan.gui.panels;

import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Background.felt;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Colors.CATAN_ORANGE;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Colors.CATAN_RED;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Dimensions.TAB_PANEL_MENU_SIZE;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Misc.buildCity;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Misc.buildDevCard;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Misc.buildRoad;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Misc.buildSettlement;
import static edu.brown.cs032.tmercuri.catan.logic.BuildConstants.DEV_CARD;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.ServerUpdate;
import edu.brown.cs032.sbreslow.catan.gui.board.DrawingPanel;
import edu.brown.cs032.tmercuri.catan.logic.move.BuildMove;

/**
 * The Class Build is a tabbed panel that helps users to manage building
 * a road, settlement, city, etc.
 */
public class Build extends JPanel implements ServerUpdate {
	private static final long serialVersionUID = -8118459292620720992L;
	private static final Font MY_FONT = new Font("Georgia", Font.BOLD, 18); // First font used for buttons
	private static final Font MY_FONT2 = new Font("Times", Font.ITALIC, 12); // Second font used for costs
	private final CatanClient _client; // The client
	private final DrawingPanel _dp; // The drawing panel
	private final JButton buildSettlementButton, buildDevCardButton, buildRoadButton, buildCityButton; // Buttons

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
		setLayout(new GridLayout(0, 4, 0, 0));
		final EmptyBorder TOP_BORDER = new EmptyBorder(7, 0, 5, 0);
		final EmptyBorder BOTTOM_LABEL_BORDER = new EmptyBorder(3, 0, 0, 0);

		JPanel devCardPanel = new JPanel();
		devCardPanel.setBorder(TOP_BORDER);
		devCardPanel.setOpaque(false);
		add(devCardPanel);
		devCardPanel.setLayout(new BorderLayout(0, 0));

		buildDevCardButton = new JButton("Build Dev. Card");
		devCardPanel.add(buildDevCardButton, BorderLayout.NORTH);
		buildDevCardButton.addActionListener(new BuildDevCardActionListener());
		buildDevCardButton.setForeground(CATAN_RED);
		buildDevCardButton.setFont(MY_FONT);
		buildDevCardButton.setEnabled(false);

		JLabel devCardCostLabel = new JLabel("Cost:");
		devCardPanel.add(devCardCostLabel, BorderLayout.CENTER);
		devCardCostLabel.setHorizontalAlignment(SwingConstants.CENTER);
		devCardCostLabel.setOpaque(false);
		devCardCostLabel.setForeground(Color.WHITE);
		devCardCostLabel.setFont(MY_FONT2);
		devCardCostLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		devCardCostLabel.setIcon(buildDevCard);
		devCardCostLabel.setHorizontalTextPosition(SwingConstants.LEFT);

		JLabel devCardVPLabel = new JLabel("+2 VPs If Largest Army");
		devCardPanel.add(devCardVPLabel, BorderLayout.SOUTH);
		devCardVPLabel.setHorizontalAlignment(SwingConstants.CENTER);
		devCardVPLabel.setOpaque(false);
		devCardVPLabel.setForeground(CATAN_ORANGE);
		devCardVPLabel.setFont(new Font("Times", Font.ITALIC, 12));
		devCardVPLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel roadPanel = new JPanel();
		roadPanel.setBorder(TOP_BORDER);
		roadPanel.setOpaque(false);
		add(roadPanel);
		roadPanel.setLayout(new BorderLayout(0, 0));

		buildRoadButton = new JButton("Build Road");
		roadPanel.add(buildRoadButton, BorderLayout.NORTH);
		buildRoadButton.addActionListener(new BuildRoadActionListener());
		buildRoadButton.setForeground(CATAN_RED);
		buildRoadButton.setFont(MY_FONT);
		buildRoadButton.setEnabled(false);

		JLabel roadCostLabel = new JLabel("Cost:");
		roadPanel.add(roadCostLabel, BorderLayout.CENTER);
		roadCostLabel.setHorizontalAlignment(SwingConstants.CENTER);
		roadCostLabel.setOpaque(false);
		roadCostLabel.setForeground(Color.WHITE);
		roadCostLabel.setFont(MY_FONT2);
		roadCostLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		roadCostLabel.setIcon(buildRoad);
		roadCostLabel.setHorizontalTextPosition(SwingConstants.LEFT);

		JLabel rdVP = new JLabel("+2 VPs If Longest Road");
		rdVP.setBorder(BOTTOM_LABEL_BORDER);
		roadPanel.add(rdVP, BorderLayout.SOUTH);
		rdVP.setHorizontalAlignment(SwingConstants.CENTER);
		rdVP.setOpaque(false);
		rdVP.setForeground(CATAN_ORANGE);
		rdVP.setFont(new Font("Times", Font.ITALIC, 12));
		rdVP.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel settlementPanel = new JPanel();
		settlementPanel.setBorder(TOP_BORDER);
		settlementPanel.setOpaque(false);
		add(settlementPanel);
		settlementPanel.setLayout(new BorderLayout(0, 0));

		buildSettlementButton = new JButton("Build Settlement");
		settlementPanel.add(buildSettlementButton, BorderLayout.NORTH);
		buildSettlementButton.addActionListener(new BuildSettlementActionListener());
		buildSettlementButton.setForeground(CATAN_RED);
		buildSettlementButton.setFont(MY_FONT);
		buildSettlementButton.setEnabled(false);

		JLabel settlementCostLabel = new JLabel("Cost:");
		settlementCostLabel.setBorder(BOTTOM_LABEL_BORDER);
		settlementPanel.add(settlementCostLabel, BorderLayout.CENTER);
		settlementCostLabel.setHorizontalAlignment(SwingConstants.CENTER);
		settlementCostLabel.setOpaque(false);
		settlementCostLabel.setForeground(Color.WHITE);
		settlementCostLabel.setFont(MY_FONT2);
		settlementCostLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		settlementCostLabel.setIcon(buildSettlement);
		settlementCostLabel.setHorizontalTextPosition(SwingConstants.LEFT);


		JLabel settlementVPLabel = new JLabel("+1 VP Per Settlement");
		settlementVPLabel.setBorder(BOTTOM_LABEL_BORDER);
		settlementPanel.add(settlementVPLabel, BorderLayout.SOUTH);
		settlementVPLabel.setHorizontalAlignment(SwingConstants.CENTER);
		settlementVPLabel.setOpaque(false);
		settlementVPLabel.setForeground(CATAN_ORANGE);
		settlementVPLabel.setFont(new Font("Times", Font.ITALIC, 12));
		settlementVPLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel cityPanel = new JPanel();
		cityPanel.setBorder(TOP_BORDER);
		cityPanel.setOpaque(false);
		add(cityPanel);
		cityPanel.setLayout(new BorderLayout(0, 0));

		buildCityButton = new JButton("Build City");
		cityPanel.add(buildCityButton, BorderLayout.NORTH);
		buildCityButton.addActionListener(new BuildCityActionListener());
		buildCityButton.setForeground(CATAN_RED);
		buildCityButton.setFont(MY_FONT);
		buildCityButton.setEnabled(false);

		JLabel cityCostLabel = new JLabel("Cost:");
		cityPanel.add(cityCostLabel, BorderLayout.CENTER);
		cityCostLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cityCostLabel.setOpaque(false);
		cityCostLabel.setForeground(Color.WHITE);
		cityCostLabel.setFont(MY_FONT2);
		cityCostLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		cityCostLabel.setIcon(buildCity);
		cityCostLabel.setHorizontalTextPosition(SwingConstants.LEFT);

		JLabel cityVPLabel = new JLabel("+2 VPs Per City");
		cityVPLabel.setBorder(BOTTOM_LABEL_BORDER);
		cityPanel.add(cityVPLabel, BorderLayout.SOUTH);
		cityVPLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cityVPLabel.setOpaque(false);
		cityVPLabel.setForeground(CATAN_ORANGE);
		cityVPLabel.setFont(new Font("Times", Font.ITALIC, 12));
		cityVPLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		setPreferredSize(TAB_PANEL_MENU_SIZE);
	}

	/**
	 * Updates GUI with latest info from server.
	 */
	@Override
	public void serverUpdate(){
		int[] resources = _client.getPlayer().getResources();
		if((resources[2]<1 || resources[4]<1)||
				(_client.getPlayer().getRoadCount()==0)||
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
				(_client.getPlayer().getCityCount()==0)||
				(!_client.getPlayer().isActive())||
				(!_client.getPlayer().hasRolled())){
			buildCityButton.setEnabled(false);
		}
		else{
			buildCityButton.setEnabled(true);
		}
		if((resources[0]<1 || resources[1]<1 || resources[2]<1 || resources[4]<1)||
				(_client.getPlayer().getSettlementCount()==0)||
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
	 * ActionListener that is used to help build roads.
	 */
	class BuildRoadActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
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
			_dp.setSelect(2);
		}
	};

	/**
	 * ActionListener that is used to build cities.
	 */
	class BuildCityActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			_dp.setSelect(3);
		}
	};

	/**
	 * ActionListener that is used to help build devcards
	 */
	class BuildDevCardActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				_client.sendMove(new BuildMove(_client.getPlayerName(), DEV_CARD, 0));
			} catch (IllegalArgumentException | IOException ex) { // Should not occur
				ex.printStackTrace();
			}
		}
	};

	/**
	 * Paints.
	 */
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

	/**
	 * Requests focus.
	 */
	@Override 
	public void requestFocus() {
		if(buildDevCardButton.isEnabled())
			buildDevCardButton.requestFocus();
		else if(buildRoadButton.isEnabled())
			buildRoadButton.requestFocus();
		else if(buildSettlementButton.isEnabled())
			buildSettlementButton.requestFocus();
		else if(buildCityButton.isEnabled())
			buildCityButton.requestFocus();
	}
}
