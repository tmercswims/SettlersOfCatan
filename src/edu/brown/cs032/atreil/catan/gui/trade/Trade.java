package edu.brown.cs032.atreil.catan.gui.trade;

import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Background.felt;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import edu.brown.cs032.eheimark.catan.gui.Update;
import edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Misc;
import edu.brown.cs032.tmercuri.catan.logic.Player;
import edu.brown.cs032.tmercuri.catan.logic.ResourceConstants;
import edu.brown.cs032.tmercuri.catan.logic.move.TradeMove;

/**
 * This class will handle trading with other players. It will be placed within the tabbed panel
 * @author Alex Treil
 *
 */
public class Trade extends JPanel implements Update {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5085232055225262547L;
	private JComboBox<String> _toPlayerCB;
	private final CatanClient _client;
	private boolean _dragging; //whether or not a resource token is being dragged
	private boolean _canDraw; //whether or not we should draw the token
	private Image _token; //the resource token that is being dragged
	private int _type; //the type of the resource being dragged
	private int _x; //x location of _token
	private int _y; //y location of _token
	private TradeFloor _give;
	private TradeFloor _center;
	private TradeFloor _get;
	private boolean _canDrop; //can we drop the current resource
	private JButton _proposeButton; //button to send trade requests
	
	public Trade(CatanClient client){
		super();
		
		_client = client;
		_toPlayerCB = new JComboBox<String>();
		_dragging = false;
		_canDrop = false;
		_canDraw = false;
		
		initializeGUI();
	}
	
	/**
	 * Initializes the GUI by adding components and setting
	 * properties
	 */
	private void initializeGUI(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//setting up proposal bar
		initializeProposalBar();
		
		//setting up trading floor
		JPanel tradingFloor = new JPanel();
		tradingFloor.setLayout(new BoxLayout(tradingFloor, BoxLayout.X_AXIS));
		tradingFloor.setOpaque(false);
		
		_give = new TradeFloor("Give", new int[]{0,0,0,0,0});
		_give.setUpdateColorEnabled(true);
		_give.setOppositeColorEnabled(true);
		_center = new TradeFloor("Click to drag", new int[]{0,0,0,0,0});
		_center.setUpdateColorEnabled(true);
		_get = new TradeFloor("Get", new int[]{0,0,0,0,0});
		_get.setUpdateColorEnabled(true);
		
		//setting listeners
//		for(int i = 0; i < 5; i++){
//			TradeFloorMouseAdapter adapter = new TradeFloorMouseAdapter(i, this);
//			center.addMouseListener(adapter);
//			center.addMouseMotionListener(adapter);
//		}
		TradeMouseAdapter l = new TradeMouseAdapter(this, _center);
		addMouseListener(l);
		addMouseMotionListener(l);
		
		tradingFloor.add(_give);
		tradingFloor.add(_center);
		tradingFloor.add(_get);
		
		add(tradingFloor);
		
		
		//setting background image
		setOpaque(false);
	}
	
	/**
	 * Initializes the proposale bar
	 */
	private void initializeProposalBar(){
		
		//visual properties
		JPanel proposalBar = new JPanel();
		proposalBar.setLayout(new BoxLayout(proposalBar, BoxLayout.X_AXIS));
		JLabel to = new JLabel("To: ");
		to.setForeground(Color.white);
		proposalBar.add(to);
		proposalBar.add(_toPlayerCB);
		
		_proposeButton = new JButton("Propose");
		_proposeButton.addActionListener(new ProposeTradeListener());
		proposalBar.add(_proposeButton);
		proposalBar.setOpaque(false);
		
		add(proposalBar);
		
		//adding players
	}
	
	/**
	 * Sets the resource count in the center frame that represents
	 * the number of resources the player has.
	 * @param resourceCount
	 */
	public void setResourceCount(int[] resourceCount){
		_center.setResourceCount(resourceCount);
	}
	
	public int getTradeCenterWidth(){
		return _center.getWidth();
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Image background = felt;
		int iw = background.getWidth(this);
		int ih = background.getHeight(this);
		if (iw > 0 && ih > 0) {
			for (int x = 0; x < getWidth(); x += iw) {
				for (int y = 0; y < getHeight(); y += ih) {
					g.drawImage(background, x, y, iw, ih, this);
				}
			}
		}
	}
	
