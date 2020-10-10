package View;

import Controller.Controller;
import cellsociety.Simulation;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.List;
import java.util.ResourceBundle;


public class View {

  private static final Paint BACKGROUND = Color.AZURE;

  private Controller controller;
  private final int numberOfRows;
  private final int numberOfColumns;
  private final Group root;
  private List<List<AbstractFrontendCell>> frontEndCellGrid;


  // constants
  private static final String RESOURCES = "resources/";
  // use Java's dot notation, like with import, for properties
  public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
  // use file system notation, standard Unix slashes, for other kinds of files
  public static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES;
  public static final String STYLESHEET = "GameOfLife.css";
  public static final String BLANK = " ";
  private ResourceBundle myResources;

  private boolean startButtonPressed;


  //refactor the parameters
  public View(String language, Controller modelController) {
    root = new Group();
    controller = modelController;
    numberOfColumns = controller.getNumberOfColumns();
    numberOfRows = controller.getNumberOfRows();
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    initializeStartupButtons();
  }

  public void initializeStartupButtons(){
   HBox result = new HBox();
//   Node startButton = makeButton(myResources.getString("StartButtonText"), event -> transition());
//   result.getChildren().add(startButton);
//   startButton.setLayoutX(Simulation.SCENE_HEIGHT/2);
//   startButton.setLayoutY(Simulation.SCENE_HEIGHT/2);
   Node conwayButton = makeButton(myResources.getString("ConwayButtonText"), event -> controller.transition("GameOfLifeModel", "ConwayStatesPulsar.csv"));
   Node percolationButton = makeButton(myResources.getString("PercolationButtonText"), event -> controller.transition("PercolationModel", "PercolationExample.csv"));
   result.getChildren().add(percolationButton);
   result.getChildren().add(conwayButton);
   root.getChildren().add(result);
  }


  // Makes a button using either an image or a label
  public Node makeButton (String property, EventHandler<ActionEvent> handler) {
    Button result = new Button();
    result.setOnAction(handler);
    result.setId(property);
    result.setText(property);
    return result;
  }

    public Scene setupScene() {
    Scene scene = new Scene(root, Simulation.SCENE_WIDTH, Simulation.SCENE_HEIGHT, BACKGROUND);
    scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
    return scene;
  }


  public void viewStep() {
    if (controller.checkButtonPress()){
      updateFrontEndCells();
    }
  }

  public void initializeFrontEndCells() {
    root.getChildren().clear();
    frontEndCellGrid = new ArrayList<>();
    double xOffset = Simulation.SCENE_WIDTH / (double)numberOfRows;
    double yOffset = Simulation.SCENE_HEIGHT / (double)numberOfColumns;
    double x;
    double y = 0;

    for (int row = 0; row < numberOfRows; row++) {
      x = 0;
      List<AbstractFrontendCell> frontEndCellRow = new ArrayList<>();
      for (int column = 0; column < numberOfColumns; column++) {
        int state = controller.getCellState(row,column);
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
        int state = controller.getCellState(row,column);
        frontEndCellGrid.get(row).get(column).setCellState(state);
      }
    }
  }

  public List<List<AbstractFrontendCell>> getFrontEndCellGrid() {
    return frontEndCellGrid;
  }
}
