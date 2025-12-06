package uk.ac.nott.cs.comp2013.app;

import javafx.scene.image.Image;
import javafx.scene.text.Font;
import uk.ac.nott.cs.comp2013.view.GameAssets;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Loads all game images and fonts.
 */
public final class GameAssetsLoader {

    /** Default HUD font size. */
    private static final double HUD_FONT_SIZE = 20;
    /** Default title font size. */
    private static final double TITLE_FONT_SIZE = 60;


    public GameAssets loadAll() {
        Image[] playerIdle = loadPlayerIdle();
        Image[] playerRunning = loadPlayerRunning();
        Image[] playerHurt = loadPlayerHurt();
        Image playerShooting = loadImage("/Sprites/Player/shoot/shootingSprite.png");
        Image playerBullet = loadImage("/Sprites/Player/shoot/bullet.png");
        Image heart = loadImage("/Sprites/heart.png");
        Image ammoBox = loadImage("/Sprites/ammo.png");
        Image cloud = loadImage("/Sprites/cloud.png");

        Image[] enemyIdle = loadEnemyIdle();
        Image[] enemyWalking = loadEnemyWalking();
        Image[] enemyRunning = loadEnemyRunning();
        Image enemyHurt = loadImage("/Sprites/Enemy/Hurt.png");
        Image[] backgrounds = loadBackgrounds();
        Image[] tiles = loadTiles();
        Font hudFont = loadFont("/Font/font.TTF", HUD_FONT_SIZE);
        Font titleFont = loadFont("/Font/font.TTF", TITLE_FONT_SIZE);

        Image winFront = (backgrounds != null && backgrounds.length > 0) ? backgrounds[0] : null;
        Image winBack = (backgrounds != null && backgrounds.length > 1) ? backgrounds[1] : null;

        return new GameAssets(
                playerIdle,
                playerRunning,
                playerHurt,
                playerShooting,
                playerBullet,
                heart,
                ammoBox,
                cloud,
                enemyIdle,
                enemyWalking,
                enemyRunning,
                enemyHurt,
                tiles,
                backgrounds,
                winFront,
                winBack,
                hudFont,
                titleFont
        );
    }

    private Image[] loadPlayerIdle() {
        return new Image[]{
                loadImage("/Sprites/Player/idle/Cyborg_idle_1.png"),
                loadImage("/Sprites/Player/idle/Cyborg_idle_2.png"),
                loadImage("/Sprites/Player/idle/Cyborg_idle_3.png"),
                loadImage("/Sprites/Player/idle/Cyborg_idle_4.png")
        };
    }

    private Image[] loadPlayerRunning() {
        return new Image[]{
                loadImage("/Sprites/Player/run/Cyborg_run_1.png"),
                loadImage("/Sprites/Player/run/Cyborg_run_2.png"),
                loadImage("/Sprites/Player/run/Cyborg_run_3.png"),
                loadImage("/Sprites/Player/run/Cyborg_run_4.png"),
                loadImage("/Sprites/Player/run/Cyborg_run_5.png"),
                loadImage("/Sprites/Player/run/Cyborg_run_6.png")
        };
    }

    private Image[] loadPlayerHurt() {
        return new Image[]{
                loadImage("/Sprites/Player/hurt/Cyborg_hurt_1.png"),
                loadImage("/Sprites/Player/hurt/Cyborg_hurt_2.png")
        };
    }

    private Image[] loadEnemyIdle() {
        return new Image[]{
                loadImage("/Sprites/Enemy/Idle/Idle_1.png"),
                loadImage("/Sprites/Enemy/Idle/Idle_2.png"),
                loadImage("/Sprites/Enemy/Idle/Idle_3.png"),
                loadImage("/Sprites/Enemy/Idle/Idle_4.png"),
                loadImage("/Sprites/Enemy/Idle/Idle_5.png"),
                loadImage("/Sprites/Enemy/Idle/Idle_6.png"),
                loadImage("/Sprites/Enemy/Idle/Idle_7.png"),
                loadImage("/Sprites/Enemy/Idle/Idle_8.png")
        };
    }

