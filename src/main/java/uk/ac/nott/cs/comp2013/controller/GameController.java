package uk.ac.nott.cs.comp2013.controller;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uk.ac.nott.cs.comp2013.app.GameAssets;
import uk.ac.nott.cs.comp2013.app.GameConfig;
import uk.ac.nott.cs.comp2013.model.AnimationState;
import uk.ac.nott.cs.comp2013.model.GameModel;
import uk.ac.nott.cs.comp2013.model.Player;
import uk.ac.nott.cs.comp2013.physicsEngine.EnemyAI;
import uk.ac.nott.cs.comp2013.physicsEngine.EnemyAnimator;
import uk.ac.nott.cs.comp2013.physicsEngine.PlayerAnimator;
import uk.ac.nott.cs.comp2013.physicsEngine.PlayerCombat;
import uk.ac.nott.cs.comp2013.physicsEngine.PlayerMovement;
import uk.ac.nott.cs.comp2013.physicsEngine.PlayerPhysics;
import uk.ac.nott.cs.comp2013.view.GameInputHandler;
import uk.ac.nott.cs.comp2013.view.GameViewFX;

/**
 * Central coordinator of the new MVC structure.
 * <p>
 * Responsibilities:
 * </p>
 * <ul>
 *     <li>Translate key events into model commands.</li>
 *     <li>Advance physics, enemy AI and bullet collisions each frame.</li>
 *     <li>Provide read-only state to the view layer.</li>
 * </ul>
 */
public class GameController implements GameInputHandler {

    private static final int WIN_X = 8000;

    private final GameModel model;
    private final PlayerController playerController;
    private final EnemyController enemyController;
    private final BulletController bulletController;
    private final PlayerCombat playerCombat;
    private final GameAssets assets;
    private GameViewFX view;
    private AnimationTimer loop;

    private boolean moveLeft;
    private boolean moveRight;
    private boolean jump;
    private boolean shoot;

    private boolean gameWon;
    private int cameraOffset;
    private long startTimeMillis = System.currentTimeMillis();
    private long lastNanos = System.nanoTime();

    public GameController(GameModel model, GameAssets assets) {
        this.model = model;
        this.assets = assets;
        Player player = model.getPlayer();
        PlayerPhysics playerPhysics = new PlayerPhysics(player);
        PlayerMovement playerMovement = new PlayerMovement(player);
        this.playerCombat = new PlayerCombat(player);
        PlayerAnimator playerAnimator = new PlayerAnimator(player, assets.getPlayerAssets());
        this.playerController = new PlayerController(player, playerPhysics, playerMovement, playerCombat, playerAnimator);
        this.enemyController = new EnemyController(new EnemyAI(new EnemyAnimator(assets.getEnemyAssets())));
        this.bulletController = new BulletController();
        initialisePlayerAppearance();
        this.playerController.snapToGround(model.getTileMap());
    }

    public void attachView(GameViewFX view) {
        this.view = view;
        this.view.bindInputHandler(this);
        startLoop();
    }

    private void startLoop() {
        loop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double deltaSeconds = (now - lastNanos) / 1_000_000_000.0;
                lastNanos = now;
                update(deltaSeconds);
                render();
            }
        };
        loop.start();
    }

    private void initialisePlayerAppearance() {
        model.getPlayer().getState().setAnimationState(AnimationState.IDLE);
        model.getPlayer().setAnimationFrame(0);
    }

    @Override
    public void onKeyPressed(KeyEvent event) {
        KeyCode code = event.getCode();
        switch (code) {
            case A -> moveLeft = true;
            case D -> moveRight = true;
            case W -> jump = true;
            case SPACE -> shoot = true;
            case ESCAPE -> resetGame();
            case ENTER -> gameWon = true;
            default -> { }
        }
    }

    @Override
    public void onKeyReleased(KeyEvent event) {
        KeyCode code = event.getCode();
        switch (code) {
            case A -> moveLeft = false;
            case D -> moveRight = false;
            case W -> jump = false;
            case SPACE -> shoot = false;
            default -> { }
        }
    }

    public void update(double deltaSeconds) {
        if (gameWon) {
            return;
        }

        applyInputsToPlayer();
        playerController.update(model.getTileMap(), model.getBullets());

        enemyController.update(model.getEnemies(), model.getPlayer(), model.getTileMap(), playerCombat);
        bulletController.update(model.getBullets(), model.getTileMap().getBlocks(), model.getEnemies());
        checkWinOrDeath();
        updateCamera();
    }

    private void applyInputsToPlayer() {
        playerController.setMoveLeft(moveLeft);
        playerController.setMoveRight(moveRight);
        playerController.setJumpRequested(jump);
        playerController.setShootRequested(shoot);
        jump = false; // consume single jump request per update
    }

    private void checkWinOrDeath() {
        if (model.getPlayer().getX() > WIN_X) {
            gameWon = true;
            return;
        }

        if (model.getPlayer().isDead()) {
            Player.incrementDeathCounter();
            playerController.resetAfterRespawn(model.getTileMap());
            enemyController.resetEnemies(model.getEnemies(), model.getTileMap());
            model.getBullets().clear();
        }
    }

    private void updateCamera() {
        int camX = model.getPlayer().getX() - (GameConfig.VIEW_WIDTH - 30) / 2;
        int mapWidth = model.getTileMap().getMapWidth();
        camX = Math.max(0, Math.min(camX, mapWidth - GameConfig.VIEW_WIDTH - 30));
        cameraOffset = camX;
    }

    private void resetGame() {
        model.getBullets().clear();
        enemyController.resetEnemies(model.getEnemies(), model.getTileMap());
        playerController.resetAfterRespawn(model.getTileMap());
        moveLeft = moveRight = jump = shoot = false;
        startTimeMillis = System.currentTimeMillis();
        gameWon = false;
    }

    private void render() {
        if (view != null) {
            view.render(model, cameraOffset, gameWon, getFormattedTime(), assets);
        }
    }

    public String getFormattedTime() {
        long elapsed = System.currentTimeMillis() - startTimeMillis;
        long totalSeconds = elapsed / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

}
 
