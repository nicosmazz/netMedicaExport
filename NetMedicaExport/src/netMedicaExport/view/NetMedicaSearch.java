package netMedicaExport.view;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;

import javax.swing.JTextField;
import java.awt.Container;
import netMedicaExport.layout.VerticalLayout;

public class NetMedicaSearch extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

