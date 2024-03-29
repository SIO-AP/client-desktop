package control;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import controller.Controller;
import model.Answer;
import model.Question;
import view.ConsoleGUI;

public class PnlDisplayQuiz extends JPanel {

	private Controller monController;

	private ButtonGroup bgAnswer = new ButtonGroup();
	private JLabel lblQuestion;
	private JLabel lblScore;
	private JLabel lblNumQuestion;
	private JLabel lblErrorDisplayQuiz;
	private TablePlayer tablePlayer;

	public PnlDisplayQuiz(Controller unController, Question currentQuestion) {
		monController = unController;

		monController.getLaConsole().setBackground("img/PnlDisplayQuiz/back.png");

		setOpaque(false);

		this.setBounds(ConsoleGUI.rectangle);
		this.setLayout(null);

		lblQuestion = new JLabel("<html><p text-align: center>" + currentQuestion.getDescriptionQuestion() + "</p>",
				JLabel.CENTER);
		lblQuestion.setFont(new Font("Corbel", Font.PLAIN, 25));
		lblQuestion.setBounds(50, 30, 650, 60);
		this.add(lblQuestion);

		JRadioButton rdbtn1 = new JRadioButton("");
		rdbtn1.setActionCommand("0");
		rdbtn1.setBounds(60, 120, 600, 35);
		rdbtn1.setFont(new Font("corbel", Font.PLAIN, 17));
		rdbtn1.setOpaque(false);
		this.add(rdbtn1);

		JRadioButton rdbtn2 = new JRadioButton("");
		rdbtn2.setActionCommand("1");
		rdbtn2.setBounds(60, 165, 600, 35);
		rdbtn2.setFont(new Font("corbel", Font.PLAIN, 17));
		rdbtn2.setOpaque(false);
		this.add(rdbtn2);

		JRadioButton rdbtn3 = new JRadioButton("");
		rdbtn3.setActionCommand("2");
		rdbtn3.setBounds(60, 210, 600, 35);
		rdbtn3.setFont(new Font("Corbel", Font.PLAIN, 17));
		rdbtn3.setOpaque(false);
		this.add(rdbtn3);

		JRadioButton rdbtn4 = new JRadioButton("");
		rdbtn4.setActionCommand("3");
		rdbtn4.setBounds(60, 255, 600, 35);
		rdbtn4.setFont(new Font("Corbel", Font.PLAIN, 17));
		rdbtn4.setOpaque(false);
		this.add(rdbtn4);

		JRadioButton rdbtn5 = new JRadioButton("");
		rdbtn5.setActionCommand("4");
		rdbtn5.setBounds(60, 340, 249, 21);
		rdbtn5.setVisible(false);
		rdbtn5.setSelected(true);
		this.add(rdbtn5);
		
		bgAnswer.add(rdbtn1);
		bgAnswer.add(rdbtn2);
		bgAnswer.add(rdbtn3);
		bgAnswer.add(rdbtn4);
		bgAnswer.add(rdbtn5);

		ButtonDisplay btnValider = new ButtonDisplay(250, 320, 250, 50, "img/PnlDisplayQuiz/valider_eteint.png",
				"img/PnlDisplayQuiz/valider_allume.png");
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isValidAnswer();
			}
		});
		this.add(btnValider);

		lblErrorDisplayQuiz = new JLabel("Veuillez sélectionner une réponse", JLabel.CENTER);
		lblErrorDisplayQuiz.setForeground(Color.RED);
		lblErrorDisplayQuiz.setVisible(false);
		lblErrorDisplayQuiz.setBounds(10, 290, 658, 32);
		this.add(lblErrorDisplayQuiz);

		Enumeration<AbstractButton> allButtons = bgAnswer.getElements();

		for (Answer answer : currentQuestion.getAnswers()) {
			allButtons.nextElement().setText(
					"<html><p>" + monController.getTheDecrypter().decrypt(answer.getDescriptionAnswer()) + "</p>");
		}

		// Sélectionne le dernier bouton radio
		allButtons.nextElement().setSelected(true);

		if (monController.isMulti()) {
			tablePlayer = new TablePlayer(monController, 60, 380, 630, 200, true);
			this.add(tablePlayer);

		} else {
			JPanel pnlInformationDisplayQuiz = new JPanel();
			pnlInformationDisplayQuiz.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			pnlInformationDisplayQuiz.setBounds(40, 420, 180, 63);
			this.add(pnlInformationDisplayQuiz);
			pnlInformationDisplayQuiz.setLayout(null);

			lblScore = new JLabel("Score : " + String.valueOf(monController.getMonPlayer().getMyScore()));
			lblScore.setBounds(10, 13, 66, 16);
			lblScore.setFont(new Font("Corbel", Font.PLAIN, 13));
			pnlInformationDisplayQuiz.add(lblScore);

			lblNumQuestion = new JLabel("Question " + String.valueOf(monController.getMonPlayer().getNbQuestion())
					+ " sur " + String.valueOf(monController.getLaGame().getNbQuestion()));
			lblNumQuestion.setBounds(10, 36, 136, 16);
			lblNumQuestion.setFont(new Font("Corbel", Font.PLAIN, 13));
			pnlInformationDisplayQuiz.add(lblNumQuestion);
		}

	}

	private void isValidAnswer() {
		int bgSelected = Integer.parseInt(bgAnswer.getSelection().getActionCommand());
		if (bgSelected == 4) {
			lblErrorDisplayQuiz.setVisible(true);
		} else {
			monController.questionTreatment(bgSelected);
		}
	}

	public ButtonGroup getBgAnswer() {
		return bgAnswer;
	}

	public void setBgAnswer(ButtonGroup bgAnswer) {
		this.bgAnswer = bgAnswer;
	}

	public JLabel getLblScore() {
		return lblScore;
	}

	public void setLblScore(JLabel lblScore) {
		this.lblScore = lblScore;
	}

	public JLabel getLblNumQuestion() {
		return lblNumQuestion;
	}

	public void setLblNumQuestion(JLabel lblNumQuestion) {
		this.lblNumQuestion = lblNumQuestion;
	}

	public TablePlayer getTablePlayer() {
		return tablePlayer;
	}

	public void setTablePlayer(TablePlayer tablePlayer) {
		this.tablePlayer = tablePlayer;
	}

}