    private Image[] loadEnemyWalking() {
        return new Image[]{
                loadImage("/Sprites/Enemy/Walking/Walk_1.png"),
                loadImage("/Sprites/Enemy/Walking/Walk_2.png"),
                loadImage("/Sprites/Enemy/Walking/Walk_3.png"),
                loadImage("/Sprites/Enemy/Walking/Walk_4.png"),
                loadImage("/Sprites/Enemy/Walking/Walk_5.png"),
                loadImage("/Sprites/Enemy/Walking/Walk_6.png"),
                loadImage("/Sprites/Enemy/Walking/Walk_7.png"),
                loadImage("/Sprites/Enemy/Walking/Walk_8.png")
        };
    }

    private Image[] loadEnemyRunning() {
        return new Image[]{
                loadImage("/Sprites/Enemy/Running/Run_1.png"),
                loadImage("/Sprites/Enemy/Running/Run_2.png"),
                loadImage("/Sprites/Enemy/Running/Run_3.png"),
                loadImage("/Sprites/Enemy/Running/Run_4.png"),
                loadImage("/Sprites/Enemy/Running/Run_5.png"),
                loadImage("/Sprites/Enemy/Running/Run_6.png"),
                loadImage("/Sprites/Enemy/Running/Run_7.png")
        };
    }

    private Image[] loadBackgrounds() {
        return new Image[]{
                loadImage("/Background/1_Background.png"),
                loadImage("/Background/2_Background.png"),
                loadImage("/Background/3_Background.png"),
                loadImage("/Background/4_Background.png")
        };
    }

    private Image[] loadTiles() {
        return new Image[]{
                loadImage("/Tiles/1_FrameTopLeftCorner.png"),
                loadImage("/Tiles/2_FrameTopRightCorner.png"),
                loadImage("/Tiles/3_FrameBottomLeftCorner.png"),
                loadImage("/Tiles/4_FrameBottomRightCorner.png"),
                loadImage("/Tiles/5_FrameTopMid.png"),
                loadImage("/Tiles/6_FrameLeftMid.png"),
                loadImage("/Tiles/7_FrameRightMid.png"),
                loadImage("/Tiles/8_FrameBottomMod.png"),
                loadImage("/Tiles/9_FrameMid.png"),
                loadImage("/Tiles/A_Box.png"),
                loadImage("/Tiles/B_HalfSlab.png"),
                loadImage("/Tiles/C_IndustrialTabLeft.png"),
                loadImage("/Tiles/D_IndustrialSlabMid.png"),
                loadImage("/Tiles/E_IndustrialSlabRight.png"),
                loadImage("/Tiles/F_LightPole.png"),
                loadImage("/Tiles/G_LightTop.png"),
                loadImage("/Tiles/H_TreadLeft.png"),
                loadImage("/Tiles/I_TreadMid.png"),
                loadImage("/Tiles/J_TreadRight.png")
        };
    }

    /** Loads an image from classpath or filesystem.*/
    private Image loadImage(String path) {

        var url = GameAssetsLoader.class.getResource(path);
        if (url != null) {
            return new Image(url.toExternalForm());
        }


        String normalized = path.startsWith("/") ? path.substring(1) : path;
        Path filePath = Paths.get("src", "main", "resources", normalized);
        if (Files.exists(filePath)) {
            try (InputStream in = Files.newInputStream(filePath)) {
                return new Image(in);
            } catch (IOException e) {
                throw new IllegalArgumentException("Failed to load resource: " + path, e);
            }
        }

        throw new IllegalArgumentException("Missing resource: " + path);
    }


    private Font loadFont(String path, double size) {
        // Try classpath first
        InputStream classpathStream = GameAssetsLoader.class.getResourceAsStream(path);
        if (classpathStream != null) {
            try (InputStream stream = classpathStream) {
                Font font = Font.loadFont(stream, size);
                if (font != null) {
                    return font;
                }
            } catch (IOException e) {

            }
        }

        String normalized = path.startsWith("/") ? path.substring(1) : path;
        Path filePath = Paths.get("src", "main", "resources", normalized);
        if (Files.exists(filePath)) {
            try (InputStream stream = Files.newInputStream(filePath)) {
                Font font = Font.loadFont(stream, size);
                if (font != null) {
                    return font;
                }
            } catch (IOException e) {

            }
        }
        return Font.font("Arial", size);
    }
}
