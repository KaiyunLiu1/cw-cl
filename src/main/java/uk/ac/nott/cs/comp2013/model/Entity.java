package uk.ac.nott.cs.comp2013.model;

/**
 * Abstract base class shared by every actor in the game world.
 * <p>
 * The class only stores state; concrete subclasses are responsible for
 * implementing behaviour.  Fields have been chosen to mirror the legacy code so
 * that refactored classes may reuse the proven physics rules.
 * </p>
 */
public abstract class Entity {

    /** World position (top-left corner). */
    protected int x;
    protected double y;

    /** Collision dimensions. */
    protected final int width;
    protected final int height;

    /** Resource counters. */
    protected int health;
    protected int ammo;

    /** Current animation/meta state. */
    protected EntityState state = new EntityState(true, AnimationState.IDLE);

    /** Horizontal speed used by controllers to update movement. */
    protected int speed;

    /** Vertical velocity used by physics integration. */
    protected double velocityY;

    /** Gravity acceleration applied during free fall. */
    protected double acceleration = 0.5;

    /** Misc. animation helpers reused by multiple entities. */
    protected int jumpCounter;
    protected int jumpStartX;
    protected int jumpStartY;
    protected long lastTime = System.currentTimeMillis();
    protected int lastAnimation = 0;

    /** Current animation frame index chosen by animator. */
    protected int currentFrame;

    /** Damage bookkeeping (knock-back, invulnerability etc.). */
    protected boolean isDamaged;
    protected double damagedTime;

    protected Entity(int x,
                     double y,
                     int width,
                     int height,
                     int health,
                     int ammo) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = health;
        this.ammo = ammo;
    }

    // --- Position helpers --------------------------------------------------

    public int getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setPosition(int x, double y) {
        this.x = x;
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // --- Velocity and acceleration ----------------------------------------

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeedX() {
        return speed;
    }

    public void setSpeedX(int speed) {
        this.speed = speed;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    // --- Jump bookkeeping --------------------------------------------------

    public int getJumpCounter() {
        return jumpCounter;
    }

    public void setJumpCounter(int jumpCounter) {
        this.jumpCounter = jumpCounter;
    }

    public int getJumpStartX() {
        return jumpStartX;
    }

    public void setJumpStartX(int jumpStartX) {
        this.jumpStartX = jumpStartX;
    }

    public int getJumpStartY() {
        return jumpStartY;
    }

    public void setJumpStartY(int jumpStartY) {
        this.jumpStartY = jumpStartY;
    }

    // --- State and flags ---------------------------------------------------

    public EntityState getState() {
        return state;
    }

    public void setState(EntityState state) {
        this.state = state;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public void setDamaged(boolean damaged) {
        isDamaged = damaged;
        damagedTime = System.currentTimeMillis();
    }

    public double getDamagedTime() {
        return damagedTime;
    }


    public long getAnimationTimestamp() { return lastTime; }
    public void setAnimationTimestamp(long timestamp) { this.lastTime = timestamp; }

    public int getAnimationFrame() { return currentFrame; }
    public void setAnimationFrame(int frame) { this.currentFrame = frame; }
}
