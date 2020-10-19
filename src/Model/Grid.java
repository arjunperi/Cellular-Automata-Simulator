package Model;

import Controller.Controller;
import Controller.ControllerException;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Queue;
import javafx.scene.control.Alert;
import org.hamcrest.Condition;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Grid {

  private final List<List<Cell>> gridOfCells = new ArrayList<>();
  private static final String TOROIDAL = "TOROIDAL";
  private static final String FINITE = "FINITE";
  private static final String OSCILLATING = "OSCILLATING";
  final String PATH = "data/";
  private final double cellsPerRow;
  private final double cellsPerColumn;
  Properties propertyFile;
  Properties defaultFile;


  public Grid(String fileName, String modelType, Queue<Cell> emptyQueue) {
    this.propertyFile = getPropertyFile(fileName);
    this.defaultFile = getPropertyFile(modelType + "Default");
    List<String[]> cellStates = readAll(fileName);
    String fullModelClassName = "Model." + modelType + "Cell";
    String[] firstRow = cellStates.get(0);
    cellsPerRow = Double.parseDouble(firstRow[0]);
    cellsPerColumn = Double.parseDouble(firstRow[1]);
    checkCSVDimensions(cellStates.size() - 1, cellStates.get(1).length);
    try {
      //checkCSVDimensions(cellStates.size() - 1, cellStates.get(1).length);
      for (int i = 1; i < cellStates.size(); i++) {
        String[] row = cellStates.get(i);
        List<Cell> cellRow = new ArrayList<>();
        for (String stateString : row) {
          int state = Integer.parseInt(stateString);
          Class<?> cl = Class.forName(fullModelClassName);
          Cell cellToAdd = (Cell) cl.getConstructor(int.class, Queue.class)
              .newInstance(state, emptyQueue);
          cellRow.add(cellToAdd);
        }
        gridOfCells.add(cellRow);
      }
    } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | ModelException e) {
      showError(e.getMessage());
    }
  }

  private void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Controller Error");
    alert.setContentText(message);
    alert.showAndWait();
  }

  private void checkCSVDimensions(int columnCheck, int rowCheck) {
    if (!(columnCheck == cellsPerColumn && rowCheck == cellsPerRow)) {
      //showError("Invalid CSV Dimensions");
      throw new ModelException("Invalid CSV");
    }
  }

  protected List<Cell> getNeighbors(int x, int y) {
    String defaultShape = defaultFile.getProperty("Shape");
    String defaultNeighborhood = defaultFile.getProperty("NeighborhoodType");
    String defaultEdge = defaultFile.getProperty("EdgePolicy");
    String shapeAndType =
            (String) propertyFile.getOrDefault("Shape", defaultShape) + propertyFile.getOrDefault("NeighborhoodType", defaultNeighborhood);
    int[][] allPossibleNeighbors = neighborhoodTypes.valueOf(shapeAndType).neighborhood;
    List<Cell> neighbors = new ArrayList<>();
    if (propertyFile.getOrDefault("EdgePolicy", defaultEdge).equals(TOROIDAL)) {
      for (int[] possibleNeighbor : allPossibleNeighbors) {
        int currentX = ((int) getCellsPerRow() + x + possibleNeighbor[0]) % (int) getCellsPerRow();
        int currentY =
            ((int) getCellsPerRow() + y + possibleNeighbor[1]) % (int) getCellsPerColumn();
        neighbors.add(getCell(currentX, currentY));
      }
    } else if (propertyFile.getOrDefault("EdgePolicy", defaultEdge).equals(OSCILLATING)) {
      for (int[] possibleNeighbor : allPossibleNeighbors) {
        int currentX = x + possibleNeighbor[0];
        int currentY = y + possibleNeighbor[1];
        if (currentX < 0 || currentY < 0 || currentX >= getCellsPerColumn()
            || currentY >= getCellsPerRow()) {
          currentX = ((int) getCellsPerRow() + y + possibleNeighbor[1]) % (int) getCellsPerColumn();
          currentY = ((int) getCellsPerRow() + x + possibleNeighbor[0]) % (int) getCellsPerRow();
        }
        neighbors.add(getCell(currentX, currentY));
      }
    } else if (propertyFile.getOrDefault("EdgePolicy", defaultEdge).equals(FINITE)) {
      for (int[] possibleNeighbor : allPossibleNeighbors) {
        int currentX = x + possibleNeighbor[0];
        int currentY = y + possibleNeighbor[1];
        if (currentX < 0 || currentY < 0 || currentX >= getCellsPerColumn()
            || currentY >= getCellsPerRow()) {
          continue;
        }
        neighbors.add(getCell(currentX, currentY));
      }
    }

    return neighbors;
  }

  //make neighborhoods and have cells have access to neighborhood
  //use interface to get neighborhood, egt empty cells throughout the grid.
  //cast the grid to the interface, pass it into the cell.
  //this interface will define neighborhood, as well as the list/way to get empty cells.

  protected void updateCells() {
    for (int row = 0; row < gridOfCells.size(); row++) {
      for (int column = 0; column < gridOfCells.get(0).size(); column++) {
        List<Cell> cellNeighbors = getNeighbors(row, column);
        getCell(row, column).updateState(cellNeighbors);
      }
    }
  }

  protected void toNextState() {
    for (int row = 0; row < gridOfCells.size(); row++) {
      for (int column = 0; column < gridOfCells.get(0).size(); column++) {
        getCell(row, column).toNextState();
      }
    }
  }

  private List<String[]> readAll(String fileName) {
    try (CSVReader csvReader = new CSVReader(new FileReader(PATH + fileName))) {
      return csvReader.readAll();
    } catch (IOException | CsvException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  void writeToCSV(String fileOut) {
    try (CSVWriter csvWriter = new CSVWriter(new FileWriter(PATH + fileOut))) {
      String[] rowsAndColumns = new String[]{Double.toString(cellsPerRow),
          Double.toString(cellsPerColumn)};
      csvWriter.writeNext(rowsAndColumns, false);
      for (List<Cell> currentRow : gridOfCells) {
        String[] currentRowStates = new String[currentRow.size()];
        for (int i = 0; i < currentRow.size(); i++) {
          currentRowStates[i] = Integer.toString(currentRow.get(i).getCurrentState());
        }
        csvWriter.writeNext(currentRowStates, false);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Cell getCell(int row, int column) {
    return gridOfCells.get(row).get(column);
  }

  public double getCellsPerRow() {
    return cellsPerRow;
  }

  public double getCellsPerColumn() {
    return cellsPerColumn;
  }

  public Properties getPropertyFile(String fileName) {
    Properties propertyFile = new Properties();
    if (fileName.contains(".csv")){
      int lastSlash =fileName.lastIndexOf('/');
      int csvTrim = fileName.lastIndexOf('.');
      fileName = fileName.substring(lastSlash+1, csvTrim);
    }
    try {
      propertyFile.load(Controller.class.getClassLoader()
          .getResourceAsStream(fileName + ".properties"));
    } catch (Exception e) {
      throw new ControllerException("Invalid File Name", e);
    }
    return propertyFile;
  }

  public enum neighborhoodTypes {
    RECTANGLECARDINAL(new int[][]{{-1, 0},
        {0, 1}, {1, 0},
        {0, -1}}),
    RECTANGLEALL(new int[][]{{-1, 0}, {-1, 1},
        {0, 1}, {1, 1}, {1, 0}, {1, -1},
        {0, -1}, {-1, -1}}),
    RECTANGLECORNER(new int[][]{{-1, 1},
        {1, 1}, {1, -1},
        {-1, -1}}),
    TRIANGLECARDINAL(new int[][]{{-1, 0},
        {0, 1}, {1, 0},
        {0, -1}}),
    TRIANGLECORNER(new int[][]{{-1, 1},
        {1, 1}, {1, -1},
        {-1, -1}}),
    TRIANGLEALL(new int[][]{{-1, 0}, {-1, 1},
        {0, 1}, {1, 1}, {1, 0}, {1, -1},
        {0, -1}, {-1, -1}}),
    HEXAGONALL(new int[][]{{-1, 0}, {-1, 1}, {1, 1}, {1, 0}, {1, -1}, {-1, -1}}),
    HEXAGONCORNER(new int[][]{{-1, 1},
        {1, 1}, {1, -1},
        {-1, -1}}),
    HEXAGONCARDINAL(new int[][]{{-1, 0}, {-1, 1}, {1, 1}, {1, 0}, {1, -1}, {-1, -1}}),
    ;

    private final int[][] neighborhood;

    neighborhoodTypes(int[][] neighborhoods) {
      this.neighborhood = neighborhoods;
    }

    public int[][] getNeighborhood() {
      return neighborhood;
    }
  }
}

