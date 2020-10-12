package Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import Model.Model;
import View.FrontEndCell;
import View.View;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class ControllerTest extends DukeApplicationTest {

  private Button buttonTest;
  private Controller mainController;

  public void start(final Stage stage) {
    mainController = new Controller();
    stage.setScene(mainController.setupScene());
    stage.show();
  }

  @Test
  public void testPauseConwayPulsar() {
    Controller testController = new Controller();
    testController.startSimulation("GameOfLife","ConwayStatesPulsar");
    testController.initializeSimulation("Test/ConwayStatesPulsar.csv", "GameOfLife",
        "Test/ConwayStatesPulsarOut.csv");
    testController.handleKeyInput(KeyCode.P);
    FrontEndCell currentCell;
    String previousCellColor = testController.getMainView().getFrontEndCellGrid().get(4).get(5)
        .getCellColor();
    for (int i = 0; i < 120; i++) {
      testController.gameStep();
      currentCell = testController.getMainView().getFrontEndCellGrid().get(4).get(5);
      assertEquals(previousCellColor, currentCell.getCellColor());
    }
  }

  @Test
  public void testStepConwayPulsar() {
    Controller testController = new Controller();
    testController.startSimulation("GameOfLife","ConwayStatesPulsar");
    testController.initializeSimulation("Test/ConwayStatesPulsar.csv", "GameOfLife",
        "Test/ConwayStatesPulsarOut.csv");
    View testView = testController.getMainView();
    Model testModel = testController.getMainModel();
    FrontEndCell currentCell = testView.getFrontEndCellGrid().get(5).get(6);
    testController.handleKeyInput(KeyCode.SPACE);
    assertEquals("0xffffffff", currentCell.getCellColor());
    testController.handleKeyInput(KeyCode.S);
    testController.gameStep();
    assertEquals("0x000000ff", currentCell.getCellColor());
    testController.handleKeyInput(KeyCode.S);
    testController.gameStep();
    assertEquals("0x000000ff", currentCell.getCellColor());
  }

  @Test
  public void testPauseConwayBlinker() {
    Controller testController = new Controller();
    testController.startSimulation("GameOfLife","ConwayStatesBlinker");
    testController.initializeSimulation("Test/ConwayStatesBlinker.csv", "GameOfLife",
        "Test/ConwayStatesBlinkerOut.csv");
    View testView = testController.getMainView();
    testController.handleKeyInput(KeyCode.P);
    FrontEndCell currentCell;
    String previousCellColor = testView.getFrontEndCellGrid().get(7).get(8).getCellColor();
    for (int i = 0; i < 120; i++) {
      testController.gameStep();
      currentCell = testView.getFrontEndCellGrid().get(7).get(8);
      assertEquals(previousCellColor, currentCell.getCellColor());
    }
  }

  @Test
  public void testStepConwayBlinker() {
    Controller testController = new Controller();
    testController.startSimulation("GameOfLife","ConwayStatesBlinker");
    testController.initializeSimulation("Test/ConwayStatesBlinker.csv", "GameOfLife",
        "Test/ConwayStatesBlinkerOut.csv");
    View testView = testController.getMainView();
    Model testModel = testController.getMainModel();
    FrontEndCell currentCell = testView.getFrontEndCellGrid().get(6).get(9);
    testController.handleKeyInput(KeyCode.SPACE);
    assertEquals("0xffffffff", currentCell.getCellColor());
    testController.handleKeyInput(KeyCode.S);
    testController.gameStep();
    assertEquals("0x000000ff", currentCell.getCellColor());
    testController.handleKeyInput(KeyCode.S);
    testController.gameStep();
    assertEquals("0xffffffff", currentCell.getCellColor());
  }

  @Test
  public void testSpeedUpConwayBlinker(){
    Controller testStepController = new Controller();
    testStepController.startSimulation("GameOfLife","ConwayStatesBlinker");
    testStepController.initializeSimulation("Test/ConwayStatesBlinker.csv", "GameOfLife",
        "Test/ConwayStatesBlinkerOut.csv");
    Controller testSpeedUpController = new Controller();
    testSpeedUpController.startSimulation("GameOfLife","ConwayStatesBlinker");
    testSpeedUpController.initializeSimulation("Test/ConwayStatesBlinker.csv", "GameOfLife",
        "Test/ConwayStatesBlinkerOut.csv");
    View testStepView = testStepController.getMainView();
    FrontEndCell currentStepCell = testStepView.getFrontEndCellGrid().get(6).get(9);
    View testSpeedUpView = testSpeedUpController.getMainView();
    FrontEndCell testSpeedUpCell = testSpeedUpView.getFrontEndCellGrid().get(6).get(9);
    for(int i = 0; i < 6; i ++){
      testSpeedUpController.handleKeyInput(KeyCode.RIGHT);
    }
    assertEquals(testSpeedUpCell.getCellColor(), currentStepCell.getCellColor());
    for(int i = 0; i < 60; i ++){
      testStepController.gameStep();
    }
    for(int i = 0; i < 30; i ++){
      testSpeedUpController.gameStep();
    }
    assertEquals(testSpeedUpCell.getCellColor(), currentStepCell.getCellColor());
  }
  @Test
  public void testSlowDownConwayBlinker(){
    Controller testNormalController = new Controller();
    testNormalController.startSimulation("GameOfLife","ConwayStatesBlinker");
    testNormalController.initializeSimulation("Test/ConwayStatesBlinker.csv", "GameOfLife",
        "Test/ConwayStatesBlinkerOut.csv");
    Controller testSlowDownController = new Controller();
    testSlowDownController.startSimulation("GameOfLife","ConwayStatesBlinker");
    testSlowDownController.initializeSimulation("Test/ConwayStatesBlinker.csv", "GameOfLife",
        "Test/ConwayStatesBlinkerOut.csv");
    View testNormalView = testNormalController.getMainView();
    FrontEndCell currentNormalCell = testNormalView.getFrontEndCellGrid().get(6).get(9);
    View testSlowDownView = testSlowDownController.getMainView();
    FrontEndCell testSlowDownCell = testSlowDownView.getFrontEndCellGrid().get(6).get(9);
    for(int i = 0; i < 6; i ++){
      testSlowDownController.handleKeyInput(KeyCode.LEFT);
    }
    assertEquals(testSlowDownCell.getCellColor(), currentNormalCell.getCellColor());
    for(int i = 0; i < 60; i ++){
      testNormalController.gameStep();
    }
    for(int i = 0; i < 120; i ++){
      testSlowDownController.gameStep();
    }
    assertEquals(testSlowDownCell.getCellColor(), currentNormalCell.getCellColor());
  }

  @Test
  public void testChangeCellState() {
    javafxRun(() -> mainController.displayInfo("GameOfLife", "ConwayStatesBeacon"));
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
    javafxRun(() -> mainController.displayInfo("GameOfLife", "ConwayStatesBeacon"));
    buttonTest = lookup("#ConwayStatesBeacon").queryButton();
    clickOn(buttonTest);
    assertEquals("0xffffffff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0x000000ff", mainController.getMainView().getFrontEndCellGrid().get(9).get(8).getCellColor());
  }

  @Test
  public void testStartConwayBlinkerStartButton(){
    javafxRun(() -> mainController.displayInfo("GameOfLife", "ConwayStatesBlinker"));
    buttonTest = lookup("#ConwayStatesBlinker").queryButton();
    clickOn(buttonTest);
    assertEquals("0xffffffff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0x000000ff", mainController.getMainView().getFrontEndCellGrid().get(7).get(8).getCellColor());
  }

  @Test
  public void testStartConwayBlockStartButton(){
    javafxRun(() -> mainController.displayInfo("GameOfLife", "ConwayStatesBlock"));
    buttonTest = lookup("#ConwayStatesBlock").queryButton();
    clickOn(buttonTest);
    assertEquals("0xffffffff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0x000000ff", mainController.getMainView().getFrontEndCellGrid().get(9).get(13).getCellColor());
  }

  @Test
  public void testStartConwayPulsarStartButton(){
    javafxRun(() -> mainController.displayInfo("GameOfLife", "ConwayStatesPulsar"));
    buttonTest = lookup("#ConwayStatesPulsar").queryButton();
    clickOn(buttonTest);
    assertEquals("0xffffffff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0x000000ff", mainController.getMainView().getFrontEndCellGrid().get(4).get(5).getCellColor());
  }

  @Test
  public void testStartConwayToadStartButton(){
    javafxRun(() -> mainController.displayInfo("GameOfLife", "ConwayStatesToad"));
    buttonTest = lookup("#ConwayStatesToad").queryButton();
    clickOn(buttonTest);
    assertEquals("0xffffffff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0x000000ff", mainController.getMainView().getFrontEndCellGrid().get(8).get(9).getCellColor());
  }

  @Test
  public void testPercolationExampleStartButton(){
    javafxRun(() -> mainController.displayInfo("Percolation", "PercolationExample"));
    buttonTest = lookup("#PercolationExample").queryButton();
    clickOn(buttonTest);
    assertEquals("0x000000ff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0xffffffff", mainController.getMainView().getFrontEndCellGrid().get(3).get(1).getCellColor());
  }

  @Test
  public void testRPS100StartButton(){
    javafxRun(() -> mainController.displayInfo("RPS", "RPS100"));
    buttonTest = lookup("#RPS100").queryButton();
    clickOn(buttonTest);
    assertEquals("0xffff00ff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0x000000ff", mainController.getMainView().getFrontEndCellGrid().get(0).get(1).getCellColor());
  }

  @Test
  public void testRPSExampleStartButton(){
    javafxRun(() -> mainController.displayInfo("RPS", "RPSExample"));
    buttonTest = lookup("#RPSExample").queryButton();
    clickOn(buttonTest);
    assertEquals("0xff0000ff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0x008000ff", mainController.getMainView().getFrontEndCellGrid().get(0).get(1).getCellColor());
  }

  @Test
  public void testSpreadingFire20StartButton(){
    javafxRun(() -> mainController.displayInfo("SpreadingFire", "SpreadingFire20"));
    buttonTest = lookup("#SpreadingFire20").queryButton();
    clickOn(buttonTest);
    assertEquals("0x008000ff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0x008000ff", mainController.getMainView().getFrontEndCellGrid().get(0).get(1).getCellColor());
  }

  @Test
  public void testSpreadingFire100StartButton(){
    javafxRun(() -> mainController.displayInfo("SpreadingFire", "SpreadingFire100"));
    buttonTest = lookup("#SpreadingFire100").queryButton();
    clickOn(buttonTest);
    assertEquals("0x008000ff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0x008000ff", mainController.getMainView().getFrontEndCellGrid().get(0).get(1).getCellColor());
  }

  @Test
  public void testSegregationExampleStartButton(){
    javafxRun(() -> mainController.displayInfo("Segregation", "SegregationExample"));
    buttonTest = lookup("#SegregationExample").queryButton();
    clickOn(buttonTest);
    assertEquals("0x008000ff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0x000000ff", mainController.getMainView().getFrontEndCellGrid().get(0).get(1).getCellColor());
  }

  @Test
  public void testWaTorExampleStartButton(){
    javafxRun(() -> mainController.displayInfo("WaTor", "WaTorExample"));
    buttonTest = lookup("#WaTorExample").queryButton();
    clickOn(buttonTest);
    assertEquals("0x0000ffff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0x008000ff", mainController.getMainView().getFrontEndCellGrid().get(0).get(1).getCellColor());
  }

  @Test
  public void testHomeButton(){
    javafxRun(() -> mainController.displayInfo("SpreadingFire", "SpreadingFire100"));
    buttonTest = lookup("#SpreadingFire100").queryButton();
    clickOn(buttonTest);
    buttonTest = lookup("#Home").queryButton();
    clickOn(buttonTest);
    javafxRun(() -> mainController.displayInfo("SpreadingFire", "SpreadingFire20"));
    buttonTest = lookup("#SpreadingFire20").queryButton();
    clickOn(buttonTest);
    assertEquals("0x008000ff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0x008000ff", mainController.getMainView().getFrontEndCellGrid().get(0).get(1).getCellColor());
  }
}
