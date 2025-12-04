//import java.awt.*;
//import java.time.Duration;
//
///**
// * Player-controlled entity.
// * <p>Extends this `entity` to include player-specific behaviors such as
// * keyboard input handling, shooting control, death tracking,
// * and sprite-based animations for different states.
// * </p>
// */
//public class Player extends entity {
//    protected Point checkPoint = new Point(20, 300);
//    protected boolean[] keys = canvas.keysPressed;
//    protected boolean justShot;
//    protected double lastShot;
//    protected static int deathCounter;
//
//    public static Image[] idleSprites;
//    public static Image[] runningSprites;
//    public static Image[] hurtSprites;
//    public static Image shootingSprite;
//    /** image representing an ammo pickup box */
//    public static Image ammoBox;
//    /** image representing a regular obstacle */
//    public static Image box;
//    public static Image heart;
//
//    /**
//     * Constructs a initial player.
//     *
//     * @param x the x-coordinate of the player's initial position
//     * @param y the y-coordinate of the player's initial position
//     * @param health the initial health value of the player
//     */
//    public Player(int x, int y, int health) {
//        super(idleSprites[0], x, y, health, 10, 30);
//    }
//
//
//    /**
//     * Updates the player's movement, gravity, collisions, and action states each frame.
//     */
//    public void update() {
//        gravity();
//        int maxSpeed = 5;
//        entity entityCopy = copy(x + speed, y);
//        if (!entityCopy.intersect()) {
//            x += speed;
//            if (keys[0]) {
//                if (speed > -maxSpeed) speed -= 1;
//            }
//            if (keys[1]) {
//                if (speed < maxSpeed) speed += 1;
//            }
//            if (((!keys[0] && !keys[1]) || (keys[0] && keys[1])) && !isDamaged) speed = 0;
//        } else {
//            speed /= 2;
//        }
//        if (isGrounded) {
//            jumpCounter = 0;
//        }
//        if (jumpCounter == 0 && !isGrounded) jumpCounter = 1;
//        if (isGrounded || !keys[2] || velocity > 0) acceleration = 0.5;
//        if (keys[2] && acceleration > 0.25) acceleration -= 0.02;
//        if (y > 900 || health <= 0) {
//            kill();
//        }
//        if (keys[3]) {
//            shoot();
//        }
//
//
//        if ((System.currentTimeMillis() - damagedTime) > 1000 && isDamaged) isDamaged = false;
//
//        if ((System.currentTimeMillis() - lastShot) > 500 && justShot) justShot = false;
//
//
//        if (x > 8000) CyborgPlatform.game.isWon = true;
//
//        updateState();
//        animate();
//    }
//
//
//    /**
//     * Calculates and returns the horizontal camera offset for rendering
//     *
//     * @return {@code true} if the player is facing forward, {@code false} otherwise
//     */
//    public int updateCmr() {
//        int camX = x - (1280 - 30) / 2;
//        camX = Math.max(0, Math.min(camX, MapBlocks.mapWidth - 1280 - 30));
//        return camX;
//    }
//
//    /** Determines this player's facing forward based on keyboard input
//     *
//     *  @return {@code true} if the player should be considered facing forwards,{@code false} otherwise.*/
//    public boolean isFacingForwards() {
//        if (!keys[0] && keys[1]) return true;
//        if (keys[0] && !keys[1]) return false;
//        return canvas.isLastDirectionForwards;
//    }
//
//    /**
//     * Determines whether the player is moving horizontally.
//     *
//     * @return true if the player is moving left or right, false otherwise
//     */
//    public boolean isMoving() {
//        return (keys[0] || keys[1]);
//    }
//
//    /**
//     * Determines whether the player is in a jumping state.
//     *
//     * @return true if this player is jumping, false otherwise
//     */
//    public boolean isJumping() {
//        return (jumpCounter > 0);
//    }
//
//
//    /**
//     * Updates the entity's current state based on its movement, grounded status,
//     * damage status, and recent actions.
//     */
//    public void updateState() {
//        String newState = "idle";
//        if (isGrounded && isMoving()) newState = "running";
//        if (isGrounded && !isMoving()) newState = "idle";
//        if (!isGrounded) newState = "aerial";
//        if (isDamaged) newState = "hurt";
//        if (justShot) newState = "shooting";
//        state = new entitystate(isFacingForwards(), newState);
//    }
//
//
//    /**
//     * Update the sprite image according to the current state and animation timer.
//     */
//    public void animate() {
//        switch (state.state) {
//            case "idle" -> {
//                if (lastAnimation >= idleSprites.length) lastAnimation = 0;
//                if ((System.currentTimeMillis() - lastTime) > 250) {
//                    image = idleSprites[lastAnimation];
//                    lastAnimation++;
//                    lastTime = System.currentTimeMillis();
//                }
//            }
//            case "running" -> {
//                if (lastAnimation >= runningSprites.length) lastAnimation = 0;
//                if ((System.currentTimeMillis() - lastTime) > 180) {
//                    image = runningSprites[lastAnimation];
//                    lastAnimation++;
//                    lastTime = System.currentTimeMillis();
//                }
//            }
//            case "aerial" -> {
//                if (velocity < 0) image = runningSprites[5];
//            }
//            case "hurt" -> {
//                image = hurtSprites[1];
//            }
//            case "shooting" -> {
//                image = shootingSprite;
//            }
//        }
//    }
//
//
//    /**
//     * Player shooting logic: spawn a new bullet and reduce ammo.
//     */
//    public void shoot() {
//        if (!justShot && ammo != 0) {
//            ammo--;
//            justShot = true;
//            lastShot = System.currentTimeMillis();
//            if (isFacingForwards()) {
//                bullet bullet = new bullet(x + 47, y + 10);
//                bullet.speed = 10;
//                bullet.startPoint = new Point(bullet.x, bullet.y);
//                canvas.activeBullets.add(bullet);
//            } else {
//                bullet bullet = new bullet(x - 25, y + 10);
//                bullet.speed = -10;
//                bullet.startPoint = new Point(bullet.x, bullet.y);
//                canvas.activeBullets.add(bullet);
//            }
//
//        }
//    }
//
//    /**
//     * Applies damage to the player from an enemy, reduces health, sets the damaged state, and adjusts knockback and vertical velocity.
//     *
//     * @param e the enemy causing damage
//     */
//    public void damage(Enemy e) {
//        if (!isDamaged) {
//            health--;
//            damagedTime = System.currentTimeMillis();
//            isDamaged = true;
//            if (x < e.x) {
//                speed = -4;
//            } else speed = 4;
//            velocity = -6;
//        }
//    }
//
//
//    /**
//     * Draws the player's health, ammo, timer, control instructions, and game-specific messages.
//     *
//     * @param g the Graphics context used for rendering
//     */
//
//    public void drawGUI(Graphics g) {
//        Font font = canvas.font;
//        font = font.deriveFont(60.0f);
//        g.setColor(Color.BLACK);
//        g.setFont(font);
//
//
//        if (health > 0) g.drawImage(heart, 20 + canvas.cameraOffset, 20, null);
//        if (health > 1) g.drawImage(heart, 89 + canvas.cameraOffset, 20, null);
//        if (health > 2) g.drawImage(heart, 158 + canvas.cameraOffset, 20, null);
//
//
//        g.drawImage(ammoBox, 1040 + canvas.cameraOffset, 20, null);
//        g.drawString(String.valueOf(ammo), 1130 + canvas.cameraOffset, 68);
//
//
//        long gameTimer = (long) (System.currentTimeMillis() - Game.startTime);
//        Duration duration = Duration.ofMillis(gameTimer);
//        long minutes = duration.toMinutes();
//        duration = duration.minusMinutes(minutes);
//        long seconds = duration.getSeconds();
//        String timer = String.format("%02d" + ":" + "%02d", minutes, seconds);
//        g.drawString(timer, 510 + canvas.cameraOffset, 68);
//
//        font = font.deriveFont(20.0f);
//        g.setColor(Color.BLACK);
//        g.setFont(font);
//        String[] controls = new String[]{
//                "Use WASD to Move",
//                "You can Double Jump",
//                "Use SPACE to Shoot",
//                "Press ESCAPE to Restart"
//        };
//        int printAtY = 250;
//        for (String control : controls) {
//            g.drawString(control, 470, printAtY);
//            printAtY += 30;
//        }
//        g.drawString("This is The Winner Tunnel", 6800, 310);
//        g.drawString("Just Keep Walking!", 6800, 340);
//        g.drawString("ATTEMPTS: " + deathCounter, 20 + canvas.cameraOffset, 110);
//
//    }
//
//
//    public void kill() {
//        deathCounter++;
//        CyborgPlatform.game.spawnEntities();
//    }
//}
