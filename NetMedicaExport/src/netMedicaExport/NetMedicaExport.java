package netMedicaExport;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.UIManager;

import netMedicaExport.controller.NetMedicaExportController;
import netMedicaExport.view.NetMedicaExportPanel;

public class NetMedicaExport {

	private JFrame frame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Set cross-platform Java L&F
				 	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

					NetMedicaExport window = new NetMedicaExport();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public NetMedicaExport() {
		// Redirect System Streams to file
	    
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
	
		frame = new JFrame();
		frame.setTitle("NetMedica Export");
		frame.setBounds(100, 100, 1100, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		NetMedicaExportPanel netMedicaExportPanel = new NetMedicaExportPanel();
		frame.getContentPane().add(netMedicaExportPanel);
	    new NetMedicaExportController(netMedicaExportPanel, frame);
		
	
	}
}

