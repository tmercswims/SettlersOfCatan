package edu.brown.cs032.eheimark.catan.gui.chat;

import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Edge.blue;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Edge.orange;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Edge.red;
import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Edge.white;

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
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.jdesktop.xswingx.PromptSupport;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;

/**
 * The Class ChatPanel contains the server log panel and chat log panel
 */
public class ChatPanel extends JPanel {
	private static final long serialVersionUID = 8319006484243162853L;
	private final CatanClient _client;
	private final MyChatScrollPane _chatLog, _serverLog; // chat pane goes at bottom, server log at top
	private final JTextField _chatBoxField; // used for sending messages to other players

	// Attribute set
	SimpleAttributeSet _red;
	SimpleAttributeSet _blue;
	SimpleAttributeSet _orange;
	SimpleAttributeSet _server;
	SimpleAttributeSet _white;

	/** The _history for the chat panel. */
	private final LinkedList<String> _history;
	/** The _unsent contents in chat panel. */
	private String _unsentContents;
	private int _position;
	

	/**
	 * Instantiates a new chat panel.
	 *
	 * @param cc the cc
	 * @param size the fixed size
	 */
	public ChatPanel(CatanClient cc, Dimension size) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		_client = cc;
		this.setMaximumSize(size);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		Dimension d = new Dimension(400, 15);
		Dimension d2 = new Dimension(400, size.height / 2 - 15);
		Dimension d3 = new Dimension(400, size.height / 2);


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

	/**
	 * Adds the message.
	 *
	 * @param message the message
	 */
	public void addMessage(String message){
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

			if(attr.equals(_blue)){
				message = message.replaceAll("\\(blue\\)", "");
			} else if(attr.equals(_red)){
				message = message.replaceAll("\\(red\\)", "");
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

			if(attr.equals(_server)) { // dispatch to server log panel
				_serverLog.insertString(message, attr);
			}
			else { // dispatch to chat log panel
				_chatLog.insertString(message, attr);
			}
		}
	}

	/**
	 * Println.
	 *
	 * @param message the message
	 */
	private void println(String message){
		try {
			_client.sendMessage(message);
		} catch (IOException ex) {
			System.err.println(String.format("ERROR: %s", ex.getMessage()));
		}
	}

	/**
	 * Requests focus
	 */
	@Override 
	public void requestFocus() {
		_chatBoxField.requestFocus();
	}

	/**
	 * @return boolean indicating whether has focus
	 */
	@Override 
	public boolean hasFocus() {
		return _chatBoxField.hasFocus();
	}

	/**
	 * Pressed key up used for chat history.
	 */
	public void pressedKeyUp() {
		requestFocus();
		if (_position == -1) {
			_unsentContents = _chatBoxField.getText();
		}
		_position = (_position+1 > _history.size()-1) ? _position : _position+1;
		String textU = (_position == -1) ? _chatBoxField.getText() : _history.get(_position);
		_chatBoxField.setText(textU);
	}

	/**
	 * Pressed key down used for chat listener
	 */
	public void pressedKeyDown() {
		requestFocus();
		_position = (_position-1 < 0) ? -1 : _position-1;
		String textD = (_position == -1) ? _unsentContents : _history.get(_position);
		_chatBoxField.setText(textD);
	}

	/**
	 * Listener used for chat box messages.
	 */
	private class ChatListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
			if(e.getKeyChar() == '\n') {
				String message = _chatBoxField.getText();
				_chatBoxField.setText("");
				_history.addFirst(message);
				_unsentContents = "";
                _position = -1;
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
			}
		}
		@Override
		public void keyPressed(KeyEvent e) {}
		@Override
		public void keyReleased(KeyEvent e) {}
	}
}
