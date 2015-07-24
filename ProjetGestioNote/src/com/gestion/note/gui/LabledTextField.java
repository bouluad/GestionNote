package com.gestion.note.gui;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LabledTextField extends JPanel{
	

	private JTextField txt;

	private JLabel label;

	public LabledTextField(String s) {

		txt = new JTextField();
		label = new JLabel(s);
		
		setLayout(new GridLayout(1,1));
		add(label);
		add(txt);
	}
	
	
	public LabledTextField(int h,String s) {

		txt = new JTextField(h);
		label = new JLabel(s);
		setLayout(new GridLayout(1,1));
		add(label);
		add(txt);
	}


	public String getTxt() {
		return txt.getText();
	}

	public void setTxt(int t) {
		this.txt.setText(t+"");
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	
}
