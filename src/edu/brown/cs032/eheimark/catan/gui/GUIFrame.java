package edu.brown.cs032.eheimark.catan.gui;

import javax.swing.JFrame;

public class GUIFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public GUIFrame() {
		super("GUI Frame");
		setVisible(true);
//		setResizable(false);
//		setMinimumSize(Constants.MENU_SIZE);
//
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new GUIFrame();
	}
}
