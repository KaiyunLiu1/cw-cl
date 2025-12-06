package uk.ac.nott.cs.comp2013.model;

/**
 * Represents a single tile in the game map.
 *
 * <p>Each MapBlock has a position, type character,
 * and collision properties. The type character maps to tile images
 * using the pattern '1'-'9' and 'A'-'J'.</p>
 */
public class MapBlock {

    private static int tileSize = 48;
    private static int halfTileSize = 24;
    private final int x;
    private final int y;
    private final char type;
    private final int width;
    private final int height;

    /**
     * Constructs a MapBlock with the specified type and position.
     *
     * @param type tile type character ('1'-'9', 'A'-'J', '0' for empty)
     * @param x    X position in pixels
     * @param y    Y position in pixels
     */
    public MapBlock(char type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = tileSize;
        this.height = determineHeight(type);
    }

   /** The following is getter/setter section.*/
    public static void setTileSize(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Tile size must be positive.");
        }
        tileSize = size;
        halfTileSize = size / 2;
    }

    public static int getTileSize() {
        return tileSize;
    }
    public char getType() {
        return type;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public boolean isSolid() {
        return type != '0';
    }

    private int determineHeight(char tileType) {
        return switch (tileType) {
            case 'B', 'C', 'D', 'E', 'H', 'I', 'J' -> halfTileSize;
            default -> tileSize;
        };
    }
}
