package edu.brown.cs032.eheimark.catan.menu.screens;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import edu.brown.cs032.eheimark.catan.jcomponents.CatanMenuButton;
import edu.brown.cs032.eheimark.catan.menu.LaunchMenu;

public class MainMenu extends CatanMenu {
	private static final long serialVersionUID = 1L;
	private final JButton host, join, settings, quit;
	
	public MainMenu() {
		super();

		host = new CatanMenuButton("Host");
		host.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						LaunchMenu.frame.setPage(new HostMenu());
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
						LaunchMenu.frame.setPage(new JoinGameMenu());
					}
				});
			}
		});

		
		settings = new CatanMenuButton("Settings");
		settings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						LaunchMenu.frame.setPage(new SettingsMenu());
					}
				});
			}
		});
		
		quit = new CatanMenuButton("Quit");
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LaunchMenu.frame.exit();
			}
		});

		addButton(host);
		addButton(join);
		addButton(settings);
		addButton(quit);
	}
}