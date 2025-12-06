package uk.ac.nott.cs.comp2013.controller;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uk.ac.nott.cs.comp2013.model.AnimationState;
import uk.ac.nott.cs.comp2013.model.GameModel;
import uk.ac.nott.cs.comp2013.model.Player;
import uk.ac.nott.cs.comp2013.physics.EnemyBehaviour;
import uk.ac.nott.cs.comp2013.physics.EnemyAnimator;
import uk.ac.nott.cs.comp2013.physics.PlayerAnimator;
import uk.ac.nott.cs.comp2013.physics.PlayerBehaviour;
import uk.ac.nott.cs.comp2013.utils.GameConstants;
import uk.ac.nott.cs.comp2013.view.GameAssets;
import uk.ac.nott.cs.comp2013.view.GameInputHandler;
import uk.ac.nott.cs.comp2013.view.GameViewFX;

/**
 * Central game controller.
 */
public class GameController implements GameInputHandler {

    private final GameModel model;
    private final PlayerController playerController;
    private final EnemyController enemyController;
    private final BulletController bulletController;
    private final PlayerBehaviour playerBehaviour;
    private final GameAssets assets;
    private GameViewFX view;
    private AnimationTimer loop;

    private boolean moveLeft;
    private boolean moveRight;
    private boolean jump;
    private boolean shoot;

    private boolean gameWon;
    private int cameraOffset;
    private long startTimeMillis;
    private long lastNanos;

    /** Constructs a GameController with the specified model and assets.*/
    public GameController(GameModel model, GameAssets assets) {
        this.model = model;
        this.assets = assets;
        this.startTimeMillis = System.currentTimeMillis();
        this.lastNanos = System.nanoTime();
        this.model.resetDeathCounter();

        Player player = model.getPlayer();
        this.playerBehaviour = new PlayerBehaviour(player);
        PlayerAnimator playerAnimator = new PlayerAnimator();

        this.playerController = new PlayerController(
                player, playerBehaviour, playerAnimator);

        EnemyAnimator enemyAnimator = new EnemyAnimator();
        this.enemyController = new EnemyController(new EnemyBehaviour(enemyAnimator));
        this.bulletController = new BulletController();

        initializePlayerState();
        playerController.snapToGround(model.getTileMap());
        enemyController.resetEnemies(model.getEnemies(), model.getTileMap());
    }

    /**
     * Attaches the view and starts the game loop.
     */
    public void attachView(GameViewFX view) {
        this.view = view;
        this.view.bindInputHandler(this);
        startGameLoop();
    }


    private void startGameLoop() {
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

    public void update(double deltaSeconds) {
        if (gameWon) {
            return;
        }

        applyInputsToPlayer();
        playerController.update(model.getTileMap(), model.getBullets());
        enemyController.update(model.getEnemies(), model.getPlayer(),
                model.getTileMap(), playerBehaviour);
        bulletController.update(model.getBullets(),
                model.getTileMap(), model.getEnemies());

        checkWinOrDeath();
        updateCamera();
    }

    private void render() {
        if (view != null) {
            view.render(model, cameraOffset, gameWon, getFormattedTime(), assets);
        }
    }

    @Override
    public void onKeyPressed(KeyEvent event) {
        KeyCode code = event.getCode();

        switch (code) {
            case A -> moveLeft = true;
            case D -> moveRight = true;
            case W -> jump = true;
            case SPACE -> shoot = true;
            case ESCAPE -> {
                if (gameWon) {
                    model.resetDeathCounter();
                } else {
                    model.incrementDeathCounter();
                }
                resetGame();
            }
            case ENTER -> {
                if (gameWon) {
                    System.exit(0);
                }
            }
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

    private void applyInputsToPlayer() {
        playerController.setMoveLeft(moveLeft);
        playerController.setMoveRight(moveRight);
        playerController.setJumpRequested(jump);
        playerController.setShootRequested(shoot);
        jump = false;
    }


    /**
     * Initializes player animation state.
     */
    private void initializePlayerState() {
        model.getPlayer().getState().setAnimationState(AnimationState.IDLE);
        model.getPlayer().setAnimationFrame(0);
    }

    private void checkWinOrDeath() {
        if (model.getPlayer().getX() > GameConstants.WIN_X) {
            gameWon = true;
            return;
        }

        if (model.getPlayer().isDead()) {
            model.incrementDeathCounter();
            playerController.resetAfterRespawn(model.getTileMap());
            enemyController.resetEnemies(model.getEnemies(), model.getTileMap());
            model.getBullets().clear();
        }
    }

    private void updateCamera() {
        int camX = model.getPlayer().getX() - (GameConstants.VIEW_WIDTH - 30) / 2;
        int mapWidth = model.getTileMap().getMapWidth();
        camX = Math.max(0, Math.min(camX, mapWidth - GameConstants.VIEW_WIDTH - 30));
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

    public String getFormattedTime() {
        long elapsed = System.currentTimeMillis() - startTimeMillis;
        long totalSeconds = elapsed / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
