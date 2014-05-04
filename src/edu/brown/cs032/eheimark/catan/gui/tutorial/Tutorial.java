package edu.brown.cs032.eheimark.catan.gui.tutorial;

import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Misc.back;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Misc.brickTileIcon;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Misc.brickToken;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Misc.city;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Misc.forward;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Misc.oreTileIcon;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Misc.oreToken;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Misc.road;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Misc.settlement;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Misc.sheepTileIcon;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Misc.wheatTileIcon;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Misc.wheatToken;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Misc.woodTileIcon;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Misc.woodToken;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Misc.woolToken;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import edu.brown.cs032.eheimark.catan.gui.Constants;

public class Tutorial extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel _myTutorialPanel;
	private final ArrayList<TutorialPage> _pages;
	private final JButton forwardButton, backButton;
	private int _idx;

	public Tutorial() {
		super("Tutorial");
		_myTutorialPanel = new JPanel();
		_pages = new ArrayList<TutorialPage>();

		backButton = new JButton();
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						swapPage(findPrevious());
					}
				});
			}
		}); 
		backButton.setIcon(back);
		backButton.setPreferredSize(new Dimension(back.getIconWidth(), back.getIconHeight()));

		forwardButton = new JButton();
		forwardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						swapPage(findNext());
					}
				});
			}
		}); 
		forwardButton.setIcon(forward);
		forwardButton.setPreferredSize(new Dimension(forward.getIconWidth(), forward.getIconHeight()));

		forwardButton.setOpaque(false);
		forwardButton.setContentAreaFilled(false);
		forwardButton.setBorderPainted(false);
		backButton.setOpaque(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorderPainted(false);
		
		addPages();
		_idx = -1; // So starts at 0 once you findNext()
		swapPage(findNext());

		setMaximumSize(Constants.TUTORIAL_FRAME_SIZE);
		setMinimumSize(Constants.TUTORIAL_FRAME_SIZE);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public TutorialPage findNext() {
		_idx = (_idx + 1) % _pages.size();
		return _pages.get(_idx);
	}

	public TutorialPage findPrevious() {
		_idx = (_idx - 1 + _pages.size()) % _pages.size();
		return _pages.get(_idx);
	}

	public void swapPage(TutorialPage p) {
		remove(_myTutorialPanel);
		_myTutorialPanel = new JPanel(new BorderLayout());
		_myTutorialPanel.add(backButton, BorderLayout.WEST);
		_myTutorialPanel.add(p, BorderLayout.NORTH);
		_myTutorialPanel.add(forwardButton, BorderLayout.EAST);
		JLabel pgNumber = new JLabel(_idx + "/" + (_pages.size() - 1));
		pgNumber.setHorizontalAlignment(SwingConstants.CENTER);
		_myTutorialPanel.add(pgNumber);
		add(_myTutorialPanel, BorderLayout.CENTER);
		_myTutorialPanel.repaint();
		_myTutorialPanel.revalidate();

	}

	public void addPages() {
		_pages.add(new TutorialPage("***SETTLERS TUTORIAL***\n\nClick to continue.", null));
		_pages.add(new TutorialPage("In the game Settlers of Catan, there are five types of resources...", null));
		_pages.add(new TutorialPage("Brick", brickToken));
		_pages.add(new TutorialPage("Ore", oreToken));
		_pages.add(new TutorialPage("Wool", woolToken));
		_pages.add(new TutorialPage("Wheat", wheatToken));
		_pages.add(new TutorialPage("Wood", woodToken));
		_pages.add(new TutorialPage("Each tile marked with the number rolled produces one resource for each adjacent village and two resources for each adjacent city.", null));
		_pages.add(new TutorialPage("There are five types of tiles...", null));
		_pages.add(new TutorialPage("Brick Tile", brickTileIcon));
		_pages.add(new TutorialPage("Ore Tile", oreTileIcon));
		_pages.add(new TutorialPage("Sheep Tile", sheepTileIcon));
		_pages.add(new TutorialPage("Wood Tile", woodTileIcon));
		_pages.add(new TutorialPage("Wheat Tile", wheatTileIcon));
		_pages.add(new TutorialPage("You can use resources to build...", null));
		_pages.add(new TutorialPage("Road", road));
		_pages.add(new TutorialPage("Settlement", settlement));
		_pages.add(new TutorialPage("City", city));
		_pages.add(new TutorialPage("Development Card", null));
		_pages.add(new TutorialPage("A Settlement is worth one Victory Point.\n\nA City is worth two Victory Points.", null));
		_pages.add(new TutorialPage("The first player to reach ten Victory Points wins!", null));
		_pages.add(new TutorialPage("The Robber: If a player rolls a seven, three actions are taken:", null));
		_pages.add(new TutorialPage("(i) Any player with more than seven cards must discard half their cards, rounding down.", null));
		_pages.add(new TutorialPage("(ii) The player moves the Robber to any tile other than the wasteland.", null));
		_pages.add(new TutorialPage("(iii) The player steals one resource card from a player with a village adjacent to the Robber hex.", null));
		_pages.add(new TutorialPage("After rolling the die, users can:\n(a) build,\n(b) trade,\n(c) play a development card.", null));
		_pages.add(new TutorialPage("There are five development cards...", null));
		_pages.add(new TutorialPage("Knight Dev. Card:\nMove robber. If you play 3x, you get the Largest Army.", null));
		_pages.add(new TutorialPage("Road Builder Dev. Card:\nBuild two roads.", null));
		_pages.add(new TutorialPage("Year of Plenty Dev. Card:\nTake any two resources from the bank.", null));
		_pages.add(new TutorialPage("Monopoly Dev. Card:\nChoose a resource and all resources of that type will be transferred.", null));
		_pages.add(new TutorialPage("Victory Point Dev. Card:\n+1 VP.", null));
		_pages.add(new TutorialPage("The game begins by having players place two initial settlements and two initial roads. ", null));
		_pages.add(new TutorialPage("Once your turn begins, roll the dice. Then trade, build, or play a development card!", null));
		_pages.add(new TutorialPage("To propose a drag, select your proposed trade by using drag and drop!", null));
		_pages.add(new TutorialPage("Use the chat box to chat, or /p <player> <msg> to private message. Use up/down for chat history.", null));
		_pages.add(new TutorialPage("There are also keyboard shortcuts...", null));
		_pages.add(new TutorialPage("CTRL+C=ChatBox, CTRL+O=Overview, CTRL+B=Build, CTRL+T=Trade, CTRL+D=DevCard, CTRL+R=ROLL", null));
		_pages.add(new TutorialPage("***END TUTORIAL***", null));
	}

	public static void main(String[] args) {
		new Tutorial();
	}
}
