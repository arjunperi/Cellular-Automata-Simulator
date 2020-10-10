package Controller;

import Model.Model;
import View.View;
import javafx.scene.input.KeyCode;

import java.lang.reflect.InvocationTargetException;

public class Controller {

  private Model mainModel;
  private View mainView;
  private boolean startButtonPressed = false;

  public Controller(Model mainModel) {
    this.mainModel = mainModel;
    mainView = new View("English", this);
  }

  public int getCellState (int row, int column){
    return mainModel.getCellState(row,column);
  }

  public int getNumberOfColumns(){
    return mainModel.getNumberOfColumns();
  }

  public int getNumberOfRows(){
    return mainModel.getNumberOfRows();
  }

  public void transition(String token, String fileName){
    try{
      startButtonPressed = true;
      Class<?> cl = Class.forName("Model." + token);
      mainModel = (Model) cl.getDeclaredConstructor(String.class).newInstance(fileName);
      mainView.initializeFrontEndCells();
    }
    catch (IllegalAccessException | InstantiationException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
      throw new UnsupportedOperationException(String.format("Unrecognized command: %s", token));
    }
  }

  public boolean checkButtonPress(){
    return startButtonPressed;
  }

  public void handleKeyInput(KeyCode code) {
    switch (code) {
      case SPACE -> mainModel.switchPause();
      case S -> mainModel.step();
    }
  }

  public View getView(){
    return mainView;
  }

  public Model getModel() {
    return mainModel;
  }
}
