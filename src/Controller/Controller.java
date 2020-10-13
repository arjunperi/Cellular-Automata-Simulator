package Controller;

import Model.Model;
import Model.ModelException;
import View.View;
import View.FrontEndCell;


import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;

import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.commons.lang3.concurrent.ConcurrentRuntimeException;

import javax.swing.*;


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

  private Button homeButton = makeButton("Home", event -> initializeButtonMenu());
  private TextField inputText;
  private Map<String, String> saveList = new HashMap<>();

  public Controller() {
    this.mainView = new View();
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "English");
    initializeButtonMenu();
  }

  public void initializeSimulation(String fileName, String modelType, String fileOut) {
    frontEndCellColors = new ArrayList<>();
    stateColorMapping.clear();
    try {
      Class<?> cl = Class.forName("Model." + modelType + "Model");
      this.mainModel = (Model) cl.getConstructor(String.class,String.class,String.class).newInstance(fileName, modelType, fileOut);
      this.frontEndCellColors = updateFrontEndCellColors();
      mainView.initializeFrontEndCells(mainModel.getNumberOfRows(),
          mainModel.getNumberOfColumns(), frontEndCellColors);
      simIsSet = true;
      addCellEventHandlers();
      //mainView.getRoot().setTop(homeButton);
      VBox result = new VBox();
      Button saveButton = makeButton("Save", event -> getSaveInputs());
      result.getChildren().add(homeButton);
      result.getChildren().add(saveButton);
      //result.getChildren().add(inputText);
      mainView.getRoot().setTop(result);
      //mainView.getRoot().setBottom();

    }
    catch (NoSuchMethodException | ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException| ModelException e) {
        showError("Invalid Model Type");
    }
  }

  private void getSaveInputs(){
    JTextField Title = new JTextField();
    JTextField Author = new JTextField();
    JTextField Description = new JTextField();
    Object[] message = {
            "Title:", Title,
            "Author:", Author,
            "Description", Description
    };
    JOptionPane.showConfirmDialog(null, message, "Enter Save File Details", JOptionPane.OK_CANCEL_OPTION);
    saveList.put("Title", Title.getText());
    saveList.put("Author", Author.getText());
    saveList.put("Description", Description.getText());
    writeToPropertyFile();
  }

  private void writeToPropertyFile(){
    try (OutputStream output = new FileOutputStream("Properties/" + saveList.get("Title") + ".properties")) {
      Properties prop = new Properties();
      prop.setProperty("Title", saveList.get("Title"));
      prop.setProperty("Author", saveList.get("Author"));
      prop.setProperty("Description", saveList.get("Description"));
      prop.store(output, null);
      System.out.println(saveList);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    initializeButtonMenu();
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
    VBox result = new VBox();
    inputText = new TextField();
    inputText.setId("inputTextBox");
    EventHandler<ActionEvent> event = e -> {
      String fileChosen = inputText.getText();
      try{
        Properties propertyFile = getPropertyFile(fileChosen);
        displayInfo(propertyFile.getProperty("Type"), fileChosen);
      }
      catch (ControllerException c){
        showError(c.getMessage());
      }
    };
    inputText.setOnAction(event);
    Label inputLabel = new Label("Enter Simulation Name and Press Enter");
    result.getChildren().add(inputLabel);
    result.getChildren().add(inputText);
    mainView.getRoot().setCenter(result);
  }



  public void displayInfo(String token, String fileName){
//    homeButton = makeButton("Home", event -> initializeButtonMenu());
    mainView.getRoot().getChildren().clear();
    HBox result = new HBox();
    Button startButton = makeButton(fileName, event -> startSimulation(token, fileName));
    result.getChildren().add(homeButton);
    result.getChildren().add(startButton);
    mainView.getRoot().setTop(result);
    try{
        Text startupText = new Text();
        Properties propertyFile  = getPropertyFile(fileName);
        String type = propertyFile.getProperty("Type");
        String title = propertyFile.getProperty("Title");
        String author = propertyFile.getProperty("Author");
        String description = propertyFile.getProperty("Description");
        startupText.setText(type+ "\n" + title + "\n" + author + "\n" + description);
        mainView.getRoot().setCenter(startupText);
    }
    catch (ControllerException e){
      showError(e.getMessage());
    }
  }

  private void showError(String message){
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Controller Error");
    alert.setContentText(message);
    alert.showAndWait();
  }


  private Button makeButton (String property, EventHandler<ActionEvent> handler) {
    Button result = new Button();
    result.setId(property);
    result.setText(property);
    result.setOnAction(handler);
    result.setId(property);
    return result;
  }

  public void startSimulation(String token, String fileName) {
    currentFileName = fileName;
    initializeSimulation(fileName + ".csv", token, fileName + "Out.csv");
  }

  public void initializeColorMapping(int state) {
    Properties propertyFile = getPropertyFile(currentFileName);
    String color = propertyFile.getProperty(String.valueOf(state));
    if (!stateColorMapping.containsKey(state)) {
      stateColorMapping.put(state, color);
    }
  }


  public Properties getPropertyFile(String fileName) {
    Properties propertyFile = new Properties();
    try {
      propertyFile.load(Controller.class.getClassLoader().getResourceAsStream(fileName + ".properties"));
    } catch (Exception e) {
      throw new ControllerException("Invalid File Name", e);
    }
    return propertyFile;
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
    EventTarget clickedEvent = event.getTarget();
    FrontEndCell clickedCell = (FrontEndCell) clickedEvent;
    int clickedCellRow = clickedCell.getRow();
    int clickedCellColumn = clickedCell.getColumn();
    mainModel.getGridOfCells().getCell(clickedCellRow, clickedCellColumn).cycleNextState();
  }
}





