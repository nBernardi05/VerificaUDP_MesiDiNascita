package serverudp;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author bernardi.nicola
 */
public class ServerUdp {
    public static void main(String[] args) throws UnknownHostException, SocketException, IOException {
        Server server = new Server();
        server.eseguiServer();
    }
}
