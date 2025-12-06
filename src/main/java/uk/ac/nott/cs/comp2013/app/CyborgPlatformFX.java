package uk.ac.nott.cs.comp2013.app;

import javafx.application.Application;
import javafx.stage.Stage;
import uk.ac.nott.cs.comp2013.utils.GameConstants;

/** Application entry point.*/
public class CyborgPlatformFX extends Application {


    @Override
    public void start(Stage stage) {
        GameInitializer initializer = new GameInitializer();
        var scene = initializer.initGame();

        stage.setTitle(GameConstants.WINDOW_TITLE);
        stage.setScene(scene);
        stage.setResizable(GameConstants.WINDOW_RESIZABLE);
        stage.show();
        initializer.requestViewFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
