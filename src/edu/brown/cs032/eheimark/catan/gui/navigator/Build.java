package edu.brown.cs032.eheimark.catan.gui.navigator;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Constants;
import edu.brown.cs032.sbreslow.catan.gui.board.DrawingPanel;


/**
 * The Class Build is a tabbed panel that helps users to manage building
 * a road, settlement, city, etc.
 */
public class Build extends JPanel {
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

		setLayout(null);
		
		buildSettlementButton = new JButton("Build Settlement");
		buildSettlementButton.addActionListener(new BuildSettlementActionListener());
		buildSettlementButton.setForeground(Constants.CATAN_RED);
		buildSettlementButton.setFont(MY_FONT);
		buildSettlementButton.setBounds(288, 26, 185, 29);
		add(buildSettlementButton);
		
		buildDevCardButton = new JButton("Build Dev. Card");
		buildDevCardButton.addActionListener(new BuildDevCardActionListener());
		buildDevCardButton.setForeground(Constants.CATAN_RED);
		buildDevCardButton.setFont(MY_FONT);
		buildDevCardButton.setBounds(763, 26, 170, 29);
		add(buildDevCardButton);
		
		buildRoadButton = new JButton("Build Road");
		buildRoadButton.addActionListener(new BuildRoadActionListener());
		buildRoadButton.setForeground(Constants.CATAN_RED);
		buildRoadButton.setFont(MY_FONT);
		buildRoadButton.setBounds(73, 26, 148, 29);
		add(buildRoadButton);
		
		buildCityButton = new JButton("Build City");
		buildCityButton.addActionListener(new BuildCityActionListener());
		buildCityButton.setForeground(Constants.CATAN_RED);
		buildCityButton.setFont(MY_FONT);
		buildCityButton.setBounds(548, 26, 148, 29);
		add(buildCityButton);
		
		JLabel roadCostLabel = new JLabel("Cost: 1 Brick, 1 Lumber");
		roadCostLabel.setOpaque(false);
		roadCostLabel.setForeground(Color.WHITE);
		roadCostLabel.setFont(MY_FONT2);
		roadCostLabel.setBounds(88, 53, 118, 16);
		add(roadCostLabel);
		
		JLabel settlementCostLabel = new JLabel("Cost: 1 Brick, 1 Lumber, 1 Wood, 1 Wheat");
		settlementCostLabel.setOpaque(false);
		settlementCostLabel.setForeground(Color.WHITE);
		settlementCostLabel.setFont(MY_FONT2);
		settlementCostLabel.setBounds(278, 53, 205, 16);
		add(settlementCostLabel);
		
		JLabel lblCostOre_1 = new JLabel("Cost: 3 Ore, 2 Wheat");
		lblCostOre_1.setOpaque(false);
		lblCostOre_1.setForeground(Color.WHITE);
		lblCostOre_1.setFont(MY_FONT2);
		lblCostOre_1.setBounds(570, 53, 104, 16);
		add(lblCostOre_1);
		
		JLabel lblCostOre = new JLabel("Cost: 1 Ore, 1 Wheat, 1 Wool");
		lblCostOre.setOpaque(false);
		lblCostOre.setForeground(Color.WHITE);
		lblCostOre.setFont(MY_FONT2);
		lblCostOre.setBounds(776, 53, 145, 16);
		add(lblCostOre);
		
		JLabel rdVP = new JLabel("+2 VPs If Longest Road");
		rdVP.setOpaque(false);
		rdVP.setForeground(Color.CYAN);
		rdVP.setFont(new Font("Times", Font.ITALIC, 12));
		rdVP.setBounds(90, 71, 115, 16);
		add(rdVP);
		
		JLabel label = new JLabel("+1 VP Per Settlement");
		label.setOpaque(false);
		label.setForeground(Color.CYAN);
		label.setFont(new Font("Times", Font.ITALIC, 12));
		label.setBounds(328, 71, 104, 16);
		add(label);
		
		JLabel label_1 = new JLabel("+2 VPs Per City");
		label_1.setOpaque(false);
		label_1.setForeground(Color.CYAN);
		label_1.setFont(new Font("Times", Font.ITALIC, 12));
		label_1.setBounds(583, 71, 78, 16);
		add(label_1);
		
		JLabel label_2 = new JLabel("+2 VPs If Largest Army");
		label_2.setOpaque(false);
		label_2.setForeground(Color.CYAN);
		label_2.setFont(new Font("Times", Font.ITALIC, 12));
		label_2.setBounds(791, 71, 115, 16);
		add(label_2);
		//update();
	}
	
	//TODO Fix this
	/**
	 * Update.
	 */
	public void update(){
		int[] resources = _client.getPlayer().getResources();
		if(resources[2]<1 || resources[4]<1){
			buildRoadButton.setEnabled(false);
		}
		else{
			buildRoadButton.setEnabled(true);
		}
		if(resources[0]<1 || resources[1]<1 || resources[3]<1){
			buildDevCardButton.setEnabled(false);
		}
		else{
			buildDevCardButton.setEnabled(true);
		}
		if(resources[3]<3 || resources[0]<2){
			buildCityButton.setEnabled(false);
		}
		else{
			buildCityButton.setEnabled(true);
		}
		if(resources[0]<1 || resources[1]<1 || resources[2]<1 || resources[4]<1){
			buildSettlementButton.setEnabled(false);
		}
		else{
			buildSettlementButton.setEnabled(false);
		}
		
		setPreferredSize(Constants.TAB_PANEL_MENU_SIZE);
		setMaximumSize(Constants.TAB_PANEL_MENU_SIZE);
		setMinimumSize(Constants.TAB_PANEL_MENU_SIZE);
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
		}
	};

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(MY_BACKGROUND);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(img, 0, 0, null);
	}
}
