//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.util.ArrayList;
//
///**
// * A main game canvas responsible for rendering and handling user inputs.
// * <p>
// * This class manages the core game loop visuals and input interactions, including:
// * <ul>
// *     <li>Rendering full game scene, including gameplay frames and end screen</li>
// *     <li>Handling keyboard input</li>
// *     <li>Displaying FPS and game-end screen</li>
// * </ul>
// */
//public class canvas extends JComponent implements KeyListener, ActionListener, MouseListener {
//    protected double last = System.nanoTime() / 1000000000.;
//    protected static Player player;
//    /** a list of active enemy entities currently in the game */
//    protected static ArrayList<Enemy> enemies;
//    /** a list of all active bullets fired by the player */
//    protected static ArrayList<bullet> activeBullets = new ArrayList<>();
//    protected static boolean[] keysPressed = new boolean[4];
//    protected static boolean isLastDirectionForwards = true;
//    protected static int cameraOffset;
//    protected static Font font;
//    /** Image used for rendering cloud when the player jumps. */
//    protected static Image cloud;
//
//    /**
//     * Registers this canvas as a KeyListener and MouseListener,
//     * and sets it focusable to allow keyboard input.
//     */
//    public canvas() {
//        addKeyListener(this);
//        addMouseListener(this);
//        setFocusable(true);
//    }
//
//
//    /**
//     * Renders the entire game frame, including background, player, enemies,
//     * bullets, UI elements, and visual effects.
//     *
//     * @param g Graphics context used to draw this game components
//     */
//    public void paint(Graphics g) {
//        if (!CyborgPlatform.game.isWon) {
//            ArrayList<bullet> bulletsCopy = new ArrayList<bullet>(activeBullets);
//            ArrayList<Enemy> enemiesCopy = new ArrayList<Enemy>(enemies);
//
//            cameraOffset = player.updateCmr();
//            g.translate(-cameraOffset, 0);
//
//
//            Background.drawBackground(g);
//            MapBlocks.drawMap(g);
//
//
//            player.update();
//
//
//            displayFPS();
//
//
//            player.drawGUI(g);
//
//
//            if (player.isFacingForwards()) g.drawImage(player.image, player.x, player.y, null);
//            else
//                g.drawImage(player.image, player.x + 30, player.y, -player.image.getWidth(null), player.image.getHeight(null), null);
//            if (player.jumpCounter > 2 && player.velocity < 0)
//                g.drawImage(cloud, player.jumpX, player.jumpY + 42, null);
//
//            for (Enemy enemy : enemiesCopy) {
//                enemy.doBehavior();
//            }
//
//            for (Enemy enemy : enemiesCopy) {
//                if (enemy.isFacingForwards) g.drawImage(enemy.image, enemy.x, enemy.y, null);
//                else
//                    g.drawImage(enemy.image, enemy.x + 30, enemy.y, -enemy.image.getWidth(null), enemy.image.getHeight(null), null);
//            }
//
//
//            for (bullet bullet : bulletsCopy) {
//                if (!activeBullets.isEmpty()) bullet.update();
//            }
//            if (!activeBullets.isEmpty()) {
//
//                for (bullet bullet : bulletsCopy) {
//                    if (bullet.speed > 0) g.drawImage(bullet.image, bullet.x, bullet.y, null);
//                    else
//                        g.drawImage(bullet.image, bullet.x + bullet.image.getWidth(null), bullet.y, -bullet.image.getWidth(null), bullet.image.getHeight(null), null);
//                }
//            }
//        } else end(g);
//    }
//
//
//    public void displayFPS() {
//        System.out.println((int) (1 / (System.nanoTime() / 1000000000. - last)));
//        last = System.nanoTime() / 1000000000.;
//    }
//
//
//    /**
//     * Draws the end-game screen when the player wins.
//     * <p>Displays background images, congratulatory messages,
//     * the number of attempts taken, and restart/exit instructions.</p>
//     *
//     * @param g Graphics context used to draw this game components
//     */
//    public void end(Graphics g) {
//
//        g.drawImage(Background.background[0], 0, 0, null);
//        g.drawImage(Background.background[1], 0, 0, null);
//
//        Font font = canvas.font;
//        font = font.deriveFont(60.0f);
//        g.setColor(Color.BLACK);
//        g.setFont(font);
//        String[] ends = new String[]{
//                "You Won!",
//                "It Took You",
//                Player.deathCounter + " Attempts",
//                "Press ESC to Restart,",
//                "Or ENTER to exit",
//        };
//        g.drawString(ends[0], 460, 220);
//        g.drawString(ends[1], 375, 300);
//        g.drawString(ends[2], 405, 380);
//        g.drawString(ends[3], 150, 460);
//        g.drawString(ends[4], 270, 540);
//
//    }
//
//
//    @Override
//    public void keyTyped(KeyEvent e) {
//
//    }
//
//    /**
//     * Handles keyboard input for controlling the player and game state.
//     * <p>
//     * Responds to specific key presses to perform corresponding actions:
//     * <ul>
//     *     <li><b>A</b> — Move player left</li>
//     *     <li><b>D</b> — Move player right</li>
//     *     <li><b>W</b> — Make the player jump if under the two-jump limit</li>
//     *     <li><b>Space</b> — Fire a bullet</li>
//     *     <li><b>ESC</b> — Restart the game and increment death counter</li>
//     *     <li><b>Enter</b> — Exit the game</li>
//     * </ul>
//     *
//     * @param e a KeyEvent representing the key that was pressed
//     */
//    @Override
//    public void keyPressed(KeyEvent e) {
//        switch (e.getKeyCode()) {
//            case KeyEvent.VK_A -> {
//                keysPressed[0] = true;
//                isLastDirectionForwards = false;
//            }
//            case KeyEvent.VK_D -> {
//                keysPressed[1] = true;
//                isLastDirectionForwards = true;
//            }
//            case KeyEvent.VK_W -> {
//                keysPressed[2] = true;
//                if (player.jumpCounter <= 1) {
//                    player.jump();
//                    player.jumpCounter += 2;
//                }
//            }
//            case KeyEvent.VK_SPACE -> {
//                keysPressed[3] = true;
//            }
//            case KeyEvent.VK_ESCAPE -> {
//                Player.deathCounter++;
//                CyborgPlatform.game.spawnEntities();
//            }
//            case KeyEvent.VK_ENTER -> {
//                System.exit(0);
//            }
//        }
//    }
//
//    /**
//     * Handles key release events to reset input states and update the jump counter.
//     *
//     * @param e a KeyEvent representing the key that was released
//     */
//    @Override
//    public void keyReleased(KeyEvent e) {
//        switch (e.getKeyCode()) {
//            case KeyEvent.VK_A -> {
//                keysPressed[0] = false;
//            }
//            case KeyEvent.VK_D -> {
//                keysPressed[1] = false;
//            }
//            case KeyEvent.VK_W -> {
//                if (player.jumpCounter < 3) player.jumpCounter--;
//                keysPressed[2] = false;
//            }
//            case KeyEvent.VK_SPACE -> {
//                keysPressed[3] = false;
//            }
//        }
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//    }
//
//    @Override
//    public void mouseClicked(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mousePressed(MouseEvent e) {
//        System.out.println(e.getX() + cameraOffset + "," + e.getY());
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseEntered(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseExited(MouseEvent e) {
//
//    }
//
//}
