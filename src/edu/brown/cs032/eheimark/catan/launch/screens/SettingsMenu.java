package edu.brown.cs032.eheimark.catan.launch.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import edu.brown.cs032.eheimark.catan.launch.LaunchConfiguration;
import edu.brown.cs032.eheimark.catan.launch.SettlersOfCatan;
import edu.brown.cs032.eheimark.catan.launch.screens.jcomponents.CatanJLabel;
import edu.brown.cs032.eheimark.catan.launch.screens.jcomponents.CatanJRadioButton;
import edu.brown.cs032.eheimark.catan.launch.screens.jcomponents.CatanMenuButton;
import edu.brown.cs032.eheimark.catan.launch.screens.jcomponents.CatanToggleButton;

/**
 * The Class SettingsMenu is the settings page menu within the Catan launch menus, allowing the user
 * to toggle in-game help.
 */
public class SettingsMenu extends CatanMenu {
	private static final long serialVersionUID = 1L;
	private final JToggleButton help;
	private final JButton back;
	private final JLabel size;
	private final ButtonGroup bg;
	private final JRadioButton small, medium, large;
	private final SettlersOfCatan soc;

	/**
	 * Instantiates a new settings menu.
	 * @param soc reference to Settlers Of Catan class instance (which contains launch configurations etc)
	 */
	public SettingsMenu(SettlersOfCatan socIn) {
		super();
		this.soc = socIn;
		back = new CatanMenuButton("Back");
		size = new CatanJLabel("Select Board Size:");
		bg = new ButtonGroup();
		small = new CatanJRadioButton("Small");
		medium = new CatanJRadioButton("Medium");
		large = new CatanJRadioButton("Large");
		bg.add(small);
		bg.add(medium);
		bg.add(large);
		if(soc.getLaunchConfiguration().getBoardSize() == LaunchConfiguration.SMALL_BOARD) {
			small.setSelected(true);
		}
		else if(soc.getLaunchConfiguration().getBoardSize() == LaunchConfiguration.MEDIUM_BOARD) {
			medium.setSelected(true);
		}
		else if(soc.getLaunchConfiguration().getBoardSize() == LaunchConfiguration.LARGE_BOARD) {
			large.setSelected(true);
		}

		small.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				soc.getLaunchConfiguration().setBoardSize(LaunchConfiguration.SMALL_BOARD);
			}
		});
		medium.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				soc.getLaunchConfiguration().setBoardSize(LaunchConfiguration.MEDIUM_BOARD);
			}
		});
		large.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				soc.getLaunchConfiguration().setBoardSize(LaunchConfiguration.LARGE_BOARD);
			}
		});

		if(soc.getLaunchConfiguration().isInGameHelpOn()) {
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
					soc.getLaunchConfiguration().setInGameHelpOn(false);
				}
				else {
					help.setText("In-Game Help: On");
					soc.getLaunchConfiguration().setInGameHelpOn(true);
				}
			}
		});

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						soc.getFrame().setPage(new MainMenu(soc));
					}
				});
			}
		});

		addComponent(size);
		addComponent(small);
		addComponent(medium);
		addComponent(large);
		addComponent(help);
		addComponent(back);
	}
}
