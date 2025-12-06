package uk.ac.nott.cs.comp2013.controller;

import uk.ac.nott.cs.comp2013.model.EntityFactory;
import uk.ac.nott.cs.comp2013.model.AnimationState;
import uk.ac.nott.cs.comp2013.model.Enemy;
import uk.ac.nott.cs.comp2013.model.Player;
import uk.ac.nott.cs.comp2013.model.TileMap;
import uk.ac.nott.cs.comp2013.physics.EnemyBehaviour;
import uk.ac.nott.cs.comp2013.physics.PlayerBehaviour;
import java.util.Iterator;
import java.util.List;

/** Manages enemy behaviours and lifecycle.*/
public class EnemyController {

    private final EnemyBehaviour enemyBehaviour;

    public EnemyController(EnemyBehaviour enemyBehaviour) {
        this.enemyBehaviour = enemyBehaviour;
    }

    /**
     * Advances all enemies by one frame, applying behaviours and removing dead ones.
     *
     * @param enemies         active enemies
     * @param player          the player for interactions
     * @param tileMap         tiles for collisions
     * @param playerBehaviour player behaviour to apply damage
     */
    public void update(List<Enemy> enemies,
                       Player player,
                       TileMap tileMap,
                       PlayerBehaviour playerBehaviour) {
        Iterator<Enemy> iterator = enemies.iterator();

        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            enemyBehaviour.update(enemy, player, tileMap, playerBehaviour);

            if (!enemy.isAlive()) {
                iterator.remove();
                player.setAmmo(player.getAmmo() + 2);
            }
        }
    }

    /** Resets enemies to initial spawn positions and animation state.*/
    public void resetEnemies(List<Enemy> enemies, TileMap tileMap) {
        enemies.clear();
        enemies.addAll(EntityFactory.createAllEnemies());

        long now = System.currentTimeMillis();
        for (Enemy enemy : enemies) {
            snapToGround(enemy, tileMap);

            enemy.getState().setAnimationState(AnimationState.IDLE);
            enemy.getState().setFacingForward(false);
            enemy.getState().setGrounded(false);

            enemy.setAnimationFrame(0);
            enemy.setAnimationTimestamp(now);

            enemy.setRunning(false);
            enemy.markJustMoved(now);
            enemy.setSpeed(0);
            enemy.setJumpCounter(0);

            enemy.setDamaged(false);
        }
    }

    /** Simulates gravity to place enemies on the nearest floor at beginning. */
    private void snapToGround(Enemy enemy, TileMap tileMap) {
        int maxY = tileMap.getBlocks().stream()
                .mapToInt(block -> block.getY() + block.getHeight())
                .max()
                .orElse(0);

        double y = enemy.getY();
        int iterations = 0;
        while (!tileMap.collides(enemy.getX(), y + 1, enemy.getWidth(), enemy.getHeight())
                && y + enemy.getHeight() < maxY
                && iterations < 5000) {
            y += 1;
            iterations++;
        }
        enemy.setPosition(enemy.getX(), y);
    }

    /** Initial enemies to idle animation state. */
    public void primeIdle(List<Enemy> enemies) {
        long now = System.currentTimeMillis();

        for (Enemy enemy : enemies) {
            enemy.getState().setAnimationState(AnimationState.IDLE);
            enemy.setAnimationFrame(0);
            enemy.setAnimationTimestamp(now);
            enemy.setRunning(false);
            enemy.markJustMoved(now);
            enemy.setSpeed(0);
        }
    }
}
