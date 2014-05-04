package edu.brown.cs032.sbreslow.catan.gui.devCards;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Misc;
import edu.brown.cs032.tmercuri.catan.logic.move.YearOfPlentyMove;

public class YoPFrame extends JFrame {
	
	private CatanClient _cc;
	private JRadioButton[] _tbuttons = new JRadioButton[5];
	private JRadioButton[] _bbuttons = new JRadioButton[5];
	private JLabel[] _images = new JLabel[5];
	private final ButtonGroup _top;
	private final ButtonGroup _bot;
	
	public YoPFrame(CatanClient cc){
		super("Year Of Plenty");
		_cc = cc;
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,5));
		_top = new ButtonGroup();
		_bot = new ButtonGroup();
		for(int i = 0; i < 5; i++){
			switch(i){
			case 0:
				_tbuttons[i] = new JRadioButton();
				_bbuttons[i] = new JRadioButton();
				_images[i] = new JLabel(Misc.wheatToken);
				break;
			case 1:
				_tbuttons[i] = new JRadioButton();
				_bbuttons[i] = new JRadioButton();
				_images[i] = new JLabel(Misc.woolToken);
				break;
			case 2:
				_tbuttons[i] = new JRadioButton();
				_bbuttons[i] = new JRadioButton();
				_images[i] = new JLabel(Misc.brickToken);
				break;
			case 3:
				_tbuttons[i] = new JRadioButton();
				_bbuttons[i] = new JRadioButton();
				_images[i] = new JLabel(Misc.oreToken);
				break;
			case 4:
				_tbuttons[i] = new JRadioButton();
				_bbuttons[i] = new JRadioButton();
				_images[i] = new JLabel(Misc.woodToken);
				break;
			}
			_tbuttons[i].setHorizontalAlignment(SwingConstants.CENTER);
			_bbuttons[i].setHorizontalAlignment(SwingConstants.CENTER);
			_tbuttons[i].setOpaque(false);
			_bbuttons[i].setOpaque(false);
			_top.add(_tbuttons[i]);
			_bot.add(_bbuttons[i]);
			panel.add(_images[i]);
		}
		//panel.add(new JLabel(""));
		for(JRadioButton b: _tbuttons){
			b.setForeground(Color.white);
			panel.add(b);
		}
		for(JRadioButton b: _bbuttons){
			b.setForeground(Color.white);
			panel.add(b);
		}
		JButton submit = new JButton("Submit");
		submit.addActionListener(new SubmitList(this));
		submit.setFocusable(false);
		JPanel bp = new JPanel();
		bp.add(submit);
		JPanel mp = new BackgroundPanel();
		mp.setLayout(new BoxLayout(mp, BoxLayout.PAGE_AXIS));
		panel.setOpaque(false);
		bp.setOpaque(false);
		mp.add(panel);
		mp.add(bp);
		this.add(mp);
		//this.setLocationRelativeTo(cc.getFrame());
		this.setVisible(true);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.pack();
		//this.setUndecorated(true);
	}
	
	private class SubmitList implements ActionListener{
		
		private JFrame _frame;
		
		private SubmitList(JFrame frame){
			_frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int tcount = 0;
			int bcount = 0;
			for(JRadioButton b: _tbuttons){
				if(b.isSelected()){
					tcount++;
				}
			}
			for(JRadioButton b: _bbuttons){
				if(b.isSelected()){
					bcount++;
				}
			}
			if(tcount==1 && bcount==1){
				int tdex = 0;
				int bdex = 0;
				for(int i = 0; i < 5; i++){
					if(_tbuttons[i].isSelected()){
						tdex = i;
					}
					if(_bbuttons[i].isSelected()){
						bdex = i;
					}
				}
				try {
					_cc.sendMove(new YearOfPlentyMove(_cc.getPlayerName(), tdex, bdex));
				} catch (IllegalArgumentException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				_frame.setVisible(false);
				_frame.dispose();
			}
		}
		
	}
	
}
