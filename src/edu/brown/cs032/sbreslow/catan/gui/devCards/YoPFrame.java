package edu.brown.cs032.sbreslow.catan.gui.devCards;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.tmercuri.catan.logic.move.YearOfPlentyMove;

public class YoPFrame extends JFrame {
	
	private CatanClient _cc;
	private JRadioButton[] _tbuttons = new JRadioButton[5];
	private JRadioButton[] _bbuttons = new JRadioButton[5];
	private final ButtonGroup _top;
	private final ButtonGroup _bot;
	
	public YoPFrame(CatanClient cc){
		super("Year Of Plenty");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_cc = cc;
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,5));
		_top = new ButtonGroup();
		_bot = new ButtonGroup();
		for(int i = 0; i < 5; i++){
			switch(i){
			case 0:
				_tbuttons[i] = new JRadioButton("Wheat");
				_bbuttons[i] = new JRadioButton("Wheat");
				break;
			case 1:
				_tbuttons[i] = new JRadioButton("Wool");
				_bbuttons[i] = new JRadioButton("Wool");
				break;
			case 2:
				_tbuttons[i] = new JRadioButton("Brick");
				_bbuttons[i] = new JRadioButton("Brick");
				break;
			case 3:
				_tbuttons[i] = new JRadioButton("Ore");
				_bbuttons[i] = new JRadioButton("Ore");
				break;
			case 4:
				_tbuttons[i] = new JRadioButton("Lumber");
				_bbuttons[i] = new JRadioButton("Lumber");
				break;
			}
			_top.add(_tbuttons[i]);
			_bot.add(_bbuttons[i]);
			panel.add(_tbuttons[i]);
		}
		panel.add(new JLabel(""));
		for(JRadioButton b: _bbuttons){
			panel.add(b);
		}
		JButton submit = new JButton("Submit");
		submit.addActionListener(new SubmitList(this));
		panel.add(submit);
		this.add(panel);
		this.setVisible(true);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.pack();
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
