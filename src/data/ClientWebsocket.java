package data;

import java.io.IOException;

import java.sql.Connection;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;

public class ClientWebsocket {
	Client client = new Client();
	
	public ClientWebsocket() {
		Kryo kryo = client.getKryo();
		kryo.register(SomeRequest.class);
		kryo.register(SomeResponse.class);
		client.start();
		try {
			client.connect(50000, "127.0.0.1", 54555, 54777);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		client.addListener(new Listener() {
        public void received (Connection connection, Object object) {
        	if (object instanceof SomeResponse) {       
        		SomeResponse response = (SomeResponse)object;
        		System.out.println(response.text);
            }
        }
    });
    SomeRequest request = new SomeRequest();
    request.text = "Here is the request!";
    client.sendTCP(request);

}

}
