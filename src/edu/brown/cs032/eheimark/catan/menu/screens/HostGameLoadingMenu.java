package edu.brown.cs032.eheimark.catan.menu.screens;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

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
	private CatanServer cs;
	private ServerUpdate su;

	public HostGameLoadingMenu() {
		super();
		sb = new StringBuilder();
		jsp = new CatanScrollableTextArea(); 
		back = new CatanMenuButton("Main Menu");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if(cs != null) {
							cs.kill(); 
						}
						if(su != null) {
							su.stop(); // TODO: Better option than stop?
						}
						LaunchMenu.frame.setPage(new MainMenu());
						repaint();
					}
				});
			}
		});
		addButton(jsp);
		addButton(back);	
	}


	public void loadServer() {
		try {
			updateTextArea("Trying to launch server...\n");
			cs = new CatanServer(LaunchMenu.lc);
			cs.start();
			updateTextArea("Server launched successfully!\n");
			su = new ServerUpdate();
			su.start();
		} catch (IOException e) {
			e.printStackTrace();
			sb.append("Error launching server!");
			updateTextArea(sb.toString());
			e.printStackTrace();
		}

	}

	private class ServerUpdate extends Thread {
		@Override
		public void run() {
			while(true) { //TODO: Switch to isRunning method
				String message = cs.readStatus();
				updateTextArea(message);
			}
		}
	}

	private void updateTextArea(String s) {
		sb.append(s);
		jsp.getTextArea().setText(sb.toString());
	}
}
