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

public class NetMedicaInvioReferto extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JFrame frameInvio;
	private JPanel panelInsert;
	private JPanel panelInvio;
	private JPanel panelInsertIdTipoDocumento;
	private JPanel panelInsertIdentificativo;
	private JPanel panelInsertDataDocumento;
	private JPanel panelInsertIdEsito;
	public JPanel panelError;
	private JLabel lblIdTipoDocumento;
	private JLabel lblIdentificativo;
	private JLabel lblDataDocumento;
	private JLabel lblIdEsito;
	public static JLabel lblError;
	private JTextField txtIdTipoDocumento;
	private JTextField txtIdentificativo;
	private JTextField txtDataDocumento;
	private JTextField txtIdEsito;
	private JButton btnInvio;
	
	public NetMedicaInvioReferto(){
		
		panelInsert=new JPanel();
		panelInvio=new JPanel();
		panelInsertIdTipoDocumento=new JPanel();
		panelInsertIdentificativo=new JPanel();
		panelInsertDataDocumento=new JPanel();
		panelInsertIdEsito=new JPanel();
		panelError=new JPanel();
		frameInvio=new JFrame();
		
		frameInvio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container content = frameInvio.getContentPane();
		frameInvio.setSize(500, 300);
		frameInvio.setTitle("Invio del referto");
		frameInvio.setLocationRelativeTo(null);
		panelInsert.setLayout(new VerticalLayout(0, VerticalLayout.LEFT, VerticalLayout.TOP));
		content.add(panelInsert);
		
		lblIdTipoDocumento=new JLabel("Inserisci l'id del documento: ");
		panelInsertIdTipoDocumento.add(lblIdTipoDocumento);
		txtIdTipoDocumento=new JTextField(10);
		panelInsertIdTipoDocumento.add(txtIdTipoDocumento);
		panelInsert.add(panelInsertIdTipoDocumento);
		
		lblIdentificativo=new JLabel("Inserisci l'identificativo:       ");
		panelInsertIdentificativo.add(lblIdentificativo);
		txtIdentificativo=new JTextField(20);
		panelInsertIdentificativo.add(txtIdentificativo);
		panelInsert.add(panelInsertIdentificativo);
		
		lblDataDocumento=new JLabel("Inserisci la data:                  ");
		panelInsertDataDocumento.add(lblDataDocumento);
		txtDataDocumento=new JTextField(10);
		panelInsertDataDocumento.add(txtDataDocumento);
		panelInsert.add(panelInsertDataDocumento);
		
		lblIdEsito=new JLabel("Inserisci l'id dell'esito:          ");
		panelInsertIdEsito.add(lblIdEsito);
		txtIdEsito=new JTextField(10);
		panelInsertIdEsito.add(txtIdEsito);
		panelInsert.add(panelInsertIdEsito);
		
		lblError=new JLabel("Attenzione riempire tutti i campi!");
		lblError.setForeground(Color.red);
		panelError.add(lblError);
		lblError.setVisible(false);
		panelInsert.add(panelError);
		
		FlowLayout flowLayout = (FlowLayout) panelInvio.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		content.add(panelInvio, BorderLayout.SOUTH);
		
		btnInvio=new JButton("Invia");
		panelInvio.add(btnInvio);
		frameInvio.setVisible(true);
		
	}
	
	public JTextField getTxtIdTipoDocumento(){
		return txtIdTipoDocumento;
	}
	
	public JTextField getTxtIdentificativo(){
		return txtIdentificativo;
	}
	
	public JTextField getTxtDataDocumento(){
		return txtDataDocumento;
	}
	
	public JTextField getTxtIdEsito(){
		return txtIdEsito;
	}
	
	public JButton getBtnInvio(){
		return btnInvio;
	}
	
}

