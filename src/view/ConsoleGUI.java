
package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
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
import controller.Controller;
import enpoints.Message;
import model.Game;
import model.Question;

public class ConsoleGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Controller monController;

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
	// private int numCurrentQuestion;
	private boolean multi;
	private boolean createGameMulti;
	private boolean reloadJoinGame = false;
	private boolean waitingScreen = false;
	private int pnlListPlayerChange = 1; // 1 : pnlDislpayQuiz / 2 : pnlResultAnswer / 3 : pnlEndQuiz

	public static int width = 1080;
	public static int height = 620;
	public static Rectangle rectangle = new Rectangle(0, 0, ConsoleGUI.width, ConsoleGUI.height);

	public ConsoleGUI(Controller unController) {
		// Appelle le constructeur de la classe mère
		super();

		monController = unController;

		setIconImage(
				Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("img/vinci_ico.jpg")));
		setTitle("The Legend of Vinci Quiz");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(width, height);

		setBackground("img/PnlLogin/back.png");

		getContentPane().setLayout(null);

		pnlLogin = new PnlLogin(monController);
		getContentPane().add(pnlLogin);

		// Ajouter un WindowListener pour écouter l'événement windowClosing
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				int option = JOptionPane.showOptionDialog(monController.getLaConsole(),
						"Voulez-vous vraiment fermer l'application ?", "Confirmation", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new Object[] { "Oui", "Non" }, "Non");

				if (option == JOptionPane.YES_OPTION) {
					if (monController.isPlaying()) { // En partie
						if (isMulti()) { // En partie multijoueur
							System.out.println("en game multi");
							monController.getLeClient().getClient().sendTCP(new Message(6,
									monController.getLaGame().getIdGame(), monController.getMonPlayer()));

						} else { // En partie solo
							System.out.println("en game solo");
							monController.getLaBase().finishedSoloPlayerGame(monController.getLaGame());

						}
					} else { // Pas en partie
						System.out.println("pas en game");
					}

					// Fermer la fenêtre
					monController.getLaConsole().dispose();
				}
			}
		});

	}

	private void startSoloPlayerMode() {
		try {
			ArrayList<Question> questions;

			questions = monController.getLaBase().getQuestions(numberOfQuestion);

			monController.setLaGame(new Game(0, "Solo", monController.getMonPlayer().getMyId(), null, questions,
					numberOfQuestion, null));

			monController.setLaGame(monController.getLaBase().createSoloPlayerGame(monController.getLaGame()));

			currentQuestion = monController.getLaGame().getGroupQuestions()
					.get(monController.getMonPlayer().getNbQuestion() - 1);

			pnlDisplayQuiz = new PnlDisplayQuiz(monController, currentQuestion);
			getContentPane().add(pnlDisplayQuiz);

			// monController.getMonPlayer().setNbQuestion(monController.getMonPlayer().getNbQuestion()
			// + 1);
			// numCurrentQuestion++;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void startMultiplayerMode() {
		// Selectionne la question en cours
		currentQuestion = monController.getLaGame().getGroupQuestions()
				.get(monController.getMonPlayer().getNbQuestion() - 1);
		// Changement de panel
		pnlDisplayQuiz = new PnlDisplayQuiz(monController, currentQuestion);
		getContentPane().add(pnlDisplayQuiz);

		pnlDisplayQuiz.setVisible(false);
		pnlDisplayQuiz.setVisible(true);

		// monController.getMonPlayer().setNbQuestion(monController.getMonPlayer().getNbQuestion()
		// + 1);
		// numCurrentQuestion++;
	}

	public void lancementQuiz(int nbQuestion, Boolean multiplayer) {
		numberOfQuestion = nbQuestion;
		// monController.getMonPlayer().setNbQuestion(1);
		// numCurrentQuestion = 1;

		if (multiplayer) {
			startMultiplayerMode();
		} else {
			startSoloPlayerMode();
		}

	}

	public void questionTreatment(int bgSelected) {
		// Changement de panel
		pnlDisplayQuiz.setVisible(false);
		getContentPane().remove(pnlDisplayQuiz);
		pnlDisplayQuiz = null;

		pnlListPlayerChange = 2;

		pnlResultAnswer = new PnlResultAnswer(monController);
		getContentPane().add(pnlResultAnswer);

		if (monController.isCorrectThisAnswer(currentQuestion, bgSelected)) {
			// Affiche que la réponse est correcte
			pnlResultAnswer.getLblAnswer().setText("Bonne réponse Vous avez gagné 10 points");
		} else {
			// Affiche que la réponse est fausse
			pnlResultAnswer.getLblAnswer().setText("Mauvaise réponse");
		}

	}

	public void nextQuestion() {
		// Selectionne la question en cours
		currentQuestion = monController.getLaGame().getGroupQuestions()
				.get(monController.getMonPlayer().getNbQuestion() - 1);

		// Changement de panel
		pnlDisplayQuiz = new PnlDisplayQuiz(monController, currentQuestion);
		getContentPane().add(pnlDisplayQuiz);
		// monController.getMonPlayer().setNbQuestion(monController.getMonPlayer().getNbQuestion()
		// + 1);
		// numCurrentQuestion++;
	}

	public void setBackground(String path) {
		try {
			BufferedImage myImage = ImageIO.read(getClass().getClassLoader().getResource(path));
			Image resizedImage = myImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			ImagePanel imagePanel = new ImagePanel(resizedImage);
			imagePanel.setPreferredSize(new Dimension(width, height)); // définir la taille préférée de l'image
			setContentPane(imagePanel);
			pack(); // ajuster la taille de la frame en fonction de la taille préférée de l'image
		} catch (IOException e) {
			e.printStackTrace();
		}
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

//	public int getNumberOfQuestion() {
//		return numberOfQuestion;
//	}
//
//	public void setNumberOfQuestion(int numberOfQuestion) {
//		this.numberOfQuestion = numberOfQuestion;
//	}

//	public int getNumCurrentQuestion() {
//		return numCurrentQuestion;
//	}
//
//	public void setNumCurrentQuestion(int numCurrentQuestion) {
//		this.numCurrentQuestion = numCurrentQuestion;
//	}

	public PnlGameMode getPnlGameMode() {
		return pnlGameMode;
	}

	public void setPnlGameMode(PnlGameMode pnlGameMode) {
		this.pnlGameMode = pnlGameMode;
	}

	public PnlMultiGameMode getPnlMultiGameMode() {
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

	public PnlMultiJoinGame getPnlMultiJoinGame() {
		return pnlMultiJoinGame;
	}

	public void setPnlMultiJoinGame(PnlMultiJoinGame pnlMultiJoinGame) {
		this.pnlMultiJoinGame = pnlMultiJoinGame;
	}

	public boolean isCreateGameMulti() {
		return createGameMulti;
	}

	public void setCreateGameMulti(boolean createGameMulti) {
		this.createGameMulti = createGameMulti;
	}

	public boolean isReloadJoinGame() {
		return reloadJoinGame;
	}

	public void setReloadJoinGame(boolean reloadJoinGame) {
		this.reloadJoinGame = reloadJoinGame;
	}

	public boolean isWaitingScreen() {
		return waitingScreen;
	}

	public void setWaitingScreen(boolean waitingScreen) {
		this.waitingScreen = waitingScreen;
	}

	public Question getCurrentQuestion() {
		return currentQuestion;
	}

	public void setCurrentQuestion(Question currentQuestion) {
		this.currentQuestion = currentQuestion;
	}

	public int getPnlListPlayerChange() {
		return pnlListPlayerChange;
	}

	public void setPnlListPlayerChange(int pnlListPlayerChange) {
		this.pnlListPlayerChange = pnlListPlayerChange;
	}
}

class ImagePanel extends JComponent {
	private Image image;

	public ImagePanel(Image image) {
		this.image = image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}
}