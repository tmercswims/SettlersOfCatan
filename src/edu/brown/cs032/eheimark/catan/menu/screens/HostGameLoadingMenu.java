package edu.brown.cs032.eheimark.catan.menu.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import edu.brown.cs032.atreil.catan.networking.server.CatanServer;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanMenuButton;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanScrollableTextArea;
import edu.brown.cs032.eheimark.catan.menu.LaunchMenu;
import javax.swing.text.DefaultCaret;

public class HostGameLoadingMenu extends CatanMenu {
	private static final long serialVersionUID = 1L;
	private final JButton back;
	private final CatanScrollableTextArea jsp;
	private final JTextArea jta;
	private CatanServer cs;
	private ServerUpdate su;

	public HostGameLoadingMenu() {
		super();
		jsp = new CatanScrollableTextArea(); 
		jta = jsp.getTextArea();
        DefaultCaret caret = (DefaultCaret)jta.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		jta.append("Trying to launch server...\n");
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
		su = new ServerUpdate();
		su.start();
	}

	private class ServerUpdate extends Thread {
		@Override
		public void run() {
			try {
				cs = new CatanServer(LaunchMenu.lc);
				cs.start();
				jta.append("Server launched successfully!\n");
				while(true) { //TODO: Switch to isRunning method
					jta.append(cs.readStatus());
				}
			} catch(IOException e) {
				e.printStackTrace();
				jta.append("Error: " + e.getMessage() + "\n");
				e.printStackTrace();
			}
		}
	}
}
