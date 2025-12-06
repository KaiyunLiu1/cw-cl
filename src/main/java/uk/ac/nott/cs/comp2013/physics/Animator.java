package uk.ac.nott.cs.comp2013.physics;

import uk.ac.nott.cs.comp2013.model.AnimationState;
import uk.ac.nott.cs.comp2013.model.Entity;

/** Abstract base class for entity animators.*/
public abstract class Animator<T extends Entity> {

    public abstract void updateState(T entity);
    public abstract void animate(T entity);

    /** Calculates the next animation frame index.*/
    protected int nextFrame(T entity, int spriteCount, long delayMs) {
        if (spriteCount <= 0) {
            return entity.getAnimationFrame();
        }

        long now = System.currentTimeMillis();
        long elapsed = now - entity.getAnimationTimestamp();

        if (elapsed > delayMs) {
            int currentFrame = entity.getAnimationFrame();
            currentFrame++;
            if (currentFrame >= spriteCount) {
                currentFrame = 0;
            }
            entity.setAnimationTimestamp(now);
            return currentFrame;
        }

        return entity.getAnimationFrame();
    }

    /**
     * Resets player's animation when state changes.
     *
     * @param entity   the entity to reset
     * @param newState the new animation state
     */
    protected void resetAnimationIfStateChanged(T entity, AnimationState newState) {
        if (entity.getState().getAnimationState() != newState) {
            entity.setAnimationFrame(0);
            entity.setAnimationTimestamp(System.currentTimeMillis());
        }
        entity.getState().setAnimationState(newState);
    }

    /**
     * Updates enemies state without resetting animation frame.
     *
     * @param entity   the entity to update
     * @param newState the new animation state
     */
    protected void updateStateWithoutReset(T entity, AnimationState newState) {
        entity.getState().setAnimationState(newState);
    }
}