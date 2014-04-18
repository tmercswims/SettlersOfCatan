package edu.brown.cs032.eheimark.catan.menu;

import javax.swing.SwingUtilities;

import edu.brown.cs032.eheimark.catan.menu.screens.MainMenu;


public class LaunchMenu extends LaunchConfiguration {
	public static CatanFrame frame;
	public static LaunchConfiguration lc;
	
	public LaunchMenu() {
		lc = new LaunchConfiguration();
		frame = new CatanFrame(new MainMenu(), "Settlers of Catan");
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new LaunchMenu();
			}
		});
	}
}
