import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    // listening for incoming connections or clients and communicating w them

    // here we put port number, client socket, server socket,

    Socket clientSocket = null;
    ServerSocket serverSocket = null;
    int port = 12345;
    int clientCounter;
    static String[] board = new String[9];
    static String currentTurn = "X";

    static ArrayList<ClientHandler> clients = new ArrayList<>();

    public Server() {
        java.util.Arrays.fill(board, "");
        try {
            this.serverSocket = new ServerSocket(port);
            clientCounter = 0;
        } catch (IOException e) {
            System.out.println("Can't initialize server: " + e.getMessage());
            System.exit(1);
        }
    }
    public static void main(String[] args) {
        new Server().startServer();
    }
    public void startServer() {
        if (serverSocket == null) return;

        try {
            System.out.println("Waiting on clients...");

            while(!serverSocket.isClosed() && clientCounter < 2){
                clientSocket = serverSocket.accept();
                System.out.println("A new client has connected");

                String symbol = (clientCounter == 0) ? "X" : "O";

                ClientHandler ch = new ClientHandler(clientSocket, symbol, this);
                clients.add(ch);
                // uruchamiamy obslugę klienta
                ch.start();
                clientCounter++;

                // zamykamy serverSocket po 2 graczu
                if(clientCounter == 2) {
                    serverSocket.close();
                }
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void closeServerSocket() {
        try {
            if(serverSocket != null){
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String checkWinner() {
        for (int a = 0; a < 8; a++) {
            String line = null;

            switch (a) {
                case 0:
                    line = board[0] + board[1] + board[2];
                    break;
                case 1:
                    line = board[3] + board[4] + board[5];
                    break;
                case 2:
                    line = board[6] + board[7] + board[8];
                    break;
                case 3:
                    line = board[0] + board[3] + board[6];
                    break;
                case 4:
                    line = board[1] + board[4] + board[7];
                    break;
                case 5:
                    line = board[2] + board[5] + board[8];
                    break;
                case 6:
                    line = board[0] + board[4] + board[8];
                    break;
                case 7:
                    line = board[2] + board[4] + board[6];
                    break;
            }
            // For X winner
            if (line.equals("XXX")) {
                return "X";
            }

            // For O winner
            else if (line.equals("OOO")) {
                return "O";
            }

        }
        return null;
    }

    public boolean isDraw() {
        for (String cell : board) {
            if (cell.isEmpty()) return false;
        }
        return true;
    }

    public static void resetGame() {
        for (int i = 0; i < 9; i++) board[i] = "";
        currentTurn = "X";
        broadcast("RESET");
    }


    // boradcasting message when player makes a move
    public static synchronized void broadcast(String message) {
        for(ClientHandler ch : clients) {
            ch.out.println(message);
        }
    }
}
