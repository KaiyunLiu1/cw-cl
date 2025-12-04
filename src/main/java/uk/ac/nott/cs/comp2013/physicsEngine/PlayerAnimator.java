package uk.ac.nott.cs.comp2013.physicsEngine;

import javafx.scene.image.Image;
import uk.ac.nott.cs.comp2013.model.AnimationState;
import uk.ac.nott.cs.comp2013.model.Player;
import uk.ac.nott.cs.comp2013.view.PlayerView;

/**
 * Updates the logical animation state and chooses the correct sprite.
 */
public final class PlayerAnimator {

    private final Player player;
    private final PlayerView.PlayerAssets assets;

    private long lastFrameTime;
    private int frameIndex;

    public PlayerAnimator(Player player, PlayerView.PlayerAssets assets) {
        this.player = player;
        this.assets = assets;
    }

    public void updateState(boolean isMoving) {
        AnimationState newState = AnimationState.IDLE;
        if (player.isGrounded() && isMoving) {
            newState = AnimationState.RUNNING;
        }
        if (!player.isGrounded()) {
            newState = AnimationState.AERIAL;
        }
        if (player.isDamaged()) {
            newState = AnimationState.HURT;
        }
        if (player.isJustShot()) {
            newState = AnimationState.SHOOTING;
        }
        player.getState().setAnimationState(newState);
    }

    public void animate() {
        if (assets == null) {
            return;
        }
        switch (player.getState().getAnimationState()) {
            case IDLE -> animateArray(assets.getIdleSprites(), 250);
            case RUNNING, WALKING -> animateArray(assets.getRunningSprites(), 180);
            case AERIAL -> animateAerial();
            case HURT -> setFixedFrameIndex(hurtFrameIndex());
            case SHOOTING -> setFixedFrameIndex(0);
            default -> { }
        }
    }

    private void animateArray(Image[] sprites, long delayMs) {
        if (sprites == null || sprites.length == 0) {
            return;
        }
        long now = System.currentTimeMillis();
        if (now - lastFrameTime > delayMs) {
            frameIndex = (frameIndex + 1) % sprites.length;
            player.setAnimationFrame(frameIndex);
            lastFrameTime = now;
        }
    }

    private void animateAerial() {
        Image[] running = assets.getRunningSprites();
        Image[] idle = assets.getIdleSprites();

        if (running != null && running.length > 5) {
            player.setAnimationFrame(5);
        } else if (idle != null && idle.length > 0) {
            player.setAnimationFrame(0);
        }
    }

    private void setFixedFrameIndex(int idx) {
        player.setAnimationFrame(idx);
    }

    private int hurtFrameIndex() {
        Image[] hurt = assets.getHurtSprites();
        if (hurt != null && hurt.length > 1) {
            return 1;
        }
        return 0;
    }
}
