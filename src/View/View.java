package View;

import Model.Grid;
import Model.Model;
import cellsociety.Simulation;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.List;


public class View {
  private final Group root;
  private List<List<AbstractFrontEndCell>> frontEndCellGrid;

  public View() {
    root = new Group();
  }

  public Scene setupScene() {
    return new Scene(root, Simulation.SCENE_WIDTH, Simulation.SCENE_HEIGHT, Simulation.BACKGROUND);
  }

  public void viewStep(Grid grid) {
    updateFrontEndCells(grid);
  }

  public void initializeFrontEndCells(String modelType, int numberOfRows, int numberOfColumns, Grid grid) {
    String fullAbstractCellType = "View." + modelType + "FrontEndCell";
    frontEndCellGrid = new ArrayList<>();
    double xOffset = Simulation.SCENE_WIDTH / (double) numberOfRows;
    double yOffset = Simulation.SCENE_HEIGHT / (double) numberOfColumns;
    double x;
    double y = 0;
    for (int row = 0; row < numberOfRows; row++) {
      x = 0;
      List<AbstractFrontEndCell> frontEndCellRow = new ArrayList<>();
      for (int column = 0; column < numberOfColumns; column++) {
        addFrontEndCellToScene(row, column, x, y, xOffset, yOffset, frontEndCellRow, fullAbstractCellType, grid);
        x += xOffset;
      }
      frontEndCellGrid.add(frontEndCellRow);
      y += yOffset;
    }
  }

  private void addFrontEndCellToScene(int row, int column, double x, double y, double xOffset,
      double yOffset, List<AbstractFrontEndCell> frontEndCellRow, String fullAbstractCellType, Grid grid) {
    try {
      int state = grid.getCell(row, column).getCurrentState();
      Class<?> cl = Class.forName(fullAbstractCellType);
      AbstractFrontEndCell currentFrontEndCell = (AbstractFrontEndCell) cl
          .getConstructor(int.class, double.class,
              double.class, double.class, double.class).newInstance(state, x, y, xOffset, yOffset);
      frontEndCellRow.add(currentFrontEndCell);
      root.getChildren().add(currentFrontEndCell.getCellShape());
    } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void updateFrontEndCells(Grid grid) {
    for (int row = 0; row < grid.getCellsPerColumn(); row++) {
      for (int column = 0; column < grid.getCellsPerRow(); column++) {
        int state = grid.getCell(row, column).getCurrentState();
        frontEndCellGrid.get(row).get(column).setCellState(state);
      }
    }
  }

  public List<List<AbstractFrontEndCell>> getFrontEndCellGrid() {
    return frontEndCellGrid;
  }
}
