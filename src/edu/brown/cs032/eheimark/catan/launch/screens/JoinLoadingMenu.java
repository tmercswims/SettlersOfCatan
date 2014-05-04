package edu.brown.cs032.eheimark.catan.launch.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultCaret;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.GUIFrame;
import edu.brown.cs032.eheimark.catan.launch.SettlersOfCatan;
import edu.brown.cs032.eheimark.catan.launch.screens.jcomponents.CatanMenuButton;
import edu.brown.cs032.eheimark.catan.launch.screens.jcomponents.CatanScrollPane;

/**
 * The Class JoinLoadingMenu displays status while trying to join an outside server.
 * At the top of the page is a JTextArea that continually displays messages indicating server status (e.g.
 * how many players have connected).  Once the game is launched, this page disappears for each client and 
 * the board game appears instead.
 */
public class JoinLoadingMenu extends CatanMenu {
	private static final long serialVersionUID = 5799077217722813351L;
	private final JButton back; // Button to go back
	private final CatanScrollPane jsp; // Scrollable text pane 
	private final JTextArea jta; // JTextArea contained with CatanScrollableTextPane
	private final SettlersOfCatan soc; // Reference to launch instance
	private CatanClient cc; // Reference to client

	/**
	 * Instantiates a new join loading menu.
	 *
	 * @param socIn the soc in
	 */
	public JoinLoadingMenu(SettlersOfCatan socIn) {
		super();
		soc = socIn;
		jsp = new CatanScrollPane(); 
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

	/**
	 * Update j text area.
	 *
	 * @param str the str
	 */
	public void updateJTextArea(final String str) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override	
			public void run() {
				jta.append(str);
			}
		});
	}

	/**
	 * Launch game.
	 */
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
	
	/**
	 * Sets the focus for easy keyboard shortcuts.
	 */
	@Override
	public void requestFocus() {
		super.requestFocus();
		back.requestFocus();
	}
}

