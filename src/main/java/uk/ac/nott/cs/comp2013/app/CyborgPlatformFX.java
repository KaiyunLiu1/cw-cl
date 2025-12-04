package uk.ac.nott.cs.comp2013.app;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX entry point for the refactored MVC architecture.
 */
public class CyborgPlatformFX extends Application {

    @Override
    public void start(Stage stage) {
        GameContext context = new GameInitializer().initGame();

        stage.setTitle(GameConfig.WINDOW_TITLE);
        stage.setScene(context.scene());
        stage.setResizable(GameConfig.WINDOW_RESIZABLE);
        stage.show();
        context.requestViewFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
