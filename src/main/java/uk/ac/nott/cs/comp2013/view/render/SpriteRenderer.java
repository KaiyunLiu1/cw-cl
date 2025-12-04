package uk.ac.nott.cs.comp2013.view.render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Small rendering helper to draw sprites facing either direction without
 * duplicating flip/translate logic across views.
 */
public final class SpriteRenderer {

    private SpriteRenderer() {}

    /**
     * Draws a sprite either normally (facing right) or mirrored horizontally.
     *
     * @param g            canvas context
     * @param sprite       image to draw
     * @param x            world-space x
     * @param y            world-space y
     * @param facingRight  true to draw normally; false to mirror on X
     */
    public static void drawFacing(GraphicsContext g, Image sprite, double x, double y, boolean facingRight) {
        if (sprite == null) return;

        if (facingRight) {
            g.drawImage(sprite, x, y);
            return;
        }

        g.save();
        g.translate(x + sprite.getWidth(), y);
        g.scale(-1, 1);
        g.drawImage(sprite, 0, 0);
        g.restore();
    }
}
