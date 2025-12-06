package uk.ac.nott.cs.comp2013.utils;


/** Centralizes all game constants to eliminate magic numbers.*/
public final class GameConstants {

    private GameConstants() {

    }

    /** Window width in pixels. */
    public static final int VIEW_WIDTH = 1280;
    /** Window height in pixels. */
    public static final int VIEW_HEIGHT = 720;
    /** Window title text. */
    public static final String WINDOW_TITLE = "Cyborg Platform V2";
    /** Whether window can be resized. */
    public static final boolean WINDOW_RESIZABLE = false;

    /** X coordinate threshold for winning the game. */
    public static final int WIN_X = 8000;
    /** Y coordinate threshold for death. */
    public static final int VOID_Y = 900;
    /** Target frame rate. */
    public static final int TARGET_FPS = 60;

    /** Default gravity acceleration. */
    public static final double GRAVITY = 0.5;
    /** Maximum fall speed. */
    public static final double MAX_FALL_SPEED = 14;
    /** First jump velocity. */
    public static final double FIRST_JUMP_VELOCITY = -12;
    /** Double jump velocity. */
    public static final double DOUBLE_JUMP_VELOCITY = -10;
    /** Player maximum horizontal speed. */
    public static final int PLAYER_MAX_SPEED = 5;

    /** Cooldown between shots in milliseconds. */
    public static final long SHOOT_COOLDOWN_MS = 500;
    /** Player damage invulnerability duration in milliseconds. */
    public static final long PLAYER_DAMAGE_COOLDOWN_MS = 1000;
    /** Enemy damage cooldown duration in milliseconds. */
    public static final long ENEMY_DAMAGE_COOLDOWN_MS = 300;
    /** Bullet travel speed. */
    public static final int BULLET_SPEED = 10;
    /** Bullet maximum travel distance. */
    public static final int BULLET_MAX_DISTANCE = 600;

    /** Enemy patrol speed (walking). */
    public static final double ENEMY_PATROL_SPEED = 1;
    /** Enemy chase speed (running). */
    public static final double ENEMY_RUN_SPEED = 3;
    /** Enemy detection radius for player. */
    public static final int ENEMY_DETECTION_RADIUS = 400;
    /** Distance at which enemy starts running. */
    public static final int ENEMY_RUN_RADIUS = 200;
    /** Maximum vertical delta to detect player. */
    public static final int ENEMY_VERTICAL_DETECTION_DELTA = 100;
    /** Horizontal distance under which enemy stops. */
    public static final int ENEMY_STOP_DISTANCE = 20;
    /** Horizontal gap needed before enemy will jump toward player. */
    public static final int ENEMY_JUMP_HORIZONTAL_THRESHOLD = 40;
    /** Enemy jump velocity when leaping toward player. */
    public static final int ENEMY_JUMP_VELOCITY = -8;
    /** Factor to slow enemies when damaged. */
    public static final double ENEMY_DAMAGE_SLOW_FACTOR = 2.0;

    /** Animation frame delay for idle state (ms). */
    public static final long IDLE_FRAME_DELAY = 250;
    /** Animation frame delay for walking/running state (ms). */
    public static final long MOVEMENT_FRAME_DELAY = 180;
    /** Player idle animation frame count. */
    public static final int PLAYER_IDLE_FRAMES = 4;
    public static final int PLAYER_RUNNING_FRAMES = 6;
    public static final int PLAYER_HURT_FRAMES = 2;
    /** Enemy idle animation frame count. */
    public static final int ENEMY_IDLE_FRAMES = 8;
    public static final int ENEMY_WALKING_FRAMES = 8;
    public static final int ENEMY_RUNNING_FRAMES = 7;
    /** Path to the map file. */
    public static final String MAP_FILE = "/Maps.txt";
    /** Path to the font file. */
    public static final String FONT_PATH = "/Font/font.TTF";
    public static final int PLAYER_SPAWN_X = 20;
    public static final int PLAYER_SPAWN_Y = 300;
    public static final int TILE_SIZE = 48;
}
