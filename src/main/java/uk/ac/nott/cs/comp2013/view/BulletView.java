package uk.ac.nott.cs.comp2013.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uk.ac.nott.cs.comp2013.model.Bullet;

public class BulletView {

    private final Bullet bullet;
    private final Image sprite;

    public BulletView(Bullet bullet, Image sprite) {
        this.bullet = bullet;
        this.sprite = sprite;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(sprite, bullet.getX(), bullet.getY(),
                bullet.getWidth(), bullet.getHeight());
    }
}
