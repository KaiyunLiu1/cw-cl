import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Initial unit tests for the refactored {@code entity} model.
 * These tests are written for the "v2" version of the codebase, where:
 *  - entity is treated as a pure model class (no UI or controller logic).
 *  - some long methods are decomposed into smaller methods.
 */
public class entityStubTest {

    class entityStub extends entity{
        /** Default vertical velocity for first jump. */
        private static final double FIRST_JUMP_VELOCITY = -8.0;

        /** Default vertical velocity for second jump. */
        private static final double SECOND_JUMP_VELOCITY = -6.0;

        /** Threshold above which landing velocity is damped. */
        private static final double LANDING_DAMPING_THRESHOLD = 1.5;

        /** Factor by which landing velocity is reduced. */
        private static final double LANDING_DAMPING_FACTOR = 1.5;

        /** Factor by which upward bounce velocity is reduced. */
        private static final double BOUNCE_DAMPING_FACTOR = 4.0;
        private double distance;
        private boolean collisionstate;
        public entityStub(Image image, int x, int y, int health, int ammo, int hitBox) {
            super(image, x, y, health, ammo, hitBox);
        }

        /**
         * Computes the Euclidean distance from this entity's current position
         * to the given target coordinates.
         *
         * @param targetX target x coordinate
         * @param targetY target y coordinate
         * @return distance between (x, y) and (targetX, targetY)
         */
        public double distanceTo(int targetX, int targetY) {
            return distance;
        }

        /**intersect() will be refactored in task 4.*/
        public boolean intersect() {
            return collisionstate;
        }

        /** gravity() will call yhe following three methods.*/
        public void gravity(){}
        private void applyFreeFall(){}
        private void handleVerticalCollision(){}


    }

    /** Shared dummy image used to avoid null pointer issues in tests. */
    private Image dummyImage;

    /** Entity under test for each test case. */
    private entityStub testEntity;

    @BeforeEach
    void setUp() {
        // Create a simple 32x32 transparent image that can be used as a placeholder sprite.
        dummyImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);

        // Ensure the global MapBlocks.map is initialised and cleared
        // so that each test starts with a clean tile map.
        if (MapBlocks.map == null) {
            MapBlocks.map = new ArrayList<>();
        } else {
            MapBlocks.map.clear();
        }

        // Construct a default stub entity instance for tests
        testEntity = new entityStub(dummyImage, 100, 100, 3, 10, 32);
    }




    /**
     * intersect() should return false when there are no blocks overlapped.
     */
    @Test
    @Disabled("Stub test — actual behaviour implemented in Task 4")
    @DisplayName("intersect(): returns false when there are no map blocks")
    void intersectWhenNoBlocks() {
        MapBlocks.map.clear();

        boolean result = testEntity.intersect();

        assertFalse(result,
                "Without any MapBlocks, entity.intersect() should return false.");
    }

    /**
     * intersect() should return true when the entity's bounds overlap a map block.
     */
    @Test
    @Disabled("Stub test — actual behaviour implemented in Task 4")
    @DisplayName("intersect(): returns true when entity overlaps a MapBlock")
    void intersectWhenOverlappingBlock() {
        MapBlocks.map.clear();

        // Place a single block at the same position as the entity so that their
        // rectangles definitely overlap.
        MapBlocks.map.add(new MapBlocks(dummyImage, testEntity.x, testEntity.y));

        boolean result = testEntity.intersect();

        assertTrue(result,
                "When a MapBlock overlaps the entity's bounds, intersect() should return true.");
    }


    /**
     * gravity() should set the entity as grounded when a block is directly below
     * and the vertical projection intersects that block.
     *
     * This test uses the real MapBlocks.map to simulate the ground.
     */
    @Test
    @Disabled("Stub test — actual behaviour implemented in Task 4")
    @DisplayName("gravity(): sets entity as grounded when a block is directly below")
    void gravitySetsEntityGroundedWhenCollidingWithBlock() {
        MapBlocks.map.clear();

        // Place a ground block just below the entity, at y + 1 so that
        // the projection y + velocity collides with it.
        int blockX = testEntity.x;
        int blockY = testEntity.y + 1;
        MapBlocks.map.add(new MapBlocks(dummyImage, blockX, blockY));

        testEntity.velocity = 1.0;

        testEntity.gravity();

        assertTrue(testEntity.isGrounded,
                "Entity should be grounded after gravity() when there is a block directly below.");
    }

    /**
     * applyFreeFall(): when called, should move the entity downwards by its current
     * velocity, increase the velocity by the acceleration, and mark it as not grounded.
     */
    @Test
    @Disabled("Stub test — actual behaviour implemented in Task 4")
    @DisplayName("applyFreeFall(): updates y and velocity, clears grounded flag")
    void applyFreeFallUpdatesPositionAndVelocity() {

        testEntity.y = 100;
        testEntity.velocity = 2.0;
        testEntity.acceleration = 0.5;
        testEntity.isGrounded = true;

        testEntity.applyFreeFall();

        assertEquals(102, testEntity.y,
                "applyFreeFall() should move y downwards by the current velocity.");
        // velocity: 2.0 + 0.5 = 2.5
        assertEquals(2.5, testEntity.velocity, 1e-6,
                "applyFreeFall() should increase velocity by the acceleration.");
        assertFalse(testEntity.isGrounded,
                "applyFreeFall() should mark the entity as not grounded.");
    }

    @Test
    @Disabled("Stub test — actual behaviour implemented in Task 4")
    @DisplayName("handleVerticalCollision(): dampens large downward velocity without grounding")
    void handleVerticalCollisionDampensLargeDownwardVelocity() {
        entityStub stub = testEntity;

        stub.velocity = 3.0;
        stub.isGrounded = false;

        stub.handleVerticalCollision();

        assertEquals(2.0, stub.velocity, 1e-6,
                "High downward velocity will be reduced by LANDING_DAMPING.");
        assertFalse(stub.isGrounded,
                "Entity should not be on ground.");
    }

    @Test
    @Disabled("Stub test — actual behaviour implemented in Task 4")
    @DisplayName("handleVerticalCollision(): converts upward velocity into reduced downward bounce")
    void handleVerticalCollisionBouncesUpwardVelocity() {

        testEntity.velocity = -4.0;
        testEntity.isGrounded = false;

        testEntity.handleVerticalCollision();

        assertEquals(1.0, testEntity.velocity, 1e-6,
                "Upward velocity should be inverted and reduced by BOUNCE_DAMPING.");
        assertFalse(testEntity.isGrounded,
                "After bouncing off the ceiling, entity should still not be grounded.");
    }


}
