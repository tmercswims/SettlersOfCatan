package edu.brown.cs032.atreil.catan.gui.trade;

import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Dimensions.TABBED_MENU_SIZE;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.GUIFrame;
import edu.brown.cs032.sbreslow.catan.gui.devCards.BackgroundPanel;
import edu.brown.cs032.tmercuri.catan.logic.move.TradeMove;

import javax.swing.JDialog;

/**
 * This class contains all of the information about an incoming trade. The frame
 * acts as a pop up that will appear on the receiving players window
 * @author Alex Treil
 *
 */
public class TradeFrame extends JFrame {

	private TradeFloor _give; //displays what user is giving up
	private TradeFloor _afterTrade; //displays what the result of the trade will be
	private TradeFloor _get; //displays what the user is getting
	
	private TradeMove _trade; //contains information about the trade
	private CatanClient _client;
	/**
	 * 
	 */
	private static final long serialVersionUID = 7178921858469946687L;

	public TradeFrame(String frameName, TradeMove trade, CatanClient client, GUIFrame frame){
		//super(frame, frameName, true);
		super(frameName);
		_trade = trade;
		_client = client;
		
		initializeGUI();
	}
	
	/**
	 * Initializes the GUI by adding components
	 * and setting the appropriate properties
	 */
	private void initializeGUI(){
		JPanel mainPanel = new BackgroundPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		
		//setting up resource counts
		int[] tradeResource = _trade.getResources();
		int[] getResource = new int[]{0,0,0,0,0};
		int[] giveResource = new int[]{0,0,0,0,0};
		int[] afterTrade = _client.getPlayer().getResources();
		
		
		_give = new TradeFloor("Give", giveResource);
		_give.setUpdateColorEnabled(true);
		_give.setOppositeColorEnabled(true);
		_get = new TradeFloor("Get", getResource);
		_get.setUpdateColorEnabled(true);
		
		for(int i = 0; i < 5; i++){
			int count = tradeResource[i];
			
			if(count < 0){
				getResource[i] = -count;
			
				//do this to get color changes
				for(int j = 0; j < (-count); j++){
					_get.incrementCount(i);
				}
			}
			else if(count > 0){
				giveResource[i] = count;
				
				//do this to get color changes
				for(int j = 0; j < count; j++){
					_give.incrementCount(i);
				}
			}
		}
		
		//setting up after trade frame
		_afterTrade = new TradeFloor("After Trade", afterTrade);
		_afterTrade.setUpdateColorEnabled(true);
		
		//getting resources
		for(int i = 0; i < 5; i++){
			int count = getResource[i];
			
			for(int j = 0; j < count; j++)
				_afterTrade.incrementCount(i);
		}
		
		//giving resources
		for(int i = 0; i < 5; i++){
			int count = giveResource[i];
			
			for(int j = 0; j < count; j++)
				_afterTrade.decrementCount(i);
		}
		
		
		//setting up after trade look
		JPanel afterTradePanel = new JPanel();
		afterTradePanel.setLayout(new BoxLayout(afterTradePanel, BoxLayout.Y_AXIS));
		afterTradePanel.setOpaque(false);
		
		//houses accept and reject buttons
		JPanel acceptReject = new JPanel();
		acceptReject.setLayout(new BoxLayout(acceptReject, BoxLayout.X_AXIS));
		acceptReject.setOpaque(false);
		JButton accept = new JButton("Accept");
		accept.addActionListener(new ResponseListener(true));
		JButton reject = new JButton("Reject");
		reject.addActionListener(new ResponseListener(false));
		acceptReject.add(accept);
		acceptReject.add(reject);
		
		afterTradePanel.add(_afterTrade);
		afterTradePanel.add(acceptReject);
		
		
		//adding in resource count
		mainPanel.add(_give);
		mainPanel.add(afterTradePanel);
		mainPanel.add(_get);
		
		add(mainPanel);
		pack();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		
		setMinimumSize(TABBED_MENU_SIZE);
		pack();
		setLocationRelativeTo(_client.getFrame());
		setVisible(true);
		setResizable(false);
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	/**
	 * Method for responding to trades
	 * @author Alex Treil
	 *
	 */
	private class ResponseListener implements ActionListener{

		private boolean _accept; //whether the client is accepting or rejecting the trade
		
		public ResponseListener(boolean accept){
			_accept = accept;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(_accept)
				_trade.setType(1);
			else
				_trade.setType(0);
			
			try {
				_client.sendMove(_trade);
			} catch (IllegalArgumentException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				close();
			}
		}
		
	}
	
	/**
	 * Close the frame and dispose properly.
	 */
	public void close() {
		setVisible(false);
		dispose();
	}
}
