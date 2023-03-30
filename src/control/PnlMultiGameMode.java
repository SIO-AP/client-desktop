package control;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;

public class PnlMultiGameMode extends JPanel {

	private Controller monController;
	private Boolean createGame;


	public PnlMultiGameMode(Controller unController) {
		monController = unController;
		setOpaque(false);
		this.setBounds(10, 10, 678, 453);
		this.setLayout(null);

		JButton btnCreateGameMulti = new JButton("Créer un quiz");
		btnCreateGameMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createGame = true;
				monController.getLaConsole().NextPanel(monController.getLaConsole().getPnlMultiGameMode());
			}
		});
		btnCreateGameMulti.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCreateGameMulti.setBounds(130, 220, 160, 50);
		this.add(btnCreateGameMulti);

		JButton btnJoinGameMulti = new JButton("Rejoindre un quiz");
		btnJoinGameMulti.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnJoinGameMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createGame = false;
				monController.initLesGames();
			}
		});
		btnJoinGameMulti.setBounds(390, 220, 160, 50);
		this.add(btnJoinGameMulti);

		JButton btnMultiReturn = new JButton("Annuler");
		btnMultiReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.getLaConsole().PreviousPanel(monController.getLaConsole().getPnlMultiGameMode());
			}
		});
		btnMultiReturn.setBounds(267, 376, 103, 35);
		this.add(btnMultiReturn);
	}


	public Boolean getCreateGame() {
		return createGame;
	}


	public void setCreateGame(Boolean createGame) {
		this.createGame = createGame;
	}
	
	
	
}
