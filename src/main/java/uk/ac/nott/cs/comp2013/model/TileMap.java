package uk.ac.nott.cs.comp2013.model;

import uk.ac.nott.cs.comp2013.utils.CollisionUtils;

import java.util.List;

/**
 * 简单的 tile 地图：仅保存所有块和整体宽度
 */
public class TileMap {

    private final List<MapBlock> blocks;
    private final int mapWidth;

    public TileMap(List<MapBlock> blocks, int mapWidth) {
        this.blocks = blocks;
        this.mapWidth = mapWidth;
    }

    public List<MapBlock> getBlocks() {
        return blocks;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    /**
     * Simple axis-aligned collision test against all solid map blocks.
     *
     * @param px   top-left x coordinate
     * @param py   top-left y coordinate
     * @param w    width of the rectangle
     * @param h    height of the rectangle
     * @return {@code true} when any tile overlaps the rectangle
     */
    public boolean collides(double px, double py, int w, int h) {
        int ax1 = (int) px;
        int ay1 = (int) py;
        int ax2 = ax1 + w;
        int ay2 = ay1 + h;

        for (MapBlock block : blocks) {
            if (block.isSolid() && CollisionUtils.overlaps(
                    ax1, ay1, ax2, ay2,
                    block.getX(), block.getY(),
                    block.getX() + block.getWidth(),
                    block.getY() + block.getHeight())
            ) {
                return true;
            }
        }
        return false;
    }
}
