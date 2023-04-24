package control;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;
import view.ConsoleGUI;

public class PnlResultAnswer extends JPanel {

	private Controller monController;

	private JLabel lblAnswer;
	private TablePlayer tablePlayer;

	public PnlResultAnswer(Controller unController) {
		monController = unController;

		monController.getLaConsole().setBackground("img/PnlResultAnswer/back.png");

		setOpaque(false);

		this.setBounds(ConsoleGUI.rectangle);
		this.setLayout(null);

		ButtonDisplay btnNextQuestion;

		if (monController.getMonPlayer().getNbQuestion() + 1 <= monController.getLaGame().getNbQuestion()) {
			// Question suivante
			btnNextQuestion = new ButtonDisplay(150, 220, 500, 50, "img/PnlResultAnswer/question_suivante_eteint.png",
					"img/PnlResultAnswer/question_suivante_allume.png");
		} else {
			// Fin du Quiz
			btnNextQuestion = new ButtonDisplay(150, 220, 500, 50, "img/PnlResultAnswer/resultats_eteint.png",
					"img/PnlResultAnswer/resultats_allume.png");

		}
		this.add(btnNextQuestion);

		btnNextQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.NextPanel(monController.getLaConsole().getPnlResultAnswer());
			}
		});

		lblAnswer = new JLabel("", JLabel.CENTER);
		lblAnswer.setBounds(40, 100, 700, 50);
		lblAnswer.setFont(new Font("Corbel", Font.PLAIN, 20));
		this.add(lblAnswer);

		if (monController.isMulti()) {
			tablePlayer = new TablePlayer(monController, 60, 300, 630, 200, true);
			this.add(tablePlayer);
		}

	}

	public JLabel getLblAnswer() {
		return lblAnswer;
	}

	public void setLblAnswer(JLabel lblAnswer) {
		this.lblAnswer = lblAnswer;
	}

	public TablePlayer getTablePlayer() {
		return tablePlayer;
	}

	public void setTablePlayer(TablePlayer tablePlayer) {
		this.tablePlayer = tablePlayer;
	}

}
