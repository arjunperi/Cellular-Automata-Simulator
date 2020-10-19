package Controller;

import Model.Model;
import View.View;
import View.FrontEndCell;


import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;


public class Controller {

  private Model mainModel;
  private final View mainView;
  private boolean simIsSet = false;
  private boolean graphShowing = false;
  private String modelType;
  private Properties defaultFile;

  private static final String RESOURCES = "Resources/";
  public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
  public static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES;
  public static final String STYLESHEET = "GameOfLife.css";
  public static final String BLANK = " ";
  private  ResourceBundle projectTextResources;
  private final Map<Integer, String> stateColorMapping = new HashMap<>();
  private List<List<String>> frontEndCellColors;
  private String currentFileName;
  private GraphController graphController;
  private TextField inputText;

  public Controller() {
    projectTextResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "English");
    this.mainView = new View(projectTextResources);
    initializeSplashMenu();
  }

  public void initializeSplashMenu() {
    if(this.graphShowing){
      this.graphController.closeGraph();
      this.graphShowing = false;
    }
    this.inputText = new TextField();
    EventHandler<ActionEvent> simulationInputEvent = e -> {
      String fileChosen = inputText.getText();
      try {
        Properties propertyFile = getPropertyFile(fileChosen);
        modelType = propertyFile.getProperty("Type");
        displayInfo(fileChosen);
      } catch (ControllerException c) {
        showError(c.getMessage());
      }
    };
    this.mainView.createInputTextField(this.inputText, simulationInputEvent, event -> changeTextResourceFile("English"),
        event -> changeTextResourceFile("Spanish"), event -> changeTextResourceFile("FakeLanguage"));
    this.mainView.setHomeButton(homeButtonEvent -> initializeSplashMenu());
  }

  public void displayInfo(String fileName) {
   currentFileName = fileName;
    try {
      Properties propertyFile = getPropertyFile(fileName);
      this.mainView.displaySimulationInfo(fileName,propertyFile, event -> initializeSimulation(fileName + ".csv"));
    } catch (ControllerException e){
      showError(e.getMessage());
    }
  }

  public void initializeSimulation(String fileName) {
    mainView.clearCenterGroup();
    mainView.clearTopMenuGroup();
    frontEndCellColors = new ArrayList<>();
    stateColorMapping.clear();
    try {
      Properties propertyFile = getPropertyFile(currentFileName);
      Class<?> cl = Class.forName("Model." + modelType + "Model");
      this.mainModel = (Model) cl.getConstructor(String.class, String.class)
          .newInstance(fileName, modelType);
      defaultFile = getPropertyFile(modelType + "Default");
      String defaultStates = defaultFile.getProperty("States");
      mainModel.initializeAllStates((String) propertyFile.getOrDefault("States", defaultStates));
      this.frontEndCellColors = updateFrontEndCellColors();
      mainView.initializeFrontEndCells(mainModel.getNumberOfRows(),
          mainModel.getNumberOfColumns(), frontEndCellColors);
      simIsSet = true;
      addCellEventHandlers();
      initializeSimulationMenu();
    } catch (Exception e) {
      showError("Invalid Model Type");
      initializeSplashMenu();
    }
  }

  public void getSaveInputs() {
    mainModel.setPaused();
    TextField titleInput = new TextField();
    TextField authorInput = new TextField();
    TextField descriptionInput = new TextField();

    Dialog saveBox = this.mainView.showSaveInputs(titleInput,authorInput,descriptionInput);
    Optional<String> saveBoxResult = saveBox.showAndWait();
    Properties savedProperties = new Properties();

    savedProperties.setProperty("Title", titleInput.getText());
    savedProperties.setProperty("Author", authorInput.getText());
    savedProperties.setProperty("Description", descriptionInput.getText());
    savedProperties.setProperty("Type", modelType);
    if (saveBoxResult.isPresent()) {
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
    initializeSplashMenu();
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

  private void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Controller Error");
    alert.setContentText(message);
    alert.showAndWait();
  }


  public void initializeColorMapping(int state) {
    Properties propertyFile = getPropertyFile(currentFileName);
    String defaultColor = defaultFile.getProperty(String.valueOf(state));
    String color = (String) propertyFile.getOrDefault(String.valueOf(state), defaultColor);
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

  public void changeTextResourceFile(String language){
    ResourceBundle newBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    this.projectTextResources = newBundle;
    this.mainView.updateResourceBundle(newBundle);
    initializeSplashMenu();
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
    this.mainView.initializeSimulationMenu(saveEvent -> getSaveInputs(), colorEvent -> changeColorsPopUp(), graphEvent -> createGraph());
  }

  public void changeColorsPopUp() {
    mainModel.setPaused();
    TextField colorInput = new TextField();
    TextField stateInput = new TextField();
    Dialog colorBox = this.mainView.changeColorsPopUp(stateInput, colorInput);
    Optional<ButtonType> colorBoxResult = colorBox.showAndWait();
    if(colorBoxResult.isPresent()){
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
      showError(this.projectTextResources.getString("ColorMappingError"));
    }
  }

  public void createGraph(){
    if(this.graphShowing) {
      showError(this.projectTextResources.getString("GraphError"));
      return;
    }
    this.graphController = new GraphController(this.mainModel, this.stateColorMapping);
    this.graphShowing = true;
  }

  public Scene setupScene() {
    return mainView.setupScene();
  }

  public View getMainView() {
    return mainView;
  }
}





