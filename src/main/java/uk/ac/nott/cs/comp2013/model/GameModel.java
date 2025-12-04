package uk.ac.nott.cs.comp2013.model;

import java.util.List;

/**
 * Bundles all mutable game domain objects for easier wiring.
 */
public final class GameModel {

    private final Player player;
    private final List<Enemy> enemies;
    private final List<Bullet> bullets;
    private final TileMap tileMap;

    public GameModel(Player player,
                     List<Enemy> enemies,
                     List<Bullet> bullets,
                     TileMap tileMap) {
        this.player = player;
        this.enemies = enemies;
        this.bullets = bullets;
        this.tileMap = tileMap;
    }

    public Player getPlayer() { return player; }
    public List<Enemy> getEnemies() { return enemies; }
    public List<Bullet> getBullets() { return bullets; }
    public TileMap getTileMap() { return tileMap; }
}
