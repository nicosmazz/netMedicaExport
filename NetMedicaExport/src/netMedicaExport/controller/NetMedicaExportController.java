package netMedicaExport.controller;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYLineAnnotation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import netMedicaExport.NetMedicaExport;
import netMedicaExport.model.Examination;
import netMedicaExport.model.Patient;
import netMedicaExport.utility.SaxHandler;
import netMedicaExport.view.NetMedicaExportPanel;

public class NetMedicaExportController {
	private NetMedicaExportPanel view;
	private boolean firstData = true;
	private JFreeChart chart;
	private JFreeChart chart2;
	private ChartPanel myChart;
	private ChartPanel myChart2;
	private boolean line1, line2, line3, line4 = false;
	private HashMap<String, Double> intersectionPoint = new HashMap<String, Double>();
	private JFrame frame;
	private DecimalFormat formatter = new DecimalFormat("#0.00");
	private NetMedicaExportController netMedicaController;

	public NetMedicaExportController(final NetMedicaExportPanel view, JFrame frame) {
		this.view = view;
		netMedicaController = this;

		JMenuBar menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmSelezionaFile = new JMenuItem("Seleziona file");
		mntmSelezionaFile.setIcon(new ImageIcon(NetMedicaExport.class.getResource("img/scegli-file.png")));
		mnFile.add(mntmSelezionaFile);

		mntmSelezionaFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				clearAll();
				String userDir = System.getProperty("user.home");
				//userDir + "/Desktop/IM"
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showOpenDialog(frame);
				File file = fileChooser.getSelectedFile();
				if (file != null) {
					try {
						SAXParserFactory factory = SAXParserFactory.newInstance();
						SAXParser saxParser = factory.newSAXParser();
						SaxHandler saxHandler = new SaxHandler(netMedicaController);
						saxParser.parse(file, saxHandler);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		view.getBtnPulisci().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clearAll();
			}
		});
	}

	public void showExam(Patient patient) {
		String pattern = "dd/MM/yyyy";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date dateOfBirthday = patient.getDateOfBirthday();
		int age = Period.between(dateOfBirthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()).getYears();
		
		view.getLblCognomeNome().setText(view.getLblCognomeNome().getText() + " " + patient.getLastName().substring(0, 1).toUpperCase() + patient.getLastName().substring(1) 
				+ ", " + patient.getFirstName().substring(0, 1).toUpperCase() + patient.getFirstName().substring(1));
		view.getLblDataDiNascita().setText(view.getLblDataDiNascita().getText() + " " + format.format(patient.getDateOfBirthday()));
		view.getLblValoreOrario().setText(patient.getExamination().getTimeExamination());
		view.getLblEt().setText(view.getLblEt().getText() + " " + String.valueOf(age));
		view.getPanelCenter().setVisible(true);
		
		String chartTitle = "Test del reflusso venoso";
		String xAxisLabel = "t(s)";
		String yAxisLabel = "V";

		XYDataset dataset = createDataset(patient);
		XYDataset dataset2 = createDataset(patient);

		chart = ChartFactory.createXYLineChart(chartTitle + " Destro", xAxisLabel, yAxisLabel, dataset);
		setGraphDetail(chart, Color.YELLOW);
		chart2 = ChartFactory.createXYLineChart(chartTitle + " Sinistro", xAxisLabel, yAxisLabel, dataset2);
		setGraphDetail(chart2, Color.BLUE);
		
		myChart = new ChartPanel(chart);
		myChart2 = new ChartPanel(chart2);
		
		
		view.getPanelGraphs().add(myChart, FlowLayout.LEFT);
		view.getPanelGraphs().add(myChart2, FlowLayout.LEFT);
		
		myChart.addChartMouseListener(new graphListener());
		myChart2.addChartMouseListener(new graphListener());
		
	}	

	private XYDataset createDataset(Patient patient) {
		XYSeries series1;
		ArrayList<Double> data;
		
		Examination exam = patient.getExamination();
		int duration = exam.getDuration();
		if(firstData){
			data = exam.getDataR();
			series1 = new XYSeries("E1 Destro");
			firstData = false;
			
		} else{
			data = exam.getDataL();
			series1 = new XYSeries("E1 Sinistro");
		}
		double gap = duration / data.size();
		double start = 0;

		XYSeriesCollection dataset = new XYSeriesCollection();

		for (Double i : data) {
			series1.add(start, i);
			start += gap/10;
		}

		dataset.addSeries(series1);
		return dataset;
	}
	
	private void setGraphDetail(JFreeChart chart, Color color){
		
		XYPlot plot = chart.getXYPlot();
		plot.getRenderer().setSeriesPaint(0, color);
		plot.setBackgroundPaint(Color.GRAY);
		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);
		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);
	}
	
	private class graphListener implements ChartMouseListener{

		@Override
		public void chartMouseClicked(ChartMouseEvent arg0) {
			
			ChartPanel chartPanel;
			if(arg0.getChart().equals(chart)){
				chartPanel = myChart;
				if(line1 == false){
					line1 = true;
					drawLine(chartPanel, arg0.getTrigger().getPoint(), arg0.getChart().getXYPlot());
				} else if (line1 == true && line2 == false){
					line2 = true;
					drawLine(chartPanel, arg0.getTrigger().getPoint(), arg0.getChart().getXYPlot());
				} else{
					int n = JOptionPane.showConfirmDialog(
						    frame,
						    "All'interno del grafico sono presenti il numero massimo di marker.\n" +
						    "Vuoi rimuovere gli attuali per aggiungerne degli altri?",
						    "Rimuovere i marker attuali dal grafico?",
						    JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.YES_OPTION){
						@SuppressWarnings("unchecked")
						List<XYLineAnnotation> anns = (List<XYLineAnnotation>)arg0.getChart().getXYPlot().getAnnotations();
						for(XYLineAnnotation ann : anns){
							arg0.getChart().getXYPlot().removeAnnotation(ann);
						}
						view.getPanelT0Valore2().removeAll();
						view.getPanelV0Valore2().removeAll();
						view.getPanelStatoE1Dx().removeAll();
						view.revalidate();
						view.repaint();
						line1 = false;
						line2= false;
						intersectionPoint.remove("graph1X1");
						intersectionPoint.remove("graph1Y1");
						intersectionPoint.remove("graph1X2");
						intersectionPoint.remove("graph1Y2");
					}
				}
			} else{
				chartPanel = myChart2;
				if(line3 == false){
					line3 = true;
					drawLine(chartPanel, arg0.getTrigger().getPoint(), arg0.getChart().getXYPlot());
				} else if (line3 == true && line4 == false){
					line4 = true;
					drawLine(chartPanel, arg0.getTrigger().getPoint(), arg0.getChart().getXYPlot());
				} else{
					int n = JOptionPane.showConfirmDialog(
						    frame,
						    "All'interno del grafico sono presenti il numero massimo di marker.\n" +
						    "Vuoi rimuovere gli attuali per aggiungerne degli altri?",
						    "Rimuovere i marker attuali dal grafico?",
						    JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.YES_OPTION){
						@SuppressWarnings("unchecked")
						List<XYLineAnnotation> anns = (List<XYLineAnnotation>)arg0.getChart().getXYPlot().getAnnotations();
						for(XYLineAnnotation ann : anns){
							arg0.getChart().getXYPlot().removeAnnotation(ann);
						}
						view.getPanelT0Valore().removeAll();
						view.getPanelV0Valore().removeAll();
						view.getPanelStatoE1Sx().removeAll();
						view.revalidate();
						view.repaint();
						line3 = false;
						line4 = false;
						intersectionPoint.remove("graph2X1");
						intersectionPoint.remove("graph2Y1");
						intersectionPoint.remove("graph2X2");
						intersectionPoint.remove("graph2Y2");
					}
				}
			}
		}

		@Override
		public void chartMouseMoved(ChartMouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	private void drawLine(ChartPanel chartPanel, Point point, XYPlot plot ){
		Point2D p = chartPanel.translateScreenToJava2D(point);
		Rectangle2D plotArea = chartPanel.getScreenDataArea();
		
		double chartX = Double.valueOf(formatter.format(plot.getDomainAxis().java2DToValue(p.getX(), plotArea, plot.getDomainAxisEdge())).replace("," , "."));
		double chartY = Double.valueOf(formatter.format(plot.getRangeAxis().java2DToValue(p.getY(), plotArea, plot.getRangeAxisEdge())).replace("," , "."));
		if(chartPanel.equals(myChart)){
			if(!intersectionPoint.containsKey("graph1X1")){
				intersectionPoint.put("graph1X1", chartX);
				intersectionPoint.put("graph1Y1", chartY);
			} else{
				intersectionPoint.put("graph1X2", chartX);
				intersectionPoint.put("graph1Y2", chartY);
				Thread thread = new Thread(new Runnable() {
					public void run() {
						calculateValues("graph1");
					}
				});
				thread.start();	
			}
		} else{
			if(!intersectionPoint.containsKey("graph2X1")){
				intersectionPoint.put("graph2X1", chartX);
				intersectionPoint.put("graph2Y1", chartY);
			} else{
				intersectionPoint.put("graph2X2", chartX);
				intersectionPoint.put("graph2Y2", chartY);
				calculateValues("graph2");
			}
		}
		
		XYLineAnnotation annotation=new XYLineAnnotation(chartX, chartY, chartX, chartY+30, new BasicStroke(3f), Color.RED);
		XYLineAnnotation annotation2=new XYLineAnnotation(chartX, chartY, chartX, chartY-30,  new BasicStroke(3f), Color.RED);
		plot.addAnnotation(annotation);
		plot.addAnnotation(annotation2);
	}
	
	private void calculateValues(String descr){
		double t0;
		double v0;
		
		if(descr.equals("graph1")){
			double x1 = intersectionPoint.get("graph1X1"); 
			double x2 = intersectionPoint.get("graph1X2");
			double y1 = intersectionPoint.get("graph1Y1");
			double y2 = intersectionPoint.get("graph1Y2");
			if (x1<x2){
				t0 = x2 - x1;
				v0 = ((y2 - y1) / 100); 
			} else{
				t0 = x1 - x2;
				v0 = ((y1 - y2) / 100);
			}
			showDetail("graph1", t0, v0);
			
		} else{
			double x1 = intersectionPoint.get("graph2X1"); 
			double x2 = intersectionPoint.get("graph2X2");
			double y1 = intersectionPoint.get("graph2Y1");
			double y2 = intersectionPoint.get("graph2Y2");
			if (x1<x2){
				t0 = x2 - x1;
				v0 = ((y2 - y1) / 100); 
			} else{
				t0 = x1 - x2;
				v0 = ((y1 - y2) / 100);
			}
			showDetail("graph2", t0, v0);
		}
	}
	
	private void showDetail(String descr, Double t0, Double v0){
		ImageIcon image;
		
		if(t0 == 0){
			image = new ImageIcon(NetMedicaExport.class.getResource("img/0.png"));
		} else if (t0 > 0 && t0 <= 10){
			image = new ImageIcon(NetMedicaExport.class.getResource("img/0X10.png"));
		} else if(t0 > 10 && t0 < 15) {
			image = new ImageIcon(NetMedicaExport.class.getResource("img/10X15.png"));
		} else if (t0 == 15){
			image = new ImageIcon(NetMedicaExport.class.getResource("img/15.png"));
		} else if (t0 > 15 && t0 < 20){
			image = new ImageIcon(NetMedicaExport.class.getResource("img/15X20.png"));
		} else if (t0 == 20){
			image = new ImageIcon(NetMedicaExport.class.getResource("img/20.png"));
		} else if (t0 > 20 && t0 < 25){
			image = new ImageIcon(NetMedicaExport.class.getResource("img/20X25.png"));
		} else if (t0 == 25){
			image = new ImageIcon(NetMedicaExport.class.getResource("img/25.png"));
		} else if (t0 > 25 && t0 < 30){
			image = new ImageIcon(NetMedicaExport.class.getResource("img/25X30.png"));
		} else if (t0 == 30){
			image = new ImageIcon(NetMedicaExport.class.getResource("img/30.png"));
		} else if (t0 > 30 && t0 < 35){
			image = new ImageIcon(NetMedicaExport.class.getResource("img/30X35.png"));
		} else if (t0 == 35){
			image = new ImageIcon(NetMedicaExport.class.getResource("img/35.png"));
		} else{
			image = new ImageIcon(NetMedicaExport.class.getResource("img/30X35.png"));
		}
		
		if(descr.equals("graph1")){
			view.getPanelT0Valore2().add(new JLabel(formatter.format(t0)));
			view.getPanelV0Valore2().add(new JLabel(formatter.format(v0)+"%"));
			view.getPanelStatoE1Dx().add(new JLabel("", image, JLabel.CENTER));
		} else{
			view.getPanelT0Valore().add(new JLabel(formatter.format(t0)));
			view.getPanelV0Valore().add(new JLabel(formatter.format(v0)+"%"));
			view.getPanelStatoE1Sx().add(new JLabel("", image, JLabel.CENTER));
		}
		view.getPanelDatiDx().revalidate();
		view.getPanelDatiDx().repaint();
	}
	
	private void clearAll(){
		view.getPanelGraphs().removeAll();
		view.getLblCognomeNome().setText("Cognome, Nome:");
		view.getLblDataDiNascita().setText("Data di nascita");
		view.getLblEt().setText("Età");
		view.getPanelT0Valore2().removeAll();
		view.getPanelV0Valore2().removeAll();
		view.getPanelStatoE1Dx().removeAll();
		view.getPanelT0Valore().removeAll();
		view.getPanelV0Valore().removeAll();
		view.getPanelStatoE1Sx().removeAll();
		view.getPanelCenter().setVisible(false);
		view.validate();
		view.repaint();
	}
}
