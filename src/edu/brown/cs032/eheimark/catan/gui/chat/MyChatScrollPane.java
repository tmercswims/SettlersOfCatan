package edu.brown.cs032.eheimark.catan.gui;

import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
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

public class MyChatScrollPane {
	private static final long serialVersionUID = 1L;
	
	private JTextPane _myTextPane;
	private JScrollPane _scroll;

	SimpleAttributeSet _red;
	SimpleAttributeSet _blue;
	SimpleAttributeSet _orange;
	SimpleAttributeSet _server;
	SimpleAttributeSet _white;

	public MyChatScrollPane(Dimension d) {
		super();
		_myTextPane = new JTextPane();
		_myTextPane.setOpaque(false);
		_myTextPane.setSize(d);
		_myTextPane.setEditable(false);
		_myTextPane.setEditorKit(new WrapEditorKit());
		DefaultCaret caret = (DefaultCaret) _myTextPane.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		_myTextPane.setCaret(caret);
		_myTextPane.setFocusable(true);
		_myTextPane.setPreferredSize(d);
		_myTextPane.setMargin(new Insets(10,10,10,10));
		
		_scroll = new JScrollPane(_myTextPane);
		_scroll.setOpaque(false);
		_scroll.getViewport().setOpaque(false);
		_scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		_scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		_scroll.setPreferredSize(d);
		_scroll.setBorder(BorderFactory.createEmptyBorder());
		_scroll.setFocusable(false);
	}
	
	public JScrollPane getScrollPane() {
		return _scroll;
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

	public void insertString(final String f, final SimpleAttributeSet attr){
		SwingUtilities.invokeLater(new Runnable() {
			@Override	
			public void run() {
				try {
					_myTextPane.getDocument().insertString(_myTextPane.getDocument().getLength(),f.trim()+"\n",attr);
					StyleConstants.setFontFamily(attr, "Helvetica");
					StyleConstants.setItalic(attr, false);
					_myTextPane.setCaretPosition(_myTextPane.getDocument().getLength());
				} catch (BadLocationException ex) {
					System.out.println(String.format("ERROR: %s", ex.getMessage()));
				}
			}
		});
	}

}
