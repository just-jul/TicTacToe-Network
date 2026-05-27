import java.util.ArrayList;

public class Client {

    private String name;
    private int points;
    private int wins;
    private String symbol;

    public Client(String name, String symbol){
        this.name = name;
        this.symbol = symbol;
        this.points = 0;
        this.wins = 0;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public int getWins() {
        return wins;
    }

    public String getSymbol() {
        return symbol;
    }

    public void addPoint(){
        points++;
    }
    public void addWin(){
        wins++;
    }

}
