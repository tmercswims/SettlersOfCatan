package edu.brown.cs032.eheimark.catan.menu.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.GUIFrame;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanMenuButton;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanScrollableTextArea;
import edu.brown.cs032.eheimark.catan.menu.LaunchMenu;
import javax.swing.text.DefaultCaret;

public class JoinGameLoadingMenu extends CatanMenu {
	private static final long serialVersionUID = 1L;
	private final JButton back;
	private final CatanScrollableTextArea jsp;
	private CatanClient cc;
	private ServerUpdate su;
	private final JTextArea jta;

	public JoinGameLoadingMenu() {
		super();
		jsp = new CatanScrollableTextArea(); 
		jta = jsp.getTextArea();
        DefaultCaret caret = (DefaultCaret)jta.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		jta.append("Trying to connect to server...\n");
		back = new CatanMenuButton("Main Menu");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if(cc != null) {
							cc.kill();
						}
						if(su != null) {
							su.stop(); // TODO: Better option than stop?
						}
						LaunchMenu.frame.setPage(new MainMenu());
					}
				});
			}
		});
		addButton(jsp);
		addButton(back);
		su = new ServerUpdate();
		su.start();
	}

	class ServerUpdate extends Thread {
		@Override
		public void run() {
			try {
				cc = new CatanClient(LaunchMenu.lc);
				while(true) { //TODO: Switch to isRunning method
					if(!cc.getIsStarting()) {
						jta.append(cc.readServerMessage());
					}
					else {
						jta.append("Launching the game...");
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								new GUIFrame(cc);
							}
						});
						break; //break loop //TODO: Double check
					}
				}
			}
			catch (UnknownHostException e) {
				e.printStackTrace();
				jta.append(e.getMessage() + "...\n");
				jta.append("Please try again!");
			} catch (IOException e) {
				e.printStackTrace();
				jta.append(e.getMessage() + "...\n");
				jta.append("Please try again!");
			}
		}
	}
}
