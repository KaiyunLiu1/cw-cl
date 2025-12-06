package uk.ac.nott.cs.comp2013.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import uk.ac.nott.cs.comp2013.model.Bullet;
import uk.ac.nott.cs.comp2013.model.Enemy;
import uk.ac.nott.cs.comp2013.model.GameModel;
import uk.ac.nott.cs.comp2013.model.Player;

import java.util.List;

/**
 * Main game view.
 *
 * <p>This class is responsible for all rendering operations. It receives
 * model data from the controller and renders it to a JavaFX Canvas.</p>
 */
public class GameViewFX extends Pane {

    private final Canvas canvas;
    private final GraphicsContext gc;
    private final Font hudFont;
    private final Font bigFont;

    private final GameRenderer renderer;
    private GameInputHandler inputHandler;

    /**
     * Constructs the game view with required renderers and dimensions.
     *
     * @param renderer           unified renderer
     * @param assets             game assets
     * @param width              view width
     * @param height             view height
     */
    public GameViewFX(GameRenderer renderer,
                      GameAssets assets,
                      double width,
                      double height) {
        this.renderer = renderer;
        this.hudFont = (assets != null && assets.getHudFont() != null) ? assets.getHudFont() : Font.font("Arial", 20);
        this.bigFont = (assets != null && assets.getTitleFont() != null) ? assets.getTitleFont() : Font.font("Arial", 60);

        this.canvas = new Canvas(width, height);
        this.gc = canvas.getGraphicsContext2D();
        this.gc.setImageSmoothing(false);

        getChildren().add(canvas);
        installInputHandlers();
    }

