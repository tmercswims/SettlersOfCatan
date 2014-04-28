package edu.brown.cs032.eheimark.catan.gui.navigator;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Constants;
import edu.brown.cs032.eheimark.catan.gui.Update;
import edu.brown.cs032.tmercuri.catan.logic.Player;
import edu.brown.cs032.tmercuri.catan.logic.move.FirstMove;
import edu.brown.cs032.tmercuri.catan.logic.move.LastMove;

public class ActivePlayer extends JPanel implements Update {

	private static final long serialVersionUID = 1L;
	private JLabel mystats;
	private CatanClient client;
	private final JButton gameManagerButton; // manages a turn, allowing user to either roll die or end turn
	private boolean rollDie; // indicates whether in roll die mode or end turn mode

	public ActivePlayer(CatanClient cc) {
		super(new BorderLayout());
		
		this.client = cc;
		this.rollDie = true;

		setBackground(Constants.CATAN_RED);
		
		mystats = new JLabel("MY STATISTICS");
		mystats.setFont(Constants.DEFAULT_LABEL_FONT);
		mystats.setForeground(Constants.CATAN_YELLOW);
		mystats.setHorizontalAlignment(SwingConstants.CENTER);
		add(mystats, BorderLayout.CENTER);
		setMinimumSize(Constants.ACTIVEPLAYER_MENU_SIZE);
		setMaximumSize(Constants.ACTIVEPLAYER_MENU_SIZE);
		setPreferredSize(Constants.ACTIVEPLAYER_MENU_SIZE);
		
		gameManagerButton = new JButton("Roll Die");
		gameManagerButton.setBounds(852, 82, (int)Constants.ROLL_BUTTON.getWidth(), (int)Constants.ROLL_BUTTON.getHeight());
		add(gameManagerButton, BorderLayout.EAST);
		gameManagerButton.setFont(Constants.MY_FONT_SMALL);
		gameManagerButton.setForeground(Constants.CATAN_RED);
		gameManagerButton.addActionListener(new TurnListener());
		gameManagerButton.setSize(Constants.ROLL_BUTTON);
		gameManagerButton.setPreferredSize(Constants.ROLL_BUTTON);
		gameManagerButton.setMinimumSize(Constants.ROLL_BUTTON);
		gameManagerButton.setMaximumSize(Constants.ROLL_BUTTON);
	}

	@Override
	public void ericUpdate() {
		Player[] players = this.client.getPlayers();
		for(Player p : players) {
			if(p.getName().equals(client.getPlayer().getName())) { //TODO Change equality check
				int[] resources = p.getResources();
				String s = "Ore:" + resources[3] + " Wheat:" + resources[0] + " Wool:" + resources[1] + " Lumber:" + resources[4] + " Brick:" + resources[2];
				mystats.setText(s);
			}		
		}
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

}
