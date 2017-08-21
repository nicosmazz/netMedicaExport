package netMedicaExport.controller;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import netMedicaExport.model.Examination;
import netMedicaExport.model.Patient;
import netMedicaExport.utility.SaxHandler;
import netMedicaExport.view.NetMedicaExportPanel;

public class NetMedicaExportController {
	private NetMedicaExportPanel view;
	// private JFrame frame;
	NetMedicaExportController netMedicaController;
	
	public NetMedicaExportController(final NetMedicaExportPanel view, JFrame frame) {
		this.view = view;
		// this.frame = frame;
		
		netMedicaController=this;
		
		JMenuBar menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmSelezionaFile = new JMenuItem("Seleziona file");
		// mntmSelezionaFile.setIcon(new ImageIcon(CheckIndirizziController.class.getResource("/it/fashionbank/fm/checkindirizzi/images/scegli-file.png")));
		JMenuItem mntmImport = new JMenuItem("Import");
		// mntmGeneraFiles.setIcon(new ImageIcon(CheckIndirizziController.class.getResource("/it/fashionbank/fm/checkindirizzi/images/export.png")));
		mnFile.add(mntmSelezionaFile);
		mnFile.add(mntmImport);

		mntmSelezionaFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				String userDir = System.getProperty("user.home");
				JFileChooser fileChooser = new JFileChooser(userDir + "/Desktop/IM");
				fileChooser.showOpenDialog(frame);
				File file = fileChooser.getSelectedFile();
				if (file != null) {
					try {
						SAXParserFactory factory = SAXParserFactory.newInstance();
						SAXParser saxParser = factory.newSAXParser();
						SaxHandler saxHandler = new SaxHandler(netMedicaController);
						saxParser.parse(file, saxHandler);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

	}

	public void showExam(Patient patient) {
		String pattern = "dd/MM/yyyy";
	    SimpleDateFormat format = new SimpleDateFormat(pattern);
		
	    view.getLblCognomeNome().setText(view.getLblCognomeNome().getText() + " " + patient.getLastName() + ", " + patient.getFirstName());
		view.getLblDataDiNascita().setText(view.getLblDataDiNascita().getText() + format.format(patient.getDateOfBirthday()));
		
		//view.getLblEt().setText(view.getLblEt().getText() + p.getYears());
		
		view.getPanelCenter().setVisible(true);
		String chartTitle = "Test del reflusso venoso";
		String xAxisLabel = "X";
		String yAxisLabel = "Y";

		XYDataset dataset = createDataset(patient);

		JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);
		ChartPanel myChart = new ChartPanel(chart);
		view.getPanelGrafico().add(myChart,FlowLayout.LEFT);
	}
	
	private XYDataset createDataset(Patient patient){
		Examination exam = patient.getExamination();
		int duration = exam.getDuration();
		ArrayList<Double> dataL = exam.getDataL();
		ArrayList<Double> dataR = exam.getDataR();
		double gap = duration / dataL.size();
		double start = 0;

		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series1 = new XYSeries("E1 Sinistro");
		XYSeries series2 = new XYSeries("E2 Destro");

		for (Double i : dataL) {
			series1.add(start, i);
			start += gap;
		}

		start = 0;
		for (Double i : dataR) {
			series2.add(start, i);
			start += gap;
		}

		dataset.addSeries(series1);
		dataset.addSeries(series2);
		return dataset;
	}

}
