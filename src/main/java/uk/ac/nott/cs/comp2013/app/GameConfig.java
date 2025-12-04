package uk.ac.nott.cs.comp2013.app;

/**
 * Centralised configuration for map path, window size, and window behaviour.
 */
public final class GameConfig {

    private GameConfig() {}

    public static final String MAP_FILE = "/Maps.txt";
    public static final int VIEW_WIDTH = 1280;
    public static final int VIEW_HEIGHT = 720;
    public static final String WINDOW_TITLE = "Cyborg Platform V2";
    public static final boolean WINDOW_RESIZABLE = false;
}
