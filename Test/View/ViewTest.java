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
        mainController = new Controller(stage);
        stage.setScene(mainController.setupScene());
        stage.show();
    }

  @Test
  public void testInitializeFrontendCells() {
      javafxRun(() -> mainController.initializeSplashMenu());
      inputTest = lookup("#inputTextBox").query();
      inputTest.setText("ConwayStatesPulsar");
      press(KeyCode.ENTER);
      buttonTest = lookup("#ConwayStatesPulsar").queryButton();
      clickOn(buttonTest);
      View testView = mainController.getMainView();
      assertEquals("0xffffffff", testView.getFrontEndCellGrid().get(0).get(0).getCellColor());
  }

  @Test
  public void testViewStepBeacon() {
      javafxRun(() -> mainController.initializeSplashMenu());
      inputTest = lookup("#inputTextBox").query();
      inputTest.setText("ConwayStatesBeacon");
      press(KeyCode.ENTER);
      buttonTest = lookup("#ConwayStatesBeacon").queryButton();
      clickOn(buttonTest);
      assertEquals("0xffffffff",
              mainController.getMainView().getFrontEndCellGrid().get(7).get(7).getCellColor());
      clickOn(lookup("#Step").queryButton());
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
      buttonTest = lookup("#ConwayStatesBlinker").queryButton();
      clickOn(buttonTest);
      View testView = mainController.getMainView();
      assertEquals("0x000000ff", testView.getFrontEndCellGrid().get(7).get(8).getCellColor());
      clickOn(lookup("#Step").queryButton());
      javafxRun(() -> mainController.gameStep());
      assertEquals("0xffffffff", testView.getFrontEndCellGrid().get(7).get(8).getCellColor());
  }

  @Test
  public void testViewStepBlock() {
      javafxRun(() -> mainController.initializeSplashMenu());
      inputTest = lookup("#inputTextBox").query();
      inputTest.setText("ConwayStatesBlock");
      press(KeyCode.ENTER);
      buttonTest = lookup("#ConwayStatesBlock").queryButton();
      clickOn(buttonTest);
      View testView = mainController.getMainView();
      assertEquals("0x000000ff", testView.getFrontEndCellGrid().get(9).get(13).getCellColor());
      clickOn(lookup("#Step").queryButton());
      javafxRun(() -> mainController.gameStep());
      assertEquals("0x000000ff", testView.getFrontEndCellGrid().get(9).get(13).getCellColor());
  }

  @Test
  public void testViewStepToad() {
      javafxRun(() -> mainController.initializeSplashMenu());
      inputTest = lookup("#inputTextBox").query();
      inputTest.setText("ConwayStatesToad");
      press(KeyCode.ENTER);
      buttonTest = lookup("#ConwayStatesToad").queryButton();
      clickOn(buttonTest);
      View testView = mainController.getMainView();
      assertEquals("0xffffffff", testView.getFrontEndCellGrid().get(9).get(10).getCellColor());
      clickOn(lookup("#Step").queryButton());
      javafxRun(() -> mainController.gameStep());
      assertEquals("0x000000ff", testView.getFrontEndCellGrid().get(9).get(10).getCellColor());
  }
}
