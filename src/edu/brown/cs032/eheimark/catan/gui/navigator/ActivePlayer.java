package edu.brown.cs032.eheimark.catan.gui.navigator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Constants;
import edu.brown.cs032.eheimark.catan.gui.Update;
import edu.brown.cs032.eheimark.catan.launch.screens.JoinSettingsMenu;
import edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Misc;
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
	private JLabel ore, wheat, wool, wood, brick;
	private boolean blinkState;
	private Timer makeItBlink;

	public ActivePlayer(CatanClient cc) {
		super();

		this.client = cc;
		this.rollDie = true;
		this.setOpaque(false); // set background to opaque b/c drawing done in GUI class for background

		//setPreferredSize(new Dimension(600, 50));

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{600, 0};
		gridBagLayout.rowHeights = new int[]{25, 25, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		gameManagerButton = new JButton("Roll Die");
		gameManagerButton.setPreferredSize(Constants.ROLL_BUTTON);
		//gameManagerButton.setMaximumSize(Constants.ROLL_BUTTON);
		//gameManagerButton.setMinimumSize(Constants.ROLL_BUTTON);
		gameManagerButton.setFont(Constants.MY_FONT_ACTIVEPLAYER);
		gameManagerButton.setForeground(Constants.CATAN_RED);
		gameManagerButton.addActionListener(new TurnListener());

		JPanel playerResourcesPanel = new JPanel();
		playerResourcesPanel.setOpaque(false);
		//playerResourcesPanel.setPreferredSize(Constants.PLAYER_STATS);
		playerResourcesPanel.setLayout(new GridLayout(1,10));
		playerResourcesPanel.add(new JLabel(new ImageIcon(Misc.oreToken.getImage().getScaledInstance(
				(int)(Misc.oreToken.getIconWidth()*Constants.TAB_PANEL_MENU_SIZE.getHeight()/5/Misc.oreToken.getIconHeight()),
				(int)(Constants.PLAYER_STATS.getHeight()),Image.SCALE_SMOOTH))));

		ore = new JLabel();
		ore.setOpaque(true);
		ore.setFont(Constants.DEFAULT_LABEL_FONT);
		ore.setBackground(Color.LIGHT_GRAY);
		ore.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		ore.setHorizontalAlignment(SwingConstants.CENTER);
		playerResourcesPanel.add(ore);

		playerResourcesPanel.add(new JLabel(new ImageIcon(Misc.wheatToken.getImage().getScaledInstance(
				(int)(Misc.wheatToken.getIconWidth()*Constants.TAB_PANEL_MENU_SIZE.getHeight()/5/Misc.wheatToken.getIconHeight()),
				(int)(Constants.PLAYER_STATS.getHeight()),Image.SCALE_SMOOTH))));

		wheat = new JLabel();
		wheat.setOpaque(true);
		wheat.setFont(Constants.DEFAULT_LABEL_FONT);
		wheat.setBackground(Color.LIGHT_GRAY);
		wheat.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		wheat.setHorizontalAlignment(SwingConstants.CENTER);
		playerResourcesPanel.add(wheat);

		playerResourcesPanel.add(new JLabel(new ImageIcon(Misc.woolToken.getImage().getScaledInstance(
				(int)(Misc.woolToken.getIconWidth()*Constants.TAB_PANEL_MENU_SIZE.getHeight()/5/Misc.woolToken.getIconHeight()),
				(int)(Constants.PLAYER_STATS.getHeight()),Image.SCALE_SMOOTH))));

		wool = new JLabel();
		wool.setOpaque(true);
		wool.setFont(Constants.DEFAULT_LABEL_FONT);
		wool.setBackground(Color.LIGHT_GRAY);
		wool.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		wool.setHorizontalAlignment(SwingConstants.CENTER);
		playerResourcesPanel.add(wool);

		playerResourcesPanel.add(new JLabel(new ImageIcon(Misc.woodToken.getImage().getScaledInstance(
				(int)(Misc.woodToken.getIconWidth()*Constants.TAB_PANEL_MENU_SIZE.getHeight()/5/Misc.woodToken.getIconHeight()),
				(int)(Constants.PLAYER_STATS.getHeight()),Image.SCALE_SMOOTH))));

		wood = new JLabel();
		wood.setOpaque(true);
		wood.setFont(Constants.DEFAULT_LABEL_FONT);
		wood.setBackground(Color.LIGHT_GRAY);
		wood.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		wood.setHorizontalAlignment(SwingConstants.CENTER);
		playerResourcesPanel.add(wood);

		playerResourcesPanel.add(new JLabel(new ImageIcon(Misc.brickToken.getImage().getScaledInstance(
				(int)(Misc.brickToken.getIconWidth()*Constants.TAB_PANEL_MENU_SIZE.getHeight()/5/Misc.brickToken.getIconHeight()),
				(int)(Constants.PLAYER_STATS.getHeight()),Image.SCALE_SMOOTH))));

		brick = new JLabel();
		brick.setOpaque(true);
		brick.setFont(Constants.DEFAULT_LABEL_FONT);
		brick.setBackground(Color.LIGHT_GRAY);
		brick.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		brick.setHorizontalAlignment(SwingConstants.CENTER);
		playerResourcesPanel.add(brick);
		mystats = new JLabel("MY STATISTICS");
		mystats.setFont(Constants.MY_FONT_ACTIVEPLAYER);
		mystats.setForeground(Constants.CATAN_YELLOW);
		mystats.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_mystats = new GridBagConstraints();
		gbc_mystats.insets = new Insets(0, 0, 5, 0);
		gbc_mystats.gridx = 0;
		gbc_mystats.gridy = 0;
		add(playerResourcesPanel, gbc_mystats);

		GridBagConstraints gbc_gameManagerButton = new GridBagConstraints();
		gbc_gameManagerButton.fill = GridBagConstraints.VERTICAL;
		gbc_gameManagerButton.gridx = 0;
		gbc_gameManagerButton.gridy = 1;
		gameManagerButton.setEnabled(false);
		add(gameManagerButton, gbc_gameManagerButton);

		makeItBlink = new Timer(1000, // 1000 milliseconds
				new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				blinkState = !blinkState;
				if (blinkState) {
					setBlinkTextColor();
				}
				else {
					setNormalTextColor();
				}
			}

			private void setNormalTextColor() {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						gameManagerButton.setForeground(Constants.CATAN_RED);
					}
				});
			}

			private void setBlinkTextColor() {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						gameManagerButton.setForeground(Constants.CATAN_WHITE);
					}
				});
			}
		});
		blinkState = false;
	}

	@Override
	public void ericUpdate() {
		Player[] players = this.client.getPlayers();

		//TODO Why is the latter check here?
		if(this.client.getPlayer().isActive() && this.client.getPlayer().getRoadCount()<=13){
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					gameManagerButton.setEnabled(true);
					gameManagerButton.requestFocus();
					makeItBlink.start();
				}
			});
		}
		else{
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					gameManagerButton.setEnabled(false);
					makeItBlink.stop();
					gameManagerButton.setForeground(Constants.CATAN_RED);
				}
			});
		}
		for(Player p : players) {
			if(p.getName().equals(client.getPlayer().getName())) { //TODO Change equality check
				int[] resources = p.getResources();
				wheat.setText(resources[0] + "");
				wheat.setBackground(p.getColor().brighter());
				wool.setText(resources[1] + "");
				wool.setBackground(p.getColor().brighter());
				brick.setText(resources[2] + "");
				brick.setBackground(p.getColor().brighter());
				ore.setText(resources[3] + "");
				ore.setBackground(p.getColor().brighter());
				wood.setText(resources[4] + "");
				wood.setBackground(p.getColor().brighter());
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
	
	@Override 
	public void requestFocus() {
		gameManagerButton.requestFocus();
	}
}
