package edu.brown.cs032.eheimark.catan.gui;

import static edu.brown.cs032.sbreslow.catan.gui.board.GUIConstants.Dimensions.MINIMUM_GUI_FRAME_SIZE;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.newdawn.easyogg.OggClip;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;

/**
 * The Class GUIFrame contains the main gui panel with the board at top
 * and tabbed panels at the bottom.
 */
public class GUIFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private OggClip _music;
	private GUI _gui;
	private int _x;
	private int _y;
	private CatanClient _cc;

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
		setMinimumSize(MINIMUM_GUI_FRAME_SIZE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		addComponentListener(new SizeList(cc, this));
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new KeyboardShortcuts());
		_x = 600/23;
		_y = 600/14;
		_cc = cc;
	}

	private class SizeList implements ComponentListener{
		private CatanClient _cc;
		private JFrame _frame;

		public SizeList(CatanClient cc, JFrame frame) {
			_cc = cc;
			_frame = frame;
		}

		@Override
		public void componentResized(final ComponentEvent e) {
			System.out.println(String.format("WIDTH %S HEIGHT %S", _frame.getContentPane().getWidth(), _frame.getContentPane().getHeight()));
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					_cc.getGUI().getDP().setSize(_frame.getContentPane().getWidth()*600/1000, 
							_frame.getContentPane().getHeight()*600/825);
					if(checkDelta(_frame.getContentPane().getWidth()*600/1000, 
							_frame.getContentPane().getHeight()*600/825)){
						//System.err.println("HERE");
						_cc.getGUI().getDP().setResize(_frame.getContentPane().getWidth()*600/1000, 
								_frame.getContentPane().getHeight()*600/825);
					}
					_cc.getBoard().resize(_cc.getGUI().getDP().getWidth(), _cc.getGUI().getDP().getHeight());
					_cc.getGUI().getDP().serverUpdate();
				}
				
			});
		}

		@Override
		public void componentMoved(ComponentEvent e) {}
		@Override
		public void componentShown(ComponentEvent e) {}
		@Override
		public void componentHidden(ComponentEvent e) {}
	}
	
	private boolean checkDelta(int i, int j) {
		System.out.println("OLD: "+_x+", "+_y);
		System.out.println("CHECKING: "+i/23+", "+j/14);
		if((_x!=i/23) || (_y!=j/14)){
			_x = i/23;
			_y = j/14;
			_cc.setXY(_x, _y);
			return true;
		}
		else{
			return false;
		}
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
	 * Stops the music.
	 */
	public void stopMusic() {
		_music.stop();
	}

	/**
	 * Plays the music.
	 */
	public void playMusic() {
		_music.loop();
	}

	/**
	 * Exits.
	 */
	public void exit(){
		super.setVisible(false);
		super.dispose();
		_music.stop();
	}

	/**
	 * Toggles music.
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
	 * Keyboard shortcuts for the GUI.
	 */
	private class KeyboardShortcuts implements KeyEventDispatcher {
		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if (e.getID() == KeyEvent.KEY_PRESSED && e.isControlDown()) {
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
				else if(e.getKeyCode() == KeyEvent.VK_TAB) {
					if(e.isShiftDown()) {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								_gui.getTabbedMenu().incrementPageLeft();
							}
						});
					} 
					else {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								_gui.getTabbedMenu().incrementPageRight();
							}
						});
					}
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
			}
			else if(e.getID() == KeyEvent.KEY_PRESSED && _gui.getChat().hasFocus()) {
				if(e.getKeyCode() == KeyEvent.VK_UP) {
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
