package Controller;

import Model.Model;
import View.View;
import View.FrontEndCell;


import java.io.*;
import java.lang.reflect.InvocationTargetException;

import java.util.*;

import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;


public class Controller {

  private Model mainModel;
  private final View mainView;
  private boolean simIsSet = false;

  private boolean graphShowing = false;
  private final Map<Integer, XYChart.Series> stateSeries = new HashMap<>();
  private final Map<Integer, Integer> stateCountsMap = new HashMap<>();

  private String modelType;
  private Properties defaultFile;
  private Properties currentPropertyFile;

  private static final String RESOURCES = "Resources/";
  public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
  public static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES;
  public static final String STYLESHEET = "GameOfLife.css";
  public static final String BLANK = " ";
  private final ResourceBundle myResources;
  private final Map<Integer, String> stateColorMapping = new HashMap<>();
  private List<List<String>> frontEndCellColors;
  private String currentFileName;
  private GraphController graphController;
  private final Button homeButton = makeButton("Home", event -> initializeButtonMenu());
  private TextField inputText;

  public Controller() {
    this.mainView = new View();
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "English");
    initializeButtonMenu();
  }

  public void initializeButtonMenu() {
    if(this.graphShowing){
      this.graphController.closeGraph();
      this.graphShowing = false;
    }
    mainView.getCenterGroup().getChildren().clear();
    mainView.getTopGroup().getChildren().clear();
    VBox result = new VBox();
    inputText = new TextField();
    inputText.setId("inputTextBox");
    EventHandler<ActionEvent> event = e -> {
      currentFileName = inputText.getText();
      try {
        currentPropertyFile = getPropertyFile(currentFileName);
        displayInfo(currentFileName);
      } catch (ControllerException c) {
        showError(c.getMessage());
      }
    };
    inputText.setOnAction(event);
    Label inputLabel = new Label("Enter Simulation Name and Press Enter");
    result.getChildren().add(inputLabel);
    result.getChildren().add(inputText);
    mainView.getCenterGroup().getChildren().add(result);
  }

  public void displayInfo(String fileName) {
    mainView.getCenterGroup().getChildren().clear();
    HBox result = new HBox();
    System.out.println(currentFileName);
    Button startButton = makeButton(currentFileName,
            event -> initializeSimulation(currentFileName + ".csv"));
    result.getChildren().add(homeButton);
    result.getChildren().add(startButton);
    mainView.getTopGroup().getChildren().add(result);
    try {
      Text startupText = new Text();
      String type = (String) currentPropertyFile.getOrDefault("Type", "No Type Specified");
      String title = (String) currentPropertyFile.getOrDefault("Title", "No Title specified");
      String author = (String) currentPropertyFile.getOrDefault("Author", "No Author Specified");
      String description = (String) currentPropertyFile.getOrDefault("Description", "No Description Specified");
      startupText.setText(type + "\n" + title + "\n" + author + "\n" + description);
      mainView.getCenterGroup().getChildren().add(startupText);
    } catch (ControllerException e) {
      showError(e.getMessage());
    }
  }

  public void initializeSimulation(String fileName) {
    mainView.getCenterGroup().getChildren().clear();
    mainView.getTopGroup().getChildren().clear();
    frontEndCellColors = new ArrayList<>();
    stateColorMapping.clear();
    try {
      modelType = currentPropertyFile.getProperty("Type");
      this.mainModel = new Model((String) currentPropertyFile.getOrDefault("FileName", currentFileName + ".csv"), modelType);
      defaultFile = getPropertyFile(modelType + "Default");
      String defaultStates = defaultFile.getProperty("States");
      mainModel.initializeAllStates((String) currentPropertyFile.getOrDefault("States", defaultStates));
      this.frontEndCellColors = updateFrontEndCellColors();
      mainView.initializeFrontEndCells(mainModel.getNumberOfRows(),
          mainModel.getNumberOfColumns(), frontEndCellColors);
      simIsSet = true;
      addCellEventHandlers();
      initializeSimulationMenu();
    } catch (Exception e) {
      showError("Invalid Model Type");
      initializeButtonMenu();
    }
  }

  public void getSaveInputs() {
    mainModel.setPaused();
    Dialog saveBox = new TextInputDialog();
    saveBox.getDialogPane().lookupButton(ButtonType.OK).setId("SaveOK");
    TextField titleInput = new TextField();
    titleInput.setId("titleInput");
    TextField authorInput = new TextField();
    authorInput.setId("authorInput");
    TextField descriptionInput = new TextField();
    descriptionInput.setId("descriptionInput");
    GridPane grid = new GridPane();
    titleInput.setPromptText("Title: ");
    authorInput.setPromptText("Author: ");
    descriptionInput.setPromptText("Description: ");
    GridPane.setConstraints(titleInput, 0, 0);
    grid.getChildren().add(titleInput);
    GridPane.setConstraints(authorInput, 0, 1);
    grid.getChildren().add(authorInput);
    GridPane.setConstraints(descriptionInput, 0, 2);
    grid.getChildren().add(descriptionInput);
    saveBox.getDialogPane().setContent(grid);
    Optional<String> result = saveBox.showAndWait();
    Properties savedProperties = new Properties();
    savedProperties.setProperty("Title", titleInput.getText());
    savedProperties.setProperty("Author", authorInput.getText());
    savedProperties.setProperty("Description", descriptionInput.getText());
    savedProperties.setProperty("Type", modelType);
    if (result.isPresent()) {
      writeToPropertyFile(savedProperties);
      mainModel.writeToCSV(savedProperties.getProperty("Title") + ".csv");
    }
    mainModel.switchPause();
  }

  public void writeToPropertyFile(Properties propertyFile) {
    try {
      FileWriter writer = new FileWriter(
              "Properties/" + propertyFile.getProperty("Title") + ".properties");
      propertyFile.store(writer, null);
    }catch (IOException ex) {
      ex.printStackTrace();
    }
    initializeButtonMenu();
  }

  public void gameStep() {
    if (simIsSet) {
      if(mainModel.modelStep() && graphShowing){
        this.graphShowing = graphController.updateGraph();
      }
      this.frontEndCellColors = updateFrontEndCellColors();
      mainView.viewStep(this.frontEndCellColors);
    }
  }

  private List<List<String>> updateFrontEndCellColors() {
    List<List<String>> frontEndCellColors = new ArrayList<>();
    for (int row = 0; row < mainModel.getNumberOfRows(); row++) {
      List<String> colorRow = new ArrayList<>();
      for (int column = 0; column < mainModel.getNumberOfColumns(); column++) {
        int currentState = mainModel.getCellState(row, column);
        if (!stateColorMapping.containsKey(currentState)) {
          initializeColorMapping(currentState);
        }
        colorRow.add(stateColorMapping.get(mainModel.getCellState(row, column)));
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


  private void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Controller Error");
    alert.setContentText(message);
    alert.showAndWait();
  }


  private Button makeButton(String buttonName, EventHandler<ActionEvent> handler) {
    Button result = new Button();
    result.setId(buttonName);
    result.setText(buttonName);
    result.setOnAction(handler);
    return result;
  }

  public void initializeColorMapping(int state) {
    String defaultColor = defaultFile.getProperty(String.valueOf(state));
    String color = (String) currentPropertyFile.getOrDefault(String.valueOf(state), defaultColor);
    if (!stateColorMapping.containsKey(state)) {
      stateColorMapping.put(state, color);
    }
  }

  public Properties getPropertyFile(String fileName) {
    Properties propertyFile = new Properties();
    try {
      propertyFile
          .load(Controller.class.getClassLoader().getResourceAsStream(fileName + ".properties"));
    } catch (Exception e) {
      throw new ControllerException("Invalid File Name", e);
    }
    return propertyFile;
  }


  public void handleKeyInput(KeyCode code) {
    switch (code) {
      case P -> mainModel.switchPause();
      case S -> {
        mainModel.step();
        if(graphController!=null) {
          graphController.updateGraph();
        }
      }
      case W -> mainModel.speedUp();
      case Q -> mainModel.slowDown();
    }
  }

  public void addCellEventHandlers() {
    List<List<FrontEndCell>> frontEndCells = this.mainView.getFrontEndCellGrid();
    for (List<FrontEndCell> cellRow : frontEndCells) {
      for (FrontEndCell cell : cellRow) {
        cell.setOnMouseClicked(this::changeClickedCellState);
      }
    }
  }

  public void changeClickedCellState(Event event) {
    EventTarget clickedEvent = event.getTarget();
    FrontEndCell clickedCell = (FrontEndCell) clickedEvent;
    int clickedCellRow = clickedCell.getRow();
    int clickedCellColumn = clickedCell.getColumn();
    mainModel.cycleState(clickedCellRow, clickedCellColumn);
  }

  public void initializeSimulationMenu() {
    HBox test = new HBox();
    Button saveButton = makeButton("Save", event -> getSaveInputs());
    test.getChildren().add(homeButton);
    test.getChildren().add(saveButton);
    test.getChildren().add(makeButton("changeColors", event -> changeColorsPopUp()));
    test.getChildren().add(makeButton("State Concentration Graph", event -> createGraph()));
    mainView.getTopGroup().getChildren().add(test);
  }

  public void changeColorsPopUp() {
    mainModel.setPaused();
    Dialog colorBox = new TextInputDialog();
    TextField colorInput = new TextField();
    colorInput.setId("colorInput");
    TextField stateInput = new TextField();
    colorInput.setId("stateInput");
    GridPane grid = new GridPane();
    colorInput.setPromptText("New Color");
    stateInput.setPromptText("State to Change");
    GridPane.setConstraints(stateInput, 0, 0);
    grid.getChildren().add(stateInput);
    GridPane.setConstraints(colorInput, 0, 1);
    grid.getChildren().add(colorInput);
    colorBox.getDialogPane().setContent(grid);
    colorBox.showAndWait();
    if(stateInput.getText().length() + colorInput.getText().length() > 0){
      updateColorStateMapping(stateInput.getText(), colorInput.getText());
    }
    mainModel.switchPause();
  }

  public void updateColorStateMapping(String state, String color) {
    try {
      int stateInt = Integer.parseInt(state);
      if (!this.stateColorMapping.containsKey(stateInt)) {
        throw new IllegalArgumentException();
      }
      Paint.valueOf(color);
      this.stateColorMapping.put(stateInt, color);
    } catch (Exception e) {
      showError("Please enter a valid color state mapping");
    }
  }

  public void createGraph(){
    if(this.graphShowing) {
      showError("Please close current graph instance");
      return;
    }
    this.graphController = new GraphController(this.mainModel, this.stateColorMapping);
    this.graphShowing = true;
  }
}





