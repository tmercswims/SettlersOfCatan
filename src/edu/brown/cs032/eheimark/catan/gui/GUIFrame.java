package edu.brown.cs032.eheimark.catan.gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GUIFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public GUIFrame() {
		super("GUI Frame");
		add(new GUI());
		setVisible(true);
//		setResizable(false);
//		setMinimumSize(Constants.MENU_SIZE);
//
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GUIFrame();
			}
		});
	}
}
