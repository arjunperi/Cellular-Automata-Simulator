package Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import Controller.Controller;
import View.View;
import View.FrontEndCell;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class CSVWriteTest extends DukeApplicationTest {

    private Button buttonTest;
    private Controller mainController;
    private TextField inputTest;

    public void start(final Stage stage) {
        mainController = new Controller();
        stage.setScene(mainController.setupScene());
        stage.show();
    }

    @Test
    public void testWriteToCSV(){
        javafxRun(() -> mainController.initializeSplashMenu());
        inputTest = lookup("#inputTextBox").query();
        inputTest.setText("TestSaveBlinker");
        press(KeyCode.ENTER);
        buttonTest = lookup("#TestSaveBlinker").queryButton();
        clickOn(buttonTest);

        javafxRun(() -> mainController.updateColorStateMapping("0", "PINK"));
        javafxRun(() -> mainController.gameStep());

        buttonTest = lookup("#Save").queryButton();
        clickOn(buttonTest);
        TextField titleTest = lookup("#titleInput").query();
        titleTest.setText("TestSaveBlinker");
        Button okTest = lookup("#SaveOK").queryButton();
        clickOn(okTest);

        javafxRun(() -> mainController.initializeSplashMenu());
        inputTest = lookup("#inputTextBox").query();
        inputTest.setText("TestSaveBlinker");
        press(KeyCode.ENTER);
        javafxRun(() -> mainController.displayInfo("TestSaveBlinker"));
        assertEquals("0xffc0cbff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    }
}
