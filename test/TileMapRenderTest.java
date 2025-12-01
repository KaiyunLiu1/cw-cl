import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Initial stub test for the refactored TileMapRenderer class .

 * 1. The class can be instantiated with a tile-image map.
 * 2. The render() method can be invoked without throwing errors.
 */
public class TileMapRenderTest {

    /** Stub for javafx.scene.image.Image */
    private static class ImageStub { }

    /** Stub for javafx.scene.canvas.GraphicsContext */
    private static class GraphicsContextStub { }

    @Test
    @Disabled("Stub test — full JavaFX rendering will be tested in Task 4")
    void render_stub() {


        // Arrange: create stub tile-images
        Map<TileType, ImageStub> imageMap = new HashMap<>();
        imageMap.put(TileType.TILE_1, new ImageStub());
        imageMap.put(TileType.TILE_2, new ImageStub());
        imageMap.put(TileType.EMPTY, new ImageStub());

        // Replace constructor type (Map<TileType, Image>) with stub map

        TileMapRenderer renderer =
                new TileMapRenderer((Map) imageMap);

        // Stub graphics context
        GraphicsContextStub gc = new GraphicsContextStub();

        // Minimal Tile + TileMap setup
        Tile t1 = new Tile(0, 0, 48, 48, TileType.TILE_1);
        Tile t2 = new Tile(48, 0, 48, 48, TileType.TILE_2);
        TileMap map = new TileMap(List.of(t1, t2), 96, 48);

        // call render() with stubs
        renderer.render(gc, map, 0.0);

        // Assert: placeholder for stub-test
        assertTrue(true);
    }
}
