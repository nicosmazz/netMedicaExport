package netMedicaExport.controller;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Base64;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;
import java.io.ByteArrayInputStream;

public class CodificationRefertoController {

	public BufferedImage bImage;
	public static byte[] encodedBytes;
	
	public CodificationRefertoController(JPanel panel){
		
	    bImage = new BufferedImage(panel.getWidth(),panel.getHeight(), BufferedImage.TYPE_INT_RGB);
	    Graphics2D g = bImage.createGraphics();
	    panel.paint(g);
		
	    JFileChooser saveImg = new JFileChooser();
	    saveImg.setDialogTitle("Salva l'immagine del referto");
	    int retrival = saveImg.showSaveDialog(null);
	    if (retrival == JFileChooser.APPROVE_OPTION) {
	        try {
	        	try {
					ImageIO.write(bImage, "jpg", new File(saveImg.getSelectedFile()+".jpg"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	    
	    ByteArrayOutputStream byteArrOS = new ByteArrayOutputStream();
	    try {
			ImageIO.write(bImage, "jpg", byteArrOS);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    encodedBytes= Base64.getEncoder().encode(byteArrOS.toByteArray());
		//System.out.println("encodedBytes \n" + new String(encodedBytes));
		new LoginController();
	 
	    
	}
	
}
