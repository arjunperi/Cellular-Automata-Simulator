package Model;

import Controller.Controller;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

/**
 * Grid class designed to hold all cells in a given simulation and handle cell updates and information
 * transfers to the controller.
 *
 * @author Chris Shin
 * @author Alex Jimenez
 * @author Arjun Peri
 */

public class Grid {

  private List<List<Cell>> gridOfCells;
  private static final String TOROIDAL = "TOROIDAL";
  private static final String FINITE = "FINITE";
  private static final String OSCILLATING = "OSCILLATING";
  private static final String INVALID_MODEL = "Invalid Model Type";
  private static final String INVALID_CSV_GRID = "Invalid CSV Grid Formatting";
  private static final String INVALID_CSV_STATES = "Invalid CSV Initial State Inputs";
  private static final String INVALID_NEIGHBORHOOD_SHAPES =
      "Pausing Simulation: Invalid Shape/Neighborhood Type Input. " +
          "\nPress home to return to main menu and try loading a different simulation.";
  private static final String INVALID_FILE_OUT = "Invalid File Out";
  private static final String MODEL_DOT = "Model.";
  private static final String CELL = "Cell";
  private static final String GRID_ERROR = "Grid Error";
  private static final String SHAPE = "Shape";
  private static final String NEIGHBORHOOD_TYPE = "NeighborhoodType";
  private static final String INITIALIZE_CELL = "initializeCell";
  private static final String EDGE_POLICY = "EdgePolicy";
  private static final String GET_NEIGHBORS = "getNeighbors";
  private static final String DOT_CSV = ".csv";
  private static final String STATES = "States";
  final String PATH = "data/";
  private final double cellsPerRow;
  private final double cellsPerColumn;
  Properties propertyFile;
  Properties defaultPropertyFile;
  int[][] allPossibleNeighbors;
  private final String fullModelClassName;
  private List<Integer> allStatesList;
  private final List<String[]> cellStates;


  public Grid(String fileName, String modelType) {
    cellStates = readAll(fileName);
    fullModelClassName = MODEL_DOT + modelType + CELL;
    String[] firstRow = cellStates.get(0);
    cellsPerRow = Double.parseDouble(firstRow[0]);
    cellsPerColumn = Double.parseDouble(firstRow[1]);
    checkCSVDimensions(cellStates.size() - 1, cellStates.get(1).length);
    initializeCellCSV();
  }

