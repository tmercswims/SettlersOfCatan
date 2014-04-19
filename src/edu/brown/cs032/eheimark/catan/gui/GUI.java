package edu.brown.cs032.eheimark.catan.gui;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JPanel;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.navigator.TabbedPanel;
import edu.brown.cs032.eheimark.catan.menu.LaunchMenu;
import edu.brown.cs032.sbreslow.catan.gui.board.DrawingPanel;

public class GUI extends JPanel {
	private JPanel gameBoard, tabbedMenu, chatBox;
	
	//TODO: Delete throws
	public GUI() throws UnknownHostException, IOException {
		super(new BorderLayout());
		CatanClient cc = new CatanClient(new LaunchMenu());
		
		

		gameBoard = new DrawingPanel();
		add(gameBoard, BorderLayout.CENTER);		
		tabbedMenu = new TabbedPanel();
		add(tabbedMenu, BorderLayout.SOUTH);

	}

}
