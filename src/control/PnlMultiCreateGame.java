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
import model.Party;

public class PnlMultiCreateGame extends JPanel {

	private Controller monController;

	private JTextField txtNameParty;
	private ListNbQuestion listeNbQuestion;

	public PnlMultiCreateGame(Controller unController) {

		monController = unController;

		this.setBounds(10, 10, 678, 453);
		this.setLayout(null);

		txtNameParty = new JTextField();
		txtNameParty.setBounds(204, 157, 287, 48);
		txtNameParty.setColumns(10);
		this.add(txtNameParty);

		JLabel lblNameParty = new JLabel("Nom de la partie :");
		lblNameParty.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNameParty.setBounds(41, 154, 153, 48);
		this.add(lblNameParty);

		JLabel lblNbQuestionMulti = new JLabel("Nombre de questions :");
		lblNbQuestionMulti.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNbQuestionMulti.setBounds(40, 222, 190, 32);
		this.add(lblNbQuestionMulti);

		JButton btnLancementQuizMulti = new JButton("Commencer");
		btnLancementQuizMulti.setBounds(501, 214, 170, 48);
		this.add(btnLancementQuizMulti);
		btnLancementQuizMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nbQuestion = (int) listeNbQuestion.getSelectedItem();
				monController.setLaParty(new Party(txtNameParty.getText(), monController.getMonPlayer().getMyId(), nbQuestion));
				monController.getLaConsole().setCreateGameMulti(true);
				monController.getLeClient().createGame();
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
