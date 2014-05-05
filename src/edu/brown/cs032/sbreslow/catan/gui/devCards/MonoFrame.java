package edu.brown.cs032.sbreslow.catan.gui.devCards;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.GUIFrame;
import edu.brown.cs032.tmercuri.catan.logic.move.MonopolyMove;
import edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Misc;

public class MonoFrame extends JFrame implements ActionListener {

	private CatanClient _cc;
    private GUIFrame _frame;
	private JRadioButton[] _buttons = new JRadioButton[5];
	private JLabel[] _images = new JLabel[5];
	private final ButtonGroup _top;

	public MonoFrame(CatanClient cc, GUIFrame frame){
		//super(frame, "Monopoly", true);
		super("Monopoly");
		_cc = cc;
        _frame = frame;
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,5));
		_top = new ButtonGroup();
		//_bot = new ButtonGroup();
		for(int i = 0; i < 5; i++){
			switch(i){
			case 0:
				_buttons[i] = new JRadioButton("Wheat");
				_images[i] = new JLabel(Misc.wheatToken);
				//_bbuttons[i] = new JRadioButton("Wheat");
				break;
			case 1:
				_buttons[i] = new JRadioButton("Sheep");
				_images[i] = new JLabel(Misc.woolToken);
				//_bbuttons[i] = new JRadioButton("Wool");
				break;
			case 2:
				_buttons[i] = new JRadioButton("Brick");
				_images[i] = new JLabel(Misc.brickToken);
				//_bbuttons[i] = new JRadioButton("Brick");
				break;
			case 3:
				_buttons[i] = new JRadioButton("Ore");
				_images[i] = new JLabel(Misc.oreToken);
				//_bbuttons[i] = new JRadioButton("Ore");
				break;
			case 4:
				_buttons[i] = new JRadioButton("Wood");
				_images[i] = new JLabel(Misc.woodToken);
				//_bbuttons[i] = new JRadioButton("Lumber");
				break;
			}
			_buttons[i].setForeground(Color.white);
			_top.add(_buttons[i]);
			//_bot.add(_bbuttons[i]);
			panel.add(_images[i]);
		}
		for(JRadioButton b: _buttons){
			panel.add(b);
		}
		JButton submit = new JButton("Submit");
		submit.addActionListener(this);
		submit.setFocusable(false);
		//panel.add(submit);
		JPanel bp = new JPanel();
		bp.add(submit);
		BackgroundPanel mp = new BackgroundPanel();
		mp.setLayout(new BoxLayout(mp, BoxLayout.PAGE_AXIS));
		panel.setOpaque(false);
		mp.add(panel);
		bp.setOpaque(false);
		mp.add(bp);
		this.add(mp);
		//this.setUndecorated(true);
		//this.setDefaultLookAndFeelDecorated(true);
		//removeMinMaxClose(this);
		//this.setLocationRelativeTo(cc.getFrame());
		this.setVisible(true);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.pack();
		setMinimumSize(getSize());
		setMaximumSize(getSize());
		repaint();
		//System.out.println(getSize());
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
        this.setVisible(false);
        this.dispose();
    }
}
