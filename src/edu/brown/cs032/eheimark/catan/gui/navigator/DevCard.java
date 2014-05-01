package edu.brown.cs032.eheimark.catan.gui.navigator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Constants;
import edu.brown.cs032.eheimark.catan.gui.Update;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Background.felt;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.DevCard.*;
import edu.brown.cs032.sbreslow.catan.gui.board.DrawingPanel;
import edu.brown.cs032.sbreslow.catan.gui.devCards.MonoFrame;
import edu.brown.cs032.sbreslow.catan.gui.devCards.YoPFrame;
import edu.brown.cs032.tmercuri.catan.logic.move.DevCardMove;
import edu.brown.cs032.tmercuri.catan.logic.move.VictoryPointMove;

public class DevCard extends JPanel implements Update{
	
	private int[] _cards = {0,0,0,0,0};
	/**
	 * knight = 0;
	 * roadbuilder = 1;
	 * year of plenty = 2;
	 * monopoly = 3;
	 * vp = 4;
	 */
	//private JLabel _vp, _knight, _rb, _mono, _yop;
	private CatanClient _cc;
	private DrawingPanel _dp;
	private DevCardButton[] _buttons = new DevCardButton[5];
	//button.setIcon(imageicon)
	public DevCard(CatanClient cc, DrawingPanel dp){
		super();
		_cc = cc;
		_dp = dp;
		setMinimumSize(Constants.TAB_PANEL_MENU_SIZE);
		setPreferredSize(Constants.TAB_PANEL_MENU_SIZE);
		setMaximumSize(Constants.TAB_PANEL_MENU_SIZE);
		GridLayout gl = new GridLayout(1,5, 5, 5);
		this.setLayout(gl);
		/*_vp = new JLabel("Victory Point Card(s): 0");
		_knight = new JLabel("Knight Card(s): 0");
		_rb = new JLabel("Road Building Card(s): 0");
		_mono = new JLabel("Monopoly Card(s): 0");
		_yop = new JLabel("Year of Plenty Card(s): 0");
		this.add(_knight);
		this.add(_rb);
		this.add(_yop);
		this.add(_mono);
		this.add(_vp);*/
		System.out.println(w + " " +h);
		for(int i = 0; i < 5; i++){
			switch(i){
			case 0:
				_buttons[i] = new DevCardButton(new ImageIcon(knight.getScaledInstance(
						(int)((w/h)*Constants.TAB_PANEL_MENU_SIZE.getHeight()*3/5), 
						(int)Constants.TAB_PANEL_MENU_SIZE.getHeight()*3/5, Image.SCALE_DEFAULT)));
                //_buttons[i].setIcon(knight);
				_buttons[i].setText("Knight Card(s): 0");
				_buttons[i].addActionListener(new KnightList());
				break;
			case 1:
				_buttons[i] = new DevCardButton(new ImageIcon(roadBuilder.getScaledInstance(
						(int)((w/h)*Constants.TAB_PANEL_MENU_SIZE.getHeight()*3/5), 
						(int)Constants.TAB_PANEL_MENU_SIZE.getHeight()*3/5, Image.SCALE_DEFAULT)));
                //_buttons[i].setIcon(roadBuilder);
				_buttons[i].setText("Road Building Card(s): 0");
				_buttons[i].addActionListener(new RBList());
				break;
			case 2:
				_buttons[i] = new DevCardButton(new ImageIcon(yearOfPlenty.getScaledInstance(
						(int)((w/h)*Constants.TAB_PANEL_MENU_SIZE.getHeight()*3/5),
						(int)Constants.TAB_PANEL_MENU_SIZE.getHeight()*3/5, Image.SCALE_DEFAULT)));
                //_buttons[i].setIcon(yearOfPlenty);
				_buttons[i].setText("Year of Plenty Card(s): 0");
                _buttons[i].addActionListener(new YoPList());
				break;
			case 3:
				_buttons[i] = new DevCardButton(new ImageIcon(monopoly.getScaledInstance(
						(int)((w/h)*Constants.TAB_PANEL_MENU_SIZE.getHeight()*3/5),
						(int)Constants.TAB_PANEL_MENU_SIZE.getHeight()*3/5, Image.SCALE_DEFAULT)));
                //_buttons[i].setIcon(monopoly);
				_buttons[i].setText("Monopoly Card(s): 0");
				_buttons[i].addActionListener(new MonoList());
				break;
			case 4:
				_buttons[i] = new DevCardButton(new ImageIcon(victoryPoint.getScaledInstance(
						(int)((wVP/hVP)*Constants.TAB_PANEL_MENU_SIZE.getHeight()*3/5),
						(int)Constants.TAB_PANEL_MENU_SIZE.getHeight()*3/5, Image.SCALE_DEFAULT)));
                //_buttons[i].setIcon(victoryPoint);
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
		
		
		//add button listeners
		//this.update();
	}
	
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
	
	public void ericUpdate(){
		_cards = _cc.getPlayer().getDevCards();
		if(_cc.getPlayer().isActive()){
			for(int i = 0; i < 5; i++){
				switch(i){
				case 0:
					_buttons[i].setText("Knight Card(s): "+_cards[i]);
					break;
				case 1:
					_buttons[i].setText("Road Building Card(s): "+_cards[i]);
					break;
				case 2:
					_buttons[i].setText("Year of Plenty Card(s): "+_cards[i]);
					break;
				case 3:
					_buttons[i].setText("Monopoly Card(s): "+_cards[i]);
					break;
				case 4:
					_buttons[i].setText("Victory Point Card(s): "+_cards[i]);
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
	
	private class KnightList implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				_cc.sendMove(new DevCardMove(_cc.getPlayerName(), 0));
			} catch (IllegalArgumentException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_dp.setSelect(0);
		}
		
	}
	
	private class VPList implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				_cc.sendMove(new DevCardMove(_cc.getPlayerName(), 4));
				_cc.sendMove(new VictoryPointMove(_cc.getPlayerName()));
			} catch (IllegalArgumentException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//_cards[4]--;
			//_cc.getPlayer().incVictoryPoints();
		}
		
	}
	
	private class RBList implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				_cc.sendMove(new DevCardMove(_cc.getPlayerName(), 1));
			} catch (IllegalArgumentException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//_cards[1]--;
			_dp.setSelect(4);
		}
		
	}
	
	private class YoPList implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				_cc.sendMove(new DevCardMove(_cc.getPlayerName(), 2));
			} catch (IllegalArgumentException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			new YoPFrame(_cc);
		}
		
	}
	
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

}
