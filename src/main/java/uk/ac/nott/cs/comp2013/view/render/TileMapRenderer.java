package uk.ac.nott.cs.comp2013.view.render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uk.ac.nott.cs.comp2013.model.MapBlock;
import uk.ac.nott.cs.comp2013.model.TileMap;

/**
 * Draws tiles using the same ordering as the legacy Swing map renderer.
 */
public class TileMapRenderer {

    private final Image[] tiles;

    public TileMapRenderer(Image[] tiles) {
        this.tiles = tiles;
    }

    public void render(GraphicsContext gc, TileMap map) {
        if (map == null || tiles == null) return;

        for (MapBlock block : map.getBlocks()) {
            Image tile = imageFor(block.getType());
            if (tile != null) {
                gc.drawImage(tile, block.getX(), block.getY());
            }
        }
    }

    private Image imageFor(char type) {
        if (type >= '1' && type <= '9') {
            return tiles[type - '1'];
        }
        if (type >= 'A' && type <= 'J') {
            int idx = 9 + (type - 'A');
            if (idx >= 0 && idx < tiles.length) {
                return tiles[idx];
            }
        }
        return null;
    }
}
