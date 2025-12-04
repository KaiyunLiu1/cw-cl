package uk.ac.nott.cs.comp2013.model;

/**
 * Data representation of an aggressive enemy.
 * <p>
 * Behaviour (movement, gravity, animation) is provided by helper classes in
 * the physics layer, keeping the model small and testable.
 * </p>
 */
public class Enemy extends Entity {

    /**
     * Collision box matches legacy enemy sprite dimensions (30x64).
     */
    public static final int HITBOX_WIDTH = 30;
    public static final int HITBOX_HEIGHT = 64;

    private boolean facingForward = true;
    private boolean running;
    private long lastMoveTime = System.currentTimeMillis();

    public Enemy(int x, int y, int health) {
        super(x, y, HITBOX_WIDTH, HITBOX_HEIGHT, health, 0);
        setFacingForward(false);
        setAnimationFrame(0);
    }

    public double distanceFrom(Player p) {
        int dx = p.getX() - x;
        double dy = p.getY() - y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void damage() {
        health--;
        isDamaged = true;
        damagedTime = System.currentTimeMillis();
        velocityY = -6;
    }

    public boolean isFacingForwards() {
        return facingForward;
    }

    public void setFacingForward(boolean facingForward) {
        this.facingForward = facingForward;
        state.setFacingForward(facingForward);
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void markGrounded(boolean grounded) {
        state.setGrounded(grounded);
        if (grounded) {
            jumpCounter = 0;
        }
    }

    public void stop() {
        speed = 0;
    }

    public long getLastMoveTime() {
        return lastMoveTime;
    }

    public void markJustMoved(long timestamp) {
        this.lastMoveTime = timestamp;
    }
}
