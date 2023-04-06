package control;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;

import controller.Controller;
import model.Game;
import view.ConsoleGUI;

public class PnlMultiCreateGame extends JPanel {

	private Controller monController;

	private JTextField txtNameParty;
	private ListNbQuestion listeNbQuestion;
	private JSpinner spinnerTimeStart;
	private SpinnerDateModel model;

	public PnlMultiCreateGame(Controller unController) {
		monController = unController;

		monController.getLaConsole().setBackground("img/link 2.png");

		setOpaque(false);

		this.setBounds(ConsoleGUI.rectangle);
		this.setLayout(null);

		txtNameParty = new JTextField();
		txtNameParty.setBounds(204, 157, 287, 48);
		txtNameParty.setColumns(10);
		this.add(txtNameParty);

		JLabel lblNameParty = new JLabel("Nom de la partie :");
		lblNameParty.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNameParty.setBounds(41, 162, 190, 32);
		this.add(lblNameParty);

		JLabel lblNbQuestionMulti = new JLabel("Nombre de questions :");
		lblNbQuestionMulti.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNbQuestionMulti.setBounds(40, 222, 190, 32);
		this.add(lblNbQuestionMulti);

		JLabel lblTimeStart = new JLabel("Début de la partie :");
		lblTimeStart.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTimeStart.setBounds(41, 282, 190, 32);
		this.add(lblTimeStart);

		JButton btnLancementQuizMulti = new JButton("Commencer");
		btnLancementQuizMulti.setBounds(501, 214, 170, 48);
		this.add(btnLancementQuizMulti);
		btnLancementQuizMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nbQuestion = (int) listeNbQuestion.getSelectedItem();
				monController.setLaGame(new Game(txtNameParty.getText(), monController.getMonPlayer().getMyId(),
						nbQuestion, monController.getMonPlayer(), getHeure()));
				monController.getLaConsole().setCreateGameMulti(true);
				monController.getLeClient().createGame();
			}
		});

		JButton btnMultiReturn = new JButton("Annuler");
		btnMultiReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.PreviousPanel(monController.getLaConsole().getPnlMultiCreateGame());
			}
		});
		btnMultiReturn.setBounds(267, 376, 103, 35);
		this.add(btnMultiReturn);

		// Création de la liste déroulante pour le nombre de question
		listeNbQuestion = new ListNbQuestion(monController);
		this.add(listeNbQuestion);

		model = new SpinnerDateModel();
		spinnerTimeStart = new JSpinner(model);

		// Configuration de la zone de saisie
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerTimeStart, "HH:mm:ss");
		editor.getTextField().setEditable(false); // permettre la saisie manuelle
		spinnerTimeStart.setEditor(editor);
		spinnerTimeStart.setPreferredSize(new Dimension(150, 48));
		Font font = spinnerTimeStart.getFont();
		spinnerTimeStart.setFont(new Font(font.getName(), font.getStyle(), 15)); // taille de police de 20 points
		spinnerTimeStart.setBounds(204, 272, 153, 48);

		// Centrage de l'heure dans le spinner
		JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor) spinnerTimeStart.getEditor();
		spinnerEditor.getTextField().setHorizontalAlignment(SwingConstants.CENTER);

		this.add(spinnerTimeStart);

	}

	// Méthode pour récupérer l'heure saisie
	public String getHeure() {
		Date date = model.getDate();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		return format.format(date);
	}
}
