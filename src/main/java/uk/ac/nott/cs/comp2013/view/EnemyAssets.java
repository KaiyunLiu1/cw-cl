package uk.ac.nott.cs.comp2013.view;

import javafx.scene.image.Image;

/**
 * Bundles enemy sprite sheets so the model layer remains UI-agnostic.
 * <p>
 * Populated by {@code GameAssetsLoader} at startup and reused by the animator to
 * select the correct frame for each state.
 * </p>
 */
public final class EnemyAssets {

    private final Image[] idleSprites;
    private final Image[] walkingSprites;
    private final Image[] runningSprites;
    private final Image hurtSprite;

    public EnemyAssets(Image[] idleSprites,
                       Image[] walkingSprites,
                       Image[] runningSprites,
                       Image hurtSprite) {
        this.idleSprites = idleSprites;
        this.walkingSprites = walkingSprites;
        this.runningSprites = runningSprites;
        this.hurtSprite = hurtSprite;
    }

    public Image[] getIdleSprites() {
        return idleSprites;
    }

    public Image[] getWalkingSprites() {
        return walkingSprites;
    }

    public Image[] getRunningSprites() {
        return runningSprites;
    }

    public Image getHurtSprite() {
        return hurtSprite;
    }
}
