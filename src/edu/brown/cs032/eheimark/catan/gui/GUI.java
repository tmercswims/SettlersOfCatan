package edu.brown.cs032.eheimark.catan.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.io.IOException;
import javax.swing.JPanel;
import edu.brown.cs032.atreil.catan.chat.client.ChatClient;
import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.navigator.TabbedPanel;
import edu.brown.cs032.sbreslow.catan.gui.board.DrawingPanel;

/**
 * The Class GUI contains elements needed to run the game once it is initialized (that is,
 * once the launch menus are complete). It is a JPanel that contains the board at the top,
 * chat box on the right-hand side, and tabbed panel menu at the bottom.
 */
public class GUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private final CatanClient client;
	private JPanel tabbedMenu;
	private DrawingPanel gameBoard;
	
	/**
	 * Instantiates a new gui.
	 *
	 * @param cc the cc
	 */
	public GUI(CatanClient cc) {
		super(new BorderLayout());
		this.client = cc;
		this.client.setGUI(this);
		this.client.start(); // start listening on client
		gameBoard = new DrawingPanel(client);
		add(gameBoard, BorderLayout.CENTER);		
		tabbedMenu = new TabbedPanel(client, gameBoard);
		add(tabbedMenu, BorderLayout.SOUTH);
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
	public void paintComponent(Graphics g) {
		gameBoard.repaint();
		tabbedMenu.repaint();
	}
}
