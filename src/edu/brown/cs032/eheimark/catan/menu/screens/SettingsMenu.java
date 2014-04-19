package edu.brown.cs032.eheimark.catan.menu.screens;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import edu.brown.cs032.eheimark.catan.jcomponents.CatanJLabel;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanJRadioButton;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanMenuButton;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanToggleButton;
import edu.brown.cs032.eheimark.catan.menu.LaunchConfiguration;
import edu.brown.cs032.eheimark.catan.menu.LaunchMenu;


public class SettingsMenu extends CatanMenu {
	private static final long serialVersionUID = 1L;
	private final JToggleButton help;
	private final JButton back;
	private final JLabel size;

	private final ButtonGroup bg;
	private final JRadioButton small, medium, large;

	public SettingsMenu() {
		super();

		back = new CatanMenuButton("Back");
		size = new CatanJLabel("Select Board Size:");

		bg = new ButtonGroup();
		small = new CatanJRadioButton("Small");
		medium = new CatanJRadioButton("Medium");
		large = new CatanJRadioButton("Large");
		bg.add(small);
		bg.add(medium);
		bg.add(large);
		
		if(LaunchMenu.lc.getBoardSize() == LaunchConfiguration.SMALL_BOARD) {
			small.setSelected(true);
		}
		else if(LaunchMenu.lc.getBoardSize() == LaunchConfiguration.MEDIUM_BOARD) {
			medium.setSelected(true);
		}
		else if(LaunchMenu.lc.getBoardSize() == LaunchConfiguration.LARGE_BOARD) {
			large.setSelected(true);
		}
		
		small.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LaunchMenu.lc.setBoardSize(LaunchConfiguration.SMALL_BOARD);
			}
		});
		medium.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LaunchMenu.lc.setBoardSize(LaunchConfiguration.MEDIUM_BOARD);
			}
		});
		large.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LaunchMenu.lc.setBoardSize(LaunchConfiguration.LARGE_BOARD);
			}
		});
		

		if(LaunchMenu.lc.isInGameHelpOn()) {
			help = new CatanToggleButton("In-Game Help: On");
		}
		else {
			help = new CatanToggleButton("In-Game Help: Off");		
			help.setSelected(true);
		}

		help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(help.isSelected()) {
					help.setText("In-Game Help: Off");	
					LaunchMenu.lc.setInGameHelpOn(false);
				}
				else {
					help.setText("In-Game Help: On");
					LaunchMenu.lc.setInGameHelpOn(true);
				}
			}
		});


		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						LaunchMenu.frame.setPage(new MainMenu());
					}
				});
			}
		});

		addButton(size);
		addButton(small);
		addButton(medium);
		addButton(large);
		addButton(help);
		addButton(back);
	}
}
