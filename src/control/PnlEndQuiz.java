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

		monController.getLaConsole().setBackground("img/PnlEndQuiz/back.png");

		setOpaque(false);

		this.setBounds(ConsoleGUI.rectangle);
		this.setLayout(null);

		JLabel lblEndQuiz = new JLabel("Fin du Quiz");
		lblEndQuiz.setFont(new Font("Corbel", Font.BOLD, 20));
		lblEndQuiz.setHorizontalAlignment(SwingConstants.CENTER);
		lblEndQuiz.setBounds(545, 100, 520, 30);
		this.add(lblEndQuiz);

		lblFinalScore = new JLabel(
				"Votre score final est de " + String.valueOf(monController.getMonPlayer().getMyScore()) + " point(s).");
		lblFinalScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblFinalScore.setFont(new Font("Corbel", Font.PLAIN, 20));
		lblFinalScore.setBounds(545, 160, 520, 30);
		this.add(lblFinalScore);

		ButtonDisplay btnMultiReturn = new ButtonDisplay(700, 500, 250, 50, "img/PnlEndQuiz/menu_eteint.png",
				"img/PnlEndQuiz/menu_allume.png");
		btnMultiReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.NextPanel(monController.getLaConsole().getPnlEndQuiz());
			}
		});
		this.add(btnMultiReturn);

		if (monController.isMulti()) {
			tablePlayer = new TablePlayer(monController, 545, 260, 520, 200, true);
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
