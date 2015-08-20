package com.gestion.note.gui;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DialogueImprimer extends JDialog{
	
	

	 
	
	 
	 public DialogueImprimer()
	 {
		 setTitle("Help -App.Ges.Note");
		 setSize(750,550);
		 setResizable(false);
		 setLocationRelativeTo(null);
		 JPanel panelImage=new JPanel();
		String iconDirectory = "resources/icons/";

		JLabel label1=new JLabel(new ImageIcon(iconDirectory + "imp.png"));
		
		panelImage.setLayout(new BorderLayout());
		panelImage.add(label1,BorderLayout.CENTER);
		add(panelImage);
			
			
	 }
	 
	 
}
