package com.gestion.note.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.print.PrinterException;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class TableStudentsPanel2 extends JPanel implements TableModelListener
{

	/** Le tableau */
	private JTable dataTable;

	/** Le modèle du tableau */
	private TableStudentModel2 model;

	/** Indicateur de modification du tableau */
	private boolean modifiedFlag = false;

	
	public TableStudentsPanel2() {
		
		setLayout(new GridLayout(1,1));

		model = new TableStudentModel2();
		model.addTableModelListener(this);
		dataTable = new JTable(model);

		dataTable.setFillsViewportHeight(true);

		JScrollPane scrol = new JScrollPane(dataTable);

		add(scrol);

	}
	
	public TableStudentsPanel2(List<String> st) {
		
		setLayout(new GridLayout(1,1));

		model = new TableStudentModel2(st);
		model.addTableModelListener(this);
		dataTable = new JTable(model);

		dataTable.setFillsViewportHeight(true);

		JScrollPane scrol = new JScrollPane(dataTable);

		add(scrol);

	}

	/**
	 * Imprime le contenu du tableau
	 */
	public void printTable() {

		try {
			dataTable.print();
		} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void refresh() {

	}

	public void tableChanged(TableModelEvent e) {
		modifiedFlag = true;
		int row = e.getFirstRow();
		int column = e.getColumn();
		TableModel model = (TableModel) e.getSource();
		String columnName = model.getColumnName(column);
		Object data = model.getValueAt(row, column);

	}

	public JTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(JTable dataTable) {
		this.dataTable = dataTable;
	}

	public TableStudentModel2 getModel() {
		return model;
	}

	public void setModel(TableStudentModel2 model) {
		this.model = model;
	}


}
