package Controller;

import Model.Model;
import View.View;
import View.FrontEndCell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;

public class Controller {

  private Model mainModel;
  private final View mainView;
  private boolean simIsSet = false;

  private static final String RESOURCES = "Resources/";
  public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
  public static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES;
  public static final String STYLESHEET = "GameOfLife.css";
  public static final String BLANK = " ";
  private final ResourceBundle myResources;
  private final Map<Integer, String> stateColorMapping = new HashMap<>();
  private List<List<String>> frontEndCellColors;
  private String simulationName;


  public Controller() {
    this.mainView = new View();
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "English");
    initializeButtonMenu();
  }

  //on button press
  public void initializeSimulation(String fileName, String modelType, String fileOut) {
    this.simulationName=modelType;
    frontEndCellColors = new ArrayList<>();
    this.mainModel = new Model(fileName, modelType, fileOut);

    this.frontEndCellColors = updateFrontEndCellColors();
    mainView.initializeFrontEndCells(mainModel.getNumberOfRows(),
        mainModel.getNumberOfColumns(), frontEndCellColors);
    simIsSet = true;
    addCellEventHandlers();
  }

  public void gameStep() {
    if (simIsSet) {
      mainModel.modelStep();
      this.frontEndCellColors = updateFrontEndCellColors();
      mainView.viewStep(this.frontEndCellColors);
    }
  }

  private List<List<String>> updateFrontEndCellColors() {
    List<List<String>> frontEndCellColors = new ArrayList<>();
    for(int row=0; row<mainModel.getNumberOfRows();row++) {
      List<String> colorRow = new ArrayList<>();
      for(int column=0; column< mainModel.getNumberOfColumns(); column++) {
        int currentState = mainModel.getCellState(row,column);
        if(!stateColorMapping.containsKey(currentState)) {
          initializeColorMapping(currentState);
        }
        colorRow.add(stateColorMapping.get(mainModel.getCellState(row,column)));
      }
      frontEndCellColors.add(colorRow);
    }
    return frontEndCellColors;
  }

  public Scene setupScene() {
    return mainView.setupScene();
  }

  public Model getMainModel() {
    return mainModel;
  }

  public View getMainView() {
    return mainView;
  }


  public void initializeButtonMenu() {
    HBox result = new HBox();
    ComboBox comboBoxGameOfLife = new ComboBox(FXCollections.observableArrayList(
            "ConwayStatesPulsar", "ConwayStatesBlinker", "ConwayStatesBlock", "ConwayStatesToad",
            "ConwayStatesBeacon"));
    comboBoxGameOfLife
            .setOnAction(event -> transition("GameOfLife", comboBoxGameOfLife.getValue().toString()));
    comboBoxGameOfLife.setPromptText("GameOfLife");
    ComboBox comboBoxPercolation = new ComboBox(
            FXCollections.observableArrayList("PercolationExample"));
    comboBoxPercolation
            .setOnAction(event -> transition("Percolation", comboBoxPercolation.getValue().toString()));
    comboBoxPercolation.setPromptText("Percolation");
    ComboBox comboBoxRPS = new ComboBox(FXCollections.observableArrayList("RPSExample"));
    comboBoxRPS.setOnAction(event -> transition("RPS", comboBoxRPS.getValue().toString()));
    comboBoxRPS.setPromptText("RPS");
    ComboBox comboBoxSpreadingFire = new ComboBox(
            FXCollections.observableArrayList("SpreadingFire20"));
    comboBoxSpreadingFire.setOnAction(
            event -> transition("SpreadingFire", comboBoxSpreadingFire.getValue().toString()));
    comboBoxSpreadingFire.setPromptText("SpreadingFire");
    mainView.getRoot().getChildren().add(comboBoxGameOfLife);
    mainView.getRoot().getChildren().add(comboBoxPercolation);
    mainView.getRoot().getChildren().add(comboBoxRPS);
    mainView.getRoot().getChildren().add(comboBoxSpreadingFire);
    result.getChildren().add(comboBoxGameOfLife);
    result.getChildren().add(comboBoxPercolation);
    result.getChildren().add(comboBoxRPS);
    result.getChildren().add(comboBoxSpreadingFire);
    mainView.getRoot().getChildren().add(result);
  }


  public void transition(String token, String fileName) {
    try {
      initializeSimulation(fileName + ".csv", token, fileName + "Out.csv");
    } catch (Exception e) {
      throw new UnsupportedOperationException(String.format("Unrecognized command: %s", token));
    }
  }

  public void initializeColorMapping(int state) {
    String result = "";
    String propertyFileName = simulationName + ".properties";
    try {
      Properties prop = new Properties();
      prop.load(Controller.class.getClassLoader().getResourceAsStream(propertyFileName));
      result = prop.getProperty(String.valueOf(state));
      if(!stateColorMapping.containsKey(state)) {
        stateColorMapping.put(state,result);
      }
    } catch (IOException e) {
      System.out.println("error");
    }
  }

  public void handleKeyInput(KeyCode code) {
    switch (code) {
      case P -> mainModel.switchPause();
      case S -> mainModel.step();
      case RIGHT -> mainModel.speedUp();
      case LEFT-> mainModel.slowDown();
    }
  }

  public void addCellEventHandlers() {
    List<List<FrontEndCell>> frontEndCells = this.mainView.getFrontEndCellGrid();
    for(List<FrontEndCell> cellRow : frontEndCells){
      for(FrontEndCell cell : cellRow){
        cell.setOnMouseClicked(event -> changeClickedCellState(event));
      }
    }
  }


  public void changeClickedCellState(Event event){
    FrontEndCell clickedCell = (FrontEndCell) event.getSource();
    int clickedCellRow = clickedCell.getRow();
    int clickedCellColumn = clickedCell.getColumn();
    mainModel.getGridOfCells().getCell(clickedCellRow, clickedCellColumn).cycleNextState();
  }
}



