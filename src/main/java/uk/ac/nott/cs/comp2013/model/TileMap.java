package uk.ac.nott.cs.comp2013.model;

import uk.ac.nott.cs.comp2013.utils.CollisionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the complete tile map of a game level.
 *
 * <p>This class stores all map blocks and provides collision detection
 * against the map geometry. It follows the immutable pattern where blocks
 * are set at construction time.</p>
 *
 * <p>Design Patterns Applied:</p>
 * <ul>
 *     <li><b>Immutable Object</b>: Map data cannot be modified after creation</li>
 * </ul>
 *
 * <p>SOLID Principles:</p>
 * <ul>
 *     <li><b>SRP</b>: Only manages map data and collision queries</li>
 * </ul>
 *
 * @author Refactored for COMP2013
 * @version 2.0
 */
public class TileMap {

    /** Unmodifiable list of map blocks. */
    private final List<MapBlock> blocks;

    /** Total width of the map in pixels. */
    private final int mapWidth;

    /**
     * Constructs a TileMap with the specified blocks and width.
     *
     * @param blocks   list of map blocks (will be wrapped as unmodifiable)
     * @param mapWidth total map width in pixels
     */
    public TileMap(List<MapBlock> blocks, int mapWidth) {
        this.blocks = Collections.unmodifiableList(blocks);
        this.mapWidth = mapWidth;
    }

    /**
     * Returns an unmodifiable view of the map blocks.
     *
     * @return list of map blocks
     */
    public List<MapBlock> getBlocks() {
        return blocks;
    }

    /**
     * Returns the total map width.
     *
     * @return width in pixels
     */
    public int getMapWidth() {
        return mapWidth;
    }

    /**
     * Tests for collision between a rectangle and any solid map block.
     *
     * <p>This method is used for entity collision detection against the
     * map geometry.</p>
     *
     * @param px top-left X coordinate
     * @param py top-left Y coordinate
     * @param w  rectangle width
     * @param h  rectangle height
     * @return true if any solid block overlaps the rectangle
     */
    public boolean collides(double px, double py, int w, int h) {
        int ax1 = (int) px;
        int ay1 = (int) py;
        int ax2 = ax1 + w;
        int ay2 = ay1 + h;

        for (MapBlock block : blocks) {
            if (block.isSolid() && CollisionUtils.overlaps(
                    ax1, ay1, w, h,
                    block.getX(), block.getY(), block.getWidth(), block.getHeight())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of blocks in the map.
     *
     * @return block count
     */
    public int getBlockCount() {
        return blocks.size();
    }

    /**
     * Loads a TileMap from the specified resource path
     *
     * @param path resource path ： "/Maps.txt"
     * @return loaded TileMap
     */
    public static TileMap load(String path) {
        List<MapBlock> blocks = new ArrayList<>();
        int tileY = 0;
        int mapWidth = 0;
        int tileSize = MapBlock.getTileSize();

        try (BufferedReader reader = openReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                char[] row = line.toCharArray();
                int tileX = 0;

                mapWidth = row.length * tileSize;

                for (char type : row) {
                    if (type != '0') {
                        blocks.add(new MapBlock(type, tileX, tileY));
                    }
                    tileX += tileSize;
                }

                tileY += tileSize;
            }
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load map: " + path, ex);
        }

        return new TileMap(blocks, mapWidth);
    }

    private static BufferedReader openReader(String path) throws IOException {
        InputStream in = TileMap.class.getResourceAsStream(path);
        if (in != null) {
            return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        }

        String normalized = path.startsWith("/") ? path.substring(1) : path;
        Path filePath = Paths.get("src", "main", "resources", normalized);
        if (Files.exists(filePath)) {
            return Files.newBufferedReader(filePath, StandardCharsets.UTF_8);
        }

        throw new IOException("Map file not found: " + path);
    }
}
