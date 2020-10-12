package Controller;

import Model.Model;
import View.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


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
  private String currentFileName;

  private Button homeButton;


  public Controller() {
    this.mainView = new View();
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "English");
    initializeButtonMenu();
  }

  //on button press
  public void initializeSimulation(String fileName, String modelType, String fileOut) {
    frontEndCellColors = new ArrayList<>();
    this.mainModel = new Model(fileName, modelType, fileOut);
    this.frontEndCellColors = updateFrontEndCellColors();
    mainView.initializeFrontEndCells(mainModel.getNumberOfRows(),
        mainModel.getNumberOfColumns(), frontEndCellColors);
    simIsSet = true;
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
    mainView.getRoot().getChildren().clear();
    HBox result = new HBox();
    homeButton = makeButton("Home", event -> goHome());
    result.getChildren().add(homeButton);
    ComboBox comboBoxGameOfLife = new ComboBox(FXCollections.observableArrayList(
            "ConwayStatesPulsar", "ConwayStatesBlinker", "ConwayStatesBlock", "ConwayStatesToad",
            "ConwayStatesBeacon"));
    comboBoxGameOfLife
            .setOnAction(event -> displayInfo("GameOfLife", comboBoxGameOfLife.getValue().toString()));
    comboBoxGameOfLife.setPromptText("GameOfLife");
    ComboBox comboBoxPercolation = new ComboBox(
            FXCollections.observableArrayList("PercolationExample"));
    comboBoxPercolation
            .setOnAction(event -> displayInfo("Percolation", comboBoxPercolation.getValue().toString()));
    comboBoxPercolation.setPromptText("Percolation");
    ComboBox comboBoxRPS = new ComboBox(FXCollections.observableArrayList("RPSExample"));
    comboBoxRPS.setOnAction(event -> displayInfo("RPS", comboBoxRPS.getValue().toString()));
    comboBoxRPS.setPromptText("RPS");
    ComboBox comboBoxSpreadingFire = new ComboBox(
            FXCollections.observableArrayList("SpreadingFire20"));
    comboBoxSpreadingFire.setOnAction(
            event -> displayInfo("SpreadingFire", comboBoxSpreadingFire.getValue().toString()));
    comboBoxSpreadingFire.setPromptText("SpreadingFire");
    result.getChildren().add(comboBoxGameOfLife);
    result.getChildren().add(comboBoxPercolation);
    result.getChildren().add(comboBoxRPS);
    result.getChildren().add(comboBoxSpreadingFire);
    mainView.getRoot().setTop(result);
  }

  private void goHome(){
    initializeButtonMenu();
  }

  public void displayInfo(String token, String fileName){
    mainView.getRoot().getChildren().clear();
    HBox result = new HBox();
    Button startButton = makeButton(fileName, event -> startSimulation(token, fileName));
    startButton.setId(fileName);
    result.getChildren().add(homeButton);
    result.getChildren().add(startButton);
    mainView.getRoot().setTop(result);
    Text startupText = new Text();
    Properties propertyFile  = getPropertyFile(fileName);
    String type = propertyFile.getProperty("Type");
    String title = propertyFile.getProperty("Title");
    String author = propertyFile.getProperty("Author");
    String description = propertyFile.getProperty("Description");
    startupText.setText(type+ "\n" + title + "\n" + author + "\n" + description);
    mainView.getRoot().setCenter(startupText);
  }

  private Button makeButton (String property, EventHandler<ActionEvent> handler) {
    Button result = new Button();
    result.setText(property);
    result.setOnAction(handler);
    result.setId(property);
    return result;
  }

  public void startSimulation(String token, String fileName) {
    currentFileName = fileName;
    try {
      initializeSimulation(fileName + ".csv", token, fileName + "Out.csv");
    } catch (Exception e) {
      throw new UnsupportedOperationException(String.format("Unrecognized command: %s", token));
    }
  }

  public void initializeColorMapping(int state) {
    Properties propertyFile = getPropertyFile(currentFileName);
    String color = propertyFile.getProperty(String.valueOf(state));
    if (!stateColorMapping.containsKey(state)) {
      stateColorMapping.put(state, color);
    }
  }


  private Properties getPropertyFile(String fileName) {
    Properties propertyFile = new Properties();
    try {
      propertyFile.load(Controller.class.getClassLoader().getResourceAsStream(fileName + ".properties"));
    } catch (IOException e) {
      System.out.println("error");
    }
    return propertyFile;
  }


  public void handleKeyInput(KeyCode code) {
    switch (code) {
      case P -> mainModel.switchPause();
      case S -> mainModel.step();
    }
  }
}



