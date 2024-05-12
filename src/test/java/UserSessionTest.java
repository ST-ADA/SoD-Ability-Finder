import com.stada.sodabilityfinder.objects.User;
import com.stada.sodabilityfinder.objects.UserSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserSessionTest {

    private UserSession userSession;
    private User user;

    @BeforeEach
    public void setUp() {
        userSession = UserSession.getInstance();
        user = new User("TestUser", "TestPassword", false);
    }

    @Test
    public void testGetInstance() {
        UserSession instance = UserSession.getInstance();
        assertNotNull(instance);
        assertEquals(userSession, instance);
    }

    @Test
    public void testSetUser() {
        userSession.setUser(user);
        assertEquals(user, userSession.getUser());
    }

    @Test
    public void testGetUser() {
        userSession.setUser(user);
        User result = userSession.getUser();
        assertEquals(user, result);
    }

    @Test
    public void testCleanUserSession() {
        userSession.setUser(user);
        userSession.cleanUserSession();
        assertNull(userSession.getUser());
        assertNull(UserSession.getInstance().getUser());
    }
}