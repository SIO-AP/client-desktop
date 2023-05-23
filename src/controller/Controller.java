package controller;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import control.PnlDisplayQuiz;
import control.PnlEndQuiz;
import control.PnlGameMode;
import control.PnlLogin;
import control.PnlMultiCreateGame;
import control.PnlMultiGameMode;
import control.PnlMultiJoinGame;
import control.PnlResultAnswer;
import control.PnlSoloCreateGame;
import control.PnlWaitingRoom;
import control.TablePlayer;
import data.ClientWebsocket;
import data.MySQLAccess;
import enpoints.Message;
import model.Game;
import model.LesGame;
import model.Player;
import model.Question;
import view.ConsoleGUI;

public class Controller {

	private MySQLAccess laBase;
	private LesGame lesGames;
	private ConsoleGUI laConsole;
	private Player monPlayer;
	private ClientWebsocket leClient;
	private Game laGame;
	private Jasypt theDecrypter;

	private boolean isPlaying = false;

	private Question currentQuestion;
	private int numberOfQuestion;
	
	private boolean multi;
	private boolean createGameMulti;

	public Controller() {
		this.theDecrypter = new Jasypt();
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
		} else if (message.getOption() == 4) { // Mise à jour des scores
			setListPlayerGame(message);
		}
	}

	private void setListPlayerGame(Message message) {
		laGame.setPlayerList(message.getLesPlayer());
		if (laConsole.isWaitingScreen()) {
			try {
				laConsole.getPnlWaitingRoom().getTablePlayer().setVisible(false);
				laConsole.getPnlWaitingRoom().remove(laConsole.getPnlWaitingRoom().getTablePlayer());
				laConsole.getPnlWaitingRoom().setTablePlayer(new TablePlayer(this, 10, 47, 658, 246, false));
				laConsole.getPnlWaitingRoom().add(laConsole.getPnlWaitingRoom().getTablePlayer());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			int i = laConsole.getPnlListPlayerChange();
			switch (i) {
			case 1: {
				try {
					laConsole.getPnlDisplayQuiz().getTablePlayer().setVisible(false);
					laConsole.getPnlDisplayQuiz().remove(laConsole.getPnlDisplayQuiz().getTablePlayer());
					laConsole.getPnlDisplayQuiz().setTablePlayer(new TablePlayer(this, 60, 380, 630, 200, true));
					laConsole.getPnlDisplayQuiz().add(laConsole.getPnlDisplayQuiz().getTablePlayer());
					break;
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
			case 2: {
				try {
					laConsole.getPnlResultAnswer().getTablePlayer().setVisible(false);
					laConsole.getPnlResultAnswer().remove(laConsole.getPnlResultAnswer().getTablePlayer());
					laConsole.getPnlResultAnswer().setTablePlayer(new TablePlayer(this, 60, 300, 630, 200, true));
					laConsole.getPnlResultAnswer().add(laConsole.getPnlResultAnswer().getTablePlayer());
					break;
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}

			}
			case 3: {
				try {
					laConsole.getPnlEndQuiz().getTablePlayer().setVisible(false);
					laConsole.getPnlEndQuiz().remove(laConsole.getPnlEndQuiz().getTablePlayer());
					laConsole.getPnlEndQuiz().setTablePlayer(new TablePlayer(this, 545, 260, 520, 200, true));
					laConsole.getPnlEndQuiz().add(laConsole.getPnlEndQuiz().getTablePlayer());
					break;
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
			default:

			}

		}

	}

	private void joinGameInpossible() {
		JOptionPane.showMessageDialog(laConsole,
				"<html><center>La partie que vous souhaitez rejoindre a déjà commencé ou est fini,<br>il est donc impossible de la rejoindre."
						+ "<br>Veuillez sélectionner une autre partie.",
				"Impossible de rejoindre la partie", JOptionPane.ERROR_MESSAGE);

		laConsole.setReloadJoinGame(true);
		initLesGames();

	}

	public void lancementQuiz(int nbQuestion, Boolean multiplayer) {
		numberOfQuestion = nbQuestion;

		if (multiplayer) {
			startMultiplayerMode();
		} else {
			startSoloPlayerMode();
		}

	}

	private void startSoloPlayerMode() {
		try {
			ArrayList<Question> questions;

			questions = laBase.getQuestions(numberOfQuestion);

			laGame = new Game(0, "Solo", monPlayer.getMyId(), null, questions, numberOfQuestion, null);

			laGame = laBase.createSoloPlayerGame(laGame);

			currentQuestion = laGame.getGroupQuestions().get(monPlayer.getNbQuestion() - 1);

			laConsole.setPnlDisplayQuiz(new PnlDisplayQuiz(this, currentQuestion));
			laConsole.getContentPane().add(laConsole.getPnlDisplayQuiz());

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void startMultiplayerMode() {
		// Selectionne la question en cours
		currentQuestion = laGame.getGroupQuestions().get(monPlayer.getNbQuestion() - 1);
		// Changement de panel
		laConsole.setPnlDisplayQuiz(new PnlDisplayQuiz(this, currentQuestion));
		laConsole.getContentPane().add(laConsole.getPnlDisplayQuiz());

		laConsole.repaint();
		// laConsole.getPnlDisplayQuiz().setVisible(true);
	}

	private void startGame() {
		laConsole.getPnlWaitingRoom().setVisible(false);
		laConsole.getContentPane().remove(laConsole.getPnlWaitingRoom());
		laConsole.setPnlWaitingRoom(null);

		laConsole.setWaitingScreen(false);

		int nbQuestion = getLaGame().getNbQuestion();
		lancementQuiz(nbQuestion, true);
	}

	public void startGameFromServer() {
		leClient.launchGame(laGame.getIdGame());
	}

	public Boolean isCorrectThisAnswer(Question question, int idAnswer) {
		if (question.getAnswers().get(idAnswer).getIsCorrect()) {
			this.monPlayer.setMyScore(this.monPlayer.getMyScore() + 10);
			return true;
		} else {
			return false;
		}
	}

	public void questionTreatment(int bgSelected) {
		// Changement de panel
		laConsole.getPnlDisplayQuiz().setVisible(false);
		laConsole.getContentPane().remove(laConsole.getPnlDisplayQuiz());
		laConsole.setPnlDisplayQuiz(null);

		laConsole.setPnlListPlayerChange(2);

		laConsole.setPnlResultAnswer(new PnlResultAnswer(this));
		laConsole.getContentPane().add(laConsole.getPnlResultAnswer());

		if (isCorrectThisAnswer(currentQuestion, bgSelected)) {
			// Affiche que la réponse est correcte
			laConsole.getPnlResultAnswer().getLblAnswer().setForeground(new Color(0, 153, 51));
			laConsole.getPnlResultAnswer().getLblAnswer()
					.setText("<html><p text-align: center>BONNE RÉPONSE<br/>Vous gagnez 10 points</p>");
		} else {
			// Affiche que la réponse est fausse
			laConsole.getPnlResultAnswer().getLblAnswer().setForeground(new Color(204, 51, 0));
			laConsole.getPnlResultAnswer().getLblAnswer()
					.setText("<html><p text-align: center>MAUVAISE RÉPONSE<br/>Vous gagnez 0 point</p>");
		}

	}

	public void nextQuestion() {
		// Selectionne la question en cours
		currentQuestion = laGame.getGroupQuestions()
				.get(monPlayer.getNbQuestion() - 1);

		// Changement de panel
		laConsole.setPnlDisplayQuiz(new PnlDisplayQuiz(this, currentQuestion));
		laConsole.getContentPane().add(laConsole.getPnlDisplayQuiz());
	}

	public void NextPanel(Object object) {
		// Dans le cas où le JPanel est celui de connexion
		if (object instanceof PnlLogin) {
			laConsole.getPnlLogin().setVisible(false);
			laConsole.remove(laConsole.getPnlLogin());
			laConsole.setPnlLogin(null);

			laConsole.setPnlGameMode(new PnlGameMode(this));
			laConsole.getContentPane().add(laConsole.getPnlGameMode());
		}

		if (object instanceof PnlGameMode) {
			laConsole.getPnlGameMode().setVisible(false);
			laConsole.remove(laConsole.getPnlGameMode());
			laConsole.setPnlGameMode(null);

			if (isMulti()) {

				try {
					setLeClient(new ClientWebsocket(this));
					laConsole.setPnlMultiGameMode(new PnlMultiGameMode(this));
					laConsole.getContentPane().add(laConsole.getPnlMultiGameMode());
				} catch (IOException e) {
					// e.printStackTrace();
					laConsole.setPnlGameMode(new PnlGameMode(this));
					laConsole.getContentPane().add(laConsole.getPnlGameMode());
					
					JOptionPane.showMessageDialog(laConsole,
							"Un problème est survenue lors de la connexion au serveur.\r\nVeuillez réessayer.",
							"Erreur de connexion", JOptionPane.ERROR_MESSAGE);
				}

			} else {
				laConsole.setPnlSoloCreateGame(new PnlSoloCreateGame(this));
				laConsole.getContentPane().add(laConsole.getPnlSoloCreateGame());
			}

		}

		if (object instanceof PnlSoloCreateGame) {

			int nbQuestion = (int) laConsole.getPnlSoloCreateGame().getListeNbQuestion().getSelectedItem();

			laConsole.getPnlSoloCreateGame().setVisible(false);
			laConsole.remove(laConsole.getPnlSoloCreateGame());
			laConsole.setPnlSoloCreateGame(null);

			isPlaying = true;

			lancementQuiz(nbQuestion, false);
		}

		if (object instanceof PnlMultiCreateGame) {
			laConsole.getPnlMultiCreateGame().setVisible(false);
			laConsole.remove(laConsole.getPnlMultiCreateGame());
			laConsole.setPnlMultiCreateGame(null);

			laConsole.setPnlWaitingRoom(new PnlWaitingRoom(this));
			laConsole.getContentPane().add(laConsole.getPnlWaitingRoom());
			laConsole.getPnlWaitingRoom().setVisible(false);
			laConsole.getPnlWaitingRoom().setVisible(true);

			isPlaying = true;
			laConsole.setWaitingScreen(true);
		}

		if (object instanceof PnlMultiJoinGame) {
			laConsole.getPnlMultiJoinGame().setVisible(false);
			laConsole.remove(laConsole.getPnlMultiJoinGame());
			laConsole.setPnlMultiJoinGame(null);

			if (laConsole.isReloadJoinGame()) {

				LesGame lesParty = getLesGames();

				laConsole.setPnlMultiJoinGame(new PnlMultiJoinGame(this, lesParty));
				laConsole.getContentPane().add(laConsole.getPnlMultiJoinGame());
				laConsole.getPnlMultiJoinGame().setVisible(false);
				laConsole.getPnlMultiJoinGame().setVisible(true);

				laConsole.setReloadJoinGame(false);
			} else {
				laConsole.setPnlWaitingRoom(new PnlWaitingRoom(this));
				laConsole.getContentPane().add(laConsole.getPnlWaitingRoom());
				laConsole.getPnlWaitingRoom().setVisible(false);
				laConsole.getPnlWaitingRoom().setVisible(true);

				isPlaying = true;
				laConsole.setWaitingScreen(true);
			}
		}

		if (object instanceof PnlResultAnswer) {

			laConsole.getPnlResultAnswer().setVisible(false);
			laConsole.getContentPane().remove(laConsole.getPnlResultAnswer());
			laConsole.setPnlResultAnswer(null);

			laConsole.setPnlListPlayerChange(1);

			monPlayer.setNbQuestion(monPlayer.getNbQuestion() + 1);

			if (monPlayer.getNbQuestion() <= laGame.getNbQuestion()) {
				// Question suivante
				nextQuestion();
			} else {
				// Fin du Quiz
				laConsole.setPnlListPlayerChange(3);
				laConsole.setPnlEndQuiz(new PnlEndQuiz(this));
				laConsole.getContentPane().add(laConsole.getPnlEndQuiz());
			}

			if (isMulti()) {
				leClient.getClient().sendTCP(new Message(5, laGame.getIdGame(), monPlayer));
			}
		}

		if (object instanceof PnlMultiGameMode) {
			Boolean createGame = laConsole.getPnlMultiGameMode().isCreateGame();

			laConsole.getPnlMultiGameMode().setVisible(false);
			laConsole.getContentPane().remove(laConsole.getPnlMultiGameMode());
			laConsole.setPnlMultiGameMode(null);

			if (createGame) {
				laConsole.setPnlMultiCreateGame(new PnlMultiCreateGame(this));
				laConsole.getContentPane().add(laConsole.getPnlMultiCreateGame());

			} else {
				LesGame lesParty = getLesGames();

				laConsole.setPnlMultiJoinGame(new PnlMultiJoinGame(this, lesParty));
				laConsole.getContentPane().add(laConsole.getPnlMultiJoinGame());
				laConsole.getPnlMultiJoinGame().setVisible(false);
				laConsole.getPnlMultiJoinGame().setVisible(true);
			}
		}

		if (object instanceof PnlWaitingRoom) {
			laConsole.setWaitingScreen(false);
			startGameFromServer();

		}

		if (object instanceof PnlEndQuiz) {
			laConsole.getPnlEndQuiz().setVisible(false);
			laConsole.getContentPane().remove(laConsole.getPnlEndQuiz());
			laConsole.setPnlEndQuiz(null);			

			if (isMulti()) {
				setMulti(false);
				leClient.getClient().close();
			} else {
				getLaBase().finishedSoloPlayerGame(getLaGame());
			}			
			
			resetParam();

			laConsole.setPnlGameMode(new PnlGameMode(this));
			laConsole.getContentPane().add(laConsole.getPnlGameMode());
		}
	}

	public void PreviousPanel(Object object) {
		if (object instanceof PnlSoloCreateGame) {
			laConsole.getPnlSoloCreateGame().setVisible(false);
			laConsole.remove(laConsole.getPnlSoloCreateGame());
			laConsole.setPnlSoloCreateGame(null);

			laConsole.setPnlGameMode(new PnlGameMode(this));
			laConsole.getContentPane().add(laConsole.getPnlGameMode());
		}

		if (object instanceof PnlMultiGameMode) {
			laConsole.getPnlMultiGameMode().setVisible(false);
			laConsole.remove(laConsole.getPnlMultiGameMode());
			laConsole.setPnlMultiGameMode(null);

			setMulti(false);
			leClient.getClient().close();

			laConsole.setPnlGameMode(new PnlGameMode(this));
			laConsole.getContentPane().add(laConsole.getPnlGameMode());
		}

		if (object instanceof PnlMultiCreateGame) {
			laConsole.getPnlMultiCreateGame().setVisible(false);
			laConsole.remove(laConsole.getPnlMultiCreateGame());
			laConsole.setPnlMultiCreateGame(null);

			laConsole.setPnlMultiGameMode(new PnlMultiGameMode(this));
			laConsole.getContentPane().add(laConsole.getPnlMultiGameMode());
		}

		if (object instanceof PnlMultiJoinGame) {
			laConsole.setReloadJoinGame(false);
			laConsole.getPnlMultiJoinGame().setVisible(false);
			laConsole.remove(laConsole.getPnlMultiJoinGame());
			laConsole.setPnlMultiJoinGame(null);

			laConsole.setPnlMultiGameMode(new PnlMultiGameMode(this));
			laConsole.getContentPane().add(laConsole.getPnlMultiGameMode());
		}

	}
	
	public void resetParam() {
		isPlaying = false;
		setLaGame(null);
		getMonPlayer().setMyScore(0);
		getMonPlayer().setNbQuestion(1);
	}

	public boolean verification(String name, String password) {
		int idPlayer = laBase.verifLogin(name, password);
		if (idPlayer != 0) {
			this.monPlayer = new Player(idPlayer, name, 0, 1);
			return true;
		}
		return false;
	}

	public void initLesGames() {
		leClient.searchGame();
	}

	public Game getLaGame() {
		return laGame;
	}

	public void setLaGame(Game laGame) {
		this.laGame = laGame;
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

	public ClientWebsocket getLeClient() {
		return leClient;
	}

	public void setLeClient(ClientWebsocket leClient) {
		this.leClient = leClient;
	}

	public LesGame getLesGames() {
		return lesGames;
	}

	public void setLesGames(LesGame lesGames) {
		this.lesGames = lesGames;
	}

	public Jasypt getTheDecrypter() {
		return theDecrypter;
	}

	public void setTheDecrypter(Jasypt theDecrypter) {
		this.theDecrypter = theDecrypter;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}
	
	public boolean isMulti() {
		return multi;
	}

	public void setMulti(boolean multi) {
		this.multi = multi;
	}
	
	public boolean isCreateGameMulti() {
		return createGameMulti;
	}
	
	public void setCreateGameMulti(boolean createGameMulti) {
		this.createGameMulti = createGameMulti;
	}


}
