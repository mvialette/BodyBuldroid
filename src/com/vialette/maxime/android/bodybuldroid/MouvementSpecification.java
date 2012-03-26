package com.vialette.maxime.android.bodybuldroid;

public class MouvementSpecification {

	private int id;

	private String name;

	private int charge;

	// how much time the exercice was sucessly done
	private int completeTime;

	public MouvementSpecification(String name, int charge, int completeTime) {
		super();
		this.name = name;
		this.charge = charge;
		this.completeTime = completeTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
