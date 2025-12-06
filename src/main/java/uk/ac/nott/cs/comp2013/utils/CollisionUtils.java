package uk.ac.nott.cs.comp2013.utils;

/** Utils for collision behaviours. */
public final class CollisionUtils {

    private CollisionUtils() {

    }

    public static boolean overlaps(double ax, double ay, double aw, double ah,
                                   double bx, double by, double bw, double bh) {
        double ax2 = ax + aw;
        double ay2 = ay + ah;
        double bx2 = bx + bw;
        double by2 = by + bh;

        boolean widthOverlaps = Math.min(ax2, bx2) > Math.max(ax, bx);
        boolean heightOverlaps = Math.min(ay2, by2) > Math.max(ay, by);
        return widthOverlaps && heightOverlaps;
    }
}
