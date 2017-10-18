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
import netMedicaExport.view.NetMedicaSearch;
import netMedicaExport.view.ProgressBar;

public class SearchPazientiController {
	
	public static String verificaErroreSearch=null;
	public static String codiceFiscale="";
	public static String idPaziente="";
	private NetMedicaSearch panelSearch;

		public SearchPazientiController(){
			String soapEndpointUrl = "http://cloud.fimmg.org/wsdl.php";
		    String soapAction = "urn:FIMMGwsdl#search_pazienti";
		    panelSearch=new NetMedicaSearch();
			panelSearch.getBtnSearch().addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					codiceFiscale=panelSearch.getTxtCodicefiscale().getText();
					if(!codiceFiscale.equals("")){
						NetMedicaSearch.frameSearch.setVisible(false);
						NetMedicaSearch.frameSearch.dispose();
						callSoapWebService(soapEndpointUrl, soapAction);
					}
					else if (codiceFiscale.equals("")){
						NetMedicaSearch.lblError.setVisible(true);
					}
					
				}
			});     
		}

		private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
			SOAPPart soapPart = soapMessage.getSOAPPart();
		    String search ="search_pazienti";
		    String token="token";
		    String directory="keycartella";
		    String idPaz="id_paziente";
		    String cognome="cognome";
		    String nome="nome";
		    String codiceFisc="cfpaziente";
		    String codiceTessera="codicetessera";
		    String dataNascita="datadinascita";
		    String tipoRicerca="tiporicerca";
		    String utentiSel="utentisel";
		    SOAPEnvelope envelope = soapPart.getEnvelope();
		    SOAPBody soapBody = envelope.getBody();
		    SOAPElement soapBodyElem = soapBody.addChildElement(search);
		    SOAPElement soapBodyElem1 = soapBodyElem.addChildElement(token);
		    soapBodyElem1.addTextNode(LoginController.token);
		    SOAPElement soapBodyElem2 = soapBodyElem.addChildElement(directory);
		    soapBodyElem2.addTextNode(LoginController.directory);
		    SOAPElement soapBodyElem3 = soapBodyElem.addChildElement(idPaz);
		    soapBodyElem3.addTextNode("");
		    SOAPElement soapBodyElem4 = soapBodyElem.addChildElement(cognome);
		    soapBodyElem4.addTextNode("");
		    SOAPElement soapBodyElem5 = soapBodyElem.addChildElement(nome);
		    soapBodyElem5.addTextNode("");
		    SOAPElement soapBodyElem6 = soapBodyElem.addChildElement(codiceFisc);
		    soapBodyElem6.addTextNode(codiceFiscale);
		    SOAPElement soapBodyElem7 = soapBodyElem.addChildElement(codiceTessera);
		    soapBodyElem7.addTextNode("");
		    SOAPElement soapBodyElem8 = soapBodyElem.addChildElement(dataNascita);
		    soapBodyElem8.addTextNode("");
		    SOAPElement soapBodyElem9 = soapBodyElem.addChildElement(tipoRicerca);
		    soapBodyElem9.addTextNode("");
		    SOAPElement soapBodyElem10 = soapBodyElem.addChildElement(utentiSel);
		    soapBodyElem10.addTextNode("");
		}

		private static void callSoapWebService(String soapEndpointUrl, String soapAction) {
			try {
				Border border = BorderFactory.createTitledBorder("Ricerca in corso...");
				ProgressBar.progressBar.setBorder(border);
		        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		        SOAPConnection soapConnection = soapConnectionFactory.createConnection();
		        SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);
		        System.out.println("Response SOAP Message:");
		        soapResponse.writeTo(System.out);
		        System.out.println();
		        verificaErroreSearch=faultCode(soapResponse);
		        if(verificaErroreSearch!=null){
		        	ProgressBar.frameProgressBar.dispose();
		        	JOptionPane.showMessageDialog(null, "Paziente inesistente");
		        }
		        else {
		        	NodeList search = soapResponse.getSOAPBody().getElementsByTagName("ns1:search_pazientiResponse");
				 	Element elementBody = (Element) search.item(0);	 	            
				 	Node nodeIdPaziente =elementBody.getElementsByTagName("id_paziente").item(0);
				 	idPaziente=nodeIdPaziente.getTextContent();
				 	System.out.println(nodeIdPaziente.getTextContent()+"\n");
		            new InvioRefertoController();
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
