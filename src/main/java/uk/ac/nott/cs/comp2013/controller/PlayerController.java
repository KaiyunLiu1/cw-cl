package uk.ac.nott.cs.comp2013.controller;

import uk.ac.nott.cs.comp2013.model.Bullet;
import uk.ac.nott.cs.comp2013.model.Player;
import uk.ac.nott.cs.comp2013.model.TileMap;
import uk.ac.nott.cs.comp2013.physics.PlayerAnimator;
import uk.ac.nott.cs.comp2013.physics.PlayerBehaviour;

import java.util.List;

/**
 * Coordinates all player-related behaviors.
 *
 * <p>This controller takes responsible for player physics, movement, shoot,
 * and animation.</p>
 */
public class PlayerController {

    private final Player player;
    private final PlayerBehaviour behaviour;
    private final PlayerAnimator animator;
    private boolean moveLeft;
    private boolean moveRight;
    private boolean jumpRequested;
    private boolean shootRequested;

    /**
     * Constructs a PlayerController with all required handlers.
     *
     * @param player   the player entity
     * @param behaviour combined behaviour handler
     * @param animator animation handler
     */
    public PlayerController(Player player,
                            PlayerBehaviour behaviour,
                            PlayerAnimator animator) {
        this.player = player;
        this.behaviour = behaviour;
        this.animator = animator;
    }

    /**
     * Sets left movement input state.
     *
     * @param moveLeft true if moving left
     */
    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    /**
     * Sets right movement input state.
     *
     * @param moveRight true if moving right
     */
    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    /**
     * Sets jump input state.
     *
     * @param jumpRequested true if jump requested
     */
    public void setJumpRequested(boolean jumpRequested) {
        this.jumpRequested = jumpRequested;
    }

    /**
     * Sets shoot input state.
     *
     * @param shootRequested true if shoot requested
     */
    public void setShootRequested(boolean shootRequested) {
        this.shootRequested = shootRequested;
    }


    /**
     * Updates the player for one frame.
     *
     * @param map     tile map for collision
     * @param bullets bullet list for shooting
     */
    public void update(TileMap map, List<Bullet> bullets) {
        behaviour.applyGravity(map);
        behaviour.advance(map);
        applyMovementInput();
        handleShooting(bullets);

        behaviour.refreshCooldowns();
        animator.updateState(player);
        animator.animate(player);
    }

    /**
     * Applies movement input to player.
     */
    private void applyMovementInput() {
        if (moveLeft && !moveRight) {
            behaviour.accelerateLeft();
        } else if (moveRight && !moveLeft) {
            behaviour.accelerateRight();
        } else {
            behaviour.halt();
        }

        if (jumpRequested) {
            behaviour.jump();
            jumpRequested = false;
        }
    }

    /**
     * Handles shooting input.
     */
    private void handleShooting(List<Bullet> bullets) {
        if (shootRequested) {
            behaviour.shoot(bullets);
        }
    }

    /** Snaps player to ground at current position.*/
    public void snapToGround(TileMap map) {
        behaviour.snapToGround(map);
    }

    /** Resets player after respawn.*/
    public void resetAfterRespawn(TileMap map) {
        behaviour.resetAfterRespawn(map);
    }
}
