import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    JFrame frame;
    int frameWidth = 360;
    int frameHeight = 640;

    Image backgroundImage;
    Image birdImage;
    Image lowerPipeImage;
    Image upperPipeImage;

    //    player
    int playerStartPosX = frameWidth / 8;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 32;
    int playerHeight = 18;
    int score = 0;
    Player player;


    // Pipe
    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeigth = 512;
    ArrayList<Pipe> pipes;
//    Game Logic
    Timer gameLoop;
    Timer pipesCooldown;
    int gravity = 1;

    JLabel scoreLabel;

    //    Constructor
    public FlappyBird(JFrame frame) {
        this.frame = frame; // Initialize the frame reference
        setPreferredSize(new Dimension(360, 640));
        setFocusable(true);
        addKeyListener(this);

//        load images
        backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("assets/bintang.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();
//        create player
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes = new ArrayList<Pipe>();

//pipes cooldown timer

        pipesCooldown = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
//        score
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(scoreLabel, BorderLayout.NORTH);

        pipesCooldown.start();
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }


    public void placePipes(){
        int randomPipePosY = (int) (pipeStartPosY - pipeHeigth/4 - Math.random() * (pipeHeigth/2));
        int openingSpace = frameHeight/4;

        Pipe upperPipe = new Pipe(pipeStartPosX,randomPipePosY,pipeWidth,pipeHeigth,upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX,randomPipePosY + pipeHeigth + openingSpace,pipeWidth,pipeHeigth,lowerPipeImage);
        pipes.add(lowerPipe);
    }
    private void checkPipePassing() {
        for (Pipe pipe : pipes) {
            if (!pipe.isPassed() && pipe.getPosX() + pipe.getWidth() < player.getPosX()) {
                // If the bird has passed the pipe, increment the score
                pipe.setPassed(true);
                score++;
                updateScoreLabel(); // Update the score label
            }
        }
    }
    private void updateScoreLabel() {
        // Update the text of the score label
        scoreLabel.setText("Score: " + score);
    }



    // ini dibuat untuk menambahkan component ke panel
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    //    fungsi untuk menampilkan objek ke panel
    private void draw(Graphics g) {
//        buat gambar background
        g.drawImage(backgroundImage, 0, 0, frameWidth, frameHeight, null);
//        buat gambar player
        g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);

//        gambar semua objek pipe yang ada di list pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.getImage(),pipe.getPosX(),pipe.getPosY(),pipe.getWidth(),pipe.getHeight(),null);
        }

    }


    private void checkGameOver() {
        // Check collision with pipes
        for (Pipe pipe : pipes) {
//            ngebuat semacam hitbox
//            nanti kalo kedua hitbox ini bersinggungan/nyentuh dia kan langsung run fungsi gameover
            Rectangle birdBounds = new Rectangle(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight());
            Rectangle pipeBounds = new Rectangle(pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight());

            if (birdBounds.intersects(pipeBounds)) {
                gameOver();
                return;
            }
        }

        // Check collision with bottom frame
        if (player.getPosY() + player.getHeight() > frameHeight) {
            gameOver();
        }
    }

    private void gameOver() {

        gameLoop.stop(); // Stop the game loop
        pipesCooldown.stop(); // Stop spawning new pipes
        // display a game over message or screen
        JOptionPane.showMessageDialog(this, "Game Over! Press 'R' to restart", "Game Over", JOptionPane.INFORMATION_MESSAGE);

    }

    private void restartGame() {
//        reset score
        score = 0;
        // Reset player position
        player.setPosX(playerStartPosX);
        player.setPosY(playerStartPosY);
        // Clear existing pipes
        pipes.clear();
        // Restart timers
        gameLoop.start();
        pipesCooldown.start();
    }




    private void move(){
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY((Math.max(player.getPosY(),0)));

//        gerakan pipa
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipe.getVelocityX());
        }

        // Check game over condition
        checkPipePassing();
        checkGameOver();

    }


    private void restartGameIfNeeded(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            restartGame();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
//           // di java kebalik, Y kebawah positif
//            jadi dengan dikasi -10 dia naik 10 keatas
            player.setVelocityY(-10);
        }else {
            restartGameIfNeeded(e); // Check if 'R' key is pressed for restart
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
