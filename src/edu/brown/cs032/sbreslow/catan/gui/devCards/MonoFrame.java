package edu.brown.cs032.sbreslow.catan.gui.devCards;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.GUIFrame;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Icon.icon;
import edu.brown.cs032.tmercuri.catan.logic.move.MonopolyMove;
import edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Misc;

public class MonoFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 8149638523473052072L;
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
		for(int i = 0; i < 5; i++){
			switch(i){
			case 0:
				_buttons[i] = new JRadioButton();
				_images[i] = new JLabel(Misc.wheatToken);
				break;
			case 1:
				_buttons[i] = new JRadioButton();
				_images[i] = new JLabel(Misc.woolToken);
				break;
			case 2:
				_buttons[i] = new JRadioButton();
				_images[i] = new JLabel(Misc.brickToken);
				break;
			case 3:
				_buttons[i] = new JRadioButton();
				_images[i] = new JLabel(Misc.oreToken);
				break;
			case 4:
				_buttons[i] = new JRadioButton();
				_images[i] = new JLabel(Misc.woodToken);
				break;
			}
            _buttons[i].setOpaque(false);
            _buttons[i].setHorizontalAlignment(SwingConstants.CENTER);
			_top.add(_buttons[i]);
			panel.add(_images[i]);
		}
		for(JRadioButton b: _buttons){
			panel.add(b);
		}
		JButton submit = new JButton("Submit");
		submit.addActionListener(this);
		submit.setFocusable(false);
		JPanel bp = new JPanel();
		bp.add(submit);
		BackgroundPanel mp = new BackgroundPanel();
		mp.setLayout(new BoxLayout(mp, BoxLayout.PAGE_AXIS));
		panel.setOpaque(false);
		mp.add(panel);
		bp.setOpaque(false);
		mp.add(bp);
		add(mp);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(_frame);
		pack();
		setMinimumSize(getSize());
		setMaximumSize(getSize());
		repaint();
        setIconImage(icon);
		setVisible(true);
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
            e1.printStackTrace();
        }
        setVisible(false);
        dispose();
    }
}
