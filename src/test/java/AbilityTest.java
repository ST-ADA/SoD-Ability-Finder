import com.stada.sodabilityfinder.objects.Ability;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbilityTest {

    private Ability ability;

    @BeforeEach
    public void setUp() {
        byte[] image = new byte[]{0, 1, 2, 3, 4, 5};
        ability = new Ability("Test Ability", image, "Test Description", "Test Location", 1);
    }

    @Test
    public void testGetName() {
        assertEquals("Test Ability", ability.getName());
    }

    @Test
    public void testGetImage() {
        byte[] expectedImage = new byte[]{0, 1, 2, 3, 4, 5};
        assertArrayEquals(expectedImage, ability.getImage());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Test Description", ability.getDescription());
    }

    @Test
    public void testGetLocation() {
        assertEquals("Test Location", ability.getLocation());
    }

    @Test
    public void testGetClassId() {
        assertEquals(1, ability.getClassId());
    }
}
