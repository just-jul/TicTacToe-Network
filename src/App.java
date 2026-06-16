import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;


public class App extends JFrame implements ActionListener {

    // tutaj jest czesc graficzna

    private JLabel title = new JLabel("Tic Tac Toe");
    private JPanel titlePanel = new JPanel();
    private JPanel gridPanel = new JPanel();
    private JButton startBtn = new JButton("Start");
    private JPanel startBtnPanel = new JPanel();

    private JPanel scorePanel1 = new JPanel();
    private JLabel scoreTitle1 = new JLabel("Player 1", SwingConstants.CENTER);
    private JLabel scoreDisplay1 = new JLabel("0:0", SwingConstants.CENTER);
    private JLabel winsDisplay1 = new JLabel("Wins: ", SwingConstants.CENTER);

    private JPanel scorePanel2 = new JPanel();
    private JLabel scoreTitle2 = new JLabel("Player 2", SwingConstants.CENTER);
    private JLabel scoreDisplay2 = new JLabel("0:0", SwingConstants.CENTER);
    private JLabel winsDisplay2 = new JLabel("Wins: ", SwingConstants.CENTER);
    static Font globalFont = new Font("Helvetica", Font.PLAIN, 16);

    private String[] board = Server.board;
    private JButton[] buttons = new JButton[9];

    private Client playerX;
    private Client playerY;
    private Client currentPlayer;
    private String playerName;
    private boolean isHost;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;


    public static Server server = null;

    public static void main(String[] args) {

        UIManager.put("Button.font", new FontUIResource(globalFont));
        UIManager.put("Label.font", new FontUIResource(globalFont));
        UIManager.put("TextField.font", new FontUIResource(globalFont));
        UIManager.put("TextArea.font", new FontUIResource(globalFont));
        UIManager.put("ComboBox.font", new FontUIResource(globalFont));
        UIManager.put("Panel.font", new FontUIResource(globalFont));
        UIManager.put("OptionPane.messageFont", new FontUIResource(globalFont));
        UIManager.put("OptionPane.buttonFont", new FontUIResource(globalFont));

        App okno = new App("Tic Tac Toe");
        okno.init();
        okno.setVisible(true);

    }

    App(String tytul) {
        super(tytul);
    }

    void init() {
        setSize(1000, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        title.setFont(new Font("Helvetica", Font.BOLD, 32));
        titlePanel.setPreferredSize(new Dimension(1000, 100));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(title);
        titlePanel.setLayout(new GridBagLayout());

        // Panel wyników dla Gracza 1
        scorePanel1.setLayout(new BorderLayout());
        scorePanel1.setPreferredSize(new Dimension(160, 200));
        scorePanel1.setBackground(Color.WHITE);
        scorePanel1.add(scoreTitle1, BorderLayout.NORTH);
        scorePanel1.add(scoreDisplay1, BorderLayout.CENTER);
        scorePanel1.add(winsDisplay1, BorderLayout.SOUTH);

        // Panel wyników dla Gracza 2
        scorePanel2.setLayout(new BorderLayout());
        scorePanel2.setPreferredSize(new Dimension(160, 200));
        scorePanel2.add(scoreTitle2, BorderLayout.NORTH);
        scorePanel2.add(scoreDisplay2, BorderLayout.CENTER);
        scorePanel2.setBackground(Color.WHITE);
        scorePanel2.add(winsDisplay2, BorderLayout.SOUTH);



        JPanel gridContainer = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // for spacings in between elements
        gbc.insets = new Insets(5,10,5,10);

        gbc.gridx = 0;
        gridContainer.add(scorePanel1, gbc);
        gbc.gridx = 1;
        gridContainer.add(gridPanel, gbc);
        gbc.gridx = 2;
        gridContainer.add(scorePanel2, gbc);

        gridPanel.setPreferredSize(new Dimension(600, 600));
        gridPanel.setLayout(new GridLayout(3, 3));
        gridPanel.setBackground(Color.WHITE);



        for (int i =0; i<9; i++){
            buttons[i] = new JButton("");
            buttons[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));

            int position = i;
            buttons[i].addActionListener(e -> {
                System.out.println("Clicked position: " + position);

                if (!buttons[position].getText().isEmpty()) {
                    return; // already occupied
                }
                move(position);
            });

            gridPanel.add(buttons[i]);
        }

        startBtnPanel.setPreferredSize(new Dimension(1000, 80));
        startBtnPanel.setLayout(new GridBagLayout());
        startBtnPanel.setBackground(Color.WHITE);
        startBtnPanel.add(startBtn);
        startBtn.setMargin(new Insets(10,20,10,20));


        add(titlePanel, BorderLayout.NORTH);
        add(gridContainer, BorderLayout.CENTER);
        add(startBtnPanel, BorderLayout.SOUTH);

        startBtn.addActionListener(this);

    }

