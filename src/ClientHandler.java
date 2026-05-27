import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientHandler extends Thread {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    String nazwa;
    String symbol;


    public ClientHandler(Socket socket, String symbol){
        this.socket = socket;
        this.symbol = symbol;
    }

    public void run(){
        try{
            out.println("Podaj nazwe: -> ");
            nazwa = in.readLine();

            Client client = new Client(nazwa, symbol);
        }
    }

}
