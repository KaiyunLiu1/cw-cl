/*
Name: Marcos Ibáñez Matles
 */

import javax.swing.*;
import java.awt.*;

/**
 * Application entry point. Sets up the main window, initializes the game
 * resources and starts the game loop.
 *
 * @author Max L. Wilson
 * @author Kaiyun Liu
 * @version %G%
 */
public class CyborgPlatform {

    /**
     * Main game instance used throughout the application.
     */
     public static Game game;

    /**
     * Launches the platform game application.
     *
     * <p>Sets up the main JFrame, initializes game resources,
     * spawns entities, prepares the map, and starts the game loop.</p>
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Platform Game");
        frame.setPreferredSize(new Dimension(1280, 758));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas canvas = new canvas();
        game = new Game();
        game.loadImages();
        game.spawnEntities();
        MapBlocks.getMap();
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        game.startTimer(canvas);
    }
}
