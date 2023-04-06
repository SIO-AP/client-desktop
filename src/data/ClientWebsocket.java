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

		client.connect(5000, "127.0.0.1", 54556);

		client.addListener(new Listener() {
			
			public void disconnected(Connection connection) {
		        System.out.println("déco");	 
		    }
			
			
			public void received(Connection connection, Object object) {
				
				if (object instanceof Game) {
					Game game = (Game) object;
					monController.setLaGame(game);
					if (monController.getLaConsole().isCreateGameMulti()) {
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
