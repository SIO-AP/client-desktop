package model;

import java.util.ArrayList;

public class Party {

	private int idParty;
	private String name;
	private int idLeader;
	private int nbQuestion;
	private ArrayList<Player> playerList;
	private ArrayList<Question> groupQuestions;
	private String time;
	
	public Party() {};
	
	public Party(String name, int idLeader, int nbQuestion) {
		this.name = name;
		this.idLeader = idLeader;
		this.nbQuestion = nbQuestion;
	}
	
	
	public Party(String name, int idParty, int nbQuestion, String time) {
		this.name = name;
		this.nbQuestion = nbQuestion;
		this.time = time;
		this.idParty = idParty;
	}
	
	public Party(int idParty, String name, int idLeader, ArrayList<Player> playerList, ArrayList<Question> theQuestions, int nbQuestion) {
		this.idParty = idParty;
		this.name = name;
		this.idLeader = idLeader;
		this.playerList = playerList;
		this.groupQuestions = theQuestions;
		this.nbQuestion = nbQuestion;
	}

	public int getIdParty() {
		return idParty;
	}

	public void setIdParty(int idParty) {
		this.idParty = idParty;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIdLeader() {
		return idLeader;
	}

	public void setIdLeader(int idLeader) {
		this.idLeader = idLeader;
	}

	public int getNbQuestion() {
		return nbQuestion;
	}

	public void setNbQuestion(int nbQuestion) {
		this.nbQuestion = nbQuestion;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	

	
}
