
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;



public class TileMapLoaderTest {

    /**
     * Local stub version of TileMapLoader for Task 3.
     * The real implementation will be introduced in Task 4.
     */
    private static class TileMapLoader {
        public void load(String path) {
            // stub — no real loading in Task 3
        }
    }


    @Test
    @Disabled("Stub test — real map loading behaviour will be implemented and tested in Task 4")
    void load_stub() throws Exception {
        // Arrange: create the new loader instance
        TileMapLoader loader = new TileMapLoader();


        loader.load("dummy-path-does-not-need-to-exist.txt");

        // Assert: placeholder assertion for initial test structure
        assertTrue(true);
    }
}
