import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RoundedScoreLabel extends JLabel {
    private int radius;
    private JLabel playerName;
    private JLabel score;
    private JLabel wins;


    public RoundedScoreLabel(String text, int radius){
        this.setLayout(new BorderLayout());
        this.radius = radius;
        this.setBorder(new EmptyBorder(0, 10, 0, 0));

        playerName = new JLabel(text, SwingConstants.CENTER);
        playerName.setFont(new Font("Helvetica", Font.BOLD, 14));
        playerName.setOpaque(false);
        this.add(playerName, BorderLayout.NORTH);


        setOpaque(false);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.fillRect(0, getHeight()/2, getWidth(), getHeight() / 2);
        g2.dispose();
        super.paintComponent(g);
    }

}
