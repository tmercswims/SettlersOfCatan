package edu.brown.cs032.eheimark.catan.menu.screens;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.atreil.catan.networking.server.CatanServer;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanMenuButton;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanScrollableTextArea;
import edu.brown.cs032.eheimark.catan.menu.LaunchMenu;

public class JoinGameLoadingMenu extends CatanMenu {
	private static final long serialVersionUID = 1L;
	private JButton back;
	private final CatanScrollableTextArea jsp;
	private StringBuilder sb;
	private CatanClient cc;
	private ServerUpdate su;

	public JoinGameLoadingMenu() {
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
	}

	class ServerUpdate extends Thread {
		@Override
		public void run() {
			while(true) { //TODO: Switch to isRunning method
				try {
					if(!cc.getIsStarting()) {
						String sm = cc.readServerMessage();
						sb.append(sm);
						System.out.println(sm);
						jsp.getTextArea().setText(sb.toString());
					}
					else {
						System.out.println("Ready to START!!!!!");
						break; //break loop //TODO: Double check
					}
				}
				catch (UnknownHostException e) {
					sb.append(e.getMessage() + "...\n");
					sb.append("Please try again!");
					jsp.getTextArea().setText(sb.toString());

				} catch (IOException e) {
					sb.append(e.getMessage() + "...\n");
					sb.append("Please try again!");
					jsp.getTextArea().setText(sb.toString());
				}
			}
		}
	}

	public void loadClient() {
		try {
			cc = new CatanClient(LaunchMenu.lc);
			sb.append("Trying to connect to server...\n");
			jsp.getTextArea().setText(sb.toString());
			repaint();
			su = new ServerUpdate();
			su.start();
		} catch (UnknownHostException e) {
			sb.append(e.getMessage() + "...\n");
			sb.append("Please try again!");
			jsp.getTextArea().setText(sb.toString());
		} catch (IOException e) {
			sb.append(e.getMessage() + "...\n");
			sb.append("Please try again!");
			jsp.getTextArea().setText(sb.toString());
		}
	}
}
