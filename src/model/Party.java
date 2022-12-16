package model;

import java.util.ArrayList;

public class Party {

	private int idGroup;
	private String name;
	private int leaderId;
	private ArrayList<Player> playerList;
	private ArrayList<Question> groupQuestions;
	
	public Party() {};
	
	public Party(int aGroup, String name, int aleaderId, ArrayList<Player> playerList, ArrayList<Question> theQuestions) {
		this.idGroup = aGroup;
		this.name = name;
		this.leaderId = aleaderId;
		this.playerList = playerList;
		this.groupQuestions = theQuestions;
	}

	

	public int getLeaderId() {
		return leaderId;
	}



	public void setLeaderId(int leaderId) {
		this.leaderId = leaderId;
	}



	public int getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(int idGroup) {
		this.idGroup = idGroup;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}

	public ArrayList<Question> getGroupQuestions() {
		return groupQuestions;
	}

	public void setGroupQuestions(ArrayList<Question> groupQuestions) {
		this.groupQuestions = groupQuestions;
	}
}
