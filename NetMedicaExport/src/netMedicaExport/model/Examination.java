package netMedicaExport.model;

import java.util.ArrayList;
import java.util.Date;

public class Examination {
	
	public Date dateExamination;
	public String timeExamination;
	public String info;
	public Boolean isDemo;
	public String note;
	public int duration;
	public int refillEndPosL;
	public int refillEndPosR;
	public int exerciseEndPos;
	public int verst;
	public int offsetL;
	public int offsettR;
	public ArrayList<Double> dataL;
	
	public Examination(Date dateExamination, String timeExamination, String info, Boolean isDemo, String note, int duration, int refillEndPosL, int refillEndPosR, int exerciseEndPos, int verst,
			int offsetL, int offsettR, ArrayList<Double> dataL, ArrayList<Double> dataR) {
		this.dateExamination = dateExamination;
		this.timeExamination = timeExamination;
		this.info = info;
		this.isDemo = isDemo;
		this.note = note;
		this.duration = duration;
		this.refillEndPosL = refillEndPosL;
		this.refillEndPosR = refillEndPosR;
		this.exerciseEndPos = exerciseEndPos;
		this.verst = verst;
		this.offsetL = offsetL;
		this.offsettR = offsettR;
		this.dataL = dataL;
		this.dataR = dataR;
	}
	
	public Date getDateExamination() {
		return dateExamination;
	}

	public String getTimeExamination() {
		return timeExamination;
	}

	public String getInfo() {
		return info;
	}

	public Boolean getIsDemo() {
		return isDemo;
	}

	public String getNote() {
		return note;
	}

	public int getDuration() {
		return duration;
	}

	public int getRefillEndPosL() {
		return refillEndPosL;
	}

	public int getRefillEndPosR() {
		return refillEndPosR;
	}

	public int getExerciseEndPos() {
		return exerciseEndPos;
	}

	public int getVerst() {
		return verst;
	}

	public int getOffsetL() {
		return offsetL;
	}

	public int getOffsettR() {
		return offsettR;
	}

	public ArrayList<Double> getDataL() {
		return dataL;
	}

	public ArrayList<Double> getDataR() {
		return dataR;
	}

	public ArrayList<Double> dataR;

	@Override
	public String toString() {
		return "Examination [dateExamination=" + dateExamination + ", timeExamination=" + timeExamination + ", info=" + info + ", isDemo=" + isDemo + ", note=" + note + ", duration=" + duration
				+ ", refillEndPosL=" + refillEndPosL + ", refillEndPosR=" + refillEndPosR + ", exerciseEndPos=" + exerciseEndPos + ", verst=" + verst + ", offsetL=" + offsetL + ", offsettR="
				+ offsettR + ", dataL=" + dataL + ", dataR=" + dataR + "]";
	}

	

}
