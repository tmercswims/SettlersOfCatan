package edu.brown.cs032.eheimark.catan.gui.misc;

import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Colors.CATAN_ORANGE;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Colors.CATAN_RED;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Colors.CATAN_WHITE;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Dimensions.PLAYER_STATS;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Dimensions.ROLL_BUTTON;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Dimensions.TAB_PANEL_MENU_SIZE;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Fonts.DEFAULT_LABEL_FONT;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Fonts.MY_FONT_ACTIVEPLAYER;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.ServerUpdate;
import edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Edge;
import edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Misc;
import edu.brown.cs032.tmercuri.catan.logic.Player;
import edu.brown.cs032.tmercuri.catan.logic.move.FirstMove;
import edu.brown.cs032.tmercuri.catan.logic.move.LastMove;

/**
 * The Class PlayerStatsAndRollButton is a JPanel that contains the
 * client player's active stats and the roll button to make or end a turn.
 */
public class PlayerStatsAndRollButton extends JPanel implements ServerUpdate {
	private static final long serialVersionUID = -9089787737608391491L;
	private JLabel mystats;
	private CatanClient client;
	private final JButton gameManagerButton; // manages a turn, allowing user to either roll die or end turn
	private boolean rollDieModeOrEndTurnMode; // indicates whether in roll die mode or end turn mode
	private boolean blinkState; // indicates whether or not button should be blinking (button blinks when active)
	private Timer makeItBlink; // Timer used to make button blink
	private JLabel ore, wheat, wool, wood, brick; // Resource labels
	private boolean _enable;

