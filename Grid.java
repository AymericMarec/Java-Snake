import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Random;
import java.lang.Thread;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Grid extends JFrame{
    private JPanel[][] GridGame;
    private JPanel[][] ObjectsGame;
    private boolean color;
    private Snake Snake;
    private Timer timer;
    private Position ApplePosition;

    public Grid(){
        GridGame = new JPanel[15][15];
        ObjectsGame = new JPanel[15][15];
        color = true;
        Snake = new Snake();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JLayeredPane layeredPane = new JLayeredPane();

        // Setting the GridGame's BackGround
        for(int i = 0;i < 15;i++){
            for(int j = 0;j < 15;j++){
                color = !color;
                GridGame[i][j] = new JPanel();
                if (color) {
                    GridGame[i][j].setBackground(new Color(170,215,81));
                }else{
                    GridGame[i][j].setBackground(new Color(162,209,73));
                }
                GridGame[i][j].setBounds(j * 50, i * 50, 50, 50);
                layeredPane.add(GridGame[i][j], JLayeredPane.DEFAULT_LAYER);
            }
        }
        // Setting the Grid for Player and apple
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                ObjectsGame[i][j] = new JPanel();
                ObjectsGame[i][j].setOpaque(false);
                ObjectsGame[i][j].setBounds(j * 50, i * 50, 50, 50);
                layeredPane.add(ObjectsGame[i][j], JLayeredPane.PALETTE_LAYER);
            }
        }

        //Add the Snake and the first apple in the Grid
        ObjectsGame[Snake.GetHeadPosition().x][Snake.GetHeadPosition().y].setBackground(new Color(69, 115, 232));
        ObjectsGame[Snake.GetHeadPosition().x][Snake.GetHeadPosition().y].setOpaque(true);
        ApplePosition = AddApple();

        add(layeredPane, BorderLayout.CENTER);
        pack();
        setSize(1000,1000);
        setVisible(true);
        //Event each seconds
        timer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                Position LastTail;
                //If the Snake had a tail
                if (Snake.GetTailLenght() == 0){
                    LastTail = new Position(Snake.GetHeadPosition().x, Snake.GetHeadPosition().y);
                }else {
                    LastTail = new Position(Snake.GetTail().get(Snake.GetTailLenght()-1).x, Snake.GetTail().get(Snake.GetTailLenght()-1).y);
                }
                //If the Snake had a tail
                if(Snake.GetTail().size() != 0){
                    Snake.MoveTheTail();
                }

                Snake.Move();
                //If loose :
                if (Snake.GetHeadPosition().x > 14 || Snake.GetHeadPosition().x < 0 || Snake.GetHeadPosition().y > 14 ||Snake.GetHeadPosition().y < 0 || Snake.EatHimSelf()){
                    timer.stop();
                    YouLoose();
                    return;
                }   

                //Set new position of the snake's head and remove the the last part of the snake's tail
                ObjectsGame[LastTail.x][LastTail.y].setOpaque(false);
                ObjectsGame[LastTail.x][LastTail.y].setBackground(Color.black);
                ObjectsGame[Snake.GetHeadPosition().x][Snake.GetHeadPosition().y].setBackground(new Color(69,70,232));
                ObjectsGame[Snake.GetHeadPosition().x][Snake.GetHeadPosition().y].setOpaque(true);
                //set the color for the snake's tail
                for (int i = 0 ; i < Snake.GetTailLenght();i++){
                    ObjectsGame[Snake.GetTail().get(i).x][Snake.GetTail().get(i).y].setBackground(new Color(69,115,232));
                    ObjectsGame[Snake.GetTail().get(i).x][Snake.GetTail().get(i).y].setOpaque(true);
                }


                if (Integer.valueOf(Snake.GetHeadPosition().x).equals(Integer.valueOf(ApplePosition.x)) && Integer.valueOf(Snake.GetHeadPosition().y).equals(Integer.valueOf(ApplePosition.y))){
                    Snake.AddToTail(LastTail.x,LastTail.y);
                    ObjectsGame[ApplePosition.x][ApplePosition.y].setOpaque(true);
                    ObjectsGame[ApplePosition.x][ApplePosition.y].setBackground(new Color(69,115,232));
                    ApplePosition = AddApple();
                }
            }
        });

        timer.start();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        if (Snake.GetDirection() != 3 ){
                            Snake.SetTempDirection(1);
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (Snake.GetDirection() != 4 ){
                            Snake.SetTempDirection(2);
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (Snake.GetDirection() != 1 ){
                            Snake.SetTempDirection(3);
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (Snake.GetDirection() != 2 ){
                            Snake.SetTempDirection(4);
                        }
                        break;
                }
            }
        });

        setFocusable(true);
    }
    private Position AddApple(){
        Position ApplePosition = Position.GetRandomPosition();
        while (Snake.AppleInSnake(ApplePosition)){
            System.out.println("Apple in snaaake");
            ApplePosition = Position.GetRandomPosition();
        }
        ObjectsGame[ApplePosition.x][ApplePosition.y].setBackground(Color.red);
        ObjectsGame[ApplePosition.x][ApplePosition.y].setOpaque(true);
        return ApplePosition;
    }
    private void YouLoose() {
        getContentPane().removeAll();
        getContentPane().setBackground(new Color(255,127,127));
        
        JLabel gameOverLabel = new JLabel("T'as perdu gros chien");
        gameOverLabel.setForeground(Color.WHITE);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 50));
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        add(gameOverLabel, BorderLayout.CENTER);
        
        revalidate(); 
        repaint();
    }
}