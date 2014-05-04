package edu.brown.cs032.eheimark.catan.gui.navigator;

import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Misc.musicOff;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Misc.musicOn;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.tutorial.Tutorial;

public class MusicPanel extends JPanel {
	private static final long serialVersionUID = -1167737874016826607L;
	private final CatanClient client;
	private final JButton questionButton, musicButton;

	public MusicPanel(CatanClient cc) {
		super();
		client = cc;
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		questionButton = new JButton("?") {
			private static final long serialVersionUID = 229623158965666152L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
			}
		};
		questionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						new Tutorial();
					}
				});
			}
		});
		musicButton = new JButton() {
			private static final long serialVersionUID = 8345488729823071304L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (client.getFrame().isMusicPlaying()) {
					setIcon(musicOn);
				} else {
					setIcon(musicOff);
				}
			}
		};
		musicButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						client.getFrame().toggleMusic();
					}
				});
			}
		});
		//		_musicButton.setBounds(0,0,50,50);
		musicButton.setMinimumSize(new Dimension(50,50));
		musicButton.setMaximumSize(new Dimension(50,50));
		musicButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		questionButton.setAlignmentY(Component.TOP_ALIGNMENT);

		//		_questionButton.setBounds(getWidth()-50,0,50,50);
		questionButton.setMinimumSize(new Dimension(50,50));
		questionButton.setMaximumSize(new Dimension(50,50));
		questionButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		questionButton.setAlignmentY(Component.TOP_ALIGNMENT);

		add(musicButton);
		add(Box.createHorizontalGlue());
		add(questionButton);
	}
}
