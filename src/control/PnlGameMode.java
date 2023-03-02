package control;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.Controller;

public class PnlGameMode extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textNom;
	private JLabel lblErrorStartQuiz;
	private Controller monController;

	public PnlGameMode(Controller unController) {

		monController = unController;

		this.setBounds(10, 10, 678, 453);
		this.setLayout(null);

		textNom = new JTextField();
		textNom.setBounds(204, 215, 287, 48);
		this.add(textNom);
		textNom.setColumns(10);

		JLabel lblNom = new JLabel("Nom d'utilisateur :");
		lblNom.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNom.setBounds(40, 212, 153, 48);
		this.add(lblNom);

		JButton btnSoloPlayer = new JButton("Solo");
		btnSoloPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.getLaConsole().setMulti(false);
				monController.getLaConsole().NextPanel(monController.getLaConsole().getPnlGameMode());
			}
		});

		btnSoloPlayer.setBounds(164, 326, 138, 34);
		this.add(btnSoloPlayer);

		JButton btnMultiPlayer = new JButton("Multijoueurs");
		btnMultiPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.getLaConsole().setMulti(true);
				monController.getLaConsole().NextPanel(monController.getLaConsole().getPnlGameMode());
			}
		});
		btnMultiPlayer.setBounds(393, 326, 138, 34);
		this.add(btnMultiPlayer);

		JLabel lblTitreGameMode = new JLabel("Bienvenue dans le Quiz Game Vinci");
		lblTitreGameMode.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitreGameMode.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitreGameMode.setBounds(10, 68, 658, 34);
		this.add(lblTitreGameMode);

		lblErrorStartQuiz = new JLabel("");
		lblErrorStartQuiz.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorStartQuiz.setBounds(0, 394, 678, 13);
		this.add(lblErrorStartQuiz);
		lblErrorStartQuiz.setForeground(Color.RED);

	}

	public JTextField getTextNom() {
		return textNom;
	}

	public void setTextNom(JTextField textNom) {
		this.textNom = textNom;
	}

	public JLabel getLblErrorStartQuiz() {
		return lblErrorStartQuiz;
	}

	public void setLblErrorStartQuiz(JLabel lblErrorStartQuiz) {
		this.lblErrorStartQuiz = lblErrorStartQuiz;
	}

}
