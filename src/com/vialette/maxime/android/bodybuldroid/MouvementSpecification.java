package com.vialette.maxime.android.bodybuldroid;

public class MouvementSpecification {

	private int id;

	private String practiceName;

	private int charge;

	// how much time the exercice was sucessly done
	private int completeTime;
	
	private String serieName;
	
	public MouvementSpecification(String practiceName, int charge, int completeTime,String serieName) {
		super();
		this.practiceName = practiceName;
		this.charge = charge;
		this.completeTime = completeTime;
		this.serieName = serieName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPracticeName() {
		return practiceName;
	}

	public void setPracticeName(String practiceName) {
		this.practiceName = practiceName;
	}

	public int getCharge() {
		return charge;
	}

	public void setCharge(int charge) {
		this.charge = charge;
	}

	public int getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(int completeTime) {
		this.completeTime = completeTime;
	}

	public String getSerieName() {
		return serieName;
	}

	public void setSerieName(String serieName) {
		this.serieName = serieName;
	}
}
