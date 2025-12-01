import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Stub tests for the future Player view . It will be implemented in task4.
 */

public class PlayerViewTest {

    /**
     * Planned test:
     * <ol>
     *     <li>Bind Player(model) to this PlayerView.</li>
     *     <li>Assert that the view renders three heart icons.</li>
     *     <li>Reduce the model's health to 2 and notify the view.</li>
     *     <li>Assert that the view now renders only two hearts.</li>
     * </ol>
     *
     */
    @Test
    @Disabled("Controller for v2 will be implemented in Task 4.")
    void viewUpdatesHeartsWhenHealthChanges() {
        // TODO: implement in Task 4.
    }

    /**
     * Planned test:
     * <ol>
     *     <li>Bind PlayerModel's "state" (idle, running, aerial, hurt, shooting)
     *         .</li>
     *     <li>Change the model's state to another.</li>
     *     <li>Assert that the view displays the appropriate animation.</li>
     * </ol>
     */
    @Test
    @Disabled("Controller for v2 will be implemented in Task 4.")
    void viewPlayerStateAnimation() {
        // TODO: implement in Task 4.
    }

    /**
     * Planned test:
     * <ol>
     *     <li>Simulate the player moving near the middle of a long level.</li>
     *     <li>Assert that the camera offset translation centres
     *         the player within the viewport.</li>
     *     <li>Move the player near the left and right edges of the map and assert
     *         that the camera stops scrolling past the boundaries.</li>
     * </ol>
     */
    @Test
    @Disabled("Controller for v2 will be implemented in Task 4.")
    void cameraFollowsPlayerAndClampsToWorldBounds() {
        // TODO: implement in Task 4.
    }
}
