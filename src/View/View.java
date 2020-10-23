package View;

import Controller.ControllerException;
import cellsociety.SimulationRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


/**
 * @author Alex Jimenez
 * @author Arjun Peri
 */
public class View {

  private static final String COLOR_BUTTON = "colorsButton";
  private static final String THEME = "Theme";
  private static final String RESOURCES = "Resources/";
  private static final String DUKE = "Duke";
  private static final String LIGHT = "Light";
  private static final String DARK = "Dark";
  private static final String DOT_CSS = ".css";
  private static final String START = "Start ";
  private static final String DISPLAY_BOX_ID = "displayBox";
  private static final String SLIDER = "slider";
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
  private static final String PAUSE = "Pause";
  private static final String STEP = "Step";
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

  private static final int SLIDER_MAX = 100;
  private static final int SLIDER_MIN = 0;
  private static final int SLIDER_INITIAL_VALUE = 50;
  private static final int SLIDER_TICK_VALUE = 1;
  private static final int SLIDER_BLOCK = 10;

  private Scene scene;
  private final BorderPane root;
  private final Group topGroup;
  private final Group centerGroup;
  private List<List<FrontEndCell>> frontEndCellGrid;
  private List<List<String>> frontEndCellColors;
  private Button homeButton;
  private ResourceBundle viewTextResources;


  /**
   * Constructor. Sets up a BorderPane root so that the interface can be organized into sections on a screen.
   * @param resources
   */
  public View(ResourceBundle resources) {
    topGroup = new Group();
    centerGroup = new Group();
    root = new BorderPane();
    root.setCenter(centerGroup);
    root.setTop(topGroup);
    this.viewTextResources = resources;
  }

  /**
   * Sets up the JavaFX scene.
   * @return
   */
  public Scene setupScene() {
    this.scene = new Scene(root, SimulationRunner.SCENE_WIDTH, SimulationRunner.SCENE_HEIGHT,
        SimulationRunner.BACKGROUND);
    updateCSS(RESOURCES + DUKE + DOT_CSS);
    return this.scene;
  }

  /**
   * Updates front end cells with the passed in front end cell colors at every iteration of the game loop.
   * @param frontEndCellColors
   */
  public void viewStep(List<List<String>> frontEndCellColors) {
    updateFrontEndCells(frontEndCellColors);
  }

