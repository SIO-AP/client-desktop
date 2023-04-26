package data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JOptionPane;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import control.PnlGameMode;
import controller.Controller;
import enpoints.Message;
import model.Answer;
import model.LesGame;
import model.Game;
import model.Player;
import model.Question;
import model.QuizGame;

public class ClientWebsocket {

	private Client client = new Client(10000000, 10000000);
	private Controller monController;

	public ClientWebsocket(Controller unController) throws IOException {
		monController = unController;

		Kryo kryo = client.getKryo();
		kryo.register(SomeRequest.class);
		kryo.register(Message.class);
		kryo.register(ArrayList.class);
		kryo.register(QuizGame.class);
		kryo.register(Answer.class);
		kryo.register(Player.class);
		kryo.register(Question.class);
		kryo.register(Game.class);
		kryo.register(LesGame.class);

		client.start();

		// Charge le fichier de propriété contenant les informations d'accès à la BDD
		Properties properties = new Properties();
		try (InputStream fis = getClass().getClassLoader().getResourceAsStream("data/conf.properties")) {
			properties.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}

		client.connect(500000, monController.getTheDecrypter().decrypt(properties.getProperty("websocket.ip")),
				Integer.parseInt(monController.getTheDecrypter().decrypt(properties.getProperty("websocket.port"))));

		client.addListener(new Listener() {

			public void disconnected(Connection connection) {
				if (monController.isMulti()) { // problème connexion
					errorConnection();
				}
			}

			public void received(Connection connection, Object object) {

				if (object instanceof Game) {
					Game game = (Game) object;
					monController.setLaGame(game);
					if (monController.isCreateGameMulti()) {
						monController.NextPanel(monController.getLaConsole().getPnlMultiCreateGame());
					} else {
						monController.NextPanel(monController.getLaConsole().getPnlMultiJoinGame());
					}
				}

				if (object instanceof LesGame) {
					LesGame lesParty = (LesGame) object;
					monController.setLesGames(lesParty);
					if (monController.getLaConsole().isReloadJoinGame()) {
						monController.NextPanel(monController.getLaConsole().getPnlMultiJoinGame());
					} else {
						monController.NextPanel(monController.getLaConsole().getPnlMultiGameMode());
					}
				}

				if (object instanceof Message) {
					Message message = (Message) object;
					monController.selectOption(message);
				}
			}
		});
	}

	private void errorConnection() {
		JOptionPane.showMessageDialog(monController.getLaConsole(),
				"Un problème de connexion au serveur est survenue.\r\nVeuillez réessayer.", "Erreur de connexion",
				JOptionPane.ERROR_MESSAGE);

		if (monController.getLaConsole().getPnlMultiGameMode() != null) {
			monController.getLaConsole().getPnlMultiGameMode().setVisible(false);
			monController.getLaConsole().getContentPane().remove(monController.getLaConsole().getPnlMultiGameMode());
			monController.getLaConsole().setPnlMultiGameMode(null);
		} else if (monController.getLaConsole().getPnlMultiCreateGame() != null) {
			monController.getLaConsole().getPnlMultiCreateGame().setVisible(false);
			monController.getLaConsole().remove(monController.getLaConsole().getPnlMultiCreateGame());
			monController.getLaConsole().setPnlMultiCreateGame(null);
		} else if (monController.getLaConsole().getPnlMultiJoinGame() != null) {
			monController.getLaConsole().getPnlMultiJoinGame().setVisible(false);
			monController.getLaConsole().remove(monController.getLaConsole().getPnlMultiJoinGame());
			monController.getLaConsole().setPnlMultiJoinGame(null);
		} else if (monController.getLaConsole().getPnlWaitingRoom() != null) {
			monController.getLaConsole().getPnlWaitingRoom().setVisible(false);
			monController.getLaConsole().getContentPane().remove(monController.getLaConsole().getPnlWaitingRoom());
			monController.getLaConsole().setPnlWaitingRoom(null);
		} else if (monController.getLaConsole().getPnlDisplayQuiz() != null) {
			monController.getLaConsole().getPnlDisplayQuiz().setVisible(false);
			monController.getLaConsole().getContentPane().remove(monController.getLaConsole().getPnlDisplayQuiz());
			monController.getLaConsole().setPnlDisplayQuiz(null);
		} else if (monController.getLaConsole().getPnlResultAnswer() != null) {
			monController.getLaConsole().getPnlResultAnswer().setVisible(false);
			monController.getLaConsole().getContentPane().remove(monController.getLaConsole().getPnlResultAnswer());
			monController.getLaConsole().setPnlResultAnswer(null);
		} else if (monController.getLaConsole().getPnlEndQuiz() != null) {
			monController.getLaConsole().getPnlEndQuiz().setVisible(false);
			monController.getLaConsole().getContentPane().remove(monController.getLaConsole().getPnlEndQuiz());
			monController.getLaConsole().setPnlEndQuiz(null);
		}

		monController.setMulti(false);
		monController.resetParam();

		monController.getLaConsole().setPnlGameMode(new PnlGameMode(monController));
		monController.getLaConsole().getContentPane().add(monController.getLaConsole().getPnlGameMode());
		monController.getLaConsole().getPnlGameMode().repaint();
		System.out.println(monController.isMulti());

	}

	public void createGame() {
		client.sendTCP(monController.getLaGame());
	}

	public void searchGame() {
		ArrayList<Game> lesGames = new ArrayList<Game>();
		lesGames.add(new Game());
		client.sendTCP(new LesGame(lesGames));
	}

	public void joinGame(int idGame) {
		client.sendTCP(new Message(1, idGame, monController.getMonPlayer()));
	}

	public void launchGame(int idGame) {
		client.sendTCP(new Message(2, idGame));
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
