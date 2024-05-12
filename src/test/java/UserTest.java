import com.stada.sodabilityfinder.objects.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUser() {
        String username = "TestUser";
        String password = "TestPassword";
        boolean isAdmin = false;

        User user = new User(username, password, isAdmin);

        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(isAdmin, user.isAdmin());
    }
}