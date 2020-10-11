package Controller;

import Model.Model;
import View.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

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
  private Map<Integer, String> stateColorMapping;
  private List<List<String>> frontEndCellColors;


  public Controller() {
    this.mainView = new View();
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "English");
    initializeButtonMenu();
  }

  //on button press
  public void initializeSimulation(String fileName, String modelType, String fileOut) {
    frontEndCellColors = new ArrayList<>();
    initializeColorMapping();
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
        colorRow.add(stateColorMapping.get(mainModel.getCellState(row,column)));
      }
      frontEndCellColors.add(colorRow);
    }
    return frontEndCellColors;
  }

  public void initializeColorMapping() {
    Map<Integer, String> colorMap = Map.of(
        0, "WHITE",
        1, "BLACK");
    this.stateColorMapping = colorMap;
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
