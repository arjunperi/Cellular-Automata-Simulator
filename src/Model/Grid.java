package Model;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import javafx.scene.control.Alert;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Grid {

  private final List<List<Cell>> gridOfCells = new ArrayList<>();
  final String PATH = "data/";
  private final double cellsPerRow;
  private final double cellsPerColumn;


  public Grid(String fileName, String modelType) {
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
          Cell cellToAdd = (Cell) cl.getConstructor(int.class).newInstance(state);
          cellRow.add(cellToAdd);
        }
        gridOfCells.add(cellRow);
      }
    } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException| ModelException e) {
      showError(e.getMessage());
    }
  }

  private void showError(String message){
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Controller Error");
    alert.setContentText(message);
    alert.showAndWait();
  }

  private void checkCSVDimensions(int columnCheck, int rowCheck){
    if (!(columnCheck == cellsPerColumn && rowCheck == cellsPerRow)){
      showError("Invalid CSV Dimensions");
      throw new ModelException("Invalid CSV");
    }
  }

  protected List<Cell> getNeighbors(int x, int y, Cell currentCell) {
    int[][] allPossibleNeighbors = currentCell.getPossibleNeighbors();
    List<Cell> neighbors = new ArrayList<>();
    for (int[] possibleNeighbor : allPossibleNeighbors) {
      int currentX = x + possibleNeighbor[0];
      int currentY = y + possibleNeighbor[1];
      if (currentX < 0 || currentY < 0 || currentX >= getCellsPerColumn()
          || currentY >= getCellsPerRow()) {
        continue;
      }
      neighbors.add(getCell(currentX, currentY));
    }
    return neighbors;
  }

  protected void updateCells() {
    for (int row = 0; row < gridOfCells.size(); row++) {
      for (int column = 0; column < gridOfCells.get(0).size(); column++) {
        List<Cell> cellNeighbors = getNeighbors(row, column, gridOfCells.get(row).get(column));
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
}

