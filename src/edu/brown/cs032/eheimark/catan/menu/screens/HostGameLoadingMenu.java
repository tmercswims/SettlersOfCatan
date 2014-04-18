package edu.brown.cs032.eheimark.catan.menu.screens;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JScrollPane;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.atreil.catan.networking.server.CatanServer;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanMenuButton;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanScrollableTextArea;
import edu.brown.cs032.eheimark.catan.menu.LaunchMenu;

public class HostGameLoadingMenu extends CatanMenu {
	private static final long serialVersionUID = 1L;
	private JButton back;
	private final CatanScrollableTextArea jsp;
	private StringBuilder sb;

	public HostGameLoadingMenu() {
		super();
		sb = new StringBuilder();
		jsp = new CatanScrollableTextArea(); 
		back = new CatanMenuButton("Main Menu");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LaunchMenu.frame.setPage(new MainMenu());
			}
		});
		addButton(jsp);
		addButton(back);
		
	}
	
	public void loadServer() {
		CatanServer cs;
		
		try {
			sb.append("Trying to launch server...\n");
			cs = new CatanServer(LaunchMenu.lc);
			cs.start();
			sb.append("Server launched successfully!");
			updateTextArea();
		} catch (IOException e) {
			sb.append("Error launching server!");
			updateTextArea();
			e.printStackTrace();
		}
		
	}
	
	private void updateTextArea() {
		jsp.getTextArea().setText(sb.toString());
	}
}
