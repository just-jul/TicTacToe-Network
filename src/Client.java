import java.util.ArrayList;

public class Client {
    // przwchowuje informacje o kliencie i jego punktach

    private String name;
    private int points;
    private String symbol;
    private ClientHandler clientHandler;

    public Client(String name, String symbol, ClientHandler clientHandler){
        this.name = name;
        this.symbol = symbol;
        this.clientHandler = clientHandler;
        this.points = 0;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public String getSymbol() {
        return symbol;
    }

    public void addPoint(){
        points++;
    }


}
