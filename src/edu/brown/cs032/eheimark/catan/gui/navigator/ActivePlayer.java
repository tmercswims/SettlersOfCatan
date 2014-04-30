package edu.brown.cs032.eheimark.catan.gui.navigator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
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
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Background.*;
import java.awt.Insets;

public class ActivePlayer extends JPanel implements Update {

	private static final long serialVersionUID = 1L;
	private JLabel mystats;
	private CatanClient client;
	private final JButton gameManagerButton; // manages a turn, allowing user to either roll die or end turn
	private boolean rollDie; // indicates whether in roll die mode or end turn mode

	public ActivePlayer(CatanClient cc) {
		super();

		this.client = cc;
		this.rollDie = true;
		this.setOpaque(false); // set background to opaque b/c drawing done in GUI class for background

		setPreferredSize(new Dimension(600, 50));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{600, 0};
		gridBagLayout.rowHeights = new int[]{25, 25, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		gameManagerButton = new JButton("Roll Die");
		gameManagerButton.setPreferredSize(Constants.ROLL_BUTTON);
		gameManagerButton.setMaximumSize(Constants.ROLL_BUTTON);
		gameManagerButton.setMinimumSize(Constants.ROLL_BUTTON);
		gameManagerButton.setFont(Constants.MY_FONT_ACTIVEPLAYER);
		gameManagerButton.setForeground(Constants.CATAN_RED);
		gameManagerButton.addActionListener(new TurnListener());

		mystats = new JLabel("MY STATISTICS");
		mystats.setFont(Constants.MY_FONT_ACTIVEPLAYER);
		mystats.setForeground(Constants.CATAN_YELLOW);
		mystats.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_mystats = new GridBagConstraints();
		gbc_mystats.fill = GridBagConstraints.BOTH;
		gbc_mystats.insets = new Insets(0, 0, 5, 0);
		gbc_mystats.gridx = 0;
		gbc_mystats.gridy = 0;
		add(mystats, gbc_mystats);
		GridBagConstraints gbc_gameManagerButton = new GridBagConstraints();
		gbc_gameManagerButton.fill = GridBagConstraints.VERTICAL;
		gbc_gameManagerButton.gridx = 0;
		gbc_gameManagerButton.gridy = 1;
		add(gameManagerButton, gbc_gameManagerButton);

	}

	@Override
	public void ericUpdate() {
		Player[] players = this.client.getPlayers();
		for(Player p : players) {
			if(p.getName().equals(client.getPlayer().getName())) { //TODO Change equality check
				int[] resources = p.getResources();
				String s = "Ore:" + resources[3] + " Wheat:" + resources[0] + " Wool:" + resources[1] + " Lumber:" + resources[4] + " Brick:" + resources[2];
				mystats.setText(s);
				mystats.setForeground(p.getColor());
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

//	@Override
//	public void paintComponent(Graphics g) {
//		Image background = wood;
//		int iw = background.getWidth(this);
//		int ih = background.getHeight(this);
//		if (iw > 0 && ih > 0) {
//			for (int x = 0; x < getWidth(); x += iw) {
//				for (int y = 0; y < getHeight(); y += ih) {
//					System.out.println("DREW A BG TILE");
//					g.drawImage(background, x, y, iw, ih, this);
//				}
//			}
//		}
//	}
}
