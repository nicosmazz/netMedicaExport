package netMedicaExport.view;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.border.MatteBorder;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import netMedicaExport.layout.VerticalLayout;
import javax.swing.Box;

public class NetMedicaSearch extends JFrame{

	public static JFrame frameSearch;
	private JPanel panelSearch;
	private JPanel panelInsert;
	private JPanel panelInsertCodiceFiscale;
	public JPanel panelError;
	private JLabel lblCodiceFiscale;
	public static JLabel lblError;
	private JTextField txtCodiceFiscale;
	private JButton btnSearch;
	
	public NetMedicaSearch(){
		
		panelInsert=new JPanel();
		panelInsertCodiceFiscale=new JPanel();
		panelError=new JPanel();
		panelSearch=new JPanel();
		frameSearch=new JFrame();
		
		frameSearch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container content = frameSearch.getContentPane();
		frameSearch.setSize(500, 150);
		frameSearch.setTitle("Ricerca del paziente");
		frameSearch.setLocationRelativeTo(null);
		panelInsert.setLayout(new VerticalLayout(0, VerticalLayout.LEFT, VerticalLayout.TOP));
		content.add(panelInsert);
		
		lblCodiceFiscale=new JLabel("Inserisci il codice fiscale del paziente:");
		panelInsertCodiceFiscale.add(lblCodiceFiscale);
		txtCodiceFiscale=new JTextField(16);
		panelInsertCodiceFiscale.add(txtCodiceFiscale);
		panelInsert.add(panelInsertCodiceFiscale);
		
		lblError=new JLabel("Attenzione riempire tutti i campi!");
		lblError.setForeground(Color.red);
		panelError.add(lblError);
		lblError.setVisible(false);
		panelInsert.add(panelError);
		
		FlowLayout flowLayout = (FlowLayout) panelSearch.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		content.add(panelSearch, BorderLayout.SOUTH);
		
		btnSearch=new JButton("Cerca");
		panelSearch.add(btnSearch);
		frameSearch.setVisible(true);
		
	}
	
	public JTextField getTxtCodicefiscale(){
		return txtCodiceFiscale;
	}
	
	public JButton getBtnSearch(){
		return btnSearch;
	}
	
}

