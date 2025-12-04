package uk.ac.nott.cs.comp2013.utils;

public final class CollisionUtils {

    private CollisionUtils() {}

    /**
     * Rectangle overlap using inclusive-exclusive bounds (open on max edge).
     */
    public static boolean overlaps(int ax1, int ay1, int ax2, int ay2,
                                   int bx1, int by1, int bx2, int by2) {

        boolean widthIsPositive = Math.min(ax2, bx2) > Math.max(ax1, bx1);
        boolean heightIsPositive = Math.min(ay2, by2) > Math.max(ay1, by1);

        return widthIsPositive && heightIsPositive;
    }

    /**
     * Rectangle overlap using top-left coordinates and width/height.
     */
    public static boolean overlaps(double ax, double ay, int aw, int ah,
                                   double bx, double by, int bw, int bh) {
        int ax1 = (int) ax;
        int ay1 = (int) ay;
        int ax2 = ax1 + aw;
        int ay2 = ay1 + ah;

        int bx1 = (int) bx;
        int by1 = (int) by;
        int bx2 = bx1 + bw;
        int by2 = by1 + bh;

        return overlaps(ax1, ay1, ax2, ay2, bx1, by1, bx2, by2);
    }
}
