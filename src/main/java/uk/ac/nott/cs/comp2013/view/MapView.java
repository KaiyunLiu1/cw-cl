package uk.ac.nott.cs.comp2013.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uk.ac.nott.cs.comp2013.model.MapBlock;
import uk.ac.nott.cs.comp2013.model.TileMap;

import java.util.Map;

public class MapView {

    private final TileMap tileMap;
    private final Map<Character, Image> tileImages;

    public MapView(TileMap tileMap, Map<Character, Image> tileImages) {
        this.tileMap = tileMap;
        this.tileImages = tileImages;
    }

    public void draw(GraphicsContext gc, double cameraOffset) {
        for (MapBlock block : tileMap.getBlocks()) {
            Image img = tileImages.get(block.getType());
            gc.drawImage(img, block.getX() + cameraOffset, block.getY());
        }
    }
}
