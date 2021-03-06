package netMedicaExport.controller;

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

import org.w3c.dom.Node;
import netMedicaExport.view.ProgressBar;
	
public class ElencoRefertiController {
	
	private static String verificaErroreElencoReferti=null;
	private static String stringIdTipoDocumento;
	private static String stringIdEsito;
	private static String stringToken;
	private static String stringDirectory;
	private static String stringIdPaziente;
				    
	public ElencoRefertiController(String token, String directory, String idPaziente, String idTipoDocumento, String idEsito){
		stringToken=token;
		stringDirectory=directory;
		stringIdPaziente=idPaziente;
		stringIdTipoDocumento=idTipoDocumento;
		stringIdEsito=idEsito;
		String soapEndpointUrl = "http://cloud.fimmg.org/wsdl.php";
		String soapAction = "urn: FIMMGwsdl#elenco_RefertiPaziente";
		Border border = BorderFactory.createTitledBorder("Controllo elenco referti in corso...");
		ProgressBar.progressBar.setBorder(border);
		callSoapWebService(soapEndpointUrl, soapAction);
	}

	private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
		SOAPPart soapPart = soapMessage.getSOAPPart();
		String invioRefertoPaziente ="elenco_RefertiPaziente";
		String token="token";
		String keyCartella="keycartella";
		String idPaz="id_paziente";
		String idEsito1="id_esito";
		String idTipoDoc="id_tipodocumento";
		SOAPEnvelope envelope = soapPart.getEnvelope();
		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement(invioRefertoPaziente);
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement(token);
		soapBodyElem1.addTextNode(stringToken);
		SOAPElement soapBodyElem2 = soapBodyElem.addChildElement(keyCartella);
		soapBodyElem2.addTextNode(stringDirectory);
		SOAPElement soapBodyElem3 = soapBodyElem.addChildElement(idPaz);
		soapBodyElem3.addTextNode(stringIdPaziente);
		SOAPElement soapBodyElem4 = soapBodyElem.addChildElement(idEsito1);
		soapBodyElem4.addTextNode(stringIdEsito);
		SOAPElement soapBodyElem5 = soapBodyElem.addChildElement(idTipoDoc);
		soapBodyElem5.addTextNode(stringIdTipoDocumento);		
	}

	private static void callSoapWebService(String soapEndpointUrl, String soapAction) {
		try {
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			// Send SOAP Message to SOAP Server
			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);
			System.out.println("Response SOAP Message:");
			soapResponse.writeTo(System.out);
            System.out.println();
			verificaErroreElencoReferti=faultCode(soapResponse);
			if(verificaErroreElencoReferti!=null){
				ProgressBar.frameProgressBar.dispose();
				JOptionPane.showMessageDialog(null, "Errore connessione");
				
			}
			else {
				ProgressBar.frameProgressBar.dispose();
				JOptionPane.showMessageDialog(null, "Il referto � presente nell'elenco");				
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
