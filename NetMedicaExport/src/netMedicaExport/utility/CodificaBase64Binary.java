package netMedicaExport.utility;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;

public class CodificaBase64Binary {
		
	public static byte[] encodedBytes;
		
		public CodificaBase64Binary(){

			//20170112_1858_b_b_19701221.vs
			StringBuilder result = new StringBuilder("");
			File file = new File("resources/referto-venoscreen.pdf");
			try (Scanner scanner = new Scanner(file)) {
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					result.append(line).append("\n");
				}
				scanner.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			encodedBytes= Base64.getEncoder().encode(result.toString().getBytes());
			System.out.println("encodedBytes \n" + new String(encodedBytes)+ "\n");
		  }
}

