package carrent.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class AutoSuggest extends JPanel {
	public final JTextField textField;
	private final JComboBox combo = new JComboBox();
	private final Vector<String> vector = new Vector<String>();

	public AutoSuggest(ArrayList<String> elements) {
		super(new BorderLayout());
		//super.setPreferredSize();
		for (String s : elements) {
			vector.add(s);
		}

		combo.setEditable(true);
		textField = (JTextField) combo.getEditor().getEditorComponent();
		textField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						String text = textField.getText();
						if (text.length() == 0) {
							combo.hidePopup();
							setModel(new DefaultComboBoxModel(vector), "");
						} else {
							DefaultComboBoxModel m = getSuggestedModel(vector,text);
							if (m.getSize() == 0 || hide_flag) {
								combo.hidePopup();
								hide_flag = false;
							} else {
								setModel(m, text);
								combo.showPopup();
							}
						}
					}
				});
			}

			public void keyPressed(KeyEvent e) {
				String text = textField.getText();
				int code = e.getKeyCode();
				if (code == KeyEvent.VK_ENTER) {
					if (!vector.contains(text)) {
						vector.addElement(text);
						Collections.sort(vector);
						setModel(getSuggestedModel(vector, text), text);
						}
					hide_flag = true;
				} else if (code == KeyEvent.VK_ESCAPE) {
					hide_flag = true;
				} else if (code == KeyEvent.VK_RIGHT) {
					for (int i = 0; i < vector.size(); i++) {
						String str = vector.elementAt(i);
						if (str.toLowerCase().startsWith(text.toLowerCase())) {
							combo.setSelectedIndex(-1);
							textField.setText(str);
							return;
						}
					}
				}
			}
		});
		setModel(new DefaultComboBoxModel(vector), "");
		JPanel p = new JPanel(new BorderLayout());
		p.add(combo, BorderLayout.NORTH);
		add(p);
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setPreferredSize(new Dimension(200, 40));
	}

	private boolean hide_flag = false;

	private void setModel(DefaultComboBoxModel mdl, String str) {
		combo.setModel(mdl);
		combo.setSelectedIndex(-1);
		textField.setText(str);
	}

	private static DefaultComboBoxModel getSuggestedModel(
			java.util.List<String> list, String text) {
		DefaultComboBoxModel m = new DefaultComboBoxModel();
		m.getSize();
		for (String s : list) {
			if (s.toLowerCase().startsWith(text.toLowerCase()))
				m.addElement(s);
		}
		return m;
	}

}
