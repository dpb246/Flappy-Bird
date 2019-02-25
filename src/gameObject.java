import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.max;
import static java.lang.Math.min;
/**
 * All Object in GameWorld extend this class
 */
public abstract class gameObject extends JComponent{
    protected int x, y, dx, dy, w, h;
    protected Image image;
    protected Rectangle2D.Float hitbox;
    protected boolean wantsRemoval = false;
    public gameObject(int x, int y, int w, int h, String filepath) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        loadImage(filepath);
        hitbox = new Rectangle2D.Float(this.x, this.y, this.w, this.h);
    }
    public gameObject(int x, int y, int w, int h, Image im) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.image = im;
        hitbox = new Rectangle2D.Float(this.x, this.y, this.w, this.h);
    }
    public void destroy() {
        wantsRemoval = true;
    }
    public boolean getWantDestroyal() {
        return this.wantsRemoval;
    }
    /**
     * Updates hitbox to new position
     */
    protected void updateHitbox() {
        hitbox.setRect(this.x, this.y, this.w, this.h);
    }
    /**
     * Loads image from filepath
     * @param filepath string to image file
     */
    protected void loadImage(String filepath) {
        ImageIcon ii = new ImageIcon(filepath);
        this.image = ii.getImage();
    }
    /**
     * Draw method that is called every frame
     */
    public void draw(Graphics2D g, ImageObserver io) {
        g.drawImage(this.image, this.x, this.y, io);
    }
    /**
     * Method called every frame
     */
    public abstract void update();
    /**
     * @return rectangle hitbox
     */
    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }
    /**
     * @return current x position on screen
     */
    public int getX() {
        return this.x;
    }
    /**
     * @return current y position on screen
     */
    public int getY() {
        return this.y;
    }
    /**
     * @return height of image
     */
    public int getHeight() {
        return this.h;
    }
    /**
     * @return width of image
     */
    public int getWidth() {
        return this.w;
    }
    /**
     * @return image sprite
     */
    public Image getImage() {
        return this.image;
    }
}
