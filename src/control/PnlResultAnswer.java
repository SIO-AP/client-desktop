package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;

public class PnlResultAnswer extends JPanel {

	private Controller monController;

	private JLabel lblAnswer;

	public PnlResultAnswer(Controller unController) {
		monController = unController;

		this.setBounds(10, 10, 678, 453);
		this.setLayout(null);

		JButton btnNextQuestion = new JButton("Question suivante");
		btnNextQuestion.setBounds(276, 286, 148, 21);
		this.add(btnNextQuestion);

		btnNextQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.getLaConsole().NextPanel(monController.getLaConsole().getPnlResultAnswer());
			}
		});

		lblAnswer = new JLabel("");
		lblAnswer.setBounds(209, 164, 287, 24);
		this.add(lblAnswer);
	}

	public JLabel getLblAnswer() {
		return lblAnswer;
	}

	public void setLblAnswer(JLabel lblAnswer) {
		this.lblAnswer = lblAnswer;
	}

}
