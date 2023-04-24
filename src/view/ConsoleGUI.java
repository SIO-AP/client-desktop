
package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import control.PnlDisplayQuiz;
import control.PnlEndQuiz;
import control.PnlGameMode;
import control.PnlLogin;
import control.PnlMultiCreateGame;
import control.PnlMultiGameMode;
import control.PnlMultiJoinGame;
import control.PnlResultAnswer;
import control.PnlSoloCreateGame;
import control.PnlWaitingRoom;
import controller.Controller;
import enpoints.Message;
import model.Game;
import model.Question;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class ConsoleGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Controller monController;

	private PnlLogin pnlLogin;
	private PnlGameMode pnlGameMode;
	private PnlSoloCreateGame pnlSoloCreateGame;
	private PnlMultiGameMode pnlMultiGameMode;
	private PnlDisplayQuiz pnlDisplayQuiz;
	private PnlResultAnswer pnlResultAnswer;
	private PnlEndQuiz pnlEndQuiz;
	private PnlMultiCreateGame pnlMultiCreateGame;
	private PnlWaitingRoom pnlWaitingRoom;
	private PnlMultiJoinGame pnlMultiJoinGame;

	private boolean reloadJoinGame = false;
	private boolean waitingScreen = false;
	private int pnlListPlayerChange = 1; // 1 : pnlDislpayQuiz / 2 : pnlResultAnswer / 3 : pnlEndQuiz

	public static int width = 1080;
	public static int height = 620;
	public static Rectangle rectangle = new Rectangle(0, 0, ConsoleGUI.width, ConsoleGUI.height);

	public ConsoleGUI(Controller unController) {
		// Appelle le constructeur de la classe mère
		super();

		monController = unController;

		setIconImage(
				Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("img/vinci_ico.jpg")));
		setTitle("The Legend of Vinci Quiz");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(width, height);

		setBackground("img/PnlLogin/back.png");

		getContentPane().setLayout(null);

		pnlLogin = new PnlLogin(monController);
		getContentPane().add(pnlLogin);

		// Ajout d'un WindowListener pour écouter l'événement windowClosing
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				int option = JOptionPane.showOptionDialog(monController.getLaConsole(),
						"Voulez-vous vraiment fermer l'application ?", "Confirmation", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new Object[] { "Oui", "Non" }, "Non");

				if (option == JOptionPane.YES_OPTION) {
					if (monController.isPlaying()) { // En partie
						if (monController.isMulti()) { // En partie multijoueur
							System.out.println("en game multi");
							monController.getLeClient().getClient().sendTCP(new Message(6,
									monController.getLaGame().getIdGame(), monController.getMonPlayer()));

						} else { // En partie solo
							System.out.println("en game solo");
							monController.getLaBase().finishedSoloPlayerGame(monController.getLaGame());

						}
					} else { // Pas en partie
						System.out.println("pas en game");
					}

					// Ferme la fenêtre
					monController.getLaConsole().dispose();
				}
			}
		});

	}

	public void setBackground(String path) {
		try {
			BufferedImage myImage = ImageIO.read(getClass().getClassLoader().getResource(path));
			Image resizedImage = myImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			ImagePanel imagePanel = new ImagePanel(resizedImage);
			imagePanel.setPreferredSize(new Dimension(width, height)); // définit la taille préférée de l'image
			setContentPane(imagePanel);
			pack(); // ajuste la taille de la frame en fonction de la taille préférée de l'image
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PnlWaitingRoom getPnlWaitingRoom() {
		return pnlWaitingRoom;
	}

	public void setPnlWaitingRoom(PnlWaitingRoom pnlWaitingRoom) {
		this.pnlWaitingRoom = pnlWaitingRoom;
	}

	public PnlSoloCreateGame getPnlSoloCreateGame() {
		return pnlSoloCreateGame;
	}

	public void setPnlSoloCreateGame(PnlSoloCreateGame pnlSoloCreateGame) {
		this.pnlSoloCreateGame = pnlSoloCreateGame;
	}

	public PnlGameMode getPnlGameMode() {
		return pnlGameMode;
	}

	public void setPnlGameMode(PnlGameMode pnlGameMode) {
		this.pnlGameMode = pnlGameMode;
	}

	public PnlMultiGameMode getPnlMultiGameMode() {
		return pnlMultiGameMode;
	}

	public void setPnlMultiGameMode(PnlMultiGameMode pnlMultiGameMode) {
		this.pnlMultiGameMode = pnlMultiGameMode;
	}

	public PnlDisplayQuiz getPnlDisplayQuiz() {
		return pnlDisplayQuiz;
	}

	public void setPnlDisplayQuiz(PnlDisplayQuiz pnlDisplayQuiz) {
		this.pnlDisplayQuiz = pnlDisplayQuiz;
	}

	public PnlResultAnswer getPnlResultAnswer() {
		return pnlResultAnswer;
	}

	public void setPnlResultAnswer(PnlResultAnswer pnlResultAnswer) {
		this.pnlResultAnswer = pnlResultAnswer;
	}

	public PnlEndQuiz getPnlEndQuiz() {
		return pnlEndQuiz;
	}

	public void setPnlEndQuiz(PnlEndQuiz pnlEndQuiz) {
		this.pnlEndQuiz = pnlEndQuiz;
	}

	public PnlMultiCreateGame getPnlMultiCreateGame() {
		return pnlMultiCreateGame;
	}

	public void setPnlMultiCreateGame(PnlMultiCreateGame pnlMultiCreateGame) {
		this.pnlMultiCreateGame = pnlMultiCreateGame;
	}

	public PnlLogin getPnlLogin() {
		return pnlLogin;
	}

	public void setPnlLogin(PnlLogin pnlLogin) {
		this.pnlLogin = pnlLogin;
	}

	public PnlMultiJoinGame getPnlMultiJoinGame() {
		return pnlMultiJoinGame;
	}

	public void setPnlMultiJoinGame(PnlMultiJoinGame pnlMultiJoinGame) {
		this.pnlMultiJoinGame = pnlMultiJoinGame;
	}

	public boolean isReloadJoinGame() {
		return reloadJoinGame;
	}

	public void setReloadJoinGame(boolean reloadJoinGame) {
		this.reloadJoinGame = reloadJoinGame;
	}

	public boolean isWaitingScreen() {
		return waitingScreen;
	}

	public void setWaitingScreen(boolean waitingScreen) {
		this.waitingScreen = waitingScreen;
	}

	public int getPnlListPlayerChange() {
		return pnlListPlayerChange;
	}

	public void setPnlListPlayerChange(int pnlListPlayerChange) {
		this.pnlListPlayerChange = pnlListPlayerChange;
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