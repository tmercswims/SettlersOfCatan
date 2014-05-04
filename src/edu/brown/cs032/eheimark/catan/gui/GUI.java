package edu.brown.cs032.eheimark.catan.gui;

import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Background.wood;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.chat.ChatPanel;
import edu.brown.cs032.eheimark.catan.gui.misc.PlayerStatsAndRollButton;
import edu.brown.cs032.eheimark.catan.gui.panels.TabbedPanel;
import edu.brown.cs032.sbreslow.catan.gui.board.DrawingPanel;

/**
 * The Class GUI contains elements needed to run the game once it is initialized (that is,
 * once the launch menus are complete). It is a JPanel that contains the board at the top,
 * chat box on the right-hand side, and tabbed panel menu at the bottom.
 */
public class GUI extends JPanel implements ServerUpdate {
	private static final long serialVersionUID = -6013729324663486937L;
	private final CatanClient client; // Reference to client
	private final TabbedPanel tabbedMenu; // Tabbed menu goes below
	private final DrawingPanel gameBoard; // Game board goes below
	private final ChatPanel chat; // Chat panel is situated on right
	private final PlayerStatsAndRollButton activeplayer;
	private JPanel left; // Left panel contains PlayerStatsAndRullButton and the Drawing Panel

	/**
	 * Instantiates a new gui.
	 *
	 * @param cc the cc
	 */
	public GUI(CatanClient cc) {
		super(new BorderLayout());
		left = new JPanel(new BorderLayout());
        left.setOpaque(false);
		this.client = cc;
		activeplayer = new PlayerStatsAndRollButton(client);
		left.add(activeplayer, BorderLayout.SOUTH);
		gameBoard = new DrawingPanel(client);
		left.add(gameBoard, BorderLayout.NORTH);
		add(left, BorderLayout.CENTER);
		tabbedMenu = new TabbedPanel(client, gameBoard);
		
		add(tabbedMenu, BorderLayout.SOUTH);
		this.client.setGUI(this);
        setOpaque(false);
		this.chat = new ChatPanel(cc, left.getSize());
		this.add(this.chat, BorderLayout.EAST);
	}
    
	/**
	 * Paints the GUI.
	 *
	 * @param g the g
	 */
    @Override
    public void paintComponent(Graphics g) {
        Image background = wood;
        int iw = background.getWidth(this);
        int ih = background.getHeight(this);
        if (iw > 0 && ih > 0) {
            for (int x = 0; x < getWidth(); x += iw) {
                for (int y = 0; y < getHeight(); y += ih) {
                    g.drawImage(background, x, y, iw, ih, this);
                }
            }
        }
        super.paintComponent(g);
    }
	
	/**
	 * Gets the dp.
	 *
	 * @return the dp
	 */
	public DrawingPanel getDP(){
		return gameBoard;
	}
	
	/**
	 * Gets the tabbed menu.
	 *
	 * @return the tabbed menu
	 */
	public TabbedPanel getTabbedMenu() {
		return tabbedMenu;
	}
	
	/**
	 * Gets the chat panel.
	 *
	 * @return the chat panel
	 */
	public ChatPanel getChatPanel() {
		return chat;
	}
	
	/**
	 * Repaints subcomponents when it gets a server update
	 */
	@Override
	public void serverUpdate() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				gameBoard.serverUpdate();
				tabbedMenu.serverUpdate();
				client.confirmPacket();
			}
		});
	}
	
	/**
	 * Update board.
	 */
	public void updateBoard() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				gameBoard.serverUpdate();
				client.confirmPacket();
			}
		});
	}
	
	/**
	 * Update players.
	 */
	public void updatePlayers() {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				tabbedMenu.serverUpdate();
				activeplayer.serverUpdate();
				client.confirmPacket();
			}
		});
	}

	/**
	 * Gets the chat.
	 *
	 * @return the chat
	 */
	public ChatPanel getChat() {
		return chat;
	}

	/**
	 * Gets the active player.
	 *
	 * @return the active player
	 */
	public Component getActivePlayer() {
		return activeplayer;
	}
}
