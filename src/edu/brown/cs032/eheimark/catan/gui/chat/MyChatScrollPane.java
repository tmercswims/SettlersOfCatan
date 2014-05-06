package edu.brown.cs032.eheimark.catan.gui.chat;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.ParagraphView;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

/**
 * The Class MyChatScrollPane is used for the Server Chat Log and Player Chat Box Log.
 */
public class MyChatScrollPane {
	private JTextPane _myTextPane; // Text pane
	private JScrollPane _scroll; // Scroll pane that contains text pane

	// Attributes based on color
	SimpleAttributeSet _red;
	SimpleAttributeSet _blue;
	SimpleAttributeSet _orange;
	SimpleAttributeSet _server;
	SimpleAttributeSet _white;

	/**
	 * Instantiates a new my chat scroll pane.
	 *
	 * @param d the dimension
	 */
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

	/**
	 * Gets the scroll pane.
	 *
	 * @return the scroll pane
	 */
	public JScrollPane getScrollPane() {
		return _scroll;
	}

	/**
	 * The Class WrapEditorKit.
	 */
	class WrapEditorKit extends StyledEditorKit {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 2347939257335358031L;

		/** The default factory. */
		ViewFactory defaultFactory=new WrapColumnFactory();

		@Override
		public ViewFactory getViewFactory() {
			return defaultFactory;
		}
	}

	/**
	 * A factory for creating WrapColumn objects.
	 */
	class WrapColumnFactory implements ViewFactory {

		/* (non-Javadoc)
		 * @see javax.swing.text.ViewFactory#create(javax.swing.text.Element)
		 */
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

	/**
	 * The Class WrapLabelView.
	 */
	class WrapLabelView extends LabelView {

		/**
		 * Instantiates a new wrap label view.
		 *
		 * @param elem the elem
		 */
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

	/**
	 * Insert string.
	 *
	 * @param msg the msg to insert
	 * @param attr the attribute
	 */
	public void insertString(final String msg, final SimpleAttributeSet attr){
		SwingUtilities.invokeLater(new Runnable() {
			@Override	
			public void run() {
				try {
					_myTextPane.getDocument().insertString(_myTextPane.getDocument().getLength(),msg.trim()+"\n",attr);
					StyleConstants.setFontFamily(attr, "Helvetica");
					StyleConstants.setItalic(attr, false);
					_myTextPane.setCaretPosition(_myTextPane.getDocument().getLength());
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			}
		});
	}

}
