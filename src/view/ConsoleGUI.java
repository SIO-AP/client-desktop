
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

	private JPanel pnlStartGame;
	private JPanel pnlDisplayQuiz;
	private JPanel pnlResultAnswer;
	private JPanel pnlEndQuiz;
	private JLabel lblAnswer;
	private JLabel lblScore;
	private JTextField textNom;
	private JLabel lblQuestion;
	private JLabel lblFinalScore;
	private ButtonGroup bgAnswer = new ButtonGroup();
	private int numberOfQuestion;
	private int numCurrentQuestion;
	private Question currentQuestion;
	private JLabel lblErrorDisplayQuiz;
	private JLabel lblErrorStartQuiz;
	private JComboBox<Object> listeNbQuestion;
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

		pnlResultAnswer = new JPanel();
		pnlResultAnswer.setBounds(10, 10, 678, 453);
		pnlResultAnswer.setLayout(null);
		pnlResultAnswer.setVisible(false);

		// D�finit le JPanel de l'affichage des questions
		pnlDisplayQuiz = new JPanel();
		pnlDisplayQuiz.setBounds(10, 10, 678, 453);
		pnlDisplayQuiz.setLayout(null);
		pnlDisplayQuiz.setVisible(false);
		pane.add(pnlDisplayQuiz);
		pnlDisplayQuiz.setLayout(null);

		JPanel pnlInformationDisplayQuiz = new JPanel();
		pnlInformationDisplayQuiz.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlInformationDisplayQuiz.setBounds(37, 350, 178, 57);
		pnlDisplayQuiz.add(pnlInformationDisplayQuiz);
		getContentPane().add(pnlResultAnswer);
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

		// D�finit le JPanel du start
		pnlStartGame = new JPanel();
		pnlStartGame.setBounds(10, 10, 678, 453);
		pnlStartGame.setLayout(null);
		pane.add(pnlStartGame);

		textNom = new JTextField();
		textNom.setBounds(204, 215, 287, 48);
		pnlStartGame.add(textNom);
		textNom.setColumns(10);

		JLabel lblTitre = new JLabel("Quiz Game");
		lblTitre.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitre.setBounds(278, 84, 200, 65);
		pnlStartGame.add(lblTitre);

		JLabel lblNom = new JLabel("Nom d'utilisateur :");
		lblNom.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNom.setBounds(40, 212, 153, 48);
		pnlStartGame.add(lblNom);

		JButton btnLancementQuiz = new JButton("Commencer");
		btnLancementQuiz.setBounds(501, 214, 170, 48);
		pnlStartGame.add(btnLancementQuiz);
		btnLancementQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lancementQuiz(e, false); // get if its multiplayer
			}
		});
		/*
		JButton btnLancementMulti = new JButton("Multi joueur");
		btnLancementMulti.setBounds(501, 150, 170, 48);
		pnlStartGame.add(btnLancementMulti);
		btnLancementMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientWebsocket client = new ClientWebsocket(); // get if its multiplayer
				Party leGroupe = new Party(0, "test", 1, null, null);
				client.createParty(leGroupe);
			}
		});*/

		lblErrorStartQuiz = new JLabel("");
		lblErrorStartQuiz.setBounds(183, 394, 249, 13);
		pnlStartGame.add(lblErrorStartQuiz);
		lblErrorStartQuiz.setForeground(Color.RED);

		JLabel txtpnWelcomeToQuizzyquiz = new JLabel();
		txtpnWelcomeToQuizzyquiz.setText(
				"Welcome to the VinciQuiz,\r\nEvery right answer will give you 10 points, and you will have 5 questions..\r\nIn order to win you'll need 30 points. Good Luck ! ^^");
		txtpnWelcomeToQuizzyquiz.setBounds(132, 309, 416, 75);
		pnlStartGame.add(txtpnWelcomeToQuizzyquiz);

		JLabel lblNbQuestion = new JLabel("Nombre de questions :");
		lblNbQuestion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNbQuestion.setBounds(40, 273, 190, 32);
		pnlStartGame.add(lblNbQuestion);

		// Création de la liste déroulante pour le nombre de question
		listeNbQuestion = createJComboBox();
		listeNbQuestion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		listeNbQuestion.setBounds(204, 265, 153, 48);
		pnlStartGame.add(listeNbQuestion);

		pnlEndQuiz = new JPanel();
		pnlEndQuiz.setBounds(10, 10, 678, 453);
		pnlEndQuiz.setLayout(null);
		pnlEndQuiz.setVisible(false);
		getContentPane().add(pnlEndQuiz);

		JLabel lblEndQuiz = new JLabel("Fin du Quiz");
		lblEndQuiz.setHorizontalAlignment(SwingConstants.CENTER);
		lblEndQuiz.setBounds(10, 59, 658, 26);
		pnlEndQuiz.add(lblEndQuiz);

		lblFinalScore = new JLabel("");
		lblFinalScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblFinalScore.setBounds(10, 163, 658, 32);
		pnlEndQuiz.add(lblFinalScore);

	}

	private JComboBox<Object> createJComboBox()
			throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		listeNbQuestion = new JComboBox<>();

		int nombreTotalQuestion = monController.getLaBase().nombreTotalQuestion();
		nombreTotalQuestion = (int) (Math.floor(nombreTotalQuestion / 5));

		for (int i = 1; i < nombreTotalQuestion + 1; i++) {
			listeNbQuestion.addItem(i * 5);
		}

		return listeNbQuestion;

	}
	
	private boolean verificationChamp() {
		if (textNom.getText() == null) {
			lblErrorStartQuiz.setText("Veuillez écrire votre prénom !");
			return false;
		} return true;
	}

	private void setVisible() {
		System.out.println("Start game : \nNom : " + textNom.getText() + "\nNombre de question : "+ listeNbQuestion.getSelectedItem());
		lblErrorStartQuiz.setText("");
		pnlStartGame.setVisible(false);
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
			lblNumQuestion.setText("Question " + String.valueOf(numCurrentQuestion) + " sur " + String.valueOf(numberOfQuestion));

			numCurrentQuestion++;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private void lancementQuiz(ActionEvent event, Boolean multiplayer) {
		// Vérifie si le nom est entré
		if (verificationChamp()) {
			setVisible();
			numberOfQuestion = (int) listeNbQuestion.getSelectedItem();
			numCurrentQuestion = 1;
			
			if(multiplayer) {
				Player thePlayer = new Player(monController, textNom.getText(), 0 /* get player's group id */);
				// start mutli mode

			} else {
				Player thePlayer = new Player(monController, textNom.getText(), 0);
				startSoloMode(thePlayer);
			}

			
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