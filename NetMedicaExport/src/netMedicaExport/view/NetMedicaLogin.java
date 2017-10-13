package netMedicaExport.view;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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

public class NetMedicaLogin extends JFrame{

	public static JFrame frameLogin;
	private JPanel panelLogin;
	private JPanel panelInsert;
	private JPanel panelInsertUser;
	private JPanel panelInsertPassword;
	private JPanel panelInsertDirectory;
	public JPanel panelError;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JLabel lblDirectory;
	public static JLabel lblError;
	private JTextField txtUser;
	private JPasswordField txtPassword;
	private JTextField txtDirectory;
	private JButton btnLogin;
	
	public NetMedicaLogin(){
		
		panelInsert=new JPanel();
		panelInsertUser=new JPanel();
		panelInsertPassword=new JPanel();
		panelInsertDirectory=new JPanel();
		panelError=new JPanel();
		panelLogin=new JPanel();
		frameLogin=new JFrame();
		
		frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container content = frameLogin.getContentPane();
		frameLogin.setSize(500, 250);
		frameLogin.setLocationRelativeTo(null);
		
		frameLogin.setTitle("Autenticazione");
		panelInsert.setLayout(new VerticalLayout(0, VerticalLayout.LEFT, VerticalLayout.TOP));
		content.add(panelInsert);
		
		lblUsername=new JLabel("Username: ");
		panelInsertUser.add(lblUsername);
		txtUser=new JTextField(16);
		panelInsertUser.add(txtUser);
		panelInsert.add(panelInsertUser);
		
		lblPassword=new JLabel("Password:  ");
		panelInsertPassword.add(lblPassword);
		txtPassword=new JPasswordField(16);
		panelInsertPassword.add(txtPassword);
		panelInsert.add(panelInsertPassword);
		
		lblDirectory=new JLabel("Keycartella:");
		panelInsertDirectory.add(lblDirectory);
		txtDirectory=new JTextField(16);
		panelInsertDirectory.add(txtDirectory);
		panelInsert.add(panelInsertDirectory);
		
		lblError=new JLabel("Attenzione riempire tutti i campi!");
		lblError.setForeground(Color.red);
		panelError.add(lblError);
		lblError.setVisible(false);
		panelInsert.add(panelError);
		
		FlowLayout flowLayout = (FlowLayout) panelLogin.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		content.add(panelLogin, BorderLayout.SOUTH);
		
		btnLogin=new JButton("Login");
		panelLogin.add(btnLogin);
		frameLogin.setVisible(true);
		
	}
	
	public JTextField getTxtUser(){
		return txtUser;
	}
	
	public JPasswordField getTxtPassword(){
		return txtPassword;
	}
	
	public JTextField getTxtDirectory(){
		return txtDirectory;
	}
	
	public JButton getBtnLogin(){
		return btnLogin;
	}
	
}
