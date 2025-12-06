package uk.ac.nott.cs.comp2013.model;

/** Represents enemies' behaviour in the game.*/
public class Enemy extends Entity {

    /** Y coordinate threshold for void death. */
    private static final int VOID_Y = 900;
    /** Whether enemy is facing right. */
    private boolean facingForward;
    private boolean running;
    /** Timestamp of last movement for idle animation. */
    private long lastMoveTime;

    /**
     * Constructs an Enemy at the specified position with given health.
     *
     * @param x      initial X position
     * @param y      initial Y position
     * @param health initial health points
     * @param width  collision box width (from sprite)
     * @param height collision box height (from sprite)
     */
    public Enemy(int x, int y, int health, int width, int height) {
        super(x, y, width, height, health, 0);
        this.facingForward = false;
        this.lastMoveTime = System.currentTimeMillis();
        setAnimationFrame(0);
    }

    /**
     * Calculates the Euclidean distance from this enemy to the player.
     *
     * @param player the player to measure distance to
     * @return distance in pixels
     */
    public double distanceFrom(Player player) {
        int dx = player.getX() - x;
        double dy = player.getY() - y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /** Applies damage to this enemy, reducing health and triggering knockback.*/
    public void damage() {
        health--;
        isDamaged = true;
        damagedTime = System.currentTimeMillis();
        velocityY = -6; // Knockback effect
    }


    /**
     * Checks if the enemy is facing right.
     *
     * @return true if facing right, false if facing left
     */
    public boolean isFacingForwards() {
        return facingForward;
    }

    /** Sets the facing direction.*/
    public void setFacingForward(boolean facingForward) {
        this.facingForward = facingForward;
        state.setFacingForward(facingForward);
    }


    /**
     * Checks if the enemy is in running state.
     *
     * @return true if running, false if walking or idle
     */
    public boolean isRunning() {
        return running;
    }

    /** Sets the running state.*/
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Checks if the enemy is still alive.
     *
     * @return true if health greater than 0
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Checks if the enemy has fallen into the void.
     *
     * @return true if below void threshold
     */
    public boolean hasfallenIntoVoid() {
        return y > VOID_Y;
    }

    /** Sets the grounded state and resets jump counter if grounded */
    public void markGrounded(boolean grounded) {
        state.setGrounded(grounded);
        if (grounded) {
            jumpCounter = 0;
        }
    }

    /** Stops horizontal movement.*/
    public void stop() {
        speed = 0;
    }

    /** Returns the timestamp of last movement.*/
    public long getLastMoveTime() {
        return lastMoveTime;
    }

    /** Updates the last movement timestamp.*/
    public void markJustMoved(long timestamp) {
        this.lastMoveTime = timestamp;
    }
}
