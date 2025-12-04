package uk.ac.nott.cs.comp2013.physicsEngine;

import javafx.scene.image.Image;
import uk.ac.nott.cs.comp2013.model.AnimationState;
import uk.ac.nott.cs.comp2013.model.Enemy;
import uk.ac.nott.cs.comp2013.view.EnemyAssets;

/**
 * Calculates enemy animation state and frame index; view layer selects the actual sprite.
 */
public final class EnemyAnimator {

    private final EnemyAssets assets;

    public EnemyAnimator(EnemyAssets assets) {
        this.assets = assets;
    }

    public void updateState(Enemy enemy) {
        AnimationState current = enemy.getState().getAnimationState();
        AnimationState newState = AnimationState.IDLE;
        if (enemy.getState().isGrounded() && enemy.getSpeed() != 0) {
            enemy.markJustMoved(System.currentTimeMillis());
            newState = enemy.isRunning() ? AnimationState.RUNNING : AnimationState.WALKING;
        } else if (!enemy.getState().isGrounded()) {
            newState = AnimationState.AERIAL;
        }
        if (enemy.isDamaged()) {
            newState = AnimationState.HURT;
        }
        if (newState != current) {
            enemy.setAnimationFrame(0);
            enemy.setAnimationTimestamp(System.currentTimeMillis());
        }
        enemy.getState().setAnimationState(newState);
    }

    public void animate(Enemy enemy) {
        if (assets == null) {
            return;
        }
        int frameIndex = enemy.getAnimationFrame();
        switch (enemy.getState().getAnimationState()) {
            case IDLE -> frameIndex = nextFrame(enemy, assets.getIdleSprites(), 250);
            case WALKING -> frameIndex = nextFrame(enemy, assets.getWalkingSprites(), 180);
            case RUNNING -> frameIndex = nextFrame(enemy, assets.getRunningSprites(), 180);
            case AERIAL -> frameIndex = aerialFrameIndex(enemy);
            case HURT -> frameIndex = 0;
            default -> { }
        }
        enemy.setAnimationFrame(frameIndex);
    }

    private int nextFrame(Enemy enemy, Image[] sprites, long delayMs) {
        if (sprites == null || sprites.length == 0) {
            return enemy.getAnimationFrame();
        }
        long now = System.currentTimeMillis();
        if (now - enemy.getAnimationTimestamp() > delayMs) {
            int frame = (enemy.getAnimationFrame() + 1) % sprites.length;
            enemy.setAnimationTimestamp(now);
            return frame;
        }
        return enemy.getAnimationFrame();
    }

    private int aerialFrameIndex(Enemy enemy) {
        Image[] running = assets != null ? assets.getRunningSprites() : null;
        if (enemy.getVelocityY() < 0 && running != null && running.length > 4) {
            return Math.min(4, running.length - 1);
        }
        return 0;
    }
}
