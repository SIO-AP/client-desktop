
package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import controller.Controller;
import model.Answer;
import model.Party;
import model.Player;
import model.Question;
import model.QuizGame;
import websocket.ClientWebsocket;

public class ConsoleGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Controller monController;

	private JPanel pnlGameMode;
	private JPanel pnlSoloCreateGame;
	private JPanel pnlMultiGame;
	private JPanel pnlDisplayQuiz;
	private JPanel pnlResultAnswer;
	private JPanel pnlEndQuiz;
	private JPanel pnlMultiCreateGame;
	private JPanel pnlMultiJoinGame;

	private JLabel lblAnswer;
	private JLabel lblScore;
	private JTextField textNom;
	private JTextField textGroup;
	private JLabel lblQuestion;
	private JLabel lblFinalScore;
	private ButtonGroup bgAnswer = new ButtonGroup();
	private int numberOfQuestion;
	private int numCurrentQuestion;
	private Question currentQuestion;
	private JLabel lblErrorDisplayQuiz;
	private JLabel lblErrorStartQuiz;
	private JComboBox<Object> listeNbQuestionSolo;
	private JComboBox<Object> listeNbQuestionMulti;
	private JLabel lblNumQuestion;

	public ConsoleGUI(Controller unController) throws ParseException, UnsupportedLookAndFeelException,
			FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		// Appelle le constructeur de la classe m�re
		super();

		this.monController = unController;

		UIManager.setLookAndFeel(new NimbusLookAndFeel());

		setIconImage(
				Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("img/vinci_ico.jpg")));
		setTitle("Vinci Quiz");
		setSize(712, 510);
		setResizable(false);
		setFont(new Font("Consolas", Font.PLAIN, 12));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Pane pointe sur le container racine
		Container pane = getContentPane();
		// Fixe le Layout de la racine � Absolute
		getContentPane().setLayout(null);

		pnlMultiCreateGame = new JPanel();
		pnlMultiCreateGame.setVisible(false);

		pnlMultiGame = new JPanel();
		pnlMultiGame.setVisible(false);
		pnlMultiGame.setBounds(10, 10, 678, 453);
		getContentPane().add(pnlMultiGame);
		pnlMultiGame.setLayout(null);

		JButton btnCreateGameMulti = new JButton("Créer un quiz");
		btnCreateGameMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnlMultiGame.setVisible(false);
				pnlMultiCreateGame.setVisible(true);
			}
		});
		btnCreateGameMulti.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCreateGameMulti.setBounds(108, 222, 154, 44);
		pnlMultiGame.add(btnCreateGameMulti);

		JButton btnJoinGameMulti = new JButton("Rejoindre un quiz");
		btnJoinGameMulti.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnJoinGameMulti.setBounds(368, 222, 172, 44);
		pnlMultiGame.add(btnJoinGameMulti);

		JButton btnMultiReturn = new JButton("Annuler");
		btnMultiReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnlMultiGame.setVisible(false);
				pnlGameMode.setVisible(true);
			}
		});
		btnMultiReturn.setBounds(267, 376, 103, 35);
		pnlMultiGame.add(btnMultiReturn);
		pnlMultiCreateGame.setBounds(10, 10, 678, 453);
		getContentPane().add(pnlMultiCreateGame);
		pnlMultiCreateGame.setLayout(null);

		textGroup = new JTextField();
		textGroup.setBounds(204, 157, 287, 48);
		textGroup.setColumns(10);
		pnlMultiCreateGame.add(textGroup);

		JLabel lblGroup = new JLabel("Nom du groupe :");
		lblGroup.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGroup.setBounds(41, 154, 153, 48);
		pnlMultiCreateGame.add(lblGroup);

		JLabel lblNbQuestionMulti = new JLabel("Nombre de questions :");
		lblNbQuestionMulti.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNbQuestionMulti.setBounds(40, 273, 190, 32);
		pnlMultiCreateGame.add(lblNbQuestionMulti);

		JButton btnLancementQuizMulti = new JButton("Commencer");
		btnLancementQuizMulti.setBounds(501, 214, 170, 48);
		pnlMultiCreateGame.add(btnLancementQuizMulti);
		btnLancementQuizMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (verificationChampGroup()) {
					lancementQuiz(e, true); // get if its multiplayer
				}
			}
		});

		// Création de la liste déroulante pour le nombre de question
		listeNbQuestionMulti = createJComboBoxMulti();
		listeNbQuestionMulti.setFont(new Font("Tahoma", Font.PLAIN, 15));
		listeNbQuestionMulti.setBounds(204, 265, 153, 48);
		pnlMultiCreateGame.add(listeNbQuestionMulti);

		// D�finit le JPanel de selection du mode de jeu
		pnlGameMode = new JPanel();
		pnlGameMode.setBounds(10, 10, 678, 453);
		pnlGameMode.setLayout(null);
		pane.add(pnlGameMode);

		textNom = new JTextField();
		textNom.setBounds(204, 215, 287, 48);
		pnlGameMode.add(textNom);
		textNom.setColumns(10);

		JLabel lblNom = new JLabel("Nom d'utilisateur :");
		lblNom.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNom.setBounds(40, 212, 153, 48);
		pnlGameMode.add(lblNom);

		JButton btnSoloPlayer = new JButton("Solo");
		btnSoloPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (verificationChampNom()) {
					pnlGameMode.setVisible(false);
					pnlSoloCreateGame.setVisible(true);
				}
			}
		});
		btnSoloPlayer.setBounds(164, 326, 138, 34);
		pnlGameMode.add(btnSoloPlayer);

		JButton btnMultiPlayer = new JButton("Multijoueurs");
		btnMultiPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (verificationChampNom()) {
					pnlGameMode.setVisible(false);
					pnlMultiGame.setVisible(true);
				}
			}
		});
		btnMultiPlayer.setBounds(393, 326, 138, 34);
		pnlGameMode.add(btnMultiPlayer);

		JLabel lblTitreGameMode = new JLabel("Bienvenue dans le Quiz Game Vinci");
		lblTitreGameMode.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitreGameMode.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitreGameMode.setBounds(10, 68, 658, 34);
		pnlGameMode.add(lblTitreGameMode);

		// D�finit le JPanel du start solo
		pnlSoloCreateGame = new JPanel();
		pnlSoloCreateGame.setVisible(false);
		pnlSoloCreateGame.setBounds(10, 10, 678, 453);
		pnlSoloCreateGame.setLayout(null);
		pane.add(pnlSoloCreateGame);

		JLabel lblTitre = new JLabel("Quiz Game");
		lblTitre.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitre.setBounds(278, 84, 200, 65);
		pnlSoloCreateGame.add(lblTitre);

		JButton btnLancementQuizSolo = new JButton("Commencer");
		btnLancementQuizSolo.setBounds(501, 214, 170, 48);
		pnlSoloCreateGame.add(btnLancementQuizSolo);
		btnLancementQuizSolo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lancementQuiz(e, false); // get if its multiplayer
			}
		});

		JButton btnSoloReturn = new JButton("Annuler");
		btnSoloReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnlSoloCreateGame.setVisible(false);
				pnlGameMode.setVisible(true);
			}
		});
		btnSoloReturn.setBounds(267, 376, 103, 35);
		pnlSoloCreateGame.add(btnSoloReturn);

		lblErrorStartQuiz = new JLabel("");
		lblErrorStartQuiz.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorStartQuiz.setBounds(0, 394, 678, 13);
		pnlGameMode.add(lblErrorStartQuiz);
		lblErrorStartQuiz.setForeground(Color.RED);

		JLabel txtpnWelcomeToQuizzyquiz = new JLabel();
		txtpnWelcomeToQuizzyquiz.setText(
				"Welcome to the VinciQuiz,\r\nEvery right answer will give you 10 points, and you will have 5 questions..\r\nIn order to win you'll need 30 points. Good Luck ! ^^");
		txtpnWelcomeToQuizzyquiz.setBounds(132, 309, 416, 75);
		pnlSoloCreateGame.add(txtpnWelcomeToQuizzyquiz);

		JLabel lblNbQuestionSolo = new JLabel("Nombre de questions :");
		lblNbQuestionSolo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNbQuestionSolo.setBounds(40, 273, 190, 32);
		pnlSoloCreateGame.add(lblNbQuestionSolo);

		// Création de la liste déroulante pour le nombre de question
		listeNbQuestionSolo = createJComboBoxSolo();
		listeNbQuestionSolo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		listeNbQuestionSolo.setBounds(204, 265, 153, 48);
		pnlSoloCreateGame.add(listeNbQuestionSolo);

		pnlResultAnswer = new JPanel();
		pnlResultAnswer.setBounds(10, 10, 678, 453);
		pnlResultAnswer.setLayout(null);
		pnlResultAnswer.setVisible(false);

		// D�finit le JPanel de l'affichage des questions
		pnlDisplayQuiz = new JPanel();
		pnlDisplayQuiz.setBounds(10, 10, 678, 453);
		pnlDisplayQuiz.setLayout(null);
		pnlDisplayQuiz.setVisible(false);

		pnlEndQuiz = new JPanel();
		pnlEndQuiz.setBounds(10, 10, 678, 453);
		pnlEndQuiz.setLayout(null);
		pnlEndQuiz.setVisible(false);
		getContentPane().add(pnlEndQuiz);

		JLabel lblEndQuiz = new JLabel("Fin du Quiz");
		lblEndQuiz.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEndQuiz.setHorizontalAlignment(SwingConstants.CENTER);
		lblEndQuiz.setBounds(10, 59, 658, 26);
		pnlEndQuiz.add(lblEndQuiz);

		lblFinalScore = new JLabel("");
		lblFinalScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblFinalScore.setBounds(10, 163, 658, 32);
		pnlEndQuiz.add(lblFinalScore);
		pane.add(pnlDisplayQuiz);
		pnlDisplayQuiz.setLayout(null);

		JPanel pnlInformationDisplayQuiz = new JPanel();
		pnlInformationDisplayQuiz.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlInformationDisplayQuiz.setBounds(37, 350, 178, 57);
		pnlDisplayQuiz.add(pnlInformationDisplayQuiz);
		pnlInformationDisplayQuiz.setLayout(null);

		lblQuestion = new JLabel("");
		lblQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuestion.setBounds(10, 71, 661, 13);
		pnlDisplayQuiz.add(lblQuestion);

		lblScore = new JLabel("Score");
		lblScore.setBounds(10, 10, 66, 13);
		pnlInformationDisplayQuiz.add(lblScore);

		lblNumQuestion = new JLabel("Question");
		lblNumQuestion.setBounds(10, 33, 136, 14);
		pnlInformationDisplayQuiz.add(lblNumQuestion);

		JRadioButton rdbtn1 = new JRadioButton("");
		rdbtn1.setActionCommand("0");
		rdbtn1.setBounds(153, 168, 249, 21);
		pnlDisplayQuiz.add(rdbtn1);

		JRadioButton rdbtn2 = new JRadioButton("");
		rdbtn2.setActionCommand("1");
		rdbtn2.setBounds(153, 210, 249, 21);
		pnlDisplayQuiz.add(rdbtn2);

		JRadioButton rdbtn3 = new JRadioButton("");
		rdbtn3.setActionCommand("2");
		rdbtn3.setBounds(153, 247, 249, 21);
		pnlDisplayQuiz.add(rdbtn3);

		JRadioButton rdbtn4 = new JRadioButton("");
		rdbtn4.setActionCommand("3");
		rdbtn4.setBounds(153, 293, 249, 21);
		pnlDisplayQuiz.add(rdbtn4);

		JRadioButton rdbtn5 = new JRadioButton("");
		rdbtn5.setActionCommand("4");
		rdbtn5.setBounds(153, 340, 249, 21);
		rdbtn5.setVisible(false);
		rdbtn5.setSelected(true);
		pnlDisplayQuiz.add(rdbtn5);
		bgAnswer.add(rdbtn1);
		bgAnswer.add(rdbtn2);
		bgAnswer.add(rdbtn3);
		bgAnswer.add(rdbtn4);
		bgAnswer.add(rdbtn5);

		JButton btnValider = new JButton("Valider");
		btnValider.setBounds(501, 214, 170, 48);
		btnValider.addActionListener(this::questionTreatment);
		pnlDisplayQuiz.add(btnValider);

		lblErrorDisplayQuiz = new JLabel("");
		lblErrorDisplayQuiz.setForeground(Color.RED);
		lblErrorDisplayQuiz.setBounds(183, 394, 249, 13);
		pnlDisplayQuiz.add(lblErrorDisplayQuiz);
		getContentPane().add(pnlResultAnswer);

		JButton btnNextQuestion = new JButton("Question suivante");
		btnNextQuestion.setBounds(276, 286, 148, 21);
		pnlResultAnswer.add(btnNextQuestion);

		btnNextQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (numCurrentQuestion <= numberOfQuestion) {
					nextQuestion();
				} else {
					endQuiz();
				}
			}
		});

		lblAnswer = new JLabel("");
		lblAnswer.setBounds(209, 164, 287, 24);
		pnlResultAnswer.add(lblAnswer);

		pnlMultiJoinGame = new JPanel();
		pnlMultiJoinGame.setVisible(false);
		pnlMultiJoinGame.setBounds(10, 10, 678, 453);
		getContentPane().add(pnlMultiJoinGame);
		pnlMultiJoinGame.setLayout(null);

	}

	private JComboBox<Object> createJComboBoxSolo()
			throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		listeNbQuestionSolo = new JComboBox<>();

		int nombreTotalQuestion = monController.getLaBase().nombreTotalQuestion();
		nombreTotalQuestion = (int) (Math.floor(nombreTotalQuestion / 5));

		for (int i = 1; i < nombreTotalQuestion + 1; i++) {
			listeNbQuestionSolo.addItem(i * 5);
		}

		return listeNbQuestionSolo;

	}

	private JComboBox<Object> createJComboBoxMulti()
			throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		listeNbQuestionMulti = new JComboBox<>();

		int nombreTotalQuestion = monController.getLaBase().nombreTotalQuestion();
		nombreTotalQuestion = (int) (Math.floor(nombreTotalQuestion / 5));

		for (int i = 1; i < nombreTotalQuestion + 1; i++) {
			listeNbQuestionMulti.addItem(i * 5);
		}

		return listeNbQuestionMulti;

	}

	private boolean verificationChampNom() {
		if (textNom.getText().isEmpty()) {
			lblErrorStartQuiz.setText("Veuillez écrire votre prénom !");
			return false;
		}
		return true;
	}

	private boolean verificationChampGroup() {
		if (textGroup.getText().isEmpty()) {
			// lblErrorStartQuiz.setText("Veuillez écrire votre prénom !");
			return false;
		}
		return true;
	}

	private void setVisible() {
		System.out.println("Start game : \nNom : " + textNom.getText() + "\nNombre de question : "
				+ listeNbQuestionSolo.getSelectedItem());
		lblErrorStartQuiz.setText("");
		pnlSoloCreateGame.setVisible(false);
		pnlDisplayQuiz.setVisible(true);
	}

	private void startSoloMode(Player player) {
		try {
			ArrayList<Question> quizQuestions;
			quizQuestions = monController.getLaBase().getQuestions(numberOfQuestion);
			monController.setLaGame(new QuizGame(monController, player, quizQuestions));
			currentQuestion = monController.getLaGame().getQuestions().get(numCurrentQuestion - 1);
			lblQuestion.setText(currentQuestion.getDescriptionQuestion());
			Enumeration<AbstractButton> allButtons = bgAnswer.getElements();

			for (Answer answer : currentQuestion.getAnswers()) {
				allButtons.nextElement().setText(answer.getDescriptionAnswer());
			}

			lblScore.setText("Score : " + String.valueOf(monController.getLaGame().getMyPlayer().getMyScore()));
			lblNumQuestion.setText(
					"Question " + String.valueOf(numCurrentQuestion) + " sur " + String.valueOf(numberOfQuestion));

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
			lblQuestion.setText(currentQuestion.getDescriptionQuestion());
			Enumeration<AbstractButton> allButtons = bgAnswer.getElements();

			for (Answer answer : currentQuestion.getAnswers()) {
				allButtons.nextElement().setText(answer.getDescriptionAnswer());
			}

			lblScore.setText("Score : " + String.valueOf(monController.getLaGame().getMyPlayer().getMyScore()));
			lblNumQuestion.setText(
					"Question " + String.valueOf(numCurrentQuestion) + " sur " + String.valueOf(numberOfQuestion));

			numCurrentQuestion++;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void lancementQuiz(ActionEvent event, Boolean multiplayer) {
		// Vérifie si le nom est entré

		setVisible();
		numberOfQuestion = (int) listeNbQuestionSolo.getSelectedItem();
		numCurrentQuestion = 1;

		if (multiplayer) {
			Player thePlayer = new Player(monController, textNom.getText(), 0 /* get player's group id */);
			startMultiplayerMode(thePlayer);

		} else {
			Player thePlayer = new Player(monController, textNom.getText(), 0);
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

	private void questionTreatment(ActionEvent event) {
		// Num du bouton radio sélectionné
		int bgSelected = Integer.parseInt(bgAnswer.getSelection().getActionCommand());

		if (this.isValidAnswer(bgSelected)) {
			// Changement de panel
			pnlDisplayQuiz.setVisible(false);
			pnlResultAnswer.setVisible(true);
			lblErrorDisplayQuiz.setText("");

			if (monController.getLaGame().isCorrectThisAnswer(currentQuestion, bgSelected)) {
				// Affiche que la réponse est correcte
				lblAnswer.setText("Bonne réponse Vous avez gagné 10 points");
			} else {
				// Affiche que la réponse est fausse
				lblAnswer.setText("Mauvaise réponse");
			}
		} else {
			lblErrorDisplayQuiz.setText("Veuillez séléctionner une réponse");
		}
	}

	private void nextQuestion() {
		// Crée la liste de bouton radio
		Enumeration<AbstractButton> allButtons = bgAnswer.getElements();

		// Changement de panel
		pnlDisplayQuiz.setVisible(true);
		pnlResultAnswer.setVisible(false);

		// Affiche les informations du quiz : Score + Num Question
		lblScore.setText("Score : " + String.valueOf(monController.getLaGame().getMyPlayer().getMyScore()));
		lblNumQuestion
				.setText("Question " + String.valueOf(numCurrentQuestion) + " sur " + String.valueOf(numberOfQuestion));

		// Selectionne la question en cours
		currentQuestion = monController.getLaGame().getQuestions().get(numCurrentQuestion - 1);

		// Affiche la description de la question
		lblQuestion.setText(currentQuestion.getDescriptionQuestion());

		// Affiche les réponses
		for (Answer answer : currentQuestion.getAnswers()) {
			allButtons.nextElement().setText(answer.getDescriptionAnswer());
		}

		numCurrentQuestion++;

		// Selectionne le dernier bouton radio
		allButtons.nextElement().setSelected(true);
	}

	private void endQuiz() {
		// Changement de panel
		pnlResultAnswer.setVisible(false);
		pnlEndQuiz.setVisible(true);

		// Affiche les informations final du quiz : Score
		lblFinalScore.setText("Votre score final est de "
				+ String.valueOf(monController.getLaGame().getMyPlayer().getMyScore()) + " point(s).");
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

}