	@Override
	protected void paintChildren(Graphics g){
		super.paintChildren(g);
		
		if(_dragging && _canDraw){
			g.drawImage(_token, _x, _y, this);
			_canDraw = false;
		}
	}

	/**
	 * This class handles dragging resources between trading floors
	 * @author Alex Treil
	 *
	 */
	private class TradeMouseAdapter extends MouseAdapter{
		private final Trade _trade;
		private final TradeFloor _c;
		
		public TradeMouseAdapter(Trade trade, TradeFloor c){
			_trade = trade;
			_c = c;
		}
		
		@Override
		public void mousePressed(MouseEvent e){
			if(!_client.getPlayer().isActive())
				return;
			
			_dragging = true;
			_token = setImage(e.getLocationOnScreen());
			
			if(_token == null)
				_dragging = false;
		}
		
		@Override
		public void mouseDragged(MouseEvent e){
			if(!_client.getPlayer().isActive())
				return;
			
			if(_dragging && _center.getCount(_type) > 0){
				_canDrop = true;
				_canDraw = true;
				_x = e.getXOnScreen() - _trade.getLocationOnScreen().x - _token.getWidth(_trade)/2;
				_y = e.getYOnScreen() - _trade.getLocationOnScreen().y - _token.getHeight(_trade)/2;
				
				_trade.repaint();
			}
		}
		
		@Override
		public void mouseReleased(MouseEvent e){
			if(!_client.getPlayer().isActive())
				return;
			
			if(_dragging && _canDrop){
				_dragging = false;
				_canDrop = false;
				
				int x = e.getLocationOnScreen().x;
				
				if(x <= _c.getLocationOnScreen().x){
					if(_get.getCount(_type) > 0){
						_get.resetCount(_type);
						_center.resetCount(_type);
					}
					_give.incrementCount(_type);
					_center.decrementCount(_type);
				}
				else if(x >= _c.getLocationOnScreen().x + _c.getWidth()){
					if(_give.getCount(_type) > 0){
						_give.resetCount(_type);
						_center.resetCount(_type);
					}
					_get.incrementCount(_type);
					_center.incrementCount(_type);
				}
				
				_trade.repaint();
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e){
			if(!_client.getPlayer().isActive())
				return;
			
			//System.err.println(!_dragging);
			if(!_dragging || (_dragging && !_canDrop)){
				Point point = e.getLocationOnScreen();
				int type = getType(e.getLocationOnScreen());
				//System.err.println(type);
				if(type != -1){
					int dx = _trade.getWidth()/15;
					int x = (int) _trade.getLocationOnScreen().getX();
					
					int i;
					for(i = 0; i < 15; i++){
						if(point.x >= x && point.x <= x+dx)
							break;
						x+=dx;
					}
					
					decrementCount(i);
					_dragging = false;
				}
			}
		}
		
		/**
		 * Given an index out of 15 that represents a column in the trade panel,
		 * determines which resource was clicked and then decrements that resource count.
		 * @param i
		 */
		private void decrementCount(int i){
			if(i < 5){
				//give
				if(_give.getCount(i) > 0)
					_center.incrementCount(i);
				
				_give.decrementCount(i);
			} else if(i >= 5 && i < 10){
				//center
				i = i % 5;
				_give.resetCount(i);
				_center.resetCount(i);
				_get.resetCount(i);
			} else if(i >= 10 && i <15){
				//get
				i = i % 5;
				if(_get.getCount(i) > 0)
					_center.decrementCount(i);
				
				_get.decrementCount(i);
			} else
				throw new IllegalArgumentException(String.format("resetCount: invalid index: %s", i));
		}
		
		private Image setImage(Point point){
			point = new Point(point.x - _c.getX(), point.y - _c.getY());
			int dx = _trade.getTradeCenterWidth()/5;
			int x = (int) _trade.getLocationOnScreen().getX();
			
			for(int i = 0; i < 5; i++){
				if(point.x >= x && point.x <= x+dx){
					_type = i;
					return getImage(i);
				}
				x+=dx;
			}
			
			return null;
		}
		
		/**
		 * Given a point, returns the closest resource type
		 * @param point The point to check
		 * @return An int representing the resource type as specified by ResourceArray
		 */
		private int getType(Point point){
			//point = new Point(point.x - _c.getX(), point.y - _c.getY());
			int dx = _trade.getWidth()/15;
			int x = (int) _trade.getLocationOnScreen().getX();
			
			for(int i = 0; i < 15; i++){
				//System.err.println(String.format("x: %s, dx: %s, point.x: %s",x, dx, point.x));
				if(point.x >= x && point.x <= x+dx){
					//_type = i;
					return i;
				}
				x+=dx;
			}
			
			return -1;
		}
		
		/**
		 * Given a type, returns the image of the associated type
		 * @param type The type of the resource, as specified in ResourceArray
		 * @return the image of the resource
		 */
		private Image getImage(int type){
			if(type == ResourceConstants.ORE){
				return Misc.oreToken.getImage();
			} else if(type == ResourceConstants.WHEAT){
				return Misc.wheatToken.getImage();
			} else if(type == ResourceConstants.WOOD){
				return Misc.woodToken.getImage();
			} else if(type == ResourceConstants.SHEEP){
				return Misc.woolToken.getImage();
			} else if(type == ResourceConstants.BRICK){
				return Misc.brickToken.getImage();
			} else{
				throw new IllegalArgumentException(String.format("Invalid label. Got %s", type));
			}
		}
	}
	