  protected void initializeWithType(String initializationType, List<Integer> allStates) {
    this.allStatesList = allStates;
    gridOfCells = new ArrayList<>();
    try {
      Method method = this.getClass().getDeclaredMethod(INITIALIZE_CELL + initializationType);
      method.invoke(this);
    } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
      throw new ModelException("Invalid Initialization Type");
    }
  }

  private void initializeCellInOrder() {
    gridOfCells = new ArrayList<>();
    int stateRotator = 0;
    try {
      for (int i = 1; i < cellStates.size(); i++) {
        List<Cell> cellRow = new ArrayList<>();
        for (int j = 0; j < cellStates.get(1).length; j++) {
          int state = allStatesList.get(stateRotator % allStatesList.size());
          Class<?> cl = Class.forName(fullModelClassName);
          Cell cellToAdd = (Cell) cl.getConstructor(int.class)
              .newInstance(state);
          stateRotator++;
          cellRow.add(cellToAdd);
        }
        gridOfCells.add(cellRow);
      }
    } catch (NumberFormatException | IndexOutOfBoundsException e) {
      throw new ModelException(INVALID_CSV_STATES);
    } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
      throw new ModelException(INVALID_MODEL);
    }
  }

  private void initializeCellProbabilityDistribution() {
    Random randomNumberGenerator = new Random();
    gridOfCells = new ArrayList<>();
    try {
      for (int i = 1; i < cellStates.size(); i++) {
        List<Cell> cellRow = new ArrayList<>();
        for (int j = 0; j < cellStates.get(1).length; j++) {
          int state = allStatesList.get(randomNumberGenerator.nextInt(allStatesList.size()));
          Class<?> cl = Class.forName(fullModelClassName);
          Cell cellToAdd = (Cell) cl.getConstructor(int.class)
              .newInstance(state);
          cellRow.add(cellToAdd);
        }
        gridOfCells.add(cellRow);
      }
    } catch (NumberFormatException | IndexOutOfBoundsException e) {
      throw new ModelException(INVALID_CSV_STATES);
    } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
      throw new ModelException(INVALID_MODEL);
    }
  }

  private void initializeCellCSV() {
    gridOfCells = new ArrayList<>();
    try {
      for (int i = 1; i < cellStates.size(); i++) {
        String[] row = cellStates.get(i);
        List<Cell> cellRow = new ArrayList<>();
        for (String stateString : row) {
          int state = Integer.parseInt(stateString);
          Class<?> cl = Class.forName(fullModelClassName);
          Cell cellToAdd = (Cell) cl.getConstructor(int.class)
              .newInstance(state);
          cellRow.add(cellToAdd);
        }
        gridOfCells.add(cellRow);
      }
    } catch (NumberFormatException | IndexOutOfBoundsException e) {
      throw new ModelException(INVALID_CSV_STATES);
    } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
      throw new ModelException(INVALID_MODEL);
    }
  }

  protected void setPropertyFiles(Properties propertyFile, Properties defaultPropertyFile) {
    this.propertyFile = propertyFile;
    this.defaultPropertyFile = defaultPropertyFile;
  }

  private void checkCSVDimensions(int columnCheck, int rowCheck) throws ModelException {
    if (!(columnCheck == cellsPerColumn && rowCheck == cellsPerRow)) {
      throw new ModelException(INVALID_CSV_GRID);
    }
  }

  protected List<Cell> getNeighbors(int x, int y) throws ModelException {
    String defaultNeighborhood = defaultPropertyFile.getProperty(NEIGHBORHOOD_TYPE);
    String defaultShape = defaultPropertyFile.getProperty(SHAPE);
    String defaultEdge = defaultPropertyFile.getProperty(EDGE_POLICY);
    String shapeAndType =
        (String) propertyFile.getOrDefault(SHAPE, defaultShape) + propertyFile
            .getOrDefault(NEIGHBORHOOD_TYPE, defaultNeighborhood);
    try {
      allPossibleNeighbors = neighborhoodTypes.valueOf(shapeAndType).neighborhood;
    } catch (IllegalArgumentException e) {
      throw new ModelException(INVALID_NEIGHBORHOOD_SHAPES);
    }
    String edgePolicy = (String) propertyFile.getOrDefault(EDGE_POLICY, defaultEdge);
    List<Cell> neighbors = new ArrayList<>();
    try {
      Method method = this.getClass()
          .getDeclaredMethod(GET_NEIGHBORS + edgePolicy, int[][].class, List.class, int.class,
              int.class);
      method.invoke(this, allPossibleNeighbors, neighbors, x, y);
    } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
      neighbors = new ArrayList<>();
      getNeighborsFinite(allPossibleNeighbors, neighbors, x, y);
    }
    return neighbors;
  }

  private void getNeighborsFinite(int[][] allPossibleNeighbors, List<Cell> neighbors, int x,
      int y) {
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

  private void getNeighborsToroidal(int[][] allPossibleNeighbors, List<Cell> neighbors, int x,
      int y) {
    for (int[] possibleNeighbor : allPossibleNeighbors) {
      int currentX = ((int) getCellsPerRow() + x + possibleNeighbor[0]) % (int) getCellsPerRow();
      int currentY =
          ((int) getCellsPerRow() + y + possibleNeighbor[1]) % (int) getCellsPerColumn();
      neighbors.add(getCell(currentX, currentY));
    }
  }

  private void getNeighborsOscillating(int[][] allPossibleNeighbors, List<Cell> neighbors, int x,
      int y) {
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
  }

  protected void toNextState() {
    for (int row = 0; row < getCellsPerColumn(); row++) {
      for (int column = 0; column < getCellsPerRow(); column++) {
        getCell(row, column).toNextState();
      }
    }
  }

  private List<String[]> readAll(String fileName) throws ModelException {
    try (CSVReader csvReader = new CSVReader(new FileReader(PATH + fileName))) {
      return csvReader.readAll();
    } catch (IOException | CsvException e) {
      throw new ModelException(Model.INVALID);
    }
  }

  void writeToCSV(String fileOut) throws ModelException {
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
      throw new ModelException(INVALID_FILE_OUT);
    }
  }

  /**
   * Gets cell at specified row and column
   */

  public Cell getCell(int row, int column) {
    try {
      return gridOfCells.get(row).get(column);
    } catch (IndexOutOfBoundsException e) {
      throw new ModelException(INVALID_CSV_STATES);
    }

  }

  public double getCellsPerRow() {
    return cellsPerRow;
  }

  public double getCellsPerColumn() {
    return cellsPerColumn;
  }

  /**
   * Retrieves property file from resources as indicated by the filename
   */

  public Properties getPropertyFile(String fileName) {
    Properties propertyFile = new Properties();
    if (fileName.contains(DOT_CSV)) {
      int lastSlash = fileName.lastIndexOf(Model.SLASH);
      int csvTrim = fileName.lastIndexOf(Model.DOT);
      fileName = fileName.substring(lastSlash + 1, csvTrim);
    }
    try {
      propertyFile.load(Controller.class.getClassLoader()
          .getResourceAsStream(fileName + Model.PROPERTIES));
    } catch (Exception e) {
      throw new ModelException(Model.INVALID, e);
    }
    return propertyFile;
  }

  /**
   * Enum used to define the different types of neighborhoods possible for different shapes and
   * configurations.
   */

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
  }
}

