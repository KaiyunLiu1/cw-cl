package uk.ac.nott.cs.comp2013.model;

import java.util.List;

/**
 * Central container for all game state following the MVC pattern.
 *
 * <p>This class aggregates all game objects (player, enemies, bullets,
 * and map) into a single accessible unit.</p>
 */
public final class GameModel {

    private final Player player;
    private final List<Enemy> enemies;
    private final List<Bullet> bullets;
    private final TileMap tileMap;
    private int deathCounter;

    /**
     * Constructs a GameModel with the specified components.
     *
     * @param player  the player entity
     * @param enemies list of enemy entities
     * @param bullets list of bullet entities
     * @param tileMap the level tile map
     */
    public GameModel(Player player,
                     List<Enemy> enemies,
                     List<Bullet> bullets,
                     TileMap tileMap) {
        this.player = player;
        this.enemies = enemies;
        this.bullets = bullets;
        this.tileMap = tileMap;
    }

    /** The following is getter/setter section. */
    public Player getPlayer() {
        return player;
    }
    public List<Enemy> getEnemies() {
        return enemies;
    }
    public List<Bullet> getBullets() {
        return bullets;
    }
    public TileMap getTileMap() {
        return tileMap;
    }


    public void incrementDeathCounter() {
        deathCounter++;
    }
    public void resetDeathCounter() {
        deathCounter = 0;
    }
    public int getDeathCounter() {
        return deathCounter;
    }
}
