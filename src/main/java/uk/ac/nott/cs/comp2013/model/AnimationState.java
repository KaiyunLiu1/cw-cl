package uk.ac.nott.cs.comp2013.model;

/**
 * Enumerates the high-level animation states used by game entities.
 *
 * <p>This enum replaces magic strings with type-safe constants and supports
 * the Strategy pattern by encapsulating animation timing parameters.</p>
 */
public enum AnimationState {

    IDLE(250),
    WALKING(180),
    RUNNING(180),
    AERIAL(0),
    HURT(0),
    SHOOTING(0);

    /** Animation frame delay in milliseconds. */
    private final long frameDelayMs;

    /**
     * Constructs an AnimationState with the specified frame delay.
     *
     * @param frameDelayMs delay between animation frames in milliseconds
     */
    AnimationState(long frameDelayMs) {
        this.frameDelayMs = frameDelayMs;
    }

    /**
     * Returns the animation frame delay for this state.
     *
     * @return frame delay in milliseconds
     */
    public long getFrameDelayMs() {
        return frameDelayMs;
    }

    /**
     * Checks if this state uses animated frames.
     *
     * @return true if animated, false if static
     */
    public boolean isAnimated() {
        return frameDelayMs > 0;
    }
}