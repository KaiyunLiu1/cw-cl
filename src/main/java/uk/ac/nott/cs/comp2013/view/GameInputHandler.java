package uk.ac.nott.cs.comp2013.view;

import javafx.scene.input.KeyEvent;

/**
 * Allows the view to send key events to the controller.
 */
public interface GameInputHandler {
    void onKeyPressed(KeyEvent event);
    void onKeyReleased(KeyEvent event);
}
