package com.gestion.note.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.print.PrinterException;
import java.util.List;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import com.gestion.note.bll.ElementNotFoundException;
import com.gestion.note.bll.GestionModules;
import com.gestion.note.bll.GestionNotes;
import com.gestion.note.bo.EtudiantNote;
import com.gestion.note.bo.Matiere;
import com.gestion.note.bo.Note;
import com.gestion.note.config.ConfigurationLoader;
import com.gestion.note.db.DataBaseException;

public class TableStudentsPanel extends JPanel implements TableModelListener {

	/** Le tableau */
	private JTable dataTable;

	/** Le modèle du tableau */
	private TableStudentModel model;

	/** Indicateur de modification du tableau */
	private boolean modifiedFlag = false;

	public TableStudentsPanel() {
		setLayout(new GridLayout(1,1,20,20));

		model = new TableStudentModel();
		model.addTableModelListener(this);
		dataTable = new JTable(model);

		dataTable.setFillsViewportHeight(true);

		JScrollPane scrol = new JScrollPane(dataTable);
	
		add(scrol);

	}

	public void saveChanges(String pIntitule) throws DataBaseException {

		List<EtudiantNote> etudiantList = model.getLignes();
		GestionNotes gsNotes = GestionNotes.getInstance();
		GestionModules gsModules = GestionModules.getInstance();

		Matiere lMat = null;
		try {
			lMat = gsModules.getMatiereByName(pIntitule);
		} catch (ElementNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (EtudiantNote it : etudiantList) {
			int idEtudinat = it.getId().intValue();
			Note note = it.getNote();

			if (note.getId().intValue() >= 0) {
			    gsNotes.updateNoteSN(lMat.getId().intValue(), idEtudinat, Integer.parseInt(ConfigurationLoader.MAPCONFIG.get(ConfigurationLoader.ANNEE_UNIV)), note.getNoteSN());
				gsNotes.updateNoteSR(lMat.getId().intValue(), idEtudinat, Integer.parseInt(ConfigurationLoader.MAPCONFIG.get(ConfigurationLoader.ANNEE_UNIV)), note.getNoteSR());
				
				
			} else {
				note.setAnnee(Integer.parseInt(ConfigurationLoader.MAPCONFIG.get(ConfigurationLoader.ANNEE_UNIV)));
				note.setSemestre(Integer.parseInt(ConfigurationLoader.MAPCONFIG.get(ConfigurationLoader.SEMESTRE)));
				gsNotes.SaveNote(note, lMat.getId().intValue(), idEtudinat);
			}

			
			
		}
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
		System.out.println(modifiedFlag);

	}

	public JTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(JTable dataTable) {
		this.dataTable = dataTable;
	}

	public TableStudentModel getModel() {
		return model;
	}

	public void setModel(TableStudentModel model) {
		this.model = model;
	}

}
