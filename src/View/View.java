package View;

import Controller.ControllerException;
import cellsociety.Simulation;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class View {

  private final BorderPane root;
  private final Group topGroup;
  private final Group centerGroup;
  private List<List<FrontEndCell>> frontEndCellGrid;
  private List<List<String>> frontEndCellColors;
  private TextField inputText;
  private Button homeButton;

  public View() {
    topGroup = new Group();
    centerGroup = new Group();
    root = new BorderPane();
    root.setCenter(centerGroup);
    root.setTop(topGroup);
  }

  public Scene setupScene() {
    return new Scene(root, Simulation.SCENE_WIDTH, Simulation.SCENE_HEIGHT, Simulation.BACKGROUND);
  }

  public void viewStep(List<List<String>> frontEndCellColors) {
    updateFrontEndCells(frontEndCellColors);
  }

  public void initializeFrontEndCells(int numberOfRows, int numberOfColumns,
      List<List<String>> frontEndCellColors) {
    this.frontEndCellColors = frontEndCellColors;
    this.frontEndCellGrid = new ArrayList<>();
    double xOffset = Simulation.SCENE_WIDTH / (double) numberOfRows;
    double yOffset = Simulation.SCENE_HEIGHT / (double) numberOfColumns;
    double x;
    double y = 0;
    for (int row = 0; row < numberOfRows; row++) {
      x = 0;
      List<FrontEndCell> frontEndCellRow = new ArrayList<>();
      for (int column = 0; column < numberOfColumns; column++) {
        addFrontEndCellToScene(row, column, x, y, xOffset, yOffset, frontEndCellRow);
        x += xOffset;
      }
      frontEndCellGrid.add(frontEndCellRow);
      y += yOffset;
    }
  }

  private void addFrontEndCellToScene(int row, int column, double x, double y, double xOffset,
      double yOffset, List<FrontEndCell> frontEndCellRow) {
    String stateString = this.frontEndCellColors.get(row).get(column);
    FrontEndCell currentFrontEndCell = new FrontEndCell(stateString, x, y, xOffset, yOffset, row,
        column);
    frontEndCellRow.add(currentFrontEndCell);
    currentFrontEndCell.setId("cell" + row + column);
    centerGroup.getChildren().add(currentFrontEndCell);
  }

  private void updateFrontEndCells(List<List<String>> frontEndCellColors) {
    this.frontEndCellColors = frontEndCellColors;
    for (int row = 0; row < this.frontEndCellColors.size(); row++) {
      for (int column = 0; column < this.frontEndCellColors.get(0).size(); column++) {
        frontEndCellGrid.get(row).get(column)
            .updateCellColor(this.frontEndCellColors.get(row).get(column));
      }
    }
  }

  public void createInputTextField(TextField inputText, EventHandler<ActionEvent> inputTextEvent){
    clearCenterGroup();
    clearTopMenuGroup();
    VBox inputTextBox = new VBox();
    this.inputText = inputText;
    this.inputText.setId("inputTextBox");
    this.inputText.setOnAction(inputTextEvent);
    Label inputLabel = new Label("Enter Simulation Name and Press Enter");
    inputTextBox.getChildren().add(inputLabel);
    inputTextBox.getChildren().add(inputText);
    this.centerGroup.getChildren().add(inputTextBox);
  }

  public void displaySimulationInfo(String fileName, Properties simulationPropertyFile, EventHandler<ActionEvent> startButtonEvent){
    clearCenterGroup();
    HBox simulationInfoBox = new HBox();
    Button startButton = makeButton(fileName, startButtonEvent);
    simulationInfoBox.getChildren().add(homeButton);
    simulationInfoBox.getChildren().add(startButton);
    this.topGroup.getChildren().add(simulationInfoBox);
    try {
      Text startupText = new Text();
      String type = (String) simulationPropertyFile.getOrDefault("Type", "No Type Specified");
      String title = (String) simulationPropertyFile.getOrDefault("Title", "No Title specified");
      String author = (String) simulationPropertyFile.getOrDefault("Author", "No Author Specified");
      String description = (String) simulationPropertyFile.getOrDefault("Description", "No Description Specified");
      startupText.setText(type + "\n" + title + "\n" + author + "\n" + description);
      this.centerGroup.getChildren().add(startupText);
    } catch (ControllerException e) {
      showError(e.getMessage());
    }
  }

  public void initializeSimulationMenu(EventHandler<ActionEvent> saveEvent,EventHandler<ActionEvent> changeColorEvent,EventHandler<ActionEvent> graphEvent){
    HBox topMenuBox = new HBox();
    Button saveButton = makeButton("Save", saveEvent);
    Button changeColorsButton = makeButton("changeColors", changeColorEvent);
    Button showGraphButton = makeButton("State Concentration Graph", graphEvent);
    topMenuBox.getChildren().add(homeButton);
    topMenuBox.getChildren().add(saveButton);
    topMenuBox.getChildren().add(changeColorsButton);
    topMenuBox.getChildren().add(showGraphButton);
    this.topGroup.getChildren().add(topMenuBox);
  }

  public Dialog changeColorsPopUp(TextField stateInput, TextField colorInput){
    Dialog colorBox = new TextInputDialog();
    colorInput.setId("colorInput");
    colorInput.setId("stateInput");
    GridPane grid = new GridPane();
    colorInput.setPromptText("New Color");
    stateInput.setPromptText("State to Change");
    GridPane.setConstraints(stateInput, 0, 0);
    grid.getChildren().add(stateInput);
    GridPane.setConstraints(colorInput, 0, 1);
    grid.getChildren().add(colorInput);
    colorBox.getDialogPane().setContent(grid);
    return colorBox;
  }

  public Dialog showSaveInputs(TextField titleInput, TextField authorInput, TextField descriptionInput){
    Dialog saveBox = new TextInputDialog();
    saveBox.getDialogPane().lookupButton(ButtonType.OK).setId("SaveOK");
    titleInput.setId("titleInput");
    authorInput.setId("authorInput");
    descriptionInput.setId("descriptionInput");
    titleInput.setPromptText("Title: ");
    authorInput.setPromptText("Author: ");
    descriptionInput.setPromptText("Description: ");
    GridPane grid = new GridPane();
    GridPane.setConstraints(titleInput, 0, 0);
    grid.getChildren().add(titleInput);
    GridPane.setConstraints(authorInput, 0, 1);
    grid.getChildren().add(authorInput);
    GridPane.setConstraints(descriptionInput, 0, 2);
    grid.getChildren().add(descriptionInput);
    saveBox.getDialogPane().setContent(grid);
    return saveBox;
  }

  public void initializeButtonMenu(){}
  public void addTopBarMenu(){}
  public void makeButton(){}

  private Button makeButton(String property, EventHandler<ActionEvent> handler) {
    Button result = new Button();
    result.setId(property);
    result.setText(property);
    result.setOnAction(handler);
    return result;
  }

  public Button setHomeButton(EventHandler<ActionEvent> inputTextEvent){
    this.homeButton = makeButton("Home", inputTextEvent);
    return this.homeButton;
  }

  private void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Controller Error");
    alert.setContentText(message);
    alert.showAndWait();
  }


  public void clearTopMenuGroup(){this.topGroup.getChildren().clear();}
  public void clearCenterGroup(){this.centerGroup.getChildren().clear();}
  public List<List<FrontEndCell>> getFrontEndCellGrid() {
    return frontEndCellGrid;
  }
  public BorderPane getRoot() {
    return this.root;
  }
  public Group getCenterGroup() {
    return this.centerGroup;
  }
  public Group getTopGroup() {
    return this.topGroup;
  }
}


