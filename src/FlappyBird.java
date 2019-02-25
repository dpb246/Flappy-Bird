import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;
/**
 * Player class
 */
public class FlappyBird extends PlayerObject {
    protected double gravity;
    public FlappyBird(int x, int y, String filepath) {
        super(x, y, 20, 20, filepath);
        this.gravity = -5.0;
    }
    @Override
    /**
     * Moves player and applies gravity
     */
    public void update() {
        if (dead) return;
        Keyboard k = Keyboard.getInstance();
        if (k.justPressed(KeyEvent.VK_SPACE)) {
            this.gravity = -8.0;
        }
        this.gravity += 0.5;
        this.y += (int) this.gravity;
        this.score += 1;
        super.update();
    }
}
