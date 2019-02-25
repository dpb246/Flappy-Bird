import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.CopyOnWriteArrayList;

public class Spacecraft extends PlayerObject {
    private final int speed = 3;
    public Spacecraft(int x, int y, String filepath) {
        super(x, y, 20, 20, filepath);
    }
    public void update() {
        if (dead) return;
        Keyboard k = Keyboard.getInstance();
        if (k.isDown(KeyEvent.VK_UP) && !k.isDown(KeyEvent.VK_DOWN)) {
            this.dy = -speed;
        } else if (!k.isDown(KeyEvent.VK_UP) && k.isDown(KeyEvent.VK_DOWN)) {
            this.dy = speed;
        } else {
            this.dy = 0;
        }
        if (k.isDown(KeyEvent.VK_LEFT) && !k.isDown(KeyEvent.VK_RIGHT)) {
            this.dx = -speed;
        } else if (k.isDown(KeyEvent.VK_RIGHT) && !k.isDown(KeyEvent.VK_LEFT)) {
            this.dx = speed;
        } else {
            this.dx = 0;
        }
        if (k.justPressed(KeyEvent.VK_D)) {
            this.x += 100;
        }
        this.x += this.dx;
        this.y += this.dy;
        this.score += 1;

        super.update();
    }
}
