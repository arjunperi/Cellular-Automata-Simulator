package Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import Controller.Controller;
import View.View;
import View.AbstractFrontEndCell;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

public class CSVWriteTest {

  @Test
  public void testPauseConwayToad() {
    Model testModel = new Model("Test/ConwayStatesToad.csv", "GameOfLife", "Test/ConwayStatesToadOut.csv");
    View testView = new View(testModel, "GameOfLife");
    Controller testController = new Controller(testModel);
    AbstractFrontEndCell currentCell = testView.getFrontEndCellGrid().get(7).get(10);
    assertEquals(1, currentCell.getCellState());
    assertEquals(Color.BLACK, currentCell.getCellColor());
    testController.handleKeyInput(KeyCode.S);
    Model testModelOut = new Model("Test/ConwayStatesToadOut.csv", "GameOfLife");
    View testViewOut = new View(testModelOut, "GameOfLife");
    currentCell = testViewOut.getFrontEndCellGrid().get(7).get(10);
    assertEquals(0, currentCell.getCellState());
    assertEquals(Color.WHITE, currentCell.getCellColor());
  }

  @Test
  public void testPauseConwayPulsar() {
    Model testModel = new Model("Test/ConwayStatesPulsar.csv", "GameOfLife", "Test/ConwayStatesPulsarOut.csv");
    View testView = new View(testModel, "GameOfLife");
    Controller testController = new Controller(testModel);
    AbstractFrontEndCell currentCell = testView.getFrontEndCellGrid().get(4).get(5);
    assertEquals(1, currentCell.getCellState());
    assertEquals(Color.BLACK, currentCell.getCellColor());
    testController.handleKeyInput(KeyCode.S);
    Model testModelOut = new Model("Test/ConwayStatesPulsarOut.csv", "GameOfLife");
    View testViewOut = new View(testModelOut, "GameOfLife");
    currentCell = testViewOut.getFrontEndCellGrid().get(4).get(5);
    assertEquals(0, currentCell.getCellState());
    assertEquals(Color.WHITE, currentCell.getCellColor());
  }
}
