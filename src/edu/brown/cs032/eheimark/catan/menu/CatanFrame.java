package edu.brown.cs032.eheimark.catan.menu;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CatanFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public CatanFrame(JPanel p, String name) {
		super(name);
		setPage(p);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setPage(JPanel page) {
		setContentPane(page);
		pack();
		repaint();
	}
	
	public void exit() {
		super.dispose();
	}
}
