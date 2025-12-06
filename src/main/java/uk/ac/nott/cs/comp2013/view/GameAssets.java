package uk.ac.nott.cs.comp2013.view;

import javafx.scene.image.Image;
import javafx.scene.text.Font;

/**
 * Aggregates all game assets into a single immutable container.*/
public final class GameAssets {

    private final Image[] playerIdleSprites;
    private final Image[] playerRunningSprites;
    private final Image[] playerHurtSprites;
    private final Image playerShootingSprite;
    private final Image playerBulletImage;
    private final Image heartImage;
    private final Image ammoBoxImage;
    private final Image cloudImage;
    private final Image[] enemyIdleSprites;
    private final Image[] enemyWalkingSprites;
    private final Image[] enemyRunningSprites;
    private final Image enemyHurtSprite;
    private final Image[] tileImages;

    private final Image[] backgroundLayers;

    private final Image winFront;
    private final Image winBack;

    private final Font hudFont;
    private final Font titleFont;

    /**
     * Constructs GameAssets with all required resources.
     *
     * @param playerIdleSprites    player idle sprites
     * @param playerRunningSprites player running sprites
     * @param playerHurtSprites    player hurt sprites
     * @param playerShootingSprite player shooting sprite
     * @param playerBulletImage    bullet sprite
     * @param heartImage           health icon
     * @param ammoBoxImage         ammo icon
     * @param cloudImage           jump cloud effect
     * @param enemyIdleSprites     enemy idle sprites
     * @param enemyWalkingSprites  enemy walking sprites
     * @param enemyRunningSprites  enemy running sprites
     * @param enemyHurtSprite      enemy hurt sprite
     * @param tileImages       tile images
     * @param backgroundLayers background images
     * @param winFront         win screen front
     * @param winBack          win screen back
     * @param hudFont          HUD font
     * @param titleFont        title font
     */
    public GameAssets(Image[] playerIdleSprites,
                      Image[] playerRunningSprites,
                      Image[] playerHurtSprites,
                      Image playerShootingSprite,
                      Image playerBulletImage,
                      Image heartImage,
                      Image ammoBoxImage,
                      Image cloudImage,
                      Image[] enemyIdleSprites,
                      Image[] enemyWalkingSprites,
                      Image[] enemyRunningSprites,
                      Image enemyHurtSprite,
                      Image[] tileImages,
                      Image[] backgroundLayers,
                      Image winFront,
                      Image winBack,
                      Font hudFont,
                      Font titleFont) {
        this.playerIdleSprites = playerIdleSprites;
        this.playerRunningSprites = playerRunningSprites;
        this.playerHurtSprites = playerHurtSprites;
        this.playerShootingSprite = playerShootingSprite;
        this.playerBulletImage = playerBulletImage;
        this.heartImage = heartImage;
        this.ammoBoxImage = ammoBoxImage;
        this.cloudImage = cloudImage;
        this.enemyIdleSprites = enemyIdleSprites;
        this.enemyWalkingSprites = enemyWalkingSprites;
        this.enemyRunningSprites = enemyRunningSprites;
        this.enemyHurtSprite = enemyHurtSprite;
        this.tileImages = tileImages;
        this.backgroundLayers = backgroundLayers;
        this.winFront = winFront;
        this.winBack = winBack;
        this.hudFont = hudFont;
        this.titleFont = titleFont;
    }

    /**
     * Player images
     */
    public Image[] getPlayerIdleSprites() { return playerIdleSprites; }
    public Image[] getPlayerRunningSprites() { return playerRunningSprites; }
    public Image[] getPlayerHurtSprites() { return playerHurtSprites; }
    public Image getPlayerShootingSprite() { return playerShootingSprite; }
    public Image getPlayerBulletImage() { return playerBulletImage; }
    public Image getHeartImage() { return heartImage; }
    public Image getAmmoBoxImage() { return ammoBoxImage; }
    public Image getCloudImage() { return cloudImage; }

    /**
     * Enemy assets accessors.
     */
    public Image[] getEnemyIdleSprites() { return enemyIdleSprites; }
    public Image[] getEnemyWalkingSprites() { return enemyWalkingSprites; }
    public Image[] getEnemyRunningSprites() { return enemyRunningSprites; }
    public Image getEnemyHurtSprite() { return enemyHurtSprite; }

    /** Tile images.*/
    public Image[] getTileImages() {
        return tileImages;
    }

    /** Background layer images.*/
    public Image[] getBackgroundLayers() {
        return backgroundLayers;
    }

    /** Returns win screen front background.*/
    public Image getWinFront() {
        return winFront;
    }

    /** Returns win screen back background.*/
    public Image getWinBack() {
        return winBack;
    }

    /** Returns HUD font.*/
    public Font getHudFont() {
        return hudFont;
    }

    /** Returns title font.*/
    public Font getTitleFont() {
        return titleFont;
    }
}
