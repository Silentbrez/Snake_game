import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener , KeyListener {
    // Game code here
    private class Tile{
        int x;
        int y;

        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    int boardWidth;
    int boardheight;
    int tileSize = 25;

    Tile snakeHead;
    ArrayList<Tile> snakeBody ;
    Tile food;
    Random random = new Random();
    int highScore = 0;
    final int initialHeadX = 5;
    final int initialHeadY = 5;
    //game logic
    Timer gameloop;
    int velocityX ;
    int velocityY ;
    boolean gameOver = false;

   SnakeGame(int boardWidth, int boardheight) {
        this.boardWidth = boardWidth;
        this.boardheight = boardheight;
        setPreferredSize(new Dimension(this.boardWidth,this.boardheight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        snakeBody = new ArrayList<Tile>();
        resetGame();

        gameloop = new Timer(100, this);
        gameloop.start();
        requestFocusInWindow();
    
}

    private void resetGame(){
        snakeBody.clear();
        snakeHead = new Tile(initialHeadX, initialHeadY);
        placefood();
        velocityX = 0;
        velocityY = 1;
        gameOver = false;
        if (gameloop != null && !gameloop.isRunning()){
            gameloop.start();
        }
        requestFocusInWindow();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw (Graphics g){

        //food
        g.setColor(Color.RED);
        //g.fillRect(food.x* tileSize, food.y*tileSize, tileSize, tileSize);
        g.fill3DRect(food.x* tileSize, food.y*tileSize, tileSize, tileSize, true);
        
        //snake
        g.setColor(Color.GREEN);
        //g.fillRect(snakeHead.x* tileSize, snakeHead.y*tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x* tileSize, snakeHead.y*tileSize, tileSize, tileSize, true);
        //snake body
        //snake body
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            g.fill3DRect(snakePart.x* tileSize, snakePart.y*tileSize, tileSize, tileSize, true);
        }

        g.setFont(new Font("Ariel",Font.PLAIN,16));
        if(gameOver){
            g.setColor(Color.RED);
            g.drawString("Game Over! " + String.valueOf(snakeBody.size()) + " points", tileSize-16,tileSize);
            g.setColor(Color.WHITE);
            g.drawString("High Score: " + highScore, tileSize-16, tileSize*2);
        }
        else{
            g.setColor(Color.WHITE);
            g.drawString("Score: " + String.valueOf(snakeBody.size()) + " points", tileSize-16,tileSize);
            g.drawString("High Score: " + highScore, tileSize-16, tileSize*2);
        }
    
    }
    public void placefood(){
        int x = random.nextInt(boardWidth / tileSize);
        int y = random.nextInt(boardheight / tileSize);
        food = new Tile (x,y);
    }
    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }   
    public void move(){

        // eat food
        if (collision(snakeHead, food)){
            // grow by adding a new segment at the current head position
            snakeBody.add(new Tile(snakeHead.x, snakeHead.y));
            placefood();
        }

        // move body: shift each part to the previous part's position
        for (int i = snakeBody.size() - 1; i > 0; i--) {
            Tile prev = snakeBody.get(i - 1);
            Tile cur = snakeBody.get(i);
            cur.x = prev.x;
            cur.y = prev.y;
        }
        if (snakeBody.size() > 0) {
            Tile first = snakeBody.get(0);
            first.x = snakeHead.x;
            first.y = snakeHead.y;
        }

        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // wrap around edges instead of game over
        int maxCols = boardWidth / tileSize;
        int maxRows = boardheight / tileSize;
        if (snakeHead.x < 0) snakeHead.x = maxCols - 1;
        else if (snakeHead.x >= maxCols) snakeHead.x = 0;
        if (snakeHead.y < 0) snakeHead.y = maxRows - 1;
        else if (snakeHead.y >= maxRows) snakeHead.y = 0;

        // game over only on self-collision
        for (int i = 0; i < snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            if (collision(snakeHead, snakePart)){
                gameOver = true;
                if (snakeBody.size() > highScore) {
                    highScore = snakeBody.size();
                }
                break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {    
        
        move();
        repaint();
        if(gameOver){
            gameloop.stop();
        } 
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
       
        if (e.getKeyCode() == KeyEvent.VK_SPACE && gameOver) {
            resetGame();
            return;
        }

        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if (key == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if (key == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } else if (key == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        }


    
    



}
