package Controller;

import Model.Model;
import View.View;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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


  public Controller() {
    this.mainView = new View();
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "English");
    initializeButtonMenu();
  }

  //on button press
  public void initializeSimulation(String fileName, String modelType, String fileOut) {
    this.mainModel = new Model(fileName, modelType, fileOut);
    mainView.initializeFrontEndCells(modelType, mainModel.getNumberOfRows(),
        mainModel.getNumberOfColumns(), mainModel.getGridOfCells());
    simIsSet = true;
  }

  public void gameStep() {
    if (simIsSet) {
      mainModel.modelStep();
      mainView.viewStep(mainModel.getGridOfCells());
    }
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
        "ConwayStatesPulsar", "ConwayStatesBlinker", "ConwayStatesBLock", "ConwayStatesToad",
        "ConwayStatesBeacon"));
    comboBoxGameOfLife
        .setOnAction(event -> transition("GameOfLife", comboBoxGameOfLife.getValue().toString()));
    comboBoxGameOfLife.setPromptText("GameOfLife");
    ComboBox comboBoxPercolation = new ComboBox(
        FXCollections.observableArrayList("PercolationExample"));
    comboBoxPercolation
        .setOnAction(event -> transition("Percolation", comboBoxPercolation.getValue().toString()));
    comboBoxPercolation.setPromptText("Percolation");
    ComboBox comboBoxRPS = new ComboBox(FXCollections.observableArrayList("RPS100"));
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


  public void handleKeyInput(KeyCode code) {
    switch (code) {
      case P -> mainModel.switchPause();
      case S -> mainModel.step();
    }
  }
}
