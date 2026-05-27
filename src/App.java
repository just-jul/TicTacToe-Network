import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;


public class App extends JFrame implements ActionListener{

    private JLabel title = new JLabel("Tic Tac Toe");
    private JPanel titlePanel = new JPanel();
    private JPanel gridPanel = new JPanel();
    private JButton startBtn = new JButton("Start");
    private JPanel startBtnPanel = new JPanel();

    private JPanel scorePanel1 = new JPanel();
    private JLabel scoreTitle1 = new JLabel("Player 1");
    private JPanel scorePanel2 = new JPanel();
    private JLabel scoreTitle2 = new JLabel("Player 2");
    static Font globalFont = new Font("Helvetica", Font.PLAIN, 16);


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

        scorePanel1.setPreferredSize(new Dimension(160, 200));
        scorePanel1.setBackground(Color.WHITE);
        scorePanel1.add(scoreTitle1);
        scorePanel2.setPreferredSize(new Dimension(160, 200));
        scorePanel2.add(scoreTitle2);
        scorePanel2.setBackground(Color.WHITE);


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

//        gridContainer.add(scorePanel1, BorderLayout.WEST);
//        gridContainer.add(scorePanel2, BorderLayout.EAST);
        gridPanel.setPreferredSize(new Dimension(600, 600));
        gridPanel.setLayout(new GridLayout(3, 3));
        //gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gridPanel.setBackground(Color.WHITE);
        for (int i =0; i<9; i++){
            final JLabel label = new JLabel("");
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            gridPanel.add(label);
        }

        startBtnPanel.setPreferredSize(new Dimension(1000, 80));
        startBtnPanel.setLayout(new GridBagLayout());
        startBtnPanel.setBackground(Color.WHITE);
        startBtnPanel.add(startBtn);
        startBtn.setMargin(new Insets(10,20,10,20));


        add(titlePanel, BorderLayout.NORTH);
        add(gridContainer, BorderLayout.CENTER);
        add(startBtnPanel, BorderLayout.SOUTH);

    }

    public void connect(){
        try{
            socket = new Socket(adresSerwera, portSerwera);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Thread watek = new
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton sourceBtn = (JButton) e.getSource();

        if(sourceBtn == startBtn){

        }
    }

}
