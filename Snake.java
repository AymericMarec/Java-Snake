import java.util.*;

public class Snake {
    private Position HeadPosition;
    private List<Position> Tail;
    private int TailLenght;
    private int Direction;
    private int TempDirection;
    public Snake(){
       this.HeadPosition = new Position(7, 7);
       this.Direction = 1;
       this.Tail = new ArrayList<Position>();
       this.TailLenght = 0;
    }

    public Position GetHeadPosition(){
        return this.HeadPosition;
    }
    public int GetDirection(){
        return this.Direction;
    }
    public int GetTailLenght(){
        return this.TailLenght;
    }

    public List<Position> GetTail(){
        return this.Tail;
    }


    public void AddToTail(int x ,int y){
        this.Tail.add(new Position(x, y));
        this.TailLenght++;
    }

    public void SetHeadPosition(int x , int y){
        Position NewPosition = new Position(x, y);
        this.HeadPosition = NewPosition;
    }
    public void SetDirection(int Direction){
        this.Direction = Direction;
    }
    public void SetTempDirection(int Direction){
        this.TempDirection = Direction;
    }


    public void Move(){
        this.Direction = this.TempDirection;
        switch (this.Direction) {
            case 1:
                this.HeadPosition.x--;
                break;
            case 2:
                this.HeadPosition.y++;
                break;
            case 3:
                this.HeadPosition.x++;
                break;
            case 4:
                this.HeadPosition.y--;
                break;
            default:
                break;
        }
    }

    public void MoveTheTail(){
        List<Position> NewTail = new ArrayList<Position>();
        NewTail.add(new Position(this.HeadPosition.x,this.HeadPosition.y)); 
        for(int  i = 1 ; i < this.Tail.size();i++){
            NewTail.add(new Position(this.Tail.get(i-1).x, this.Tail.get(i-1).y));
        }
        this.Tail = NewTail;
    }
    public boolean EatHimSelf(){
        for(int i = 0 ; i < this.Tail.size();i++){
            if (this.HeadPosition.x == this.Tail.get(i).x && this.HeadPosition.y == this.Tail.get(i).y){
                return true;
            }
        }
        return false;
    }
    public boolean AppleInSnake(Position ApplePosition){
        for(int i = 0 ; i < this.Tail.size();i++){
            if ((ApplePosition.x == this.Tail.get(i).x && ApplePosition.y == this.Tail.get(i).y )||(ApplePosition.x == this.HeadPosition.x && ApplePosition.y == this.HeadPosition.y)){
                return true;
            }
        }
        return false;
    }
}
