package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import controller.Controller;
import model.Answer;
import model.Question;

public class PnlDisplayQuiz extends JPanel {

	private Controller monController;

	private ButtonGroup bgAnswer = new ButtonGroup();
	private JLabel lblQuestion;
	private JLabel lblScore;
	private JLabel lblNumQuestion;
	private JLabel lblErrorDisplayQuiz;

	public PnlDisplayQuiz(Controller unController, Question currentQuestion) {
		monController = unController;

		this.setBounds(10, 10, 678, 453);
		this.setLayout(null);

		JPanel pnlInformationDisplayQuiz = new JPanel();
		pnlInformationDisplayQuiz.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlInformationDisplayQuiz.setBounds(37, 350, 178, 57);
		this.add(pnlInformationDisplayQuiz);
		pnlInformationDisplayQuiz.setLayout(null);

		lblQuestion = new JLabel("");
		lblQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuestion.setBounds(10, 71, 661, 37);
		this.add(lblQuestion);

		lblScore = new JLabel("Score");
		lblScore.setBounds(10, 10, 66, 13);
		pnlInformationDisplayQuiz.add(lblScore);

		lblNumQuestion = new JLabel("Question");
		lblNumQuestion.setBounds(10, 33, 136, 14);
		pnlInformationDisplayQuiz.add(lblNumQuestion);

		JRadioButton rdbtn1 = new JRadioButton("");
		rdbtn1.setActionCommand("0");
		rdbtn1.setBounds(62, 167, 418, 21);
		this.add(rdbtn1);

		JRadioButton rdbtn2 = new JRadioButton("");
		rdbtn2.setActionCommand("1");
		rdbtn2.setBounds(62, 209, 418, 21);
		this.add(rdbtn2);

		JRadioButton rdbtn3 = new JRadioButton("");
		rdbtn3.setActionCommand("2");
		rdbtn3.setBounds(62, 246, 418, 21);
		this.add(rdbtn3);

		JRadioButton rdbtn4 = new JRadioButton("");
		rdbtn4.setActionCommand("3");
		rdbtn4.setBounds(62, 292, 418, 21);
		this.add(rdbtn4);

		JRadioButton rdbtn5 = new JRadioButton("");
		rdbtn5.setActionCommand("4");
		rdbtn5.setBounds(153, 340, 249, 21);
		rdbtn5.setVisible(false);
		rdbtn5.setSelected(true);
		this.add(rdbtn5);
		bgAnswer.add(rdbtn1);
		bgAnswer.add(rdbtn2);
		bgAnswer.add(rdbtn3);
		bgAnswer.add(rdbtn4);
		bgAnswer.add(rdbtn5);

		JButton btnValider = new JButton("Valider");
		btnValider.setBounds(501, 214, 170, 48);
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.getLaConsole().questionTreatment();
			}
		});
		this.add(btnValider);

		lblErrorDisplayQuiz = new JLabel("");
		lblErrorDisplayQuiz.setForeground(Color.RED);
		lblErrorDisplayQuiz.setBounds(183, 394, 249, 13);
		this.add(lblErrorDisplayQuiz);

		lblQuestion.setText(currentQuestion.getDescriptionQuestion());

		Enumeration<AbstractButton> allButtons = bgAnswer.getElements();

		for (Answer answer : currentQuestion.getAnswers()) {
			allButtons.nextElement().setText(answer.getDescriptionAnswer());
		}

		lblScore.setText("Score : " + String.valueOf(monController.getLaGame().getMyPlayer().getMyScore()));
		lblNumQuestion.setText("Question " + String.valueOf(monController.getLaConsole().getNumCurrentQuestion())
				+ " sur " + String.valueOf(monController.getLaConsole().getNumberOfQuestion()));

		// Selectionne le dernier bouton radio
		allButtons.nextElement().setSelected(true);
	}

	public ButtonGroup getBgAnswer() {
		return bgAnswer;
	}

	public void setBgAnswer(ButtonGroup bgAnswer) {
		this.bgAnswer = bgAnswer;
	}

	public JLabel getLblQuestion() {
		return lblQuestion;
	}

	public void setLblQuestion(JLabel lblQuestion) {
		this.lblQuestion = lblQuestion;
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

	public JLabel getLblErrorDisplayQuiz() {
		return lblErrorDisplayQuiz;
	}

	public void setLblErrorDisplayQuiz(JLabel lblErrorDisplayQuiz) {
		this.lblErrorDisplayQuiz = lblErrorDisplayQuiz;
	}

}
