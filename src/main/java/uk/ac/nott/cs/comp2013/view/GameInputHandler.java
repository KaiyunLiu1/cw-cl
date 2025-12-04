package uk.ac.nott.cs.comp2013.view;

import javafx.scene.input.KeyEvent;

/**
 * Allows the view to forward key events to the controller without tight coupling.
 */
public interface GameInputHandler {
    void onKeyPressed(KeyEvent event);
    void onKeyReleased(KeyEvent event);
}
