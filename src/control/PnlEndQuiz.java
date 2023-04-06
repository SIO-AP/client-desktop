package control;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.Controller;
import view.ConsoleGUI;

public class PnlEndQuiz extends JPanel {

	private Controller monController;
	private JLabel lblFinalScore;
	private TablePlayer tablePlayer;

	public PnlEndQuiz(Controller unController) {
		monController = unController;

		monController.getLaConsole().setBackground("img/link 2.png");

		setOpaque(false);

		this.setBounds(ConsoleGUI.rectangle);
		this.setLayout(null);

		JLabel lblEndQuiz = new JLabel("Fin du Quiz");
		lblEndQuiz.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEndQuiz.setHorizontalAlignment(SwingConstants.CENTER);
		lblEndQuiz.setBounds(10, 59, 658, 26);
		this.add(lblEndQuiz);

		lblFinalScore = new JLabel(
				"Votre score final est de " + String.valueOf(monController.getMonPlayer().getMyScore()) + " point(s).");
		lblFinalScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblFinalScore.setBounds(10, 163, 658, 32);
		this.add(lblFinalScore);

		JButton btnMultiReturn = new JButton("Menu");
		btnMultiReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.NextPanel(monController.getLaConsole().getPnlEndQuiz());
			}
		});
		btnMultiReturn.setBounds(267, 376, 103, 35);
		this.add(btnMultiReturn);

		if (monController.getLaConsole().isMulti()) {
			tablePlayer = new TablePlayer(monController, 10, 90, 658, 246, true);
			this.add(tablePlayer);
		}

	}

	public TablePlayer getTablePlayer() {
		return tablePlayer;
	}

	public void setTablePlayer(TablePlayer tablePlayer) {
		this.tablePlayer = tablePlayer;
	}

}
