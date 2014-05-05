package edu.brown.cs032.sbreslow.catan.gui.devCards;

import java.awt.Color;
import java.util.ArrayList;

import edu.brown.cs032.tmercuri.catan.logic.*;
import javax.swing.JFrame;

public class testmain {

	public static void main(String[] args) {
		//new SevenFrame(null);
		//new RobberFrame((List<Player>)new ArrayList<Player>(),0, null);
		new YoPFrame(null, null);
		Player p1 = new Player("Sam");
		p1.setColor(Color.red);
		Player p2 = new Player("not sam");
		p2.setColor(Color.green);
		ArrayList<Player> test = new ArrayList<Player>();
		test.add(p1);
		test.add(p2);
		//new RobberFrame(test, 3, null);
		//new MonoFrame(null);
	}

}
