package uk.ac.nott.cs.comp2013.app;

import javafx.scene.Scene;
import uk.ac.nott.cs.comp2013.controller.GameController;
import uk.ac.nott.cs.comp2013.model.Bullet;
import uk.ac.nott.cs.comp2013.model.Enemy;
import uk.ac.nott.cs.comp2013.model.GameModel;
import uk.ac.nott.cs.comp2013.model.Player;
import uk.ac.nott.cs.comp2013.model.TileMap;
import uk.ac.nott.cs.comp2013.physicsEngine.EntitySpawner;
import uk.ac.nott.cs.comp2013.physicsEngine.MapLoader;
import uk.ac.nott.cs.comp2013.view.GameViewFX;
import uk.ac.nott.cs.comp2013.view.render.BackgroundRenderer;
import uk.ac.nott.cs.comp2013.view.render.TileMapRenderer;

import java.util.List;

/**
 * Creates the model, controller, view, assets, and scene needed to start the game.
 */
public final class GameInitializer {

    private final GameAssetsLoader assetsLoader;
    private final MapLoader mapLoader;

    public GameInitializer() {
        this(new GameAssetsLoader(), new MapLoader());
    }

    public GameInitializer(GameAssetsLoader assetsLoader, MapLoader mapLoader) {
        this.assetsLoader = assetsLoader;
        this.mapLoader = mapLoader;
    }

    public GameContext initGame() {
        GameAssets assets = assetsLoader.loadAll();
        TileMap tileMap = mapLoader.load(GameConfig.MAP_FILE);

        Player player = EntitySpawner.createPlayer();
        List<Enemy> enemies = EntitySpawner.createEnemies();
        List<Bullet> bullets = EntitySpawner.createBulletList();
        GameModel model = new GameModel(player, enemies, bullets, tileMap);

        TileMapRenderer tileMapRenderer = new TileMapRenderer(assets.getTileImages());
        BackgroundRenderer backgroundRenderer = new BackgroundRenderer(
                assets.getBackgroundLayers(),
                assets.getWinFront(),
                assets.getWinBack()
        );

        GameController controller = new GameController(model, assets);
        GameViewFX view = new GameViewFX(
                tileMapRenderer,
                backgroundRenderer,
                assets,
                GameConfig.VIEW_WIDTH,
                GameConfig.VIEW_HEIGHT
        );
        controller.attachView(view);
        Scene scene = new Scene(view);

        return new GameContext(model, controller, view, assets, scene);
    }
}
