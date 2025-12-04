package uk.ac.nott.cs.comp2013.model;

/**
 * Immutable-like data object capturing the high-level status of an entity.
 * <p>
 * The state describes which animation should be rendered as well as the last
 * known facing direction and grounded flag. Keeping it as a separate class
 * proves handy when the view components only need read-only access to
 * presentation data.
 * </p>
 */
public class EntityState {

    private boolean facingForward;
    private AnimationState animationState;
    private boolean grounded;

    public EntityState(boolean facingForward, AnimationState animationState) {
        this.facingForward = facingForward;
        this.animationState = animationState;
    }

    public boolean isFacingForward() {
        return facingForward;
    }

    public void setFacingForward(boolean facingForward) {
        this.facingForward = facingForward;
    }

    public AnimationState getAnimationState() {
        return animationState;
    }

    public void setAnimationState(AnimationState animationState) {
        this.animationState = animationState;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }
}
