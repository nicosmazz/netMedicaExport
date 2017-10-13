package netMedicaExport.view;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import netMedicaExport.layout.VerticalLayout;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.border.MatteBorder;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.Component;

import javax.swing.Box;

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
	private JTextField txtMarker1;
	private JTextField txtMarker2;
	private JButton btnAddMarker;
	private JPanel panelGraphs;
	private JScrollPane scrollPaneGraphs;
	private JPanel panelStatoE1Sx;
	private JPanel panelStatoE1Dx;
	private JPanel panelT0Valore;
	private JPanel panelV0Valore;
	private JPanel panelT0Valore2;
	private JPanel panelV0Valore2;
	private JPanel panelDatiDx;
	private JLabel lblValoreOrario;
	private JButton btnPulisci;
	private JButton btnExport;

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
		panelDati.setAlignmentX(RIGHT_ALIGNMENT);
		;
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
		
		JPanel panelBoxCenter = new JPanel();
		panelGraficoValori.add(panelBoxCenter, BorderLayout.CENTER);
		panelBoxCenter.setLayout(new BoxLayout(panelBoxCenter, BoxLayout.X_AXIS));

		panelGrafico = new JPanel();
		panelBoxCenter.add(panelGrafico);
		panelGrafico.setBackground(Color.BLUE);
		panelGrafico.setLayout(new BoxLayout(panelGrafico, BoxLayout.Y_AXIS));
		
		scrollPaneGraphs = new JScrollPane();
		panelGrafico.add(scrollPaneGraphs);
		
		panelGraphs = new JPanel();

		scrollPaneGraphs.setViewportView(panelGraphs);
		panelGraphs.setLayout(new BoxLayout(panelGraphs, BoxLayout.Y_AXIS));
	
		panelDatiDx = new JPanel();
		panelBoxCenter.add(panelDatiDx);
		panelDatiDx.setAlignmentX(RIGHT_ALIGNMENT);
		panelDatiDx.setLayout(new BoxLayout(panelDatiDx, BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(20);
		panelDatiDx.add(verticalStrut);
		
		JPanel panelValoriSx = new JPanel();
		panelDatiDx.add(panelValoriSx);
		panelValoriSx.setLayout(new GridLayout(0, 3, 0, 0));

		JPanel panelTitolo = new JPanel();
		panelTitolo.setBorder(new MatteBorder(1, 1, 1, 0, (Color) Color.BLUE));
		panelValoriSx.add(panelTitolo);

		JLabel lblSinistro = new JLabel("Sinistro");
		panelTitolo.add(lblSinistro);

		JPanel panelT0 = new JPanel();
		panelT0.setBorder(new MatteBorder(1, 0, 1, 0, (Color) Color.BLUE));
		panelValoriSx.add(panelT0);

		JLabel lblNewLabel = new JLabel("T0");
		panelT0.add(lblNewLabel);

		JPanel panelV0 = new JPanel();
		panelV0.setBorder(new MatteBorder(1, 0, 1, 1, (Color) Color.BLUE));
		panelValoriSx.add(panelV0);

		JLabel lblNewLabel_1 = new JLabel("V0");
		panelV0.add(lblNewLabel_1);

		JPanel panelE1 = new JPanel();
		panelValoriSx.add(panelE1);
		
		JLabel lblE1 = new JLabel("E1");
		panelE1.add(lblE1);

		panelT0Valore = new JPanel();
		panelValoriSx.add(panelT0Valore);

		panelV0Valore = new JPanel();
		panelValoriSx.add(panelV0Valore);

		panelStatoE1Sx = new JPanel();
		panelDatiDx.add(panelStatoE1Sx);

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
		FlowLayout fl_panelOrarioa = (FlowLayout) panelOrarioa.getLayout();
		fl_panelOrarioa.setAlignment(FlowLayout.LEFT);
		panelDettaglioEsame.add(panelOrarioa);

		lblValoreOrario = new JLabel("");
		panelOrarioa.add(lblValoreOrario);

		JPanel panelValoriDx = new JPanel();
		panelDatiDx.add(panelValoriDx);
		panelValoriDx.setLayout(new GridLayout(0, 3, 0, 0));

		JPanel panelTitolo2 = new JPanel();
		panelTitolo2.setBorder(new MatteBorder(1, 1, 1, 0, (Color) Color.YELLOW));
		panelValoriDx.add(panelTitolo2);

		JLabel lblDestro = new JLabel("Destro");
		panelTitolo2.add(lblDestro);

		JPanel panelT02 = new JPanel();
		panelT02.setBorder(new MatteBorder(1, 0, 1, 0, (Color) Color.YELLOW));
		panelValoriDx.add(panelT02);

		JLabel lblT0 = new JLabel("T0");
		panelT02.add(lblT0);

		JPanel panelV02 = new JPanel();
		panelV02.setBorder(new MatteBorder(1, 0, 1, 1, (Color) Color.YELLOW));
		panelValoriDx.add(panelV02);

		JLabel lblV0 = new JLabel("V0");
		panelV02.add(lblV0);

		JPanel panelE12 = new JPanel();
		panelValoriDx.add(panelE12);
		
		JLabel lblE12 = new JLabel("E1");
		panelE12.add(lblE12);

		panelT0Valore2 = new JPanel();
		panelValoriDx.add(panelT0Valore2);

		panelV0Valore2 = new JPanel();
		panelValoriDx.add(panelV0Valore2);

		panelStatoE1Dx = new JPanel();
		panelDatiDx.add(panelStatoE1Dx);

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

		btnExport = new JButton("Export");
		btnExport.setEnabled(false);
		panelSouth.add(btnExport);

		btnPulisci = new JButton("Pulisci");
		btnPulisci.setEnabled(false);
		panelSouth.add(btnPulisci);
	}

	public JPanel getPanelStatoE1Sx() {
		return panelStatoE1Sx;
	}

	public JPanel getPanelStatoE1Dx() {
		return panelStatoE1Dx;
	}

	public JPanel getPanelCenter() {
		return panelCenter;
	}

	public JPanel getPanelGraficoValori() {
		return panelGraficoValori;
	}

	public JPanel getPanelGraphs() {
		return panelGraphs;
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
	
	public JTextField getTxtMarker1() {
		return txtMarker1;
	}

	public JTextField getTxtMarker2() {
		return txtMarker2;
	}

	public JButton getBtnAddMarker() {
		return btnAddMarker;
	}

	public JScrollPane getScrollPaneGraphs() {
		return scrollPaneGraphs;
	}

	public JPanel getPanelT0Valore() {
		return panelT0Valore;
	}

	public JPanel getPanelV0Valore() {
		return panelV0Valore;
	}

	public JPanel getPanelT0Valore2() {
		return panelT0Valore2;
	}

	public JPanel getPanelV0Valore2() {
		return panelV0Valore2;
	}

	public JPanel getPanelDatiDx() {
		return panelDatiDx;
	}

	public JLabel getLblValoreOrario() {
		return lblValoreOrario;
	}

	public JButton getBtnExport() {
		return btnExport;
	}

	public JButton getBtnPulisci() {
		return btnPulisci;
	}

}
