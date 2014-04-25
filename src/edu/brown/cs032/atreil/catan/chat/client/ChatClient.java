package edu.brown.cs032.atreil.catan.chat.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;

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

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.tmercuri.catan.logic.Player;

/**
 * This class handles receiving messages from other players and sending messages
 * to other players
 * @author Alex Treil
 *
 */
public class ChatClient {//extends JPanel{

	private Socket _socket;
	private BufferedReader _in;
	private PrintWriter _out;
	private boolean _running;
	private Thread _thread;
	private JTextField _field;
	private JTextPane _area;
	private JButton _send;
	public JPanel _panel;
	private JScrollPane _scroll;
	private CatanClient _client;
	SimpleAttributeSet _red;
	SimpleAttributeSet _blue;
	SimpleAttributeSet _orange;
	SimpleAttributeSet _server;
    private final LinkedList<String> _history;
    private int _position;
    private String _unsentContents;
	

	/**
	 * Creates a new ChatClient that will connect to a ChatServer. Upon creation,
	 * the constructor will attempt to connect to the ChatServer.
	 * @param hostname The host of the ChatServer
	 * @param port The port of the ChatServer
	 * @param player The Player that is associated with this client.
	 * @throws UnknownHostException If the host does not exist
	 * @throws IOException If something goes wrong with the IO
	 */
	public ChatClient(String hostname, int port, Player player) throws UnknownHostException, IOException{
		//super();
		_panel = new JPanel();
		Dimension d = new Dimension(250,770);
		//this.setPreferredSize(d);
		_panel.setPreferredSize(d);
		//_panel.setBorder(BorderFactory.createLineBorder(Color.black));
		_socket = new Socket(hostname, port);

		//set up streams
		_in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
		_out = new PrintWriter(_socket.getOutputStream(), true);

		_out.println(player.getName());
        
        _history = new LinkedList<>();
        _position = -1;

        /*JScrollPane scroller = new JScrollPane(_area);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);*/
        
		_field = new JTextField(21);
		_field.addKeyListener(new ChatListener());
		_area = new JTextPane();
		Dimension size = new Dimension(230,580);
		_area.setMaximumSize(size);
		_area.setMinimumSize(size);
		_area.setPreferredSize(size);
        DefaultCaret caret = (DefaultCaret)_area.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        _area.setCaret(caret);
		_area.setEditable(false);
		//_area.setLineWrap(true);
        //_area.setWrapStyleWord(true);
        Border bl = BorderFactory.createLineBorder(Color.black);
        TitledBorder b = BorderFactory.createTitledBorder(bl,"Chat/Log");
        b.setTitleJustification(TitledBorder.CENTER);
        _panel.setBorder(b);
        _area.setBorder(bl);
        //_area.setBorder(b);
        //_area.setMaximumSize(d);
        //_area.setMinimumSize(d);
		_send = new JButton("Send");
		_send.addActionListener(new SendListener());
		
		_scroll = new JScrollPane(_area);//, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				//JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//Dimension scroll = new Dimension(d);
		_scroll.setPreferredSize(size);
		//_scroll.setSize(size);
		
		//_panel.add(_area);
		_panel.add(_scroll);
		_panel.add(_field);
		//_panel.add(_send);
		_panel.setVisible(true);
		
		
		_red = new SimpleAttributeSet();
		StyleConstants.setFontFamily(_red, "Helvetica");
		StyleConstants.setForeground(_red, Color.red);
		
		_blue = new SimpleAttributeSet();
		StyleConstants.setFontFamily(_blue, "Helvetica");
		StyleConstants.setForeground(_blue, Color.blue);
		
		_orange = new SimpleAttributeSet();
		StyleConstants.setFontFamily(_orange, "Helvetica");
		StyleConstants.setForeground(_orange, Color.orange);
		
		_server = new SimpleAttributeSet();
		StyleConstants.setFontFamily(_server, "Helvetica");
		StyleConstants.setForeground(_server, Color.gray);
		StyleConstants.setBold(_server, true);
		
		_field.requestFocus();
		run();
	}

