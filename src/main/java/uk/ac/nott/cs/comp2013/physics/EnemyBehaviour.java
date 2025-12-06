package uk.ac.nott.cs.comp2013.physics;

import uk.ac.nott.cs.comp2013.model.Enemy;
import uk.ac.nott.cs.comp2013.model.Player;
import uk.ac.nott.cs.comp2013.model.TileMap;
import uk.ac.nott.cs.comp2013.utils.CollisionUtils;
import uk.ac.nott.cs.comp2013.utils.GameConstants;

/**
 * Controls enemy AI behavior.
 * This class replicates the exact logic from the old Enemy.doBehavior() method.
 */
public final class EnemyBehaviour {

    private final EnemyAnimator animator;

    public EnemyBehaviour(EnemyAnimator animator) {
        this.animator = animator;
    }

    public void update(Enemy enemy,
                       Player player,
                       TileMap tileMap,
                       PlayerBehaviour playerBehaviour) {

        double distanceFromPlayer = enemy.distanceFrom(player);
        applyGravity(enemy, tileMap);

        boolean inRange = inDetectionRange(enemy, player, distanceFromPlayer);
        double maxSpeed = GameConstants.ENEMY_PATROL_SPEED;

        if (inRange) {
            int candidateX = enemy.getX() + enemy.getSpeed();
            boolean blocked = tileMap.collides(candidateX, enemy.getY(),
                    enemy.getWidth(), enemy.getHeight());

            if (!blocked) {
                enemy.setPosition(candidateX, enemy.getY());

                if (distanceFromPlayer <= GameConstants.ENEMY_RUN_RADIUS) {
                    maxSpeed = GameConstants.ENEMY_RUN_SPEED;
                    enemy.setRunning(true);
                }

                if (player.getX() < enemy.getX() && enemy.getSpeed() > -maxSpeed) {
                    enemy.setSpeed(enemy.getSpeed() - 1);
                    enemy.setFacingForward(false);
                }
                if (player.getX() > enemy.getX() && enemy.getSpeed() < maxSpeed) {
                    enemy.setSpeed(enemy.getSpeed() + 1);
                    enemy.setFacingForward(true);
                } else if (Math.abs(enemy.getX() - player.getX()) < GameConstants.ENEMY_STOP_DISTANCE) {
                    enemy.setSpeed(0);
                }
            }

            if (enemy.getState().isGrounded()) {
                enemy.setJumpCounter(0);
            }

            boolean shouldJump = ((!enemy.getState().isGrounded()) ||
                    (distanceFromPlayer <= GameConstants.ENEMY_RUN_RADIUS
                            && Math.abs(enemy.getX() - player.getX()) > GameConstants.ENEMY_JUMP_HORIZONTAL_THRESHOLD))
                    && enemy.getJumpCounter() < 1
                    && player.getY() < enemy.getY();
            if (shouldJump) {
                enemy.setVelocityY(GameConstants.ENEMY_JUMP_VELOCITY);
                enemy.setJumpCounter(enemy.getJumpCounter() + 1);
            }

            if (collidesPlayer(enemy, player)) {
                playerBehaviour.damageFrom(enemy);
            }
        } else {
            resetOutOfRange(enemy);
        }

        applyDamageAndDeath(enemy);
        animator.updateState(enemy);
        animator.animate(enemy);
    }

    /** Applies gravity to the enemy.*/
    private void applyGravity(Enemy enemy, TileMap tileMap) {

        double nextY = enemy.getY() + enemy.getVelocityY();
        if (!tileMap.collides(enemy.getX(), nextY, enemy.getWidth(), enemy.getHeight())) {
            enemy.setPosition(enemy.getX(), nextY);
            enemy.setVelocityY(enemy.getVelocityY() + enemy.getAcceleration());
            enemy.markGrounded(false);
        } else {
            if (enemy.getVelocityY() > 1.5) {
                enemy.setVelocityY(enemy.getVelocityY() / 1.5);
            }
            else if (enemy.getVelocityY() < 0) {
                enemy.setVelocityY(-(enemy.getVelocityY() / 4));
            }
            else {
                enemy.markGrounded(true);
            }
        }
    }

    private boolean inDetectionRange(Enemy enemy, Player player, double distanceFromPlayer) {
        return distanceFromPlayer <= GameConstants.ENEMY_DETECTION_RADIUS
                && enemy.getY() - player.getY() < GameConstants.ENEMY_VERTICAL_DETECTION_DELTA;
    }

    private void resetOutOfRange(Enemy enemy) {
        enemy.setSpeed(0);
        enemy.setRunning(false);
    }

    private void applyDamageAndDeath(Enemy enemy) {
        if (enemy.getHealth() <= 0 || enemy.getY() > GameConstants.VOID_Y) {
            enemy.setHealth(0);
        }

        if (enemy.isDamaged()) {
            enemy.setSpeed((int) (enemy.getSpeed() / GameConstants.ENEMY_DAMAGE_SLOW_FACTOR));
        }

        if (enemy.isDamaged() &&
                (System.currentTimeMillis() - enemy.getDamagedTime()) > GameConstants.ENEMY_DAMAGE_COOLDOWN_MS) {
            enemy.setDamaged(false);
        }
    }

    /** Checks if this enemy collides with the player.*/
    private boolean collidesPlayer(Enemy enemy, Player player) {
        return CollisionUtils.overlaps(
                enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight(),
                player.getX(), player.getY(), player.getWidth(), player.getHeight());
    }
}
