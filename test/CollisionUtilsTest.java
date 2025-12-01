import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Initial stub tests for CollisionUtils
 * This test provides its own stub version of CollisionUtils and will be implemented in task4.
 */
public class CollisionUtilsTest {

    /**
     * Stubbed local version of CollisionUtils.
     */
    private static class CollisionUtils {
        public static boolean overlaps(int ax1, int ay1, int ax2, int ay2,
                                       int bx1, int by1, int bx2, int by2) {
            // stub — no logic
            return false;
        }
    }

    @Test
    @Disabled("Stub test — real collision logic will be tested in Task 4")
    void overlaps_stub() {
        CollisionUtils.overlaps(0, 0, 10, 10,
                20, 20, 30, 30);

        assertTrue(true);  // placeholder
    }
}