    public void connect(String playerName) {
        new Thread(() -> {
            try {
                Socket socket;

                // Próba połączenia z istniejącym serwerem
                try {

                    socket = new Socket("localhost", 12345);
                    isHost = false; // serwer już działa - gracz nie jest hostem
                } catch (IOException e) {
                    // Serwer nie odpowiada - gracz zostaje hostem
                    isHost = true;
                    server = new Server();
                    new Thread(() -> server.startServer()).start();
                    // uruchamiamy serwer w tle
                    Thread.sleep(500); // czekamy aż serwer zacznie działać
                    socket = new Socket("localhost", 12345);
                }
                 // Strumienie wejścia/wyjścia do komunikacji z serwerem
                    out = new PrintWriter(
                            socket.getOutputStream(), true);
                    in =
                            new BufferedReader(
                                    new InputStreamReader(socket.getInputStream()));

                    // Wysyłamy imie gracza jako pierwszą wiadomość
                    out.println(playerName);

                // Aktualizacja GUI na wątku Swing (SwingUtilities.invokeLater jest wymagane dla Swing)
                    SwingUtilities.invokeLater(() -> {
                        if (isHost) {
                            playerX = new Client(playerName, "X", null);
                            playerY = new Client("Opponent", "O", null);
                            scoreTitle1.setText(playerName);
                            scoreTitle2.setText("Waiting for opponent...");
                        } else {
                            playerX = new Client("Opponent", "X", null);
                            playerY = new Client(playerName, "O", null);
                            scoreTitle2.setText(playerName);
                        }
                        startBtn.setEnabled(false);
                    });


                    // receiving messages from server
                    new Thread(() -> {
                        try {
                            String msg;

                            while ((msg = in.readLine()) != null) {
                                System.out.println("Received: " + msg);

                                if (msg.startsWith("PLAYERS;")) {
                                    String[] parts = msg.split(";");
                                    SwingUtilities.invokeLater(() -> {
                                        scoreTitle1.setText(parts[1]);
                                        scoreTitle2.setText(parts[2]);
                                        playerX = new Client(parts[1], "X", null);
                                        playerY = new Client(parts[2], "O", null);
                                        startBtn.setEnabled(false);
                                        startBtn.setText("Game started!");
                                    });

                                } else if (msg.startsWith("MOVE;")) {
                                    String[] parts = msg.split(";");
                                    int position = Integer.parseInt(parts[1]);
                                    String symbol = parts[2];
                                    SwingUtilities.invokeLater(() -> {
                                        buttons[position].setText(symbol);
                                        board[position] = symbol;
                                    });

                                } else if (msg.startsWith("WIN;")) {
                                    String winner = msg.split(";")[1];
                                    SwingUtilities.invokeLater(() -> {
                                        if (winner.equals("X")) {
                                            playerX.addPoint();
                                            winsDisplay1.setText("Wins: " + playerX.getPoints());
                                        }
                                        else {
                                            playerY.addPoint();
                                            winsDisplay2.setText("Wins: " + playerY.getPoints());
                                        }
                                        updateScoreDisplays();

                                        JOptionPane.showMessageDialog(this, "Winner: " + winner);

                                        Arrays.fill(board, "");
                                    });
                                } else if (msg.equals("RESET")) {
                                    SwingUtilities.invokeLater(() -> {
                                        // clear all buttons
                                        for (int i = 0; i < 9; i++) {
                                            buttons[i].setText("");
                                            board[i] = "";
                                        }
                                    });
                                }


                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();


                } catch (Exception e) {
                    e.printStackTrace();
                }

        }).start();
    }

    public void move(int position) {
        out.println("MOVE;" + position);
    }

    public void updateScoreDisplays(){
        if (playerX == null || playerY == null) return;

        int playerXPoints = playerX.getPoints();
        int playerYPoints = playerY.getPoints();

        scoreDisplay1.setText(playerXPoints + ":" + playerYPoints);
        scoreDisplay2.setText(playerXPoints + ":" + playerYPoints);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();


        if(source == startBtn){

            String result = (String) JOptionPane.showInputDialog(this,
                    "Enter player name:", "",
                    JOptionPane.PLAIN_MESSAGE, null, null, "Name");

            if (result != null && !result.isEmpty()) {
                playerName = result;

                connect(playerName);
            }
        }
    }

}
