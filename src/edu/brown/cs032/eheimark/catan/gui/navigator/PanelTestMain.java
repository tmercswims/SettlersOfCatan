package edu.brown.cs032.eheimark.catan.gui.navigator;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.launch.LaunchConfiguration;
import edu.brown.cs032.sbreslow.catan.gui.board.DrawingPanel;

public class PanelTestMain {

	public static void main(String[] args) {
		DevCard dc = new DevCard(null, null);
		JFrame f = new JFrame();
		f.add(dc);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
	}

}
