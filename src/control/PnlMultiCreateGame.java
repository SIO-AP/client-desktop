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
import model.Game;

public class PnlMultiCreateGame extends JPanel {

	private Controller monController;

	private JTextField txtNameParty;
	private JTextField txtTimeStart;
	private ListNbQuestion listeNbQuestion;

	public PnlMultiCreateGame(Controller unController) {

		monController = unController;
		setOpaque(false);
		this.setBounds(10, 10, 678, 453);
		this.setLayout(null);

		txtNameParty = new JTextField();
		txtNameParty.setBounds(204, 157, 287, 48);
		txtNameParty.setColumns(10);
		this.add(txtNameParty);
		
		txtTimeStart = new JTextField();
		txtTimeStart.setBounds(204, 292, 287, 48);
		txtTimeStart.setColumns(10);
		this.add(txtTimeStart);

		JLabel lblNameParty = new JLabel("Nom de la partie :");
		lblNameParty.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNameParty.setBounds(41, 154, 190, 32);
		this.add(lblNameParty);

		JLabel lblNbQuestionMulti = new JLabel("Nombre de questions :");
		lblNbQuestionMulti.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNbQuestionMulti.setBounds(40, 222, 190, 32);
		this.add(lblNbQuestionMulti);
		
		JLabel lblTimeStart = new JLabel("Début de la partie :");
		lblTimeStart.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTimeStart.setBounds(41, 290, 190, 32);
		this.add(lblTimeStart);

		JButton btnLancementQuizMulti = new JButton("Commencer");
		btnLancementQuizMulti.setBounds(501, 214, 170, 48);
		this.add(btnLancementQuizMulti);
		btnLancementQuizMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nbQuestion = (int) listeNbQuestion.getSelectedItem();
				monController.setLaGame(new Game(txtNameParty.getText(), monController.getMonPlayer().getMyId(), nbQuestion, monController.getMonPlayer()));
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
