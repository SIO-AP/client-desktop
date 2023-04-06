package control;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.Controller;
import view.ConsoleGUI;

public class PnlLogin extends JPanel {
	private Controller monController;

	private JTextField txtName;
	private JPasswordField passwordField;
	private JLabel lblConnectionFasle;

	public PnlLogin(Controller unController) {
		monController = unController;

		setOpaque(false);

		this.setBounds(ConsoleGUI.rectangle);
		this.setLayout(null);

		txtName = new JTextField();
		txtName.setBounds(30, 189, 310, 85);
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txtName.setOpaque(false);
		txtName.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		this.add(txtName);
		txtName.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(30, 328, 310, 85);
		passwordField.setBorder(null);
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		passwordField.setOpaque(false);
		passwordField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		this.add(passwordField);
		passwordField.setColumns(10);

		ButtonDisplay btnLogin = new ButtonDisplay(50, 450, 250, 50, "img/PnlLogin/connexion_eteint.png", "img/PnlLogin/connexion_allume.png");
		this.add(btnLogin);
		
		lblConnectionFasle = new JLabel("Nom ou Mot de passe incorecte !");
		lblConnectionFasle.setForeground(Color.RED);
		lblConnectionFasle.setHorizontalAlignment(SwingConstants.CENTER);
		lblConnectionFasle.setVisible(false);
		lblConnectionFasle.setBounds(10, 260, 658, 19);
		this.add(lblConnectionFasle);

		ButtonDisplay btnCancel = new ButtonDisplay(50, 530, 250, 50, "img/PnlLogin/quitter_eteint.png", "img/PnlLogin/quitter_allume.png");
		this.add(btnCancel);

		// Lance la vérification du mot de passe lors du clique sur le bouton de
		// connexion
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});

		// Lance la vérification du mot de passe lors du clique sur Entrer dans le champ
		// de saisi du mot de passe
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					login();
				}
			}
		});

		// Ferme la fenêtre lors du clique sur le bouton Cancel
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.getLaConsole().dispose();
			}
		});

	}

	private void login() {
		try {
			if (monController.verification(txtName.getText(), String.valueOf(passwordField.getPassword()))) {
				monController.NextPanel(monController.getLaConsole().getPnlLogin());
			} else {
				lblConnectionFasle.setVisible(true);
				passwordField.setText("");
				// passwordField.focus
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
