package Controller;

import Model.Model;
import Model.ModelException;
import View.FrontEndCell;
import View.View;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;


/**
 * @author Alex Jimenez
 * @author Arjun Peri
 *
 * The controller class for the simulation. This "hosts" the simulation, creating the backend model and
 * the front end view and mediating communication between the two
 */
public class Controller {

  private static final String ENGLISH = "English";
  private static final String SPANISH = "Spanish";
  private static final String FAKE_LANGUAGE = "FakeLanguage";
  private static final String DOT_CSV = ".csv";
  private static final String TYPE = "Type";
  private static final String MODEL = "Model";
  private static final String DOT = ".";
  private static final String SLASH = "/";
  private static final String DEFAULT = "Default";
  private static final String STATES = "States";
  private static final String INVALID_MODEL_TYPE = "Invalid Model Type";
  private static final String WRITING_ERROR = "Error Writing to Properties File";
  private static final String TITLE = "Title";
  private static final String FILENAME = "FileName";
  private static final String AUTHOR = "Author";
  private static final String DESCRIPTION = "Description";
  private static final String PROPERTIES = "Properties";
  private static final String DOT_PROPERTIES = ".properties";
  private static final String CONTROLLER_ERROR = "Controller Error";
  private static final String INVALID = "Invalid File Name";
  private static final String COLOR_MAPPING_ERROR = "ColorMappingError";
  private static final String GRAPH_ERROR = "GraphError";
  private static final int ONE_HUNDRED = 100;

  private static final String RESOURCES = "Resources/";
  public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
  private ResourceBundle projectTextResources;
  private final Map<Integer, String> stateColorMapping = new HashMap<>();
  private List<List<String>> frontEndCellColors;
  private String currentFileName;
  private GraphController graphController;

  private final Stage stage;
  private Model mainModel;
  private final View mainView;
  private boolean simIsSet = false;
  private boolean graphShowing = false;
  private String modelType;
  private Properties defaultFile;
  private Properties currentPropertyFile;


