package edu.brown.cs032.eheimark.catan.menu.screens;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanJLabel;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanMenuButton;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanTextField;
import edu.brown.cs032.eheimark.catan.menu.Constants;
import edu.brown.cs032.eheimark.catan.menu.LaunchMenu;


public class ProfileMenu extends CatanMenu {
	private static final long serialVersionUID = 1L;
	private final JButton selectAvatar, back;
	private final JTextField username;
	private final JLabel usernameLabel;


	public ProfileMenu() {
		super();

		usernameLabel = new CatanJLabel("Enter username:");
		username = new CatanTextField(LaunchMenu.lc.getAvatarName());

		selectAvatar = new CatanMenuButton("Select Avatar");
		selectAvatar.setForeground(Constants.CATAN_BLUE);
		back = new CatanMenuButton("Back");

		username.getDocument().addDocumentListener(new DocumentListener() {
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
				String name = username.getText();
				LaunchMenu.lc.setAvatarName(name);
			}
		});
		
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LaunchMenu.frame.setPage(new MainMenu());
			}
		});

		addButton(usernameLabel);
		addButton(username);
		addButton(selectAvatar);
		addButton(back);
	}
}
