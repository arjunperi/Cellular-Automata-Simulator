package View;

import Model.Model;
import cellsociety.Simulation;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import Model.Cell;

import java.util.List;


public class View {

  private static final Paint BACKGROUND = Color.AZURE;

  private final Model backendModel;
  private final int numberOfRows;
  private final int numberOfColumns;
  private final Group root;
  private List<List<AbstractFrontendCell>> frontEndCellGrid;


  public View(Model model) {
    root = new Group();
    backendModel = model;
    numberOfColumns = backendModel.getNumberOfColumns();
    numberOfRows = backendModel.getNumberOfRows();
    initializeFrontEndCells();
  }

  public Scene setupScene() {
    return new Scene(root, Simulation.SCENE_WIDTH, Simulation.SCENE_HEIGHT, BACKGROUND);
  }

  public void viewStep() {
    updateFrontEndCells();
  }

  private void initializeFrontEndCells() {
    frontEndCellGrid = new ArrayList<>();
    double xOffset = Simulation.SCENE_WIDTH / (double)numberOfRows;
    double yOffset = Simulation.SCENE_HEIGHT / (double)numberOfColumns;
    double x;
    double y = 0;

    for (int row = 0; row < numberOfRows; row++) {
      x = 0;
      List<AbstractFrontendCell> frontEndCellRow = new ArrayList<>();
      for (int column = 0; column < numberOfColumns; column++) {
        int state = backendModel.getCellState(row,column);
        AbstractFrontendCell currentFrontEndCell = new SpreadingFireFrontEndCell(state, x, y, xOffset,
            yOffset);
        frontEndCellRow.add(currentFrontEndCell);
        root.getChildren().add(currentFrontEndCell.getCellShape());
        x += xOffset;
      }
      frontEndCellGrid.add(frontEndCellRow);
      y += yOffset;
    }
  }

  private void updateFrontEndCells() {
    for (int row = 0; row < numberOfRows; row++) {
      for (int column = 0; column < numberOfColumns; column++) {
        int state = backendModel.getCellState(row,column);
        frontEndCellGrid.get(row).get(column).setCellState(state);
      }
    }
  }

  public List<List<AbstractFrontendCell>> getFrontEndCellGrid() {
    return frontEndCellGrid;
  }
}
