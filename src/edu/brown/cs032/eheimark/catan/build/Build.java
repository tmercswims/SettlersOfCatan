package edu.brown.cs032.eheimark.catan.build;

import edu.brown.cs032.eheimark.catan.menu.CatanFrame;

public class Build {
	public static CatanFrame frame;
	
	public Build() {
		frame = new CatanFrame(new BuildMenu(), "Build Menu");
	}
	
	public static void main(String[] args) {
		new Build();
	}
}