    /**
     * Binds an input handler to receive key events.
     *
     * @param inputHandler the handler to bind
     */
    public void bindInputHandler(GameInputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    /**
     * Renders the complete game frame.
     *
     * @param model         game model
     * @param cameraOffset  camera X offset
     * @param gameWon       whether game is won
     * @param formattedTime formatted time string
     * @param assets        game assets
     */
    public void render(GameModel model, int cameraOffset, boolean gameWon,
                       String formattedTime, GameAssets assets) {
        gc.setFill(Color.BLACK);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        int deathCounter = model.getDeathCounter();

        if (!gameWon) {
            renderGameScene(model, cameraOffset, assets);
            renderHUD(model.getPlayer(), formattedTime, assets, deathCounter);
        } else {
            renderEndScreen(assets, deathCounter);
        }
    }

    /**
     * Installs keyboard event handlers.
     */
    private void installInputHandlers() {
        setFocusTraversable(true);
        setOnKeyPressed(event -> {
            if (inputHandler != null) {
                inputHandler.onKeyPressed(event);
            }
        });
        setOnKeyReleased(event -> {
            if (inputHandler != null) {
                inputHandler.onKeyReleased(event);
            }
        });
    }

    /**
     * Renders the main game scene (background, map, entities).
     */
    private void renderGameScene(GameModel model, int cameraOffset, GameAssets assets) {
        if (renderer == null) {
            return;
        }
        gc.save();
        gc.translate(-cameraOffset, 0);

        renderer.renderBackground(gc, cameraOffset, model.getTileMap().getMapWidth());
        renderer.renderTileMap(gc, model.getTileMap());
        renderPlayer(model.getPlayer(), assets);
        renderEnemies(model.getEnemies(), assets);
        renderBullets(model.getBullets(), assets);
        renderWorldPrompts();

        gc.restore();
    }

    /** Renders the player sprite.*/
    private void renderPlayer(Player player, GameAssets assets) {
        if (player == null || assets == null) return;

        Image sprite = selectPlayerSprite(player, assets);
        if (sprite != null) {
            renderer.drawFacing(gc, sprite, player.getX(), player.getY(),
                    player.isFacingForwards());
        }


        Image cloud = assets.getCloudImage();
        if (player.hasRecentlyJumped() && cloud != null) {
            gc.drawImage(cloud, player.getJumpStartX(), player.getJumpStartY() + 42);
        }
    }

    /** Renders all enemy sprites.*/
    private void renderEnemies(List<Enemy> enemies, GameAssets assets) {
        if (assets == null) return;

        for (Enemy enemy : enemies) {
            if (!enemy.isAlive()) {
                continue;
            }
            Image sprite = selectEnemySprite(enemy, assets);
            if (sprite != null) {
                renderer.drawFacing(gc, sprite, enemy.getX(), enemy.getY(),
                        enemy.isFacingForwards());
            }
        }
    }

    /** Renders all bullets.*/
    private void renderBullets(List<Bullet> bullets, GameAssets assets) {
        if (assets == null) return;

        Image sprite = assets.getPlayerBulletImage();
        if (sprite == null) return;

        for (Bullet bullet : bullets) {
            renderer.drawFacing(gc, sprite, bullet.getX(), bullet.getY(),
                    bullet.getSpeed() > 0);
        }
    }

    /** Renders the HUD (health, ammo, timer, attempts).*/
    private void renderHUD(Player player, String formattedTime, GameAssets assets, int deathCounter) {
        gc.setFill(Color.BLACK);
        gc.setFont(bigFont);

        // Health hearts
        Image heart = (assets != null) ? assets.getHeartImage() : null;
        if (heart != null) {
            if (player.getHealth() > 0) gc.drawImage(heart, 20, 20);
            if (player.getHealth() > 1) gc.drawImage(heart, 89, 20);
            if (player.getHealth() > 2) gc.drawImage(heart, 158, 20);
        }

        // Ammo
        Image ammoBox = (assets != null) ? assets.getAmmoBoxImage() : null;
        if (ammoBox != null) {
            gc.drawImage(ammoBox, 1040, 20);
        }
        gc.fillText(String.valueOf(player.getAmmo()), 1130, 68);

        // Timer
        gc.fillText(formattedTime, 510, 68);

        // Attempts
        gc.setFont(hudFont);
        gc.fillText("ATTEMPTS: " + deathCounter, 20, 110);
    }

    /** Renders the victory end screen.*/
    private void renderEndScreen(GameAssets assets, int deathCounter) {
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (renderer != null) {
            if (renderer.getWinBackgroundFront() != null) {
                gc.drawImage(renderer.getWinBackgroundFront(), 0, 0);
            }
            if (renderer.getWinBackgroundBack() != null) {
                gc.drawImage(renderer.getWinBackgroundBack(), 0, 0);
            }
        }

        gc.setFill(Color.BLACK);
        gc.setFont(bigFont);

        gc.fillText("You Won!", 460, 220);
        gc.fillText("It Took You", 375, 300);
        gc.fillText(deathCounter + " Attempts", 405, 380);
        gc.fillText("Press ESC to Restart,", 150, 460);
        gc.fillText("Or ENTER to exit", 270, 540);
    }

    /** Renders world prompts .*/
    private void renderWorldPrompts() {
        gc.setFill(Color.BLACK);
        gc.setFont(hudFont);

        String[] controls = {
                "Use WASD to Move",
                "You can Double Jump",
                "Use SPACE to Shoot",
                "Press ESCAPE to Restart"
        };

        int y = 250;
        for (String line : controls) {
            gc.fillText(line, 470, y);
            y += 30;
        }

        gc.fillText("This is The Winner Tunnel", 6800, 310);
        gc.fillText("Just Keep Walking!", 6800, 340);
    }

    /**
     * Selects the appropriate sprite for the player based on animation state.
     */
    private Image selectPlayerSprite(Player player, GameAssets assets) {
        return switch (player.getState().getAnimationState()) {
            case IDLE -> frameFrom(assets.getPlayerIdleSprites(), player.getAnimationFrame());
            case RUNNING, WALKING -> frameFrom(assets.getPlayerRunningSprites(), player.getAnimationFrame());
            case AERIAL -> selectAerialPlayerFrame(assets);
            case HURT -> frameFrom(assets.getPlayerHurtSprites(), player.getAnimationFrame());
            case SHOOTING -> assets.getPlayerShootingSprite();
            default -> null;
        };
    }

    /**
     * Selects aerial frame for player.
     */
    private Image selectAerialPlayerFrame(GameAssets assets) {
        Image[] running = assets.getPlayerRunningSprites();
        if (running != null && running.length > 5) {
            return running[Math.min(5, running.length - 1)];
        }
        return frameFrom(assets.getPlayerIdleSprites(), 0);
    }

    /**
     * Selects the appropriate sprite for an enemy based on animation state.
     */
    private Image selectEnemySprite(Enemy enemy, GameAssets assets) {
        return switch (enemy.getState().getAnimationState()) {
            case IDLE -> frameFrom(assets.getEnemyIdleSprites(), enemy.getAnimationFrame());
            case WALKING -> frameFrom(assets.getEnemyWalkingSprites(), enemy.getAnimationFrame());
            case RUNNING -> frameFrom(assets.getEnemyRunningSprites(), enemy.getAnimationFrame());
            case AERIAL -> selectAerialEnemyFrame(assets);
            case HURT -> assets.getEnemyHurtSprite();
            default -> null;
        };
    }

    /**
     * Selects aerial frame for enemy.
     */
    private Image selectAerialEnemyFrame(GameAssets assets) {
        Image[] running = assets.getEnemyRunningSprites();
        if (running != null && running.length > 4) {
            return running[Math.min(4, running.length - 1)];
        }
        return frameFrom(assets.getEnemyIdleSprites(), 0);
    }

    /**
     * Returns a frame from a sprite array with bounds checking.
     */
    private Image frameFrom(Image[] sprites, int frameIndex) {
        if (sprites == null || sprites.length == 0) {
            return null;
        }
        int idx = Math.floorMod(frameIndex, sprites.length);
        return sprites[idx];
    }
}
