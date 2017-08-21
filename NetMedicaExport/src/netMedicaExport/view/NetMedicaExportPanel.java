package netMedicaExport.view;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;


import netMedicaExport.layout.VerticalLayout;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.border.MatteBorder;

public class NetMedicaExportPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblSesso;
	private JLabel lblPeso;
	private JLabel lblAltezza;
	private JLabel lblEt;
	private JLabel lblDataDiNascita;
	private JLabel lblCognomeNome;
	private JPanel panelGraficoValori;
	private JPanel panelCenter;
	private JPanel panelGrafico;
	private JLabel lblNote;

	public NetMedicaExportPanel() {
		setLayout(new BorderLayout(0, 0));
		
		panelCenter = new JPanel();
		panelCenter.setVisible(false);
		add(panelCenter);
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
		
		JPanel panelDescrizione = new JPanel();
		panelDescrizione.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panelCenter.add(panelDescrizione);
		panelDescrizione.setLayout(new BoxLayout(panelDescrizione, BoxLayout.X_AXIS));
		
		JPanel panelDescLeft = new JPanel();
		panelDescrizione.add(panelDescLeft);
		panelDescLeft.setLayout(new VerticalLayout(0, VerticalLayout.LEFT, VerticalLayout.TOP));
		
		JPanel panelEsame = new JPanel();
		panelEsame.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelDescLeft.add(panelEsame);
		panelEsame.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblEsame = new JLabel("Esame: Light Reflection Rheography");
		panelEsame.add(lblEsame);
		
		JPanel panelNome = new JPanel();
		panelNome.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelNome.setAlignmentX(RIGHT_ALIGNMENT);
		panelDescLeft.add(panelNome);
		panelNome.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblCognomeNome = new JLabel("Cognome, Nome:");
		panelNome.add(lblCognomeNome);
		
		JPanel panelDati = new JPanel();
		panelDati.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelDati.setAlignmentX(RIGHT_ALIGNMENT);;
		panelDescLeft.add(panelDati);
		panelDati.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblDataDiNascita = new JLabel("Data di nascita:");
		panelDati.add(lblDataDiNascita);
		
		lblEt = new JLabel("Et\u00E0:");
		panelDati.add(lblEt);
		
		lblAltezza = new JLabel("Altezza:");
		panelDati.add(lblAltezza);
		
		lblPeso = new JLabel("Peso:");
		panelDati.add(lblPeso);
		
		lblSesso = new JLabel("Sesso");
		panelDati.add(lblSesso);
		
		JPanel panelDescRight = new JPanel();
		panelDescRight.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelDescrizione.add(panelDescRight);
		panelDescRight.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblOspedalereparto = new JLabel("Ospedale/Reparto");
		panelDescRight.add(lblOspedalereparto);
		
		panelGraficoValori = new JPanel();
		panelCenter.add(panelGraficoValori);
		panelGraficoValori.setLayout(new BorderLayout(0, 0));
		
		panelGrafico = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelGrafico.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panelGraficoValori.add(panelGrafico, BorderLayout.CENTER);
		
		JPanel panelDatiDx = new JPanel();
		panelGraficoValori.add(panelDatiDx, BorderLayout.EAST);
		panelDatiDx.setLayout(new BoxLayout(panelDatiDx, BoxLayout.Y_AXIS));
		
		JPanel panelValoriSx = new JPanel();
		panelDatiDx.add(panelValoriSx);
		panelValoriSx.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panelTitolo = new JPanel();
		panelValoriSx.add(panelTitolo);
		
		JLabel lblSinistro = new JLabel("Sinistro");
		panelTitolo.add(lblSinistro);
		
		JPanel panelT0 = new JPanel();
		panelValoriSx.add(panelT0);
		
		JLabel lblNewLabel = new JLabel("T0");
		panelT0.add(lblNewLabel);
		
		JPanel panelV0 = new JPanel();
		panelValoriSx.add(panelV0);
		
		JLabel lblNewLabel_1 = new JLabel("V0");
		panelV0.add(lblNewLabel_1);
		
		JPanel panelE1 = new JPanel();
		panelValoriSx.add(panelE1);
		
		JPanel panelT0Valore = new JPanel();
		panelValoriSx.add(panelT0Valore);
		
		JPanel panelV0Valore = new JPanel();
		panelValoriSx.add(panelV0Valore);
		
		JPanel panelStatoE1Sx = new JPanel();
		panelDatiDx.add(panelStatoE1Sx);
		
		ImageIcon image = new ImageIcon("img/barraStato.png");
		JLabel labelImg = new JLabel("", image, JLabel.CENTER);
		panelStatoE1Sx.add(labelImg);
		
		JPanel panelDettaglioEsame = new JPanel();
		panelDatiDx.add(panelDettaglioEsame);
		panelDettaglioEsame.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		panelDettaglioEsame.add(panel);
		
		JLabel lblEsame_1 = new JLabel("Esame");
		panel.add(lblEsame_1);
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) panel_1.getLayout();
		flowLayout_4.setAlignment(FlowLayout.LEFT);
		panelDettaglioEsame.add(panel_1);
		
		JLabel lblOrario = new JLabel("Orario");
		panel_1.add(lblOrario);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_2.getLayout();
		flowLayout_3.setAlignment(FlowLayout.LEFT);
		panelDettaglioEsame.add(panel_2);
		
		JLabel lblEStandard = new JLabel("E1 Standard");
		panel_2.add(lblEStandard);
		
		JPanel panelOrarioa = new JPanel();
		FlowLayout flowLayout_5 = (FlowLayout) panelOrarioa.getLayout();
		flowLayout_5.setAlignment(FlowLayout.LEFT);
		panelDettaglioEsame.add(panelOrarioa);
		
		JLabel lblValoreOrario = new JLabel("");
		panelOrarioa.add(lblValoreOrario);
	
		JPanel panelValoriDx = new JPanel();
		panelDatiDx.add(panelValoriDx);
		panelValoriDx.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panelTitolo2 = new JPanel();
		panelValoriDx.add(panelTitolo2);
		
		JLabel lblDestro = new JLabel("Destro");
		panelTitolo2.add(lblDestro);
		
		JPanel panelT02 = new JPanel();
		panelValoriDx.add(panelT02);
		
		JLabel lblT0 = new JLabel("T0");
		panelT02.add(lblT0);
		
		JPanel panelV02 = new JPanel();
		panelValoriDx.add(panelV02);
		
		JLabel lblV0 = new JLabel("V0");
		panelV02.add(lblV0);
		
		JPanel panelE12 = new JPanel();
		panelValoriDx.add(panelE12);
		
		JPanel panelT0Valore2 = new JPanel();
		panelValoriDx.add(panelT0Valore2);
		
		JPanel panelV0Valore2 = new JPanel();
		panelValoriDx.add(panelV0Valore2);
		
		JPanel panelStatoE1Dx = new JPanel();
		panelDatiDx.add(panelStatoE1Dx);
		
		JLabel lblImg2 = new JLabel("", image, JLabel.CENTER);
		panelStatoE1Dx.add(lblImg2);
		
		JPanel panelDidascalia = new JPanel();
		panelDatiDx.add(panelDidascalia);
		panelDidascalia.setLayout(new BoxLayout(panelDidascalia, BoxLayout.Y_AXIS));
		panelDidascalia.setAlignmentX(RIGHT_ALIGNMENT);
		
		JLabel lblNewLabel_2 = new JLabel("T0 Tempo di riempimento");
		panelDidascalia.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("V0 Sangue Movimentato");
		panelDidascalia.add(lblNewLabel_3);
		
		JPanel panelNote = new JPanel();
		FlowLayout flowLayout_6 = (FlowLayout) panelNote.getLayout();
		flowLayout_6.setAlignment(FlowLayout.LEFT);
		panelGraficoValori.add(panelNote, BorderLayout.SOUTH);
		
		lblNote = new JLabel("Note:");
		panelNote.add(lblNote);
		
		JPanel panelSouth = new JPanel();
		panelSouth.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		FlowLayout flowLayout = (FlowLayout) panelSouth.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panelSouth, BorderLayout.SOUTH);
		
		JButton btnExport = new JButton("Export");
		panelSouth.add(btnExport);
		
		JButton btnAnnulla = new JButton("Annulla");
		panelSouth.add(btnAnnulla);
	}

	public JPanel getPanelCenter() {
		return panelCenter;
	}

	public JPanel getPanelGraficoValori() {
		return panelGraficoValori;
	}

	public JPanel getPanelGrafico() {
		return panelGrafico;
	}

	public JLabel getLblSesso() {
		return lblSesso;
	}

	public JLabel getLblPeso() {
		return lblPeso;
	}

	public JLabel getLblAltezza() {
		return lblAltezza;
	}

	public JLabel getLblEt() {
		return lblEt;
	}

	public JLabel getLblDataDiNascita() {
		return lblDataDiNascita;
	}

	public JLabel getLblCognomeNome() {
		return lblCognomeNome;
	}

}
