package uk.ac.nott.cs.comp2013.controller;

import uk.ac.nott.cs.comp2013.model.Bullet;
import uk.ac.nott.cs.comp2013.model.Player;
import uk.ac.nott.cs.comp2013.model.TileMap;
import uk.ac.nott.cs.comp2013.physicsEngine.PlayerAnimator;
import uk.ac.nott.cs.comp2013.physicsEngine.PlayerCombat;
import uk.ac.nott.cs.comp2013.physicsEngine.PlayerMovement;
import uk.ac.nott.cs.comp2013.physicsEngine.PlayerPhysics;

import java.util.List;

/**
 * Coordinates all player-related behaviours: physics, movement, combat and animation.
 */
public class PlayerController {

    private final Player player;
    private final PlayerPhysics physics;
    private final PlayerMovement movement;
    private final PlayerCombat combat;
    private final PlayerAnimator animator;

    private boolean moveLeft;
    private boolean moveRight;
    private boolean jumpRequested;
    private boolean shootRequested;

    public PlayerController(Player player,
                            PlayerPhysics physics,
                            PlayerMovement movement,
                            PlayerCombat combat,
                            PlayerAnimator animator) {
        this.player = player;
        this.physics = physics;
        this.movement = movement;
        this.combat = combat;
        this.animator = animator;
    }

    public void setMoveLeft(boolean moveLeft) { this.moveLeft = moveLeft; }
    public void setMoveRight(boolean moveRight) { this.moveRight = moveRight; }
    public void setJumpRequested(boolean jumpRequested) { this.jumpRequested = jumpRequested; }
    public void setShootRequested(boolean shootRequested) { this.shootRequested = shootRequested; }

    public void update(TileMap map, List<Bullet> bullets) {
        physics.applyGravity(map);
        movement.advance(map);
        applyMovementInput();
        handleShooting(bullets);

        combat.refreshCooldowns();
        animator.updateState(moveLeft || moveRight);
        animator.animate();
    }

    private void applyMovementInput() {
        if (moveLeft && !moveRight) {
            movement.accelerateLeft();
        } else if (moveRight && !moveLeft) {
            movement.accelerateRight();
        } else {
            movement.halt();
        }
        if (jumpRequested) {
            physics.jump();
            jumpRequested = false;
        }
    }

    private void handleShooting(List<Bullet> bullets) {
        if (shootRequested) {
            combat.shoot(bullets);
        }
    }

    public void snapToGround(TileMap map) {
        physics.snapToGround(map);
    }

    public void resetAfterRespawn(TileMap map) {
        player.respawnAtStart();
        combat.resetAfterRespawn();
        snapToGround(map);
    }
}
