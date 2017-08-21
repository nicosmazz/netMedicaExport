package netMedicaExport.utility;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import netMedicaExport.controller.NetMedicaExportController;
import netMedicaExport.model.Examination;
import netMedicaExport.model.Patient;

public class SaxHandler extends DefaultHandler {

	boolean bLastName = false;
	String lastName;
	boolean bFirstName = false;
	String firstName;
	boolean bDateOfBirthday = false;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	Date dateOfBirthday;
	boolean bIsDemoPath = false;
	Boolean isDemoPath;
	boolean bDate = false;
	Date date;
	boolean bTime = false;
	String time;
	boolean bInfo = false;
	String info;
	boolean bIsDemo = false;
	Boolean isDemo;
	boolean bNote = false;
	String note;
	boolean bDuration = false;
	int duration;
	boolean bRefillEndPosL = false;
	int refillEndPosL;
	boolean bRefillEndPosR = false;
	int refillEndPosR;
	boolean bExerciseEndPos = false;
	int exerciseEndPos;
	boolean bVerst = false;
	int verst;
	boolean bOffsetL = false;
	int offsetL;
	boolean bOffsetR = false;
	int offsetR;
	boolean bData = false;
	StringBuffer data;
	NetMedicaExportController netMedicaController;
	
	

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

			if (qName.equalsIgnoreCase("LastName")) {
				bLastName = true;
			} else if (qName.equalsIgnoreCase("FirstName")) {
				bFirstName = true;
			} else if (qName.equalsIgnoreCase("DoB")) {
				bDateOfBirthday = true;
			} else if (qName.equalsIgnoreCase("IsDemoPat")) {
				bIsDemoPath = true;
			} else if (qName.equalsIgnoreCase("Date")) {
				bDate = true;
			} else if (qName.equalsIgnoreCase("Time")) {
				bTime = true;
			} else if (qName.equalsIgnoreCase("Info")) {
				bInfo = true;
			} else if (qName.equalsIgnoreCase("IsDemo")) {
				bIsDemo = true;
			} else if (qName.equalsIgnoreCase("Note")) {
				bNote = true;
			} else if (qName.equalsIgnoreCase("Duration")) {
				bDuration = true;
			} else if (qName.equalsIgnoreCase("RefillEndPosL")) {
				bRefillEndPosL = true;
			} else if (qName.equalsIgnoreCase("RefillEndPosR")) {
				bRefillEndPosR = true;
			} else if (qName.equalsIgnoreCase("ExerciseEndPos")) {
				bExerciseEndPos = true;
			} else if (qName.equalsIgnoreCase("Verst")) {
				bVerst = true;
			} else if (qName.equalsIgnoreCase("OffsetL")) {
				bOffsetL = true;
			} else if (qName.equalsIgnoreCase("OffsetR")) {
				bOffsetR = true;
			} else if (qName.equalsIgnoreCase("Data")) {
				bData = true;
				 data = new StringBuffer();
			}
			
			

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("Data")) {
			ArrayList<Double> dataL = new ArrayList<Double>();
			ArrayList<Double> dataR = new ArrayList<Double>();
			ArrayList<String> lines = new ArrayList<String>(Arrays.asList(data.toString().split("\\n")));
			lines.remove(0);
			for(String s: lines){
			    dataL.add(Double.valueOf(s.split("\t")[0]));
			    dataR.add(Double.valueOf(s.split("\t")[1]));
			}
		Examination examination = new Examination(date, time, info, isDemo, note, duration, refillEndPosL, refillEndPosR, exerciseEndPos, verst, offsetL, offsetR, dataL, dataR);
		Patient patient = new Patient(firstName, lastName, dateOfBirthday, isDemoPath, examination);
		netMedicaController.showExam(patient);
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {

		if (bLastName) {
			lastName = new String(ch, start, length);
			bLastName = false;
		} else if (bFirstName) {
			firstName = new String(ch, start, length);
			bFirstName = false;
		} else if (bDateOfBirthday) {
			try {
				dateOfBirthday = formatter.parse(new String(ch, start, length));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			bDateOfBirthday = false;
		} else if (bIsDemoPath) {
			isDemoPath = Boolean.valueOf(new String(ch, start, length));
			bIsDemoPath = false;
		} else if (bDate) {
			try {
				date = formatter.parse(new String(ch, start, length));
			} catch (Exception e) {
				e.printStackTrace();
			}
			bDate = false;
		} else if (bTime) {
			time = new String(ch, start, length);
			bTime = false;
		} else if (bInfo) {
			info = new String(ch, start, length);
			bInfo = false;
		} else if (bIsDemo) {
			isDemo = Boolean.valueOf(new String(ch, start, length));
			bIsDemo = false;
		} else if (bNote) {
			note = new String(ch, start, length);
			bNote = false;
		} else if (bDuration) {
			duration = Integer.valueOf(new String(ch, start, length));
			bDuration = false;
		} else if (bRefillEndPosL) {
			refillEndPosL = Integer.valueOf(new String(ch, start, length));
			bRefillEndPosL = false;
		} else if (bRefillEndPosR) {
			refillEndPosR = Integer.valueOf(new String(ch, start, length));
			bRefillEndPosR = false;
		} else if (bExerciseEndPos) {
			exerciseEndPos = Integer.valueOf(new String(ch, start, length));
			bExerciseEndPos = false;
		} else if (bVerst) {
			verst = Integer.valueOf(new String(ch, start, length));
			bVerst = false;
		} else if (bOffsetL) {
			offsetL = Integer.valueOf(new String(ch, start, length));
			bOffsetL = false;
		}  else if (bOffsetR) {
			offsetR = Integer.valueOf(new String(ch, start, length));
			bOffsetR = false;
		} else if (bData) {
			data.append(ch, start, length);
		}
		
	}

	public SaxHandler(NetMedicaExportController netMedicaController) {
		super();
		this.netMedicaController = netMedicaController;
		// TODO Auto-generated constructor stub
	}
}
