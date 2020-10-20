package Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import View.FrontEndCell;
import View.View;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class ControllerTest extends DukeApplicationTest {

  private Button buttonTest;
  private Controller mainController;
  private TextField inputTest;
  private Stage stage;

  public void start(final Stage stage) {
    mainController = new Controller(stage);
    this.stage = stage;
    this.stage.setScene(mainController.setupScene());
    this.stage.show();
  }

  @Test
  public void testPauseConwayPulsar() {
    javafxRun(() -> mainController.initializeSplashMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("ConwayStatesPulsar");
    press(KeyCode.ENTER);
    buttonTest = lookup("#ConwayStatesPulsar").query();
    clickOn(buttonTest);
    Button pauseButton = lookup("#Pause").queryButton();
    clickOn(pauseButton);
    FrontEndCell currentCell;
    String previousCellColor = mainController.getMainView().getFrontEndCellGrid().get(4).get(5)
            .getCellColor();
    for (int i = 0; i < 60; i++) {
      javafxRun(() -> mainController.gameStep());
      currentCell = mainController.getMainView().getFrontEndCellGrid().get(4).get(5);
      assertEquals(previousCellColor, currentCell.getCellColor());
    }
  }

  @Test
  public void testStepConwayPulsar() {
    javafxRun(() -> mainController.initializeSplashMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("ConwayStatesPulsar");
    press(KeyCode.ENTER);
    buttonTest = lookup("#ConwayStatesPulsar").query();
    clickOn(buttonTest);
    View testView = mainController.getMainView();
    FrontEndCell currentCell = testView.getFrontEndCellGrid().get(5).get(6);
    clickOn(lookup("#Pause").queryButton());
    assertEquals("0xffffffff", currentCell.getCellColor());
    clickOn(lookup("#Step").queryButton());
    javafxRun(() -> mainController.gameStep());
    assertEquals("0x000000ff", currentCell.getCellColor());
    clickOn(lookup("#Step").queryButton());
    javafxRun(() -> mainController.gameStep());
    assertEquals("0x000000ff", currentCell.getCellColor());
  }


  @Test
  public void testPauseConwayBlinker() {
    javafxRun(() -> mainController.initializeSplashMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("ConwayStatesBlinker");
    press(KeyCode.ENTER);
    buttonTest = lookup("#ConwayStatesBlinker").query();
    clickOn(buttonTest);
    Button pauseButton = lookup("#Pause").queryButton();
    clickOn(pauseButton);
    FrontEndCell currentCell;
    String previousCellColor = mainController.getMainView().getFrontEndCellGrid().get(7).get(8).getCellColor();
    for (int i = 0; i < 120; i++) {
      javafxRun(() -> mainController.gameStep());
      currentCell = mainController.getMainView().getFrontEndCellGrid().get(7).get(8);
      assertEquals(previousCellColor, currentCell.getCellColor());
    }
  }


  @Test
  public void testStepConwayBlinker() {
    javafxRun(() -> mainController.initializeSplashMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("ConwayStatesBlinker");
    press(KeyCode.ENTER);
    buttonTest = lookup("#ConwayStatesBlinker").query();
    clickOn(buttonTest);
    View testView = mainController.getMainView();
    FrontEndCell currentCell = testView.getFrontEndCellGrid().get(6).get(9);
    clickOn(lookup("#Pause").queryButton());
    assertEquals("0xffffffff", currentCell.getCellColor());
    Button stepButton = lookup("#Step").queryButton();
    clickOn(stepButton);
    javafxRun(() -> mainController.gameStep());
    assertEquals("0x000000ff", currentCell.getCellColor());
    clickOn(stepButton);
    javafxRun(() -> mainController.gameStep());
    assertEquals("0xffffffff", currentCell.getCellColor());
  }

  @Test
  public void testSpeedUpConwayBlinker(){
    javafxRun(() -> mainController.initializeSplashMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("ConwayStatesBlinker");
    press(KeyCode.ENTER);
    buttonTest = lookup("#ConwayStatesBlinker").query();
    clickOn(buttonTest);
    View testView = mainController.getMainView();
    FrontEndCell currentCell = testView.getFrontEndCellGrid().get(6).get(9);
    assertEquals("0xffffffff", currentCell.getCellColor());
    for(int i = 0; i < 60; i ++){
      javafxRun(() -> mainController.gameStep());
    }
    assertEquals("0x000000ff", currentCell.getCellColor());
    Slider slider = lookup("#slider").query();
    slider.setValue(70);
    for(int i = 0; i < 30; i ++){
      javafxRun(() -> mainController.gameStep());
    }
    assertEquals("0xffffffff", currentCell.getCellColor());
  }

  @Test
  public void testChangeCellState() {
    javafxRun(() -> mainController.initializeSplashMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("ConwayStatesBeacon");
    press(KeyCode.ENTER);
    buttonTest = lookup("#ConwayStatesBeacon").queryButton();
    clickOn(buttonTest);
    assertEquals("0xffffffff", mainController.getMainView().getFrontEndCellGrid().get(0).get(5).getCellColor());
    FrontEndCell testCell = lookup("#cell04").query();
    assertEquals("0xffffffff", testCell.getCellColor());
    clickOn("#cell04");
    javafxRun(() -> mainController.gameStep());
    assertEquals("0x000000ff", testCell.getCellColor());
  }

  @Test
  public void testStartConwayBeaconStartButton(){
    javafxRun(() -> mainController.initializeSplashMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("ConwayStatesBeacon");
    press(KeyCode.ENTER);
    buttonTest = lookup("#ConwayStatesBeacon").queryButton();
    clickOn(buttonTest);
    assertEquals("0xffffffff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0x000000ff", mainController.getMainView().getFrontEndCellGrid().get(9).get(8).getCellColor());
  }

  @Test
  public void testStartConwayBlinkerStartButton(){
    javafxRun(() -> mainController.initializeSplashMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("ConwayStatesBlinker");
    press(KeyCode.ENTER);
    buttonTest = lookup("#ConwayStatesBlinker").queryButton();
    clickOn(buttonTest);
    assertEquals("0xffffffff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0x000000ff", mainController.getMainView().getFrontEndCellGrid().get(7).get(8).getCellColor());
  }

  @Test
  public void testStartConwayBlockStartButton(){
    javafxRun(() -> mainController.initializeSplashMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("ConwayStatesBlock");
    press(KeyCode.ENTER);
    buttonTest = lookup("#ConwayStatesBlock").queryButton();
    clickOn(buttonTest);
    assertEquals("0xffffffff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0x000000ff", mainController.getMainView().getFrontEndCellGrid().get(9).get(13).getCellColor());
  }

  @Test
  public void testStartConwayPulsarStartButton(){
    javafxRun(() -> mainController.initializeSplashMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("ConwayStatesPulsar");
    press(KeyCode.ENTER);
    buttonTest = lookup("#ConwayStatesPulsar").queryButton();
    clickOn(buttonTest);
    assertEquals("0xffffffff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0x000000ff", mainController.getMainView().getFrontEndCellGrid().get(4).get(5).getCellColor());
  }

  @Test
  public void testStartConwayToadStartButton(){
    javafxRun(() -> mainController.initializeSplashMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("ConwayStatesToad");
    press(KeyCode.ENTER);
    buttonTest = lookup("#ConwayStatesToad").queryButton();
    clickOn(buttonTest);
    assertEquals("0xffffffff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0x000000ff", mainController.getMainView().getFrontEndCellGrid().get(8).get(9).getCellColor());
  }

  @Test
  public void testPercolationExampleStartButton(){
    javafxRun(() -> mainController.initializeSplashMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("PercolationExample");
    press(KeyCode.ENTER);
    buttonTest = lookup("#PercolationExample").queryButton();
    clickOn(buttonTest);
    assertEquals("0x000000ff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0xffffffff", mainController.getMainView().getFrontEndCellGrid().get(3).get(1).getCellColor());
  }

  @Test
  public void testRPS100StartButton(){
    javafxRun(() -> mainController.initializeSplashMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("RPS100");
    press(KeyCode.ENTER);
    buttonTest = lookup("#RPS100").queryButton();
    clickOn(buttonTest);
    assertEquals("0xff00ffff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0xe6e6faff", mainController.getMainView().getFrontEndCellGrid().get(0).get(1).getCellColor());
  }

  @Test
  public void testRPSExampleStartButton(){
    javafxRun(() -> mainController.initializeSplashMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("RPSExample");
    press(KeyCode.ENTER);
    buttonTest = lookup("#RPSExample").queryButton();
    clickOn(buttonTest);
    assertEquals("0xffffffff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0x00ffffff", mainController.getMainView().getFrontEndCellGrid().get(0).get(1).getCellColor());
  }

  @Test
  public void testSpreadingFire20StartButton(){
    javafxRun(() -> mainController.initializeSplashMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("SpreadingFire20");
    press(KeyCode.ENTER);
    buttonTest = lookup("#SpreadingFire20").queryButton();
    clickOn(buttonTest);
    assertEquals("0x008000ff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0x008000ff", mainController.getMainView().getFrontEndCellGrid().get(0).get(1).getCellColor());
  }

  @Test
  public void testSpreadingFire100StartButton(){
    javafxRun(() -> mainController.initializeSplashMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("SpreadingFire100");
    press(KeyCode.ENTER);
    buttonTest = lookup("#SpreadingFire100").queryButton();
    clickOn(buttonTest);
    assertEquals("0xff0000ff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0xff0000ff", mainController.getMainView().getFrontEndCellGrid().get(0).get(1).getCellColor());
  }

  @Test
  public void testSegregationExampleStartButton(){
    javafxRun(() -> mainController.initializeSplashMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("SegregationExample");
    press(KeyCode.ENTER);
    buttonTest = lookup("#SegregationExample").queryButton();
    clickOn(buttonTest);
    assertEquals("0x008000ff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0x000000ff", mainController.getMainView().getFrontEndCellGrid().get(0).get(1).getCellColor());
  }

  @Test
  public void testWaTorExampleStartButton(){
    javafxRun(() -> mainController.initializeSplashMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("WaTorExample");
    press(KeyCode.ENTER);
    buttonTest = lookup("#WaTorExample").queryButton();
    clickOn(buttonTest);
    assertEquals("0x0000ffff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0x008000ff", mainController.getMainView().getFrontEndCellGrid().get(0).get(1).getCellColor());
  }

  @Test
  public void testHomeButton(){
    javafxRun(() -> mainController.initializeSplashMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("SpreadingFire100");
    press(KeyCode.ENTER);
    buttonTest = lookup("#SpreadingFire100").queryButton();
    clickOn(buttonTest);
    buttonTest = lookup("#Home").queryButton();
    clickOn(buttonTest);
    javafxRun(() -> mainController.initializeSplashMenu());
    sleep(1000);
    inputTest = lookup("#inputTextBox").query();
    javafxRun(() -> inputTest.setText("SpreadingFire20"));
    assertEquals(inputTest.getText(), "SpreadingFire20");
  }

  @Test
  public void testDialogInput(){
    javafxRun(() -> mainController.initializeSplashMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("ConwayStatesBlinker");
    press(KeyCode.ENTER);
    buttonTest = lookup("#ConwayStatesBlinker").queryButton();
    clickOn(buttonTest);
    assertEquals("0xffffffff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0x000000ff", mainController.getMainView().getFrontEndCellGrid().get(7).get(8).getCellColor());
  }

  @Test
  public void testInvalidModelType(){
    javafxRun(() -> mainController.initializeSplashMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("Test");
    assertThrows(IllegalStateException.class, () -> mainController.displayInfo(inputTest.getText()));
  }

  @Test
  public void testInvalidFileName(){
    Controller testController = new Controller(stage);
    assertThrows(ControllerException.class, () -> testController.getPropertyFile("nonexistent"));
  }


  @Test
  public void testupdateColorStateMapping(){
    javafxRun(() -> mainController.initializeSplashMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("ConwayStatesBlinker");
    press(KeyCode.ENTER);
    javafxRun(() -> mainController.displayInfo( "ConwayStatesBlinker"));
    buttonTest = lookup("#ConwayStatesBlinker").queryButton();
    clickOn(buttonTest);
    assertEquals("0xffffffff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    javafxRun(() -> mainController.updateColorStateMapping("0", "PINK"));
    javafxRun(() -> mainController.gameStep());
    assertEquals("0xffc0cbff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
  }

  @Test
  public void testPropertyFileDefaultKey(){
    javafxRun(() -> mainController.initializeSplashMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("TestDefaults");
    press(KeyCode.ENTER);
    javafxRun(() -> mainController.displayInfo( "TestDefaults"));
    javafxRun(() ->  mainController.initializeSimulation());
    View testView = mainController.getMainView();
    assertEquals("0xffffffff", testView.getFrontEndCellGrid().get(0).get(0).getCellColor());
  }

  @Test
  public void testLanguageChange(){
    javafxRun(() -> mainController.initializeSplashMenu());
    clickOn(lookup("#spanish").queryButton());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("TestDefaults");
    press(KeyCode.ENTER);
    Button test = lookup("#Home").queryButton();
    assertEquals("Inicio", test.getText());
  }
  @Test
  public void reopenSimulationView(){
    javafxRun(() -> mainController.initializeSplashMenu());
    javafxRun(() -> mainController.initializeSplashMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("SegregationExample");
    press(KeyCode.ENTER);
    clickOn(lookup("#SegregationExample").queryButton());
    clickOn(lookup("Show State Population Graph").queryButton());
    javafxRun(() -> this.stage.close());
    assertTrue(false == this.stage.isShowing());
    clickOn(lookup("Display Simulation Grid").queryButton());
    assertTrue(true == this.stage.isShowing());
  }
}


