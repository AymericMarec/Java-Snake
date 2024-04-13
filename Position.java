import java.util.Random;

public class Position {
    public int x;
    public int y;
    public Position(int x,int y){
        this.x = x;
        this.y = y;
    }

    public static Position GetRandomPosition(){
        Random randomNumbers = new Random();
        int x = randomNumbers.nextInt(10);
        int y = randomNumbers.nextInt(10);
        Position RandomPosition = new Position(x, y);
        return RandomPosition;
    }
}
