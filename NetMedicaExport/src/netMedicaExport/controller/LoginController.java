package netMedicaExport.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.soap.SOAPFaultException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import netMedicaExport.view.NetMedicaLogin;
import netMedicaExport.view.ProgressBar;
	
public class LoginController {
	
	public static String token;
	public static String directory="";
	public static String username="";
	public static String password="";
	public static String verificaErroreLogin=null;
	private NetMedicaLogin panelLogin;
	private ProgressBar barra;
	
			    
	public LoginController(){
		String soapEndpointUrl = "http://cloudtest.netmedicaitalia.it/wsdl.php";
		String soapAction = "urn:FIMMGwsdl#login";
		barra=new ProgressBar();
		panelLogin=new NetMedicaLogin();
		panelLogin.getBtnLogin().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				username=panelLogin.getTxtUser().getText();
				password=panelLogin.getTxtPassword().getText();
				directory=panelLogin.getTxtDirectory().getText();
				if((!username.equals(""))&&(!password.equals(""))&&(!directory.equals(""))){
					NetMedicaLogin.frameLogin.setVisible(false);
					NetMedicaLogin.frameLogin.dispose();
					callSoapWebService(soapEndpointUrl, soapAction);
				}
				else if ((username.equals(""))||(password.equals(""))||(directory.equals(""))){
					NetMedicaLogin.lblError.setVisible(true);
				}
				
			}
		});
		
	}

	private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
		SOAPPart soapPart = soapMessage.getSOAPPart();
		String login ="login";
		String user="user";
		String pass="pwd";
		String keyCartella="keycartella";
		SOAPEnvelope envelope = soapPart.getEnvelope();
		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement(login);
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement(user);
		soapBodyElem1.addTextNode(username);
		SOAPElement soapBodyElem2 = soapBodyElem.addChildElement(pass);
		soapBodyElem2.addTextNode(password);
		SOAPElement soapBodyElem3 = soapBodyElem.addChildElement(keyCartella);
		soapBodyElem3.addTextNode(directory);
	}

	private static void callSoapWebService(String soapEndpointUrl, String soapAction) {
		try { 
			Border border = BorderFactory.createTitledBorder("Login in corso...");
			ProgressBar.progressBar.setBorder(border);
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			// Send SOAP Message to SOAP Server
			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);
			System.out.println("Response SOAP Message:");
			verificaErroreLogin=faultCode(soapResponse);
			if(verificaErroreLogin!=null){
				JOptionPane.showMessageDialog(null, "Credenziali errate!");
			    ProgressBar.frameProgressBar.dispose();
			}
			else {
			    NodeList login = soapResponse.getSOAPBody().getElementsByTagName("ns1:loginResponse");
			 	Element elementBody = (Element) login.item(0);	 	            
			 	Node nodeToken =elementBody.getElementsByTagName("token").item(0);
			 	token=nodeToken.getTextContent();
			 	System.out.println(nodeToken.getTextContent()+"\n");
			 	SearchPazientiController search=new SearchPazientiController();
			}	
			soapConnection.close();
		}catch (SOAPFaultException e){
			try {
			    throw new Exception("Error " + e.getMessage() + " - please check that the Bouncy Castle provider is installed.", e);
			} catch (Exception e1) {
			    // TODO Auto-generated catch block
			    e1.printStackTrace();
			}
		}
		catch (Exception e) {
			System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
			e.printStackTrace();
		}
	}
			    
	private static String faultCode (SOAPMessage soapMessage) {
		String errorCode=null;
		boolean flag=false;
		try {
			flag=soapMessage.getSOAPBody().hasFault();
			if(flag==true){
				SOAPElement error= soapMessage.getSOAPBody().getFault();
			 	Node errorBody = error.getElementsByTagName("faultcode").item(0);
			 	errorCode=errorBody.getTextContent();
			}
		} catch (SOAPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return errorCode;
	}

	private static SOAPMessage createSOAPRequest(String soapAction) throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		createSoapEnvelope(soapMessage);
		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", soapAction);
		soapMessage.saveChanges();
		System.out.println("Request SOAP Message:");
		soapMessage.writeTo(System.out);
		System.out.println("\n");
		return soapMessage;
	}		
}




