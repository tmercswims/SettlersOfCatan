package edu.brown.cs032.eheimark.catan.gui;

import edu.brown.cs032.atreil.catan.chat.server.SemiTransparentPanel;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Edge.blue;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Edge.orange;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Edge.red;
import static edu.brown.cs032.sbreslow.catan.gui.board.BoardImages.Edge.white;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import edu.brown.cs032.atreil.catan.networking.client.CatanClient;

import java.awt.BorderLayout;

import javax.swing.text.AbstractDocument;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.ParagraphView;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

public class ChatPanel extends JPanel {

	private static final long serialVersionUID = 8319006484243162853L;

	private final JTextField _field;
	private final JTextPane _area;
	private final JScrollPane _scroll;
	private final CatanClient _client;
	SimpleAttributeSet _red;
	SimpleAttributeSet _blue;
	SimpleAttributeSet _orange;
	SimpleAttributeSet _server;
	SimpleAttributeSet _white;

	public ChatPanel(CatanClient cc) {
		super(new BorderLayout());
		_client = cc;

		JPanel pan = new SemiTransparentPanel();//JPanel();

		_field = new JTextField();
		_field.setSize(new Dimension(380, (int)Math.round(_field.getSize().getHeight())));
		_field.addKeyListener(new ChatListener());
		_field.setOpaque(false);
		_field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.darkGray), BorderFactory.createEmptyBorder(4, 4, 4, 4)));
		_field.setForeground(Color.white);
		_field.setCaretColor(Color.white);

		_area = new JTextPane();
		_area.setOpaque(false);
		_area.setSize(new Dimension(380, 595));
		_area.setEditable(false);
		_area.setEditorKit(new WrapEditorKit());
		DefaultCaret caret = (DefaultCaret)_area.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		_area.setCaret(caret);
		_area.setFocusable(false);

		_scroll = new JScrollPane(_area);
		_scroll.setOpaque(false);
		_scroll.getViewport().setOpaque(false);
		_scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		_scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		_scroll.setPreferredSize(new Dimension(380, 595));
		_scroll.setBorder(BorderFactory.createEmptyBorder());
		_scroll.setFocusable(false);

		pan.setBackground(new Color(0f, 0f, 0f, .5f));
		pan.add(_scroll, BorderLayout.CENTER);
		pan.add(_field, BorderLayout.SOUTH);

		add(pan);//new AlphaContainer(pan));

		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		_red = new SimpleAttributeSet();
		StyleConstants.setFontFamily(_red, "Helvetica");
		StyleConstants.setForeground(_red, red);

		_blue = new SimpleAttributeSet();
		StyleConstants.setFontFamily(_blue, "Helvetica");
		StyleConstants.setForeground(_blue, blue);

		_orange = new SimpleAttributeSet();
		StyleConstants.setFontFamily(_orange, "Helvetica");
		StyleConstants.setForeground(_orange, orange);

		_white = new SimpleAttributeSet();
		StyleConstants.setFontFamily(_white, "Helvetica");
		StyleConstants.setForeground(_white, Color.white);

		_server = new SimpleAttributeSet();
		StyleConstants.setFontFamily(_server, "Helvetica");
		StyleConstants.setForeground(_server, Color.gray);
		StyleConstants.setBold(_server, true);

		_field.requestFocus();
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
		color = color.substring(1,color.length()-2);
		System.out.println("COLOR " + color);
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
			final String f = sb.toString();
			SwingUtilities.invokeLater(new Runnable() {
				@Override	
				public void run() {
					try {
						_area.getDocument().insertString(_area.getCaretPosition(),f.trim()+"\n",attr);
					} catch (BadLocationException ex) {
						System.out.println(String.format("ERROR: %s", ex.getMessage()));
					}
				}
			});				
		}
	}

	private void println(String message){
		try {
			_client.sendMessage(message);
		} catch (IOException ex) {
			System.out.println(String.format("ERROR: %s", ex.getMessage()));
		}
	}


	private class ChatListener implements KeyListener {
		//red blue orange white
		@Override
		public void keyTyped(KeyEvent e) {
			//System.out.println("getKeyChar "+e.getKeyChar());
			if(e.getKeyChar() == '\n') {
				String message = _field.getText();
				_field.setText("");
				//_history.addFirst(message);
				//_unsentContents = "";
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
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}
	}

}
