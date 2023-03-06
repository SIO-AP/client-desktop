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

	Client client = new Client(100000, 100000);
	private Controller monController;

	public ClientWebsocket(Controller unController) {
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
		// kryo.register(Controller.class);

		client.start();
		try {
			client.connect(500000, "127.0.0.1", 54551, 54771);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof Party) {
					Party response = (Party) object;
					System.out.println("Nouvelle partie");
					System.out.println("Nom : " + response.getName());
					System.out.println("Nombre de question : " + response.getNbQuestion());
					monController.setLaParty(response);
					monController.getLaConsole().NextPanel(monController.getLaConsole().getPnlMultiCreateGame());
				}

				if (object instanceof LesParty) {
					LesParty lesParty = (LesParty) object;
				}
			}
		});
	}

	public void test() {
		client.sendTCP(monController.getLaParty());

	}

	public void test1() {
		
		client.sendTCP(new LesParty());
	}

}
