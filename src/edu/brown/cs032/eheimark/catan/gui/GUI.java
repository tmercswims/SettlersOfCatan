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
import edu.brown.cs032.sbreslow.catan.gui.board.DrawingPanel;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Background.*;

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
	
	private final JPanel left;


	/**
	 * Instantiates a new gui.
	 *
	 * @param cc the cc
	 */
	public GUI(CatanClient cc) {
		super(new BorderLayout());
		left = new JPanel(new BorderLayout()) {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void paintComponent(Graphics g) {
				Image background = wood;
				int iw = background.getWidth(this);
				int ih = background.getHeight(this);
				if (iw > 0 && ih > 0) {
					for (int x = 0; x < getWidth(); x += iw) {
						for (int y = 0; y < getHeight(); y += ih) {
							System.out.println("DREW A BG TILE");
							g.drawImage(background, x, y, iw, ih, this);
						}
					}
				}
			}
		};
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
		this.client.start(); // start listening on client
		/*try {
			ChatClient chatc = new ChatClient(cc.getIP(), cc.getChatPort(), client.getPlayer());
			chatc.setClient(client);
			add(chatc._panel, BorderLayout.EAST);
			chatc.run();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		this.chat = new ChatPanel(cc);
		this.add(this.chat, BorderLayout.EAST);
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
