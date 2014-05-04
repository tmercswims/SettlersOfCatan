package edu.brown.cs032.eheimark.catan.gui.panels;

import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Background.felt;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.DevCard.h;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.DevCard.hVP;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.DevCard.knight;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.DevCard.monopoly;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.DevCard.roadBuilder;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.DevCard.victoryPoint;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.DevCard.w;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.DevCard.wVP;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.DevCard.yearOfPlenty;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Constants;
import edu.brown.cs032.eheimark.catan.gui.ServerUpdate;
import edu.brown.cs032.sbreslow.catan.gui.board.DrawingPanel;
import edu.brown.cs032.sbreslow.catan.gui.devCards.MonoFrame;
import edu.brown.cs032.sbreslow.catan.gui.devCards.YoPFrame;
import edu.brown.cs032.tmercuri.catan.logic.move.DevCardMove;
import edu.brown.cs032.tmercuri.catan.logic.move.VictoryPointMove;

/**
 * The Class DevCard is the JTabbedPane panel used for dev cards
 */
public class DevCard extends JPanel implements ServerUpdate{
	private static final long serialVersionUID = 8952316549317379729L;
	/** knight = 0; roadbuilder = 1; year of plenty = 2; monopoly = 3; vp = 4;. */
	private int[] _cards = {0,0,0,0,0};
	private CatanClient _cc; // Reference to cc
	private DrawingPanel _dp; // References to dp
	private DevCardButton[] _buttons = new DevCardButton[5];

	/**
	 * Instantiates a new dev card.
	 *
	 * @param cc the cc
	 * @param dp the dp
	 */
	public DevCard(CatanClient cc, DrawingPanel dp){
		super();
		_cc = cc;
		_dp = dp;
		setMinimumSize(Constants.TAB_PANEL_MENU_SIZE);
		setPreferredSize(Constants.TAB_PANEL_MENU_SIZE);
		setMaximumSize(Constants.TAB_PANEL_MENU_SIZE);
		GridLayout gl = new GridLayout(1,5, 5, 5);
		this.setLayout(gl);
		for(int i = 0; i < 5; i++){
			switch(i){
			case 0:
				_buttons[i] = new DevCardButton(new ImageIcon(knight.getScaledInstance(
						(int)((w/h)*Constants.TAB_PANEL_MENU_SIZE.getHeight()*3/5), 
						(int)Constants.TAB_PANEL_MENU_SIZE.getHeight()*3/5, Image.SCALE_DEFAULT)));
				_buttons[i].setText("Knight Card(s): 0");
				_buttons[i].addActionListener(new KnightList());
				break;
			case 1:
				_buttons[i] = new DevCardButton(new ImageIcon(roadBuilder.getScaledInstance(
						(int)((w/h)*Constants.TAB_PANEL_MENU_SIZE.getHeight()*3/5), 
						(int)Constants.TAB_PANEL_MENU_SIZE.getHeight()*3/5, Image.SCALE_DEFAULT)));
				_buttons[i].setText("Road Building Card(s): 0");
				_buttons[i].addActionListener(new RBList());
				break;
			case 2:
				_buttons[i] = new DevCardButton(new ImageIcon(yearOfPlenty.getScaledInstance(
						(int)((w/h)*Constants.TAB_PANEL_MENU_SIZE.getHeight()*3/5),
						(int)Constants.TAB_PANEL_MENU_SIZE.getHeight()*3/5, Image.SCALE_DEFAULT)));
				_buttons[i].setText("Year of Plenty Card(s): 0");
				_buttons[i].addActionListener(new YoPList());
				break;
			case 3:
				_buttons[i] = new DevCardButton(new ImageIcon(monopoly.getScaledInstance(
						(int)((w/h)*Constants.TAB_PANEL_MENU_SIZE.getHeight()*3/5),
						(int)Constants.TAB_PANEL_MENU_SIZE.getHeight()*3/5, Image.SCALE_DEFAULT)));
				_buttons[i].setText("Monopoly Card(s): 0");
				_buttons[i].addActionListener(new MonoList());
				break;
			case 4:
				_buttons[i] = new DevCardButton(new ImageIcon(victoryPoint.getScaledInstance(
						(int)((wVP/hVP)*Constants.TAB_PANEL_MENU_SIZE.getHeight()*3/5),
						(int)Constants.TAB_PANEL_MENU_SIZE.getHeight()*3/5, Image.SCALE_DEFAULT)));
				_buttons[i].setText("Victory Point Card(s): 0");
				_buttons[i].addActionListener(new VPList());
				break;
			}
			_buttons[i].setForeground(Color.LIGHT_GRAY);
			_buttons[i].setHorizontalTextPosition(SwingConstants.CENTER);
			_buttons[i].setVerticalTextPosition(SwingConstants.TOP);
			_buttons[i].setEnabled(false);
			this.add(_buttons[i]);
		}
	}

