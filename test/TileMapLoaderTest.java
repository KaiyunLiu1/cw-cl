import model.TileMapLoader;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;



public class TileMapLoaderTest {

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
