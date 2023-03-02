package data;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;

import controller.Controller;
import enpoints.Message;
import model.Answer;
import model.Player;
import model.Question;
import model.QuizGame;

public class ClientWebsocket {
	Client client = new Client(1000000, 1000000);

	public ClientWebsocket(Controller monController) {
		Kryo kryo = client.getKryo();
		kryo.register(SomeRequest.class);
		kryo.register(SomeResponse.class);
		kryo.register(Message.class);
	    kryo.register(ArrayList.class);
	    kryo.register(QuizGame.class);
	    kryo.register(Answer.class);
	    kryo.register(Player.class);
	    kryo.register(Question.class);
	//    kryo.register(Controller.class);
	 
	    
		client.start();
		try {
			client.connect(50000, "127.0.0.1", 54556, 54776);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof SomeResponse) {
					SomeResponse response = (SomeResponse) object;
					System.out.println(response.text);
				}
			}
		});
		
	QuizGame game =	new QuizGame(
				new Player(monController.getLaGame().getMyPlayer().getMyName(), 
						monController.getLaGame().getMyPlayer().getMyScore())
				, monController.getLaGame().getQuestions());
		
		client.sendTCP(game);

	}

}
