package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.UnsupportedLookAndFeelException;

import data.ClientWebsocket;
import data.MySQLAccess;
import model.Party;
import model.Player;
import model.Question;
import model.QuizGame;
import view.ConsoleGUI;

public class Controller {
	
	private MySQLAccess laBase;

	//specification
	//user interact with console
	private ConsoleGUI laConsole;
	//implementation
	//default constructor
	
	private Player monPlayer;
	
	//private QuizGame laGame;
	private ClientWebsocket leClient;
	private Party laParty;
	
	public Controller() throws ParseException, UnsupportedLookAndFeelException, FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		this.leClient = new ClientWebsocket(this);
	//	leClient.test1();
		
		this.laBase = new MySQLAccess(this);
		this.laBase.connection();
		this.laConsole = new ConsoleGUI(this);
		this.laConsole.setVisible(true);
		this.laConsole.setLocationRelativeTo(null);
		
		
	}
	
	
    public Boolean isCorrectThisAnswer(Question question, int idAnswer) {
    	if (question.getAnswers().get(idAnswer).getIsCorrect()) {    		
    		this.monPlayer.setMyScore(this.monPlayer.getMyScore() + 10); 
			return true;
    	} else {
    		return false;
    	}
    }
	
	
	
	public Party getLaParty() {
		return laParty;
	}
	public void setLaParty(Party laParty) {
		this.laParty = laParty;
	}

	public MySQLAccess getLaBase() {
		return laBase;
	}
	public void setLaBase(MySQLAccess laBase) {
		this.laBase = laBase;
	}
//	public QuizGame getLaGame() {
//		return laGame;
//	}
//	public void setLaGame(QuizGame laGame) {
//		this.laGame = laGame;
//	}
	public ConsoleGUI getLaConsole() {
		return laConsole;
	}
	public void setLaConsole(ConsoleGUI laConsole) {
		this.laConsole = laConsole;
	}
	
	
	public Player getMonPlayer() {
		return monPlayer;
	}
	public void setMonPlayer(Player monPlayer) {
		this.monPlayer = monPlayer;
	}
	public boolean verification(String name, String password) throws SQLException, ParseException {		
		int idPlayer = laBase.verifLogin(name, password);
		if (idPlayer !=0) {
			this.monPlayer = new Player(this, idPlayer, name, 0);
			return true;
		}
		return false;
	}
	public ClientWebsocket getLeClient() {
		return leClient;
	}
	public void setLeClient(ClientWebsocket leClient) {
		this.leClient = leClient;
	}
	
	
	
	
	
}
