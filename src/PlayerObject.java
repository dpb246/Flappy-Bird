import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerObject extends gameObject {
    protected boolean dead = false;
    protected int score = 0;
    public PlayerObject(int x, int y, int w, int h, String filepath) {
        super(x, y, w, h, filepath);
    }
    /**
     * Returns true if player is dead
     * @return boolean
     */
    public boolean isDead() {
        return dead;
    }
    /**
     * sets player to dead
     */
    protected void die() {
        dead = true;
    }
    /**
     * @return current player score
     */
    public int getScore() {
        return this.score/10;
    }
    @Override
    public void destroy() {
        super.destroy();
        this.die();
    }
    /**
     * Kills player if it hits any of the objects or walls
     * @param objs List of objects to check for hits against
     * @param maxw Max x value, edge of screen
     * @param maxh Max y value, edge of screen
     */
    public void checkForHit(CopyOnWriteArrayList<gameObject> objs, int maxw, int maxh) {
        if (dead) return;
        if (this.y < 0 ||(this.y+this.h) > maxh || this.x < 0 || (this.x+this.w) > maxw) { //Hit bottom edge of screen
            die();
        }
        for (gameObject go : objs) {
            if (hitbox.intersects(go.getHitbox())) {
                die();
            }
        }
    }
    @Override
    public void update() {
        super.updateHitbox();
    }
}
