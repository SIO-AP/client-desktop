package control;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.Controller;

public class PnlEndQuiz extends JPanel {

	private Controller monController;
	private JLabel lblFinalScore;

	public PnlEndQuiz(Controller unController) {
		monController = unController;

		this.setBounds(10, 10, 678, 453);
		this.setLayout(null);

		JLabel lblEndQuiz = new JLabel("Fin du Quiz");
		lblEndQuiz.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEndQuiz.setHorizontalAlignment(SwingConstants.CENTER);
		lblEndQuiz.setBounds(10, 59, 658, 26);
		this.add(lblEndQuiz);

		lblFinalScore = new JLabel("Votre score final est de "
				+ String.valueOf(monController.getLaGame().getMyPlayer().getMyScore()) + " point(s).");
		lblFinalScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblFinalScore.setBounds(10, 163, 658, 32);
		this.add(lblFinalScore);

	}
}
