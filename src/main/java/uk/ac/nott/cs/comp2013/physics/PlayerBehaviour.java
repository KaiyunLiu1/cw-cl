package uk.ac.nott.cs.comp2013.physics;

import uk.ac.nott.cs.comp2013.model.Bullet;
import uk.ac.nott.cs.comp2013.model.Enemy;
import uk.ac.nott.cs.comp2013.model.EntityFactory;
import uk.ac.nott.cs.comp2013.model.Player;
import uk.ac.nott.cs.comp2013.model.TileMap;
import uk.ac.nott.cs.comp2013.utils.GameConstants;

import java.util.List;

/**
 * Combines player movement, physics, and shoot behaviours.
 */
public final class PlayerBehaviour {

    private static final int MAX_RESOLUTION_ITERATIONS = 2000;

    private final Player player;

    public PlayerBehaviour(Player player) {
        this.player = player;
        player.setAcceleration(GameConstants.GRAVITY);
    }

    /** Gravity and vertical collision handling. */
    public void applyGravity(TileMap map) {
        if (isRestingOnGround(map)) {
            player.setGrounded(true);
            player.setVelocityY(0);
            return;
        }

        double nextY = player.getY() + player.getVelocityY();

        if (!map.collides(player.getX(), nextY, player.getWidth(), player.getHeight())) {
            player.setPosition(player.getX(), nextY);
            double newVelocity = Math.min(
                    player.getVelocityY() + player.getAcceleration(),
                    GameConstants.MAX_FALL_SPEED
            );
            player.setVelocityY(newVelocity);
            player.setGrounded(false);
        } else {
            if (player.getVelocityY() > 1.5) {
                player.setVelocityY(player.getVelocityY() / 1.5);
            } else if (player.getVelocityY() < 0) {
                player.setVelocityY(-(player.getVelocityY() / 4));
            } else {
                player.setVelocityY(0);
            }
            player.setGrounded(true);
            player.setJumpCounter(0);
        }
    }

    /** Horizontal advance respecting collisions. */
    public void advance(TileMap map) {
        double nextX = player.getX() + player.getSpeed();

        if (!map.collides(nextX, player.getY(), player.getWidth(), player.getHeight())) {
            player.setPosition((int) nextX, player.getY());
        } else {
            player.setSpeed(player.getSpeed() / 2);
        }
    }

    public void accelerateLeft() {
        adjustSpeed(-1);
        player.setFacingForward(false);
    }

    public void accelerateRight() {
        adjustSpeed(1);
        player.setFacingForward(true);
    }

    public void halt() {
        player.setSpeed(0);
    }

    /** Double-jump capable jump. */
    public void jump() {
        if (player.getJumpCounter() >= 2) {
            return;
        }

        double velocity = (player.getJumpCounter() == 0)
                ? GameConstants.FIRST_JUMP_VELOCITY
                : GameConstants.DOUBLE_JUMP_VELOCITY;

        player.setVelocityY(velocity);
        player.setJumpCounter(player.getJumpCounter() + 1);
        player.setGrounded(false);
        player.setJumpStartX(player.getX());
        player.setJumpStartY((int) player.getY());
    }

    /** Aligns player to ground at current X. */
    public void snapToGround(TileMap map) {
        double y = player.getY();
        int iterations = 0;
        while (!map.collides(player.getX(), y + 1, player.getWidth(), player.getHeight())
                && iterations < MAX_RESOLUTION_ITERATIONS * 2) {
            y += 1;
            iterations++;
        }

        player.setPosition(player.getX(), y);
        player.setVelocityY(0);
        player.setGrounded(true);
        player.setJumpCounter(0);
    }

    /** Shooting with cooldown/ammo checks. */
    public void shoot(List<Bullet> bullets) {
        if (player.isJustShot() || player.getAmmo() <= 0) {
            return;
        }

        Bullet bullet = EntityFactory.createBulletFromPlayer(player);
        bullets.add(bullet);

        player.setAmmo(player.getAmmo() - 1);
        player.setJustShot(true);
        player.setLastShotTime(System.currentTimeMillis());
    }

    /** Damage intake with knockback and invulnerability window. */
    public void damageFrom(Enemy enemy) {
        if (player.isDamaged()) {
            return;
        }

        player.setHealth(player.getHealth() - 1);
        player.setDamaged(true);
        player.setVelocityY(-6);

        if (player.getX() < enemy.getX()) {
            player.setSpeed(-4);
        } else {
            player.setSpeed(4);
        }
    }

    /** Refreshes shot and damage cooldowns. */
    public void refreshCooldowns() {
        long now = System.currentTimeMillis();

        if (player.isDamaged() && (now - player.getDamagedTime()) > GameConstants.PLAYER_DAMAGE_COOLDOWN_MS) {
            player.setDamaged(false);
        }

        if (player.isJustShot() && (now - player.getLastShotTime()) > GameConstants.SHOOT_COOLDOWN_MS) {
            player.setJustShot(false);
        }
    }

    /** Resets combat/movement state after respawn and snaps to ground. */
    public void resetAfterRespawn(TileMap map) {
        player.respawnAtStart();
        player.setDamaged(false);
        player.setJustShot(false);
        player.setLastShotTime(0);
        snapToGround(map);
    }

    private void adjustSpeed(int direction) {
        int candidateSpeed = player.getSpeed() + direction;
        candidateSpeed = Math.max(-GameConstants.PLAYER_MAX_SPEED,
                Math.min(candidateSpeed, GameConstants.PLAYER_MAX_SPEED));
        player.setSpeed(candidateSpeed);
    }

    private boolean isRestingOnGround(TileMap map) {
        return map.collides(player.getX(), player.getY() + 1, player.getWidth(), player.getHeight())
                && Math.abs(player.getVelocityY()) < 0.0001;
    }

}
