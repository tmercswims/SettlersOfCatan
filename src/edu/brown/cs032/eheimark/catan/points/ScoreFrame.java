package edu.brown.cs032.eheimark.catan.points;

import edu.brown.cs032.eheimark.catan.menu.CatanFrame;

public class ScoreFrame {
	public static CatanFrame frame;
	
	public ScoreFrame() {
		frame = new CatanFrame(new Score(), "Scores");
	}
	
	public static void main(String[] args) {
		new ScoreFrame();
	}
}
