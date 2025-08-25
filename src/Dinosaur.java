import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Dinosaur extends JPanel implements ActionListener, KeyListener {

        int boardWidth = 750;
        int boardHeight = 250;
        int trackImgHeight = 30;
        int trackImgWidth = 750;

        //images
        Image dinosaurImg;
        Image dinosaurDeadImg;
        Image dinosaurJumpImg;
        Image cactus1Img;
        Image cactus2Img;
        Image cactus3Img;
        Image dinoDuckImg;
        Image birdImg;
        Image trackImg;
        Image cloudImg;
        Image gameOverImg;
        Image resetImg;

     class Block {
        int x;
        int y;
        int width;
        int height;
        Image img;

        Block(int x, int y, int width, int height, Image img) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.img = img;
        }
    }

     Block track1;
     Block track2;
     Block cloud1;
     Block cloud2;

        //dinosaur
    int dinosaurWidth = 88;
    int dinosaurHeight = 94;
    int dinoDuckedHeight = 60;
    int dinosaurX = 50;
    int dinosaurY = boardHeight - dinosaurHeight;
    boolean isDucking = false;

    Block dinosaur;

    //kaktus
    int cactus1Width = 34;
    int cactus2Width = 69;
    int cactus3Width = 102;

    int cactusHeight = 70;
    int cactusX = 700;
    int cactusY = boardHeight - cactusHeight;
    ArrayList<Block> obstacleArray;

    //Pterodactylus
    int birdWidth = 50;
    int birdHeight = 50;
    int birdX = 700;
    int birdY = boardHeight - birdHeight - cactusHeight;

    //physik
    int velocityX = -12;
    int velocityY = 0;
    int gravity = 1;

    boolean gameOver = false;
    int score = 0;
    int highscore = 0;

    Timer gameLoop;
    Timer placeObstacleTimer;

        public Dinosaur(){

            setPreferredSize(new Dimension(boardWidth,boardHeight));
            setBackground(Color.GRAY);
            setFocusable(true);
            addKeyListener(this);

            dinosaurImg     = new ImageIcon(getClass().getResource("./img/dino-run.gif")).getImage();
            dinoDuckImg     = new ImageIcon(getClass().getResource("./img/dino-duck.gif")).getImage();
            dinosaurDeadImg = new ImageIcon(getClass().getResource("./img/dino-dead.png")).getImage();
            dinosaurJumpImg = new ImageIcon(getClass().getResource("./img/dino-jump.png")).getImage();
            cactus1Img      = new ImageIcon(getClass().getResource("./img/cactus1.png")).getImage();
            cactus2Img      = new ImageIcon(getClass().getResource("./img/cactus2.png")).getImage();
            cactus3Img      = new ImageIcon(getClass().getResource("./img/cactus3.png")).getImage();
            birdImg         = new ImageIcon(getClass().getResource("./img/bird.gif")).getImage();
            trackImg        = new ImageIcon(getClass().getResource("./img/track.png")).getImage();
            cloudImg        = new ImageIcon(getClass().getResource("./img/cloud.png")).getImage();
            gameOverImg     = new ImageIcon(getClass().getResource("./img/game-over.png")).getImage();
            resetImg        = new ImageIcon(getClass().getResource("./img/reset.png")).getImage();


            //track
            track1 = new Block(0, boardHeight - trackImgHeight, trackImgWidth, trackImgHeight, trackImg);
            track2 = new Block(boardWidth, boardHeight - trackImgHeight, trackImgWidth, trackImgHeight, trackImg);

            //wolke
            cloud1 = new Block(boardWidth, 150, 50, 100, cloudImg);
            cloud2 = new Block(boardWidth+50, 50, 60, 80, cloudImg);



            //dinosaur
            dinosaur = new Block(dinosaurX, dinosaurY, dinosaurWidth, dinosaurHeight, dinosaurImg);
            //cactus
            obstacleArray = new ArrayList<Block>();
            //timer
            gameLoop = new Timer(1000/60, this);
            gameLoop.start();
            //cactus timer
            placeObstacleTimer = new Timer(1500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    placeObstacle();
                }
            });
            placeObstacleTimer.start();
        }

        void placeObstacle() {
            if(gameOver){
                return;
            }
          double placeObstacleChange = Math.random();
          if(placeObstacleChange > .90){
              Block cactus = new Block(cactusX, cactusY, cactus3Width, cactusHeight, cactus3Img);
              obstacleArray.add(cactus);
          } else if(placeObstacleChange > .70){
              Block cactus = new Block(cactusX, cactusY, cactus2Width, cactusHeight, cactus2Img);
              obstacleArray.add(cactus);
          } else if(placeObstacleChange > .50){
              Block cactus = new Block(cactusX, cactusY, cactus1Width, cactusHeight, cactus1Img);
              obstacleArray.add(cactus);
          } else if(placeObstacleChange > .30) {
              Block bird = new Block(birdX, birdY, birdWidth, birdHeight, birdImg);
              obstacleArray.add(bird);
          }
          if(obstacleArray.size() > 10){
              obstacleArray.remove(0);
          }

        }

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            draw(g);
        }

        public void draw(Graphics g){

            g.drawImage(track1.img, track1.x , track1.y, track1.width, track1.height, null);
            g.drawImage(track2.img, track2.x, track2.y, track2.width, track2.height, null);

            g.drawImage(cloud1.img, cloud1.x, cloud1.y, cloud1.width, cloud1.height, null);
            g.drawImage(cloud2.img, cloud2.x, cloud2.y, cloud2.width, cloud2.height, null);

            if(gameOver){
                g.drawImage(gameOverImg, 200, 75, 375, 100, null);
                g.drawImage(resetImg, 365, 150, 50, 50, null);
            }

            g.drawImage(dinosaur.img, dinosaur.x, dinosaur.y, dinosaur.width, dinosaur.height, null);
            for(int i = 0; i< obstacleArray.size(); i++){
                Block obstacle = obstacleArray.get(i);
                g.drawImage(obstacle.img, obstacle.x, obstacle.y, obstacle.width, obstacle.height, null);
            }
            g.setColor(Color.BLACK);
            g.setFont(new Font("Courier", Font.PLAIN, 32));
            g.drawString("Highscore: " + highscore, 450, 35);
            if(gameOver){
                g.drawString("Game Over: " + String.valueOf(score), 10, 35);
            } else {
                g.drawString("Score: " + String.valueOf(score), 10, 35);
            }
        }

        public void move(){
            velocityY += gravity;
            dinosaur.y += velocityY;

            if (dinosaur.y>dinosaurY){
                dinosaur.y = dinosaurY;
                velocityY = 0;
                if(isDucking){
                    dinosaur.img = dinoDuckImg;
                }else{
                    dinosaur.img = dinosaurImg;
                }
            }
            track1.x += velocityX;
            track2.x += velocityX;
            cloud1.x += velocityX + 9;
            cloud2.x += velocityX + 10;

            if(cloud2.x < -60){
                cloud2.x = boardWidth + 50;
            }
            if(cloud1.x < -100){
                cloud1.x = boardWidth;
            }
            if(track1.x < -750){
                track1.x = boardWidth;
            }
            if(track2.x < -750){
                track2.x = boardWidth;
            }

            //cactus
            for(int i = 0; i< obstacleArray.size(); i++){
                Block cactus = obstacleArray.get(i);
                cactus.x += velocityX;

                if(collision(cactus, dinosaur)){
                    dinosaur.img = dinosaurDeadImg;
                    gameOver = true;
                }
            }
            score++;
        }

        boolean collision(Block a, Block b){
            return a.x < b.x + b.width &&
                    a.x + a.width > b.x &&
                    a.y < b.y + b.height &&
                    a.y + a.height > b.y;
        }

        @Override
        public void actionPerformed(ActionEvent e){
            move();
            repaint();
            if(gameOver){
                placeObstacleTimer.stop();
                gameLoop.stop();
            }
        }
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (dinosaur.y == dinosaurY) {
                    velocityY = -17;
                    dinosaur.img = dinosaurJumpImg;
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (dinosaur.y == dinosaurY) {
                    dinosaur.img = dinoDuckImg;
                    isDucking = true;
                    dinosaur.height = dinoDuckedHeight;
                    dinosaurY = boardHeight - dinoDuckedHeight;
                }
            }
                if(gameOver){
                    dinosaur.y = dinosaurY;
                    dinosaur.img = dinosaurImg;
                    highscore = Math.max(highscore, score);
                    cloud1.x = boardWidth;
                    cloud2.x = boardWidth;
                    score = 0;
                    gameOver = false;
                    velocityY = 0;
                    obstacleArray.clear();
                    gameLoop.start();
                    placeObstacleTimer.start();

                }
            }

        @Override
        public void keyReleased(KeyEvent e){
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                isDucking = false;
                dinosaur.height = dinosaurHeight;
                dinosaurY = boardHeight - dinosaurHeight;
            }
        }
}
