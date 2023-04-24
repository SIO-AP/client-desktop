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

		monController.getLaConsole().setBackground("img/PnlCreateGame/back.png");

		setOpaque(false);

		this.setBounds(ConsoleGUI.rectangle);
		this.setLayout(null);

		JLabel lblNameParty = new JLabel("Nom de la partie :", JLabel.RIGHT);
		lblNameParty.setFont(new Font("Corbel", Font.PLAIN, 20));
		lblNameParty.setBounds(130, 95, 200, 40);
		this.add(lblNameParty);

		txtNameParty = new JTextField();
		txtNameParty.setBounds(355, 85, 230, 50);
		txtNameParty.setColumns(10);
		this.add(txtNameParty);

		JLabel lblNbQuestionMulti = new JLabel("Nombre de question :", JLabel.RIGHT);
		lblNbQuestionMulti.setFont(new Font("Corbel", Font.PLAIN, 20));
		lblNbQuestionMulti.setBounds(130, 160, 200, 40);
		this.add(lblNbQuestionMulti);

		// Création de la liste déroulante pour le nombre de question
		listeNbQuestion = new ListNbQuestion(monController, 355, 150, 85, 50);
		this.add(listeNbQuestion);

		JLabel lblTimeStart = new JLabel("Début de la partie :", JLabel.RIGHT);
		lblTimeStart.setFont(new Font("Corbel", Font.PLAIN, 20));
		lblTimeStart.setBounds(130, 225, 200, 40);
		this.add(lblTimeStart);

		model = new SpinnerDateModel();
		spinnerTimeStart = new JSpinner(model);

		// Configuration de la zone de saisie
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerTimeStart, "HH:mm:ss");
		editor.getTextField().setEditable(false); // permettre la saisie manuelle
		spinnerTimeStart.setEditor(editor);
		spinnerTimeStart.setPreferredSize(new Dimension(150, 48));
		//Font font = spinnerTimeStart.getFont();
		spinnerTimeStart.setFont(new Font("Cordel", Font.PLAIN, 15));
		spinnerTimeStart.setBounds(355, 215, 153, 48);

		// Centrage de l'heure dans le spinner
		JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor) spinnerTimeStart.getEditor();
		spinnerEditor.getTextField().setHorizontalAlignment(SwingConstants.CENTER);
		this.add(spinnerTimeStart);

		ButtonDisplay btnLancementQuizMulti = new ButtonDisplay(700, 100, 250, 50,
				"img/PnlCreateGame/commencer_eteint.png", "img/PnlCreateGame/commencer_allume.png");
		this.add(btnLancementQuizMulti);
		btnLancementQuizMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nbQuestion = (int) listeNbQuestion.getSelectedItem();
				monController.setLaGame(new Game(txtNameParty.getText(), monController.getMonPlayer().getMyId(),
						nbQuestion, monController.getMonPlayer(), getHeure()));
				monController.setCreateGameMulti(true);
				monController.getLeClient().createGame();
			}
		});

		ButtonDisplay btnMultiReturn = new ButtonDisplay(700, 200, 250, 50, "img/PnlCreateGame/retour_eteint.png",
				"img/PnlCreateGame/retour_allume.png");
		btnMultiReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.PreviousPanel(monController.getLaConsole().getPnlMultiCreateGame());
			}
		});
		this.add(btnMultiReturn);

	}

	// Méthode pour récupérer l'heure saisie
	public String getHeure() {
		Date date = model.getDate();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		return format.format(date);
	}
}
