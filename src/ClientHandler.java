import java.io.*;
import java.net.Socket;


public class ClientHandler extends Thread {

    // watek obslugujacy konkretnego klienta
    // bedzie obslugiwal metode move() oraz rozdawal punkty

    Socket socket;
    BufferedReader in;
    PrintWriter out;
    String nazwa;
    String symbol;
    Server server;



    public ClientHandler(Socket socket, String symbol, Server server) throws IOException {
        this.socket = socket;
        this.symbol = symbol;
        this.server = server;
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        out = new PrintWriter( new BufferedWriter(
                new OutputStreamWriter(
                        socket.getOutputStream())), true);

    }

    public void move(int position) {
        if (!Server.currentTurn.equals(symbol)) {
            out.println("NOT_YOUR_TURN");
            return;
        }
        if (server.board[position].isEmpty()) {
            server.board[position] = symbol;
            Server.broadcast("MOVE;" + position + ";" + symbol);

            Server.currentTurn = symbol.equals("X") ? "O" : "X";

            String winner = server.checkWinner();
            if (winner != null) {
                Server.broadcast("WIN;" + winner);
                Server.resetGame();
            } else if (server.isDraw()) {
                Server.broadcast("DRAW"); // remis
                Server.resetGame();
            }
        }
    }

    public void run() {
        try {
            // out.println("Podaj nazwę gracza: ");
            nazwa = in.readLine();

            System.out.println("Connected: " + nazwa);

            if (Server.clients.size() == 2) {
                String player1 = Server.clients.get(0).nazwa;
                String player2 = Server.clients.get(1).nazwa;
                Server.broadcast("PLAYERS;" + player1 + ";" + player2);
            }

            while (true) {
                String msg = in.readLine();

                if (msg == null) {
                    break;
                }
                if (msg.startsWith("MOVE;")) {
                    int position = Integer.parseInt(msg.split(";")[1]);
                    move(position);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
