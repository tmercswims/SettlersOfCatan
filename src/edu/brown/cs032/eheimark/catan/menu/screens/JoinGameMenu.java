package edu.brown.cs032.eheimark.catan.menu.screens;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanJLabel;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanMenuButton;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanTextField;
import edu.brown.cs032.eheimark.catan.menu.LaunchMenu;


public class JoinGameMenu extends CatanMenu {
	private static final long serialVersionUID = 1L;
	private final JButton submit, back;
	private final JTextField port;
	private final JLabel joinLabel;

	public JoinGameMenu() {
		super();
		joinLabel = new CatanJLabel("Select Join Port:");
		port = new CatanTextField(Integer.toString(LaunchMenu.lc.getJoinPort()));
		submit = new CatanMenuButton("Submit");
		back = new CatanMenuButton("Back");

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
					int joinPort = Integer.parseInt(port.getText());
					LaunchMenu.lc.setJoinPort(joinPort);
				}
				catch(NumberFormatException e1) {
					e1.printStackTrace();
				}
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
				final JoinGameLoadingMenu jglm = new JoinGameLoadingMenu();
				System.out.println("Trying to change page!!");
				LaunchMenu.frame.setPage(jglm);
				System.out.println("DONE REPAINTING!!");
				jglm.loadClient();
			}
		});

		addButton(joinLabel);
		addButton(port);
		addButton(submit);
		addButton(back);
	}
}
