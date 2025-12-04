package uk.ac.nott.cs.comp2013.app;

import javafx.scene.Scene;
import uk.ac.nott.cs.comp2013.controller.GameController;
import uk.ac.nott.cs.comp2013.model.GameModel;
import uk.ac.nott.cs.comp2013.view.GameViewFX;

/**
 * Bundles the pieces created during bootstrapping.
 */
public record GameContext(
        GameModel model,
        GameController controller,
        GameViewFX view,
        GameAssets assets,
        Scene scene
) {
    public void requestViewFocus() {
        if (view != null) {
            view.requestFocus();
        }
    }
}
