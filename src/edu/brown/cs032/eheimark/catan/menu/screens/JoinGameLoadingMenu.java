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
import edu.brown.cs032.eheimark.catan.jcomponents.CatanMenuButton;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanScrollableTextArea;
import edu.brown.cs032.eheimark.catan.menu.LaunchMenu;

public class JoinGameLoadingMenu extends CatanMenu {
	private static final long serialVersionUID = 1L;
	private JButton back;
	private final CatanScrollableTextArea jsp;
	private StringBuilder sb;
	private CatanClient cc;
	private Timer timer;
	private final int SECONDS_DELAY = 1;

	public JoinGameLoadingMenu() {
		super();
		sb = new StringBuilder();
		jsp = new CatanScrollableTextArea(); 
		back = new CatanMenuButton("Main Menu");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(timer != null) {
					timer.cancel();
				}
				if(cc != null) {
					cc.stop(); // TODO: Check with Alex how to quit
				}
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						LaunchMenu.frame.setPage(new MainMenu());
					}
				});
			}
		});
		addButton(jsp);
		addButton(back);
	}

	private class UpdateTextArea extends TimerTask {
		@Override
		public void run() {
			try {
				if(!cc.getIsStarting()) {
					System.out.println("Trying to update text area...");
					String sm = cc.readServerMessage();
					sb.append(sm);
					System.out.println(sm);
					jsp.getTextArea().setText(sb.toString());
				}
				else {
					System.out.println("Ready to START!!!!!");
					if(timer != null) {
						timer.cancel();
					}
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

	public void loadClient() {
		try {
			cc = new CatanClient(LaunchMenu.lc);
			sb.append("Trying to connect to server...\n");
			jsp.getTextArea().setText(sb.toString());
			timer = new Timer();
			timer.scheduleAtFixedRate(new UpdateTextArea(), 0, SECONDS_DELAY*1000);
			repaint();
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
