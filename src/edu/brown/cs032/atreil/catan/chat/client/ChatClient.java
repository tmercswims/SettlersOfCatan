package edu.brown.cs032.atreil.catan.chat.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.brown.cs032.tmercuri.catan.logic.Player;

/**
 * This class handles receiving messages from other players and sending messages
 * to other players
 * @author Alex Treil
 *
 */
public class ChatClient extends JPanel{

	private Socket _socket;
	private BufferedReader _in;
	private PrintWriter _out;
	private boolean _running;
	private Thread _thread;
	private JTextField _field;
	private JTextArea _area;
	private JButton _send;
	
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
		super();
		Dimension d = new Dimension(250,770);
		this.setPreferredSize(d);
		_socket = new Socket(hostname, port);
		
		//set up streams
		_in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
		_out = new PrintWriter(_socket.getOutputStream(), true);
		
		_out.println(player.getName());
		
		_field = new JTextField();
		_area = new JTextArea();
		_send = new JButton("Send");
		_send.addActionListener(new SendListener());
		this.add(_area);
		this.add(_field);
		this.add(_send);
		this.setVisible(true);
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
	public void kill() throws IOException{
		_in.close();
		_out.close();
		_socket.close();
	}
	
	private void run()
	{
		// Listen for any commandline input; quit on "exit" or emptyline
		_thread = new ReceiveThread();
		_thread.start();
		_running = true;
		//Scanner in = new Scanner(System.in);
		//BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		while(_running){
			//String line = in.nextLine();
			String line;
			try {
				line = readLine();
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
			
		}
		try {
			//in.close();
			this.kill();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		//TODO: Set up the code, so inputs by the client will be sent to the server
	}
	
	class SendListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String message = _field.getText();
			_field.setText("");
			println(message);
		}
		
	}
	
	class ReceiveThread extends Thread {
        public void run() {
        	//TODO: Receive all the messages sent by the socket and display it
        	//to the client.
        	while(_running){
        		try {
        			readLine();
					//System.out.println(_input.readLine());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
    }
}
