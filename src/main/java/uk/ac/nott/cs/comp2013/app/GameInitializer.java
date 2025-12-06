package uk.ac.nott.cs.comp2013.app;

import javafx.scene.Scene;
import uk.ac.nott.cs.comp2013.controller.GameController;
import uk.ac.nott.cs.comp2013.model.EntityFactory;
import uk.ac.nott.cs.comp2013.model.Bullet;
import uk.ac.nott.cs.comp2013.model.Enemy;
import uk.ac.nott.cs.comp2013.model.GameModel;
import uk.ac.nott.cs.comp2013.model.MapBlock;
import uk.ac.nott.cs.comp2013.model.Player;
import uk.ac.nott.cs.comp2013.model.TileMap;
import uk.ac.nott.cs.comp2013.utils.GameConstants;
import uk.ac.nott.cs.comp2013.view.GameAssets;
import uk.ac.nott.cs.comp2013.view.GameViewFX;
import uk.ac.nott.cs.comp2013.view.GameRenderer;

import java.util.List;

/** Initializes all game components.*/
public final class GameInitializer {

    private final GameAssetsLoader assetsLoader;
    private GameViewFX view;
    public GameInitializer() {
        this(new GameAssetsLoader());
    }


    public GameInitializer(GameAssetsLoader assetsLoader) {
        this.assetsLoader = assetsLoader;
    }

    /**
     * Initializes the complete game and returns the context.
     *
     * @return initialized JavaFX scene
     */
    public Scene initGame() {

        GameAssets assets = assetsLoader.loadAll();
        configureDimensionsFromAssets(assets);

        TileMap tileMap = TileMap.load(GameConstants.MAP_FILE);

        Player player = EntityFactory.createPlayer();
        List<Enemy> enemies = EntityFactory.createAllEnemies();
        List<Bullet> bullets = EntityFactory.createBulletList();
        GameModel model = new GameModel(player, enemies, bullets, tileMap);

        GameRenderer renderer = new GameRenderer(
                assets.getTileImages(),
                assets.getBackgroundLayers(),
                assets.getWinFront(),
                assets.getWinBack()
        );

        GameController controller = new GameController(model, assets);

        view = new GameViewFX(
                renderer,
                assets,
                GameConstants.VIEW_WIDTH,
                GameConstants.VIEW_HEIGHT
        );

        controller.attachView(view);

        return new Scene(view);
    }

    /**
     * Reads sprite sizes from resources and passes numeric dimensions to the model layer.
     */
    private void configureDimensionsFromAssets(GameAssets assets) {
        GameAssets a = require(assets, "Assets missing; cannot configure dimensions.");
        Dimensions tileDims = dims(a.getTileImages());
        MapBlock.setTileSize(tileDims.width());

        Dimensions playerDims = dims(
                a.getPlayerIdleSprites(), a.getPlayerRunningSprites(), a.getPlayerHurtSprites(), wrap(a.getPlayerShootingSprite())
        );
        EntityFactory.setPlayerDimensions(playerDims.width(), playerDims.height());

        Dimensions enemyDims = dims(
                a.getEnemyIdleSprites(), a.getEnemyWalkingSprites(), a.getEnemyRunningSprites(), wrap(a.getEnemyHurtSprite())
        );
        EntityFactory.setEnemyDimensions(enemyDims.width(), enemyDims.height());

        var bulletImg = require(a.getPlayerBulletImage(), "Bullet image is missing; cannot configure bullet dimensions.");
        Dimensions bulletDims = dims(wrap(bulletImg));
        EntityFactory.setBulletDimensions(bulletDims.width(), bulletDims.height());
    }

    private Dimensions dims(javafx.scene.image.Image[]... groups) {
        double maxW = 0;
        double maxH = 0;
        for (var group : groups) {
            if (group == null) continue;
            for (var img : group) {
                if (img != null) {
                    maxW = Math.max(maxW, img.getWidth());
                    maxH = Math.max(maxH, img.getHeight());
                }
            }
        }
        if (maxW <= 0 || maxH <= 0) {
            throw new IllegalStateException("Sprite dimensions invalid; cannot configure dimensions.");
        }
        return new Dimensions((int) Math.round(maxW), (int) Math.round(maxH));
    }

    private javafx.scene.image.Image[] wrap(javafx.scene.image.Image image) {
        return new javafx.scene.image.Image[]{image};
    }

    private <T> T require(T value, String message) {
        if (value == null) {
            throw new IllegalStateException(message);
        }
        return value;
    }

    private record Dimensions(int width, int height) { }

    /** Requests focus on the view for keyboard input. */
    public void requestViewFocus() {
        if (view != null) {
            view.requestFocus();
        }
    }
}