	/**
	 * Reads a line from the chat server
	 * @return Line from the chat server
	 * @throws IOException If anything goes wrong with the IO
	 */
	public String readLine() throws IOException{
		return _in.readLine();
	}

	/**
	 * Sends a message to the chat
	 * @param message The message to send
	 */
	public void println(String message){
		_out.println(message);
	}

	/**
	 * Closes down associated streams and sockets
	 * @throws IOException If anything goes wrong with the IO
	 */
	public void kill(){
		try {
			_running = false;
			_in.close();
			_out.close();
			_socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void run()
	{
		// Listen for any commandline input; quit on "exit" or emptyline
		_thread = new ReceiveThread();
		_thread.start();
		_running = true;
		//Scanner in = new Scanner(System.in);
		//BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		/*while(_running){
			//String line = in.nextLine();
			try {
				String line = readLine();
				if(line.length()==0)
				{
					System.out.println("QUITTING CLIENT...");
					break;
				}
				else{
					_area.append(line);
					_area.append("\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("ECHO: "+line);

		}*/
		/*try {
			//in.close();
			//this.kill();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//TODO: Set up the code, so inputs by the client will be sent to the server
	}
	
	private class ChatListener implements KeyListener {
		//red blue orange white
		@Override
		public void keyTyped(KeyEvent e) {
			//System.out.println("getKeyChar "+e.getKeyChar());
			if(e.getKeyChar() == '\n') {
				String message = _field.getText();
				_field.setText("");
                _history.addFirst(message);
                _unsentContents = "";
				if(_client.getPlayer().getColor().equals(Color.red)){
					println("red "+message);
				}
				else if(_client.getPlayer().getColor().equals(Color.blue)){
					println("blue "+message);
				}
				else if(_client.getPlayer().getColor().equals(Color.orange)){
					println("orange "+message);
				}
				else if(_client.getPlayer().getColor().equals(Color.white)){
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
			if (e.getKeyCode() == KeyEvent.VK_UP) {
                _unsentContents = _field.getText();
                _position = (_position+1 > _history.size()) ? _position : _position+1;
                String text = _history.get(_position);
                _field.setText(text);
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                _position = (_position-1 < 0) ? -1 : _position-1;
                String text = (_position == -1) ? _unsentContents : _history.get(_position);
                _field.setText(text);
            }
		}
		
	}

	private class SendListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String message = _field.getText();
			_field.setText("");
			println(message);
		}

	}

	private class ReceiveThread extends Thread {
		public void run() {
			//TODO: Receive all the messages sent by the socket and display it
			//to the client.
			Queue<String> lines = new LinkedList<String>();
			while(_running){
				try {
					String line = _in.readLine();
					//_area.setText(line+"\n"+_area.getText());
					SimpleAttributeSet attr = new SimpleAttributeSet();
					String color = line.split(" ")[1];
					color = color.substring(1,color.length()-2);
					System.out.println(color);
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
						//System.out.println("COLOR IS WRONG: "+color);
					}
					//System.out.println(line);
					if(line.split(" ")[2].equals("*whisper*")){
						//System.out.println("here");
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
						lines.add(line);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//_area.append(line);
					//_area.append("\n");
					//if(_area.getLineCount()>_area.getRows()){
						/*int offset = 0;
						for(int i = 0; i < (_area.getLineCount()-_area.getRows())-1; i++){
							try {
								offset = _area.getLineEndOffset(i);
							} catch (BadLocationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						_area.setText(_area.getText().substring(offset,_area.getText().length()));*/
					//}
					//readLine();
					//System.out.println(_input.readLine());
				} catch (SocketException e){
					_running = false;
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					_running = false;
				} catch (NullPointerException e){
					//should quit
					_running = false;
				}
			}
			
			kill();
		}
	}

	public void setClient(CatanClient client) {
		_client = client;
	}
	
}
