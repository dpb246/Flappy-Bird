import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static java.lang.Long.max;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import javax.swing.JPanel;

public class Board extends JPanel implements Runnable {
    private final int interval = 100;
    private int countSinceLast = interval;
    private Thread animator;
    private PlayerObject bird;
    private GameWorld world;
    public Board () {
        add(Keyboard.getInstance());
        initBoard();

        this.setVisible(true);
    }
    private void initBoard() {
        this.setBackground(Color.BLACK);
        super.setDoubleBuffered(true);
        setLayout(null);
        world = GameWorld.getInstance();
        setPreferredSize(new Dimension(world.getWidth(), world.getHeight()));
        this.add(world);
    }
    @Override
    public void addNotify() {
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        world.drawAll(g, null);
        Toolkit.getDefaultToolkit().sync();
    }
    private void cycle() {
        world.updateAll();
        bird.checkForHit(world.getWalls(), world.getWidth(), world.getHeight());
        if (countSinceLast == interval) {
            world.spawnWallPair();
            countSinceLast = 0;
        }
        countSinceLast++;
        world.updateScoreDisplay();
        Keyboard.getInstance().updateStates(); //Call last to update keyboard states for next frame
    }
    private void playGame() {
        long beforeTime, timeDiff;
        beforeTime = System.currentTimeMillis();
        while (world.getPlayerAlive()) {
            cycle();
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            this.sleep(max(2, GameWorld.getInstance().DELAY - timeDiff));
            beforeTime = System.currentTimeMillis();
        }
    }
    private void initGame() {
        bird = new FlappyBird(100, 150,"src/craft.png");
        world.addPlayer(bird);
        countSinceLast = interval;
        Keyboard.getInstance().updateStates();
    }
    private void sleep(long millisecoonds) {
        try {
            Thread.sleep(millisecoonds);
        } catch (InterruptedException e) {
            String msg = String.format("Thread interrupted: %s", e.getMessage());
            JOptionPane.showMessageDialog(this, msg, "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    @Override
    public void run() {
        while (true) {
            world.resetGame();
            initGame();
            while (!Keyboard.getInstance().justPressed(KeyEvent.VK_SPACE)) {
                Keyboard.getInstance().updateStates(); //Call last to update keyboard states for next frame
                sleep(GameWorld.getInstance().DELAY);
            }
            world.setInstructionsVisible(false);
            playGame();
            System.out.println("GAME OVER");
            System.out.println("FINAL SCORE: " + world.getPlayerScore());
            world.updateInstructions("<html>FINAL SCORE: " + world.getPlayerScore() + "<br/>PRESS SPACE TO PLAY AGAIN</html>");
            world.setInstructionsVisible(true);
            sleep(250);
        }
    }

}