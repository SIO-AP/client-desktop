package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

import control.PnlWaitingRoom;
import control.TablePlayer;
import data.ClientWebsocket;
import data.MySQLAccess;
import enpoints.Message;
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
		this.laBase = new MySQLAccess(this);
		this.laConsole = new ConsoleGUI(this);
		this.laConsole.setVisible(true);
		this.laConsole.setLocationRelativeTo(null);
		
	}

	public void selectOption(Message message) {
		if (message.getOption() == 2) { // Start game
			startGame();
		} else if (message.getOption() == 3) { // Impossible de rejoindre la game
			joinGameInpossible();
		} else if (message.getOption() == 4) {
			setListPlayerGame(message);
		}

	}

	private void setListPlayerGame(Message message) {
		laParty.setPlayerList(message.getLesPlayer());
		if (laConsole.isWaitingScreen()) {
			try {
				laConsole.getPnlWaitingRoom().getTablePlayer().setVisible(false);
				laConsole.getPnlWaitingRoom().remove(laConsole.getPnlWaitingRoom().getTablePlayer());
				laConsole.getPnlWaitingRoom().setTablePlayer(new TablePlayer(this, 10, 47, 658, 246));
				laConsole.getPnlWaitingRoom().add(laConsole.getPnlWaitingRoom().getTablePlayer());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			if (laConsole.isBlPnlResultAnswer()) {
				try {
					laConsole.getPnlResultAnswer().getTablePlayer().setVisible(false);
					laConsole.getPnlResultAnswer().remove(laConsole.getPnlResultAnswer().getTablePlayer());
					laConsole.getPnlResultAnswer().setTablePlayer(new TablePlayer(this, 139, 290, 400, 150));
					laConsole.getPnlResultAnswer().add(laConsole.getPnlResultAnswer().getTablePlayer());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					laConsole.getPnlDisplayQuiz().getTablePlayer().setVisible(false);
					laConsole.getPnlDisplayQuiz().remove(laConsole.getPnlDisplayQuiz().getTablePlayer());
					laConsole.getPnlDisplayQuiz().setTablePlayer(new TablePlayer(this, 257, 330, 400, 100));
					laConsole.getPnlDisplayQuiz().add(laConsole.getPnlDisplayQuiz().getTablePlayer());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	private void joinGameInpossible() {
		JOptionPane.showMessageDialog(laConsole,
				"<html><center>La partie que vous souhaitez rejoindre a déjà commencé ou est fini,<br>il est donc impossible de la rejoindre."
						+ "<br>Veuillez sélectionner une autre partie.",
				"Impossible de rejoindre la partie", JOptionPane.ERROR_MESSAGE);

		laConsole.setReloadJoinGame(true);
		initLesParty();

	}

	private void startGame() {
		laConsole.getPnlWaitingRoom().setVisible(false);
		laConsole.getPane().remove(laConsole.getPnlWaitingRoom());
		laConsole.setPnlWaitingRoom(null);
		
		laConsole.setWaitingScreen(false);

		int nbQuestion = getLaParty().getNbQuestion();
		laConsole.lancementQuiz(nbQuestion, true);
	}

	public void startGameFromServer() {
		leClient.launchGame(laParty.getIdParty());

	}

	public Boolean isCorrectThisAnswer(Question question, int idAnswer) {
		if (question.getAnswers().get(idAnswer).getIsCorrect()) {
			this.monPlayer.setMyScore(this.monPlayer.getMyScore() + 10);
			leClient.getClient().sendTCP(new Message(5, laParty.getIdParty(), monPlayer));
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
			int idRandom = generateQuestionId(nombreTotalQuestion);

			if (listeIdQuestion.contains(idRandom)) {
				while (listeIdQuestion.contains(idRandom)) {
					idRandom = generateQuestionId(nombreTotalQuestion);
				}
				listeIdQuestion.add(idRandom);
			} else {
				listeIdQuestion.add(idRandom);
			}
		}
		return listeIdQuestion;
	}

	public int generateQuestionId(int nombreTotalQuestion) {
		int randomNumber = new Random().nextInt(nombreTotalQuestion + 0);
		return randomNumber;
	}

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

	public boolean verification(String name, String password) {
		int idPlayer = laBase.verifLogin(name, password);
		if (idPlayer != 0) {
			this.monPlayer = new Player(idPlayer, name, 0);
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
