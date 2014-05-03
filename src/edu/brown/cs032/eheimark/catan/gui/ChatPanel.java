package edu.brown.cs032.eheimark.catan.gui;

import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Edge.blue;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Edge.orange;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Edge.red;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Edge.white;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.ParagraphView;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

import org.jdesktop.xswingx.PromptSupport;

import edu.brown.cs032.atreil.catan.chat.server.SemiTransparentPanel;
import edu.brown.cs032.atreil.catan.networking.client.CatanClient;

public class ChatPanel extends JPanel {

	private static final long serialVersionUID = 8319006484243162853L;

	private final JTextField _chatBoxField;
	private final CatanClient _client;
	SimpleAttributeSet _red;
	SimpleAttributeSet _blue;
	SimpleAttributeSet _orange;
	SimpleAttributeSet _server;
	SimpleAttributeSet _white;

	private final LinkedList<String> _history;
	private String _unsentContents;
	private int _position;
	private final MyChatScrollPane _chatLog, _serverLog;

	public ChatPanel(CatanClient cc, Dimension preferredSize) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		_client = cc;

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		Dimension d = new Dimension(400, 15);
		Dimension d2 = new Dimension(400, preferredSize.height / 2 - 15);
		Dimension d3 = new Dimension(400, preferredSize.height / 2);


