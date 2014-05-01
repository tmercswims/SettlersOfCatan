package edu.brown.cs032.eheimark.catan.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.brown.cs032.atreil.catan.chat.client.ChatClient;
import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.navigator.ActivePlayer;
import edu.brown.cs032.eheimark.catan.gui.navigator.TabbedPanel;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Background.wood;
import edu.brown.cs032.sbreslow.catan.gui.board.DrawingPanel;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Background.*;
import java.awt.Image;

/**
 * The Class GUI contains elements needed to run the game once it is initialized (that is,
 * once the launch menus are complete). It is a JPanel that contains the board at the top,
 * chat box on the right-hand side, and tabbed panel menu at the bottom.
 */
public class GUI extends JPanel implements Update {
	private static final long serialVersionUID = 1L;
	private final CatanClient client;
	private final TabbedPanel tabbedMenu;
	private final DrawingPanel gameBoard;
	private final ChatPanel chat;
	private final ActivePlayer activeplayer;
	
	private JPanel left;


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
		activeplayer = new ActivePlayer(client);
		left.add(activeplayer, BorderLayout.CENTER);
		gameBoard = new DrawingPanel(client);
		left.add(gameBoard, BorderLayout.NORTH);
		add(left, BorderLayout.WEST);
		tabbedMenu = new TabbedPanel(client, gameBoard);
		add(tabbedMenu, BorderLayout.SOUTH);
		//TODO Eric changed order explain this
		this.client.setGUI(this);
        setOpaque(false);
		this.chat = new ChatPanel(cc);
		this.add(this.chat, BorderLayout.EAST);
	}
    
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
	
	@Override
	public void ericUpdate() {
		gameBoard.ericUpdate();
		tabbedMenu.ericUpdate();
	}
	
	public void updateBoard() {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				gameBoard.ericUpdate();
				client.confirmPacket();
			}
		});
	}
	
	public void updatePlayers() {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				tabbedMenu.ericUpdate();
				activeplayer.ericUpdate();
				client.confirmPacket();
			}
		});
	}

	public ChatPanel getChat() {
		return this.chat;
	}
}
