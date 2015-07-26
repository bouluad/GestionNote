package com.exemple.gui;


import java.awt.Component;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

 

/**
  * GroupableTableHeader
  *
  * @version 1.0 10/20/98
  * @author Nobuo Tamemasa
  */

public class GroupableTableHeader extends JTableHeader {
  private static final String uiClassID = "GroupableTableHeaderUI";
  protected Vector columnGroups = null;
    
  public GroupableTableHeader(TableColumnModel model) {
    super(model);
    setUI(new GroupableTableHeaderUI());
    setReorderingAllowed(false);
  }
  
  public void setReorderingAllowed(boolean b) {
    reorderingAllowed = false;
  }
    
  public void addColumnGroup(ColumnGroup g) {
    if (columnGroups == null) {
      columnGroups = new Vector();
    }
    columnGroups.addElement(g);
  }

  public void updateUI(){
//    setUI(this.getUI());

    TableCellRenderer renderer = getDefaultRenderer();
    if (renderer instanceof Component) {
        SwingUtilities.updateComponentTreeUI((Component)renderer);
    }
}
  public Enumeration getColumnGroups(TableColumn col) {
    if (columnGroups == null) return null;
    Enumeration enu = columnGroups.elements();
    while (enu.hasMoreElements()) {
      ColumnGroup cGroup = (ColumnGroup)enu.nextElement();
      Vector v_ret = (Vector)cGroup.getColumnGroups(col,new Vector());
      if (v_ret != null) { 
	return v_ret.elements();
      }
    }
    return null;
  }
  
  public void setColumnMargin() {
    if (columnGroups == null) return;
    int columnMargin = getColumnModel().getColumnMargin();
    Enumeration enu = columnGroups.elements();
    while (enu.hasMoreElements()) {
      ColumnGroup cGroup = (ColumnGroup)enu.nextElement();
      cGroup.setColumnMargin(columnMargin);
    }
  }
  
}

