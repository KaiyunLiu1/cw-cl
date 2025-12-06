package uk.ac.nott.cs.comp2013.physics;

import uk.ac.nott.cs.comp2013.model.AnimationState;
import uk.ac.nott.cs.comp2013.model.Enemy;
import uk.ac.nott.cs.comp2013.utils.GameConstants;

/** Handles animation state and frame updates for enemies.*/
public final class EnemyAnimator extends Animator<Enemy> {

    @Override
    public void updateState(Enemy enemy) {
        AnimationState newState = determineState(enemy);
        updateStateWithoutReset(enemy, newState);
    }

    @Override
    public void animate(Enemy enemy) {
        int frameIndex;
        switch (enemy.getState().getAnimationState()) {
            case IDLE:
                frameIndex = nextFrame(enemy, GameConstants.ENEMY_IDLE_FRAMES,
                        AnimationState.IDLE.getFrameDelayMs());
                break;
            case WALKING:
                frameIndex = nextFrame(enemy, GameConstants.ENEMY_WALKING_FRAMES,
                        AnimationState.WALKING.getFrameDelayMs());
                break;
            case RUNNING:
                frameIndex = nextFrame(enemy, GameConstants.ENEMY_RUNNING_FRAMES,
                        AnimationState.RUNNING.getFrameDelayMs());
                break;
            case AERIAL:
                frameIndex = enemy.getAnimationFrame(); // View handles aerial sprite selection
                break;
            case HURT:
                frameIndex = 0;
                break;
            default:
                frameIndex = enemy.getAnimationFrame();
        }

        enemy.setAnimationFrame(frameIndex);
    }

    /** Determines the appropriate animation state for the enemy.*/
    private AnimationState determineState(Enemy enemy) {
        if (enemy.isDamaged()) {
            return AnimationState.HURT;
        }
        if (!enemy.getState().isGrounded()) {
            return AnimationState.AERIAL;
        }
        boolean isMoving = (enemy.getSpeed() != 0);
        if (enemy.getState().isGrounded() && isMoving && enemy.isRunning()) {
            return AnimationState.RUNNING;
        }
        if (enemy.getState().isGrounded() && isMoving) {
            return AnimationState.WALKING;
        }
        if (enemy.getState().isGrounded() && !isMoving) {
            return AnimationState.IDLE;
        }
        return AnimationState.IDLE;
    }
}
