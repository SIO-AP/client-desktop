package control;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;
import view.ConsoleGUI;

public class PnlMultiGameMode extends JPanel {

	private Controller monController;
	private Boolean createGame;


	public PnlMultiGameMode(Controller unController) {
		monController = unController;

		monController.getLaConsole().setBackground("img/PnlMultiGameMode/back.png");

		setOpaque(false);

		this.setBounds(ConsoleGUI.rectangle);
		this.setLayout(null);

		ButtonDisplay btnCreateGameMulti = new ButtonDisplay(500, 100, 500, 50,
				"img/PnlMultiGameMode/creer_quiz_eteint.png", "img/PnlMultiGameMode/creer_quiz_allume.png");
		btnCreateGameMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createGame = true;
				monController.NextPanel(monController.getLaConsole().getPnlMultiGameMode());
			}
		});
		this.add(btnCreateGameMulti);

		JButton btnJoinGameMulti = new ButtonDisplay(500, 300, 500, 50,
				"img/PnlMultiGameMode/rejoindre_quiz_eteint.png", "img/PnlMultiGameMode/rejoindre_quiz_allume.png");
		btnJoinGameMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createGame = false;
				monController.initLesGames();
			}
		});
		this.add(btnJoinGameMulti);

		JButton btnMultiReturn = new ButtonDisplay(700, 500, 250, 50,
				"img/PnlMultiGameMode/retour_eteint.png", "img/PnlMultiGameMode/retour_allume.png");
		btnMultiReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.PreviousPanel(monController.getLaConsole().getPnlMultiGameMode());
			}
		});
		this.add(btnMultiReturn);
	}


	public Boolean isCreateGame() {
		return createGame;
	}


	public void setCreateGame(Boolean createGame) {
		this.createGame = createGame;
	}
	
	
	
}
