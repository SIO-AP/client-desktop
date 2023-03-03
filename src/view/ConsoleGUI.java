
package view;

import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.AbstractButton;
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
import control.PnlResultAnswer;
import control.PnlSoloCreateGame;
import controller.Controller;
import model.Answer;
import model.Group;
import model.Player;
import model.Question;
import model.QuizGame;
import javax.swing.JButton;

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
	private JPanel pnlMultiJoinGame;

	private String textNom;
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


		// pnlMultiJoinGame = new JPanel();
		// pnlMultiJoinGame.setVisible(false);
		// pnlMultiJoinGame.setBounds(10, 10, 678, 453);
		// pane.add(pnlMultiJoinGame);
		// pnlMultiJoinGame.setLayout(null);

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
			pnlMultiGameMode.setVisible(false);
			pane.remove(pnlMultiGameMode);
			pnlMultiGameMode = null;

			pnlMultiCreateGame = new PnlMultiCreateGame(monController);
			pane.add(pnlMultiCreateGame);
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

	private void startSoloMode(Player player) {
		try {
			ArrayList<Question> quizQuestions;
			quizQuestions = monController.getLaBase().getQuestions(numberOfQuestion);
			monController.setLaGame(new QuizGame(monController, monController.getMonPlayer(), quizQuestions));

		//	new ClientWebsocket(monController);
			currentQuestion = monController.getLaGame().getQuestions().get(numCurrentQuestion - 1);

			pnlDisplayQuiz = new PnlDisplayQuiz(monController, currentQuestion);
			pane.add(pnlDisplayQuiz);

			numCurrentQuestion++;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void startMultiplayerMode(Player player) {
		try {
			Group theGroup = monController.getLaBase().getGroup(player.getaGroupId()); // create getGroup method
			ArrayList<Question> quizQuestions = theGroup.getGroupQuestions();
			monController.setLaGame(new QuizGame(monController, player, quizQuestions));
			currentQuestion = monController.getLaGame().getQuestions().get(numCurrentQuestion - 1);
			pnlDisplayQuiz.getLblQuestion().setText(currentQuestion.getDescriptionQuestion());
			Enumeration<AbstractButton> allButtons = pnlDisplayQuiz.getBgAnswer().getElements();

			for (Answer answer : currentQuestion.getAnswers()) {
				allButtons.nextElement().setText(answer.getDescriptionAnswer());
			}

			pnlDisplayQuiz.getLblScore()
					.setText("Score : " + String.valueOf(monController.getLaGame().getMyPlayer().getMyScore()));
			pnlDisplayQuiz.getLblNumQuestion().setText(
					"Question " + String.valueOf(numCurrentQuestion) + " sur " + String.valueOf(numberOfQuestion));

			numCurrentQuestion++;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void lancementQuiz(int nbQuestion, Boolean multiplayer) {
		// Vérifie si le nom est entré

		// setVisible();
		numberOfQuestion = nbQuestion;
		numCurrentQuestion = 1;

		if (multiplayer) {
			Player thePlayer = new Player(monController, textNom, 0 /* get player's group id */);
			startMultiplayerMode(thePlayer);

		} else {
			Player thePlayer = new Player(monController, textNom, 0);
			startSoloMode(thePlayer);
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

			if (monController.getLaGame().isCorrectThisAnswer(currentQuestion, bgSelected)) {
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
		currentQuestion = monController.getLaGame().getQuestions().get(numCurrentQuestion - 1);

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