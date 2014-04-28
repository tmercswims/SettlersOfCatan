package edu.brown.cs032.eheimark.catan.gui.navigator;

import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Color;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Constants;
import edu.brown.cs032.eheimark.catan.gui.Update;
import edu.brown.cs032.tmercuri.catan.logic.Player;
import edu.brown.cs032.tmercuri.catan.logic.move.FirstMove;
import edu.brown.cs032.tmercuri.catan.logic.move.LastMove;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.SwingConstants;
//import java.awt.Image;

/**
 * The Class Overview is the overview tabbed panel which contains
 * basic game info like number of victory points, user names, number of cards
 * per player, etc.
 */
public class Overview extends JPanel implements Update {
	private static final long serialVersionUID = 1L;
	// TODO Fix background images
	private static final Color MY_BACKGROUND = Constants.CATAN_WHITE;
	private final CatanClient client;
	private final ArrayList<PlayerStats> playerstats;
	private final JLabel myResources;
	private final JButton gameManagerButton; // manages a turn, allowing user to either roll die or end turn
	private boolean rollDie; // indicates whether in roll die mode or end turn mode

	/**
	 * Instantiates a new overview panel.
	 *
	 * @param cc the cc
	 */
	public Overview(CatanClient cc) {
		super();
		setBackground(MY_BACKGROUND);
		this.client = cc;

		setLayout(null);

		PlayerStats psLabels = new PlayerStats();
		psLabels.setBounds(44, 0, PlayerStats.MY_SIZE.width, PlayerStats.MY_SIZE.height);
		add(psLabels); // just contains labels for player stats

		playerstats = new ArrayList<PlayerStats>();
		PlayerStats ps1 = new PlayerStats();
		ps1.setBounds(44, 20, PlayerStats.MY_SIZE.width, PlayerStats.MY_SIZE.height);
		PlayerStats ps2 = new PlayerStats();
		ps2.setBounds(44, 40, PlayerStats.MY_SIZE.width, PlayerStats.MY_SIZE.height);
		PlayerStats ps3 = new PlayerStats();
		ps3.setBounds(44, 60, PlayerStats.MY_SIZE.width, PlayerStats.MY_SIZE.height);

		add(ps1);
		playerstats.add(ps1);
		add(ps2);
		playerstats.add(ps2);
		add(ps3);
		playerstats.add(ps3);

		this.rollDie = true;

		setPreferredSize(Constants.TAB_PANEL_MENU_SIZE);
		setMaximumSize(Constants.TAB_PANEL_MENU_SIZE);
		setMinimumSize(Constants.TAB_PANEL_MENU_SIZE);

		myResources = new JLabel();
		myResources.setBounds(336, 81, 385, 28);
		add(myResources);
		myResources.setHorizontalAlignment(SwingConstants.RIGHT);
		myResources.setText("Player resources:");
		myResources.setFont(Constants.MY_FONT_SMALL);
		myResources.setOpaque(false);

		gameManagerButton = new JButton("Roll Die");
		gameManagerButton.setBounds(852, 82, 117, 29);
		add(gameManagerButton);
		gameManagerButton.setFont(Constants.MY_FONT_SMALL);
		gameManagerButton.setBackground(Constants.CATAN_RED);
		gameManagerButton.setForeground(Constants.CATAN_YELLOW);
		gameManagerButton.addActionListener(new TurnListener());
	}

	// TODO Remove this, cut down number of repaints
	private int i;
	@Override
	public void paintComponent(Graphics g) {
		System.out.println("Repainting " + i + "...");
		i++;
		g.setColor(MY_BACKGROUND);
		g.fillRect(0, 0, getWidth(), getHeight());
		System.out.println("Done repainting " + i + "...");
	}

	/**
	 * ActionListener that allows user to roll die or end turn.
	 */
	class TurnListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Trying to roll die...");
			if(client.getPlayer().isActive()) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						try {
							if(rollDie) {
								client.sendMove(new FirstMove(client.getPlayerName()));
								gameManagerButton.setText("End turn");
								rollDie = false;
							}
							else {
								client.sendMove(new LastMove(client.getPlayerName()));
								gameManagerButton.setText("Roll die");
								rollDie = true;
							}
						} catch (IllegalArgumentException | IOException e) {
							e.printStackTrace();
						}
					}
				});
			}
			else {
				System.out.println("Not active player! CANNOT ROLL!");
			}
		}
	}

	@Override
	public void ericUpdate() {
		Player[] players = this.client.getPlayers();
		int i = 0;
		for(Player p : players) {
			PlayerStats ps = playerstats.get(i);
			ps.setColor(p.getColor());
			ps.setSettlements(p.getSettlementsBuilt());
			ps.setCities(p.getCitiesBuilt());
			ps.setRoads(p.getRoadsBuilt());
			ps.setName(p.getName());
			ps.setVPs(p.getVictoryPoints() + "");
			ps.setDevCards(p.getDevCardCount() + "");
			ps.setResources(p.getTotalResources() + "");
			ps.setActivePlayer(false);
			if(p.getName().equals(client.getPlayer().getName())) { //TODO Change equality check
				int[] resources = p.getResources();
				String s = "Ore:" + resources[3] + " Wheat:" + resources[0] + " Wool:" + resources[1] + " Lumber:" + resources[4] + " Brick:" + resources[2];
				myResources.setText(s);
				myResources.setForeground(p.getColor());
			}
			if(p.isActive()) {
				ps.setActivePlayer(true);
			}
			i++;
		}
		repaint();
	}
}
