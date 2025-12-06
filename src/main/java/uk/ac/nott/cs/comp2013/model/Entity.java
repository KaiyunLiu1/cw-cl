package uk.ac.nott.cs.comp2013.model;

/**
 * Abstract base class for all game entities.
 *
 * <p>This class encapsulates shared attributes and behaviors such as position,
 * movement physics, health, jumping, and collision detection.</p>
 */
public abstract class Entity {

    protected int x;
    protected double y;
    /** Collision box width in pixels. */
    protected final int width;
    /** Collision box height in pixels. */
    protected final int height;
    protected int health;
    protected int ammo;
    protected EntityState state;
    protected int speed; //horizontal speed
    protected double velocityY; //vertical velocity
    protected double acceleration;
    protected int jumpCounter;
    protected int jumpStartX;
    protected int jumpStartY;
    /** Timestamp of last animation frame change. */
    protected long lastAnimationTime;
    protected int currentFrame;
    protected boolean isDamaged;
    protected double damagedTime;
    protected static final double DEFAULT_ACCELERATION = 0.5;

    /**
     * Constructs a new Entity with specified parameters.
     *
     * @param x      initial X position in pixels
     * @param y      initial Y position in pixels
     * @param width  collision box width in pixels
     * @param height collision box height in pixels
     * @param health initial health points
     * @param ammo   initial ammunition count
     */
    protected Entity(int x, double y, int width, int height, int health, int ammo) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = health;
        this.ammo = ammo;
        this.acceleration = DEFAULT_ACCELERATION;
        this.state = new EntityState(true, AnimationState.IDLE);
        this.lastAnimationTime = System.currentTimeMillis();
    }

    /** The following section contains getter/setter functions.*/

    public int getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * Sets the position of this entity.
     *
     * @param x new X position
     * @param y new Y position
     */
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

    /**
     * Checks if the entity is in damage cooldown.
     *
     * @return true if damaged, false otherwise
     */
    public boolean isDamaged() {
        return isDamaged;
    }

    /**
     * Sets the damage state and records the timestamp.
     *
     * @param damaged true to mark as damaged
     */
    public void setDamaged(boolean damaged) {
        this.isDamaged = damaged;
        if (damaged) {
            this.damagedTime = System.currentTimeMillis();
        }
    }

    public double getDamagedTime() {
        return damagedTime;
    }

    public long getAnimationTimestamp() {
        return lastAnimationTime;
    }
    public void setAnimationTimestamp(long timestamp) {
        this.lastAnimationTime = timestamp;
    }

    public int getAnimationFrame() {
        return currentFrame;
    }
    public void setAnimationFrame(int frame) {
        this.currentFrame = frame;
    }
}