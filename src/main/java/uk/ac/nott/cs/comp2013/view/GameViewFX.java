package uk.ac.nott.cs.comp2013.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import uk.ac.nott.cs.comp2013.app.GameAssets;
import uk.ac.nott.cs.comp2013.model.Bullet;
import uk.ac.nott.cs.comp2013.model.Enemy;
import uk.ac.nott.cs.comp2013.model.GameModel;
import uk.ac.nott.cs.comp2013.model.Player;
import uk.ac.nott.cs.comp2013.view.render.BackgroundRenderer;
import uk.ac.nott.cs.comp2013.view.render.SpriteRenderer;
import uk.ac.nott.cs.comp2013.view.render.TileMapRenderer;

import java.util.List;

/**
 * JavaFX 版主视图（MVC 中的 V）
 */
public class GameViewFX extends Pane {

    private final Canvas canvas;
    private final GraphicsContext gc;
    private final TileMapRenderer tileMapRenderer;
    private final BackgroundRenderer backgroundRenderer;
    private final Font hudFont;
    private final Font bigFont;

    private GameInputHandler inputHandler;

    public GameViewFX(TileMapRenderer tileMapRenderer,
                      BackgroundRenderer backgroundRenderer,
                      GameAssets assets,
                      double width,
                      double height) {
        this.tileMapRenderer = tileMapRenderer;
        this.backgroundRenderer = backgroundRenderer;
        this.hudFont = assets != null && assets.getHudFont() != null ? assets.getHudFont() : Font.font("Arial", 20);
        this.bigFont = assets != null && assets.getTitleFont() != null ? assets.getTitleFont() : Font.font("Arial", 60);

        this.canvas = new Canvas(width, height);
        this.gc = canvas.getGraphicsContext2D();
        this.gc.setImageSmoothing(false);

        getChildren().add(canvas);
        installInputHandlers();
    }

