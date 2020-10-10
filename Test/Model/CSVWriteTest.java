package Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import Controller.Controller;
import View.View;
import View.AbstractFrontendCell;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

public class CSVWriteTest {

  @Test
  public void testPauseConwayToad() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesToad.csv", "Test/ConwayStatesToadOut.csv");
    View testView = new View("English", );
    Controller testController = new Controller(testModel);
    AbstractFrontendCell currentCell = testView.getFrontEndCellGrid().get(7).get(10);
    assertEquals(1, currentCell.getCellState());
    assertEquals(Color.BLACK, currentCell.getCellColor());
    testController.handleKeyInput(KeyCode.S);
    Model testModelOut = new GameOfLifeModel("Test/ConwayStatesToadOut.csv");
    View testViewOut = new View("English", );
    currentCell = testViewOut.getFrontEndCellGrid().get(7).get(10);
    assertEquals(0, currentCell.getCellState());
    assertEquals(Color.WHITE, currentCell.getCellColor());
  }

  @Test
  public void testPauseConwayPulsar() {
    Model testModel = new GameOfLifeModel("Test/ConwayStatesPulsar.csv", "Test/ConwayStatesPulsarOut.csv");
    View testView = new View("English", );
    Controller testController = new Controller(testModel);
    AbstractFrontendCell currentCell = testView.getFrontEndCellGrid().get(4).get(5);
    assertEquals(1, currentCell.getCellState());
    assertEquals(Color.BLACK, currentCell.getCellColor());
    testController.handleKeyInput(KeyCode.S);
    Model testModelOut = new GameOfLifeModel("Test/ConwayStatesPulsarOut.csv");
    View testViewOut = new View("English", );
    currentCell = testViewOut.getFrontEndCellGrid().get(4).get(5);
    assertEquals(0, currentCell.getCellState());
    assertEquals(Color.WHITE, currentCell.getCellColor());
  }
}
