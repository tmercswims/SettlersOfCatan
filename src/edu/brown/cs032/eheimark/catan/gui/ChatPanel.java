package edu.brown.cs032.eheimark.catan.gui;

import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Edge.blue;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Edge.orange;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Edge.red;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Edge.white;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import edu.brown.cs032.atreil.catan.networking.Packet;
import edu.brown.cs032.atreil.catan.networking.client.CatanClient;

public class ChatPanel extends JPanel {
	
	private JTextField _field;
	private JTextPane _area;
	public JPanel _panel;
	private JScrollPane _scroll;
	private CatanClient _client;
	SimpleAttributeSet _red;
	SimpleAttributeSet _blue;
	SimpleAttributeSet _orange;
	SimpleAttributeSet _server;
	SimpleAttributeSet _white;
	
	public ChatPanel(CatanClient cc){
		_client = cc;
		Dimension d = new Dimension(400,600);
		this.setPreferredSize(d);
		_field = new JTextField(30);
		_field.addKeyListener(new ChatListener());
		_area = new JTextPane();
		Dimension size = new Dimension(380,540);
		_area.setBackground(Color.black);
		_area.setMaximumSize(size);
		_area.setPreferredSize(size);
        DefaultCaret caret = (DefaultCaret)_area.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        _area.setCaret(caret);
		_area.setEditable(false);
        Border bl = BorderFactory.createLineBorder(Color.black);
        TitledBorder b = BorderFactory.createTitledBorder(bl,"Chat/Log");
        b.setTitleJustification(TitledBorder.CENTER);
        this.setBorder(b);
        _area.setBorder(bl);
		_scroll = new JScrollPane(_area);
		_scroll.setPreferredSize(size);
		this.add(_scroll);
		this.add(_field);
	}
	
	public void addMessage(String message){
		System.out.println(message);
		String line = message.trim();
		SimpleAttributeSet attr = new SimpleAttributeSet();
		String[] linearray = line.split(" ");
		String color = linearray[1];
		color = color.substring(1,color.length()-2);
		System.out.println("COLOR " + color);
		if(color.equalsIgnoreCase("red")){
			attr = _red;
		}
		else if(color.equalsIgnoreCase("blue")){
			attr = _blue;
		}
		else if(color.equalsIgnoreCase("orange")){
			attr = _orange;
		}
		else if(color.equalsIgnoreCase("server")){
			attr = _server;
		}
		else{
			attr = _white;
		}
		if(linearray.length>=3){
			if(linearray[2].equals("*whisper*")){
				StyleConstants.setFontFamily(attr, "Monaco");
				StyleConstants.setItalic(attr, true);
			}
			try {
				String[] tmp = line.split(" ");
				StringBuilder sb = new StringBuilder();
				for(int i = 0; i < tmp.length; i++){
					if(i!=1){
						sb.append(tmp[i]+" ");
					}
					else{
						sb = new StringBuilder(sb.toString().trim());
						sb.append(tmp[i].charAt(tmp[i].length()-1));
					}
				}
				_area.getDocument().insertString(_area.getCaretPosition(),sb.toString().trim()+"\n",attr);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void println(String message){
		try {
			_client.sendMessage(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private class ChatListener implements KeyListener {
		//red blue orange white
		@Override
		public void keyTyped(KeyEvent e) {
			//System.out.println("getKeyChar "+e.getKeyChar());
			if(e.getKeyChar() == '\n') {
				String message = _field.getText();
				_field.setText("");
                //_history.addFirst(message);
                //_unsentContents = "";
				if(_client.getPlayer().getColor().equals(red)){
					println("red "+message);
				}
				else if(_client.getPlayer().getColor().equals(blue)){
					println("blue "+message);
				}
				else if(_client.getPlayer().getColor().equals(orange)){
					println("orange "+message);
				}
				else if(_client.getPlayer().getColor().equals(white)){
					println("white "+message);
				}
				else{
					System.out.println(_client.getPlayer().getColor());
				}
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}

}
