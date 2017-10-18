package netMedicaExport.controller;


import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
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

import netMedicaExport.view.ProgressBar;

public class SearchPatologieController {

	private static String verificaErroreSearchPatologie=null;
	private static PrintWriter filePatologie;
	private static String stringIdPaziente;
	private static String stringToken;
	private static String stringDirectory;
	
		public SearchPatologieController(String token, String directory, String idPaziente){
			stringToken=token;
			stringDirectory=directory;
			stringIdPaziente=idPaziente;
			String soapEndpointUrl = "http://cloud.fimmg.org/wsdl.php";
			String soapAction = "urn: FIMMGwsdl#search_patologie";
			callSoapWebService(soapEndpointUrl, soapAction);
		}

		private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
			SOAPPart soapPart = soapMessage.getSOAPPart();
			String search ="search_patologie";
			String token="token";
			String directory="keycartella";
			String idPaz="id_paziente";
			SOAPEnvelope envelope = soapPart.getEnvelope();
			SOAPBody soapBody = envelope.getBody();
			SOAPElement soapBodyElem = soapBody.addChildElement(search);
			SOAPElement soapBodyElem1 = soapBodyElem.addChildElement(token);
			soapBodyElem1.addTextNode(stringToken);
			SOAPElement soapBodyElem2 = soapBodyElem.addChildElement(directory);
			soapBodyElem2.addTextNode(stringDirectory);
			SOAPElement soapBodyElem3 = soapBodyElem.addChildElement(idPaz);
			soapBodyElem3.addTextNode(stringIdPaziente);
		}

		private static void callSoapWebService(String soapEndpointUrl, String soapAction) {
			try {
				Border border = BorderFactory.createTitledBorder("Ricerca patologie in corso...");
				ProgressBar.progressBar.setBorder(border);
			    SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			    SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			    SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);
			    System.out.println("Response SOAP Message:");
			    soapResponse.writeTo(System.out);
			    System.out.println();
			    verificaErroreSearchPatologie=faultCode(soapResponse);
			    if(verificaErroreSearchPatologie!=null){
			    	ProgressBar.frameProgressBar.dispose();
			    	JOptionPane.showMessageDialog(null, "Non è presente alcuna patologia");
			    }
			    else {
			    	NodeList searchPatologie = soapResponse.getSOAPBody().getElementsByTagName("item");
			    	JFileChooser savePatologie = new JFileChooser();
				    savePatologie.setDialogTitle("Salva l'elenco delle patologie");
				    int retrival = savePatologie.showSaveDialog(null);
				    if (retrival == JFileChooser.APPROVE_OPTION) {
				        try {
				        	try {
				        		filePatologie=new PrintWriter(new FileWriter(savePatologie.getSelectedFile()+".txt"));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				        	
				        } catch (Exception ex) {
				            ex.printStackTrace();
				        }
				    }
			    	for (int i = 0; i < searchPatologie.getLength(); i++) 
			        {
			            Node nodePatologia = searchPatologie.item(i);
			            Element elementPatologia = (Element) nodePatologia;
			            Element elementIcd9 =  (Element) elementPatologia.getElementsByTagName("icd9_problema").item(0);
			            Element elementDescrProblema =  (Element) elementPatologia.getElementsByTagName("descr_problema").item(0);
			            filePatologie.println("Patologia n."+i);
			            filePatologie.println("ICD9 : " + elementIcd9.getTextContent());
			            filePatologie.println("Descrizione problema : " + elementDescrProblema.getTextContent());
			            
			        }
			    	filePatologie.close();
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
			 	        Node errorBodyCode =   error.getElementsByTagName("faultcode").item(0);
			 	        Node errorBodyString =   error.getElementsByTagName("faultstring").item(0);
			 	        errorCode=errorBodyCode.getTextContent()+" "+errorBodyString.getTextContent();
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


