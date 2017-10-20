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

import org.w3c.dom.Node;
import netMedicaExport.view.NetMedicaInvioReferto;
import netMedicaExport.view.ProgressBar;


public class InvioRefertoController {
	
	private static String idTipoDocumento="";
	private static String identificativo="";
	private static String dataDocumento="";
	private static String idEsito="";
	private static String idReferto="";
	private static String verificaErroreInvio=null;
	private NetMedicaInvioReferto panelInvio;
	private static byte[] encodedBytes;
	private static String stringToken;
	private static String stringDirectory;
	private static String stringIdPaziente;
	
	public InvioRefertoController(byte[] bytes, String token,String directory, String idPaziente){
		encodedBytes = bytes;
		stringToken=token;
		stringDirectory=directory;
		stringIdPaziente=idPaziente;
		String soapEndpointUrl = "http://cloud.fimmg.org/wsdl.php";
		String soapAction = "urn: FIMMGwsdl#invio_RefertoPaziente";
		Border border = BorderFactory.createTitledBorder("Invio in corso...");
		ProgressBar.progressBar.setBorder(border);
		panelInvio=new NetMedicaInvioReferto();
		panelInvio.getBtnInvio().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				idTipoDocumento=panelInvio.getTxtIdTipoDocumento().getText();
				identificativo=panelInvio.getTxtIdentificativo().getText();
				dataDocumento=panelInvio.getTxtDataDocumento().getText();
				idEsito=panelInvio.getTxtIdEsito().getText();
				idReferto=panelInvio.getTxtIdEsito().getText();
				if((!idTipoDocumento.equals(""))&&(!identificativo.equals(""))&&(!dataDocumento.equals(""))&&(!idEsito.equals(""))&&(!idReferto.equals(""))){
					NetMedicaInvioReferto.frameInvio.setVisible(false);
					NetMedicaInvioReferto.frameInvio.dispose();
					callSoapWebService(soapEndpointUrl, soapAction);
				}
				else if ((idTipoDocumento.equals(""))||(identificativo.equals(""))||(dataDocumento.equals(""))||(idEsito.equals(""))||(idReferto.equals(""))){
					NetMedicaInvioReferto.lblError.setVisible(true);
				}
				
			}
		});     
	}

	private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
		SOAPPart soapPart = soapMessage.getSOAPPart();
		String invioRefertoPaziente ="invio_RefertoPaziente";
		String token="token";
		String keyCartella="keycartella";
		String idRef="id_referto";
		String idPaz="id_paziente";
		String idTipoDoc="id_tipodocumento";
		String identificativo1="identificativo";
		String dataDoc="data_documeto";
		String referto="referto";
		String idEsito1="id_esito";
		SOAPEnvelope envelope = soapPart.getEnvelope();
		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement(invioRefertoPaziente);
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement(token);
		soapBodyElem1.addTextNode(stringToken);
		SOAPElement soapBodyElem2 = soapBodyElem.addChildElement(keyCartella);
		soapBodyElem2.addTextNode(stringDirectory);
		SOAPElement soapBodyElem3 = soapBodyElem.addChildElement(idRef);
		soapBodyElem3.addTextNode(idReferto);
		SOAPElement soapBodyElem4 = soapBodyElem.addChildElement(idPaz);
		soapBodyElem4.addTextNode(stringIdPaziente);
		SOAPElement soapBodyElem5 = soapBodyElem.addChildElement(idTipoDoc);
		soapBodyElem5.addTextNode(idTipoDocumento);
		SOAPElement soapBodyElem6 = soapBodyElem.addChildElement(identificativo1);
		soapBodyElem6.addTextNode(identificativo);
		SOAPElement soapBodyElem7 = soapBodyElem.addChildElement(dataDoc);
		soapBodyElem7.addTextNode(dataDocumento);
		SOAPElement soapBodyElem8 = soapBodyElem.addChildElement(referto);
		soapBodyElem8.addTextNode(new String(encodedBytes));
		//System.out.println(new String(encodedBytes));
		SOAPElement soapBodyElem9 = soapBodyElem.addChildElement(idEsito1);
		soapBodyElem9.addTextNode(idEsito);
		
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
			verificaErroreInvio=faultCode(soapResponse);
			if(verificaErroreInvio!=null){
				JOptionPane.showMessageDialog(null, "Referto non inviato");
				ProgressBar.frameProgressBar.dispose();
			}
			else {
				new ElencoRefertiController(stringToken,stringDirectory,stringIdPaziente,idTipoDocumento,idEsito);
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




