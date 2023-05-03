package serverudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import static java.nio.ByteOrder.BIG_ENDIAN;
import java.util.Arrays;

/**
 *
 * @author bernardi.nicola
 */
public class Server {
    private int porta;
    private InetAddress ip;
    private DatagramSocket socket;

    public Server(int porta, InetAddress ip) throws SocketException {
        this.porta = porta;
        this.ip = ip;
        socket = new DatagramSocket(porta);      // ??
    }
    public Server() throws UnknownHostException, SocketException{
        this(50000, InetAddress.getLocalHost());
    }
    
    public void eseguiServer() throws IOException{
        byte[] invia;
        byte[] ricevi = new byte[1024];
        
        
        while(true){
            ByteBuffer bb = ByteBuffer.allocate(1024);
            bb.order(BIG_ENDIAN);
            DatagramPacket dpRicevuto = new DatagramPacket(ricevi, ricevi.length);
            socket.receive(dpRicevuto);
            bb.put(ricevi);
            bb.position(0);
            int lTot = 4+1 + bb.getInt() + bb.get();
            byte[] arr = new byte[lTot];
            for(int i=0; i<lTot; i++){
                arr[i] = ricevi[i];
            }
            bb = ByteBuffer.allocate(lTot);
            bb.order(BIG_ENDIAN);
            bb.put(arr);
            bb.position(0);
            int nIntervistati = bb.getInt();
            byte lunghezzaCitta = bb.get();
            System.out.println("Sono state intervistate "+nIntervistati+" persone");
            System.out.println("Mesi di nascita degli intervistati");
            for(int i=0; i<nIntervistati; i++){
                System.out.println(bb.get());
            }
            byte[] nC = new byte[lunghezzaCitta];
            bb.get(nC);
            String nomeCitta = new String(nC);
            System.out.println("Nome della città: "+nomeCitta+"\n");
            
            bb.position(5);
            int[] mesi = new int[12];     // mesi e occorrenze
            Arrays.fill(mesi, 0);
            for(int i=0; i<nIntervistati; i++){
                byte x = bb.get();
                mesi[x-1]+=1;
            }
            byte nOccorrenze=0;
            for(int i=0; i<mesi.length; i++){
                if(mesi[i]!=0)
                    nOccorrenze++;
            }
            int lTotale = 1+(5*nOccorrenze);
            bb = ByteBuffer.allocate(lTotale);
            bb.order(BIG_ENDIAN);
            bb.put(nOccorrenze);
            for(int i=0; i<mesi.length;i++){
                if(mesi[i]!=0){
                    bb.put((byte)i);
                    bb.putInt(mesi[i]);
                }
            }
        }
    }
    
    
}
