package uk.ac.nott.cs.comp2013.controller;

import uk.ac.nott.cs.comp2013.model.Enemy;
import uk.ac.nott.cs.comp2013.model.Player;
import uk.ac.nott.cs.comp2013.model.TileMap;
import uk.ac.nott.cs.comp2013.physicsEngine.EnemyAI;
import uk.ac.nott.cs.comp2013.physicsEngine.EntitySpawner;
import uk.ac.nott.cs.comp2013.physicsEngine.PlayerCombat;
import uk.ac.nott.cs.comp2013.model.AnimationState;

import java.util.Iterator;
import java.util.List;

/**
 * Handles enemy AI updates and lifecycle (death/removal/spawn).
 */
public class EnemyController {

    private final EnemyAI enemyAI;

    public EnemyController(EnemyAI enemyAI) {
        this.enemyAI = enemyAI;
    }

    public void update(List<Enemy> enemies,
                       Player player,
                       TileMap tileMap,
                       PlayerCombat playerCombat) {
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            enemyAI.update(enemy, player, tileMap, playerCombat);
            if (!enemy.isAlive()) {
                iterator.remove();
                player.setAmmo(player.getAmmo() + 2);
            }
        }
    }

    public void resetEnemies(List<Enemy> enemies, TileMap tileMap) {
        enemies.clear();
        enemies.addAll(EntitySpawner.createEnemies());
        setIdleAnimation(enemies);
    }

    /**
     * Primes enemies to the first idle frame (legacy start frame).
     */
    public void primeIdle(List<Enemy> enemies) {
        setIdleAnimation(enemies);
    }

    private void setIdleAnimation(List<Enemy> enemies) {
        long now = System.currentTimeMillis();
        for (Enemy enemy : enemies) {
            enemy.getState().setAnimationState(AnimationState.IDLE);
            enemy.setAnimationFrame(0);
            enemy.setAnimationTimestamp(now);
            enemy.setRunning(false);
            enemy.markJustMoved(now);
            enemy.stop();
        }
    }
}
