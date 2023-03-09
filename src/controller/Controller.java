package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.UnsupportedLookAndFeelException;

import data.ClientWebsocket;
import data.MySQLAccess;
import model.LesParty;
import model.Party;
import model.Player;
import model.Question;
import view.ConsoleGUI;

public class Controller {

	private MySQLAccess laBase;
	private LesParty lesParty;
	// specification
	// user interact with console
	private ConsoleGUI laConsole;
	// implementation
	// default constructor

	private Player monPlayer;

	// private QuizGame laGame;
	private ClientWebsocket leClient;
	private Party laParty;

	public Controller() {
		this.leClient = new ClientWebsocket(this);
		this.laBase = new MySQLAccess(this);
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

	public ArrayList<Integer> listeIdQuestion(int maxQuestions)
			throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {

		ArrayList<Integer> listeIdQuestion = new ArrayList<Integer>();
		int nombreTotalQuestion = laBase.nombreTotalQuestion();

		for (int i = 0; i < maxQuestions; i++) {
			int test = generateQuestionId(nombreTotalQuestion);

			if (listeIdQuestion.contains(test)) {
				while (listeIdQuestion.contains(test)) {
					test = generateQuestionId(nombreTotalQuestion);
				}
				listeIdQuestion.add(test);
			} else {
				listeIdQuestion.add(test);
			}
		}
		return listeIdQuestion;
	}

	public int generateQuestionId(int nombreTotalQuestion) {
		int randomNumber = new Random().nextInt(nombreTotalQuestion + 0);
		return randomNumber;
	}

	// public ArrayList<Party> getLesParty() {
	public void initLesParty() {
		leClient.searchGame();
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
		if (idPlayer != 0) {
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

	public LesParty getLesParty() {
		return lesParty;
	}

	public void setLesParty(LesParty lesParty) {
		this.lesParty = lesParty;
	}

}
