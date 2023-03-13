package data;

import java.io.IOException;
import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import controller.Controller;
import enpoints.Message;
import model.Answer;
import model.LesParty;
import model.Party;
import model.Player;
import model.Question;
import model.QuizGame;

public class ClientWebsocket {

	private Client client = new Client(100000, 100000);
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
		kryo.register(Party.class);
		kryo.register(LesParty.class);

		client.start();

		client.connect(500000, "127.0.0.1", 54556, 54776);

		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				
				if (object instanceof Party) {
					Party game = (Party) object;
					monController.setLaParty(game);
					if (monController.getLaConsole().isCreateGameMulti()) {
						monController.getLaConsole().NextPanel(monController.getLaConsole().getPnlMultiCreateGame());
					} else {
						monController.getLaConsole().NextPanel(monController.getLaConsole().getPnlMultiJoinGame());
					}
				}

				if (object instanceof LesParty) {
					LesParty lesParty = (LesParty) object;
					monController.setLesParty(lesParty);
					if (monController.getLaConsole().isReloadJoinGame()) {
						monController.getLaConsole().NextPanel(monController.getLaConsole().getPnlMultiJoinGame());
					} else {
						monController.getLaConsole().NextPanel(monController.getLaConsole().getPnlMultiGameMode());
					}
				}

				if (object instanceof Message) {
					Message message = (Message) object;
					monController.selectOption(message);
				}
			}
		});
	}

	public void createGame() {
		client.sendTCP(monController.getLaParty());
	}

	public void searchGame() {
		ArrayList<Party> lesParty = new ArrayList<Party>();
		lesParty.add(new Party());
		client.sendTCP(new LesParty(lesParty));
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
