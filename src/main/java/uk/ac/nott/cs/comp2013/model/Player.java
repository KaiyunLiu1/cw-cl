package uk.ac.nott.cs.comp2013.model;

/**
 * Pure data holder for the controllable cyborg.
 * <p>
 * Behaviour such as movement, gravity, combat and animation are handled by
 * dedicated helper classes in the {@code physicsEngine} package to satisfy
 * SRP/SOLID.  The model only exposes state required by controllers and views.
 * </p>
 */
public class Player extends Entity {

    private static final int DEFAULT_HEALTH = 3;
    private static final int DEFAULT_AMMO = 10;
    private static final int VOID_Y = 900;
    /**
     * Collision bounds tuned to sprite pixel size (30x52) from the legacy assets
     * so the character rests flush on tiles.
     */
    private static final int DEFAULT_WIDTH = 30;
    private static final int DEFAULT_HEIGHT = 52;

    private final int spawnX;
    private final int spawnY;

    private boolean justShot;
    private long lastShotTime;

    private static int deathCounter;

    public Player(int startX, int startY) {
        super(startX,
                startY,
                DEFAULT_WIDTH,
                DEFAULT_HEIGHT,
                DEFAULT_HEALTH,
                DEFAULT_AMMO);
        this.spawnX = startX;
        this.spawnY = startY;
    }

    public int getSpawnX() {
        return spawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }

    public boolean isFacingForwards() {
        return state.isFacingForward();
    }

    public void setFacingForward(boolean facingForward) {
        state.setFacingForward(facingForward);
    }

    public boolean isGrounded() {
        return state.isGrounded();
    }

    public void setGrounded(boolean grounded) {
        state.setGrounded(grounded);
    }

    public boolean hasRecentlyJumped() {
        return jumpCounter > 1 && velocityY < 0;
    }

    public boolean isJustShot() {
        return justShot;
    }

    public void setJustShot(boolean justShot) {
        this.justShot = justShot;
    }

    public long getLastShotTime() {
        return lastShotTime;
    }

    public void setLastShotTime(long lastShotTime) {
        this.lastShotTime = lastShotTime;
    }

    public boolean isDead() {
        return health <= 0 || y > VOID_Y;
    }

    public void respawnAtStart() {
        setPosition(spawnX, spawnY);
        health = DEFAULT_HEALTH;
        ammo = DEFAULT_AMMO;
        speed = 0;
        velocityY = 0;
        jumpCounter = 0;
        setGrounded(true);
        setFacingForward(true);
        justShot = false;
        lastShotTime = 0;
        state.setAnimationState(AnimationState.IDLE);
    }

    public static void incrementDeathCounter() {
        deathCounter++;
    }

    public static int getDeathCounter() {
        return deathCounter;
    }

    public static void resetDeathCounter() {
        deathCounter = 0;
    }
}
