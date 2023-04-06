package control;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
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
		
		monController.getLaConsole().setBackground("img/PnlGameMode/back.png");
		
		setOpaque(false);
		
		this.setBounds(ConsoleGUI.rectangle);
		this.setLayout(null);
		
		ButtonDisplay btnSoloPlayer = new ButtonDisplay(500, 100, 500, 50, "img/PnlGameMode/solo_eteint.png", "img/PnlGameMode/solo_allume.png");
		btnSoloPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.getLaConsole().setMulti(false);
				monController.NextPanel(monController.getLaConsole().getPnlGameMode());
			}
		});
		this.add(btnSoloPlayer);
		
		JButton btnMultiPlayer = new ButtonDisplay(500, 300, 500, 50, "img/PnlGameMode/multijoueur_eteint.png", "img/PnlGameMode/multijoueur_allume.png");
		btnMultiPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.getLaConsole().setMulti(true);
				monController.NextPanel(monController.getLaConsole().getPnlGameMode());
			}
		});
		this.add(btnMultiPlayer);
		
		
		ButtonDisplay btnLeave = new ButtonDisplay(700, 500, 250, 50, "img/PnlGameMode/quitter_eteint.png", "img/PnlGameMode/quitter_allume.png");
		btnLeave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monController.getLaConsole().dispose();
			}
		});
		this.add(btnLeave);
		

//		JLabel lblTitreGameMode = new JLabel("Bienvenue dans le Quiz Game Vinci");
//		lblTitreGameMode.setFont(new Font("Tahoma", Font.PLAIN, 20));
//		lblTitreGameMode.setHorizontalAlignment(SwingConstants.CENTER);
//		lblTitreGameMode.setBounds(10, 68, 658, 34);
//		this.add(lblTitreGameMode);

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


class ImagePanel extends JComponent {
	private Image image;

	public ImagePanel(Image image) {
		this.image = image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}
}