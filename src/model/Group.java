package model;

import java.util.ArrayList;

import controller.Controller;

public class Group {

	private Controller myController;
	private int idGroup;
	private String name;
	private ArrayList<Player> playerList;
	private ArrayList<Question> groupQuestions;
	
	public Group(Controller aController, int aGroup, String name, ArrayList<Player> playerList, ArrayList<Question> theQuestions) {
		this.myController = aController;
		this.idGroup = aGroup;
		this.name = name;
		this.playerList = playerList;
		this.groupQuestions = theQuestions;
	}
	
	
	
	public int getIdGroup() {
		return idGroup;
	}



	public ArrayList<Question> getGroupQuestions() {
		return groupQuestions;
	}

	public void setGroupQuestions(ArrayList<Question> groupQuestions) {
		this.groupQuestions = groupQuestions;
	}

	public Controller getMyController() {
		return myController;
	}

	public void setMyController(Controller myController) {
		this.myController = myController;
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
	
	
	
	
	
}
