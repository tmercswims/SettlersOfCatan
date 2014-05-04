package edu.brown.cs032.atreil.catan.gui.trade;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.brown.cs032.eheimark.catan.gui.ServerUpdate;

/**
 * This class will contain all of the resource tokens that may be
 * dragged and dropped into other TradeFloor classes.
 * @author Alex Treil
 *
 */
class TradeFloor extends JPanel implements ServerUpdate{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6151013225993425776L;

	private final JLabel _title; //title of the trade floor
	private final int[] _resourceCount; //stores how much of each resource
	private final ResourceArray _resourceArray;
	
	public TradeFloor(String title, int[] resourceCount){
		super();
		
		_title = new JLabel(title);
		_title.setForeground(Color.white);
		_title.setOpaque(false);
		_title.setAlignmentX(CENTER_ALIGNMENT);
		
		_resourceCount = Arrays.copyOf(resourceCount, resourceCount.length);
		_resourceArray = new ResourceArray(_resourceCount);
		
		initializeGUI();
	}
	
	/**
	 * Initializes the GUI by adding the components and setting
	 * the appropriate properties
	 */
	private void initializeGUI(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(_title);
		add(_resourceArray);
		
		setOpaque(false);
	}
	
	/**
	 * Sets whether or not the color of the text should update after a change
	 * @param e
	 */
	public void setUpdateColorEnabled(boolean e){
		_resourceArray.setUpdateColorEnabled(e);
	}
	
	/**
	 * Sets the mouse adapter for a given resource as indicated by the
	 * final fields of ResourceArray. The MouseAdapter must have implementations
	 * for MouseReleased and MouseDragged.
	 * @param adapter The MouseAdapter to add
	 * @param label The label to add the adapter to
	 */
	public void addMouseAdapter(MouseAdapter adapter, int label){
		_resourceArray.addMouseAdapter(adapter, label);
	}
	
	/**
	 * Sets the resources count of the trade floor. The array must
	 * be of size 5. If not, an IllegalArgumentException is thrown.
	 * @param resourceCount The count to set to
	 */
	public void setResourceCount(int[] resourceCount){
		if(resourceCount.length != _resourceCount.length)
			throw new IllegalArgumentException(String.format("Resource array must be of size %s. Got: %s", _resourceCount.length, resourceCount.length));
		
		_resourceArray.setResourceArrayCount(resourceCount);
	}
	
	/**
	 * Sets whether or not the color scheme is opposite
	 * @param e
	 */
	public void setOppositeColorEnabled(boolean e){
		_resourceArray.setOppositeColorEnabled(e);
	}
	
	/**
	 * Increments the count of the resource type. Throws an IllegalArgumentException
	 * if the type is invalid.
	 * @param type The resource to increment
	 */
	public void incrementCount(int type){
		_resourceArray.incrementCount(type);
	}
	
	/**
	 * Decrements the count of the resource type. Throws an IllegalArgumentException
	 * if the type is invalid or if decrementing the count would cause the count
	 * to be < 0.
	 * @param type The resource to decrement.
	 */
	public void decrementCount(int type){
		_resourceArray.decrementCount(type);
	}
	
	/**
	 * Given a resource type as specified by ResourceArray, returns the count.
	 * If the type is invalid, an IllegalArgumentException is thrown
	 * @param type The type of resource to get
	 */
	public int getCount(int type){
		return _resourceArray.getCount(type);
	}
	
	/**
	 * Sets the resource count of the type as specified by ResourceArray.
	 * If the type is invalid, an IllegalArgumentException is thrown
	 * @param count The count to set
	 * @param type The type to set
	 */
	public void setCount(int count, int type){
		_resourceArray.setCount(count, type);
	}
	
	/**
	 * Resets the count of the resource to what it was originally
	 * @param type The type to reset
	 */
	public void resetCount(int type){
		_resourceArray.resetCount(type);
	}
	
	/**
	 * Resets all of the resources to the original count
	 */
	public void resetCountAll(){
		_resourceArray.resetCountAll();
	}
	
	/**
	 * Returns the original count of the desired resource
	 * @param type The resource to find the count
	 * @return the original amount
	 */
	public int getOriginalCount(int type){
		return _resourceArray.getOriginalCount(type);
	}
	
	@Override
	public void serverUpdate() {
		// TODO Auto-generated method stub
		
	}

}
