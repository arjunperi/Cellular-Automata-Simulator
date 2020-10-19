package View;

import static org.junit.jupiter.api.Assertions.assertEquals;

import Controller.Controller;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class ViewTest extends DukeApplicationTest {

    private Button buttonTest;
    private Controller mainController;
    private TextField inputTest;

    public void start(final Stage stage) {
        mainController = new Controller();
        stage.setScene(mainController.setupScene());
        stage.show();
    }

  @Test
  public void testInitializeFrontendCells() {
      javafxRun(() -> mainController.initializeSplashMenu());
      inputTest = lookup("#inputTextBox").query();
      inputTest.setText("ConwayStatesPulsar");
      press(KeyCode.ENTER);
      javafxRun(() ->  mainController.displayInfo("ConwayStatesPulsar"));
      javafxRun(() ->  mainController.initializeSimulation("Test/ConwayStatesPulsar.csv"));
      View testView = mainController.getMainView();
      assertEquals("0xffffffff", testView.getFrontEndCellGrid().get(0).get(0).getCellColor());
  }

  @Test
  public void testViewStepBeacon() {
      javafxRun(() -> mainController.initializeSplashMenu());
      inputTest = lookup("#inputTextBox").query();
      inputTest.setText("ConwayStatesBeacon");
      press(KeyCode.ENTER);
      javafxRun(() ->  mainController.displayInfo("ConwayStatesBeacon"));
      javafxRun(() ->  mainController.initializeSimulation("Test/ConwayStatesBeacon.csv"));
      assertEquals("0xffffffff",
              mainController.getMainView().getFrontEndCellGrid().get(7).get(7).getCellColor());
      mainController.handleKeyInput(KeyCode.S);
      javafxRun(() -> mainController.gameStep());
      assertEquals("0x000000ff",
              mainController.getMainView().getFrontEndCellGrid().get(7).get(7).getCellColor());
  }

  @Test
  public void testViewStepBlinker() {
      javafxRun(() -> mainController.initializeSplashMenu());
      inputTest = lookup("#inputTextBox").query();
      inputTest.setText("ConwayStatesBlinker");
      press(KeyCode.ENTER);
      javafxRun(() ->  mainController.displayInfo("ConwayStatesBlinker"));
      javafxRun(() ->  mainController.initializeSimulation("Test/ConwayStatesBlinker.csv"));
      View testView = mainController.getMainView();
      assertEquals("0x000000ff", testView.getFrontEndCellGrid().get(7).get(8).getCellColor());
      mainController.handleKeyInput(KeyCode.S);
      javafxRun(() -> mainController.gameStep());
      assertEquals("0xffffffff", testView.getFrontEndCellGrid().get(7).get(8).getCellColor());
  }

  @Test
  public void testViewStepBlock() {
      javafxRun(() -> mainController.initializeSplashMenu());
      inputTest = lookup("#inputTextBox").query();
      inputTest.setText("ConwayStatesBlock");
      press(KeyCode.ENTER);
      javafxRun(() ->  mainController.displayInfo("ConwayStatesBlock"));
      javafxRun(() ->  mainController.initializeSimulation("Test/ConwayStatesBlock.csv"));
      View testView = mainController.getMainView();
      assertEquals("0x000000ff", testView.getFrontEndCellGrid().get(9).get(13).getCellColor());
      mainController.handleKeyInput(KeyCode.S);
      javafxRun(() -> mainController.gameStep());
      assertEquals("0x000000ff", testView.getFrontEndCellGrid().get(9).get(13).getCellColor());
  }

  @Test
  public void testViewStepToad() {
      javafxRun(() -> mainController.initializeSplashMenu());
      inputTest = lookup("#inputTextBox").query();
      inputTest.setText("ConwayStatesToad");
      press(KeyCode.ENTER);
      javafxRun(() ->  mainController.displayInfo("ConwayStatesToad"));
      javafxRun(() ->  mainController.initializeSimulation("Test/ConwayStatesToad.csv"));
      View testView = mainController.getMainView();
      assertEquals("0xffffffff", testView.getFrontEndCellGrid().get(9).get(10).getCellColor());
      mainController.handleKeyInput(KeyCode.S);
      javafxRun(() -> mainController.gameStep());
      assertEquals("0x000000ff", testView.getFrontEndCellGrid().get(9).get(10).getCellColor());
  }
}
