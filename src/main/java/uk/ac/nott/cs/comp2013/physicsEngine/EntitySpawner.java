package uk.ac.nott.cs.comp2013.physicsEngine;

import uk.ac.nott.cs.comp2013.model.Bullet;
import uk.ac.nott.cs.comp2013.model.Enemy;
import uk.ac.nott.cs.comp2013.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory helpers that build initial entities for a level.
 * <p>
 * Keeps hard-coded spawn data in one place so controllers remain focused on
 * orchestration.
 * </p>
 */
public class EntitySpawner {

    private static final int ENEMY_HEALTH = 2;

    public static Player createPlayer() {
        return new Player(20, 300);
    }

    public static List<Enemy> createEnemies() {
        List<Enemy> list = new ArrayList<>();
        list.add(new Enemy(1475, 230, ENEMY_HEALTH));
        list.add(new Enemy(2570, 196, ENEMY_HEALTH));
        list.add(new Enemy(2750, 320, ENEMY_HEALTH));
        list.add(new Enemy(3060, 470, ENEMY_HEALTH));
        list.add(new Enemy(4219, 100, ENEMY_HEALTH));
        list.add(new Enemy(4900, 530, ENEMY_HEALTH));
        list.add(new Enemy(4970, 530, ENEMY_HEALTH));
        list.add(new Enemy(5040, 539, ENEMY_HEALTH));
        list.add(new Enemy(6397, 196, ENEMY_HEALTH));
        list.add(new Enemy(6540, 520, ENEMY_HEALTH));
        list.add(new Enemy(6600, 520, ENEMY_HEALTH));
        list.add(new Enemy(6660, 520, ENEMY_HEALTH));
        list.add(new Enemy(6720, 520, ENEMY_HEALTH));
        return list;
    }

    public static List<Bullet> createBulletList() {
        return new ArrayList<>();
    }
}
