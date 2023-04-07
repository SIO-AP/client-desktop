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

		monController.getLaConsole().setBackground("img/link 2.png");

		setOpaque(false);

		this.setBounds(ConsoleGUI.rectangle);
		this.setLayout(null);

		JButton btnNextQuestion = new JButton();
		btnNextQuestion.setBounds(264, 200, 150, 30);
		btnNextQuestion.setFont(new Font("Tahoma", Font.PLAIN, 13));		
		if (monController.getMonPlayer().getNbQuestion() <= monController.getLaGame().getNbQuestion()) {
			// Question suivante
			btnNextQuestion.setText("Question suivante");
		} else {
			// Fin du Quiz
			btnNextQuestion.setText("Résultat");
		}			
		this.add(btnNextQuestion);

		btnNextQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.NextPanel(monController.getLaConsole().getPnlResultAnswer());
			}
		});

		lblAnswer = new JLabel("", JLabel.CENTER);
		lblAnswer.setBounds(10, 100, 658, 20);
		lblAnswer.setFont(new Font("Tahoma", Font.PLAIN, 15));
		this.add(lblAnswer);
		
		if (monController.getLaConsole().isMulti()) {
			tablePlayer = new TablePlayer(monController, 139, 290, 400, 150, true);
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
