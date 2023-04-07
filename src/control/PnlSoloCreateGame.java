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

		monController.getLaConsole().setBackground("img/PnlCreateGame/back.png");

		setOpaque(false);

		this.setBounds(ConsoleGUI.rectangle);
		this.setLayout(null);

//		JLabel lblTitre = new JLabel("Quiz Game");
//		lblTitre.setFont(new Font("Tahoma", Font.PLAIN, 20));
//		lblTitre.setBounds(278, 84, 200, 65);
//		this.add(lblTitre);

		ButtonDisplay btnLancementQuizSolo = new ButtonDisplay(700, 100, 250, 50, "img/PnlCreateGame/commencer_eteint.png", "img/PnlCreateGame/commencer_allume.png");
		this.add(btnLancementQuizSolo);
		btnLancementQuizSolo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.NextPanel(monController.getLaConsole().getPnlSoloCreateGame());
			}
		});

		ButtonDisplay btnSoloReturn = new ButtonDisplay(700, 200, 250, 50, "img/PnlCreateGame/retour_eteint.png", "img/PnlCreateGame/retour_allume.png");
		btnSoloReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.PreviousPanel(monController.getLaConsole().getPnlSoloCreateGame());
			}
		});
		this.add(btnSoloReturn);

		JLabel lblNbQuestionSolo = new JLabel("Nombre de question :");
		lblNbQuestionSolo.setFont(new Font("Corbel", Font.BOLD, 20));
		lblNbQuestionSolo.setBounds(40, 222, 200, 40);
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
