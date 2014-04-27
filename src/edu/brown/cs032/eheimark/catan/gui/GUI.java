package edu.brown.cs032.eheimark.catan.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.brown.cs032.atreil.catan.chat.client.ChatClient;
import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.navigator.TabbedPanel;
import edu.brown.cs032.sbreslow.catan.gui.board.DrawingPanel;

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
	
	/**
	 * Instantiates a new gui.
	 *
	 * @param cc the cc
	 */
	public GUI(CatanClient cc) {
		super(new BorderLayout());
		this.client = cc;
		gameBoard = new DrawingPanel(client);
		add(gameBoard, BorderLayout.CENTER);		
		tabbedMenu = new TabbedPanel(client, gameBoard);
		add(tabbedMenu, BorderLayout.SOUTH);
		//TODO Eric changed order explain this
		this.client.setGUI(this);
		this.client.start(); // start listening on client
		try {
			ChatClient chatc = new ChatClient(cc.getIP(), cc.getChatPort(), client.getPlayer());
			chatc.setClient(client);
			add(chatc._panel, BorderLayout.EAST);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
				client.confirmPacket();
			}
		});
	}
}
