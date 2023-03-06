
package view;

import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

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
import controller.Controller;
import model.Party;
import model.Question;

public class ConsoleGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Controller monController;

	private Container pane;

	private PnlLogin pnlLogin;
	private PnlGameMode pnlGameMode;
	private PnlSoloCreateGame pnlSoloCreateGame;
	private PnlMultiGameMode pnlMultiGameMode;
	private PnlDisplayQuiz pnlDisplayQuiz;
	private PnlResultAnswer pnlResultAnswer;
	private PnlEndQuiz pnlEndQuiz;
	private PnlMultiCreateGame pnlMultiCreateGame;
	private PnlWaitingRoom pnlWaitingRoom;
	private PnlMultiJoinGame pnlMultiJoinGame;

	private Question currentQuestion;
	private int numberOfQuestion;
	private int numCurrentQuestion;
	private boolean multi;

	public ConsoleGUI(Controller unController) throws ParseException, UnsupportedLookAndFeelException,
			FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		// Appelle le constructeur de la classe mère
		super();

		monController = unController;

		UIManager.setLookAndFeel(new NimbusLookAndFeel());

		setIconImage(
				Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("img/vinci_ico.jpg")));
		setTitle("Vinci Quiz");
		setSize(712, 510);
		setResizable(false);
		setFont(new Font("Consolas", Font.PLAIN, 12));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Pane pointe sur le container racine
		pane = getContentPane();
		// Fixe le Layout de la racine � Absolute
		pane.setLayout(null);

		pnlLogin = new PnlLogin(monController);
		pane.add(pnlLogin);

	}

	public void NextPanel(Object object) {
		if (object instanceof PnlLogin) {
			pnlLogin.setVisible(false);
			this.remove(pnlLogin);
			pnlLogin = null;

			pnlGameMode = new PnlGameMode(monController);
			pane.add(pnlGameMode);
		}

		if (object instanceof PnlGameMode) {
			pnlGameMode.setVisible(false);
			this.remove(pnlGameMode);
			pnlGameMode = null;

			if (multi) {
				pnlMultiGameMode = new PnlMultiGameMode(monController);
				pane.add(pnlMultiGameMode);
			} else {
				pnlSoloCreateGame = new PnlSoloCreateGame(monController);
				pane.add(pnlSoloCreateGame);
			}

		}

		if (object instanceof PnlSoloCreateGame) {

			int nbQuestion = (int) pnlSoloCreateGame.getListeNbQuestion().getSelectedItem();

			pnlSoloCreateGame.setVisible(false);
			this.remove(pnlSoloCreateGame);
			pnlSoloCreateGame = null;

			lancementQuiz(nbQuestion, false);
		}

		if (object instanceof PnlMultiCreateGame) {
			pnlMultiCreateGame.setVisible(false);
			this.remove(pnlMultiCreateGame);
			pnlMultiCreateGame = null;

			pnlWaitingRoom = new PnlWaitingRoom(monController);
			pane.add(pnlWaitingRoom);
			pnlWaitingRoom.setVisible(false);
			pnlWaitingRoom.setVisible(true);

			// lancementQuiz(nbQuestion, true);
		}

		if (object instanceof PnlResultAnswer) {
			pnlResultAnswer.setVisible(false);
			pane.remove(pnlResultAnswer);
			pnlResultAnswer = null;

			if (numCurrentQuestion <= numberOfQuestion) {
				// Question suivante
				nextQuestion();
			} else {
				// Fin du Quiz
				pnlEndQuiz = new PnlEndQuiz(monController);
				pane.add(pnlEndQuiz);
			}
		}

		if (object instanceof PnlMultiGameMode) {
			Boolean createGame = pnlMultiGameMode.getCreateGame();

			pnlMultiGameMode.setVisible(false);
			pane.remove(pnlMultiGameMode);
			pnlMultiGameMode = null;

			if (createGame) {
				pnlMultiCreateGame = new PnlMultiCreateGame(monController);
				pane.add(pnlMultiCreateGame);
				
			} else {
				
				String data[][] = { { "Partie 1", "5", "12:00:00", "1" }, { "Partie 2", "10", "12:00:00", "2" },
						{ "Partie 3", "15", "12:00:00", "3" }, { "Partie 4", "20", "12:00:00", "4" } };

				pnlMultiJoinGame = new PnlMultiJoinGame(monController, data);
				pane.add(pnlMultiJoinGame);
			}

		}

		if (object instanceof PnlWaitingRoom) {
			pnlWaitingRoom.setVisible(false);
			pane.remove(pnlWaitingRoom);
			pnlWaitingRoom = null;

			int nbQuestion = monController.getLaParty().getNbQuestion();
			lancementQuiz(nbQuestion, true);
		}
	}

	public void PreviousPanel(Object object) {
		if (object instanceof PnlSoloCreateGame) {
			pnlSoloCreateGame.setVisible(false);
			this.remove(pnlSoloCreateGame);
			pnlSoloCreateGame = null;

			pnlGameMode = new PnlGameMode(monController);
			pane.add(pnlGameMode);
		}

		if (object instanceof PnlMultiGameMode) {
			pnlMultiGameMode.setVisible(false);
			this.remove(pnlMultiGameMode);
			pnlMultiGameMode = null;

			pnlGameMode = new PnlGameMode(monController);
			pane.add(pnlGameMode);
		}

		if (object instanceof PnlMultiCreateGame) {
			pnlMultiCreateGame.setVisible(false);
			this.remove(pnlMultiCreateGame);
			pnlMultiCreateGame = null;

			pnlMultiGameMode = new PnlMultiGameMode(monController);
			pane.add(pnlMultiGameMode);
		}

	}

	private void startSoloMode() {
		try {
			ArrayList<Question> quizQuestions;
			quizQuestions = monController.getLaBase().getQuestions(numberOfQuestion);
			monController.setLaParty(new Party(0, "solo", monController.getMonPlayer().getMyId(), null, quizQuestions,
					numberOfQuestion));
			// monController, monController.getMonPlayer(), quizQuestions));

			currentQuestion = monController.getLaParty().getGroupQuestions().get(numCurrentQuestion - 1);

			pnlDisplayQuiz = new PnlDisplayQuiz(monController, currentQuestion);
			pane.add(pnlDisplayQuiz);

			numCurrentQuestion++;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void startMultiplayerMode() {
		// Selectionne la question en cours
		currentQuestion = monController.getLaParty().getGroupQuestions().get(numCurrentQuestion - 1);
		// Changement de panel
		pnlDisplayQuiz = new PnlDisplayQuiz(monController, currentQuestion);
		pane.add(pnlDisplayQuiz);

		numCurrentQuestion++;
	}

	private void lancementQuiz(int nbQuestion, Boolean multiplayer) {
		// Vérifie si le nom est entré

		// setVisible();
		numberOfQuestion = nbQuestion;
		numCurrentQuestion = 1;

		if (multiplayer) {
			startMultiplayerMode();

		} else {
			startSoloMode();
		}

	}

	private Boolean isValidAnswer(int bgSelected) {
		if (bgSelected == 4) {
			return false;
		} else {
			return true;
		}
	}

	public void questionTreatment() {
		// Num du bouton radio sélectionné
		int bgSelected = Integer.parseInt(pnlDisplayQuiz.getBgAnswer().getSelection().getActionCommand());

		if (this.isValidAnswer(bgSelected)) {
			// Changement de panel
			pnlDisplayQuiz.setVisible(false);
			pane.remove(pnlDisplayQuiz);
			pnlDisplayQuiz = null;

			pnlResultAnswer = new PnlResultAnswer(monController);
			pane.add(pnlResultAnswer);

			if (monController.isCorrectThisAnswer(currentQuestion, bgSelected)) {
				// Affiche que la réponse est correcte
				pnlResultAnswer.getLblAnswer().setText("Bonne réponse Vous avez gagné 10 points");
			} else {
				// Affiche que la réponse est fausse
				pnlResultAnswer.getLblAnswer().setText("Mauvaise réponse");
			}
		} else {
			pnlDisplayQuiz.getLblErrorDisplayQuiz().setText("Veuillez séléctionner une réponse");
		}
	}

	private void nextQuestion() {
		// Selectionne la question en cours
		currentQuestion = monController.getLaParty().getGroupQuestions().get(numCurrentQuestion - 1);

		// Changement de panel
		pnlDisplayQuiz = new PnlDisplayQuiz(monController, currentQuestion);
		pane.add(pnlDisplayQuiz);

		numCurrentQuestion++;
	}

	private String resultMessageScore(int score, int numberOfQuestion) {
		String resultMessageScore = "";

		int scoreMoyenne = score / numberOfQuestion;

		if (scoreMoyenne < 2.5) {
			resultMessageScore = "";
		} else if (scoreMoyenne < 5) {
			resultMessageScore = "";
		} else if (scoreMoyenne < 7.5) {
			resultMessageScore = "";
		} else {
			resultMessageScore = "";
		}

		return resultMessageScore;
	}

	public PnlWaitingRoom getPnlWaitingRoom() {
		return pnlWaitingRoom;
	}

	public void setPnlWaitingRoom(PnlWaitingRoom pnlWaitingRoom) {
		this.pnlWaitingRoom = pnlWaitingRoom;
	}

	public PnlSoloCreateGame getPnlSoloCreateGame() {
		return pnlSoloCreateGame;
	}

	public void setPnlSoloCreateGame(PnlSoloCreateGame pnlSoloCreateGame) {
		this.pnlSoloCreateGame = pnlSoloCreateGame;
	}

	public boolean isMulti() {
		return multi;
	}

	public void setMulti(boolean multi) {
		this.multi = multi;
	}

	public int getNumberOfQuestion() {
		return numberOfQuestion;
	}

	public void setNumberOfQuestion(int numberOfQuestion) {
		this.numberOfQuestion = numberOfQuestion;
	}

	public int getNumCurrentQuestion() {
		return numCurrentQuestion;
	}

	public void setNumCurrentQuestion(int numCurrentQuestion) {
		this.numCurrentQuestion = numCurrentQuestion;
	}

	public PnlGameMode getPnlGameMode() {
		return pnlGameMode;
	}

	public void setPnlGameMode(PnlGameMode pnlGameMode) {
		this.pnlGameMode = pnlGameMode;
	}

	public JPanel getPnlMultiGameMode() {
		return pnlMultiGameMode;
	}

	public void setPnlMultiGameMode(PnlMultiGameMode pnlMultiGameMode) {
		this.pnlMultiGameMode = pnlMultiGameMode;
	}

	public PnlDisplayQuiz getPnlDisplayQuiz() {
		return pnlDisplayQuiz;
	}

	public void setPnlDisplayQuiz(PnlDisplayQuiz pnlDisplayQuiz) {
		this.pnlDisplayQuiz = pnlDisplayQuiz;
	}

	public PnlResultAnswer getPnlResultAnswer() {
		return pnlResultAnswer;
	}

	public void setPnlResultAnswer(PnlResultAnswer pnlResultAnswer) {
		this.pnlResultAnswer = pnlResultAnswer;
	}

	public PnlEndQuiz getPnlEndQuiz() {
		return pnlEndQuiz;
	}

	public void setPnlEndQuiz(PnlEndQuiz pnlEndQuiz) {
		this.pnlEndQuiz = pnlEndQuiz;
	}

	public PnlMultiCreateGame getPnlMultiCreateGame() {
		return pnlMultiCreateGame;
	}

	public void setPnlMultiCreateGame(PnlMultiCreateGame pnlMultiCreateGame) {
		this.pnlMultiCreateGame = pnlMultiCreateGame;
	}

	public PnlLogin getPnlLogin() {
		return pnlLogin;
	}

	public void setPnlLogin(PnlLogin pnlLogin) {
		this.pnlLogin = pnlLogin;
	}
}