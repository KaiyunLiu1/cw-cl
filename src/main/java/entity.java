//import java.awt.*;
//
///**
// * Base class for all movable game objects.
// * <p>
// * This class encapsulates shared attributes and behaviors such as position,
// * movement physics, health, jumping, and collision detection with map tiles.
// * </p>
// */
//public class entity {
//    /** a flag indicating whether this entity is currently damaged */
//    protected boolean isDamaged;
//    protected double damagedTime;
//    /** a flag indicating whether the entity is on the ground */
//    protected boolean isGrounded = false;
//    /** horizontal movement speed of the entity */
//    protected int speed = 0;
//    /** an image representing the entity */
//    protected Image image;
//    protected int x;
//    protected int y;
//    /** vertical velocity of this entity */
//    protected double velocity;
//    /** vertical acceleration of this entity */
//    protected double acceleration = 0.5;
//    protected int health;
//    protected int ammo;
//    /** current behavior state of the entity */
//    protected entitystate state;
//    /** counter for the number of jumps since last grounded */
//    public int jumpCounter = 0;
//    public int jumpX;
//    public int jumpY;
//    public double lastTime = System.currentTimeMillis();
//    public int lastAnimation = 0;
//    /** width of the entity's hitbox for intersection detection */
//    public int hitBox;
//
//    /** Constructor for entity.
//     *
//     * @param image   Image representing the entity
//     * @param x       the x-coordinate (in pixels) of the entity's top-left corner on the game map
//     * @param y       the y-coordinate (in pixels) of the entity's top-left corner on the game map
//     * @param health  starting health points
//     * @param ammo    starting ammo amount
//     * @param hitBox  width of the entity's hitbox for intersection detection
//     */
//    public entity(Image image, int x, int y, int health, int ammo, int hitBox) {
//        this.image = image;
//        this.x = x;
//        this.y = y;
//        this.health = health;
//        this.ammo = ammo;
//        this.hitBox = hitBox;
//        this.state = new entitystate(true, "idle");
//    }
//
//
//    /**
//     * Checks whether this entity is colliding with any map block
//     * using axis-aligned bounding box collision detection.
//     *
//     * @return true if this entity intersects any map tile, false otherwise
//     */
//    public boolean intersect() {
//        int x2 = x + hitBox;
//        int y2 = y + image.getHeight(null);
//        // A flag indicating whether the entity is intersecting any map block
//        boolean isInside = false;
//        for (int i = 0; i < MapBlocks.map.size(); i++) {
//            MapBlocks block = MapBlocks.map.get(i);
//            int blockX = block.x;
//            int blockY = block.y;
//            int blockX2 = block.x + block.image.getWidth(null);
//            int blockY2 = block.y + block.image.getHeight(null);
//            boolean widthIsPositive = Math.min(x2, blockX2) > Math.max(x, blockX);
//            boolean heightIsPositive = Math.min(y2, blockY2) > Math.max(y, blockY);
//            if (widthIsPositive && heightIsPositive) {
//                isInside = true;
//                break;
//            }
//        }
//        return isInside;
//    }
//
//
//    /** Makes the entity jump,sets the velocity and records the starting position. */
//    public void jump() {
//        if (jumpCounter == 1) velocity = -6;
//        else velocity = -8;
//        jumpX = x;
//        jumpY = y;
//    }
//
//
//    /**
//     * Returns a shallow copy of this entity at a new position, sharing the same image, health, and ammo.
//     *
//     * @return a new {@code entity} object with the same attributes but a different position
//     */
//    public entity copy(int newX, int newY) {
//        entity copy = new entity(image, newX, newY, health, ammo, hitBox);
//        copy.image = image;
//        return copy;
//    }
//
//
//    /**
//     * Applies gravity to the entity and updates its velocity and grounded status based on intersection.
//     */
//    public void gravity() {
//        entity entityCopy = copy(x, (int) (y + velocity));
//        if (!entityCopy.intersect()) {
//            y += velocity;
//            velocity += acceleration;
//            isGrounded = false;
//        } else {
//            if (velocity > 1.5) velocity /= 1.5;
//            if (velocity < 0) velocity = -(velocity / 4);
//            else isGrounded = true;
//        }
//    }
//}
//
///** Represents the current orientation and action state of this entity.*/
//class entitystate {
//    protected boolean isFacingForward;
//    protected String state;
//
//    /**
//     * Creates a new {@code entitystate} with the given facing direction and behavior state.
//     *
//     * @param isFacingForward true if the entity is facing forward, false otherwise
//     * @param state the string representing current behavior state
//     */
//    public entitystate(boolean isFacingForward, String state) {
//        this.isFacingForward = isFacingForward;
//        this.state = state;
//    }
//}