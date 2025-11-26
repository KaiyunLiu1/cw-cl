import java.awt.*;

/**
 * Bullet fired by the player. Moves in a straight line and checks for
 * collisions with map tiles or enemies. A bullet is removed itself when it travels
 * beyond a maximum range or hits something.
 */
public class
bullet extends entity {
    public Point startPoint;
    public static Image bulletImage;

    public bullet(int x, int y) {
        super(bulletImage, x, y, 1, 0, bulletImage.getWidth(null));
    }


    /**
     * Updates the bullet's position. Removes the bullet if it collides
     * with an obstacle or enemy, or if it travels beyond its maximum range.
     */
    public void update() {
        bullet bulletCopy = copy(x + speed, y);
        if (!bulletCopy.intersect() && !bulletCopy.collidesEnemy()) {
            x += speed;
            if (travelledDistance() >= 600) canvas.activeBullets.remove(this);
        } else {
            canvas.activeBullets.remove(this);
        }
    }

    /**
     * Creates a copy of the bullet at a specified position.
     * Used for collision prediction without moving the actual bullet.
     *
     * @return a new Bullet instance at (newX, newY)
     */
    @Override
    public bullet copy(int newX, int newY) {
        bullet copy = new bullet(newX, newY);
        copy.speed = 0;
        copy.velocity = 0;
        copy.image = image;
        copy.state = state;
        return copy;
    }


    public double travelledDistance() {
        int dx = startPoint.x - x;
        int dy = startPoint.y - y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Checks if the bullet collides with any enemy.
     * If a collision occurs, applies damage to the enemy.
     * @return true if a collision occurred, false otherwise
     */
    public boolean collidesEnemy() {
        boolean isInside = false;
        for (Enemy e : canvas.enemies) {
            int x2 = x + image.getWidth(null);
            int y2 = y + image.getHeight(null);
            int eX2 = e.x + e.image.getWidth(null);
            int eY2 = e.y + e.image.getHeight(null);
            boolean widthIsPositive = Math.min(x2, eX2) > Math.max(x, e.x);
            boolean heightIsPositive = Math.min(y2, eY2) > Math.max(y, e.y);
            if (widthIsPositive && heightIsPositive) {
                isInside = true;
                e.damage();
            }
        }
        return isInside;
    }
}
