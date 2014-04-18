package edu.brown.cs032.eheimark.catan.menu.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.brown.cs032.eheimark.catan.jcomponents.CatanJLabel;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanJRadioButton;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanMenuButton;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanTextField;
import edu.brown.cs032.eheimark.catan.menu.LaunchMenu;

public class HostMenu extends CatanMenu {
	private static final long serialVersionUID = 1L;
	private final JButton submit, back;
	private final JTextField port;
	private final JRadioButton threePlayer, fourPlayer;
	private final ButtonGroup bg;
	private final JLabel hostLabel;

	public HostMenu() {
		super();
		hostLabel = new CatanJLabel("Select Host Port:");
		port = new CatanTextField(Integer.toString(LaunchMenu.lc.getHostPort()));
		submit = new CatanMenuButton("Submit");
		back = new CatanMenuButton("Back");

		bg = new ButtonGroup();
		threePlayer = new CatanJRadioButton("3 Player Game");
		fourPlayer = new CatanJRadioButton("4 Player Game");
		bg.add(threePlayer);
		bg.add(fourPlayer);
		if(LaunchMenu.lc.isFourPlayerGame()) {
			fourPlayer.setSelected(true);
		}
		else {
			threePlayer.setSelected(true);
		}

		port.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				change();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				change();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				change();
			}
			private void change() {
				try {
					int hostPort = Integer.parseInt(port.getText());
					LaunchMenu.lc.setHostPort(hostPort);
				}
				catch(NumberFormatException e1) {
					e1.printStackTrace();
				}
			}
		});

		threePlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LaunchMenu.lc.setFourPlayerGame(false);
			}
		});

		fourPlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LaunchMenu.lc.setFourPlayerGame(true);
			}
		});

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LaunchMenu.frame.setPage(new MainMenu());
			}
		});
		
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HostGameLoadingMenu hglm = new HostGameLoadingMenu();
				LaunchMenu.frame.setPage(hglm);
				hglm.loadServer();
			}
		});

		addButton(hostLabel);
		addButton(port);
		addButton(threePlayer);
		addButton(fourPlayer);
		addButton(submit);
		addButton(back);
	}
}
