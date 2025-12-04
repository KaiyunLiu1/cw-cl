package uk.ac.nott.cs.comp2013.physicsEngine;

import uk.ac.nott.cs.comp2013.model.Enemy;
import uk.ac.nott.cs.comp2013.model.Player;
import uk.ac.nott.cs.comp2013.model.TileMap;
import uk.ac.nott.cs.comp2013.utils.CollisionUtils;

/**
 * Encapsulates enemy behaviour (movement, chasing, jumping and animation).
 */
public final class EnemyAI {

    private static final double PATROL_SPEED = 1;
    private static final double RUN_SPEED = 3;
    private static final int DETECTION_RADIUS = 400;
    private static final int RUN_RADIUS = 200;

    private final EnemyAnimator animator;

    public EnemyAI(EnemyAnimator animator) {
        this.animator = animator;
    }

    public void update(Enemy enemy,
                       Player player,
                       TileMap tileMap,
                       PlayerCombat playerCombat) {
        double distance = enemy.distanceFrom(player);
        applyGravity(enemy, tileMap);

        if (distance <= DETECTION_RADIUS && (enemy.getY() - player.getY()) < 100) {
            double maxSpeed = distance <= RUN_RADIUS ? RUN_SPEED : PATROL_SPEED;
            enemy.setRunning(distance <= RUN_RADIUS);
            moveHorizontally(enemy, player, tileMap, maxSpeed);
            handleJump(enemy, player, distance);

            if (collidesPlayer(enemy, player)) {
                playerCombat.damageFrom(enemy);
            }
        } else {
            enemy.stop();
            enemy.setRunning(false);
        }

        if (enemy.getY() > 900) {
            enemy.setHealth(0);
        }
        if (enemy.getHealth() <= 0) {
            enemy.stop();
        }
        clearDamageCooldown(enemy);

        animator.updateState(enemy);
        animator.animate(enemy);
    }

    private void moveHorizontally(Enemy enemy,
                                  Player player,
                                  TileMap tileMap,
                                  double maxSpeed) {
        double candidateX = enemy.getX() + enemy.getSpeed();
        boolean blocked = tileMap.collides(candidateX, enemy.getY(), enemy.getWidth(), enemy.getHeight());

        if (!blocked) {
            enemy.setPosition((int) candidateX, enemy.getY());
            if (player.getX() < enemy.getX()) {
                if (enemy.getSpeed() > -maxSpeed) {
                    enemy.setSpeed(enemy.getSpeed() - 1);
                }
                enemy.setFacingForward(false);
            }
            if (player.getX() > enemy.getX()) {
                if (enemy.getSpeed() < maxSpeed) {
                    enemy.setSpeed(enemy.getSpeed() + 1);
                }
                enemy.setFacingForward(true);
            } else if (Math.abs(enemy.getX() - player.getX()) < 20) {
                enemy.setSpeed(0);
            }
        }
    }

    private void handleJump(Enemy enemy, Player player, double distance) {
        if (!enemy.getState().isGrounded()
                || (distance <= RUN_RADIUS && Math.abs(enemy.getX() - player.getX()) > 40)) {
            if (enemy.getJumpCounter() < 1 && player.getY() < enemy.getY()) {
                enemy.setVelocityY(-8);
                enemy.setJumpCounter(enemy.getJumpCounter() + 1);
            }
        } else {
            enemy.setJumpCounter(0);
        }
    }

    private void applyGravity(Enemy enemy, TileMap tileMap) {
        double nextY = enemy.getY() + enemy.getVelocityY();
        if (!tileMap.collides(enemy.getX(), nextY, enemy.getWidth(), enemy.getHeight())) {
            enemy.setPosition(enemy.getX(), nextY);
            enemy.setVelocityY(enemy.getVelocityY() + enemy.getAcceleration());
            enemy.markGrounded(false);
        } else {
            if (enemy.getVelocityY() > 1.5) {
                enemy.setVelocityY(enemy.getVelocityY() / 1.5);
            } else if (enemy.getVelocityY() < 0) {
                enemy.setVelocityY(-(enemy.getVelocityY() / 4));
            } else {
                enemy.setVelocityY(0);
            }
            enemy.markGrounded(true);
        }
    }

    private boolean collidesPlayer(Enemy enemy, Player player) {
        return CollisionUtils.overlaps(
                enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight(),
                player.getX(), player.getY(), player.getWidth(), player.getHeight());
    }

    private void clearDamageCooldown(Enemy enemy) {
        if (enemy.isDamaged() && (System.currentTimeMillis() - enemy.getDamagedTime()) > 300) {
            enemy.setDamaged(false);
        }
    }
}
