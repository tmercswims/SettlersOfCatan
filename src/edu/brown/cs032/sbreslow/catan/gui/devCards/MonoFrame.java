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

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.tmercuri.catan.logic.move.MonopolyMove;
import edu.brown.cs032.tmercuri.catan.logic.move.YearOfPlentyMove;

public class MonoFrame extends JFrame {

	private CatanClient _cc;
	private JRadioButton[] _buttons = new JRadioButton[5];
	private final ButtonGroup _top;

	public MonoFrame(CatanClient cc){
		super();
		_cc = cc;
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,4));
		_top = new ButtonGroup();
		//_bot = new ButtonGroup();
		for(int i = 0; i < 5; i++){
			switch(i){
			case 0:
				_buttons[i] = new JRadioButton("Wheat");
				//_bbuttons[i] = new JRadioButton("Wheat");
				break;
			case 1:
				_buttons[i] = new JRadioButton("Wool");
				//_bbuttons[i] = new JRadioButton("Wool");
				break;
			case 2:
				_buttons[i] = new JRadioButton("Brick");
				//_bbuttons[i] = new JRadioButton("Brick");
				break;
			case 3:
				_buttons[i] = new JRadioButton("Ore");
				//_bbuttons[i] = new JRadioButton("Ore");
				break;
			case 4:
				_buttons[i] = new JRadioButton("Lumber");
				//_bbuttons[i] = new JRadioButton("Lumber");
				break;
			}
			_top.add(_buttons[i]);
			//_bot.add(_bbuttons[i]);
			panel.add(_buttons[i]);
		}
		/*for(JRadioButton b: _bbuttons){
			panel.add(b);
		}*/
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
			int index = 0;
			for(int i = 0; i < _buttons.length; i++){
				if(_buttons[i].isSelected()){
					index = i;
				}
			}
			try {
				_cc.sendMove(new MonopolyMove(_cc.getPlayerName(), index));
			} catch (IllegalArgumentException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_frame.setVisible(false);
			_frame.dispose();
		}

	}

}
