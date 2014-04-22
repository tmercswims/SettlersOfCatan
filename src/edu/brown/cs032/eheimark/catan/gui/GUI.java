package edu.brown.cs032.eheimark.catan.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JPanel;

import edu.brown.cs032.atreil.catan.chat.client.ChatClient;
import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.navigator.TabbedPanel;
import edu.brown.cs032.eheimark.catan.launch.SettlersOfCatan;
import edu.brown.cs032.sbreslow.catan.gui.board.DrawingPanel;
import edu.brown.cs032.tmercuri.catan.logic.Player;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GUI extends JPanel {
	private final CatanClient client;
	private JPanel tabbedMenu, chatBox;
	private DrawingPanel gameBoard;
	
	//TODO: Delete throws
	public GUI(CatanClient cc) {
		super(new BorderLayout());
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                   }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.err.println("ERROR: " + ex.getMessage());
        }
		this.client = cc;
		this.client.setGUI(this);
		this.client.start(); // start listening on client
		gameBoard = new DrawingPanel(client);
		add(gameBoard, BorderLayout.CENTER);		
		tabbedMenu = new TabbedPanel(client, gameBoard);
		add(tabbedMenu, BorderLayout.SOUTH);
		try {
			ChatClient chatc = new ChatClient(cc.getIP(), cc.getChatPort(), client.getPlayer());
			add(chatc._panel, BorderLayout.EAST);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		gameBoard.repaint();
		tabbedMenu.repaint();
	}
	
	
}
