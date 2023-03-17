package control;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.Controller;
import view.ConsoleGUI;

public class PnlGameMode extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblErrorStartQuiz;
	private Controller monController;

	public PnlGameMode(Controller unController) {

		monController = unController;
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 5));
		lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIgnoreRepaint(true);
		lblNewLabel.setIconTextGap(0);
		lblNewLabel.setEnabled(false);
		lblNewLabel.setIcon(new ImageIcon(ConsoleGUI.class.getResource("/img/background.png")));
		lblNewLabel.setBounds(0, 0, 698, 473);
		add(lblNewLabel);
		
		
		setOpaque(true);
		
		this.setBounds(10, 10, 678, 453);
		this.setLayout(null);

		JButton btnSoloPlayer = new JButton("Quiz Solo");
		btnSoloPlayer.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSoloPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.getLaConsole().setMulti(false);
				monController.getLaConsole().NextPanel(monController.getLaConsole().getPnlGameMode());
			}
		});

		btnSoloPlayer.setBounds(130, 220, 160, 50);
		this.add(btnSoloPlayer);
		
		JButton btnMultiPlayer = new JButton("Quiz Multijoueurs");
		btnMultiPlayer.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnMultiPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.getLaConsole().setMulti(true);
				monController.getLaConsole().NextPanel(monController.getLaConsole().getPnlGameMode());
			}
		});
		btnMultiPlayer.setBounds(390, 220, 160, 50);
		this.add(btnMultiPlayer);

		JLabel lblTitreGameMode = new JLabel("Bienvenue dans le Quiz Game Vinci");
		lblTitreGameMode.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitreGameMode.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitreGameMode.setBounds(10, 68, 658, 34);
		this.add(lblTitreGameMode);

		lblErrorStartQuiz = new JLabel("");
		lblErrorStartQuiz.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorStartQuiz.setBounds(0, 394, 678, 13);
		this.add(lblErrorStartQuiz);
		lblErrorStartQuiz.setForeground(Color.RED);

	}

	public JLabel getLblErrorStartQuiz() {
		return lblErrorStartQuiz;
	}

	public void setLblErrorStartQuiz(JLabel lblErrorStartQuiz) {
		this.lblErrorStartQuiz = lblErrorStartQuiz;
	}

	

	
}
