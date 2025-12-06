package uk.ac.nott.cs.comp2013.physics;

import uk.ac.nott.cs.comp2013.model.Bullet;
import uk.ac.nott.cs.comp2013.model.Enemy;
import uk.ac.nott.cs.comp2013.model.TileMap;
import uk.ac.nott.cs.comp2013.utils.CollisionUtils;

import java.util.List;

/**
 * Handles collision detection for bullets.
 *
 * <p>This class checks bullet collisions against the map and enemies,
 * and tracks travel distance.</p>
 */
public final class BulletPhysics {

    private final Bullet bullet;
    private final TileMap tileMap;
    private final List<Enemy> enemies;

    /**
     * Constructs BulletPhysics for the specified bullet.
     *
     * @param bullet  the bullet to check
     * @param tileMap map for collision
     * @param enemies enemies for collision
     */
    public BulletPhysics(Bullet bullet, TileMap tileMap, List<Enemy> enemies) {
        this.bullet = bullet;
        this.tileMap = tileMap;
        this.enemies = enemies;
    }

    /**
     * Checks if bullet collides with any map block.
     *
     * @return true if collision occurred
     */
    public boolean collidesMap() {
        return tileMap.collides(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
    }

    /**
     * Checks if bullet collides with any enemy.
     *
     * @return the enemy hit, or null if none
     */
    public Enemy collidesEnemy() {
        for (Enemy enemy : enemies) {
            if (CollisionUtils.overlaps(
                    bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight(),
                    enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight())) {
                return enemy;
            }
        }
        return null;
    }

    /**
     * Checks if bullet has exceeded its maximum travel distance.
     *
     * @return true if distance exceeded
     */
    public boolean exceededDistance() {
        int dx = Math.abs(bullet.getX() - bullet.getStartX());
        return dx >= bullet.getMaxDistance();
    }
}
