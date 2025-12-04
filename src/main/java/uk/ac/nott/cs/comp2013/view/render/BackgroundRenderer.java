package uk.ac.nott.cs.comp2013.view.render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * 背景渲染器：支持多层 parallax 背景 + HUD 图标 + 终局背景。
 */
public class BackgroundRenderer {

    private final Image[] layers;
    private final Image winBackgroundFront;
    private final Image winBackgroundBack;

    public BackgroundRenderer(Image[] layers,
                              Image winBackgroundFront,
                              Image winBackgroundBack) {
        this.layers = layers;
        this.winBackgroundFront = winBackgroundFront;
        this.winBackgroundBack = winBackgroundBack;
    }

    public void render(GraphicsContext gc, int cameraOffset, int mapWidth) {
        if (layers == null) return;

        double height = gc.getCanvas().getHeight();

        for (int i = 0; i < layers.length; i++) {
            Image img = layers[i];
            if (img == null) continue;

            double parallaxOffset = switch (i) {
                case 0 -> cameraOffset;          // front-most: moves with camera
                case 1 -> cameraOffset / 2.0;    // slightly slower
                case 2 -> cameraOffset / 4.0;    // mid parallax
                default -> cameraOffset / 16.0;  // far back
            };

            for (double mapX = 0; mapX < mapWidth; mapX += img.getWidth()) {
                double drawX = mapX + parallaxOffset;
                gc.drawImage(img, drawX, 0, img.getWidth(), height);
            }
        }
    }

    public Image getWinBackgroundFront() { return winBackgroundFront; }
    public Image getWinBackgroundBack() { return winBackgroundBack; }
}
