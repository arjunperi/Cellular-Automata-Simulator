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
  private final Group root;
  private List<List<AbstractFrontendCell>> frontEndCellGrid;


  public View(Model model) {
    root = new Group();
    backendModel = model;
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
    List<List<Cell>> backendCells = backendModel.getModelCells();
    double xOffset = Simulation.SCENE_WIDTH / backendCells.size();
    double yOffset = Simulation.SCENE_HEIGHT / backendCells.get(0).size();
    double x;
    double y = 0;

    for (List<Cell> currentBackendCellRow : backendCells) {
      x = 0;
      List<AbstractFrontendCell> frontEndCellRow = new ArrayList<>();
      for (Cell currentBackEndCell : currentBackendCellRow) {
        int state = currentBackEndCell.getCurrentState();
        AbstractFrontendCell currentFrontEndCell = new GameOfLifeFrontendCell(state, x, y, xOffset,
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
    List<List<Cell>> backendCells = backendModel.getModelCells();
    for (int row = 0; row < backendCells.size(); row++) {
      for (int column = 0; column < backendCells.get(row).size(); column++) {
        int state = backendCells.get(row).get(column).getCurrentState();
        frontEndCellGrid.get(row).get(column).setCellState(state);
      }
    }
  }

  public List<List<AbstractFrontendCell>> getFrontEndCellGrid() {
    return frontEndCellGrid;
  }
}
