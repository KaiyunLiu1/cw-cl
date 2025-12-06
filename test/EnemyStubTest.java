import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Initial unit tests for Enemy behaviour in the planned Model Layer.
 *  In the refactored design images are owned by the view layer.
 * Here we assign a dummy AWT image only so that width/height-based
 * collision logic in the model can be tested.
 */
public class EnemyStubTest {

    /** New methods are extracted from the original long methods for refactor and stub methods are used for these new methods.
     *  This subclass is created to introduce stub methods.
     */

    class EnemyStub extends Enemy{
        private boolean isDeadValue;
        public EnemyStub (int x, int y,int health){super(x,y,health);}

        /** The following section shows stub methods.*/

        /** Checks whether this enemy should be considered dead.*/
        public boolean isDead() {
            return isDeadValue;
        }

        /** Reduces speed when the enemy is damaged*/
        public void applyDamageSlowdown() {
        }

        /**
         * Takes one part of the responsibility of doBehaviour-resets the damaged flag once the damage cooldown has expired.
         */
        public void updateDamageCooldown(long now) {

        }

        /** getters / setters*/

        public boolean isRunning() {
            return isRunning;
        }

        public void setRunning(boolean running) {
            isRunning = running;
        }

        public boolean isFacingForwards() {
            return isFacingForwards;
        }

        public void setFacingForwards(boolean facingForwards) {
            isFacingForwards = facingForwards;}
    }

    private BufferedImage dummy;
    private EnemyStub testEnemy;
    private Player player;

    @BeforeEach
    void setUp() {
        dummy = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        testEnemy=new EnemyStub(100,100,2);
        player = new Player(103, 104, 5);
        testEnemy.image = dummy;
        player.image = dummy;

        canvas.enemies = new ArrayList<>();
        canvas.enemies.add(testEnemy);
        canvas.player = player;

    }


    /**
     * distanceFromPlayer() should return the Euclidean distance
     * between the enemy and the player using the shared distanceTo() helper.
     */
    @Test
    @Disabled("Stub test — actual behaviour implemented in Task 4")
    void distanceFromPlayer() {

        double distance = testEnemy.distanceFromPlayer(player);

        assertEquals(5.0, distance, 0.0001,
                "Distance from (100,100) to (103,104) should be 5.");
    }


    /**
     *Checks whether the speed is non-zero.
     */
    @Test
    @Disabled("Stub test — actual behaviour implemented in Task 4")
    void isMoving() {
        testEnemy.speed = 0;
        assertFalse(testEnemy.isMoving(), "Enemy isn't moving");

        testEnemy.speed = 2;
        assertTrue(testEnemy.isMoving(), "Enemy is moving");
    }


    /**
     * isDead(): returns true when health <= 0 or when the enemy has fallen
     * below 900, and false otherwise.
     */
    @Test
    @Disabled("Stub test — actual behaviour implemented in Task 4")
    void isDead() {
        // Case 1: health <= 0 → dead
        testEnemy.health = 0;
        testEnemy.y = 100;
        assertTrue(testEnemy.isDead(),
                "Enemy should be dead when health is zero or below.");

        // Case 2: y > 900
        testEnemy.health = 5;
        testEnemy.y = 1000; // greater than DEATH_Y = 900
        assertTrue(testEnemy.isDead(),
                "Enemy should be dead when it has fallen beyond the death Y threshold.");

        // Case 3: healthy and within bounds → not dead
        testEnemy.health = 5;
        testEnemy.y = 200;
        assertFalse(testEnemy.isDead(),
                "Enemy should not be dead when health > 0 and y <= DEATH_Y.");
    }

    /**
     * applyDamageSlowdown(): Halved speed based when isDamaged is true.
     */
    @Test
    @Disabled("Stub test — actual behaviour implemented in Task 4")
    void applyDamageSlowdownHalves() {
        // When damaged → speed should be halved
        testEnemy.isDamaged = true;
        testEnemy.speed = 8;

        testEnemy.applyDamageSlowdown();

        assertEquals(4, testEnemy.speed,
                "Speed should be halved while damaged.");}

    /**
     * applyDamageSlowdown() remain speed when the enemy is not damaged.
     */
    @Test
    @Disabled("Stub test — actual behaviour implemented in Task 4")
    void applyDamageSlowdownUnchanged(){
        // When not damaged → speed should remain unchanged
        testEnemy.isDamaged = false;
        testEnemy.speed = 10;

        testEnemy.applyDamageSlowdown();

        assertEquals(10, testEnemy.speed,
                "Speed should remain unchanged when the enemy is not damaged.");
    }

    /**
     * updateDamageCooldown(): clears the damaged flag once the cooldown
     * has fully expired.
     */
    @Test
    @Disabled("Stub test — actual behaviour implemented in Task 4")
    void updateDamageCooldown() {
        long now = 1_000L;

        // Case 1: elapsed time > DAMAGE_COOLDOWN_MS → isDamaged should become false
        testEnemy.isDamaged = true;
        testEnemy.damagedTime = now - 400;

        testEnemy.updateDamageCooldown(now);

        assertFalse(testEnemy.isDamaged,
                "isDamaged should be reset to false after the damage cooldown expires.");

        // Case 2: elapsed time < DAMAGE_COOLDOWN_MS → isDamaged should remain true
        testEnemy.isDamaged = true;
        testEnemy.damagedTime = now - 100;

        testEnemy.updateDamageCooldown(now);

        assertTrue(testEnemy.isDamaged,
                "isDamaged should remain true if the damage cooldown has not yet expired.");
    }

}
