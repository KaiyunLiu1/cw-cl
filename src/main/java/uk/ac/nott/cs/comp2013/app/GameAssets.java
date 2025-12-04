package uk.ac.nott.cs.comp2013.app;

import javafx.scene.image.Image;
import javafx.scene.text.Font;
import uk.ac.nott.cs.comp2013.view.EnemyAssets;
import uk.ac.nott.cs.comp2013.view.PlayerView;

/**
 * Immutable bundle of loaded resources.
 */
public final class GameAssets {

    private final PlayerView.PlayerAssets playerAssets;
    private final EnemyAssets enemyAssets;
    private final Image[] tileImages;
    private final Image[] backgroundLayers;
    private final Image winFront;
    private final Image winBack;
    private final Font hudFont;
    private final Font titleFont;

    public GameAssets(PlayerView.PlayerAssets playerAssets,
                      EnemyAssets enemyAssets,
                      Image[] tileImages,
                      Image[] backgroundLayers,
                      Image winFront,
                      Image winBack,
                      Font hudFont,
                      Font titleFont) {
        this.playerAssets = playerAssets;
        this.enemyAssets = enemyAssets;
        this.tileImages = tileImages;
        this.backgroundLayers = backgroundLayers;
        this.winFront = winFront;
        this.winBack = winBack;
        this.hudFont = hudFont;
        this.titleFont = titleFont;
    }

    public PlayerView.PlayerAssets getPlayerAssets() { return playerAssets; }
    public EnemyAssets getEnemyAssets() { return enemyAssets; }
    public Image[] getTileImages() { return tileImages; }
    public Image[] getBackgroundLayers() { return backgroundLayers; }
    public Image getWinFront() { return winFront; }
    public Image getWinBack() { return winBack; }
    public Font getHudFont() { return hudFont; }
    public Font getTitleFont() { return titleFont; }
}
