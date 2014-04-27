package edu.brown.cs032.sbreslow.catan.gui.devCards;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;

public class YoPFrame extends JFrame {
	
	private CatanClient _cc;
	private JRadioButton[] _tbuttons;
	private JRadioButton[] _bbuttons;
	
	public YoPFrame(CatanClient cc){
		super();
		_cc = cc;
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,5));
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
			panel.add(_tbuttons[i]);
		}
		for(JRadioButton b: _bbuttons){
			panel.add(b);
		}
		JButton submit = new JButton("Submit");
		submit.addActionListener(new SubmitList());
	}
	
	private class SubmitList implements ActionListener{

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
		}
		
	}
	
}
