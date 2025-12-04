package uk.ac.nott.cs.comp2013.model;

import uk.ac.nott.cs.comp2013.utils.CollisionUtils;

/**
 * 地图中的一个实心方块（碰撞块）
 */
public class MapBlock {

    public static final int TILE_SIZE = 48;

    private final int x;
    private final int y;
    private final char type; // '1'..'9', 'A'..'J', '0' 代表空

    public MapBlock(char type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public char getType() { return type; }
    public int getX() { return x; }
    public int getY() { return y; }

    public int getWidth() { return TILE_SIZE; }
    public int getHeight() { return TILE_SIZE; }

    public boolean isSolid() {
        return type != '0';
    }

    public boolean overlaps(int ax1, int ay1, int ax2, int ay2) {
        return CollisionUtils.overlaps(
                ax1, ay1, ax2, ay2,
                x, y, x + TILE_SIZE, y + TILE_SIZE
        );
    }
}
