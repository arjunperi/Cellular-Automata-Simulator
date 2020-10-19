package View;

import Controller.ControllerException;
import cellsociety.Simulation;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
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
  private ResourceBundle viewTextResources;

  private static final String CELL = "cell";
  private static final String INPUT_TEXT_BOX = "inputTextBox";
  private static final String ENGLISH = "English";
  private static final String SPANISH = "Spanish";
  private static final String SPANISH_LOWER = "spanish";
  private static final String FAKE_LANGUAGE = "FakeLanguage";
  private static final String TYPE = "Type";
  private static final String HOME = "Home";
  private static final String INPUT = "Input";
  private static final String PROMPT = "Prompt";
  private static final String TEXT = "Text";
  private static final String LABEL = "Label";
  private static final String TITLE = "Title";
  private static final String AUTHOR = "Author";
  private static final String DESCRIPTION = "Description";
  private static final String TITLE_LOWER = "title";
  private static final String AUTHOR_LOWER = "author";
  private static final String DESCRIPTION_LOWER = "description";
  private static final String NO = "No";
  private static final String SPACE = " ";
  private static final String SPECIFIED = "Specified";
  private static final String NEWLINE = "\n";
  private static final String COLORS = "Colors";
  private static final String COLOR_LOWER = "color";
  private static final String COLOR = "Color";
  private static final String BUTTON = "Button";
  private static final String SAVE = "Save";
  private static final int ZERO = 0;
  private static final int ONE = 1;
  private static final int TWO = 2;
  private static final String STATE_LOWER = "state";
  private static final String STATE = "State";
  private static final String CONTROLLER = "Controller";
  private static final String GRAPH = "Graph";
  private static final String OK = "OK";
  private static final String ALERT = "Alert";




  public View(ResourceBundle resources) {
    topGroup = new Group();
    centerGroup = new Group();
    root = new BorderPane();
    root.setCenter(centerGroup);
    root.setTop(topGroup);
    this.viewTextResources = resources;
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
    currentFrontEndCell.setId(CELL + row + column);
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

  public void createInputTextField(TextField inputText, EventHandler<ActionEvent> inputTextEvent,
      EventHandler<ActionEvent> englishEvent,EventHandler<ActionEvent> spanishEvent, EventHandler<ActionEvent> languageEvent){
    clearCenterGroup();
    clearTopMenuGroup();
    VBox inputTextBox = new VBox();
    this.inputText = inputText;
    this.inputText.setId(INPUT_TEXT_BOX);
    this.inputText.setOnAction(inputTextEvent);
    Label inputLabel = new Label(viewTextResources.getString( INPUT+LABEL+TEXT));
    inputTextBox.getChildren().add(inputLabel);
    inputTextBox.getChildren().add(inputText);
    inputTextBox.getChildren().add(makeButton(ENGLISH, englishEvent));
    Button spanishButton = makeButton(SPANISH, spanishEvent);
    spanishButton.setId(SPANISH_LOWER);
    inputTextBox.getChildren().add(spanishButton);
    inputTextBox.getChildren().add(makeButton(FAKE_LANGUAGE, languageEvent));

    this.centerGroup.getChildren().add(inputTextBox);
  }

  public void displaySimulationInfo(String fileName, Properties simulationPropertyFile, EventHandler<ActionEvent> startButtonEvent){
    clearCenterGroup();
    HBox simulationInfoBox = new HBox();
    Button startButton = makeButton(fileName, startButtonEvent);
    updateHomeButton();
    simulationInfoBox.getChildren().add(homeButton);
    simulationInfoBox.getChildren().add(startButton);
    this.topGroup.getChildren().add(simulationInfoBox);
    try {
      Text startupText = new Text();
      String type = (String) simulationPropertyFile.getOrDefault(TYPE, NO + SPACE + TYPE + SPACE + SPECIFIED);
      String title = (String) simulationPropertyFile.getOrDefault(TITLE, NO + SPACE + TITLE + SPACE + SPECIFIED);
      String author = (String) simulationPropertyFile.getOrDefault(AUTHOR, NO + SPACE + AUTHOR + SPACE + SPECIFIED);
      String description = (String) simulationPropertyFile.getOrDefault(DESCRIPTION, NO + SPACE + DESCRIPTION + SPACE + SPECIFIED);
      startupText.setText(type + NEWLINE + title + NEWLINE + author + NEWLINE + description);
      this.centerGroup.getChildren().add(startupText);
    } catch (ControllerException e) {
      showError(e.getMessage());
    }
  }

  public void initializeSimulationMenu(EventHandler<ActionEvent> saveEvent,EventHandler<ActionEvent> changeColorEvent,EventHandler<ActionEvent> graphEvent){
    HBox topMenuBox = new HBox();
    Button saveButton = makeButton(viewTextResources.getString(SAVE + BUTTON + TEXT), saveEvent);
    Button changeColorsButton = makeButton(viewTextResources.getString(COLORS + BUTTON + TEXT), changeColorEvent);
    Button showGraphButton = makeButton(viewTextResources.getString(GRAPH + BUTTON + TEXT), graphEvent);
    updateHomeButton();
    topMenuBox.getChildren().add(homeButton);
    topMenuBox.getChildren().add(saveButton);
    topMenuBox.getChildren().add(changeColorsButton);
    topMenuBox.getChildren().add(showGraphButton);
    this.topGroup.getChildren().add(topMenuBox);
  }

  public Dialog changeColorsPopUp(TextField stateInput, TextField colorInput){
    Dialog colorBox = new TextInputDialog();
    colorInput.setId(COLOR_LOWER + INPUT);
    colorInput.setId(STATE_LOWER + INPUT);
    GridPane grid = new GridPane();
    colorInput.setPromptText(viewTextResources.getString(COLOR+INPUT+PROMPT));
    stateInput.setPromptText(viewTextResources.getString(STATE+INPUT+PROMPT));
    GridPane.setConstraints(stateInput, ZERO, ZERO);
    grid.getChildren().add(stateInput);
    GridPane.setConstraints(colorInput, ZERO, ONE);
    grid.getChildren().add(colorInput);
    colorBox.getDialogPane().setContent(grid);
    return colorBox;
  }

  public Dialog showSaveInputs(TextField titleInput, TextField authorInput, TextField descriptionInput){
    Dialog saveBox = new TextInputDialog();
    saveBox.getDialogPane().lookupButton(ButtonType.OK).setId(SAVE + OK);
    titleInput.setId(TITLE_LOWER + INPUT);
    authorInput.setId(AUTHOR_LOWER + INPUT);
    descriptionInput.setId(DESCRIPTION_LOWER + INPUT);
    titleInput.setPromptText(viewTextResources.getString(TITLE+INPUT+TEXT));
    authorInput.setPromptText(viewTextResources.getString(AUTHOR+INPUT+TEXT));
    descriptionInput.setPromptText(viewTextResources.getString(DESCRIPTION+INPUT+TEXT));
    GridPane grid = new GridPane();
    GridPane.setConstraints(titleInput, ZERO, ZERO);
    grid.getChildren().add(titleInput);
    GridPane.setConstraints(authorInput, ZERO, ONE);
    grid.getChildren().add(authorInput);
    GridPane.setConstraints(descriptionInput, ZERO, TWO);
    grid.getChildren().add(descriptionInput);
    saveBox.getDialogPane().setContent(grid);
    return saveBox;
  }

  private Button makeButton(String property, EventHandler<ActionEvent> handler) {
    Button result = new Button();
    result.setId(property);
    result.setText(property);
    result.setOnAction(handler);
    return result;
  }

  public Button setHomeButton(EventHandler<ActionEvent> inputTextEvent){
    this.homeButton = makeButton(viewTextResources.getString(HOME + BUTTON + TEXT), inputTextEvent);
    this.homeButton.setId(HOME);
    return this.homeButton;
  }

  public void updateHomeButton(){
    this.homeButton.setText(viewTextResources.getString(HOME + BUTTON + TEXT));
  }

  private void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(viewTextResources.getString(CONTROLLER + ALERT + TEXT));
    alert.setContentText(message);
    alert.showAndWait();
  }

  public void updateResourceBundle(ResourceBundle resources){this.viewTextResources = resources;}
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


