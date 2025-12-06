package uk.ac.nott.cs.comp2013.model;

/** Represents the player character in the game.*/
public class Player extends Entity {

    public static final int DEFAULT_HEALTH = 3;
    public static final int DEFAULT_AMMO = 10;
    /** Y coordinate threshold for death. */
    public static final int VOID_Y = 900;
    private final int spawnX;
    private final int spawnY;
    private boolean justShot;
    private long lastShotTime;

    /**
     * Constructs a Player at the specified starting position.
     *
     * @param startX initial X position and spawn point
     * @param startY initial Y position and spawn point
     * @param width  collision box width (from sprite)
     * @param height collision box height (from sprite)
     */
    public Player(int startX, int startY, int width, int height) {
        super(startX, startY, width, height, DEFAULT_HEALTH, DEFAULT_AMMO);
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


    /**
     * Checks if the player has recently jumped.
     * Used for rendering the jump cloud effect.
     *
     * @return true if recently double-jumped and ascending
     */
    public boolean hasRecentlyJumped() {
        return jumpCounter > 1 && velocityY < 0;
    }


    /**
     * Checks if the player has just fired a shot.
     *
     * @return true if in shooting cooldown
     */
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


    /**
     * Checks if the player dead.
     *
     * @return true if dead, false otherwise
     */
    public boolean isDead() {
        return health <= 0 || y > VOID_Y;
    }

    /**
     * Resets the player to spawn point with full health and ammo.
     * Called after death or game restart.
     */
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
        isDamaged = false;
        state.setAnimationState(AnimationState.IDLE);
        currentFrame = 0;
    }

}
