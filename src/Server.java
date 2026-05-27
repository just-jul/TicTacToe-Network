import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    // listening for incoming connections or clients and communicating w them
    Socket clientSocket = null;
    ServerSocket serverSocket = null;
    PrintWriter out;
    int port = 12345;
    String serverAdress = "127.0.0.1";
    Client client;


    public Server(ServerSocket serverSocket) {
        try{
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Can't initialize server.");
            System.exit(1);
        }
    }
    public void startServer(){
        if(serverSocket == null) return;

        try{
            System.out.println("Waiting on client...");

            while(!serverSocket.isClosed()){
                clientSocket = serverSocket.accept();
                System.out.println("A new client has connected");

                new ClientHandler(clientSocket).start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void closeServerSocket(){
        try{
            if(serverSocket != null){
                serverSocket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