	/**
	 * Instantiates a new active player.
	 *
	 * @param cc the cc
	 */
	public PlayerStatsAndRollButton(CatanClient cc) {
		super();

		this.client = cc;
		this.rollDieModeOrEndTurnMode = true;
		this.setOpaque(false); // set background to opaque b/c drawing done in GUI class for background

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		gameManagerButton = new JButton("Roll Die");
		gameManagerButton.setMinimumSize(ROLL_BUTTON);
		gameManagerButton.setFont(MY_FONT_ACTIVEPLAYER);
		gameManagerButton.setForeground(CATAN_RED);
		gameManagerButton.addActionListener(new TurnListener());
		_enable = true;

		JPanel playerResourcesPanel = new JPanel();
		playerResourcesPanel.setOpaque(false);
		playerResourcesPanel.setLayout(new GridLayout(1,10));

		ore = new JLabel();
		ore.setOpaque(true);
		ore.setFont(DEFAULT_LABEL_FONT);
		ore.setBackground(Color.LIGHT_GRAY);
		ore.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		ore.setHorizontalAlignment(SwingConstants.CENTER);

		wheat = new JLabel();
		wheat.setOpaque(true);
		wheat.setFont(DEFAULT_LABEL_FONT);
		wheat.setBackground(Color.LIGHT_GRAY);
		wheat.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		wheat.setHorizontalAlignment(SwingConstants.CENTER);

		wool = new JLabel();
		wool.setOpaque(true);
		wool.setFont(DEFAULT_LABEL_FONT);
		wool.setBackground(Color.LIGHT_GRAY);
		wool.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		wool.setHorizontalAlignment(SwingConstants.CENTER);

		wood = new JLabel();
		wood.setOpaque(true);
		wood.setFont(DEFAULT_LABEL_FONT);
		wood.setBackground(Color.LIGHT_GRAY);
		wood.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		wood.setHorizontalAlignment(SwingConstants.CENTER);

		brick = new JLabel();
		brick.setOpaque(true);
		brick.setFont(DEFAULT_LABEL_FONT);
		brick.setBackground(Color.LIGHT_GRAY);
		brick.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		brick.setHorizontalAlignment(SwingConstants.CENTER);

		//adding counts
		playerResourcesPanel.add(new JLabel(new ImageIcon(Misc.wheatToken.getImage().getScaledInstance(
				(int)(Misc.wheatToken.getIconWidth()*TAB_PANEL_MENU_SIZE.getHeight()/5/Misc.wheatToken.getIconHeight()),
				(int)(PLAYER_STATS.getHeight()),Image.SCALE_SMOOTH))));
		playerResourcesPanel.add(wheat);
		
		playerResourcesPanel.add(new JLabel(new ImageIcon(Misc.woolToken.getImage().getScaledInstance(
				(int)(Misc.woolToken.getIconWidth()*TAB_PANEL_MENU_SIZE.getHeight()/5/Misc.woolToken.getIconHeight()),
				(int)(PLAYER_STATS.getHeight()),Image.SCALE_SMOOTH))));
		playerResourcesPanel.add(wool);
		
		playerResourcesPanel.add(new JLabel(new ImageIcon(Misc.brickToken.getImage().getScaledInstance(
				(int)(Misc.brickToken.getIconWidth()*TAB_PANEL_MENU_SIZE.getHeight()/5/Misc.brickToken.getIconHeight()),
				(int)(PLAYER_STATS.getHeight()),Image.SCALE_SMOOTH))));
		playerResourcesPanel.add(brick);

		playerResourcesPanel.add(new JLabel(new ImageIcon(Misc.oreToken.getImage().getScaledInstance(
				(int)(Misc.oreToken.getIconWidth()*TAB_PANEL_MENU_SIZE.getHeight()/5/Misc.oreToken.getIconHeight()),
				(int)(PLAYER_STATS.getHeight()),Image.SCALE_SMOOTH))));
		playerResourcesPanel.add(ore);

		playerResourcesPanel.add(new JLabel(new ImageIcon(Misc.woodToken.getImage().getScaledInstance(
				(int)(Misc.woodToken.getIconWidth()*TAB_PANEL_MENU_SIZE.getHeight()/5/Misc.woodToken.getIconHeight()),
				(int)(PLAYER_STATS.getHeight()),Image.SCALE_SMOOTH))));
		playerResourcesPanel.add(wood);

		mystats = new JLabel("MY STATISTICS");
		mystats.setFont(MY_FONT_ACTIVEPLAYER);
		mystats.setForeground(CATAN_ORANGE);
		mystats.setHorizontalAlignment(SwingConstants.CENTER);
		add(playerResourcesPanel);

		gameManagerButton.setEnabled(false);
		gameManagerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(gameManagerButton);

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
						gameManagerButton.setForeground(CATAN_RED);
					}
				});
			}

			private void setBlinkTextColor() {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						gameManagerButton.setForeground(CATAN_WHITE);
					}
				});
			}
		});
		blinkState = false;
	}
	
	public void enableRoll(){
		gameManagerButton.setEnabled(true);
		_enable = true;
		System.out.println("ENABLED ROLL");
	}
	
	public void disableRoll(){
		gameManagerButton.setEnabled(false);
		_enable = false;
		System.out.println("DISABLED ROLL");
	}

	/**
	 * Updates GUI with latest info from server.
	 */
	@Override
	public void serverUpdate() {
		Player[] players = this.client.getPlayers();

		if(this.client.getPlayer().isActive() && this.client.getPlayer().getRoadCount()<=13 && _enable){
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
					gameManagerButton.setForeground(CATAN_RED);
				}
			});
		}
		for(Player p : players) {
			if(p.getName().equals(client.getPlayer().getName())) { //TODO Change equality check
				Color c = p.getColor();
				if(!c.equals(Edge.orange)){
					c = c.brighter();
				}
				int[] resources = p.getResources();
				wheat.setText(resources[0] + "");
				wheat.setBackground(c);
				wool.setText(resources[1] + "");
				wool.setBackground(c);
				brick.setText(resources[2] + "");
				brick.setBackground(c);
				ore.setText(resources[3] + "");
				ore.setBackground(c);
				wood.setText(resources[4] + "");
				wood.setBackground(c);
			}
		}
	}


	/**
	 * ActionListener that allows user to roll die or end turn.
	 *
	 * @see TurnEvent
	 */
	class TurnListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(client.getPlayer().isActive()) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						try {
							if(rollDieModeOrEndTurnMode) {
								client.sendMove(new FirstMove(client.getPlayerName()));
								gameManagerButton.setText("End turn");
								rollDieModeOrEndTurnMode = false;
							}
							else {
								client.sendMove(new LastMove(client.getPlayerName()));
                                client.getGUI().getDP().setSelect(-1);
								gameManagerButton.setText("Roll die");
								rollDieModeOrEndTurnMode = true;
							}
						} catch (IllegalArgumentException | IOException e) {
							e.printStackTrace();
						}
					}
				});
			}
		}
	}

	/**
	 * Requests focus.
	 */
	@Override 
	public void requestFocus() {
		gameManagerButton.requestFocus();
	}
}
