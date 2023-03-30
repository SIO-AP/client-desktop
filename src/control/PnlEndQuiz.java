package control;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.Controller;

public class PnlEndQuiz extends JPanel {

	private Controller monController;
	private JLabel lblFinalScore;
	private TablePlayer tablePlayer;

	public PnlEndQuiz(Controller unController) {
		monController = unController;
		setOpaque(false);
		this.setBounds(10, 10, 678, 453);
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
				monController.getLaConsole().NextPanel(monController.getLaConsole().getPnlEndQuiz());
			}
		});
		btnMultiReturn.setBounds(267, 376, 103, 35);
		this.add(btnMultiReturn);
		
		if (monController.getLaConsole().isMulti()) {
			tablePlayer = new TablePlayer(monController, 257, 330, 400, 100);
			this.add(tablePlayer);
		}

	}
}
