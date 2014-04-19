package edu.brown.cs032.eheimark.catan.gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;

public class GUIFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public GUIFrame(CatanClient cc) {
		super("Settlers of Catan -- " + cc.getPlayerName());
		add(new GUI(cc));
		setVisible(true);
//		setResizable(false);
		setMinimumSize(Constants.GUI_SIZE);
//
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