  public void initializeFrontEndCells(int numberOfRows, int numberOfColumns,
      List<List<String>> frontEndCellColors) {
    this.frontEndCellColors = frontEndCellColors;
    this.frontEndCellGrid = new ArrayList<>();
    double xOffset = SimulationRunner.SCENE_WIDTH / (double) numberOfRows;
    double yOffset = (SimulationRunner.SCENE_HEIGHT) / (double) numberOfColumns;
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

  
  /**
   * Creates the input text box to be displayed at the start of the program and adds to the center of the BorderPane.
   * @param inputText
   * @param inputTextEvent
   * @param englishEvent
   * @param spanishEvent
   * @param languageEvent
   */
  public void createInputTextField(TextField inputText, EventHandler<ActionEvent> inputTextEvent,
      EventHandler<ActionEvent> englishEvent, EventHandler<ActionEvent> spanishEvent,
      EventHandler<ActionEvent> languageEvent) {
    clearCenterGroup();
    clearTopMenuGroup();
    VBox inputTextBox = new VBox();
    inputText.setId(INPUT_TEXT_BOX);
    inputText.setOnAction(inputTextEvent);
    Label inputLabel = new Label(viewTextResources.getString(INPUT + LABEL + TEXT));
    inputTextBox.getChildren().add(inputLabel);
    inputTextBox.getChildren().add(inputText);
    inputTextBox.getChildren().add(makeButton(ENGLISH, englishEvent));
    Button spanishButton = makeButton(SPANISH, spanishEvent);
    spanishButton.setId(SPANISH_LOWER);
    inputTextBox.getChildren().add(spanishButton);
    inputTextBox.getChildren().add(makeButton(FAKE_LANGUAGE, languageEvent));
    this.centerGroup.getChildren().add(inputTextBox);
  }

  /**
   * Displays the information about a simulation as read in from the corresponding properties file along with the start button and home button.
   * @param fileName
   * @param simulationPropertyFile
   * @param startButtonEvent
   */
  public void displaySimulationInfo(String fileName, Properties simulationPropertyFile,
      EventHandler<ActionEvent> startButtonEvent) {
    clearCenterGroup();
    HBox simulationInfoBox = new HBox();
    Button startButton = makeButton(START + fileName, startButtonEvent);
    startButton.setId(fileName);
    updateHomeButton();
    simulationInfoBox.getChildren().add(homeButton);
    simulationInfoBox.getChildren().add(startButton);
    this.topGroup.getChildren().add(simulationInfoBox);
    try {
      Text startupText = new Text();
      startupText.getStyleClass().add(DISPLAY_BOX_ID);
      String type = (String) simulationPropertyFile
          .getOrDefault(TYPE, NO + SPACE + TYPE + SPACE + SPECIFIED);
      String title = (String) simulationPropertyFile
          .getOrDefault(TITLE, NO + SPACE + TITLE + SPACE + SPECIFIED);
      String author = (String) simulationPropertyFile
          .getOrDefault(AUTHOR, NO + SPACE + AUTHOR + SPACE + SPECIFIED);
      String description = (String) simulationPropertyFile
          .getOrDefault(DESCRIPTION, NO + SPACE + DESCRIPTION + SPACE + SPECIFIED);
      startupText.setText(type + NEWLINE + title + NEWLINE + author + NEWLINE + description);
      this.centerGroup.getChildren().add(startupText);
    } catch (ControllerException e) {
      showError(e.getMessage());
    }
  }

  /**
   * Displays the appropriate buttons and sliders that exist while the simultion is running.
   * @param saveEvent
   * @param changeColorEvent
   * @param graphEvent
   * @param pauseEvent
   * @param stepEvent
   * @param sliderEvent
   */
  public void initializeSimulationMenu(EventHandler<ActionEvent> saveEvent,
      EventHandler<ActionEvent> changeColorEvent,
      EventHandler<ActionEvent> graphEvent, EventHandler<ActionEvent> pauseEvent,
      EventHandler<ActionEvent> stepEvent, ChangeListener<Number> sliderEvent) {
    HBox topMenuBox = new HBox();
    Button saveButton = makeButton(viewTextResources.getString(SAVE + BUTTON + TEXT), saveEvent);
    Button changeColorsButton = makeButton(viewTextResources.getString(COLORS + BUTTON + TEXT),
        changeColorEvent);
    Button showGraphButton = makeButton(viewTextResources.getString(GRAPH + BUTTON + TEXT),
        graphEvent);
    Button pauseButton = makeButton(viewTextResources.getString(PAUSE + BUTTON + TEXT), pauseEvent);
    Button stepButton = makeButton(viewTextResources.getString(STEP + BUTTON + TEXT), stepEvent);
    changeColorsButton.setId(COLOR_BUTTON);
    updateHomeButton();
    topMenuBox.getChildren().add(homeButton);
    topMenuBox.getChildren().add(saveButton);
    topMenuBox.getChildren().add(pauseButton);
    topMenuBox.getChildren().add(stepButton);
    topMenuBox.getChildren().add(getThemeComboBox());
    topMenuBox.getChildren().add(changeColorsButton);
    topMenuBox.getChildren().add(showGraphButton);
    topMenuBox.getChildren().add(createSpeedSlider(sliderEvent));
    this.topGroup.getChildren().add(topMenuBox);
  }

  private Slider createSpeedSlider(ChangeListener<Number> sliderEvent) {
    Slider speedSlider = new Slider(SLIDER_MIN, SLIDER_MAX, SLIDER_INITIAL_VALUE);
    speedSlider.setMinorTickCount(SLIDER_MAX);
    speedSlider.setMajorTickUnit(SLIDER_TICK_VALUE);
    speedSlider.setMinorTickCount(SLIDER_TICK_VALUE);
    speedSlider.setBlockIncrement(SLIDER_BLOCK);
    speedSlider.valueProperty().addListener(sliderEvent);
    speedSlider.setId(SLIDER);
    return speedSlider;
  }

  public Dialog changeColorsPopUp(TextField stateInput, TextField colorInput) {
    Dialog colorBox = new TextInputDialog();
    stateInput.setId(STATE_LOWER + INPUT);
    colorInput.setId(COLOR_LOWER + INPUT);
    GridPane grid = new GridPane();
    colorInput.setPromptText(viewTextResources.getString(COLOR + INPUT + PROMPT));
    stateInput.setPromptText(viewTextResources.getString(STATE + INPUT + PROMPT));
    GridPane.setConstraints(stateInput, ZERO, ZERO);
    grid.getChildren().add(stateInput);
    GridPane.setConstraints(colorInput, ZERO, ONE);
    grid.getChildren().add(colorInput);
    colorBox.getDialogPane().setContent(grid);
    return colorBox;
  }

  /**
   * Displays a pop dialog box that enables a user to input their specifications for the file they are saving.
   * @param titleInput
   * @param authorInput
   * @param descriptionInput
   * @return
   */
  public Dialog showSaveInputs(TextField titleInput, TextField authorInput,
      TextField descriptionInput) {
    Dialog saveBox = new TextInputDialog();
    saveBox.getDialogPane().lookupButton(ButtonType.OK).setId(SAVE + OK);
    titleInput.setId(TITLE_LOWER + INPUT);
    authorInput.setId(AUTHOR_LOWER + INPUT);
    descriptionInput.setId(DESCRIPTION_LOWER + INPUT);
    titleInput.setPromptText(viewTextResources.getString(TITLE + INPUT + TEXT));
    authorInput.setPromptText(viewTextResources.getString(AUTHOR + INPUT + TEXT));
    descriptionInput.setPromptText(viewTextResources.getString(DESCRIPTION + INPUT + TEXT));
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

  /**
   * Generalized method to allow for buttons to be made given a property that is the button ID and text display, as well as an Event Handler tha  specifies
   * the action event of the button press.
   * @param property
   * @param handler
   * @return
   */
  private Button makeButton(String property, EventHandler<ActionEvent> handler) {
    Button result = new Button();
    result.setId(property);
    result.setText(property);
    result.setOnAction(handler);
    return result;
  }

  
  /**
   * Makes the home button based on information read in from the resources package.
   * @param inputTextEvent
   * @return
   */
  public Button setHomeButton(EventHandler<ActionEvent> inputTextEvent) {
    this.homeButton = makeButton(viewTextResources.getString(HOME + BUTTON + TEXT), inputTextEvent);
    this.homeButton.setId(HOME);
    return this.homeButton;
  }

  public void updateHomeButton() {
    this.homeButton.setText(viewTextResources.getString(HOME + BUTTON + TEXT));
  }

  public ComboBox getThemeComboBox() {
    ComboBox themeComboBox = new ComboBox();
    themeComboBox.setPromptText(viewTextResources.getString(THEME));
    themeComboBox.getItems().addAll(
        DUKE,
        LIGHT,
        DARK
    );
    themeComboBox.valueProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue ov, String oldString, String newString) {
        updateCSS(RESOURCES + newString + DOT_CSS);
      }
    });
    return themeComboBox;
  }

  public void updateCSS(String cssFile) {
    this.scene.getStylesheets().clear();
    this.scene.getStylesheets().add(cssFile);
  }

  private void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(viewTextResources.getString(CONTROLLER + ALERT + TEXT));
    alert.setContentText(message);
    alert.showAndWait();
  }

  public void updateResourceBundle(ResourceBundle resources) {
    this.viewTextResources = resources;
  }

  public void clearTopMenuGroup() {
    this.topGroup.getChildren().clear();
  }

  public void clearCenterGroup() {
    this.centerGroup.getChildren().clear();
  }

  public List<List<FrontEndCell>> getFrontEndCellGrid() {
    return frontEndCellGrid;
  }
}