		JPanel chatBoxPanel = new SemiTransparentPanel(), serverPanel = new SemiTransparentPanel();
		_chatBoxField = new JTextField();
		_chatBoxField.setMaximumSize(d);
		_chatBoxField.setMinimumSize(d);
		_chatBoxField.addKeyListener(new ChatListener());
		_chatBoxField.setOpaque(false);
		_chatBoxField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.darkGray), BorderFactory.createEmptyBorder(4, 4, 4, 4)));
		_chatBoxField.setForeground(Color.white);
		_chatBoxField.setCaretColor(Color.white);
		PromptSupport.setPrompt("Type message, /p <player> to whisper, or use arrows for chat history...", _chatBoxField);

		_serverLog = new MyChatScrollPane(d2);
		_chatLog = new MyChatScrollPane(d3);

		chatBoxPanel.setBackground(new Color(0f, 0f, 0f, .5f));
		chatBoxPanel.setLayout(new BoxLayout(chatBoxPanel, BoxLayout.PAGE_AXIS));
		serverPanel.setBackground(new Color(1f, 1f, 1f, .5f));
		serverPanel.setLayout(new BoxLayout(serverPanel, BoxLayout.PAGE_AXIS));
		chatBoxPanel.add(_chatLog.getScrollPane(), BorderLayout.CENTER);
		chatBoxPanel.add(_chatBoxField, BorderLayout.SOUTH);
		chatBoxPanel.setPreferredSize(d);
		serverPanel.add(_serverLog.getScrollPane());

		add(serverPanel);
		add(chatBoxPanel);

		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		_red = new SimpleAttributeSet();
		StyleConstants.setFontFamily(_red, "Helvetica");
		StyleConstants.setFontSize(_red, 14);
		StyleConstants.setForeground(_red, red.brighter());

		_blue = new SimpleAttributeSet();
		StyleConstants.setFontFamily(_blue, "Helvetica");
		StyleConstants.setFontSize(_blue, 14);
		StyleConstants.setForeground(_blue, blue.brighter());

		_orange = new SimpleAttributeSet();
		StyleConstants.setFontFamily(_orange, "Helvetica");
		StyleConstants.setFontSize(_orange, 14);
		StyleConstants.setForeground(_orange, orange.brighter());

		_white = new SimpleAttributeSet();
		StyleConstants.setFontFamily(_white, "Helvetica");
		StyleConstants.setFontSize(_white, 14);
		StyleConstants.setForeground(_white, Color.white);

		_server = new SimpleAttributeSet();
		StyleConstants.setFontFamily(_server, "Helvetica");
		StyleConstants.setFontSize(_server, 14);
		StyleConstants.setForeground(_server, Color.black);
		StyleConstants.setBold(_server, true);

		_history = new LinkedList<>();
		_unsentContents = "";
		_position = -1;
	}

	class WrapEditorKit extends StyledEditorKit {
		private static final long serialVersionUID = 2347939257335358031L;
		ViewFactory defaultFactory=new WrapColumnFactory();
		@Override
		public ViewFactory getViewFactory() {
			return defaultFactory;
		}
	}

	class WrapColumnFactory implements ViewFactory {
		@Override
		public View create(Element elem) {
			String kind = elem.getName();
			if (kind != null) {
				switch (kind) {
				case AbstractDocument.ContentElementName:
					return new WrapLabelView(elem);
				case AbstractDocument.ParagraphElementName:
					return new ParagraphView(elem);
				case AbstractDocument.SectionElementName:
					return new BoxView(elem, View.Y_AXIS);
				case StyleConstants.ComponentElementName:
					return new ComponentView(elem);
				case StyleConstants.IconElementName:
					return new IconView(elem);
				}
			}
			// default to text display
			return new LabelView(elem);
		}
	}

	class WrapLabelView extends LabelView {
		public WrapLabelView(Element elem) {
			super(elem);
		}
		@Override
		public float getMinimumSpan(int axis) {
			switch (axis) {
			case View.X_AXIS:
				return 0;
			case View.Y_AXIS:
				return super.getMinimumSpan(axis);
			default:
				throw new IllegalArgumentException("Invalid axis: " + axis);
			}
		}
	}

	public void addMessage(String message){
		System.out.println("CHATPANEL " + message);
		String line = message.trim();
		final SimpleAttributeSet attr;
		String[] linearray = line.split(" ");
		String color = linearray[1];
		
		/**
		 * Extracting color
		 */
		Pattern pattern = Pattern.compile("\\((.*?)\\)");
		Matcher match = pattern.matcher(color);
		match.find();
		color = match.group(1);
		System.out.println("COLOR " + color);
		/**
		 * 
		 */
		
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
			
			StringBuilder sb = new StringBuilder();
			/*
			for(int i = 0; i < linearray.length; i++){
				if(linearray[i].equalsIgnoreCase("server")){
					linearray[0] = "";
					linearray[1] = " ";
				}
				if(i!=1){
					sb.append(" " + linearray[i]);
				}
				else{
					sb = new StringBuilder(sb.toString().trim());
					sb.append(linearray[i].charAt(linearray[i].length()-1));
				}
			}
			*/
						
			if(attr.equals(_blue)){
				message = message.replaceAll("\\(blue\\)", "");
			} else if(attr.equals(_red)){
				
			} else if(attr.equals(_orange)){
				message = message.replaceAll("\\(orange\\)", "");
			} else if(attr.equals(_white)){
				message = message.replaceAll("\\(white\\)", "");
			} else if(attr.equals(_server)){
				message = message.replaceAll("\\(server\\):", "");
				message = message.replaceAll("\\(Server\\):", "");
				message = message.replaceAll("server", "");
				message = message.replaceAll("Server", "");
			}

			if(attr.equals(_server)) {
				_serverLog.insertString(message, attr);
			}
			else {
				_chatLog.insertString(message, attr);
			}
		}
	}

	private void println(String message){
		try {
			_client.sendMessage(message);
		} catch (IOException ex) {
			System.out.println(String.format("ERROR: %s", ex.getMessage()));
		}
	}

	@Override 
	public void requestFocus() {
		_chatBoxField.requestFocus();
	}

	@Override 
	public boolean hasFocus() {
		return _chatBoxField.hasFocus();
	}

	public void pressedKeyUp() {
		requestFocus();
		if (_position == -1) {
			_unsentContents = _chatBoxField.getText();
		}
		_position = (_position+1 > _history.size()-1) ? _position : _position+1;
		String textU = (_position == -1) ? _chatBoxField.getText() : _history.get(_position);
		_chatBoxField.setText(textU);
	}

	public void pressedKeyDown() {
		requestFocus();
		_position = (_position-1 < 0) ? -1 : _position-1;
		String textD = (_position == -1) ? _unsentContents : _history.get(_position);
		_chatBoxField.setText(textD);
	}

	private class ChatListener implements KeyListener {
		//red blue orange white
		@Override
		public void keyTyped(KeyEvent e) {
			//System.out.println("getKeyChar "+e.getKeyChar());
			if(e.getKeyChar() == '\n') {
				String message = _chatBoxField.getText();
				_chatBoxField.setText("");
				_history.addFirst(message);
				_unsentContents = "";
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
		public void keyPressed(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {}
	}

}
