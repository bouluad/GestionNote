package com.gestion.note.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gestion.note.bll.ElementNotFoundException;
import com.gestion.note.bll.GestionModules;
import com.gestion.note.bo.Module;
import com.gestion.note.db.DataBaseException;

public class ModelsPanel extends JPanel{

	GestionModules lGestModules  = GestionModules.getInstance();
	
	private JLabel modules[];
	
	public ModelsPanel(Long idClasse) {
		try {
			

			List<Module> listModules=lGestModules.getClasseModules(idClasse);
			this.setLayout(new GridLayout(1, listModules.size(), 400, 10));
			modules=new JLabel[listModules.size()];
	    //	add(Box.createRigidArea(new Dimension (50,0)),BorderLayout.EAST);
			for(int i=0;i<listModules.size();i++)
			{
				modules[i]=new JLabel(listModules.get(i).getIntitule());
				System.out.println(listModules.get(i).getIntitule()+"11111111111111111111111111111111111111");
				add(modules[i]);
		    	
				//add(Box.createRigidArea(new Dimension (220,0)),BorderLayout.EAST);

				
			}
		} catch (DataBaseException | ElementNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//setBorder(BorderFactory.createLineBorder(Color.GRAY));
	}

	public ModelsPanel() {
		// TODO Auto-generated constructor stub
	}

}
