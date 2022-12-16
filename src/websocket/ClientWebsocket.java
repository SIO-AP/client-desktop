package websocket;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import model.Party;

public class ClientWebsocket {
	Client client = new Client();
	public ClientWebsocket() {
	    Kryo kryo = client.getKryo();
	    kryo.register(Message.class);
	    kryo.register(Party.class);
	    
	    new Thread(client).start();

	    try {
	        client.connect(50000, "127.0.0.1", 54556, 54776);
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }

	    client.addListener(new Listener() {
	    	public void received(Connection connection, Object object) {
	        	if (object instanceof Message) {
	        		Message response = (Message)object;
	        		System.out.println(response.text);
	            }
	         }
	    });
	}
	
	 public void createParty(Party aParty) {
 		Message response = new Message();
 		response.text = "test";
 		client.sendTCP(response.text);

		    //client.sendTCP(aParty);
	}

}