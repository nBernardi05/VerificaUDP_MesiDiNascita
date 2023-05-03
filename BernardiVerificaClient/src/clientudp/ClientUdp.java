package clientudp;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author bernardi.nicola
 */
public class ClientUdp {
    public static void main(String[] args) throws UnknownHostException, SocketException, IOException {
            Client client = new Client();
            client.eseguiClient();
            client.chiudiSocket();
        
    }
}