	/**
	 * Paints the panel
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Image background = felt;
		int iw = background.getWidth(this);
		int ih = background.getHeight(this);
		if (iw > 0 && ih > 0) {
			for (int x = 0; x < getWidth(); x += iw) {
				for (int y = 0; y < getHeight(); y += ih) {
					g.drawImage(background, x, y, iw, ih, this);
				}
			}
		}
	}

	/**
	 * Updates GUI with latest info from server.
	 */
	@Override
	public void serverUpdate(){
		_cards = _cc.getPlayer().getDevCards();
		int[] newCards = _cc.getPlayer().getNewDevCards();
		if(_cc.getPlayer().isActive()){
			for(int i = 0; i < 5; i++){
				switch(i){
				case 0:
					_buttons[i].setText(String.format("Knight Card(s): %d", (_cards[i]+newCards[i])));
					break;
				case 1:
					_buttons[i].setText(String.format("Road Building Card(s): %d", (_cards[i]+newCards[i])));
					break;
				case 2:
					_buttons[i].setText(String.format("Year of Plenty Card(s): %d", (_cards[i]+newCards[i])));
					break;
				case 3:
					_buttons[i].setText(String.format("Monopoly Card(s): %d", (_cards[i]+newCards[i])));
					break;
				case 4:
					_buttons[i].setText(String.format("Victory Point Card(s): %d", (_cards[i]+newCards[i])));
					break;
				}
				if((_cards[i]==0)||(!_cc.getPlayer().isActive())||
						((!_cc.getPlayer().hasRolled())&&(i!=0))){
					_buttons[i].setEnabled(false);
				}
				else{
					_buttons[i].setEnabled(true);
				}
			}
			this.repaint();
		}
		else{
			for(JButton b: _buttons){
				b.setEnabled(false);
			}
		}
	}

	/**
	 * The Class KnightList is Listener for Knight cards.
	 */
	private class KnightList implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				_cc.sendMove(new DevCardMove(_cc.getPlayerName(), 0));
			} catch (IllegalArgumentException | IOException e1) {
				e1.printStackTrace();
			}
			System.out.println("KnightList setSelect(0)");
			_dp.setSelect(0);
		}
	}

	/**
	 * The Class VPList.
	 */
	private class VPList implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				_cc.sendMove(new DevCardMove(_cc.getPlayerName(), 4));
				_cc.sendMove(new VictoryPointMove(_cc.getPlayerName()));
			} catch (IllegalArgumentException | IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * The Class RBList.
	 */
	private class RBList implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				_cc.sendMove(new DevCardMove(_cc.getPlayerName(), 1));
			} catch (IllegalArgumentException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_dp.setSelect(4);
		}
	}

	/**
	 * The Class YoPList.
	 */
	private class YoPList implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				_cc.sendMove(new DevCardMove(_cc.getPlayerName(), 2));
			} catch (IllegalArgumentException | IOException e1) {
				e1.printStackTrace();
			}
			new YoPFrame(_cc);
		}
	}

	/**
	 * The Class MonoList is Listener for Monopoly Cards
	 */
	private class MonoList implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				_cc.sendMove(new DevCardMove(_cc.getPlayerName(), 3));
			} catch (IllegalArgumentException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			new MonoFrame(_cc);
		}

	}

	/**
	 * Requests focus.
	 */
	@Override 
	public void requestFocus() {
		if(_buttons[0].isEnabled())
			_buttons[0].requestFocus();
		else if(_buttons[1].isEnabled())
			_buttons[1].requestFocus();
		else if(_buttons[2].isEnabled())
			_buttons[2].requestFocus();
		else if(_buttons[3].isEnabled())
			_buttons[3].requestFocus();
		else if(_buttons[4].isEnabled())
			_buttons[4].requestFocus();
	}
}
