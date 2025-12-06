package uk.ac.nott.cs.comp2013.model;

import uk.ac.nott.cs.comp2013.model.Bullet;
import uk.ac.nott.cs.comp2013.model.Enemy;
import uk.ac.nott.cs.comp2013.model.Player;
import uk.ac.nott.cs.comp2013.utils.GameConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for creating game entities.
 *
 * <p> It encapsulates spawn positions and default values, making it easy
 * to modify entity initialization.</p> */
public final class EntityFactory {

    private static final int DEFAULT_ENEMY_HEALTH = 2;

    private static Dimension playerDim;
    private static Dimension enemyDim;
    private static Dimension bulletDim;

    /** Enemy spawn positions  */
    private static final int[][] ENEMY_SPAWN_POSITIONS = {
            {1475, 230},
            {2570, 196},
            {2750, 320},
            {3060, 470},
            {4219, 100},
            {4900, 530},
            {4970, 530},
            {5040, 539},
            {6397, 196},
            {6540, 520},
            {6600, 520},
            {6660, 520},
            {6720, 520}
    };

    /**
     * Private constructor to prevent instantiation.
     */
    private EntityFactory() {
    }

    /**
     * Creates a new Player at the default spawn position.
     *
     * @return new Player instance
     */
    public static Player createPlayer() {
        Dimension dim = ensureConfigured(playerDim, "Player");
        return new Player(GameConstants.PLAYER_SPAWN_X, GameConstants.PLAYER_SPAWN_Y, dim.width(), dim.height());
    }

    /**
     * Creates all enemies for the level at their spawn positions.
     *
     * @return list of Enemy instances
     */
    public static List<Enemy> createAllEnemies() {
        List<Enemy> enemies = new ArrayList<>();
        for (int[] pos : ENEMY_SPAWN_POSITIONS) {
            enemies.add(createEnemy(pos[0], pos[1]));
        }
        return enemies;
    }

    /**
     * Creates a single Enemy at the specified position.
     *
     * @param x spawn X coordinate
     * @param y spawn Y coordinate
     * @return new Enemy instance
     */
    public static Enemy createEnemy(int x, int y) {
        Dimension dim = ensureConfigured(enemyDim, "Enemy");
        return new Enemy(x, y, DEFAULT_ENEMY_HEALTH, dim.width(), dim.height());
    }

    /**
     * Sets player dimensions from the loaded sprite.
     *
     * @param width  sprite width in pixels
     * @param height sprite height in pixels
     */
    public static void setPlayerDimensions(int width, int height) {
        playerDim = createDimension("Player", width, height);
    }

    /** Sets enemy heights and width from the loaded sprites. */
    public static void setEnemyDimensions(int width, int height) {
        enemyDim = createDimension("Enemy", width, height);
    }

    /** Sets bullet width and height from the loaded sprite.*/
    public static void setBulletDimensions(int width, int height) {
        bulletDim = createDimension("Bullet", width, height);
    }

    /** Creates a new empty bullet list.*/
    public static List<Bullet> createBulletList() {
        return new ArrayList<>();
    }

    /**
     * Creates a Bullet at the specified position with direction.
     *
     * @param x             spawn X coordinate
     * @param y             spawn Y coordinate
     * @param facingForward true for rightward bullet, false for leftward
     * @return new Bullet instance with appropriate speed
     */
    public static Bullet createBullet(int x, int y, boolean facingForward) {
        Dimension dim = ensureConfigured(bulletDim, "Bullet");

        Bullet bullet = new Bullet(x, y, dim.width(), dim.height());
        bullet.setSpeed(facingForward ? GameConstants.BULLET_SPEED : -GameConstants.BULLET_SPEED);
        bullet.setStartPoint(x, y);
        return bullet;
    }

    /**
     * Creates a Bullet fired from a Player.
     *
     * @param player the player firing the bullet
     * @return new Bullet instance
     */
    public static Bullet createBulletFromPlayer(Player player) {
        int bulletX = player.isFacingForwards() ? player.getX() + 47 : player.getX() - 25;
        int bulletY = (int) player.getY() + 10;
        return createBullet(bulletX, bulletY, player.isFacingForwards());
    }

    private static Dimension createDimension(String label, int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException(label + " dimensions must be positive.");
        }
        return new Dimension(width, height);
    }

    private static Dimension ensureConfigured(Dimension dim, String label) {
        if (dim == null) {
            throw new IllegalStateException(label + " dimensions not configured. Call set" + label + "Dimensions() during initialization.");
        }
        return dim;
    }

    private record Dimension(int width, int height) { }
}
