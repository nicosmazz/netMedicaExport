package netMedicaExport.model;

import java.util.Date;

public class Patient {

	public String firstName;
	public String lastName;
	public Date dateOfBirthday;
	public Boolean isDemoPath;
	public Examination examination;
	
	public Patient(String firstName, String lastName, Date dateOfBirthday, Boolean isDemoPath, Examination examination) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirthday = dateOfBirthday;
		this.isDemoPath = isDemoPath;
		this.examination = examination;
	}

	@Override
	public String toString() {
		return "Patient [firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirthday=" + dateOfBirthday + ", isDemoPath=" + isDemoPath + ", examination=" + examination + "]";
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Date getDateOfBirthday() {
		return dateOfBirthday;
	}

	public Boolean getIsDemoPath() {
		return isDemoPath;
	}

	public Examination getExamination() {
		return examination;
	}
	
	
	
}
