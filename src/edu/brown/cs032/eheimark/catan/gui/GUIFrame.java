package edu.brown.cs032.eheimark.catan.gui;

import javax.swing.JFrame;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;

/**
 * The Class GUIFrame contains the main gui panel with the board at top
 * and tabbed panels at the bottom.
 */
public class GUIFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new GUI frame.
	 *
	 * @param cc the cc
	 */
	public GUIFrame(CatanClient cc) {
		super("Settlers of Catan : " + cc.getPlayerName());
		add(new GUI(cc));
		setVisible(true);
		setResizable(false);
		setMinimumSize(Constants.GUI_SIZE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
	}
}