    public void bindInputHandler(GameInputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public void render(GameModel model, int cameraOffset, boolean gameWon, String formattedTime, GameAssets assets) {
        gc.setFill(Color.BLACK);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (!gameWon) {
            renderGameScene(model, cameraOffset, assets);
            renderHUD(gc, model.getPlayer(), formattedTime, assets);
        } else {
            renderEndScreen(assets);
        }
    }

    private void installInputHandlers() {
        setFocusTraversable(true);
        setOnKeyPressed(event -> {
            if (inputHandler != null) inputHandler.onKeyPressed(event);
        });
        setOnKeyReleased(event -> {
            if (inputHandler != null) inputHandler.onKeyReleased(event);
        });
    }

    private void renderGameScene(GameModel model, int cameraOffset, GameAssets assets) {
        Player player = model.getPlayer();
        List<Enemy> enemies = model.getEnemies();
        List<Bullet> bullets = model.getBullets();

        gc.save();
        gc.translate(-cameraOffset, 0);

        if (backgroundRenderer != null) {
            backgroundRenderer.render(gc, cameraOffset, model.getTileMap().getMapWidth());
        }
        if (tileMapRenderer != null) {
            tileMapRenderer.render(gc, model.getTileMap());
        }

        renderPlayer(gc, player, assets);
        renderEnemies(gc, enemies, assets);
        renderBullets(gc, bullets, assets);
        renderWorldPrompts(gc);

        gc.restore();
    }

    private void renderPlayer(GraphicsContext g, Player player, GameAssets assets) {
        if (player == null || assets == null || assets.getPlayerAssets() == null) return;
        Image sprite = spriteForPlayer(player, assets);
        if (sprite == null) return;

        SpriteRenderer.drawFacing(g, sprite, player.getX(), player.getY(), player.isFacingForwards());

        Image cloud = assets.getPlayerAssets().getCloudImage();
        if (player.hasRecentlyJumped() && cloud != null) {
            g.drawImage(cloud, player.getJumpStartX(), player.getJumpStartY() + 42);
        }
    }

    private void renderEnemies(GraphicsContext g, List<Enemy> enemies, GameAssets assets) {
        if (assets == null || assets.getEnemyAssets() == null) return;
        for (Enemy enemy : enemies) {
            if (!enemy.isAlive()) continue;
            Image sprite = spriteForEnemy(enemy, assets);
            if (sprite == null) continue;
            SpriteRenderer.drawFacing(g, sprite, enemy.getX(), enemy.getY(), enemy.isFacingForwards());
        }
    }

    private void renderBullets(GraphicsContext g, List<Bullet> bullets, GameAssets assets) {
        if (assets == null || assets.getPlayerAssets() == null) return;
        for (Bullet b : bullets) {
            Image sprite = assets.getPlayerAssets().getBulletImage();
            if (sprite == null) continue;
            SpriteRenderer.drawFacing(g, sprite, b.getX(), b.getY(), b.getSpeed() > 0);
        }
    }

    private void renderHUD(GraphicsContext g, Player player, String formattedTime, GameAssets assets) {
        g.setFill(Color.BLACK);
        g.setFont(bigFont);

        Image heart = assets != null && assets.getPlayerAssets() != null ? assets.getPlayerAssets().getHeartImage() : null;
        if (heart != null) {
            if (player.getHealth() > 0) g.drawImage(heart, 20, 20);
            if (player.getHealth() > 1) g.drawImage(heart, 89, 20);
            if (player.getHealth() > 2) g.drawImage(heart, 158, 20);
        }

        Image ammo = assets != null && assets.getPlayerAssets() != null ? assets.getPlayerAssets().getAmmoBoxImage() : null;
        if (ammo != null) {
            g.drawImage(ammo, 1040, 20);
        }
        g.fillText(String.valueOf(player.getAmmo()), 1130, 68);
        g.fillText(formattedTime, 510, 68);

        g.setFont(hudFont);
        g.fillText("ATTEMPTS: " + Player.getDeathCounter(), 20, 110);
    }

    private void renderEndScreen(GameAssets assets) {
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (backgroundRenderer != null) {
            if (backgroundRenderer.getWinBackgroundFront() != null) {
                gc.drawImage(backgroundRenderer.getWinBackgroundFront(), 0, 0);
            }
            if (backgroundRenderer.getWinBackgroundBack() != null) {
                gc.drawImage(backgroundRenderer.getWinBackgroundBack(), 0, 0);
            }
        }

        gc.setFill(Color.BLACK);
        gc.setFont(bigFont);

        String[] ends = new String[]{
                "You Won!",
                "It Took You",
                Player.getDeathCounter() + " Attempts",
                "Press ESC to Restart,",
                "Or ENTER to exit",
        };
        gc.fillText(ends[0], 460, 220);
        gc.fillText(ends[1], 375, 300);
        gc.fillText(ends[2], 405, 380);
        gc.fillText(ends[3], 150, 460);
        gc.fillText(ends[4], 270, 540);
    }

    private void renderWorldPrompts(GraphicsContext g) {
        g.setFill(Color.BLACK);
        g.setFont(hudFont);
        String[] controls = new String[]{
                "Use WASD to Move",
                "You can Double Jump",
                "Use SPACE to Shoot",
                "Press ESCAPE to Restart"
        };
        int printAtY = 250;
        for (String line : controls) {
            g.fillText(line, 470, printAtY);
            printAtY += 30;
        }
        g.fillText("This is The Winner Tunnel", 6800, 310);
        g.fillText("Just Keep Walking!", 6800, 340);
    }

    private Image spriteForPlayer(Player player, GameAssets assets) {
        if (assets == null || assets.getPlayerAssets() == null) return null;
        return switch (player.getState().getAnimationState()) {
            case RUNNING, WALKING -> frameFrom(assets.getPlayerAssets().getRunningSprites(), player.getAnimationFrame());
            case AERIAL -> aerialPlayerFrame(player, assets);
            case HURT -> frameFrom(assets.getPlayerAssets().getHurtSprites(), player.getAnimationFrame());
            case SHOOTING -> assets.getPlayerAssets().getShootingSprite();
            case IDLE -> frameFrom(assets.getPlayerAssets().getIdleSprites(), player.getAnimationFrame());
            default -> null;
        };
    }

    private Image aerialPlayerFrame(Player player, GameAssets assets) {
        Image[] running = assets.getPlayerAssets().getRunningSprites();
        if (running != null && running.length > 5) {
            return running[Math.min(5, running.length - 1)];
        }
        Image[] idle = assets.getPlayerAssets().getIdleSprites();
        return frameFrom(idle, 0);
    }

    private Image spriteForEnemy(Enemy enemy, GameAssets assets) {
        if (assets == null || assets.getEnemyAssets() == null) return null;
        return switch (enemy.getState().getAnimationState()) {
            case WALKING -> frameFrom(assets.getEnemyAssets().getWalkingSprites(), enemy.getAnimationFrame());
            case RUNNING -> frameFrom(assets.getEnemyAssets().getRunningSprites(), enemy.getAnimationFrame());
            case AERIAL -> enemyAerialFrame(assets);
            case HURT -> assets.getEnemyAssets().getHurtSprite();
            case IDLE -> frameFrom(assets.getEnemyAssets().getIdleSprites(), enemy.getAnimationFrame());
            default -> null;
        };
    }

    private Image enemyAerialFrame(GameAssets assets) {
        Image[] running = assets.getEnemyAssets().getRunningSprites();
        if (running != null && running.length > 4) {
            return running[Math.min(4, running.length - 1)];
        }
        Image[] idle = assets.getEnemyAssets().getIdleSprites();
        return frameFrom(idle, 0);
    }

    private Image frameFrom(Image[] sprites, int frameIndex) {
        if (sprites == null || sprites.length == 0) return null;
        int idx = Math.floorMod(frameIndex, sprites.length);
        return sprites[idx];
    }
}
