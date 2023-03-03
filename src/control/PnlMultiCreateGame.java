package control;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.Controller;

public class PnlMultiCreateGame extends JPanel {

	private Controller monController;

	private JTextField textGroup;
	private ListNbQuestion listeNbQuestion;

	public PnlMultiCreateGame(Controller unController) {

		monController = unController;

		this.setBounds(10, 10, 678, 453);
		this.setLayout(null);

		textGroup = new JTextField();
		textGroup.setBounds(204, 157, 287, 48);
		textGroup.setColumns(10);
		this.add(textGroup);

		JLabel lblGroup = new JLabel("Nom du groupe :");
		lblGroup.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGroup.setBounds(41, 154, 153, 48);
		this.add(lblGroup);

		JLabel lblNbQuestionMulti = new JLabel("Nombre de questions :");
		lblNbQuestionMulti.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNbQuestionMulti.setBounds(40, 222, 190, 32);
		this.add(lblNbQuestionMulti);

		JButton btnLancementQuizMulti = new JButton("Commencer");
		btnLancementQuizMulti.setBounds(501, 214, 170, 48);
		this.add(btnLancementQuizMulti);
		btnLancementQuizMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		JButton btnMultiReturn = new JButton("Annuler");
		btnMultiReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.getLaConsole().PreviousPanel(monController.getLaConsole().getPnlMultiCreateGame());
			}
		});
		btnMultiReturn.setBounds(267, 376, 103, 35);
		this.add(btnMultiReturn);

		// Création de la liste déroulante pour le nombre de question
		listeNbQuestion = new ListNbQuestion(monController);
		this.add(listeNbQuestion);
	}
}
