package edu.brown.cs032.eheimark.catan.launch.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import edu.brown.cs032.eheimark.catan.launch.SettlersOfCatan;
import edu.brown.cs032.eheimark.catan.launch.screens.jcomponents.CatanMenuButton;

/**
 * The Class MainMenu is the first page that launches when the Catan game starts, allowing the
 * user to navigate to other pages.
 */
public class MainMenu extends CatanMenu {
	private static final long serialVersionUID = 6630368977600473866L;
	private final JButton host, join, quit; // buttons on main menu screen
	private final SettlersOfCatan soc; // reference to SOC instance

	/**
	 * Instantiates a new main menu.
	 * @param socIn reference to Settlers Of Catan class instance, which is the launch menu
	 */
	public MainMenu(SettlersOfCatan socIn) {
		super();
		soc = socIn;

		host = new CatanMenuButton("Host");
		host.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						soc.getFrame().setPage(new HostSettingsMenu(soc));
					}
				});
			}
		});
		
		join = new CatanMenuButton("Join");
		join.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						soc.getFrame().setPage(new JoinSettingsMenu(soc));
					}
				});
			}
		});

		quit = new CatanMenuButton("Quit");
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				soc.getFrame().exit();
			}
		});

		addComponent(host);
		addComponent(join);
		addComponent(quit);
	}

	/**
	 * Sets the focus for easy keyboard shortcuts.
	 */
	@Override
	public void requestFocus() {
		super.requestFocus();
		host.requestFocus();
	}
}