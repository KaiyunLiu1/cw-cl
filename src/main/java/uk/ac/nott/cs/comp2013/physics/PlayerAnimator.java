package uk.ac.nott.cs.comp2013.physics;

import uk.ac.nott.cs.comp2013.model.AnimationState;
import uk.ac.nott.cs.comp2013.model.Player;
import uk.ac.nott.cs.comp2013.utils.GameConstants;

/** Handles animation state and frame updates for the Player. */
public final class PlayerAnimator extends Animator<Player> {

    @Override
    public void updateState(Player player) {
        AnimationState newState = determineState(player);
        updateStateWithoutReset(player, newState);
    }

    @Override
    public void animate(Player player) {
        int frameIndex;
        switch (player.getState().getAnimationState()) {
            case IDLE:
                frameIndex = nextFrame(player, GameConstants.PLAYER_IDLE_FRAMES,
                        AnimationState.IDLE.getFrameDelayMs());
                break;
            case RUNNING:
            case WALKING:
                frameIndex = nextFrame(player, GameConstants.PLAYER_RUNNING_FRAMES,
                        AnimationState.RUNNING.getFrameDelayMs());
                break;
            case AERIAL:
                frameIndex = player.getAnimationFrame();
                break;
            case HURT:
                frameIndex = 1;
                break;
            case SHOOTING:
                frameIndex = 0;
                break;
            default:
                frameIndex = player.getAnimationFrame();
        }

        player.setAnimationFrame(frameIndex);
    }

    /**
     * Determines the appropriate animation state for the player.
     * Matches old Player.updateState() priority order exactly.
     */
    private AnimationState determineState(Player player) {
        if (player.isJustShot()) {
            return AnimationState.SHOOTING;
        }

        if (player.isDamaged()) {
            return AnimationState.HURT;
        }

        if (!player.isGrounded()) {
            return AnimationState.AERIAL;
        }

        if (player.isGrounded() && player.getSpeed() == 0) {
            return AnimationState.IDLE;
        }

        if (player.isGrounded() && player.getSpeed() != 0) {
            return AnimationState.RUNNING;
        }

        return AnimationState.IDLE;
    }
}
