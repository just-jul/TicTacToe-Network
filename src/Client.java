import java.util.ArrayList;

public class Client {

    private String name;
    private int points;
    private int wins;
    private char symbol;

    public Client(String name, char symbol){
        this.name = name;
        this.symbol = symbol;
        this.points = 0;
        this.wins = 0;
    }

    public void addPoint(){
        points++;
    }
    public void addWin(){
        wins++;
    }

}
