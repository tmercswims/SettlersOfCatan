package edu.brown.cs032.eheimark.catan.launch;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The Class CatanFrame is a generic JFrame for the Catan game.
 */
public class CatanFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new Catan frame.
	 *
	 * @param p the initial pane
	 * @param name the name of the frame
	 */
	public CatanFrame(JPanel p, String name) {
		super(name);
		setPage(p);
		setVisible(true);
		setResizable(false);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Sets the page.
	 *
	 * @param page the new page to swap out old JPanel for
	 */
	public void setPage(JPanel page) {
		setContentPane(page);
		pack();
		repaint();
	}

	/**
	 * Exits JFrame.
	 */
	public void exit() {
		super.setVisible(false);
		super.dispose();
	}
}
