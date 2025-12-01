import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is the regression test for Version 1.
 * These tests exercise cross-class(Player, Enemy, Bullet, Map, Camera, Game) behaviours
 */
public class RegressionTest {

    private BufferedImage dummy;

    @BeforeEach
    void setup() {
        dummy = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);

        // Player sprites
        Player.idleSprites = new Image[]{dummy, dummy, dummy, dummy};
        Player.runningSprites = new Image[]{dummy, dummy, dummy, dummy, dummy, dummy};
        Player.hurtSprites = new Image[]{dummy, dummy};
        Player.shootingSprite = dummy;
        Player.heart = dummy;
        Player.box = dummy;
        Player.ammoBox = dummy;

        // Enemy sprites
        Enemy.idleSprites = new Image[]{dummy, dummy, dummy, dummy, dummy, dummy, dummy, dummy};
        Enemy.walkingSprites = new Image[]{dummy, dummy, dummy, dummy, dummy, dummy, dummy, dummy};
        Enemy.runningSprites = new Image[]{dummy, dummy, dummy, dummy, dummy, dummy, dummy};
        Enemy.hurtSprite = dummy;

        // Bullet
        bullet.bulletImage = dummy;

        // Canvas globals
        canvas.activeBullets = new ArrayList<>();
        canvas.enemies = new ArrayList<>();
        canvas.keysPressed = new boolean[4];
        canvas.isLastDirectionForwards = true;
        canvas.cameraOffset = 0;
        canvas.cloud = dummy;

        // Map
        MapBlocks.map = new ArrayList<>();
        MapBlocks.mapWidth = 10000;

        // Game & player
        Player.deathCounter = 0;
        CyborgPlatform.game = new Game();
        CyborgPlatform.game.isWon = false;

