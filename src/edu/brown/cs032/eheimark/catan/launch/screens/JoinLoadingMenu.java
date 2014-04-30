package edu.brown.cs032.eheimark.catan.launch.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.GUIFrame;
import edu.brown.cs032.eheimark.catan.launch.SettlersOfCatan;
import edu.brown.cs032.eheimark.catan.launch.screens.jcomponents.CatanMenuButton;
import edu.brown.cs032.eheimark.catan.launch.screens.jcomponents.CatanScrollableTextArea;

import javax.swing.text.DefaultCaret;

/**
 * The Class JoinLoadingMenu displays status while trying to join an outside server.
 * At the top of the page is a JTextArea that continually displays messages indicating server status (e.g.
 * how many players have connected).  Once the game is launched, this page disappears for each client and 
 * the board game appears instead.
 */
public class JoinLoadingMenu extends CatanMenu {
	private static final long serialVersionUID = 1L;
	private final JButton back;
	private final CatanScrollableTextArea jsp;
	//	private final ServerUpdate su; // Thread that continually gets a status update from server
	private final JTextArea jta;
	private final SettlersOfCatan soc;
	private CatanClient cc; // Reference to client

	/**
	 * Instantiates a new join loading menu.
	 * @param soc reference to Settlers Of Catan class instance (which contains launch configurations etc)
	 */
	public JoinLoadingMenu(SettlersOfCatan socIn) {
		super();
		soc = socIn;
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
						soc.getFrame().setPage(new MainMenu(soc));
					}
				});
			}
		});
		addComponent(jsp);
		addComponent(back);

		try {
			cc = new CatanClient(soc.getLaunchConfiguration());
			cc.setLoadingMenu(this);
			cc.start();
		}
		catch (IllegalArgumentException | IOException e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					jta.append(e.getMessage() + "...\n");
					jta.append("Please try again!");
				}
			});
		}
	}

	public void updateJTextArea(final String str) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override	
			public void run() {
				jta.append(str);
			}
		});
	}

	public void launchGame() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override	
			public void run() {
				SwingUtilities.invokeLater(new Runnable() {
					@Override	
					public void run() {
						cc.setFrame(new GUIFrame(cc));
						soc.getFrame().exit();
					}
				});
			}
		});
	}
}

