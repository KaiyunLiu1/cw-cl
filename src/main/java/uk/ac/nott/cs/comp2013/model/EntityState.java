package uk.ac.nott.cs.comp2013.model;

/** Represents the current state of an entity for animation and rendering purposes.*/
public class EntityState {

    /** Whether the entity is facing right (forward). */
    private boolean facingForward;
    private AnimationState animationState;
    private boolean grounded;

    public EntityState(boolean facingForward, AnimationState animationState) {
        this.facingForward = facingForward;
        this.animationState = animationState;
        this.grounded = false;
    }

    /** The following section contains setter and getter functions.*/
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