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

public class SearchFarmaciController {

	private static String verificaErroreSearchFarmaci=null;
	private static PrintWriter fileFarmaci;
	private static String stringIdPaziente;
	private static String stringToken;
	private static String stringDirectory;
	private static int i;
	
		public SearchFarmaciController(String token, String directory, String idPaziente){
			stringToken=token;
			stringDirectory=directory;
			stringIdPaziente=idPaziente;
			String soapEndpointUrl = "http://cloud.fimmg.org/wsdl.php";
			String soapAction = "urn:FIMMGwsdl#search_farmaci";
			Border border = BorderFactory.createTitledBorder("Ricerca farmaci in corso...");
			ProgressBar.progressBar.setBorder(border);
			callSoapWebService(soapEndpointUrl, soapAction);
				    
		}

		private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
			SOAPPart soapPart = soapMessage.getSOAPPart();
			String search ="search_farmaci";
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
			    SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			    SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			    SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);
			    System.out.println("Response SOAP Message:");
			    soapResponse.writeTo(System.out);
			    System.out.println();
			    verificaErroreSearchFarmaci=faultCode(soapResponse);
			    if(verificaErroreSearchFarmaci!=null){
			    	ProgressBar.frameProgressBar.dispose();
			    	JOptionPane.showMessageDialog(null, "Non è presente alcun farmaco");
			    }
			    else {
			    	ProgressBar.frameProgressBar.dispose();
			    	NodeList searchFarmaci = soapResponse.getSOAPBody().getElementsByTagName("item");
			    	JFileChooser saveFarmaci = new JFileChooser();
				    saveFarmaci.setDialogTitle("Salva l'elenco dei farmaci");
				    int retrival = saveFarmaci.showSaveDialog(null);
				    if (retrival == JFileChooser.APPROVE_OPTION) {
				        try {
				        	try {
				        		fileFarmaci=new PrintWriter(new FileWriter(saveFarmaci.getSelectedFile()+".txt"));
								
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				        	
				        } catch (Exception ex) {
				            ex.printStackTrace();
				        }
				    }
			    	for (i = 0; i < searchFarmaci.getLength(); i++) 
			        {
			            Node nodeFarmaco = searchFarmaci.item(i);
			            Element elementFarmaco = (Element) nodeFarmaco;
			            Element elementDescrMolecola =  (Element) elementFarmaco.getElementsByTagName("descrizmolecola").item(0);
			            Element elementDescrPrincipioAttivo =  (Element) elementFarmaco.getElementsByTagName("descrprinatt").item(0);
			            Element elementDescrFarmaco =  (Element) elementFarmaco.getElementsByTagName("descrfarm").item(0);
			            Element elementDataPrescr =  (Element) elementFarmaco.getElementsByTagName("dataprescr").item(0);
			            Element elementDescrdiagnosi =  (Element) elementFarmaco.getElementsByTagName("descrdiagnosi").item(0);
			            Element elementPosologia =  (Element) elementFarmaco.getElementsByTagName("posologia").item(0);
			            fileFarmaci.println("Farmaco n."+i);
			            fileFarmaci.println("Descrizione molecola : "+elementDescrMolecola.getTextContent());
			            fileFarmaci.println("Descrizione principio attivo : "+elementDescrPrincipioAttivo.getTextContent());
			            fileFarmaci.println("Descrizione farmaco : "+elementDescrFarmaco.getTextContent());
			            fileFarmaci.println("Data prescrizione : "+elementDataPrescr.getTextContent());
			            fileFarmaci.println("Descrizione diagnosi : "+elementDescrdiagnosi.getTextContent());
			            fileFarmaci.println("Posologia : "+elementPosologia.getTextContent());
			            
			        }
			    	fileFarmaci.close();
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

