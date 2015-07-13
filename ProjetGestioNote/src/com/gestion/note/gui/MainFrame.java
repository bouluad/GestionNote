package com.gestion.note.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.log4j.Logger;

import com.gestion.note.config.ConfigurationLoader;
import com.gestion.note.db.DBInstallation;
import com.gestion.note.db.DataBaseException;



public class MainFrame extends JFrame {

	/** Panel de la liste des étudiants */
	
	private TableStudentsPanel tableStudentsPanel;
	//affiche le nom d'un module ou d'une matière
	private JLabel lblHeader=new JLabel("");

	/** Menu de l'application */
	private AsnMenu asnMenu;

	/** Boite de dialogue */
	private DialogChoixMatiere dialogMatiereSlection;
	private DialogChoixMatiere2 dialogMatiereSlection2;

	/** Logger pour la journalisation des messages */
	private final static Logger LOG = Logger.getLogger(MainFrame.class);

	JPanel panel;
	
	
	/* 
	 * Ajouter un TableStudentPanel au centre
	 */
	
    public void addToCenterPanel(JPanel panel,JPanel table)
    {
    	panel.removeAll();
    	
    	
    	JPanel pnlHeaderJPanel=new JPanel();
	
		pnlHeaderJPanel.add(lblHeader);
    	
    	panel.add(pnlHeaderJPanel,BorderLayout.NORTH);
    	panel.add(Box.createRigidArea(new Dimension (15,15)),BorderLayout.WEST);
    	panel.add(Box.createRigidArea(new Dimension (15,15)),BorderLayout.EAST);
      	panel.add(Box.createRigidArea(new Dimension (15,15)),BorderLayout.SOUTH);
    	panel.add(table, BorderLayout.CENTER);
    	validate();
    }
	public MainFrame() {

		
		setLookAndFeel("Nimbus");
		tableStudentsPanel = new TableStudentsPanel();
		setTitle("Aide à la saisie des notes ENSAH");
		

		// Création du menu et de la barre d'outils
		asnMenu = new AsnMenu(this);
		setJMenuBar(asnMenu.getMenuBar());
		add(asnMenu.getToolBar(), BorderLayout.NORTH);

		// Définir une l'icon de l'application
		Image icone = Toolkit.getDefaultToolkit().getImage("resources/icons/appIcon.png");
		setIconImage(icone);
        panel = new JPanel();
		panel.setLayout(new BorderLayout());
	
		lblHeader.setFont(new Font("Book Antiqua", Font.BOLD, 14));
		
		add(panel, BorderLayout.CENTER);

		// Propriétés de la fenetre
		
		Toolkit lTk=Toolkit.getDefaultToolkit();
		Dimension dimension =lTk.getScreenSize();
		
		setSize((int)(dimension.width/1.04), (int)(dimension.height/1.08));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);

	}

	private void setLookAndFeel(String pLandFName) {

		// Changer le L&F
		LookAndFeelInfo LF[];
		LF = UIManager.getInstalledLookAndFeels();
		for (LookAndFeelInfo lf : LF) {
			if (pLandFName.equals(lf.getName())) {
				try {
					UIManager.setLookAndFeel(lf.getClassName());
					break;
				} catch (Exception e1) {

					new JOptionPane().showMessageDialog(null, "the " + pLandFName
							+ " is not available, the GUI use the default look and feel", "Look and Feal not found",
							JOptionPane.WARNING_MESSAGE);
				}
				break;
			}
		}

	}

	public DialogChoixMatiere getDialogMatiereSlection() {
		return dialogMatiereSlection;
	}

	
	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public void setDialogMatiereSlection(DialogChoixMatiere dialogMatiereSlection) {
		this.dialogMatiereSlection = dialogMatiereSlection;
	}

	public AsnMenu getAsnMenu() {
		return asnMenu;
	}

	public void setAsnMenu(AsnMenu asnMenu) {
		this.asnMenu = asnMenu;
	}
	

	public DialogChoixMatiere2 getDialogMatiereSlection2() {
		return dialogMatiereSlection2;
	}

	public void setDialogMatiereSlection2(DialogChoixMatiere2 dialogMatiereSlection2) {
		this.dialogMatiereSlection2 = dialogMatiereSlection2;
	}

	public TableStudentsPanel getTableStudentsPanel() {
		return tableStudentsPanel;
	}

	public void setTableStudentsPanel(TableStudentsPanel tableStudentsPanel) {
		this.tableStudentsPanel = tableStudentsPanel;
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {

				// initaliser les paramètres de configuration
				try {
					ConfigurationLoader.load();
				} catch (IOException ex) {

					JOptionPane.showMessageDialog(null, "Echec d'initialisation des paramètres de l'application",
							"Error", JOptionPane.ERROR_MESSAGE);

					System.exit(-1);
				}

				DBInstallation dbInstall = null;
				try {
					dbInstall = new DBInstallation();
					// Créer la base de données s'elle n'existe pas
					dbInstall.installDB();

				} catch (DataBaseException e) {

					JOptionPane.showMessageDialog(null, "Echec d'installation de la base de données", "Error",
							JOptionPane.ERROR_MESSAGE);

					System.exit(-1);

				}

				try {
					new MainFrame();
				} catch (Throwable e) {

					LOG.error("Erreur à cause de l'exception:" + e);
					JOptionPane.showMessageDialog(null,
							"Erreur inconnue, Merci de nous envoyer le fichier Journal à tarikboudaa@yahoo.fr afin de corriger ce bug"
									+ e, "Error", JOptionPane.ERROR_MESSAGE);
					dbInstall.disconnect();
					LOG.info("Database shutdown ");
					System.exit(-1);
				}

			}
			
		});
	}

	public JLabel getLblHeader() {
		return lblHeader;
	}
	public void setLblHeader(JLabel lblHeader) {
		this.lblHeader = lblHeader;
	}
	
	public void setTxtLabel(String s)
	{
		lblHeader.setText(s);
		
	}

	public String getTextLblHeader() {
		return lblHeader.getText();
	}
	
}
