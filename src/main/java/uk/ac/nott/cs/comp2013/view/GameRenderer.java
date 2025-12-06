package uk.ac.nott.cs.comp2013.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uk.ac.nott.cs.comp2013.model.MapBlock;
import uk.ac.nott.cs.comp2013.model.TileMap;

/**
 * Unified renderer for background, tile map, and sprite drawing.
 */
public final class GameRenderer {

    private final Image[] tileImages;
    private final Image[] backgroundLayers;
    private final Image winBackgroundFront;
    private final Image winBackgroundBack;

    /**
     * Creates a renderer with all required assets.
     *
     * @param tileImages        tile sprites
     * @param backgroundLayers  parallax background layers
     * @param winBackgroundFront win screen front image
     * @param winBackgroundBack  win screen back image
     */
    public GameRenderer(Image[] tileImages,
                        Image[] backgroundLayers,
                        Image winBackgroundFront,
                        Image winBackgroundBack) {
        this.tileImages = tileImages;
        this.backgroundLayers = backgroundLayers;
        this.winBackgroundFront = winBackgroundFront;
        this.winBackgroundBack = winBackgroundBack;
    }

    /** Renders parallax background layers. */
    public void renderBackground(GraphicsContext gc, int cameraOffset, int mapWidth) {
        if (backgroundLayers == null) {
            return;
        }
        double height = gc.getCanvas().getHeight();

        for (int i = 0; i < backgroundLayers.length; i++) {
            Image img = backgroundLayers[i];
            if (img == null) {
                continue;
            }

            double parallaxOffset = calculateParallaxOffset(i, cameraOffset);
            for (double mapX = 0; mapX < mapWidth; mapX += img.getWidth()) {
                double drawX = mapX + parallaxOffset;
                gc.drawImage(img, drawX, 0, img.getWidth(), height);
            }
        }
    }

    /** Renders the tile map. */
    public void renderTileMap(GraphicsContext gc, TileMap map) {
        if (map == null || tileImages == null) {
            return;
        }
        for (MapBlock block : map.getBlocks()) {
            Image tile = imageFor(block.getType());
            if (tile != null) {
                gc.drawImage(tile, block.getX(), block.getY());
            }
        }
    }

    /** Draws a sprite with optional horizontal flip. */
    public void drawFacing(GraphicsContext gc, Image sprite, double x, double y, boolean facingRight) {
        if (sprite == null) {
            return;
        }
        if (facingRight) {
            gc.drawImage(sprite, x, y);
        } else {
            gc.save();
            gc.translate(x + sprite.getWidth(), y);
            gc.scale(-1, 1);
            gc.drawImage(sprite, 0, 0);
            gc.restore();
        }
    }

    public Image getWinBackgroundFront() {
        return winBackgroundFront;
    }

    public Image getWinBackgroundBack() {
        return winBackgroundBack;
    }

    private double calculateParallaxOffset(int layerIndex, int cameraOffset) {
        return switch (layerIndex) {
            case 0 -> cameraOffset;
            case 1 -> cameraOffset / 2.0;
            case 2 -> cameraOffset / 4.0;
            default -> cameraOffset / 16.0;
        };
    }

    private Image imageFor(char type) {
        if (type >= '1' && type <= '9') {
            int idx = type - '1';
            return (idx >= 0 && idx < tileImages.length) ? tileImages[idx] : null;
        }
        if (type >= 'A' && type <= 'J') {
            int idx = 9 + (type - 'A');
            return (idx >= 0 && idx < tileImages.length) ? tileImages[idx] : null;
        }
        return null;
    }
}
