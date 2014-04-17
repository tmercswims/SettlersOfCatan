package edu.brown.cs032.eheimark.catan.menu.screens;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanMenuButton;
import edu.brown.cs032.eheimark.catan.jcomponents.CatanScrollableTextArea;
import edu.brown.cs032.eheimark.catan.menu.LaunchMenu;

public class LoadingMenu extends CatanMenu {
	private static final long serialVersionUID = 1L;
	private JButton back;
	private final JScrollPane jsp;


	public LoadingMenu(String loadingMessage) {
		super();
		jsp = new CatanScrollableTextArea(loadingMessage); 
		back = new CatanMenuButton("Main Menu");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LaunchMenu.frame.setPage(new MainMenu());
			}
		});
		addButton(jsp);
		addButton(back);
	}
}
