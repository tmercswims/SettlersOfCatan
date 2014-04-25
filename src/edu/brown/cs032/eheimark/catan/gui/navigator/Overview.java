package edu.brown.cs032.eheimark.catan.gui.navigator;

import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Constants;
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
public class Overview extends JPanel {
	private static final long serialVersionUID = 1L;
	// TODO Fix background images
	//	private final Image img; // background image
	private static final Color MY_BACKGROUND = Constants.CATAN_BLACK;
	private static final Color MY_FOREGROUND = Constants.CATAN_YELLOW;
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
		setForeground(MY_FOREGROUND);
		setBackground(MY_BACKGROUND);
		this.client = cc;
		//		this.img = new ImageIcon(IMG_FILE_LOC).getImage();

		setLayout(null);

		playerstats = new ArrayList<PlayerStats>();

		PlayerStats ps1 = new PlayerStats();
		ps1.setBounds(30, 33, 889, 21);
		PlayerStats ps2 = new PlayerStats();
		ps2.setBounds(30, 58, 889, 21);
		PlayerStats ps3 = new PlayerStats();
		ps3.setBounds(30, 83, 889, 21);
		PlayerStats ps4 = new PlayerStats();
		ps4.setBounds(30, 108, 889, 21);

		add(ps1);
		playerstats.add(ps1);
		add(ps2);
		playerstats.add(ps2);
		add(ps3);
		playerstats.add(ps3);
		add(ps4);
		playerstats.add(ps4);

		myResources = new JLabel();
		myResources.setHorizontalAlignment(SwingConstants.RIGHT);
		myResources.setText("Player resources:");
		myResources.setFont(Constants.MY_FONT_SMALL);
		myResources.setOpaque(false);
		myResources.setBounds(500, 0, 385, 28);
		add(myResources);

		gameManagerButton = new JButton("Roll Die");
		gameManagerButton.setFont(Constants.MY_FONT_SMALL);
		gameManagerButton.setBounds(30, 1, 117, 29);
		add(gameManagerButton);
		gameManagerButton.addActionListener(new TurnListener());

		this.rollDie = true;

		setPreferredSize(Constants.TAB_PANEL_MENU_SIZE);
		setMaximumSize(Constants.TAB_PANEL_MENU_SIZE);
		setMinimumSize(Constants.TAB_PANEL_MENU_SIZE);
	}

	/**
	 * Refresh text.
	 */
	public void refreshText() {
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
			ps.setDevCards(p.getDevCards() + "");
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
	}

	// TODO Remove this, cut down number of repaints
	private int i;
	@Override
	public void paintComponent(Graphics g) {
		System.out.println("Repainting " + i + "...");
		i++;
		refreshText();
		g.setColor(MY_BACKGROUND);
		g.fillRect(0, 0, getWidth(), getHeight());
		System.out.println("Done repainting " + i + "...");
		//		g.drawImage(img, 0, 0, null);
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
	};
}
