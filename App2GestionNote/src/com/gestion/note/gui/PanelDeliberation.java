package com.gestion.note.gui;

import java.awt.GridLayout;
import java.awt.print.PrinterException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class PanelDeliberation extends JPanel implements TableModelListener
{

	/** Le tableau */
	private JTable dataTable;

	/** Le modèle du tableau */
	private ModelTableDeliberation model;

	/** Indicateur de modification du tableau */
	private boolean modifiedFlag = false;

	
	public PanelDeliberation() {
		
		setLayout(new GridLayout(1,1));

		model = new ModelTableDeliberation();
		model.addTableModelListener(this);
		dataTable = new JTable(model);
		dataTable.setAutoResizeMode( JTable.AUTO_RESIZE_OFF);
		dataTable.setFillsViewportHeight(true);
		JScrollPane scrol = new JScrollPane(dataTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scrol);

	}
	
	public PanelDeliberation(Long idClasse) {
		
		setLayout(new GridLayout(1,1));

		model = new ModelTableDeliberation(idClasse);
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

	public ModelTableDeliberation getModel() {
		return model;
	}

	public void setModel(ModelTableDeliberation model) {
		this.model = model;
	}

}
