package uk.ac.nott.cs.comp2013.physicsEngine;

import uk.ac.nott.cs.comp2013.model.Player;
import uk.ac.nott.cs.comp2013.model.TileMap;

/**
 * Handles horizontal movement input for the player.
 */
public final class PlayerMovement {

    private static final int MAX_SPEED = 5;

    private final Player player;

    public PlayerMovement(Player player) {
        this.player = player;
    }

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

    private void adjustSpeed(int direction) {
        int candidateSpeed = player.getSpeed() + direction;
        if (candidateSpeed > MAX_SPEED) {
            candidateSpeed = MAX_SPEED;
        }
        if (candidateSpeed < -MAX_SPEED) {
            candidateSpeed = -MAX_SPEED;
        }
        player.setSpeed(candidateSpeed);
    }
}