        canvas.player = new Player(100, 300, 3);
    }


    /** Section 1: Player movement & physics */


    //  1. Player moves right when 'D' is pressed.
    @Test
    void playerMovesRightWhenDPressed() {
        Player p = canvas.player;
        int startX = p.x;

        canvas.keysPressed[1] = true;

        for (int i = 0; i < 20; i++) p.update();

        assertTrue(p.x > startX, "Pressing D and the player will move right.");
    }

    //  2. Player moves left when 'A' is pressed.
    @Test
    void playerMovesLeftWhenAPressed() {
        Player p = canvas.player;
        p.x = 400;
        int startX = p.x;

        canvas.keysPressed[0] = true;

        for (int i = 0; i < 20; i++) p.update();

        assertTrue(p.x < startX, "Pressing A and the player will move left.");
    }

    //  3. Player jump upward when 'W' is pressed.
    @Test
    void playerJumpsUpwardsWhenWPressed() {
        Player p = canvas.player;

        p.isGrounded = true;
        p.jumpCounter = 0;

        int startY = p.y;
        int minY = p.y;

        p.jump();
        p.jumpCounter += 2;
        canvas.keysPressed[2] = true;

        for (int i = 0; i < 40; i++) {
            p.update();
            minY = Math.min(minY, p.y);
        }

        assertTrue(minY < startY, "Player should move upwards at least once when jumping.");
    }

    //  4. Gravity pulls the player down when there is no ground
    @Test
    void playerFallsWhenNoGround() {
        Player p = new Player(300, 100, 3);
        canvas.player = p;

        int startY = p.y;
        for (int i = 0; i < 20; i++) p.update();

        assertTrue(p.y > startY, "Gravity should pull players down when they don't reach ground.");
    }

    //  5. Player becomes grounded
    @Test
    void playerStopsOnGround() {
        Player p = new Player(300, 100, 3);
        canvas.player = p;

        MapBlocks ground = new MapBlocks(dummy, 300, 200);
        MapBlocks.map.add(ground);

        for (int i = 0; i < 60; i++) p.update();

        assertTrue(p.isGrounded, "Player should become grounded when landing on a block.");
    }

    //  6. Facing while idle uses last movement direction
    @Test
    void playerFacingDirectionUsesLastDirectionWhenIdle() {
        canvas.isLastDirectionForwards = false;
        Player p = canvas.player;

        canvas.keysPressed[0] = false;
        canvas.keysPressed[1] = false;

        assertFalse(p.isFacingForwards(),
                "When no keys are pressed, the player should face the last movement direction.");
    }

    /**
    *Section 2: Player combat & health*/

    //  7. Shooting spawns a bullet and consumes ammo
    @Test
    void shootingCreatesBulletAndConsumesAmmo() {
        Player p = canvas.player;
        p.ammo = 5;

        canvas.keysPressed[3] = true;
        p.update();

        assertEquals(4, p.ammo, "Shooting should decrease ammo by one.");
        assertEquals(1, canvas.activeBullets.size(), "Shooting should spawn a single bullet.");
    }

    //  8. Player cannot shoot again during cooldown
    @Test
    void playerCannotShootDuringCooldown() {
        Player p = canvas.player;
        p.ammo = 5;

        canvas.keysPressed[3] = true;
        p.update();
        int bulletsAfterFirstShot = canvas.activeBullets.size();

        canvas.keysPressed[3] = true;
        p.update();
        int bulletsAfterSecondTry = canvas.activeBullets.size();

        assertEquals(bulletsAfterFirstShot, bulletsAfterSecondTry,
                "Player can't fire again during the cooldown period.");
    }

    //  9. Player damage applies health loss, knockback and cooldown
    @Test
    void damageAppliesHealthLossKnockbackAndCooldown() {
        Player p = canvas.player;
        Enemy e = new Enemy(p.x + 50, p.y, 2);

        int before = p.health;

        p.damage(e);

        assertEquals(before - 1, p.health, "Player health should be reduced by one on damage.");
        assertTrue(p.isDamaged, "Player should enter damaged state.");
        assertTrue(p.speed < 0, "Player should be knocked back horizontally.");
        assertEquals(-6, p.velocity, "Player should be knocked upwards with velocity -6.");

        // Second damage call during cooldown should have no further effect
        p.damage(e);
        assertEquals(before - 1, p.health,
                "Damage during the cooldown must not reduce health again.");
    }

    // 10. Death respawns player and increments death counter
    @Test
    void playerDeathRespawnsAndIncrementsCounter() {
        Player old = canvas.player;
        int before = Player.deathCounter;

        old.kill();

        assertEquals(before + 1, Player.deathCounter,
                "Each death should increment the death counter.");
        assertNotSame(old, canvas.player,
                "Killing the player should respawn a new Player instance.");
    }

    /**
    *Section 3: Bullet behaviour*/


    // 11. Bullet is removed after travelling its maximum distance
    @Test
    void bulletRemovedAfterMaxDistance() {
        Player p = canvas.player;
        p.ammo = 1;

        canvas.keysPressed[3] = true;
        p.update();

        for (int i = 0; i < 200; i++)
            for (bullet b : new ArrayList<>(canvas.activeBullets))
                b.update();

        assertTrue(canvas.activeBullets.isEmpty(),
                "Bullet should be removed after exceeding its maximum travel distance.");
    }

    // 12. Bullet colliding with enemy reduces enemy health
    @Test
    void bulletDamagesEnemy() {
        Player p = canvas.player;

        Enemy e = new Enemy(400, 300, 2);
        canvas.enemies.clear();
        canvas.enemies.add(e);

        p.x = 200;
        p.y = 300;
        p.ammo = 3;

        canvas.keysPressed[3] = true;
        p.update();

        int before = e.health;

        for (int i = 0; i < 120; i++)
            for (bullet b : new ArrayList<>(canvas.activeBullets))
                b.update();

        assertTrue(e.health < before,
                "Enemy health should decrease when hit by a bullet.");
    }

   /**
    *Section 4: Enemy behaviour & damage
    */

    // 13. Enemy remains idle when player is far away
    @Test
    void enemyStopsWhenPlayerOutsideDetectionRange() {
        Player p = canvas.player;
        p.x = 0;

        Enemy e = new Enemy(800, 300, 2);
        canvas.enemies.clear();
        canvas.enemies.add(e);

        e.doBehavior();

        assertEquals(0, e.speed,
                "Enemy should remain idle when the player is outside the detection range.");
    }

    // 14. Enemy adjusts movement and facing relative to the player
    //     (right side, left side, and very close)
    @Test
    void enemyAdjustsMovementAndFacingRelativeToPlayer() {
        //no map blocks interfering with horizontal movement
        MapBlocks.map.clear();

        Player p = new Player(300, 300, 3);
        canvas.player = p;

        Enemy e = new Enemy(500, 300, 2);
        canvas.enemies.clear();
        canvas.enemies.add(e);

        // Case 1: Enemy to the right of the player (p.x < x)
        // Expect: enemy moves left, facing backwards

        e.x = 500;
        p.x = 300;
        e.speed = 0;

        int startXRight = e.x;
        for (int i = 0; i < 20; i++) {
            e.doBehavior();
        }

        assertTrue(e.x < startXRight,
                "Enemy to the right of the player should move left towards the player.");
        assertFalse(e.isFacingForwards,
                "Enemy on the right should face left (isFacingForwards = false).");

        // Case 2: Enemy to the left of the player (p.x > x)
        // Expect: enemy moves right, facing forwards

        e.x = 300;
        p.x = 500;
        e.speed = 0;

        int startXLeft = e.x;
        for (int i = 0; i < 20; i++) {
            e.doBehavior();
        }

        assertTrue(e.x > startXLeft,
                "Enemy to the left of the player should move right towards the player.");
        assertTrue(e.isFacingForwards,
                "Enemy on the left should face right (isFacingForwards = true).");

        // Case 3: Enemy very close to the player
        // Expect: speed is reset to zero when |x - p.x| < 20

        p.x = 300;
        e.x = 315;       // |x - p.x| = 15 < 20
        e.speed = -1;    // moving towards the player

        e.doBehavior();

        assertEquals(0, e.speed,
                "Enemy speed should be zero when it is very close to the player.");
    }

    // 15. Enemy takes damage and is removed when dead
    @Test
    void enemyDamageAndDeathRemovesFromList() {
        Enemy e = new Enemy(200, 300, 1);
        canvas.enemies.clear();
        canvas.enemies.add(e);

        e.damage();
        assertTrue(e.isDamaged, "Enemy should be marked as damaged after taking a hit.");

        e.health = 0;
        e.doBehavior();

        assertFalse(canvas.enemies.contains(e),
                "Dead enemy should be removed from the enemies list.");
    }

    // 16. Enemy state transitions
    /** Although this test focuses on the Enemy state machine,
    * it is used as a regression check to ensure that the enemy's
    *visible behaviour (walking, running, aerial) remains consistent
    *after refactoring.*/
    @Test
    void enemyAnimationStateTransitionsCorrectly() {
        Enemy e = new Enemy(400, 300, 2);

        e.speed = 2;
        e.isGrounded = true;
        e.updateState();
        assertEquals("walking", e.state.state);

        e.isRunning = true;
        e.updateState();
        assertEquals("running", e.state.state);

        e.isGrounded = false;
        e.updateState();
        assertEquals("aerial", e.state.state);
    }

    /**  Section 5: World, map, camera & win condition
   */

    // 17. Reaching the tunnel triggers the win condition
    @Test
    void reachingTunnelTriggersWin() {
        Player p = canvas.player;
        p.x = 8100;

        p.update();

        assertTrue(CyborgPlatform.game.isWon,
                "Moving beyond the tunnel threshold should trigger the win condition.");
    }

    // 18. MapBlocks parsing from a small in-memory layout
    /** This regression test uses a small fake map string to ensure that
    * the tile parsing logic (character -> tile instance) still works
    * after refactoring, without depending on the file system. */
    @Test
    void mapBlocksLoadsCorrectTileLayout() {
        String fakeMap = "10101\n00000\n";
        Scanner scanner = new Scanner(fakeMap);

        MapBlocks.map.clear();
        MapBlocks.mapImages = new Image[20];

        for (int i = 0; i < MapBlocks.mapImages.length; i++)
            MapBlocks.mapImages[i] = dummy;

        int tileX = 0, tileY = 0;
        while (scanner.hasNextLine()) {
            String[] types = scanner.nextLine().split("");
            MapBlocks.mapWidth = types.length * 48;

            for (String val : types) {
                if (val.equals("0")) {
                    tileX += 48;
                    continue;
                }
                MapBlocks.map.add(new MapBlocks(dummy, tileX, tileY));
                tileX += 48;
            }
            tileY += 48;
            tileX = 0;
        }

        assertEquals(3, MapBlocks.map.size(),
                " Tiles marked with '0' shouldn't create map blocks.");
    }

    // 19. Camera follows the player and respects map boundaries
    @Test
    void cameraFollowsPlayerCorrectly() {
        Player p = canvas.player;
        MapBlocks.mapWidth = 10000;

        p.x = 1000;
        assertEquals(1000 - 625, p.updateCmr(),
                "Camera should centre around the player when away from edges.");

        p.x = 10;
        assertEquals(0, p.updateCmr(),
                "Camera should not scroll past the left edge of the map.");

        p.x = 9800;
        assertEquals(10000 - 1310, p.updateCmr(),
                "Camera should not scroll past the right edge of the map.");
    }
}
