package edu.brown.cs032.atreil.catan.gui.trade;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

import edu.brown.cs032.eheimark.catan.gui.Update;
import edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Misc;
import edu.brown.cs032.tmercuri.catan.logic.ResourceConstants;

/**
 * This class contains an array of resource icons that can be updated.
 * @author Alex Treil
 *
 */
class ResourceArray extends JPanel implements Update {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7124397318680691184L;
	/*
	 * Labels for the individual tokens
	 */
	private JLabel _oreLabel;
	private JLabel _wheatLabel;
	private JLabel _woodLabel;
	private JLabel _sheepLabel;
	private JLabel _brickLabel;
	
	/*
	 * Counts for each label that appears underneath each label 
	 */
	private JLabel _oreCount;
	private JLabel _wheatCount;
	private JLabel _woodCount;
	private JLabel _sheepCount;
	private JLabel _brickCount;
	
	/*
	 * Iterator for the labels
	 */
	private JLabel[] _resourceCountArray;
	private JLabel[] _resourceTokenArray;
	
	private final int[] _resourceCount; //keeps track of how much of each resource is collected
	private final int[] _originalResourceCount; //keeps track of how much was originally inputed
	private boolean _updateColor; //determines if we should change the color of the text after incrementing/decrementing
	private boolean _oppositeColor; //determines if we use normal color schemes or reverse
	private Color _increment; //color of text when count is incremented
	private Color _decrement; //color of text when count is decremented
	
	/**
	 * Initialized a ResourceArray with all resources set to 0
	 */
	public ResourceArray(int[] resourceCount){
		_resourceCount = Arrays.copyOf(resourceCount, resourceCount.length);
		_originalResourceCount = Arrays.copyOf(resourceCount, resourceCount.length);
		_updateColor = false;
		_oppositeColor = false;
		_increment = Color.green;
		_decrement = Color.red;
		
		initializeGUI();
	}
	
	/**
	 * Initializes the look of the gui
	 */
	private void initializeGUI(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//setting up tokens/labels
		JPanel tokenPanel = new JPanel();
		tokenPanel.setLayout(new GridLayout(1, 5));
		tokenPanel.setOpaque(false);
		
		_oreLabel = new JLabel(Misc.oreTokenGray);
		_oreLabel.setTransferHandler(new TransferHandler("icon"));
		_oreLabel.setOpaque(false);
		tokenPanel.add(_oreLabel);
		
		_wheatLabel = new JLabel(Misc.wheatTokenGray);
		_wheatLabel.setTransferHandler(new TransferHandler("icon"));
		_wheatLabel.setOpaque(false);
		tokenPanel.add(_wheatLabel);
		
		_woodLabel = new JLabel(Misc.woodTokenGray);
		_woodLabel.setTransferHandler(new TransferHandler("icon"));
		_woodLabel.setOpaque(false);
		tokenPanel.add(_woodLabel);
		
		_sheepLabel = new JLabel(Misc.woolTokenGray);
		_sheepLabel.setTransferHandler(new TransferHandler("icon"));
		_sheepLabel.setOpaque(false);
		tokenPanel.add(_sheepLabel);
		
		_brickLabel = new JLabel(Misc.brickTokenGray);
		_brickLabel.setTransferHandler(new TransferHandler("icon"));
		_brickLabel.setOpaque(false);
		tokenPanel.add(_brickLabel);
		
		add(tokenPanel);
		
		//setting up counts
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(1,5));
		labelPanel.setOpaque(false);
		
		_oreCount = new JLabel("");
		_oreCount.setHorizontalAlignment(JLabel.CENTER);
		_oreCount.setOpaque(false);
		
		_wheatCount = new JLabel("");
		_wheatCount.setHorizontalAlignment(JLabel.CENTER);
		_wheatCount.setOpaque(false);
		
		_woodCount = new JLabel("");
		_woodCount.setHorizontalAlignment(JLabel.CENTER);
		_woodCount.setOpaque(false);
		
		_sheepCount = new JLabel("");
		_sheepCount.setHorizontalAlignment(JLabel.CENTER);
		_sheepCount.setOpaque(false);
		
		_brickCount = new JLabel("");
		_brickCount.setHorizontalAlignment(JLabel.CENTER);
		_brickCount.setOpaque(false);
		
		labelPanel.add(_oreCount);
		labelPanel.add(_wheatCount);
		labelPanel.add(_woodCount);
		labelPanel.add(_sheepCount);
		labelPanel.add(_brickCount);
		
		add(labelPanel);
		
		
		_resourceCountArray = new JLabel[] {_oreCount, _wheatCount, _woodCount, _sheepCount, _brickCount};
		_resourceTokenArray = new JLabel[] {_oreLabel, _wheatLabel, _woodLabel, _sheepLabel, _brickLabel};
		
		
		//setting up border
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		
		setOpaque(false);
		
