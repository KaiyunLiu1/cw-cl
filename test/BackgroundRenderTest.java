import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Initial stub test for BackgroundRenderer.
 */
public class BackgroundRenderTest {

    /** Stub Image object replacing javafx.scene.image.Image */
    private static class ImageStub { }

    /** Stub GraphicsContext object replacing javafx.scene.canvas.GraphicsContext  */
    private static class GraphicsContextStub { }

    /** Local stub for the planned BackgroundRenderer view class. */
    private static class BackgroundRenderer {
        private final ImageStub[] layers;

        BackgroundRenderer(ImageStub[] layers) {
            this.layers = layers;
        }

        void render(GraphicsContextStub gc, double cameraOffset, double viewportWidth) {
            // stub — real parallax drawing will be implemented in Task 4
        }
    }

    @Test
    @Disabled("Stub test — actual rendering behaviour implemented in Task 4")
    void render_stub() {


        ImageStub[] layers = new ImageStub[4];

        // Create fake renderer with stub layers
        BackgroundRenderer renderer = new BackgroundRenderer(layers);

        // Create stub graphics context
        GraphicsContextStub gc = new GraphicsContextStub();

        // Call method
        renderer.render(gc, 0.0, 1280);

        assertTrue(true);
    }
}
