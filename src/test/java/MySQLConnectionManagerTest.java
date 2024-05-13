import com.stada.sodabilityfinder.connector.MySQLConnectionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class MySQLConnectionManagerTest {

    @Mock
    private Connection connection;

    private MySQLConnectionManager connectionManager;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        connectionManager = new MySQLConnectionManager();
    }

    @Test
    public void testEstablishConnection() throws SQLException {
        connectionManager.establishConnection();
        Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
        assertFalse(connection.isClosed());
    }
}