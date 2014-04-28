package edu.brown.cs032.sbreslow.catan.gui.devCards;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.tmercuri.catan.logic.*;
import edu.brown.cs032.tmercuri.catan.logic.move.*;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RobberFrame extends JFrame {

	private List<Player> _players;
	private List<JButton> _buttons;
	private Player _cur;
	private int _index;
	private CatanClient _cc;
	
	public RobberFrame(List<Player> plist, int index, CatanClient cc) {
		super("Robber");
		_cc = cc;
		_index = index;
		_cur = cc.getPlayer();
		_players = plist;
		_buttons = new ArrayList<JButton>();
		JPanel list = new JPanel();
		list.setLayout(new BoxLayout(list, BoxLayout.PAGE_AXIS));
		list.add(new JLabel("Please Choose a Player to Steal a Random Resource From:"));
		for(Player p : _players){
			System.out.println(p.getName()+" ");
			if(!p.equals(_cur)){
				//JButton tmp = new JButton(p.getName());
				//tmp.addActionListener(new RobList(p.getName(), this));
				_buttons.add(new JButton(p.getName()));
				System.out.println("here");
			}
		}
		for(JButton b: _buttons){
			b.addActionListener(new RobList(b.getText(), this));
			list.add(b);
		}
		list.setVisible(true);
		this.add(list);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.pack();
	}
	
	private class RobList implements ActionListener {
		
		private String _name;
		private JFrame _frame;
		
		private RobList(String name, JFrame frame){
			_name = name;
			_frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			RobberMove rm = new RobberMove(_cur.getName(), _index, _name);
			try {
				_cc.sendMove(rm);
				_frame.setVisible(false);
				_frame.dispose();
			} catch (IllegalArgumentException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}


}