		//adding listeners
//		ResourceArrayMouseAdapter l = new ResourceArrayMouseAdapter();
//		addMouseListener(l);
//		addMouseMotionListener(l);
	}
	
	/**
	 * Given a type, returns the associated token label
	 * @param type Type to find
	 * @return The resource token
	 */
	private JLabel getTokenLabel(int type){
		if(type == ResourceConstants.BRICK)
			return _brickLabel;
		else if(type == ResourceConstants.ORE)
			return _oreLabel;
		else if(type == ResourceConstants.SHEEP)
			return _sheepLabel;
		else if(type == ResourceConstants.WHEAT)
			return _wheatLabel;
		else if(type == ResourceConstants.WOOD)
			return _woodLabel;
		else
			throw new IllegalArgumentException(String.format("Invalid type %s", type));
	}
	
	/**
	 * Given a type, returns the associated count label
	 * @param type Type to find
	 * @return the count label
	 */
	private JLabel getCountLabel(int type){
		if(type == ResourceConstants.BRICK)
			return _brickCount;
		else if(type == ResourceConstants.ORE)
			return _oreCount;
		else if(type == ResourceConstants.SHEEP)
			return _sheepCount;
		else if(type == ResourceConstants.WHEAT)
			return _wheatCount;
		else if(type == ResourceConstants.WOOD)
			return _woodCount;
		else
			throw new IllegalArgumentException(String.format("Invalid type %s", type));
	}
	
	/**
	 * Sets whether or not the color of the text should update after a change
	 * @param e
	 */
	public void setUpdateColorEnabled(boolean e){
		_updateColor = e;
	}
	
	/**
	 * Sets whether or not the color scheme is opposite
	 * @param e
	 */
	public void setOppositeColorEnabled(boolean e){
		_oppositeColor = e;
		
		if(_oppositeColor){
			_increment = Color.red;
			_decrement = Color.green;
		} else{
			_increment = Color.green;
			_decrement = Color.red;
		}
	}
	
	/**
	 * Sets the mouse adapter for a given resource as indicated by the
	 * final fields of ResourceArray. The MouseAdapter must have implementations
	 * for MouseReleased and MouseDragged.
	 * @param adapter The MouseAdapter to add
	 * @param label The label to add the adapter to
	 */
	public void addMouseAdapter(MouseAdapter adapter, int label){
		if(label == ResourceConstants.ORE){
			_oreLabel.addMouseListener(adapter);
			_oreLabel.addMouseMotionListener(adapter);
		} else if(label == ResourceConstants.WHEAT){
			_wheatLabel.addMouseListener(adapter);
			_wheatLabel.addMouseMotionListener(adapter);
		} else if(label == ResourceConstants.WOOD){
			_woodLabel.addMouseListener(adapter);
			_woodLabel.addMouseMotionListener(adapter);
		} else if(label == ResourceConstants.SHEEP){
			_sheepLabel.addMouseListener(adapter);
			_sheepLabel.addMouseMotionListener(adapter);
		} else if(label == ResourceConstants.BRICK){
			_brickLabel.addMouseListener(adapter);
			_brickLabel.addMouseMotionListener(adapter);
		} else{
			throw new IllegalArgumentException(String.format("Invalid label. Got %s", label));
		}
	}
	
	/**
	 * Sets the resource count of the type as specified by ResourceArray.
	 * If the type is invalid, an IllegalArgumentException is thrown
	 * @param count The count to set
	 * @param type The type to set
	 */
	public void setCount(int count, int type){
		if(type == ResourceConstants.ORE){
			_oreCount = new JLabel(Integer.toString(count));
		} else if(type == ResourceConstants.WHEAT){
			_wheatCount = new JLabel(Integer.toString(count));
		} else if(type == ResourceConstants.WOOD){
			_woodCount = new JLabel(Integer.toString(count));
		} else if(type == ResourceConstants.SHEEP){
			_sheepCount = new JLabel(Integer.toString(count));
		} else if(type == ResourceConstants.BRICK){
			_brickCount = new JLabel(Integer.toString(count));
		} else{
			throw new IllegalArgumentException(String.format("Invalid label. Got %s", type));
		}
		
		validateCount(count);
		_resourceCount[type] = count;
		
		repaint();
	}
	
	/**
	 * Sets the resource count. This will modify the original count. Throws an IllegalArgumentException
	 * if the array is of invalid size
	 * @param resourceCount The counts to set to
	 */
	public void setResourceArrayCount(int[] resourceCount){
		if(resourceCount.length != _resourceCount.length)
			throw new IllegalArgumentException(String.format("Resource array must be of size %s. Got: %s", _resourceCount.length, resourceCount.length));
		
		for(int i = 0; i < _resourceCount.length; i++){
			_resourceCount[i] = resourceCount[i];
			_originalResourceCount[i] = resourceCount[i];
		}
		
		repaint();
	}
	
	/**
	 * Increments the count of the resource type. Throws an IllegalArgumentException
	 * if the type is invalid.
	 * @param type The resource to increment
	 */
	public void incrementCount(int type){
		try{
			_resourceCount[type]++;
		} catch(ArrayIndexOutOfBoundsException e){
			throw new IllegalArgumentException(String.format("ResourceArray: invalid resource type. Got %s", type));
		}
		
		repaint();
	}
	
	/**
	 * Decrements the count of the resource type. Throws an IllegalArgumentException
	 * if the type is invalid or if decrementing the count would cause the count
	 * to be < 0.
	 * @param type The resource to decrement.
	 */
	public void decrementCount(int type){
		try{
			if(_resourceCount[type] <= 0){
				//do nothing
			} else{
				_resourceCount[type]--;
			}
		} catch(ArrayIndexOutOfBoundsException e){
			throw new IllegalArgumentException(String.format("ResourceArray: invalid resource type. Got %s", type));
		}
		
		repaint();
	}
	
	/**
	 * Given a resource type as specified by ResourceArray, returns the count.
	 * If the type is invalid, an IllegalArgumentException is thrown
	 * @param type The type of resource to get
	 */
	public int getCount(int type){
		try{
			int toReturn = _resourceCount[type];
			return toReturn;
		} catch(ArrayIndexOutOfBoundsException e){
			throw new IllegalArgumentException(String.format("Got invalid type of %s", type));
		}
	}
	
	/**
	 * Resets the count of the resource to what it was originally
	 * @param type The type to reset
	 */
	public void resetCount(int type){
		try{
			_resourceCount[type] = _originalResourceCount[type];
		} catch(ArrayIndexOutOfBoundsException e){
			throw new IllegalArgumentException(String.format("Got invalid type: %s", type));
		}
		
		repaint();
	}
	

	
	/**
	 * Makes sure that the count is >= 0. If it is not, an
	 * IllegalArgumentException is thrown.
	 * @param count Count to validate.
	 */
	private void validateCount(int count){
		if(count < 0)
			throw new IllegalArgumentException(String.format("Count must be >= 0. Got: %s", count));
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		for(int i = 0; i < _resourceCount.length; i++){
			if(_resourceCount[i] == 0){
				if(_updateColor){
					if(_resourceCount[i] < _originalResourceCount[i]){
						_resourceCountArray[i].setForeground(_decrement);
					} else
						_resourceCountArray[i].setForeground(Color.white);
				}
				_resourceCountArray[i].setText("0");
				_resourceTokenArray[i].setIcon(getGrayImage(i));
			} else{
				if(_updateColor){
					if(_resourceCount[i] < _originalResourceCount[i])
						_resourceCountArray[i].setForeground(_decrement);
					else if(_resourceCount[i] > _originalResourceCount[i])
						_resourceCountArray[i].setForeground(_increment);
					else
						_resourceCountArray[i].setForeground(Color.white);
				}
				_resourceCountArray[i].setText(Integer.toString(_resourceCount[i]));
				_resourceTokenArray[i].setIcon(getImage(i));
			}
		}
		
		
	}
	
	/**
	 * Returns the original count of the desired resource
	 * @param type The resource to find the count
	 * @return the original amount
	 */
	public int getOriginalCount(int type){
		try{
			return _originalResourceCount[type];
		} catch(ArrayIndexOutOfBoundsException e){
			throw new IllegalArgumentException(String.format("Invalid label. Got %s", type));
		}
	}
	
	@Override
	public void ericUpdate() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Resets the resource count to their original count
	 */
	public void resetCountAll(){
		setResourceArrayCount(_originalResourceCount);
	}
	
	/**
	 * Given a type, returns the image of the associated type
	 * @param type The type of the resource, as specified in ResourceArray
	 * @return the image of the resource
	 */
	public static ImageIcon getImage(int type){
		if(type == ResourceConstants.ORE){
			return Misc.oreToken;
		} else if(type == ResourceConstants.WHEAT){
			return Misc.wheatToken;
		} else if(type == ResourceConstants.WOOD){
			return Misc.woodToken;
		} else if(type == ResourceConstants.SHEEP){
			return Misc.woolToken;
		} else if(type == ResourceConstants.BRICK){
			return Misc.brickToken;
		} else{
			throw new IllegalArgumentException(String.format("Invalid label. Got %s", type));
		}
	}
	
	/**
	 * Given a type, returns the image of the associated type
	 * @param type The type of the resource, as specified in ResourceArray
	 * @return the image of the resource
	 */
	public static ImageIcon getGrayImage(int type){
		if(type == ResourceConstants.ORE){
			return Misc.oreTokenGray;
		} else if(type == ResourceConstants.WHEAT){
			return Misc.wheatTokenGray;
		} else if(type == ResourceConstants.WOOD){
			return Misc.woodTokenGray;
		} else if(type == ResourceConstants.SHEEP){
			return Misc.woolTokenGray;
		} else if(type == ResourceConstants.BRICK){
			return Misc.brickTokenGray;
		} else{
			throw new IllegalArgumentException(String.format("Invalid label. Got %s", type));
		}
	}

}
