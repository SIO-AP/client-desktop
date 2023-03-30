
package view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;

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
import data.ClientWebsocket;
import model.Game;
import model.LesGame;
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
	private boolean createGameMulti;
	private boolean reloadJoinGame = false;
	private boolean waitingScreen = false;
	private boolean blPnlResultAnswer = false;
	

    private JLabel label;
    private JSpinner spinner;
    private SpinnerDateModel model;

	public ConsoleGUI(Controller unController) {
		// Appelle le constructeur de la classe mère
		super();

		monController = unController;

		// try {
		// UIManager.setLookAndFeel(new NimbusLookAndFeel());
		// } catch (UnsupportedLookAndFeelException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		setIconImage(
				Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("img/vinci_ico.jpg")));
		setTitle("Vinci Quiz");
		setSize(712, 510);
		setResizable(false);
		setFont(new Font("Consolas", Font.PLAIN, 12));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		BufferedImage myImage;
		try {
			myImage = ImageIO.read(getClass().getClassLoader().getResource("img/background.png"));
		//	myImage = ImageIO.read(getClass().getClassLoader().getResource("img/image.png"));
			setContentPane(new ImagePanel(myImage));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		// Pane pointe sur le container racine
		pane = getContentPane();
		
		// Fixe le Layout de la racine � Absolute
		pane.setLayout(null);
		
//		JButton btnNewButton = new JButton("New button");
//		btnNewButton.setBounds(120, 101, 203, 109);
//		getContentPane().add(btnNewButton);

		pnlLogin = new PnlLogin(monController);
		pane.add(pnlLogin);

//		 pnlMultiCreateGame = new PnlMultiCreateGame(unController);
//		 pane.add(pnlMultiCreateGame);
		
		
	//	HeureInput heureInput = new HeureInput();
	//	pane.add(heureInput);
		
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

				try {
					monController.setLeClient(new ClientWebsocket(monController));
					pnlMultiGameMode = new PnlMultiGameMode(monController);
					pane.add(pnlMultiGameMode);
				} catch (IOException e) {
					// e.printStackTrace();
					pnlGameMode = new PnlGameMode(monController);
					pane.add(pnlGameMode);
					JOptionPane.showMessageDialog(this,
							"Un problème est survenue lors de la connexion au serveur.\r\nVeuillez réessayer.",
							"Erreur de connexion", JOptionPane.ERROR_MESSAGE);
				}

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

			waitingScreen = true;
		}

		if (object instanceof PnlMultiJoinGame) {
			pnlMultiJoinGame.setVisible(false);
			this.remove(pnlMultiJoinGame);
			pnlMultiJoinGame = null;

			if (reloadJoinGame) {

				LesGame lesParty = monController.getLesGames();

				pnlMultiJoinGame = new PnlMultiJoinGame(monController, lesParty);
				pane.add(pnlMultiJoinGame);
				pnlMultiJoinGame.setVisible(false);
				pnlMultiJoinGame.setVisible(true);

				reloadJoinGame = false;
			} else {
				pnlWaitingRoom = new PnlWaitingRoom(monController);
				pane.add(pnlWaitingRoom);
				pnlWaitingRoom.setVisible(false);
				pnlWaitingRoom.setVisible(true);

				waitingScreen = true;
			}
		}

		if (object instanceof PnlResultAnswer) {
			pnlResultAnswer.setVisible(false);
			pane.remove(pnlResultAnswer);
			pnlResultAnswer = null;

			blPnlResultAnswer = false;

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
				LesGame lesParty = monController.getLesGames();

				pnlMultiJoinGame = new PnlMultiJoinGame(monController, lesParty);
				pane.add(pnlMultiJoinGame);
				pnlMultiJoinGame.setVisible(false);
				pnlMultiJoinGame.setVisible(true);
			}
		}

		if (object instanceof PnlWaitingRoom) {
			waitingScreen = false;
			monController.startGameFromServer();

		}
		
		if (object instanceof PnlEndQuiz) {
			pnlEndQuiz.setVisible(false);
			pane.remove(pnlEndQuiz);
			pnlEndQuiz = null;
			
			monController.setLaGame(null);
			monController.getMonPlayer().setMyScore(0);
			
			pnlGameMode = new PnlGameMode(monController);
			pane.add(pnlGameMode);
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
			
			monController.getLeClient().getClient().close();

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

		if (object instanceof PnlMultiJoinGame) {
			reloadJoinGame = false;
			pnlMultiJoinGame.setVisible(false);
			this.remove(pnlMultiJoinGame);
			pnlMultiJoinGame = null;

			pnlMultiGameMode = new PnlMultiGameMode(monController);
			pane.add(pnlMultiGameMode);
		}

	}

	private void startSoloMode() {
		try {
			ArrayList<Question> quizQuestions;
			ArrayList<Integer> listeIdQuestion = monController.listeIdQuestion(numberOfQuestion);
			quizQuestions = monController.getLaBase().getQuestions(listeIdQuestion);
			monController.setLaGame(
					new Game(0, "solo", monController.getMonPlayer().getMyId(), null, quizQuestions, numberOfQuestion));

			currentQuestion = monController.getLaGame().getGroupQuestions().get(numCurrentQuestion - 1);

			pnlDisplayQuiz = new PnlDisplayQuiz(monController, currentQuestion);
			pane.add(pnlDisplayQuiz);

			numCurrentQuestion++;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void startMultiplayerMode() {
		// Selectionne la question en cours
		currentQuestion = monController.getLaGame().getGroupQuestions().get(numCurrentQuestion - 1);
		// Changement de panel
		pnlDisplayQuiz = new PnlDisplayQuiz(monController, currentQuestion);
		pane.add(pnlDisplayQuiz);

		pnlDisplayQuiz.setVisible(false);
		pnlDisplayQuiz.setVisible(true);

		numCurrentQuestion++;
	}

	public void lancementQuiz(int nbQuestion, Boolean multiplayer) {
		numberOfQuestion = nbQuestion;
		numCurrentQuestion = 1;

		if (multiplayer) {
			startMultiplayerMode();
		} else {
			startSoloMode();
		}

	}

	public void questionTreatment(int bgSelected) {
		// Changement de panel
		pnlDisplayQuiz.setVisible(false);
		pane.remove(pnlDisplayQuiz);
		pnlDisplayQuiz = null;

		blPnlResultAnswer = true;

		pnlResultAnswer = new PnlResultAnswer(monController);
		pane.add(pnlResultAnswer);

		if (monController.isCorrectThisAnswer(currentQuestion, bgSelected)) {
			// Affiche que la réponse est correcte
			pnlResultAnswer.getLblAnswer().setText("Bonne réponse Vous avez gagné 10 points");
		} else {
			// Affiche que la réponse est fausse
			pnlResultAnswer.getLblAnswer().setText("Mauvaise réponse");
		}

	}

	private void nextQuestion() {
		// Selectionne la question en cours
		currentQuestion = monController.getLaGame().getGroupQuestions().get(numCurrentQuestion - 1);

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

	public Container getPane() {
		return pane;
	}

	public void setPane(Container pane) {
		this.pane = pane;
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

	public boolean isBlPnlResultAnswer() {
		return blPnlResultAnswer;
	}

	public void setBlPnlResultAnswer(boolean blPnlResultAnswer) {
		this.blPnlResultAnswer = blPnlResultAnswer;
	}

	public Question getCurrentQuestion() {
		return currentQuestion;
	}

	public void setCurrentQuestion(Question currentQuestion) {
		this.currentQuestion = currentQuestion;
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

class HeureInput extends JPanel {
    private JLabel label;
    private JSpinner spinner;
    private SpinnerDateModel model;

    public HeureInput() {
        label = new JLabel("Heure : ");
        model = new SpinnerDateModel();
        spinner = new JSpinner(model);

        // Configuration de la zone de saisie
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "HH:mm:ss");
        editor.getTextField().setEditable(false); // permettre la saisie manuelle
        spinner.setEditor(editor);
        spinner.setPreferredSize(new Dimension(150, 48));
        Font font = spinner.getFont();
        spinner.setFont(new Font(font.getName(), font.getStyle(), 20)); // taille de police de 20 points

        // Centrage de l'heure dans le spinner
        JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor) spinner.getEditor();
        spinnerEditor.getTextField().setHorizontalAlignment(SwingConstants.CENTER);

        
        // Ajout des éléments à la fenêtre
        setLayout(new FlowLayout());
        add(label);
        add(spinner);
        setBounds(0, 0, 400, 400);
    }

    // Méthode pour récupérer l'heure saisie
    public String getHeure() {
        return model.getValue().toString();
    }
}