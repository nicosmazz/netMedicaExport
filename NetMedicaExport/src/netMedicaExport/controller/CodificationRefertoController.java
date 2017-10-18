package netMedicaExport.controller;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.Base64;
import javax.swing.JFileChooser;

import netMedicaExport.view.NetMedicaExportPanel;

public class CodificationRefertoController {

	public BufferedImage bImage;
	
	public CodificationRefertoController(NetMedicaExportPanel view){
		
		BufferedImage immagineReferto = getImageFromComponent(view.getPanelDescrizione(), view.getPanelGraphs(), view.getPanelDatiDx(), view);
				
	    JFileChooser saveImg = new JFileChooser();
	    saveImg.setDialogTitle("Salva l'immagine del referto");
	    int retrival = saveImg.showSaveDialog(null);
	    if (retrival == JFileChooser.APPROVE_OPTION) {
	        try {
	        	try {
					ImageIO.write(immagineReferto, "jpg", new File(saveImg.getSelectedFile()+".jpg"));
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
			ImageIO.write(immagineReferto, "jpg", byteArrOS);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    byte[] encodedBytes= Base64.getEncoder().encode(byteArrOS.toByteArray());
	    System.out.println(encodedBytes.toString());
		new LoginController(encodedBytes);
	}
	
	private static BufferedImage getImageFromComponent(Component panelDescrizione, Component panelGraphs, Component panelValori, NetMedicaExportPanel view) {
        int totalHeight = 0;
	 	int totalWidth = 0;
        int x = 0;
        int y = 0;
	 	
        BufferedImage imgDescrizionePaziente = new BufferedImage(
	 			panelDescrizione.getWidth(), panelDescrizione.getHeight(), 
                BufferedImage.TYPE_INT_RGB);
        BufferedImage imgGraphs = new BufferedImage(
        		panelGraphs.getWidth(), panelGraphs.getHeight(), 
                BufferedImage.TYPE_INT_RGB);
        BufferedImage imgValori = new BufferedImage(
        		panelValori.getWidth(), panelValori.getHeight(), 
                BufferedImage.TYPE_INT_RGB);
        
        Graphics gDescrizione = imgDescrizionePaziente.createGraphics();
        Graphics gGraphs = imgGraphs.createGraphics();
        Graphics gValori = imgValori.createGraphics();
        panelDescrizione.paint(gDescrizione);
        panelGraphs.paint(gGraphs);
        panelValori.paint(gValori);
        gDescrizione.dispose();
        gGraphs.dispose();
        gValori.dispose();
        
        ArrayList<Image> images = new ArrayList<Image>();
        images.add(imgDescrizionePaziente);
        images.add(imgGraphs);
        images.add(imgValori);
        totalHeight += imgDescrizionePaziente.getHeight();
        totalHeight += imgGraphs.getHeight();
        totalWidth += imgDescrizionePaziente.getWidth();
        
        BufferedImage result = new BufferedImage(
        		totalWidth, totalHeight, 
                BufferedImage.TYPE_INT_RGB);
        Graphics gResult = result.getGraphics();
        for(Image image : images){
        	if(image.equals(imgValori)){
        		gResult.drawImage(image, x, y, totalWidth - imgGraphs.getWidth(), totalHeight - imgDescrizionePaziente.getHeight(), null);
        	}else{
        		gResult.drawImage(image, x, y, null);
        	}
        	
            x += image.getWidth(null);
            y += image.getHeight(null);
            if(x == totalWidth){
            	x = 0;
            }
            
            if(y == totalHeight){
            	y= imgDescrizionePaziente.getHeight();
            }
        }
        return result;
    }
}
