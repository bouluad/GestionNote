package com.gestion.note.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.log4j.Logger;

import com.gestion.note.bll.ElementNotFoundException;
import com.gestion.note.config.ConfigurationLoader;
import com.gestion.note.db.DBInstallation;
import com.gestion.note.db.DataBaseException;



public class MainFrame extends JFrame {

	/** Panel de la liste des étudiants */
	
	private TableStudentsPanel tableStudentsPanel;
	private ImportPanel importpanel;
	//affiche le nom d'un module ou d'une matière
	private JLabel lblHeader=new JLabel("");
	
	
	private DialogChoixClasse dialogChoixClasse;

	/** Menu de l'application */
	private AsnMenu asnMenu;

	private JButton validerButton;
	private JButton btnAnnuler;

	/** Logger pour la journalisation des messages */
	private final static Logger LOG = Logger.getLogger(MainFrame.class);

	JPanel panel;
	
	
	/* 
	 * Ajouter un importpanel au centre
	 */
	
    public void addToCenterPanel(JPanel panel,JPanel table)
    {
    	panel.removeAll();
    	panel.repaint();
    	JPanel pnlHeaderJPanel=new JPanel();
    	pnlHeaderJPanel.setLayout(new BorderLayout());
    	JLabel lblVrifierLesInformations = new JLabel(
				"V\u00E9rifier les informations ci-dessous et cliquer sur confirmer pour importer les notes dans la base de donn\u00E9es");
		
    	
    	pnlHeaderJPanel.add(lblVrifierLesInformations,BorderLayout.NORTH);
    	pnlHeaderJPanel.add(Box.createRigidArea(new Dimension (600,15)),BorderLayout.CENTER);
		pnlHeaderJPanel.add(lblHeader,BorderLayout.SOUTH);
		
		JPanel pnl=new JPanel();
		pnl.setLayout(new FlowLayout());
		pnl.add(pnlHeaderJPanel);
    	panel.add(pnlHeaderJPanel,BorderLayout.NORTH);
    	panel.add(Box.createRigidArea(new Dimension (15,15)),BorderLayout.WEST);
    	panel.add(Box.createRigidArea(new Dimension (15,15)),BorderLayout.EAST);
      	panel.add(Box.createRigidArea(new Dimension (15,15)),BorderLayout.SOUTH);
    	panel.add(table, BorderLayout.CENTER);
    	
    	
    	
    	
    	btnAnnuler = new JButton("Annuler");
    	validerButton=new JButton("Confirmer");
    	JPanel panelButton=new JPanel();
    	panelButton.add(btnAnnuler);
    	
    	btnAnnuler.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				getPanel().removeAll();
				im();
				validate();
			}
		});
    	
    	panelButton.add(Box.createRigidArea(new Dimension (15,15)));
    	panelButton.add(validerButton);
    	
    	validerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			System.out.println("button valide");
			String[] nameTab=lblHeader.getText().split(" : ");
			
			try {
				tableStudentsPanel.saveChanges(nameTab[1]);
				
				

			} catch (DataBaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ElementNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//****** ce que j'ai ajouter pour faire la redirection
			new JOptionPane().showMessageDialog(null, "l'enregistrement est faite avec succès", "Message",
					JOptionPane.INFORMATION_MESSAGE);
			
			getPanel().removeAll();
			im();
			validate();
			
			
			//*******
			
			}
		});
    	panel.add(panelButton,BorderLayout.SOUTH);
    	validate();
    }
    
    public void addToCenter(JPanel panel,JPanel table, JPanel models)
    {
    	panel.removeAll();
    	panel.repaint();
    	
    	JPanel pnlHeaderJPanel=new JPanel();
    	
	pnlHeaderJPanel.setLayout(new FlowLayout(FlowLayout.LEFT,350,0));
		//pnlHeaderJPanel.add(lblHeader);
		
		pnlHeaderJPanel.add(models);
    	
    	panel.add(pnlHeaderJPanel,BorderLayout.NORTH);
    	panel.add(Box.createRigidArea(new Dimension (15,15)),BorderLayout.WEST);
    	panel.add(Box.createRigidArea(new Dimension (15,15)),BorderLayout.EAST);
      	panel.add(Box.createRigidArea(new Dimension (15,15)),BorderLayout.SOUTH);
    	panel.add(table, BorderLayout.CENTER);
    	revalidate();
    }
    
    
	public MainFrame() {

		
		setLookAndFeel("Nimbus");
		tableStudentsPanel = new TableStudentsPanel();
		setTitle("Gestion des notes ENSAH");
		

		// Création du menu et de la barre d'outils
		asnMenu = new AsnMenu(this);
		setJMenuBar(asnMenu.getMenuBar());
		add(asnMenu.getToolBar(), BorderLayout.NORTH);

		// Définir une l'icon de l'application
		Image icone = Toolkit.getDefaultToolkit().getImage("resources/icons/appIcon.png");
		setIconImage(icone);
        
		
		//***********************************
			im();	

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



	
	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}


	public AsnMenu getAsnMenu() {
		return asnMenu;
	}

	public void setAsnMenu(AsnMenu asnMenu) {
		this.asnMenu = asnMenu;
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
	public TableStudentsPanel getTableStudentsPanel() {
		return tableStudentsPanel;
	}
	public void setTableStudentsPanel(TableStudentsPanel tableStudentsPanel) {
		this.tableStudentsPanel = tableStudentsPanel;
	}
	
	//***********************
	public void im()
	{
		
		JPanel pnlHeaderJPanel=new JPanel();
		
		pnlHeaderJPanel.add(Box.createRigidArea(new Dimension (2000,100)));
		
		importpanel=new ImportPanel(this);
		
		panel=new JPanel();

		panel.add(pnlHeaderJPanel,BorderLayout.NORTH);
		panel.add(importpanel,BorderLayout.CENTER);
		
		add(panel,BorderLayout.CENTER);
		
		revalidate();

	}

	public DialogChoixClasse getDialogChoixClasse() {
		return dialogChoixClasse;
	}

	public void setDialogChoixClasse(DialogChoixClasse dialogChoixClasse) {
		this.dialogChoixClasse = dialogChoixClasse;
	}


	
}
