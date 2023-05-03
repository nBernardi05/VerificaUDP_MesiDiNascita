package clientudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import static java.nio.ByteOrder.BIG_ENDIAN;
import java.util.Scanner;

/**
 *
 * @author bernardi.nicola
 */
public class Client {
    private int porta;
    private InetAddress ip;
    private DatagramSocket socket;

    public Client(int porta, InetAddress ip) throws SocketException {
        if(porta!=0){
            socket = new DatagramSocket(porta);
        }else{
            socket = new DatagramSocket();
        }
        this.ip = ip;
        this.porta = socket.getPort();      // ???
    }
    public Client() throws UnknownHostException, SocketException{
        this(0, InetAddress.getLocalHost());
    }
    
    public void eseguiClient() throws UnknownHostException, IOException{
        byte[] invia;
        byte[] ricevi;
        Scanner sc = new Scanner(System.in);
        
        int n=-1;       // numero intervistati
        String nomeCitta;
        int lTot =0;
        
        while(n<1||n>350){
            System.out.println("Inserisci il numero degli intervistati (tra 1 e 350)");
            n = sc.nextInt();
            sc.nextLine();
        }
        lTot+=n+4;
        System.out.println("Inserisci il nome della città in cui è avvenuta l'intervista");
        nomeCitta = sc.nextLine();
        lTot+=nomeCitta.length()+1;
        ByteBuffer bb = ByteBuffer.allocate(lTot);
        bb.order(BIG_ENDIAN);
        bb.putInt(n);
        bb.put((byte)nomeCitta.length());
        
        for(int i=0; i<n; i++){
            byte mese=0;
            while(mese<1||mese>12){
                System.out.println("Inserisci il numero del mese di nascita dell'intervistato numero "+(i+1));
                mese = sc.nextByte();
            }
            bb.put(mese);
        }
        bb.put(nomeCitta.getBytes());
        invia = bb.array();
        
        DatagramPacket dpInviato = new DatagramPacket(invia, invia.length, InetAddress.getLocalHost(), 50000);
        socket.send(dpInviato);
        
    }
    
    public void chiudiSocket(){
        socket.close();
    }
    
    
}
