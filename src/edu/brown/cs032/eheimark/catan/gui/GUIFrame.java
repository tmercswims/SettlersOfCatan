package edu.brown.cs032.eheimark.catan.gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.IOException;

import org.newdawn.easyogg.OggClip;

/**
 * The Class GUIFrame contains the main gui panel with the board at top
 * and tabbed panels at the bottom.
 */
public class GUIFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private OggClip _music;
	private GUI _gui;
	/**
	 * Instantiates a new GUI frame.
	 *
	 * @param cc the cc
	 */
	public GUIFrame(CatanClient cc) {
		super("Settlers of Catan : " + cc.getPlayerName());
		setMusic();
		_gui = new GUI(cc);
		add(_gui);
		setVisible(true);
		setResizable(true);
		setMinimumSize(Constants.GUI_SIZE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyDispatcher());
	}

	private void setMusic() {
		try {
			_music = new OggClip("music/ingame.ogg");
			_music.loop();
		} catch (IOException ex) {
			System.err.println(String.format("ERROR: %s", ex.getMessage()));
		}
	}
    
    /**
     * Toggles the music state.
     */
    public void toggleMusic() {
        if (_music.stopped()) {
            _music.loop();
        } else {
            _music.stop();
        }
    }
    
    /**
     * Tells whether the music is playing.
     * @return true if music is playing, false if not
     */
    public boolean isMusicPlaying() {
        return !_music.stopped();
    }

	/**
	 * Plays the music.
	 */
	public void playMusic() {
		_music.loop();
	}

	/**
	 * Stops the music.
	 */
	public void stopMusic() {
		_music.stop();
	}

	public void exit(){
		super.setVisible(false);
		super.dispose();
		_music.stop();
	}
	private class MyDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
            	if(e.getKeyCode() == KeyEvent.VK_O) {
            		SwingUtilities.invokeLater(new Runnable() {
    					@Override
    					public void run() {
    	            		_gui.getTabbedMenu().setOverviewPage();
    					}
    				});
            	}
            	else if(e.getKeyCode() == KeyEvent.VK_B) {
            		SwingUtilities.invokeLater(new Runnable() {
    					@Override
    					public void run() {
    	            		_gui.getTabbedMenu().setBuildPage();
    					}
    				});
            	}
            	else if(e.getKeyCode() == KeyEvent.VK_T) {
            		SwingUtilities.invokeLater(new Runnable() {
    					@Override
    					public void run() {
    	            		_gui.getTabbedMenu().setTradePage();
    					}
    				});
            	}
            	else if(e.getKeyCode() == KeyEvent.VK_D) {
            		SwingUtilities.invokeLater(new Runnable() {
    					@Override
    					public void run() {
    	            		_gui.getTabbedMenu().setDevCardPage();
    					}
    				});
            	}
            	else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            		SwingUtilities.invokeLater(new Runnable() {
    					@Override
    					public void run() {
    	            		_gui.getTabbedMenu().incrementPageLeft();
    					}
    				});
            	}
            	else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            		SwingUtilities.invokeLater(new Runnable() {
    					@Override
    					public void run() {
    	            		_gui.getTabbedMenu().incrementPageRight();
    					}
    				});
            	}
            	else if(e.getKeyCode() == KeyEvent.VK_C) {
            		SwingUtilities.invokeLater(new Runnable() {
    					@Override
    					public void run() {
    	            		_gui.getChat().requestFocus();
    					}
    				});
            	}
            	else if(e.getKeyCode() == KeyEvent.VK_R) {
            		SwingUtilities.invokeLater(new Runnable() {
    					@Override
    					public void run() {
    	            		_gui.getActivePlayer().requestFocus();
    					}
    				});
            	}
            	else if(e.getKeyCode() == KeyEvent.VK_UP) {
            		SwingUtilities.invokeLater(new Runnable() {
    					@Override
    					public void run() {
    	            		_gui.getChat().pressedKeyUp();
    					}
    				});
            	}
            	else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            		SwingUtilities.invokeLater(new Runnable() {
    					@Override
    					public void run() {
    	            		_gui.getChat().pressedKeyDown();
    					}
    				});
            	}
            }
            return false;
        }
    }
}
