package com.gestion.note.gui;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Madmar
 *
 */
public class LabledComboBox extends JPanel {

	/***/
	private JComboBox combo;

	private JLabel label;

	public LabledComboBox(JComboBox combo, JLabel label) {

		this.combo = combo;
		this.label = label;
	}

	public LabledComboBox(String[] items, String pLab) {

		combo = new JComboBox(items);
		label = new JLabel(pLab);
		add(label);
		add(combo);

	}
	
	public LabledComboBox(List<String> items, String pLab) {

		combo = new JComboBox(items.toArray());
		label = new JLabel(pLab);
		add(label);
		add(combo);

	}

	public void refresh(String[] choices) {
		combo.removeAllItems();
		for (int i = 0; i < choices.length; i++) {
			combo.addItem(choices[i]);
		}
	}

	public JComboBox getCombo() {
		return combo;
	}

	public void setCombo(JComboBox combo) {
		this.combo = combo;
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public void addActionListner(ActionListener pL) {
		combo.addActionListener(pL);
	}

	public void addItemListener(ItemListener pL) {
		combo.addItemListener(pL);
	}
	
	

}
