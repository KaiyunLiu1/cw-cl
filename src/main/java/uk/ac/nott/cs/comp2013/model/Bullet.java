package uk.ac.nott.cs.comp2013.model;

public class Bullet extends Entity {

    private int startX;
    private int startY;

    private static final int MAX_DISTANCE = 600;
    private static final int DEFAULT_WIDTH = 8;
    private static final int DEFAULT_HEIGHT = 5;

    public Bullet(int x, int y, int width, int height) {
        super(x, y, width, height, 1, 0);
        this.startX = x;
        this.startY = y;
    }


    public Bullet(int x, int y) {
        this(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public int getStartX() { return startX; }
    public int getStartY() { return startY; }

    public void setStartPoint(int x, int y) {
        this.startX = x;
        this.startY = y;
    }

    public int getMaxDistance() { return MAX_DISTANCE; }


    public int getSpeed() { return getSpeedX(); }
    public void setSpeed(int speed) { setSpeedX(speed); }
}
