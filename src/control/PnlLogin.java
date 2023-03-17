package control;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.Controller;

public class PnlLogin extends JPanel {
	private Controller monController;

	private JTextField txtName;
	private JPasswordField passwordField;
	private JLabel lblConnectionFasle;

	public PnlLogin(Controller unController) {
		monController = unController;
		
		setOpaque(false);
		
		this.setLayout(null);
		this.setBounds(10, 10, 678, 453);

		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(203, 190, 85, 19);
		this.add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.setBounds(340, 184, 153, 33);
		this.add(passwordField);
		passwordField.setColumns(10);

		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(377, 289, 116, 33);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		this.add(btnLogin);

		JLabel lblName = new JLabel("Name :");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblName.setBounds(203, 139, 85, 19);
		this.add(lblName);

		txtName = new JTextField();
		txtName.setBounds(340, 130, 153, 33);
		this.add(txtName);
		txtName.setColumns(10);

		lblConnectionFasle = new JLabel("Nom ou Mot de passe incorecte !");
		lblConnectionFasle.setForeground(Color.RED);
		lblConnectionFasle.setHorizontalAlignment(SwingConstants.CENTER);
		lblConnectionFasle.setVisible(false);
		lblConnectionFasle.setBounds(10, 260, 658, 19);
		this.add(lblConnectionFasle);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(209, 289, 116, 33);
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 13));
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
				monController.getLaConsole().NextPanel(monController.getLaConsole().getPnlLogin());
			}
			else {
				lblConnectionFasle.setVisible(true);
				passwordField.setText("");
			//	passwordField.focus
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
