package edu.brown.cs032.eheimark.catan.gui;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JPanel;

import edu.brown.cs032.atreil.catan.chat.client.ChatClient;
import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.navigator.TabbedPanel;
import edu.brown.cs032.eheimark.catan.menu.LaunchMenu;
import edu.brown.cs032.sbreslow.catan.gui.board.DrawingPanel;
import edu.brown.cs032.tmercuri.catan.logic.Player;

public class GUI extends JPanel {
	private final CatanClient client;
	private JPanel gameBoard, tabbedMenu, chatBox;
	
	//TODO: Delete throws
	public GUI(CatanClient cc) {
		super(new BorderLayout());
		this.client = cc;
		this.client.start(); // start listening on client
		gameBoard = new DrawingPanel(client);
		add(gameBoard, BorderLayout.CENTER);		
		tabbedMenu = new TabbedPanel(client);
		add(tabbedMenu, BorderLayout.SOUTH);
		try {
			ChatClient chatc = new ChatClient(cc.getIP(), 9000, client.getPlayer());
			add(chatc._panel, BorderLayout.EAST);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