	private class ProposeTradeListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			//check if we have client...
			if(_client == null)
				return;
			
			String proposingTo = (String) _toPlayerCB.getSelectedItem();
			
			if(proposingTo.equals("")){
				//TODO: no one to send a trade to
				return;
			}
			
			//set up trade
			int[] trade = new int[5];
			boolean get = false;
			boolean give = false;
			
			for(int i = 0; i < 5; i++){
				if(_get.getCount(i) != 0){
					trade[i] = _get.getCount(i);
					get = true;
				}
				else if(_give.getCount(i) != 0){
					trade[i] = -_give.getCount(i);
					give = true;
				}
				else
					trade[i] = 0;
			}
			
			//invalid trade
			if(!get && !give){
				//TODO: invalid trade
				return;
			}
			
			try {
				_client.sendMove(new TradeMove(_client.getName(), proposingTo, trade, -1));
			} catch (IllegalArgumentException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally{
				_give.resetCountAll();
			}
		}
		
	}
	
	private boolean updated = false;
	@Override
	public void ericUpdate() {
		// TODO Auto-generated method stub
		if(!updated) {
			//System.out.println("IN TRADE MENU UPDATE");
			Player[] players = _client.getPlayers();

			HashMap<Integer, Color> colors = new HashMap<Integer, Color>();
			int idx = 0;
			
			_center.setResourceCount(_client.getPlayer().getResources());

			_toPlayerCB.addItem("***MERCHANT***");
			colors.put(idx++, Color.WHITE);
			
			for(Player p : players) { // Adds all other players except current player to trade with
				if(!_client.getPlayerName().equals(p.getName())) {
					_toPlayerCB.addItem(p.getName());
					colors.put(idx++, p.getColor().brighter());
				}
			}
			_toPlayerCB.setRenderer(new MyComboBoxRenderer(colors));
		}
		_toPlayerCB.setSelectedItem("***MERCHANT***");
		_toPlayerCB.setBackground(Color.white);

		if((!_client.getPlayer().isActive())||(!_client.getPlayer().hasRolled())){
			_proposeButton.setEnabled(false);
		}
		else{
			_proposeButton.setEnabled(true);
		}
		updated = true;
	}
	
	class MyComboBoxRenderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = 1L;
		HashMap<Integer,Color> table;

		public MyComboBoxRenderer(HashMap<Integer,Color> table) {
			this.table = table;
			setOpaque(true);
		}

		@SuppressWarnings("rawtypes")
		public Component getListCellRendererComponent(JList jc,Object val,int idx,boolean isSelected,boolean cellHasFocus) {
			setText(val.toString());

			if(isSelected) {
				setBackground(Color.LIGHT_GRAY);
				_toPlayerCB.setBackground(table.get(idx));
			}
			else {
				setBackground(table.get(idx));
			}
			setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			return this;
		}
	}
	
}
