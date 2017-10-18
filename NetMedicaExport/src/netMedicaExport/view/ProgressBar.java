package netMedicaExport.view;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class ProgressBar {
	
	public static JFrame frameProgressBar;
	public static JProgressBar progressBar;
	
	public ProgressBar() {
		frameProgressBar = new JFrame();
		frameProgressBar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container content = frameProgressBar.getContentPane();
		progressBar = new JProgressBar();
		progressBar.setBounds(40, 40, 150, 27);
		progressBar.setIndeterminate(true);
		content.add(progressBar, BorderLayout.NORTH);
		frameProgressBar.setTitle("Processo in corso...");
		frameProgressBar.setSize(300, 100);
		frameProgressBar.setVisible(true);
	}
}


