package Model;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Grid {

  private final List<String[]> cellStates;
  private final List<List<Cell>> myCells = new ArrayList<>();
  final String PATH = "data/";
  private final double cellsPerRow;
  private final double cellsPerColumn;


  public Grid(String fileName) {
    cellStates = readAll(fileName);
    String[] firstRow = cellStates.get(0);
    cellsPerRow = Double.parseDouble(firstRow[0]);
    cellsPerColumn = Double.parseDouble(firstRow[1]);

    for (int i = 1; i < cellStates.size(); i++) {
      String[] row = cellStates.get(i);
      List<Cell> cellRow = new ArrayList<>();
      for (String stateString : row) {
        int state = Integer.parseInt(stateString);
        cellRow.add(new Cell(state));
      }
      myCells.add(cellRow);
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

  public List<List<Cell>> getGridCells() {
    return myCells;
  }

  public double getCellsPerRow() {
    return cellsPerRow;
  }

  public double getCellsPerColumn() {
    return cellsPerColumn;
  }
}
