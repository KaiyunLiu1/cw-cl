import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Initial tests for the HUD rendering in the refactored GameView.
 */
public class GameViewTest {

    @Test
    @Disabled("Stub test — HUD heart rendering will be implemented and asserted in Task 4.")
    @DisplayName("HUD: draws hearts")
    void drawHeartsBasedOnHealth() {
        // TODO (Task 4): Assert: verify the number of heart images drawn for each health value.
    }

    /**
     * Draws the ammo box icon at a fixed position.
     * This test is set to verify that both the icon and the correct
     * ammo number text are rendered.
     */
    @Test
    @Disabled("Stub test — ammo HUD rendering will be implemented in Task 4.")
    @DisplayName("HUD: shows ammo icon and current ammo count")
    void drawAmmoBoxAndCount() {
        // TODO (Task 4): Assert: the test graphics context received one ammo box draw call.
    }

    /**
     * This function displays the elapsed game time.
     */
    @Test
    @Disabled("Stub test — timer formatting helper will be implemented in Task 4.")
    @DisplayName("HUD: formats the game timer in mm:ss format based on Game.startTime")
    void drawGameTimer() {
        // TODO (Task 4)
    }

    /** The HUD shows the control instructions such as "Use WASD to Move".
     */
    @Test
    @Disabled("Stub test — control hint text rendering will be asserted in Task 4.")
    @DisplayName("Displays control hint text (movement, jump, shoot, restart)")
    void drawControlHints() {
        // TODO (Task 4): Asserts the test graphics context contains drawString calls.
    }

    /**
     Near the "winner tunnel" area of the map, the methods draws the
     *   hint strings.
     */
    @Test
    @Disabled("Stub test — winner tunnel hint text will be asserted in Task 4.")
    @DisplayName("Shows winner tunnel hint text near the tunnel location")
    void drawWinnerTunnelHints() {
        // TODO (Task 4): Asserts the winner tunnel hint text .
    }

    /**
     * This method displays the attempt counter.
     */
    @Test
    @Disabled("Stub test — attempts counter rendering will be implemented in Task 4.")
    @DisplayName("Shows attempts counter based on Player.deathCounter")
    void drawAttemptsCounter() {
        // TODO (Task 4): Asserts the test graphics context drawing number of attempts.
    }
}
