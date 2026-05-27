import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientHandler extends Thread {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;


    public ClientHandler(Socket socket){
        this.socket = socket;
    }

}
