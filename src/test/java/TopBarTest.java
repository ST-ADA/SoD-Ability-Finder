import com.stada.sodabilityfinder.screens.TopBar;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class TopBarTest extends ApplicationTest {

    private TopBar topBar;
    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        topBar = new TopBar();
    }

    @Test
    public void testCreateTopBar() {
        HBox topBarHBox = topBar.createTopBar();

        assertNotNull(topBarHBox);
        assertEquals(5, topBarHBox.getChildren().size());

        assertTrue(topBarHBox.getChildren().get(0) instanceof ImageView); // logo
        assertTrue(topBarHBox.getChildren().get(2) instanceof Label); // topLabel
        assertTrue(topBarHBox.getChildren().get(4) instanceof Hyperlink); // adminLink
    }
}
