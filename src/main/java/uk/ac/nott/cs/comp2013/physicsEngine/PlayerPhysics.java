package uk.ac.nott.cs.comp2013.physicsEngine;

import uk.ac.nott.cs.comp2013.model.Player;
import uk.ac.nott.cs.comp2013.model.TileMap;

/**
 * Encapsulates gravity, jump handling and ground snapping for the player.
 */
public final class PlayerPhysics {

    private static final double MAX_FALL_SPEED = 14;
    private static final double FIRST_JUMP_VELOCITY = -12;//-8
    private static final double DOUBLE_JUMP_VELOCITY = -10;//-6
    private static final double ACCELERATION = 0.5;

    private final Player player;

    public PlayerPhysics(Player player) {
        this.player = player;
        player.setAcceleration(ACCELERATION);
    }

    public void applyGravity(TileMap map) {
        // If resting on solid ground with no vertical movement, keep idle/grounded.
        if (map.collides(player.getX(), player.getY() + 1, player.getWidth(), player.getHeight())
                && Math.abs(player.getVelocityY()) < 0.0001) {
            player.setGrounded(true);
            player.setVelocityY(0);
            return;
        }

        double nextY = player.getY() + player.getVelocityY();
        if (!map.collides(player.getX(), nextY, player.getWidth(), player.getHeight())) {
            player.setPosition(player.getX(), nextY);
            double newVelocity = Math.min(player.getVelocityY() + player.getAcceleration(), MAX_FALL_SPEED);
            player.setVelocityY(newVelocity);
            player.setGrounded(false);
        } else {
            resolveGroundCollision(map);
        }
    }

    private void resolveGroundCollision(TileMap map) {
        double correctedY = player.getY();
        int guard = 0;
        while (map.collides(player.getX(), correctedY, player.getWidth(), player.getHeight()) && guard < 2000) {
            correctedY -= 1;
            guard++;
        }
        player.setPosition(player.getX(), correctedY);

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

    public void snapToGround(TileMap map) {
        double targetY = player.getY();
        int guard = 0;
        while (map.collides(player.getX(), targetY, player.getWidth(), player.getHeight()) && guard < 2000) {
            targetY -= 1;
            guard++;
        }
        while (!map.collides(player.getX(), targetY + 1, player.getWidth(), player.getHeight()) && guard < 4000) {
            targetY += 1;
            guard++;
        }
        player.setPosition(player.getX(), targetY);
        player.setVelocityY(0);
        player.setGrounded(true);
        player.setJumpCounter(0);
    }

    public void jump() {
        if (player.getJumpCounter() >= 2) {
            return;
        }

        double velocity = (player.getJumpCounter() == 0) ? FIRST_JUMP_VELOCITY : DOUBLE_JUMP_VELOCITY;
        player.setVelocityY(velocity);
        player.setJumpCounter(player.getJumpCounter() + 1);
        player.setGrounded(false);
        player.setJumpStartX(player.getX());
        player.setJumpStartY((int) player.getY());
    }
}