  /**
   * Constructor for the controller class. Initializes a controller object and in doing so
   * creates a view and launches the simulation splash screen.
   *
   * @param stage The stage upon which the main simulation grid is created.
   */
  public Controller(Stage stage) {
    projectTextResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + ENGLISH);
    this.mainView = new View(projectTextResources);
    initializeSplashMenu();
    this.stage = stage;
  }

  /**
   * Initializes the user inputs for the splash screen that decides which simulation type is ran.
   * It then passes these inputs to the view which creates the splash screen view
   */
  public void initializeSplashMenu() {
    if (this.graphShowing) {
      this.graphController.closeGraph();
      this.graphShowing = false;
    }
    TextField inputText = new TextField();
    EventHandler<ActionEvent> simulationInputEvent = e -> {
      currentFileName = inputText.getText();
      try {
        currentPropertyFile = getPropertyFile(currentFileName);
        displayInfo(currentFileName);
      } catch (ControllerException c) {
        showError(c.getMessage());
      }
    };
    this.mainView.createInputTextField(inputText, simulationInputEvent,
        event -> changeTextResourceFile(ENGLISH),
        event -> changeTextResourceFile(SPANISH), event -> changeTextResourceFile(FAKE_LANGUAGE));
    this.mainView.setHomeButton(homeButtonEvent -> initializeSplashMenu());
  }

  /**
   * Calls the view to create the page after the simulation splash screen. This view displays the information for the
   * chosen simulation and has buttons to go home or to start the simulation grid view
   *
   * @param fileName The file name of the simulation being ran that was input by the user in the
   *                 inputText text field of the initializeSplashMenu function
   */
  public void displayInfo(String fileName) {
    try {
      this.mainView
          .displaySimulationInfo(fileName, currentPropertyFile, event -> initializeSimulation());
    } catch (ControllerException e) {
      showError(e.getMessage());
    }
  }

  /**
   * This function is called by a button created in the view called in the displayInfo scene. It mediates
   * between the backend model and the front end view to diplay the simulation grid.
   */
  public void initializeSimulation() {
    mainView.clearCenterGroup();
    mainView.clearTopMenuGroup();
    frontEndCellColors = new ArrayList<>();
    stateColorMapping.clear();
    try {
      initializeModel();
      this.frontEndCellColors = updateFrontEndCellColors();
      mainView.initializeFrontEndCells(mainModel.getNumberOfRows(),
          mainModel.getNumberOfColumns(), frontEndCellColors);
      simIsSet = true;
      addCellEventHandlers();
      initializeSimulationMenu();
    } catch (Exception e) {
      showError(e.getMessage());
      initializeSplashMenu();
    }
  }

  /**
   * Called in the initializeSimulation method, this activates the backend model with the proper simulation
   * type. This model's information is then used to display the front end grid view.
   */
  private void initializeModel() {
    try {
      modelType = currentPropertyFile.getProperty(TYPE);
      Class<?> cl = Class.forName(MODEL + DOT + modelType + MODEL);
      this.mainModel = (Model) cl.getConstructor(String.class, String.class)
          .newInstance(currentPropertyFile.getOrDefault(FILENAME, currentFileName + DOT_CSV),
              modelType);
      defaultFile = getPropertyFile(modelType + DEFAULT);
      String defaultStates = defaultFile.getProperty(STATES);
      mainModel
          .initializeAllStates((String) currentPropertyFile.getOrDefault(STATES, defaultStates));
    } catch (ClassNotFoundException e) {
      throw new ControllerException(INVALID_MODEL_TYPE);
    } catch (InvocationTargetException e) {
      throw new ControllerException(e.getTargetException().getMessage());
    } catch (Exception e) {
      throw new ControllerException(e.getMessage());
    }
  }

  public void getSaveInputs() {
    mainModel.setPaused();
    TextField titleInput = new TextField();
    TextField authorInput = new TextField();
    TextField descriptionInput = new TextField();

    Dialog saveBox = this.mainView.showSaveInputs(titleInput, authorInput, descriptionInput);
    Optional<String> saveBoxResult = saveBox.showAndWait();
    Properties savedProperties = new Properties();

    savedProperties.setProperty(TITLE, titleInput.getText());
    savedProperties.setProperty(AUTHOR, authorInput.getText());
    savedProperties.setProperty(DESCRIPTION, descriptionInput.getText());
    savedProperties.setProperty(TYPE, modelType);
    if (saveBoxResult.isPresent()) {
      writeToPropertyFile(savedProperties);
      mainModel.writeToCSV(savedProperties.getProperty(TITLE) + DOT_CSV);
    }
    mainModel.switchPause();
  }

  public void writeToPropertyFile(Properties propertyFile) {
    try {
      FileWriter writer = new FileWriter(
          PROPERTIES + SLASH + propertyFile.getProperty(TITLE) + DOT_PROPERTIES);
      propertyFile.store(writer, null);
    } catch (IOException ex) {
      throw new ModelException(WRITING_ERROR);
    }
    initializeSplashMenu();
  }

  /**
   * Corresponds to a step in the backend model and graph if it is visible. After updating the backend model,
   * the front end view is updated using the new data
   */
  public void gameStep() {
    if (simIsSet) {
      if (mainModel.modelStep() && graphShowing) {
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

  public void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(CONTROLLER_ERROR);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Adds a mapping of the input state and its corresponding color in the property file to the
   * color map.
   *
   * @param state The cell state being considered
   */
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
          .load(Controller.class.getClassLoader().getResourceAsStream(fileName + DOT_PROPERTIES));
    } catch (Exception e) {
      throw new ControllerException(INVALID, e);
    }
    return propertyFile;
  }

  public void changeTextResourceFile(String language) {
    ResourceBundle newBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    this.projectTextResources = newBundle;
    this.mainView.updateResourceBundle(newBundle);
    initializeSplashMenu();
  }

  /**
   * Adds eventHandlers to the view cells that cause for the cells to cycle colors when clicked on
   */
  public void addCellEventHandlers() {
    List<List<FrontEndCell>> frontEndCells = this.mainView.getFrontEndCellGrid();
    for (List<FrontEndCell> cellRow : frontEndCells) {
      for (FrontEndCell cell : cellRow) {
        cell.setOnMouseClicked(this::changeClickedCellState);
      }
    }
  }

  /**
   * Calls to the model to cycle the state of the cell that was clicked on
   *
   * @param event Event that indicated what cell was clicked on
   */
  public void changeClickedCellState(Event event) {
    EventTarget clickedEvent = event.getTarget();
    FrontEndCell clickedCell = (FrontEndCell) clickedEvent;
    int clickedCellRow = clickedCell.getRow();
    int clickedCellColumn = clickedCell.getColumn();
    mainModel.cycleState(clickedCellRow, clickedCellColumn);
  }

  /**
   * Initializes the simulation menu that is displayed while the simulation grid is being displayed.
   * Creates events that handle what occurs when the buttons are clicked on then delegates to the view
   * to create and display the buttons.
   */
  public void initializeSimulationMenu() {
    EventHandler<ActionEvent> saveEvent = e -> getSaveInputs();
    EventHandler<ActionEvent> colorEvent = e -> changeColorsPopUp();
    EventHandler<ActionEvent> graphEvent = e -> createGraph();
    EventHandler<ActionEvent> pauseEvent = e -> mainModel.switchPause();
    EventHandler<ActionEvent> stepEvent = e -> {
      mainModel.step();
      if (graphController != null) {
        graphController.updateGraph();
      }
    };
    ChangeListener<Number> speedEvent = (ov, old_val, new_val) -> {
      this.mainModel.setSimulationSpeed(new_val.doubleValue() / ONE_HUNDRED);
    };
    this.mainView.initializeSimulationMenu(saveEvent, colorEvent, graphEvent, pauseEvent, stepEvent,
        speedEvent);
  }


  /**
   * Creates the text pop up to allow users to change the state color mappings that they input.
   */
  public void changeColorsPopUp() {
    mainModel.setPaused();
    TextField colorInput = new TextField();
    TextField stateInput = new TextField();
    Dialog colorBox = this.mainView.changeColorsPopUp(stateInput, colorInput);
    Optional<ButtonType> colorBoxResult = colorBox.showAndWait();
    if (colorBoxResult.isPresent()) {
      updateColorStateMapping(stateInput.getText(), colorInput.getText());
    }
    mainModel.switchPause();
  }

  /**
   * Updates the color map of the state specified to map to the new color
   *
   * @param state The state of the mapping to be changed
   * @param color The color of the mapping to be changed to
   */
  public void updateColorStateMapping(String state, String color) {
    try {
      int stateInt = Integer.parseInt(state);
      if (!this.stateColorMapping.containsKey(stateInt)) {
        throw new IllegalArgumentException();
      }
      Paint.valueOf(color);
      this.stateColorMapping.put(stateInt, color);
    } catch (Exception e) {
      showError(this.projectTextResources.getString(COLOR_MAPPING_ERROR));
    }
  }

  /**
   * Displays the state concentration graph if not already showing
   */
  public void createGraph() {
    if (this.graphShowing) {
      showError(this.projectTextResources.getString(GRAPH_ERROR));
      return;
    }
    this.graphController = new GraphController(this.mainModel, this.stateColorMapping, this.stage,
        this.projectTextResources);
    this.graphShowing = true;
  }

  public Scene setupScene() {
    return mainView.setupScene();
  }

  public View getMainView() {
    return mainView;
  }
}





