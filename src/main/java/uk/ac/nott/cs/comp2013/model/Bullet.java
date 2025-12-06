package uk.ac.nott.cs.comp2013.model;

/** Manages bullet state and physics</li>
 */
public class Bullet extends Entity {

    /** Maximum travel distance before bullet is removed. */
    public static final int MAX_DISTANCE = 600;
    private int startX;
    private int startY;

    public Bullet(int x, int y, int width, int height) {
        super(x, y, width, height, 1, 0);
        this.startX = x;
        this.startY = y;
    }

    public int getStartX() {
        return startX;
    }
    public int getStartY() {
        return startY;
    }

    /** Sets the starting point for distance calculations.*/
    public void setStartPoint(int x, int y) {
        this.startX = x;
        this.startY = y;
    }

    /** Returns the maximum travel distance.*/
    public int getMaxDistance() {
        return MAX_DISTANCE;
    }

    /** Calculates the distance traveled from the start point.*/
    public double getTravelledDistance() {
        int dx = x - startX;
        int dy = (int) y - startY;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /** Returns the bullet speed. */
    @Override
    public int getSpeed() {
        return getSpeedX();
    }

    /** Sets the bullet speed. */
    @Override
    public void setSpeed(int speed) {
        setSpeedX(speed);
    }
}