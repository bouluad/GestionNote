package com.gestion.note.gui;

import javax.swing.JTable;

public class TableModel1 extends JTable{
	
	public TableModel1(ModelTableDeliberation model){	
		setModel(model);
	}
	public boolean getScrollableTracksViewportWidth()
    {
        return getPreferredSize().width < getParent().getWidth();
    }

}
