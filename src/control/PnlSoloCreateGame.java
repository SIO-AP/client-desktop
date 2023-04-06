package control;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;
import view.ConsoleGUI;

public class PnlSoloCreateGame extends JPanel {

	private Controller monController;
	private ListNbQuestion listeNbQuestion;

	public PnlSoloCreateGame(Controller unController) {
		monController = unController;

		monController.getLaConsole().setBackground("img/link 2.png");

		setOpaque(false);

		this.setBounds(ConsoleGUI.rectangle);
		this.setLayout(null);

		JLabel lblTitre = new JLabel("Quiz Game");
		lblTitre.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitre.setBounds(278, 84, 200, 65);
		this.add(lblTitre);

		JButton btnLancementQuizSolo = new JButton("Commencer");
		btnLancementQuizSolo.setBounds(501, 214, 170, 48);
		this.add(btnLancementQuizSolo);
		btnLancementQuizSolo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.NextPanel(monController.getLaConsole().getPnlSoloCreateGame());
			}
		});

		JButton btnSoloReturn = new JButton("Retour");
		btnSoloReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.PreviousPanel(monController.getLaConsole().getPnlSoloCreateGame());
			}
		});
		btnSoloReturn.setBounds(267, 376, 103, 35);
		this.add(btnSoloReturn);

		JLabel txtpnWelcomeToQuizzyquiz = new JLabel();
		txtpnWelcomeToQuizzyquiz.setText(
				"Welcome to the VinciQuiz,\r\nEvery right answer will give you 10 points. Good Luck ! ^^");
		txtpnWelcomeToQuizzyquiz.setBounds(132, 309, 416, 75);
		this.add(txtpnWelcomeToQuizzyquiz);

		JLabel lblNbQuestionSolo = new JLabel("Nombre de question :");
		lblNbQuestionSolo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNbQuestionSolo.setBounds(40, 222, 190, 32);
		this.add(lblNbQuestionSolo);

		// Création de la liste déroulante pour le nombre de question
		listeNbQuestion = new ListNbQuestion(monController);
		this.add(listeNbQuestion);
	}

	public ListNbQuestion getListeNbQuestion() {
		return listeNbQuestion;
	}

	public void setListeNbQuestion(ListNbQuestion listeNbQuestion) {
		this.listeNbQuestion = listeNbQuestion;
	}

}
