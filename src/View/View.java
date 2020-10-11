package View;

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

  private static final Paint BACKGROUND = Color.AZURE;

  private final Model backendModel;
  private final String fullAbstractCellType;
  private final int numberOfRows;
  private final int numberOfColumns;
  private final Group root;
  private List<List<AbstractFrontEndCell>> frontEndCellGrid;


  public View(Model model, String modelType) {
    root = new Group();
    backendModel = model;
    this.fullAbstractCellType = "View." + modelType + "FrontEndCell";
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
    double xOffset = Simulation.SCENE_WIDTH / (double) numberOfRows;
    double yOffset = Simulation.SCENE_HEIGHT / (double) numberOfColumns;
    double x;
    double y = 0;
    for (int row = 0; row < numberOfRows; row++) {
      x = 0;
      List<AbstractFrontEndCell> frontEndCellRow = new ArrayList<>();
      for (int column = 0; column < numberOfColumns; column++) {
        addFrontEndCellToScene(row, column, x, y, xOffset, yOffset, frontEndCellRow);
        x += xOffset;
      }
      frontEndCellGrid.add(frontEndCellRow);
      y += yOffset;
    }
  }

  private void addFrontEndCellToScene(int row, int column, double x, double y, double xOffset,
      double yOffset, List<AbstractFrontEndCell> frontEndCellRow) {
    try {
      int state = backendModel.getCellState(row, column);
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

  private void updateFrontEndCells() {
    for (int row = 0; row < numberOfRows; row++) {
      for (int column = 0; column < numberOfColumns; column++) {
        int state = backendModel.getCellState(row, column);
        frontEndCellGrid.get(row).get(column).setCellState(state);
      }
    }
  }

  public List<List<AbstractFrontEndCell>> getFrontEndCellGrid() {
    return frontEndCellGrid;
  }
}
