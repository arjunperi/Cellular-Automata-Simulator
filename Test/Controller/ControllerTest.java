package Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import View.FrontEndCell;
import View.View;
import javafx.scene.control.Button;
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
    mainController = new Controller();
    this.stage = stage;
    this.stage.setScene(mainController.setupScene());
    this.stage.show();
  }

  @Test
  public void testPauseConwayPulsar() {
    javafxRun(() -> mainController.initializeButtonMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("ConwayStatesPulsar");
    press(KeyCode.ENTER);
    buttonTest = lookup("#ConwayStatesPulsar").query();
    clickOn(buttonTest);
    javafxRun(() -> mainController.handleKeyInput(KeyCode.P));
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
    javafxRun(() -> mainController.initializeButtonMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("ConwayStatesPulsar");
    press(KeyCode.ENTER);
    buttonTest = lookup("#ConwayStatesPulsar").query();
    clickOn(buttonTest);
    View testView = mainController.getMainView();
    FrontEndCell currentCell = testView.getFrontEndCellGrid().get(5).get(6);
    press(KeyCode.SPACE);
    assertEquals("0xffffffff", currentCell.getCellColor());
    javafxRun(() -> mainController.handleKeyInput(KeyCode.S));
    javafxRun(() -> mainController.gameStep());
    sleep(1000);
    assertEquals("0x000000ff", currentCell.getCellColor());
    javafxRun(() -> mainController.handleKeyInput(KeyCode.S));
    javafxRun(() -> mainController.gameStep());
    sleep(1000);
    assertEquals("0x000000ff", currentCell.getCellColor());
  }


  @Test
  public void testPauseConwayBlinker() {
    javafxRun(() -> mainController.initializeButtonMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("ConwayStatesBlinker");
    press(KeyCode.ENTER);
    buttonTest = lookup("#ConwayStatesBlinker").query();
    clickOn(buttonTest);
    javafxRun(() -> mainController.handleKeyInput(KeyCode.P));
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
    javafxRun(() -> mainController.initializeButtonMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("ConwayStatesBlinker");
    press(KeyCode.ENTER);
    buttonTest = lookup("#ConwayStatesBlinker").query();
    clickOn(buttonTest);
    View testView = mainController.getMainView();
    FrontEndCell currentCell = testView.getFrontEndCellGrid().get(6).get(9);
    press(KeyCode.SPACE);
    assertEquals("0xffffffff", currentCell.getCellColor());
    javafxRun(() -> mainController.handleKeyInput(KeyCode.S));
    javafxRun(() -> mainController.gameStep());
    sleep(1000);
    assertEquals("0x000000ff", currentCell.getCellColor());
    javafxRun(() -> mainController.handleKeyInput(KeyCode.S));
    javafxRun(() -> mainController.gameStep());
    sleep(1000);
    assertEquals("0xffffffff", currentCell.getCellColor());
  }
//
//  @Test
//  public void testSpeedUpConwayBlinker(){
//    javafxRun(() -> mainController.initializeButtonMenu());
//    inputTest = lookup("#inputTextBox").query();
//    inputTest.setText("ConwayStatesBlinker");
//    press(KeyCode.ENTER);
//    javafxRun(() ->  mainController.displayInfo("ConwayStatesBlinker"));
//    javafxRun(() ->  mainController.initializeSimulation("ConwayStatesBlinker.csv",
//            "ConwayStatesBlinkerOut.csv"));
//    View testView = mainController.getMainView();
//
//    Controller testStepController = new Controller();
//    testStepController.startSimulation("GameOfLife","ConwayStatesBlinker");
//    testStepController.initializeSimulation("ConwayStatesBlinker.csv", "GameOfLife",
//        "ConwayStatesBlinkerOut.csv");
//    Controller testSpeedUpController = new Controller();
//    testSpeedUpController.startSimulation("GameOfLife","ConwayStatesBlinker");
//    testSpeedUpController.initializeSimulation("ConwayStatesBlinker.csv", "GameOfLife",
//        "ConwayStatesBlinkerOut.csv");
//    View testStepView = testStepController.getMainView();
//    FrontEndCell currentStepCell = testStepView.getFrontEndCellGrid().get(6).get(9);
//    View testSpeedUpView = testSpeedUpController.getMainView();
//    FrontEndCell testSpeedUpCell = testSpeedUpView.getFrontEndCellGrid().get(6).get(9);
//    for(int i = 0; i < 6; i ++){
//      testSpeedUpController.handleKeyInput(KeyCode.W);
//    }
//    assertEquals(testSpeedUpCell.getCellColor(), currentStepCell.getCellColor());
//    for(int i = 0; i < 60; i ++){
//      testStepController.gameStep();
//    }
//    for(int i = 0; i < 30; i ++){
//      testSpeedUpController.gameStep();
//    }
//    assertEquals(testSpeedUpCell.getCellColor(), currentStepCell.getCellColor());
//  }
//  @Test
//  public void testSlowDownConwayBlinker(){
//    Controller testNormalController = new Controller();
//    testNormalController.startSimulation("GameOfLife","ConwayStatesBlinker");
//    testNormalController.initializeSimulation("ConwayStatesBlinker.csv", "GameOfLife",
//        "ConwayStatesBlinkerOut.csv");
//    Controller testSlowDownController = new Controller();
//    testSlowDownController.startSimulation("GameOfLife","ConwayStatesBlinker");
//    testSlowDownController.initializeSimulation("ConwayStatesBlinker.csv", "GameOfLife",
//        "ConwayStatesBlinkerOut.csv");
//    View testNormalView = testNormalController.getMainView();
//    FrontEndCell currentNormalCell = testNormalView.getFrontEndCellGrid().get(6).get(9);
//    View testSlowDownView = testSlowDownController.getMainView();
//    FrontEndCell testSlowDownCell = testSlowDownView.getFrontEndCellGrid().get(6).get(9);
//    for(int i = 0; i < 6; i ++){
//      testSlowDownController.handleKeyInput(KeyCode.Q);
//    }
//    assertEquals(testSlowDownCell.getCellColor(), currentNormalCell.getCellColor());
//    for(int i = 0; i < 60; i ++){
//      testNormalController.gameStep();
//    }
//    for(int i = 0; i < 120; i ++){
//      testSlowDownController.gameStep();
//    }
//    assertEquals(testSlowDownCell.getCellColor(), currentNormalCell.getCellColor());
//  }
//

  @Test
  public void testChangeCellState() {
    javafxRun(() -> mainController.initializeButtonMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("ConwayStatesBeacon");
    press(KeyCode.ENTER);
    buttonTest = lookup("#ConwayStatesBeacon").queryButton();
    clickOn(buttonTest);
    assertEquals("0xffffffff", mainController.getMainView().getFrontEndCellGrid().get(0).get(5).getCellColor());
    FrontEndCell testCell = lookup("#cell04").query();
    assertEquals("0xffffffff", testCell.getCellColor());
    clickOn("#cell04");
    sleep(1000);
    javafxRun(() -> mainController.gameStep());
    assertEquals("0x000000ff", testCell.getCellColor());
  }

  @Test
  public void testStartConwayBeaconStartButton(){
    javafxRun(() -> mainController.initializeButtonMenu());
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
    javafxRun(() -> mainController.initializeButtonMenu());
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
    javafxRun(() -> mainController.initializeButtonMenu());
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
    javafxRun(() -> mainController.initializeButtonMenu());
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
    javafxRun(() -> mainController.initializeButtonMenu());
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
    javafxRun(() -> mainController.initializeButtonMenu());
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
    javafxRun(() -> mainController.initializeButtonMenu());
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
    javafxRun(() -> mainController.initializeButtonMenu());
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
    javafxRun(() -> mainController.initializeButtonMenu());
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
    javafxRun(() -> mainController.initializeButtonMenu());
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
    javafxRun(() -> mainController.initializeButtonMenu());
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
    javafxRun(() -> mainController.initializeButtonMenu());
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
    javafxRun(() -> mainController.initializeButtonMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("SpreadingFire100");
    press(KeyCode.ENTER);
    buttonTest = lookup("#SpreadingFire100").queryButton();
    clickOn(buttonTest);
    buttonTest = lookup("#Home").queryButton();
    clickOn(buttonTest);
    javafxRun(() -> mainController.initializeButtonMenu());
    sleep(1000);
    inputTest = lookup("#inputTextBox").query();
    javafxRun(() -> inputTest.setText("SpreadingFire20"));
    assertEquals(inputTest.getText(), "SpreadingFire20");
  }

  @Test
  public void testDialogInput(){
    javafxRun(() -> mainController.initializeButtonMenu());
    inputTest = lookup("#inputTextBox").query();
    inputTest.setText("ConwayStatesBlinker");
    press(KeyCode.ENTER);
    buttonTest = lookup("#ConwayStatesBlinker").queryButton();
    clickOn(buttonTest);
    assertEquals("0xffffffff", mainController.getMainView().getFrontEndCellGrid().get(0).get(0).getCellColor());
    assertEquals("0x000000ff", mainController.getMainView().getFrontEndCellGrid().get(7).get(8).getCellColor());
  }

  @Test
  public void testInvalidFileName(){
    Controller testController = new Controller();
    assertThrows(ControllerException.class, () -> testController.getPropertyFile("nonexistent"));
  }

  @Test
  public void testupdateColorStateMapping(){
    javafxRun(() -> mainController.initializeButtonMenu());
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
      javafxRun(() -> mainController.initializeButtonMenu());
      inputTest = lookup("#inputTextBox").query();
      inputTest.setText("TestDefaults");
      press(KeyCode.ENTER);
      javafxRun(() -> mainController.displayInfo( "TestDefaults"));
      javafxRun(() ->  mainController.initializeSimulation());
      View testView = mainController.getMainView();
      assertEquals("0xffffffff", testView.getFrontEndCellGrid().get(0).get(0).getCellColor());
    }

//  @Test
//  public void testSaveSimulationProperties(){
//    javafxRun(() -> mainController.initializeButtonMenu());
//    inputTest = lookup("#inputTextBox").query();
//    inputTest.setText("ConwayStatesPulsar");
//    press(KeyCode.ENTER);
//    javafxRun(() ->  mainController.displayInfo("ConwayStatesPulsar"));
//    javafxRun(() ->  mainController.initializeSimulation("ConwayStatesPulsar.csv",
//            "ConwayStatesPulsarOut.csv"));
//    buttonTest = lookup("#Save").queryButton();
//    clickOn(buttonTest);
//    TextField titleTest = lookup("#titleInput").query();
//    TextField authorTest = lookup("#authorInput").query();
//    TextField descriptionTest = lookup("#descriptionInput").query();
//    titleTest.setText("TestPulsarSave");
//    authorTest.setText("TestPulsarAuthorSave");
//    descriptionTest.setText("TestPulsarDescriptionSave");
//    Button okTest = lookup("#SaveOK").queryButton();
//    clickOn(okTest);
//    javafxRun(() ->  mainController.initializeSimulation("ConwayStatesPulsar.csv",
//            "ConwayStatesPulsarOut.csv"));
//    buttonTest = lookup("#Home").queryButton();
//    javafxRun(() -> stage.close());
//    javafxRun(() ->  mainController.initializeSimulation("ConwayStatesPulsar.csv",
//            "ConwayStatesPulsarOut.csv"));
//    assertEquals("TestPulsarDescriptionSave", mainController.getPropertyFile("TestPulsarSave").getProperty("Description"));
//  }

}
