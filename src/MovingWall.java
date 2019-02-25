import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Moving wall that player has to avoid
 */
public class MovingWall extends gameObject {
    public MovingWall(int x, int y, int width, int height, Image im) {
        super(x, y, width, height, im);
        this.dx = -2;
    }
    @Override
    public void draw(Graphics2D g, ImageObserver io) {
        for (int i = this.y; i < this.y+this.h; i += 20) {
            g.drawImage(this.image, this.x, i, io);
        }
    }
    @Override
    /**
     * moves left each frame
     */
    public void update() {
        this.x += this.dx;
        updateHitbox();
        if (this.x + this.w < 0) {
            this.destroy();
        }
    }
}
