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
  public void testWriteConwayToad() {
      javafxRun(() -> mainController.initializeButtonMenu());
      inputTest = lookup("#inputTextBox").query();
      inputTest.setText("ConwayStatesToad");
      press(KeyCode.ENTER);
      javafxRun(() ->  mainController.displayInfo("ConwayStatesToad"));
      javafxRun(() ->  mainController.initializeSimulation());
      View testView = mainController.getMainView();
      FrontEndCell currentCell = testView.getFrontEndCellGrid().get(7).get(10);
      assertEquals("0x000000ff", currentCell.getCellColor());
      mainController.handleKeyInput(KeyCode.S);
      javafxRun(() -> mainController.initializeButtonMenu());
      inputTest = lookup("#inputTextBox").query();
      inputTest.setText("ConwayStatesToad");
      press(KeyCode.ENTER);
      javafxRun(() ->  mainController.displayInfo("ConwayStatesToad"));
      javafxRun(() ->  mainController.initializeSimulation());
      View testViewOut = mainController.getMainView();
      currentCell = testViewOut.getFrontEndCellGrid().get(7).get(10);
      assertEquals("0xffffffff", currentCell.getCellColor());
  }

  @Test
  public void testWriteConwayPulsar() {
      javafxRun(() -> mainController.initializeButtonMenu());
      inputTest = lookup("#inputTextBox").query();
      inputTest.setText("ConwayStatesPulsar");
      press(KeyCode.ENTER);
      javafxRun(() ->  mainController.displayInfo("ConwayStatesPulsar"));
      javafxRun(() ->  mainController.initializeSimulation());
      View testView = mainController.getMainView();
      FrontEndCell currentCell = testView.getFrontEndCellGrid().get(4).get(5);
      assertEquals("0x000000ff", currentCell.getCellColor());
      mainController.handleKeyInput(KeyCode.S);
      javafxRun(() -> mainController.initializeButtonMenu());
      inputTest = lookup("#inputTextBox").query();
      inputTest.setText("ConwayStatesPulsar");
      press(KeyCode.ENTER);
      javafxRun(() ->  mainController.displayInfo("ConwayStatesPulsar"));
      javafxRun(() ->  mainController.initializeSimulation());
      View testViewOut = mainController.getMainView();
      currentCell = testViewOut.getFrontEndCellGrid().get(4).get(5);
      assertEquals("0xffffffff", currentCell.getCellColor());
  }
}
