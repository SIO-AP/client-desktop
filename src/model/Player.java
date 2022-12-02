package model;

import controller.Controller;

public class Player {
	
	private Controller monCtroller;
	private String myName;
	private int myScore;
	private int aGroupId;

	public Player(Controller aController, String aName, int aScore) {
		this.monCtroller = aController;
		this.myName = aName;
		this.myScore = aScore;				
	}
	
	public Player(Controller aController, String aName, int aScore, int groupId) {
		this.monCtroller = aController;
		this.myName = aName;
		this.myScore = aScore;				
		this.aGroupId = groupId;
	}
	
	public int getaGroupId() {
		return aGroupId;
	}

	public void setaGroupId(int aGroupId) {
		this.aGroupId = aGroupId;
	}

	public Controller getMonCtroller() {
		return monCtroller;
	}

	public void setMonCtroller(Controller monCtroller) {
		this.monCtroller = monCtroller;
	}

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public int getMyScore() {
		return myScore;
	}

	public void setMyScore(int myScore) {
		this.myScore = myScore;
	}
	
	
}
