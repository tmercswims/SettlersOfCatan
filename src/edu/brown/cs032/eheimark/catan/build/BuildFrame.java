package edu.brown.cs032.eheimark.catan.build;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BuildFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public BuildFrame() {
		super();
		setPage(new BuildMenu());
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setPage(JPanel page) {
		setContentPane(page);
		pack();
	}
	
	public void exit() {
		super.dispose();
	}
	
	public static void main(String[] args) {
		new Build();
	}
}
