import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
/**
 * Singleton GameWorld
 * Stores all gameObjects and provides methods to control all of them
 */
public class GameWorld extends JComponent {
    private final int gapBetweenWalls = 100;
    public final int DELAY = 16;
    private CopyOnWriteArrayList<gameObject> objs;
    private CopyOnWriteArrayList<gameObject> walls;
    private static GameWorld gw = null;
    private PlayerObject player = null;
    private int swidth, sheight;
    private Random random = new Random();
    private Image wall;
    private JLabel instructions;
    private JLabel score;

    private GameWorld() {
        objs = new CopyOnWriteArrayList<>();
        walls = new CopyOnWriteArrayList<>();
        this.swidth = 500;
        this.sheight = 350;
        ImageIcon ii = new ImageIcon("src/wall.png");
        this.wall = ii.getImage();

        score = new JLabel("Score: 0");
        score.setForeground(Color.WHITE);
        score.setBounds(5, 5, 100, 20);
        score.setLocation(5, 5);
        score.setVisible(true);
        super.add(score);

        instructions = new JLabel("Press Space to play");
        instructions.setForeground(Color.WHITE);
        instructions.setBounds(100, 200, 200, 44);
        instructions.setLocation(100, 200);
        instructions.setVisible(true);
        super.add(instructions);
    }
    /**
     * Reset game for replay
     */
    public void resetGame() {
        objs.clear();
        walls.clear();
        player = null;
    }
    public int getWidth() {
        return this.swidth;
    }
    public int getHeight() {
        return this.sheight;
    }
    public boolean getPlayerAlive() {
        if (player == null) return true;
        return !player.isDead();
    }
    public int getPlayerScore() {
        if (player == null) return 0;
        return player.getScore();
    }
    public void updateScoreDisplay() {
        this.score.setText("Score: "  + this.getPlayerScore());
    }
    public void updateInstructions(String s) {
        this.instructions.setText(s);
    }
    public void setInstructionsVisible(boolean visible) {
        this.instructions.setVisible(visible);
    }
    /**
     * Ensure that only one instance of GameWorld exists
     * @return GameWorld
     */
    public static GameWorld getInstance() {
        if (gw == null) gw = new GameWorld();
        return gw;
    }
    /**
     * adds gameObject to world
     * @param go gameObject to be added
     * @return the gameObject reference
     */
    public gameObject add(gameObject go) {
        this.objs.add(go);
        super.add(go);
        return go;
    }
    /**
     * adds gameObject as a wall to world
     * @param go gameObject to be added
     * @return the gameObject reference
     */
    public gameObject addWall(gameObject go) {
        this.walls.add(go);
        return this.add(go);
    }
    /**
     * adds player to world
     * @param go player (extends gameObject) to be added
     * @return the player reference
     */
    public gameObject addPlayer(PlayerObject go) {
        this.player = go;
        return this.add(go);
    }
    public CopyOnWriteArrayList<gameObject> getWalls() {
        return walls;
    }
    /**
     * Randomly spawns a pair of walls with a gap in between
     */
    public void spawnWallPair() {
        int length = 20*random.nextInt(10)+20;
        this.addWall(new MovingWall(this.swidth+2, 0, 20, length, wall));
        this.addWall(new MovingWall(this.swidth+2, length+gapBetweenWalls, 20, sheight-length-gapBetweenWalls, wall));
    }
    /**
     * Draws all objects in GameWorld
     * @param g Graphics to draw objects onto
     * @param io ImageObserver to pass to draw call
     */
    public void drawAll(Graphics g, ImageObserver io) {
        Graphics2D g2d = (Graphics2D) g;
        for (gameObject o : objs) {
            o.draw(g2d, io);
        }
    }
    /**
     * Calls update for each object in GameWorld
     */
    public void updateAll() {
        for (gameObject o : objs) {
            o.update();
            if (o.getWantDestroyal()) {
                this.objs.remove(o);
                this.walls.remove(o);
            }
        }
    }
}
