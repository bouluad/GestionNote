package com.gestion.note.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.gestion.note.bll.LireXML;
import com.gestion.note.db.DataBaseException;
import com.gestion.note.outils.FileFiltre;

public class ImportPanel extends JPanel {

	private JTextField fileNameText;

	private MainFrame parentFrame;
	
	private LireXML lireXML;
	
	private File file;

	/**
	 * Create the panel.
	 */
	
	public ImportPanel(MainFrame pFrame)
	{
		setBorder(BorderFactory.createTitledBorder(""));
		parentFrame = pFrame;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblImportationDesNotes = new JLabel(
				"Importation des notes d'une mati\u00E8re depuis un fichier XML");
		GridBagConstraints gbc_lblImportationDesNotes = new GridBagConstraints();
		gbc_lblImportationDesNotes.insets = new Insets(0, 0, 5, 0);
		gbc_lblImportationDesNotes.gridx = 1;
		gbc_lblImportationDesNotes.gridy = 0;
		add(lblImportationDesNotes, gbc_lblImportationDesNotes);

		lblImportationDesNotes.setFont(new Font("Book Antiqua", Font.BOLD, 14));
		
		JLabel lblChoisirLaMatire = new JLabel("Choisir la classe");
		GridBagConstraints gbc_lblChoisirLaMatire = new GridBagConstraints();
		gbc_lblChoisirLaMatire.insets = new Insets(0, 0, 5, 5);
		gbc_lblChoisirLaMatire.anchor = GridBagConstraints.WEST;
		gbc_lblChoisirLaMatire.gridx = 0;
		gbc_lblChoisirLaMatire.gridy = 1;
		add(lblChoisirLaMatire, gbc_lblChoisirLaMatire);

		JComboBox comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		add(comboBox, gbc_comboBox);

		JLabel lblChosirLaMatire = new JLabel("Chosir la mati\u00E8re");
		GridBagConstraints gbc_lblChosirLaMatire = new GridBagConstraints();
		gbc_lblChosirLaMatire.anchor = GridBagConstraints.WEST;
		gbc_lblChosirLaMatire.insets = new Insets(0, 0, 5, 5);
		gbc_lblChosirLaMatire.gridx = 0;
		gbc_lblChosirLaMatire.gridy = 2;
		add(lblChosirLaMatire, gbc_lblChosirLaMatire);

		JComboBox comboBox_1 = new JComboBox();
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 1;
		gbc_comboBox_1.gridy = 2;
		add(comboBox_1, gbc_comboBox_1);

		JLabel lblSession = new JLabel("Session");
		GridBagConstraints gbc_lblSession = new GridBagConstraints();
		gbc_lblSession.anchor = GridBagConstraints.EAST;
		gbc_lblSession.insets = new Insets(0, 0, 5, 5);
		gbc_lblSession.gridx = 0;
		gbc_lblSession.gridy = 3;
		add(lblSession, gbc_lblSession);

		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] { "Normale",
				"Rattrapage" }));
		GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
		gbc_comboBox_2.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_2.gridx = 1;
		gbc_comboBox_2.gridy = 3;
		add(comboBox_2, gbc_comboBox_2);

		JLabel lblNewLabel = new JLabel(
				"Entrer le chemin du fichier ou cliquer sur Parcourir pour importer le fichier des notes");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 4;
		add(lblNewLabel, gbc_lblNewLabel);

		JButton btnImporterLeFichier = new JButton("Parcourir");
		GridBagConstraints gbc_btnImporterLeFichier = new GridBagConstraints();
		gbc_btnImporterLeFichier.insets = new Insets(0, 0, 5, 5);
		gbc_btnImporterLeFichier.gridx = 0;
		gbc_btnImporterLeFichier.gridy = 5;
		add(btnImporterLeFichier, gbc_btnImporterLeFichier);

		fileNameText = new JTextField();
		GridBagConstraints gbc_fileNameText = new GridBagConstraints();
		gbc_fileNameText.insets = new Insets(0, 0, 5, 0);
		gbc_fileNameText.fill = GridBagConstraints.HORIZONTAL;
		gbc_fileNameText.gridx = 1;
		gbc_fileNameText.gridy = 5;
		add(fileNameText, gbc_fileNameText);
		fileNameText.setColumns(10);
		
		btnImporterLeFichier.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				FileFiltre mft = new FileFiltre( new String[]{"xml"},

                        "les fichiers texte (*.xml)");

				 JFileChooser choix = new JFileChooser();

				 choix. addChoosableFileFilter(mft);
				    int retour = choix.showOpenDialog(null);

				    if(retour == JFileChooser.APPROVE_OPTION) {

				    	file=choix.getSelectedFile();
				       fileNameText.setText(file.getAbsolutePath());

				    } else ;
				
			}
		});

		JButton btnImporter = new JButton("Importer");
		btnImporter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {

			lireXML=new LireXML(fileNameText.getText());
			afficherTable();

			}
		});
		GridBagConstraints gbc_btnImporter = new GridBagConstraints();
		gbc_btnImporter.gridx = 1;
		gbc_btnImporter.gridy = 6;
		add(btnImporter, gbc_btnImporter);

	}
	
	private void afficherTable(){
		TableStudentsPanel tableStudentsPanel = parentFrame.getTableStudentsPanel();
		
		parentFrame.getPanel().setLayout(new BorderLayout());
		parentFrame.addToCenterPanel(parentFrame.getPanel(),tableStudentsPanel);
		parentFrame.setTxtLabel( "Matière : "+lireXML.getTitre());

		TableStudentModel model = tableStudentsPanel.getModel();

		try {
			model.updae(lireXML.getEtudiantNotes());
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
