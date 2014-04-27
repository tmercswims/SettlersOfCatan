package edu.brown.cs032.eheimark.catan.gui.navigator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Update;
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
	private JLabel _vp, _knight, _rb, _mono, _yop;
	private CatanClient _cc;
	private DrawingPanel _dp;
	private DevCardButton[] _buttons = new DevCardButton[5];
	//button.setIcon(imageicon)
	public DevCard(CatanClient cc, DrawingPanel dp){
		super();
		_cc = cc;
		_dp = dp;
		this.setLayout(new GridLayout(2,5));
		_vp = new JLabel("Victory Point Card(s): 0");
		_knight = new JLabel("Knight Card(s): 0");
		_rb = new JLabel("Road Building Card(s): 0");
		_mono = new JLabel("Monopoly Card(s): 0");
		_yop = new JLabel("Year of Plenty Card(s): 0");
		this.add(_vp);
		this.add(_knight);
		this.add(_rb);
		this.add(_mono);
		this.add(_yop);
		for(int i = 0; i < 5; i++){
			switch(i){
			case 0:
				_buttons[i] = new DevCardButton(knight);
                //_buttons[i].setIcon(knight);
				_buttons[i].addActionListener(new KnightList());
				break;
			case 1:
				_buttons[i] = new DevCardButton(roadBuilder);
                //_buttons[i].setIcon(roadBuilder);
				_buttons[i].addActionListener(new RBList());
				break;
			case 2:
				_buttons[i] = new DevCardButton(yearOfPlenty);
                //_buttons[i].setIcon(yearOfPlenty);
                _buttons[i].addActionListener(new YoPList());
				break;
			case 3:
				_buttons[i] = new DevCardButton(monopoly);
                //_buttons[i].setIcon(monopoly);
				_buttons[i].addActionListener(new MonoList());
				break;
			case 4:
				_buttons[i] = new DevCardButton(victoryPoint);
                //_buttons[i].setIcon(victoryPoint);
				_buttons[i].addActionListener(new VPList());
				break;
			}
			_buttons[i].setEnabled(false);
			this.add(_buttons[i]);
		}
		//add button listeners
		//this.update();
	}
	
	public void ericUpdate(){
		_cards = _cc.getPlayer().getDevCards();
		if(_cc.getPlayer().isActive()){
			for(int i = 0; i < 5; i++){
				if(_cards[i]==0){
					switch(i){
					case 0:
						_knight.setText("Knight Card(s): "+_cards[i]);
						break;
					case 1:
						_rb.setText("Road Building Card(s): "+_cards[i]);
						break;
					case 2:
						_yop.setText("Year of Plenty Card(s): "+_cards[i]);
						break;
					case 3:
						_mono.setText("Monopoly Card(s): "+_cards[i]);
						break;
					case 4:
						_vp.setText("Victory Point Card(s): "+_cards[i]);
						break;
					}
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
