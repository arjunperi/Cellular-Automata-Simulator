package View;

import Model.Grid;
import cellsociety.Simulation;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;


public class View {

  private final Group root;
  private List<List<FrontEndCell>> frontEndCellGrid;
  private List<List<String>> frontEndCellColors;

  public View() {
    root = new Group();
  }

  public Scene setupScene() {
    return new Scene(root, Simulation.SCENE_WIDTH, Simulation.SCENE_HEIGHT, Simulation.BACKGROUND);
  }

  public void viewStep(List<List<String>> frontEndCellColors) {
    updateFrontEndCells(frontEndCellColors);
  }

  public void initializeFrontEndCells(int numberOfRows, int numberOfColumns,
      List<List<String>> frontEndCellColors) {
    this.frontEndCellColors = frontEndCellColors;
    this.frontEndCellGrid = new ArrayList<>();
    double xOffset = Simulation.SCENE_WIDTH / (double) numberOfRows;
    double yOffset = Simulation.SCENE_HEIGHT / (double) numberOfColumns;
    double x;
    double y = 0;
    for (int row = 0; row < numberOfRows; row++) {
      x = 0;
      List<FrontEndCell> frontEndCellRow = new ArrayList<>();
      for (int column = 0; column < numberOfColumns; column++) {
        addFrontEndCellToScene(row, column, x, y, xOffset, yOffset, frontEndCellRow);
        x += xOffset;
      }
      frontEndCellGrid.add(frontEndCellRow);
      y += yOffset;
    }
  }

    private void addFrontEndCellToScene ( int row, int column, double x, double y, double xOffset,
    double yOffset, List<FrontEndCell > frontEndCellRow){
      String stateString = this.frontEndCellColors.get(row).get(column);
      FrontEndCell currentFrontEndCell = new FrontEndCell(stateString, x, y, xOffset, yOffset);
      frontEndCellRow.add(currentFrontEndCell);
      root.getChildren().add(currentFrontEndCell.getCellShape());
    }

    private void updateFrontEndCells (List < List < String >> frontEndCellColors) {
      this.frontEndCellColors = frontEndCellColors;
      for (int row = 0; row < this.frontEndCellColors.size(); row++) {
        for (int column = 0; column < this.frontEndCellColors.get(0).size(); column++) {
          frontEndCellGrid.get(row).get(column)
              .updateCellColor(this.frontEndCellColors.get(row).get(column));
        }
      }
    }

    public List<List<FrontEndCell>> getFrontEndCellGrid () {
      return frontEndCellGrid;
    }

    public Group getRoot () {
      return this.root;
    }
  }
