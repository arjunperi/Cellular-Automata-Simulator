package Controller;

import Model.Model;
import View.View;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class Controller {

  private Model mainModel;
  private final View mainView;
  private boolean simIsSet=false;

  public Controller() {
    //initialize the GUI
    this.mainView = new View();
  }

  //on button press
  public void initializeSimulation(String fileName, String modelType, String fileOut) {
    this.mainModel = new Model(fileName, modelType, fileOut);
    mainView.initializeFrontEndCells(modelType, mainModel.getNumberOfRows(), mainModel.getNumberOfColumns(), mainModel.getGridOfCells());
    simIsSet=true;
  }

  public void gameStep() {
    if(simIsSet) {
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

  public void handleKeyInput(KeyCode code) {
    switch (code) {
      case SPACE -> mainModel.switchPause();
      case S -> mainModel.step();
    }
  }
}
